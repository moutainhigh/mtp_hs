package com.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.exception.ServiceException;
import com.model.AmtRec;
import com.proto.CenterBank.Msg21304;
import com.service.BankVerifyNotifyService;

import net.sf.json.JSONObject;

/**
 *  清算中心向交易所推送审核结果返回
 * ClassName: BankVerifyNotifyServiceImpl.java
 * @version
 */
@Service
@Transactional
public class BankVerifyNotifyServiceImpl implements BankVerifyNotifyService {
	private static final Logger LOGGER = Logger.getLogger(BankVerifyNotifyServiceImpl.class);
	/**
	 *  
	 * ClassName: BankVerifyNotifyServiceImpl.java
	 * date: 2016年12月14日上午11:16:27
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CacheServiceImpl cacheServiceImpl;
	@Override
	public void doBankVerifyNotifyService(Msg21304 msg21304, long recMsgId) throws Exception {
		try{
			LOGGER.info("*清算中心向交易所推送审核结果返回*");
			
			JSONObject json = new JSONObject();
			if(Integer.parseInt(msg21304.getRetCode())==0)
				json.put("errorNo", 0);
			else
				json.put("errorNo", Integer.parseInt(msg21304.getRetCode()));
			json.put("errorInfo", msg21304.getRetDesc());
			
			cacheServiceImpl.putCenter_Msg(msg21304.getBankSeq(), json.toString());
		}catch(ServiceException e){
			LOGGER.error(e);
			JSONObject json = new JSONObject();
			json.put("errorNo", 1);
			json.put("errorInfo", e.getMessage());
			cacheServiceImpl.putCenter_Msg(msg21304.getBankSeq(), json.toString());
		}catch(Exception e){
			throw e;
		}
	}

}
