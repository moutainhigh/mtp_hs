package com.service.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.exception.ServiceException;
import com.dao.AmtRecMapper;
import com.model.AmtRec;
import com.proto.CenterBank.Msg20204;
import com.service.BankAmtOutService;

import net.sf.json.JSONObject;

/**
 *  银行出金交易所返回
 * ClassName: BankAmtOutServiceImpl.java
 * date: 2016年12月14日上午11:38:32
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class BankAmtOutServiceImpl implements BankAmtOutService {
	private static final Logger LOGGER = Logger.getLogger(BankAmtOutServiceImpl.class);
	/**
	 *  
	 * ClassName: BankAmtOutServiceImpl.java
	 * date: 2016年12月14日上午11:38:32
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private AmtRecMapper amtRecMapper;
	@Autowired
	private CacheServiceImpl cacheServiceImpl;
	@Override
	public void doAmtOutReq(Msg20204 msg20204, long recMsgId) throws Exception {
		try{
			LOGGER.info("*银行出金交易所返回*");
			AmtRec getAmtRec = new AmtRec();
			getAmtRec.setBankSeq(msg20204.getBankSeq());
			List<AmtRec> amtRecList = amtRecMapper.selectByBean(getAmtRec);
			
			if(amtRecList.size()==0)
				throw new ServiceException("*交易所返回银行流水号错误*");
			
			AmtRec amtRec = amtRecList.get(0);
			amtRec.setCenterSeq(msg20204.getCenterSeq());
			amtRec.setDealStatus(Integer.parseInt(msg20204.getRetCode()));
			amtRec.setDealDesc(msg20204.getRetDesc());
			amtRecMapper.updateByPrimaryKeySelective(amtRec);
			
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20204.getCenterSeq());
			if(Integer.parseInt(msg20204.getRetCode())==0)
				json.put("errorNo", 0);
			else
				json.put("errorNo", Integer.parseInt(msg20204.getRetCode()));
			json.put("errorInfo", msg20204.getRetDesc());
			
			cacheServiceImpl.putCenter_Msg(msg20204.getBankSeq(), json.toString());
		}catch(ServiceException e){
			JSONObject json = new JSONObject();
			json.put("tradeSerialNo", msg20204.getCenterSeq());
			json.put("errorNo", 1);
			json.put("errorInfo", e.getMessage());
			
			cacheServiceImpl.putCenter_Msg(msg20204.getBankSeq(), json.toString());
		}catch(Exception e){
			throw e;
		}
	}

}
