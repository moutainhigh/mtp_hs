package com.ccps.exchgate.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ccps.exchgate.api.t2.service.server.ExchOwnT2Service;
import com.common.util.CenterToExchUtil;
import com.core.exception.ServiceException;
import com.dao.ClientMapper;
import com.dao.TranMapper;
import com.dao.Impl.AmtRecDao;
import com.dao.Impl.SecretKeyDao;
import com.dao.Impl.SendMsgDao;
import com.dao.Impl.SignRecDao;
import com.model.AmtRec;
import com.model.Client;
import com.model.SecretKey;
import com.model.SignRec;
import com.model.Tran;
import com.muchinfo.common.util.PropertiesUtil;
import com.service.GetExchRspService;
import com.service.GetHSFileService;
import com.service.SendMsgExchService;

//import com.muchinfo.mtp.bankservice.ccps.exchgate.api.pojo.ExchBaseResp;

import net.sf.json.JSONObject;

/**
 * 清算发起
 * @author jiangzz
 *
 */

@Service
@Scope("singleton")
public class ExchOwnT2ServiceImpl implements ExchOwnT2Service {
	
	private static final Logger LOGGER = Logger.getLogger(ExchOwnT2ServiceImpl.class);
//	private ExchBaseResp outMess ;	
	 //---------------------------------清算中心(广清所)向交易所发起的服务调用-------------------------------------------//
		/**
		 * @author yu.jian
		 *客户与银行签约/解约通知
		 * @see com.ccps.exchgate.api.t2.service.server.ExchOwnT2Service#bankSignOrOut(java.lang.String)
		 */
	@Autowired
	private SignRecDao signRecDao;
	@Autowired
	private ClientMapper clientMapper;
	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private SendMsgExchService sendMsgExchServiceImpl;
	@Autowired
	private AmtRecDao amtRecDao;
	@Autowired
	private SecretKeyDao secretKeyDao;
	@Autowired
	private GetHSFileService getHSFileServiceImpl;
	@Autowired
	private GetExchRspService getExchRspServiceImpl;
	@Autowired
	private TranMapper tranMapper;
	@Override
	public String bankSignOrOut(String reqStr) {
		Long t1 = System.currentTimeMillis();
		LOGGER.info("*银行签约/解约通知*");
		LOGGER.info("*取银行签解约信息*");
		JSONObject json = JSONObject.fromObject(reqStr);
		long initDate = json.getLong("initDate"); // 业务发生日期
		String serialNo = json.getString("serialNo"); // 银行流水号
		String exchangeId = json.getString("exchangeId"); // 交易所代码
		String exchangeFundAccount = json.getString("exchangeFundAccount"); // 交易所资金账号
		String bankProCode = json.getString("bankProCode"); // 银行产品代码
		Tran tran = tranMapper.selectByBankPro(bankProCode);
		String bankAccountName = json.getString("fullName"); // 银行账户名称
		String bankAccount = json.getString("bankAccount"); // 银行帐号
		// String fundPassword = json.getString("fundPassword"); //资金密码
		String moneyType = json.getString("moneyType"); // 币种编码
		String signType = json.getString("signType"); // 签约类型（1：签约；2:解约；3:强签；4：强解；5：预签约）
		// String custSign = json.getString("custSign"); //签名信息
		String memberMainType = json.getString("memberMainType"); // 会员主体类型
		String fullName = json.getString("fullName"); // 会员全称
		String idKind = json.getString("idKind"); // 证件类型
		String idNo = json.getString("idNo"); // 证件号码

		Client client = clientMapper.selectByTradeAcct(exchangeFundAccount);
		if (client == null)
			throw new ServiceException("*客户信息不存在*");
		LOGGER.info("*记录签约记录表*");
		SignRec signRec = new SignRec();
		Long signRecID = signRecDao.generateId(); // 生成签约记录ID
		signRec.setSignRecId(signRecID);
		signRec.setTranDate(initDate + "");
		signRec.setClientId(client.getClientId()); // 用户Id
		signRec.setExchNo(PropertiesUtil.getProperty("exchNO")); // 交易所编号
		signRec.setTradeAcct(exchangeFundAccount); // 交易账号
		signRec.setTranNo(tran.getTranNo()); // 银行业务编号
		signRec.setAcct(bankAccount);
		signRec.setAcctName(bankAccountName);
		signRec.setAcctType(Integer.parseInt(memberMainType) == 1 ? 2 : 1); // 银行账户类型
		signRec.setCurrency(moneyType.equals("CNY") ? "RMB" : moneyType); // 币种
		if (Integer.parseInt(signType) == 3||Integer.parseInt(signType) == 4)
			signRec.setIsForce(1); // 是否强制
		else
			signRec.setIsForce(0);
		signRec.setSignFlag(Integer.parseInt(signType)); // 签约标志
		signRec.setChangeType(1); // 变更类别
		signRec.setSenderType(2); // 发起方类型
		Long sendMsgId = sendMsgDao.generateId(); // 生成往包ID主键
		signRec.setSendMsgId(sendMsgId); // 往包ID
		signRec.setSysTime(new Date()); // 系统时间
		signRec.setDealStatus(9);
		signRec.setCardAcct(bankAccount);
		signRec.setCardName(bankAccountName == null ? "" : bankAccountName);
		signRec.setBankSeq(serialNo);
		signRec.setClientName(fullName);
		signRec.setCertType(CenterToExchUtil.SH_CERT_TYPE.get(idKind));
		signRec.setCertCode(idNo);
		signRecDao.getCorpMapper().insert(signRec);
		LOGGER.info("*签约记录表插入成功*");
		
		client.setClientAcctType(signRec.getAcctType());
		client.setCertType(signRec.getCertType());
		client.setCertCode(signRec.getCertCode());
		client.setCurrency(signRec.getCurrency());
		clientMapper.updateByPrimaryKeySelective(client);
		try {
			if(Integer.parseInt(signType)==1||Integer.parseInt(signType)==3)
				sendMsgExchServiceImpl.sendMsg20301(signRec);
			else if(Integer.parseInt(signType)==2||Integer.parseInt(signType)==4)
				sendMsgExchServiceImpl.sendMsg20401(signRec);
			else
				new Exception("*签解约类型错误*");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String msg = null;
		try {
			msg = getExchRspServiceImpl.getRspMsg(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Long t2 = System.currentTimeMillis();

		System.out.println("回给广清的消息----------------" + msg + "----时间差" + (t2 - t1));
		return msg;
	}
		
	/**
	 *  客户银行信息变更
	 * ClassName: ExchOwnT2ServiceImpl.java
	 * date: 2016年12月14日下午3:28:08
	 * @author yu.jian
	 * @version
	 */
	@Override
	public String bankInfoChange(String reqStr) {
		Long t1 = System.currentTimeMillis();
		LOGGER.info("*银行签约信息变更通知*");
		LOGGER.info("*取请求信息*");
		
		JSONObject json = JSONObject.fromObject(reqStr);
		
		long initDate = json.getLong("initDate"); // 业务发生日期
		String serialNo = json.getString("serialNo"); // 银行流水号
		String exchangeId = json.getString("exchangeId"); // 交易所代码
		String exchangeFundAccount = json.getString("exchangeFundAccount"); // 交易所资金账号
		String bankProCode = json.getString("bankProCode"); // 银行产品代码
		String bankAccountName = json.getString("bankAccountName"); // 银行账户名称
		String bankAccount = json.getString("bankAccount"); // 银行帐号
		// String fundPassword = json.getString("fundPassword"); //资金密码
		String fullName = json.getString("fullName"); // 会员全称，只有名称能变更

		Client client = clientMapper.selectByTradeAcct(exchangeFundAccount);
		if (client == null)
			throw new ServiceException("*客户信息不存在*");
		
		Tran tran = tranMapper.selectByBankPro(bankProCode);
		
		LOGGER.info("*记录签约记录表*");
		SignRec signRec = new SignRec();
		Long signRecID = signRecDao.generateId(); // 生成签约记录ID
		signRec.setSignRecId(signRecID);
		signRec.setTranDate(initDate + "");
		signRec.setClientId(client.getClientId()); // 用户Id
		signRec.setExchNo(PropertiesUtil.getProperty("exchNO")); // 交易所编号
		signRec.setTradeAcct(exchangeFundAccount); // 交易账号
		signRec.setTranNo(tran.getTranNo()); // 银行业务编号
		signRec.setAcct(bankAccount);
		signRec.setAcctName(bankAccountName);
		signRec.setAcctType(client.getClientAcctType()); // 银行账户类型
		signRec.setChangeType(2); // 变更类别
		signRec.setSenderType(2); // 发起方类型
		Long sendMsgId = sendMsgDao.generateId(); // 生成往包ID主键
		signRec.setSendMsgId(sendMsgId); // 往包ID
		signRec.setSysTime(new Date()); // 系统时间
		signRec.setDealStatus(9);
		signRec.setCardAcct(bankAccount);
		signRec.setCardName(bankAccountName == null ? "" : bankAccountName);
		signRec.setBankSeq(serialNo);
		signRec.setClientName(fullName);
		signRec.setCertType(client.getCertType());
		signRec.setCertCode(client.getCertCode());
		signRecDao.getCorpMapper().insert(signRec);
		LOGGER.info("*签约记录表插入成功*");
		
		client.setClientName(fullName);
		clientMapper.updateByPrimaryKeySelective(client);
		
		try {
			sendMsgExchServiceImpl.sendMsg20301(signRec);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String msg = null;
		try {
			msg = getExchRspServiceImpl.getRspMsg(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long t2 = System.currentTimeMillis();

		System.out.println("回给广清的消息----------------" + msg + "----时间差" + (t2 - t1));
		return msg;
	}

	/**
	 * @author yu.jian 客户银行卡变更
	 * @see com.ccps.exchgate.api.t2.service.server.ExchOwnT2Service#bankNoChange(java.lang.String)
	 */
	public String bankNoChange(String reqStr) {
		Long t1 = System.currentTimeMillis();
		LOGGER.info("*银行变更银行卡通知*");
		LOGGER.info("*取银请求信息*");
		JSONObject json = JSONObject.fromObject(reqStr);
		
		long initDate = json.getLong("initDate"); // 业务发生日期
		String serialNo = json.getString("serialNo"); // 银行流水号
		String exchangeId = json.getString("exchangeId"); // 交易所代码
		String exchangeFundAccount = json.getString("exchangeFundAccount"); // 交易所资金账号
		String bankProCode = json.getString("bankProCode"); // 银行产品代码
		String bankAccountName = json.getString("bankAccountName"); // 银行账户名称
		String bankAccount = json.getString("bankAccount"); // 银行帐号
		// String fundPassword = json.getString("fundPassword"); //资金密码

		Client client = clientMapper.selectByTradeAcct(exchangeFundAccount);
		if (client == null)
			throw new ServiceException("*客户信息不存在*");
		
		Tran tran = tranMapper.selectByBankPro(bankProCode);
		LOGGER.info("*记录签约记录表*");
		SignRec signRec = new SignRec();
		Long signRecID = signRecDao.generateId(); // 生成签约记录ID
		signRec.setSignRecId(signRecID);
		signRec.setTranDate(initDate + "");
		signRec.setClientId(client.getClientId()); // 用户Id
		signRec.setExchNo(PropertiesUtil.getProperty("exchNO")); // 交易所编号
		signRec.setTradeAcct(exchangeFundAccount); // 交易账号
		signRec.setTranNo(tran.getTranNo()); // 银行业务编号
		signRec.setAcct(bankAccount);
		signRec.setAcctName(bankAccountName);
		signRec.setAcctType(client.getClientAcctType()); // 银行账户类型
		signRec.setChangeType(6); // 变更类别
		signRec.setSenderType(2); // 发起方类型
		Long sendMsgId = sendMsgDao.generateId(); // 生成往包ID主键
		signRec.setSendMsgId(sendMsgId); // 往包ID
		signRec.setSysTime(new Date()); // 系统时间
		signRec.setDealStatus(9);
		signRec.setCardAcct(bankAccount);
		signRec.setCardName(bankAccountName == null ? "" : bankAccountName);
		signRec.setBankSeq(serialNo);
		signRec.setClientName(client.getClientName());
		signRec.setCertType(client.getCertType());
		signRec.setCertCode(client.getCertCode());
		signRecDao.getCorpMapper().insert(signRec);
		LOGGER.info("*签约记录表插入成功*");

		try {
			sendMsgExchServiceImpl.sendMsg20301(signRec);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String msg = null;
		try {
			msg = getExchRspServiceImpl.getRspMsg(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long t2 = System.currentTimeMillis();

		System.out.println("回给广清的消息----------------" + msg + "----时间差" + (t2 - t1));
		return msg;
	}

	/**
	 * @author yu.jian 客户入金
	 * @see com.ccps.exchgate.api.t2.service.server.ExchOwnT2Service#fundIn(java.lang.String)
	 */
	public String fundIn(String reqStr) {
		Long t1 = System.currentTimeMillis();
		LOGGER.info("*银行入金请求*");
		LOGGER.info("*取请求信息*");
		
		JSONObject json = JSONObject.fromObject(reqStr);
		long initDate = json.getLong("initDate"); // 业务发生日期
		String serialNo = json.getString("serialNo"); // 银行流水号
		String exchangeId = json.getString("exchangeId"); // 交易所代码
		String exchangeFundAccount = json.getString("exchangeFundAccount"); // 交易所资金账号
		String bankProCode = json.getString("bankProCode"); // 银行产品代码
		String accountName = json.getString("accountName"); // 银行账户名称
		String bankAccount = json.getString("bankAccount"); // 银行帐号
		// String fundPassword = json.getString("fundPassword"); //资金密码 
//		String idKind = json.getString("idKind"); // 证件类型
//		String idNo = json.getString("idNo"); // 证件号码
//		String memberMainType = json.getString("memberMainType"); // 会员主体类型
//		String fullName = json.getString("fullName"); // 会员全称
		String moneyType = json.getString("moneyType");
//		String bisinType = json.getString("bisinType");
		double occurAmount = json.getDouble("occurAmount");
		String remark = json.getString("remark");
		
		Tran tran = tranMapper.selectByBankPro(bankProCode);
		LOGGER.info("*插入出入金记录*");
		AmtRec amtRec = new AmtRec();
		Long amtRecID = amtRecDao.generateId();
		amtRec.setAmtRecId(amtRecID);
		amtRec.setExchNo(PropertiesUtil.getProperty("exchNO"));                        //交易所编号	exch_no
		amtRec.setTradeAcct(exchangeFundAccount);                   //交易账号	trade_acct
		amtRec.setTranNo(tran.getTranNo());                       //银行业务编号	tran_no
		amtRec.setTranDate(initDate+"");            //业务日期	tran_date
		amtRec.setAcct(bankAccount);                             //银行账号	acct
		amtRec.setAcctName(accountName==null?"":accountName);                     //银行账户名	acct_name
		amtRec.setAmt(BigDecimal.valueOf(occurAmount)); 						//金额	amt
		amtRec.setCurrency(moneyType.equals("CNY") ? "RMB" : moneyType);                       //币种	currency
		amtRec.setRemark(remark);                               //备注	remark
		amtRec.setCardAcct(bankAccount);                //银行卡号
		amtRec.setCardName(accountName==null?"":accountName);                 //银行卡户名
		amtRec.setOutInFlag(1);                     //出入金标志	out_in_flag
		amtRec.setSenderType(2);               //发起方类型	sender_type
		amtRec.setDealStatus(9);                      //处理状态	deal_status
		amtRec.setBankSeq(serialNo);
		Long sendMsgId = sendMsgDao.generateId();
		amtRec.setSendMsgId(sendMsgId);                                          //往包ID	send_msg_id
		amtRec.setSysTime(new Date());  
		amtRecDao.getAmtRecMapper().insert(amtRec);
		
		try {
			sendMsgExchServiceImpl.sendMsg20101(amtRec);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String msg = null;
		try {
			msg = getExchRspServiceImpl.getRspMsg(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long t2 = System.currentTimeMillis();

		System.out.println("回给广清的消息----------------" + msg + "----时间差" + (t2 - t1));
		return msg;
	}

	/**
	 * 
	 * @author yu.jian 客户出金
	 * @see com.ccps.exchgate.api.t2.service.server.ExchOwnT2Service#fundOut(java.lang.String)
	 */
	public String fundOut(String reqStr) {

		Long t1 = System.currentTimeMillis();
		LOGGER.info("*银行出金请求*");
		LOGGER.info("*取请求信息*");
		
		JSONObject json = JSONObject.fromObject(reqStr);
		long initDate = json.getLong("initDate"); // 业务发生日期
		String serialNo = json.getString("serialNo"); // 银行流水号
		String exchangeId = json.getString("exchangeId"); // 交易所代码
		String exchangeFundAccount = json.getString("exchangeFundAccount"); // 交易所资金账号
		String bankProCode = json.getString("bankProCode"); // 银行产品代码
		String accountName = json.getString("accountName"); // 银行账户名称
		String bankAccount = json.getString("bankAccount"); // 银行帐号
		// String fundPassword = json.getString("fundPassword"); //资金密码 
//		String idKind = json.getString("idKind"); // 证件类型
//		String idNo = json.getString("idNo"); // 证件号码
//		String memberMainType = json.getString("memberMainType"); // 会员主体类型
//		String fullName = json.getString("fullName"); // 会员全称
		String moneyType = json.getString("moneyType");
//		String bisinType = json.getString("bisinType");
		double occurAmount = json.getDouble("occurAmount");
		String remark = json.getString("remark");
		
		Tran tran = tranMapper.selectByBankPro(bankProCode);
		LOGGER.info("*插入出入金记录*");
		AmtRec amtRec = new AmtRec();
		Long amtRecID = amtRecDao.generateId();
		amtRec.setAmtRecId(amtRecID);
		amtRec.setExchNo(PropertiesUtil.getProperty("exchNO"));                        //交易所编号	exch_no
		amtRec.setTradeAcct(exchangeFundAccount);                   //交易账号	trade_acct
		amtRec.setTranNo(tran.getTranNo());                       //银行业务编号	tran_no
		amtRec.setTranDate(initDate+"");            //业务日期	tran_date
		amtRec.setAcct(bankAccount);                             //银行账号	acct
		amtRec.setAcctName(accountName==null?"":accountName);                     //银行账户名	acct_name
		amtRec.setAmt(BigDecimal.valueOf(occurAmount)); 						//金额	amt
		amtRec.setCurrency(moneyType.equals("CNY") ? "RMB" : moneyType);                       //币种	currency
		amtRec.setRemark(remark);                               //备注	remark
		amtRec.setCardAcct(bankAccount);                //银行卡号
		amtRec.setCardName(accountName==null?"":accountName);                 //银行卡户名
		amtRec.setOutInFlag(0);                     //出入金标志	out_in_flag
		amtRec.setSenderType(2);               //发起方类型	sender_type
		amtRec.setDealStatus(9);                      //处理状态	deal_status
		amtRec.setBankSeq(serialNo);
		Long sendMsgId = sendMsgDao.generateId();
		amtRec.setSendMsgId(sendMsgId);                                          //往包ID	send_msg_id
		amtRec.setSysTime(new Date());  
		amtRecDao.getAmtRecMapper().insert(amtRec);
		
		try {
			sendMsgExchServiceImpl.sendMsg20201(amtRec);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String msg = null;
		try {
			msg = getExchRspServiceImpl.getRspMsg(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long t2 = System.currentTimeMillis();

		LOGGER.info("回给广清的消息----------------" + msg + "----时间差" + (t2 - t1));
		return msg;

	}

	/**
	 * @author yu.jian 清算中心向交易所推送文件通知
	 * @see com.ccps.exchgate.api.t2.service.server.ExchOwnT2Service#fileNotify(java.lang.String)
	 */
	public String fileNotify(String reqStr) {
		Long t1 = System.currentTimeMillis();
		try {
			getHSFileServiceImpl.doGetHSFileService(reqStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();

		json.put("errorNo", "0");
		json.put("errorInfo", null);
		
		Long t2 = System.currentTimeMillis();

		LOGGER.info("回给广清的消息----------------" + json.toString() + "----时间差" + (t2 - t1));
		return json.toString();
	}

	@Override
	public String verifyNotify(String reqStr) {
		Long t1 = System.currentTimeMillis();
		LOGGER.info("*清算中心向交易所推送审核结果*");
		LOGGER.info("*取请求信息*");
		
		JSONObject json = JSONObject.fromObject(reqStr);
		long initDate = json.getLong("initDate"); // 业务发生日期
		String exchangeId = json.getString("exchangeId"); // 交易所代码
		String auditSerialNo = json.getString("auditSerialNo"); // 审核流水号
		String tradeSerialNo = json.getString("tradeSerialNo"); // 交易所流水号
		String serialNo = json.getString("serialNo"); // 银行流水号
		String functionNo = json.getString("functionNo"); // 审核功能号
		String auditBusinessContent = json.getString("auditBusinessContent"); // 业务参数JSON串
		String auditStatus = json.getString("auditStatus");//审核状态
		String auditRemark = json.getString("auditRemark");//审核备注

		HashMap<String,String> map=new HashMap<String,String>();
		map.put("initDate", String.valueOf(initDate));
		map.put("exchangeId", exchangeId);
		map.put("auditSerialNo", auditSerialNo);
		map.put("tradeSerialNo", tradeSerialNo);
		map.put("serialNo", serialNo);
		map.put("functionNo", functionNo);
		map.put("auditBusinessContent", auditBusinessContent);
		map.put("auditStatus", auditStatus);
		map.put("auditRemark", auditRemark);
		
		try {
			sendMsgExchServiceImpl.sendMsg21301(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String msg = null;
		try {
			msg = getExchRspServiceImpl.getRspMsg(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long t2 = System.currentTimeMillis();

		LOGGER.info("回给广清的消息----------------" + msg + "----时间差" + (t2 - t1));
		return msg;
	}

	@Override
	public String secretkeyNotify(String reqStr) {
		Long t1 = System.currentTimeMillis();
		LOGGER.info("*清算中心通知交易所密钥交互结果*");
		LOGGER.info("*取请求信息*");
		
		JSONObject json = JSONObject.fromObject(reqStr);
		long initDate = json.getLong("initDate"); // 业务发生日期
		String exchangeId = json.getString("exchangeId"); // 交易所代码
		String bankProCode = json.getString("bankProCode"); // 银行产品代码
		String keyInfo = json.getString("keyInfo");
		
		LOGGER.info("*插入密钥信息记录*");
		SecretKey secretKey = new SecretKey();
		secretKey.setKeyId(secretKeyDao.generateId());
		secretKey.setInitDate(initDate);
		secretKey.setExchangeId(exchangeId);
		secretKey.setBankProCode(bankProCode);
		secretKey.setKeyInfo(keyInfo);
		secretKeyDao.getSecretKeyMapper().insert(secretKey);
		
		String error_no="0";
		String error_info="密钥信息接收成功";
		
		String msg = error_no+":"+error_info;
		/*try {
			msg = getExchRspServiceImpl.getRspMsg(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Long t2 = System.currentTimeMillis();

		LOGGER.info("回给广清的消息----------------" + msg + "----时间差" + (t2 - t1));
		return msg;
	}

}

