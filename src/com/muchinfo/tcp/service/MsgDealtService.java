package com.muchinfo.tcp.service;


/**
 * 
 * @ClassName: MsgDealtService
 * @Description: 处理t2服务消息
 * @author zhou.yao
 * @date 2016年7月6日 上午10:38:14
 *
 */
public interface MsgDealtService {
	/**
	 * 发送至清算所
	 * sendMsgReq:(处理交易所的请求消息). <br/>
	 * @author zhou.yao
	 * @param functionId 方法id
	 * @param jsonReq 消息内容
	 * @return
	 */
	public String sendMsgReq(String functionId ,String jsonReq) throws Exception;
	
	
	/**
	 *  发送交易所的返回信息
	 * sendMsgResp:(处理清算所发起的业务). <br/>
	 * @author zhou.yao
	 * @param functionId 方法id
	 * @param jsonReq 消息内容
	 * @return
	 */
	//public String sendMsgResp(String jsonReq) throws Exception;
	
	
	
	
	
	
	
	
}
