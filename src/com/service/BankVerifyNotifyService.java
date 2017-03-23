package com.service;

import com.proto.CenterBank.Msg21304;

/**
 *  审核结果通知返回报文
 * ClassName: BankVerifyNotifyService.java
 * @version
 */
public interface BankVerifyNotifyService {
	public void doBankVerifyNotifyService(Msg21304 msg21304, long recMsgId) throws Exception;
}
