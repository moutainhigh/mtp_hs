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
import com.dao.Impl.AccountDao;
import com.dao.Impl.SendMsgDao;
import com.dao.Impl.SignRecDao;
import com.model.Account;
import com.model.Client;
import com.model.SendMsg;
import com.model.SignRec;
import com.model.Tran;
import com.muchinfo.common.util.PropertiesUtil;
import com.proto.CenterBank.Msg10302;
import com.proto.CenterBank.Msg10303;
import com.proto.ExchangeCenter;
import com.service.ExchSignService;
import com.service.SendMsgService;

import net.sf.json.JSONObject;

/**
 *  
 * ClassName: ExchSignServiceImpl.java
 * date: 2016年12月13日下午3:53:37
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class ExchSignServiceImpl implements ExchSignService {
	private static final Logger LOGGER = Logger.getLogger(ExchSignServiceImpl.class);
	/**
	 *  
	 * ClassName: ExchSignServiceImpl.java
	 * date: 2016年12月13日下午3:53:37
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
	private AccountDao accountDao;
	@Autowired
	private TranMapper tranMapper;
	@Override
	public void doSignReq(Msg10302 msg10302, Long recvMsgId) throws Exception {
		try{
			LOGGER.info("*客户端签约*");
			String hsExchNo=PropertiesUtil.getProperty("HSexchNO");
			Client client = clientMapper.selectByTradeAcct(msg10302.getTradeAcct());
			Tran tran = tranMapper.selectByPrimaryKey(msg10302.getTranNo());
			if(client == null)
				throw new ServiceException("*客户信息不存在*");
			LOGGER.info("*记录签约记录表*");
			SignRec signRec = new SignRec();
			Long signRecID = signRecDao.generateId();                          //生成签约记录ID
			signRec.setSignRecId(signRecID);                                 
			signRec.setClientId(client.getClientId());                       //用户Id
			signRec.setExchNo(msg10302.getExchNo());                             //交易所编号
			signRec.setTradeAcct(msg10302.getTradeAcct());                      //交易账号
			signRec.setTranNo(msg10302.getTranNo());    //银行业务编号
			signRec.setAcctType(Integer.parseInt(msg10302.getAcctType()));                       //银行账户类型
			signRec.setCurrency(msg10302.getCurrency());                           //币种
			signRec.setIsForce(msg10302.getIsForce());                               //是否强制
			signRec.setSignFlag(FinalConstants.SignFlag.SIGN_ON);               //签约标志	
			signRec.setChangeType(msg10302.getChangeType());                //变更类别
			signRec.setSenderType(FinalConstants.SenderType.EXCH);               //发起方类型
			signRec.setRecvMsgId(recvMsgId);                                   //来包ID
			Long  sendMsgId = sendMsgDao.generateId();            	         //生成往包ID主键
			signRec.setSendMsgId(sendMsgId);                                   //往包ID
			signRec.setSysTime(new Date());                                     //系统时间
			signRec.setDealStatus(FinalConstants.DealStatus.UNKNOW);
			signRec.setCardAcct(msg10302.getCardAcct());
			signRec.setCardName(msg10302.getCardName()==null?"":msg10302.getCardName());
			signRec.setExtendInfo(msg10302.getExtendInfo());
			signRec.setAcct(msg10302.getAcct()==null?"":msg10302.getAcct());
			signRec.setAcctName(msg10302.getAcctName()==null?"":msg10302.getAcctName());
			signRecDao.getCorpMapper().insert(signRec);
			LOGGER.info("*签约记录表插入成功*");
			
			client.setCurrency(signRec.getCurrency());
			clientMapper.updateByPrimaryKeySelective(client);
			
			
			JSONObject json = new JSONObject();
			json.put("requestId", msg10302.getCenterSeq());
			json.put("initDate",msg10302.getBankDate());
			json.put("exchangeId", hsExchNo);
			json.put("exchangeFundAccount", msg10302.getTradeAcct());
			json.put("bankProCode", tran.getBankProCode());        //银行产品代码Y
			json.put("bankAccount", msg10302.getCardAcct());
			json.put("bankAccountName", msg10302.getCardName());
			json.put("bankPassword", "");
			json.put("moneyType", CenterToExchUtil.HS_CURRENCY.get(msg10302.getCurrency()));
			if(msg10302.getIsForce()==0)
				json.put("signType", 1);          //签约类型（1：签约；2:解约；3:强签；4：强解；5：预签约）
			else if(msg10302.getIsForce()==1)
				json.put("signType", 3);
			json.put("custSign", "");
			json.put("uKeyStr", "");
			json.put("memberMainType", client.getClientType()== 1 ? 2 : 1);
			json.put("fullName", client.getClientName());
			json.put("idKind", CenterToExchUtil.HS_CERT_TYPE.get(client.getCertType()));
			json.put("idNo", client.getCertCode());
			json.put("busiDatetime", DateHelp.getCurrentDateTimeOfStringHS());
			
			String result = exchCallClearT2Service.dealMSG319006(json.toString());
			JSONObject jsonObject = JSONObject.fromObject(result);
			String serialNo = jsonObject.getString("serialNo");  //	银行流水号	serialNo
			int errorNo = jsonObject.getInt("errorNo"); //错误代码	errorNo
			String errorInfo = jsonObject.getString("errorInfo");//错误信息	errorInfo
			
			LOGGER.info("*组发送至中心的签约应答报文Msg10303报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(10303); // 功能号
			Msg10303.Builder msg10303 = Msg10303.newBuilder();
			msg10303.setTranNo(msg10302.getTranNo());
			msg10303.setBankDate(msg10302.getBankDate());
			msg10303.setBankSeq(serialNo);
			msg10303.setCenterSeq(msg10302.getCenterSeq());
			msg10303.setAcct(msg10302.getCardAcct());
			msg10303.setAcctName(msg10303.getCardName());
			if (errorNo == 0){
				msg10303.setRetCode(FinalConstants.RetCode.SUCCESS);
				
				LOGGER.info("*插入账户表*");
				Account account = new Account();
				account.setAccountId(accountDao.generateId());
				account.setTranNo(signRec.getTranNo());
				account.setClientId(client.getClientId());
				account.setExchNo(signRec.getExchNo());
				account.setTradeAcct(signRec.getTradeAcct());
				account.setAcct(msg10302.getCardAcct());
				account.setAcctName(msg10303.getCardName());
				account.setCurrency(signRec.getCurrency());
				account.setAcctType(signRec.getAcctType().shortValue());
				account.setCertType(signRec.getCertType());
				account.setCertCode(signRec.getCertCode());
				account.setClientName(signRec.getClientName());
				account.setMobile(signRec.getMobile());
				account.setEmail(signRec.getEmail());
				account.setExtendInfo(signRec.getExtendInfo());
				account.setSignTime(new Date());
				account.setTranSender(1);
				account.setSignStatus(1);
//				account.setCardStatus();
				accountDao.getAccountMapper().insert(account);
			}
			
			else if (errorNo == 99) {
				msg10303.setRetCode(FinalConstants.RetCode.UNKNOWN);
			} else {
				msg10303.setRetCode(FinalConstants.RetCode.FAIL);
			}
			msg10303.setRetDesc(errorInfo);
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			Long sendMsgID = sendMsgDao.generateId();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recvMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("10303"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(signRecID); // 业务流水号
			sendMsg.setSendMsg(msg10303.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg10303.build().toByteArray(), FunCodeConstants.MSG10303, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送签约应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
			/*}catch(ServiceException e){
			LOGGER.info("*组发送至中心的签约应答报文Msg10303报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(10303); // 功能号
			Msg10303.Builder msg10303 = Msg10303.newBuilder();
			msg10303.setTranNo(msg10302.getTranNo());
			msg10303.setBankDate(msg10302.getBankDate());
			msg10303.setCenterSeq(msg10302.getCenterSeq());
			msg10303.setAcct(msg10302.getCardAcct());
			msg10303.setAcctName(msg10303.getCardName());
			msg10303.setRetCode(FinalConstants.RetCode.FAIL);
			msg10303.setRetDesc(e.getMessage());
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			Long sendMsgID = sendMsgDao.generateId();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recvMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("10303"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(0L); // 业务流水号
			sendMsg.setSendMsg(msg10303.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg10303.build().toByteArray(), FunCodeConstants.MSG10303, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送签约应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);*/
		}catch(Exception e){
			throw e;
		}
	}

}
