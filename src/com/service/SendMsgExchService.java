package com.service;

import java.util.HashMap;

import com.model.AmtRec;
import com.model.SignRec;

/**
 *  
 * ClassName: SendMsgExchService.java
 * date: 2016年12月14日下午2:32:23
 * @author yu.jian
 * @version
 */
public interface SendMsgExchService {
	//签约
	public void sendMsg20301(SignRec signRec) throws Exception;
	
	//解约
	public void sendMsg20401(SignRec signRec) throws Exception;
	
	//入金
	public void sendMsg20101(AmtRec amtRec) throws Exception;
	
	//入金
	public void sendMsg20201(AmtRec amtRec) throws Exception;
	
	//清算中心向交易所推送审核结果
	public void sendMsg21301(HashMap map) throws Exception;
}
