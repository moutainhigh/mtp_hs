package com.service;

import com.proto.CenterBank.Msg31001;

/**
 *  持仓明细文件
 * ClassName: MemberPosition.java
 * date: 2016年12月16日下午3:58:47
 * @author yu.jian
 * @version
 */
public interface MakeMemberPositionFile {
	public String doMakeMemberPositionFile(Msg31001 msg31001, long recId) throws Exception;
}