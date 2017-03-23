package com.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.constants.FinalConstants;
import com.core.exception.ServiceException;
import com.dao.ClientMapper;
import com.dao.SignRecMapper;
import com.dao.Impl.AccountDao;
import com.model.Account;
import com.model.Client;
import com.model.SignRec;
import com.proto.CenterBank.Msg20304;
import com.service.BankSignService;

import net.sf.json.JSONObject;

/**
 *  银行签约
 * ClassName: BankSignServiceImpl.java
 * date: 2016年12月14日上午10:42:56
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class BankSignServiceImpl implements BankSignService {
	private static final Logger LOGGER = Logger.getLogger(BankSignServiceImpl.class);
	/**
	 *  
	 * ClassName: BankSignServiceImpl.java
	 * date: 2016年12月14日上午10:42:56
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private SignRecMapper signRecMapper;
	@Autowired
	private CacheServiceImpl cacheServiceImpl;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ClientMapper clientMapper;
	@Override
	public void doBankSignReq(Msg20304 msg20304, long recMsgId) throws Exception {
		try{
			LOGGER.info("*收到银行签约中心应答*"); 
//			SignRec getSignRec = new SignRec();
//			getSignRec.setBankSeq(msg20304.getBankSeq());
			SignRec signRec = signRecMapper.selectByBankSeq(msg20304.getBankSeq());
			
			if(!(msg20304.getRetCode().equals(FinalConstants.RetCode.SUCCESS)))
				throw new ServiceException(msg20304.getRetDesc());
			
			if(signRec==null)
				throw new ServiceException("*交易所返回银行流水号错误*");
			
			Client client = clientMapper.selectByTradeAcct(signRec.getTradeAcct());
			
			signRec.setRecvMsgId(recMsgId);
			signRec.setCenterSeq(msg20304.getCenterSeq());
			signRec.setDealStatus(Integer.parseInt(msg20304.getRetCode()));
			signRec.setDealDesc(msg20304.getRetDesc());
			signRecMapper.updateByPrimaryKeySelective(signRec);
			
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20304.getCenterSeq());
			if(Integer.parseInt(msg20304.getRetCode())==0){
				json.put("errorNo", 0);
				
				LOGGER.info("*插入账户表*");
				Account account = new Account();
				account.setAccountId(accountDao.generateId());
				account.setTranNo(signRec.getTranNo());
				account.setClientId(client.getClientId());
				account.setExchNo(signRec.getExchNo());
				account.setTradeAcct(signRec.getTradeAcct());
				account.setAcct(signRec.getAcct());
				account.setAcctName(signRec.getAcctName());
				account.setCurrency(signRec.getCurrency());
				account.setAcctType(signRec.getAcctType().shortValue());
				account.setCertType(signRec.getCertType());
				account.setCertCode(signRec.getCertCode());
				account.setClientName(signRec.getClientName());
				account.setMobile(signRec.getMobile());
				account.setEmail(signRec.getEmail());
				account.setExtendInfo(signRec.getExtendInfo());
				account.setSignTime(new Date());
				account.setTranSender(2);
				account.setSignStatus(1);
//				account.setCardStatus();
				accountDao.getAccountMapper().insert(account);
				
			}
			else
				json.put("errorNo", Integer.parseInt(msg20304.getRetCode()));
			json.put("errorInfo", msg20304.getRetDesc());
			
			cacheServiceImpl.putCenter_Msg(msg20304.getBankSeq(), json.toString());
		}catch(ServiceException e){
			System.out.println(e.getMessage());
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20304.getCenterSeq());
			json.put("errorNo", 1);
			json.put("errorInfo", e.getMessage());
			
			cacheServiceImpl.putCenter_Msg(msg20304.getBankSeq(), json.toString());
		
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}

}
