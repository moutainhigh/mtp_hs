package com.service.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.exception.ServiceException;
import com.dao.AmtRecMapper;
import com.model.AmtRec;
import com.muchinfo.common.util.PropertiesUtil;
import com.proto.CenterBank.Msg20104;
import com.service.BankAmtInService;

import net.sf.json.JSONObject;

/**
 *  银行入金交易所返回
 * ClassName: BankAmtInServiceImpl.java
 * date: 2016年12月14日上午11:16:27
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class BankAmtInServiceImpl implements BankAmtInService {
	private static final Logger LOGGER = Logger.getLogger(BankAmtInServiceImpl.class);
	/**
	 *  
	 * ClassName: BankAmtInServiceImpl.java
	 * date: 2016年12月14日上午11:16:27
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private AmtRecMapper amtRecMapper;
	@Autowired
	private CacheServiceImpl cacheServiceImpl;
	@Override
	public void doBankAmtIn(Msg20104 msg20104, long recMsgId) throws Exception {
		try{
			LOGGER.info("*银行入金交易所返回*");
			AmtRec getAmtRec = new AmtRec();
			getAmtRec.setBankSeq(msg20104.getBankSeq());
			List<AmtRec> amtRecList = amtRecMapper.selectByBean(getAmtRec);
			
			if(amtRecList.size()==0)
				throw new ServiceException("*交易所返回银行流水号错误*");
			
			AmtRec amtRec = amtRecList.get(0);
			amtRec.setCenterSeq(msg20104.getCenterSeq());
			amtRec.setDealStatus(Integer.parseInt(msg20104.getRetCode()));
			amtRec.setDealDesc(msg20104.getRetDesc());
			amtRecMapper.updateByPrimaryKeySelective(amtRec);
			
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20104.getCenterSeq());
			if(Integer.parseInt(msg20104.getRetCode())==0)
				json.put("errorNo", 0);
			else
				json.put("errorNo", Integer.parseInt(msg20104.getRetCode()));
			json.put("errorInfo", msg20104.getRetDesc());
			
			cacheServiceImpl.putCenter_Msg(msg20104.getBankSeq(), json.toString());
		}catch(ServiceException e){
			LOGGER.error(e);
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20104.getCenterSeq());
			json.put("errorNo", 1);
			json.put("errorInfo", e.getMessage());
			cacheServiceImpl.putCenter_Msg(msg20104.getBankSeq(), json.toString());
		}catch(Exception e){
			throw e;
		}
	}

}
