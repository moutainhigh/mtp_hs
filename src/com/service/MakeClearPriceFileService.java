package com.service;

import com.proto.CenterBank.Msg31001;

/**
 *  结算价文件
 * ClassName: MakeClearPriceFileService.java
 * date: 2016年12月16日下午6:08:31
 * @author yu.jian
 * @version
 */
public interface MakeClearPriceFileService {
	public String doMakeClearPriceFile(Msg31001 msg31001, long recId) throws Exception;
}
