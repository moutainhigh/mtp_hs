package com.service;

import com.proto.CenterBank.Msg20304;

/**
 *  银行签约
 * ClassName: BankSignService.java
 * date: 2016年12月14日上午10:41:39
 * @author yu.jian
 * @version
 */
public interface BankSignService {
	public void doBankSignReq(Msg20304 msg20304, long recMsgId) throws Exception;
}
