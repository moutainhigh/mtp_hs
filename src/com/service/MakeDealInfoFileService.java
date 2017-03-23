package com.service;

import com.proto.CenterBank.Msg31001;

/**
 *  成交单文件
 * ClassName: MakeDealInfoFileService.java
 * date: 2016年12月16日下午6:40:10
 * @author yu.jian
 * @version
 */
public interface MakeDealInfoFileService {
	public String doMakeDealInfoFile(Msg31001 msg31001, long recId) throws Exception;
}
