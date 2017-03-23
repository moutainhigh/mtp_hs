package com.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.constants.FunCodeConstants;
import com.common.util.DateHelp;
import com.common.util.TASProtoHelper;
import com.core.exception.ServiceException;
import com.dao.SendMsgMapper;
import com.dao.Impl.AmtRecDao;
import com.dao.Impl.ProductDao;
import com.dao.Impl.SendMsgDao;
import com.model.AmtRec;
import com.model.Product;
import com.model.SendMsg;
import com.model.SignRec;
import com.proto.CenterBank.Msg20101;
import com.proto.CenterBank.Msg20201;
import com.proto.CenterBank.Msg20301;
import com.proto.CenterBank.Msg20401;
import com.proto.CenterBank.Msg21301;
import com.proto.ExchangeCenter;
import com.service.SendMsgExchService;
import com.service.SendMsgService;


/**
 *  
 * ClassName: SendMsgExchServiceImpl.java
 * date: 2016年12月14日下午2:33:17
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class SendMsgExchServiceImpl implements SendMsgExchService {
	private static final Logger LOGGER = Logger.getLogger(SendMsgExchServiceImpl.class);
	/**
	 *  
	 * ClassName: SendMsgExchServiceImpl.java
	 * date: 2016年12月14日下午2:33:17
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private SendMsgMapper sendMsgMapper;
	@Autowired
	private SendMsgService sendMsgService;
	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private AmtRecDao amtRecDao;
	@Override
	public void sendMsg20301(SignRec signRec) throws Exception {
		try{
			LOGGER.info("*组发送至交易所的签约报文Msg20301报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(20301); // 功能号
			Msg20301.Builder msg20301 = Msg20301.newBuilder();
			msg20301.setTranNo(signRec.getTranNo());
			msg20301.setBankDate(signRec.getTranDate());
			msg20301.setBankSeq(signRec.getBankSeq());
			msg20301.setExchNo(signRec.getExchNo());
			msg20301.setTradeAcct(signRec.getTradeAcct());
			msg20301.setAcct(signRec.getAcct());
			msg20301.setAcctName(signRec.getAcctName());
//			msg20301.setCurrency(signRec.getCurrency());
			msg20301.setCardBankNo("null");
			msg20301.setCardAcct(signRec.getCardAcct());
			msg20301.setCardName(signRec.getCardName());
			msg20301.setAcctType(signRec.getAcctType());
			msg20301.setChangeType(signRec.getChangeType());

			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			sendMsg.setSendMsgId(signRec.getSendMsgId()); // 往包ID
			// sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(0); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("20301"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(signRec.getSignRecId()); // 业务流水号
			sendMsg.setSendMsg(msg20301.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgMapper.insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg20301.build().toByteArray(), FunCodeConstants.MSG20301, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送银行签约请求报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(0);
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 *  
	 * ClassName: SendMsgExchServiceImpl.java
	 * date: 2016年12月14日下午2:47:49
	 * @author yu.jian
	 * @version
	 */
	@Override
	public void sendMsg20401(SignRec signRec) throws Exception {
		try{
			LOGGER.info("*组发送至交易所的解约报文Msg20401报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(20401); // 功能号
			Msg20401.Builder msg20401 = Msg20401.newBuilder();
			msg20401.setTranNo(signRec.getTranNo());
			msg20401.setBankDate(signRec.getTranDate());
			msg20401.setBankSeq(signRec.getBankSeq());
			msg20401.setExchNo(signRec.getExchNo());
			msg20401.setTradeAcct(signRec.getTradeAcct());
			msg20401.setAcct(signRec.getAcct());
			msg20401.setAcctName(signRec.getAcctName());
			msg20401.setCurrency(signRec.getCurrency()==null?"":signRec.getCurrency());

			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			sendMsg.setSendMsgId(signRec.getSendMsgId()); // 往包ID
			// sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(0); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("20401"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(signRec.getSignRecId()); // 业务流水号
			sendMsg.setSendMsg(msg20401.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgMapper.insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg20401.build().toByteArray(), FunCodeConstants.MSG20401, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送银行签约请求报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(0);
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 *  
	 * ClassName: SendMsgExchServiceImpl.java
	 * date: 2016年12月14日下午4:39:24
	 * @author yu.jian
	 * @version
	 */
	@Override
	public void sendMsg20101(AmtRec amtRec) throws Exception {
		try{
			LOGGER.info("*组发送至交易所的入金请求报文Msg20101报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(20101); // 功能号
			Msg20101.Builder msg20101 = Msg20101.newBuilder();
			msg20101.setTranNo(amtRec.getTranNo());
			msg20101.setBankDate(amtRec.getTranDate());
			msg20101.setBankSeq(amtRec.getBankSeq());
			msg20101.setExchNo(amtRec.getExchNo());
			msg20101.setAcct(amtRec.getAcct());
			msg20101.setAcctName(amtRec.getAcctName());
			msg20101.setAmt(amtRec.getAmt().doubleValue());
			msg20101.setRemark(amtRec.getRemark());
			msg20101.setCardBankNo("null");
			msg20101.setCardAcct(amtRec.getCardAcct());
			msg20101.setCardName(amtRec.getCardName());
			msg20101.setCardStatusUpdate(0);

			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			sendMsg.setSendMsgId(amtRec.getSendMsgId()); // 往包ID
			// sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(0); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("20101"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(amtRec.getAmtRecId()); // 业务流水号
			sendMsg.setSendMsg(msg20101.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgMapper.insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg20101.build().toByteArray(), FunCodeConstants.MSG20101, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送银行入金请求报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(0);
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 *  
	 * ClassName: SendMsgExchServiceImpl.java
	 * date: 2016年12月14日下午4:54:54
	 * @author yu.jian
	 * @version
	 */
	@Override
	public void sendMsg20201(AmtRec amtRec) throws Exception {
		try{
			LOGGER.info("*组发送至交易所的出金请求报文Msg20201报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(20201); // 功能号
			Msg20201.Builder msg20201 = Msg20201.newBuilder();
			msg20201.setTranNo(amtRec.getTranNo());
			msg20201.setBankDate(amtRec.getTranDate());
			msg20201.setBankSeq(amtRec.getBankSeq());
			msg20201.setExchNo(amtRec.getExchNo());
			msg20201.setAcct(amtRec.getAcct());
			msg20201.setAcctName(amtRec.getAcctName());
			msg20201.setAmt(amtRec.getAmt().doubleValue());
			msg20201.setRemark(amtRec.getRemark());
			msg20201.setCardBankNo("null");
			msg20201.setCardAcct(amtRec.getCardAcct());
			msg20201.setCardName(amtRec.getCardName());
			msg20201.setIsApply("0");

			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			sendMsg.setSendMsgId(amtRec.getSendMsgId()); // 往包ID
			// sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(0); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("20201"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(amtRec.getAmtRecId()); // 业务流水号
			sendMsg.setSendMsg(msg20201.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgMapper.insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg20201.build().toByteArray(), FunCodeConstants.MSG20201, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送银行出金请求报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(0);
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		}catch(Exception e){
			throw e;
		}
	}
	
	/*
	 * 清算中心向交易所推送审核结果
	 */
	@Override
	public void sendMsg21301(HashMap map) throws Exception {
		try{
			LOGGER.info("*组发送至交易所的推送审核结果请求报文Msg21301报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(21301); // 功能号
			Msg21301.Builder msg21301 = Msg21301.newBuilder();
			String centerSeq=String.valueOf(map.get("tradeSerialNo"));//交易所流水号（原中心流水号 ）
			String tranNo="";
			Long sendMsgId=null;
			msg21301.setBankDate(String.valueOf(map.get("initDate")));//业务日期
			msg21301.setBankSeq(String.valueOf(map.get("serialNo")));//银行流水号
			msg21301.setOrgSeq(centerSeq);
			msg21301.setCheckDate(String.valueOf(map.get("initDate")));
			msg21301.setExtendInfo(String.valueOf(map.get("auditSerialNo")));
			//4:审核通过;5:审核不通过;6:审核通过，执行失败
			String status=String.valueOf(map.get("auditStatus"));
			if(status.equals("4")){
				msg21301.setVerifyStatus("1");
			}
			if(status.equals("5")){
				msg21301.setVerifyStatus("2");
			}
			if(status.equals("6")){
				msg21301.setVerifyStatus("3");
			}
			msg21301.setVerifyDesc(String.valueOf(map.get("auditRemark")));
			
			String functionCode=String.valueOf(map.get("functionNo"));//审核功能号
			if(functionCode.equals("378001") || functionCode.equals("378002") || functionCode.equals("378003")){//商品新增修改状态变更
				
				Product product = productDao.getProductMapper().selectByCenterSeq(centerSeq);
				if(product!=null){
					tranNo=product.getTran_no();
				}
				msg21301.setTranNo(tranNo);
				msg21301.setSerNo("171");
				
			}else if(functionCode.equals("315002")){//客户出金
				
				AmtRec amtRec = new AmtRec();
				amtRec.setCenterSeq(centerSeq);
				List<AmtRec> amtRecList =amtRecDao.getAmtRecMapper().selectByBean(amtRec);
				if(amtRecList.size()!=0){
					tranNo=amtRecList.get(0).getTranNo();
					sendMsgId=amtRecList.get(0).getSendMsgId();
				}
				msg21301.setTranNo(tranNo);
				msg21301.setSerNo("102");
				
			}
			
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			sendMsg.setSendMsgId(sendMsgId); // 往包ID
			// sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(0); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("21301"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(Long.parseLong(centerSeq)); // 业务流水号
			sendMsg.setSendMsg(msg21301.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgMapper.insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg21301.build().toByteArray(), FunCodeConstants.MSG21301, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送推送审核结果请求报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(0);
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		}catch(Exception e){
			throw e;
		}
	}

}
