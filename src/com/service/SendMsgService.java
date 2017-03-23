package com.service;

import com.model.SendMsg;

/** 
 * @ClassName: SendMsgService 
 * @Description: 发送消息
 * @author: zhang.wei1
 * @date: 2016年8月25日 下午6:20:27  
 */
public interface SendMsgService {

	/** 
	 * @Title: send 
	 * @Description: TODO
	 * 			消息包括报头
	 * @param sendMsg
	 * @param body
	 * @throws Exception
	 * @return: void
	 */
	public void send(SendMsg sendMsg, byte[] body) throws Exception;
	/** 
	 * @Title: sendMsg 
	 * @Description: TODO
	 * 			消息不包括报头	
	 * 			报文增加报头并发送
	 * @param msgCode
	 * @param sendMsg
	 * @param body
	 * @throws Exception
	 * @return: void
	 */
	public void sendMsg(int msgCode, SendMsg sendMsg, byte[] body) throws Exception;
	public void sendMsg(int msgCode, SendMsg sendMsg, byte[] body, int sessionId) throws Exception;
}
