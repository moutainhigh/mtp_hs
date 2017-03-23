package com.service;
/**
 *  获取存在cache中的交易所应答
 * ClassName: GetExchRspService.java
 * date: 2016年12月14日下午5:00:46
 * @author yu.jian
 * @version
 */
public interface GetExchRspService {
	public String getRspMsg(String serialNo) throws Exception;
}
