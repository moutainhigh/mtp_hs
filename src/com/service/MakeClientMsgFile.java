package com.service;

import com.proto.CenterBank.Msg31001;

/**
 *  客户信息文件
 * ClassName: MakeClientMsgFile.java
 * date: 2016年12月17日上午10:59:32
 * @author yu.jian
 * @version
 */
public interface MakeClientMsgFile {
	public String doMakeClientMsgFile(Msg31001 msg31001, long recId) throws Exception;
}