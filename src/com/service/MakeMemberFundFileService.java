package com.service;
import java.util.Map;

import com.proto.CenterBank.Msg31001;


/**
 *  资金余额文件
 * ClassName: MakeMemberFundFileService.java
 * date: 2016年12月16日下午2:53:21
 * @author yu.jian
 * @version
 */
public interface MakeMemberFundFileService {
	public String doMakeFundFile(Msg31001 msg31001, long recId) throws Exception;
	
	public Map<String, Object> readBF04(String filePath) throws Exception;
}
