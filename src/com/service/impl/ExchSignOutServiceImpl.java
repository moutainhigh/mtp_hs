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
import com.dao.ClientMapper;
import com.dao.TranMapper;
import com.dao.Impl.SendMsgDao;
import com.dao.Impl.SignRecDao;
import com.model.Client;
import com.model.SendMsg;
import com.model.SignRec;
import com.model.Tran;
import com.muchinfo.common.util.PropertiesUtil;
import com.proto.CenterBank.Msg10402;
import com.proto.CenterBank.Msg10403;
import com.proto.ExchangeCenter;
import com.service.ExchSignOutService;
import com.service.SendMsgService;

import net.sf.json.JSONObject;

/**
 * 
 * ClassName: ExchSignOutServiceImpl.java date: 2016年12月13日下午5:14:41
 * 
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class ExchSignOutServiceImpl implements ExchSignOutService {
	private static final Logger LOGGER = Logger.getLogger(ExchSignOutServiceImpl.class);
	/**
	 * 
	 * ClassName: ExchSignOutServiceImpl.java date: 2016年12月13日下午5:14:41
	 * 
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private SignRecDao signRecDao;
	@Autowired
	private ClientMapper clientMapper;
	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private ExchCallClearT2Service exchCallClearT2Service;
	@Autowired
	private SendMsgService sendMsgService;
	@Autowired
	private TranMapper tranMapper;

	@Override
	public void doSignOutReq(Msg10402 msg10402, long recMsgId) throws Exception {
		try {
			LOGGER.info("*客户端解约*");
			String hsExchNo=PropertiesUtil.getProperty("HSexchNO");
			Client client = clientMapper.selectByTradeAcct(msg10402.getTradeAcct());
			if (client == null)
				throw new ServiceException("*客户信息不存在*");
			Tran tran = tranMapper.selectByPrimaryKey(msg10402.getTranNo());
			LOGGER.info("*记录解约记录表*");
			SignRec signRec = new SignRec();
			Long signRecID = signRecDao.generateId(); // 生成解约记录ID
			signRec.setSignRecId(signRecID);
			signRec.setClientId(client.getClientId()); // 用户Id
			signRec.setExchNo(msg10402.getExchNo()); // 交易所编号
			signRec.setTradeAcct(msg10402.getTradeAcct()); // 交易账号
			signRec.setTranNo(msg10402.getTranNo()); // 银行业务编号
			signRec.setCurrency(msg10402.getCurrency()); // 币种
			signRec.setSignFlag(FinalConstants.SignFlag.SIGN_ON); // 解约标志
			signRec.setSenderType(FinalConstants.SenderType.EXCH); // 发起方类型
			signRec.setRecvMsgId(recMsgId); // 来包ID
			Long sendMsgId = sendMsgDao.generateId(); // 生成往包ID主键
			signRec.setSendMsgId(sendMsgId); // 往包ID
			signRec.setSysTime(new Date()); // 系统时间
			signRec.setDealStatus(FinalConstants.DealStatus.UNKNOW);
			signRec.setAcct(msg10402.getAcct() == null ? "" : msg10402.getAcct());
			signRec.setAcctName(msg10402.getAcctName() == null ? "" : msg10402.getAcctName());
			signRecDao.getCorpMapper().insert(signRec);
			LOGGER.info("*解约记录表插入成功*");

			JSONObject json = new JSONObject();
			json.put("requestId", msg10402.getCenterSeq());
			json.put("initDate", msg10402.getBankDate());
			json.put("exchangeId", hsExchNo);
			json.put("exchangeFundAccount", msg10402.getTradeAcct());
			json.put("bankPassword", "");
			json.put("moneyType", CenterToExchUtil.HS_CURRENCY.get(msg10402.getCurrency()));
			json.put("bisinType", "1"); // 银行业务类型（1：普通；2：冲正；3：重发；4：调账）
			json.put("bankProCode", tran.getBankProCode()); // 银行产品代码Y
			json.put("accountName", msg10402.getAcctName());
			json.put("bankAccount", msg10402.getAcct());
			json.put("busiDatetime", DateHelp.getCurrentDateTimeOfStringHS());
			json.put("memberMainType", client.getClientType());
			json.put("fullName", client.getClientName());
			json.put("idKind", CenterToExchUtil.HS_CERT_TYPE.get(client.getCertType()));
			json.put("idNo", client.getCertCode());
			json.put("signType", 2); // 解约类型（1：解约；2:解约；3:强签；4：强解；5：预解约）

			String result = exchCallClearT2Service.dealMSG319006(json.toString());
			JSONObject jsonObject = JSONObject.fromObject(result);
			String serialNo = jsonObject.getString("serialNo"); // 银行流水号
																// serialNo
			int errorNo = jsonObject.getInt("errorNo"); // 错误代码 errorNo
			String errorInfo = jsonObject.getString("errorInfo");// 错误信息
																	// errorInfo

			LOGGER.info("*组发送至中心的解约应答报文Msg10403报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(10403); // 功能号
			Msg10403.Builder msg10403 = Msg10403.newBuilder();
			msg10403.setTranNo(msg10402.getTranNo());
			msg10403.setBankDate(msg10402.getBankDate());
			msg10403.setBankSeq(serialNo);
			msg10403.setCenterSeq(msg10402.getCenterSeq());
			if (errorNo == 0){
				signRec.setDealDesc(errorInfo);
				signRec.setDealStatus(FinalConstants.DealStatus.SUCCESS);
				msg10403.setRetCode(FinalConstants.RetCode.SUCCESS);
				msg10403.setRetDesc(errorInfo);
			}
			else if (errorNo == 99) {
				signRec.setDealDesc(errorInfo);
				msg10403.setRetCode(FinalConstants.RetCode.UNKNOWN);
				msg10403.setRetDesc("正在审核中，等待后续审核通知");
			} else {
				signRec.setDealStatus(FinalConstants.DealStatus.FAIL);
				signRec.setDealDesc(errorInfo);
				msg10403.setRetCode(FinalConstants.RetCode.FAIL);
				msg10403.setRetDesc(errorInfo);
			}
			signRecDao.getCorpMapper().updateByPrimaryKeySelective(signRec);
		
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			Long sendMsgID = sendMsgDao.generateId();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("10403"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(signRecID); // 业务流水号
			sendMsg.setSendMsg(msg10403.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg10403.build().toByteArray(), FunCodeConstants.MSG10403, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送解约应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		} catch (ServiceException e) {
			LOGGER.info("*组发送至中心的解约应答报文Msg10403报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(10403); // 功能号
			Msg10403.Builder msg10403 = Msg10403.newBuilder();
			msg10403.setTranNo(msg10402.getTranNo());
			msg10403.setBankDate(msg10402.getBankDate());
			msg10403.setCenterSeq(msg10402.getCenterSeq());
			msg10403.setRetCode(FinalConstants.RetCode.FAIL);
			msg10403.setRetDesc(e.getMessage());
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			Long sendMsgID = sendMsgDao.generateId();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("10403"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(0L); // 业务流水号
			sendMsg.setSendMsg(msg10403.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg10403.build().toByteArray(), FunCodeConstants.MSG10403, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送解约应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		} catch (Exception e) {
			throw e;
		}
	}

}
