package com.service;

import com.proto.CenterBank.Msg31001;

/**
 *  
 * ClassName: ExchFileNoService.java
 * date: 2016年12月15日上午9:21:24
 * @author yu.jian
 * @version
 */
public interface ExchFileNoService {
	public void doExchFileNoReq(Msg31001 msg31001, long recMsgId) throws Exception;
}
