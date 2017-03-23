package com.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.exception.ServiceException;
import com.dao.SignRecMapper;
import com.model.SignRec;
import com.proto.CenterBank.Msg20404;
import com.service.BankSignOutService;

import net.sf.json.JSONObject;

/**
 *  银行解约交易所返回
 * ClassName: BankSignOutServiceImpl.java
 * date: 2016年12月14日上午11:12:08
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class BankSignOutServiceImpl implements BankSignOutService {
	private static final Logger LOGGER = Logger.getLogger(BankSignOutServiceImpl.class);
	/**
	 *  
	 * ClassName: BankSignOutServiceImpl.java
	 * date: 2016年12月14日上午11:12:08
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private SignRecMapper signRecMapper;
	@Autowired
	private CacheServiceImpl cacheServiceImpl;
	@Override
	public void doSignOutReq(Msg20404 msg20404, long recMsgId) throws Exception {
		try{
			LOGGER.info("*收到银行解约中心应答*");
//			SignRec getSignRec = new SignRec();
//			getSignRec.setBankSeq(msg20404.getBankSeq());
			SignRec signRec = signRecMapper.selectByBankSeq(msg20404.getBankSeq());
			
			if(signRec==null)
				throw new ServiceException("*返回银行流水号错误*");
			
			signRec.setCenterSeq(msg20404.getCenterSeq());
			signRec.setDealStatus(Integer.parseInt(msg20404.getRetCode()));
			signRec.setDealDesc(msg20404.getRetDesc());
			signRecMapper.updateByPrimaryKeySelective(signRec);
			
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20404.getCenterSeq());
			if(Integer.parseInt(msg20404.getRetCode())==0)
				json.put("errorNo", 0);
			else
				json.put("errorNo", Integer.parseInt(msg20404.getRetCode()));
			json.put("errorInfo", msg20404.getRetDesc());
			
			cacheServiceImpl.putCenter_Msg(msg20404.getBankSeq(), json.toString());
		}catch(ServiceException e){
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20404.getCenterSeq());
			json.put("errorNo", 1);
			json.put("errorInfo", e.getMessage());
			
			cacheServiceImpl.putCenter_Msg(msg20404.getBankSeq(), json.toString());
		}catch(Exception e){
			throw e;
		}
	}

}
