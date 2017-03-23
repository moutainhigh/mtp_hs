package com.service;

import com.proto.CenterBank.Msg20204;

/**
 *  
 * ClassName: BankAmtOutService.java
 * date: 2016年12月14日上午11:37:18
 * @author yu.jian
 * @version
 */
public interface BankAmtOutService {
	public void doAmtOutReq(Msg20204 msg20204, long recMsgId) throws Exception;
}
