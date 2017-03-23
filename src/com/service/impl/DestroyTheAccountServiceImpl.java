package com.service.impl;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccps.exchgate.api.t2.service.client.ExchCallClearT2Service;
import com.common.constants.FinalConstants;
import com.common.constants.FunCodeConstants;
import com.common.util.CenterToExchUtil;
import com.common.util.DateHelp;
import com.common.util.TASProtoHelper;
import com.core.exception.ServiceException;
import com.dao.Impl.ClientDao;
import com.dao.Impl.ClientRecDao;
import com.dao.Impl.SendMsgDao;
import com.model.Client;
import com.model.ClientRec;
import com.model.SendMsg;
import com.muchinfo.common.util.PropertiesUtil;
import com.proto.CenterBank.Msg10802;
import com.proto.CenterBank.Msg10803;
import com.proto.ExchangeCenter;
import com.service.DestroyTheAccountService;
import com.service.SendMsgService;

import net.sf.json.JSONObject;

/**
 * 
 * ClassName: DestroyTheAccountServiceImpl.java date: 2016年12月13日下午2:39:41
 * 
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class DestroyTheAccountServiceImpl implements DestroyTheAccountService {
	private static final Logger LOGGER = Logger.getLogger(DestroyTheAccountServiceImpl.class);
	/**
	 * 
	 * ClassName: DestroyTheAccountServiceImpl.java date: 2016年12月13日下午2:39:42
	 * 
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private ClientRecDao clientRecDao;
	@Autowired
	private ExchCallClearT2Service exchCallClearT2Service;
	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private SendMsgService sendMsgService;
	@Autowired
	private ClientDao clientDao;

	@Override
	public void doDesTheAccReq(Msg10802 msg10802, long recMsgId) throws Exception {
		try {
			LOGGER.info("*客户销户*");
			String hsExchNo=PropertiesUtil.getProperty("HSexchNO");
			Client client = clientDao.getClientMapper().selectByTradeAcct(msg10802.getTradeAcct());
			if (client == null)
				throw new ServiceException("*客户信息不存在*");
			LOGGER.info("*插入客户信息记录表*");
			ClientRec clientRec = new ClientRec();
			Long clientRecId = clientRecDao.generateId();
			clientRec.setClientRecId(clientRecId);
			clientRec.setExchNo(msg10802.getExchNo());
			clientRec.setTradeAcct(msg10802.getTradeAcct());
			clientRec.setTranNo(msg10802.getTranNo());
			clientRec.setTranDate(msg10802.getBankDate()); // 业务日期
			clientRec.setClientName(client.getClientName());
			clientRec.setClientType(client.getClientType());
			clientRec.setCertType(client.getCertType()); // 证件类型
			clientRec.setCertCode(client.getCertCode()); // 证件号码
			clientRec.setContactPhone(client.getContactPhone()); // 联系人电话
			clientRec.setChangeType(FinalConstants.ChangeType.CANCEL);
			clientRec.setContactFax(client.getContactFax());
			clientRec.setContactEmail(client.getContactEmail());
			clientRec.setIsSendLink(0);
			clientRec.setOrgCode(client.getOrgCode());
			clientRec.setSex(client.getSex());
			clientRec.setAddress(client.getAddress());
			clientRecDao.getClientRecMapper().insert(clientRec);
			LOGGER.info("*插入客户信息记录表成功*");
			
			JSONObject json = new JSONObject();
			json.put("exchangeId", hsExchNo);
			json.put("memCode", msg10802.getTradeAcct());
			json.put("fullName", client.getClientName());
			json.put("idKind", CenterToExchUtil.HS_CERT_TYPE.get(client.getCertType()));
			json.put("idNo", client.getCertCode());
			json.put("busiDatetime", msg10802.getBankDate());
			
			String result = exchCallClearT2Service.dealMSG309002(json.toString());
			JSONObject jsonObj = JSONObject.fromObject(result);
			int errorNo = jsonObj.getInt("errorNo");
			String errorInfo = jsonObj.getString("errorInfo");
			
			LOGGER.info("*组发送至中心的销户报文Msg10803报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(10803); // 功能号
			Msg10803.Builder msg10803 = Msg10803.newBuilder();
			msg10803.setTranNo(msg10802.getTranNo());
			msg10803.setBankDate(msg10802.getBankDate());
//			msg10803.setBankSeq(clientRecId + "");
			msg10803.setCenterSeq(msg10802.getCenterSeq());
			if (errorNo == 0){
				msg10803.setRetCode(FinalConstants.RetCode.SUCCESS);
				client.setClientStatus(FinalConstants.ClientStatus.CANCEL);
				clientDao.getClientMapper().updateByPrimaryKeySelective(client);
			}
			else if (errorNo == 99) {
				msg10803.setRetCode(FinalConstants.RetCode.UNKNOWN);
				msg10803.setRetDesc("正在审核中，等待后续审核通知");
			} else {
				msg10803.setRetCode(FinalConstants.RetCode.FAIL);
				msg10803.setRetDesc(errorInfo);
			}
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			Long sendMsgID = sendMsgDao.generateId();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("10803"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(clientRecId); // 业务流水号
			sendMsg.setSendMsg(msg10803.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg10803.build().toByteArray(), FunCodeConstants.MSG10803, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送销户应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);

		} catch (ServiceException e) {
			LOGGER.info("*组发送至中心的销户报文Msg10803报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(10803); // 功能号
			Msg10803.Builder msg10803 = Msg10803.newBuilder();
			msg10803.setTranNo(msg10802.getTranNo());
			msg10803.setBankDate(msg10802.getBankDate());
//			msg10803.setBankSeq(clientRecId + "");
			msg10803.setCenterSeq(msg10802.getCenterSeq());
			msg10803.setRetCode(FinalConstants.RetCode.FAIL);
			msg10803.setRetDesc(e.getMessage());
			
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			Long sendMsgID = sendMsgDao.generateId();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("10803"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(0L); // 业务流水号
			sendMsg.setSendMsg(msg10803.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg10803.build().toByteArray(), FunCodeConstants.MSG10803, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功销户应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		} catch (Exception e) {

		}
	}

}
