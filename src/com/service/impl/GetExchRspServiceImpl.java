package com.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hundsun.jres.common.util.StringUtils;
import com.service.GetExchRspService;
/**
 *  
 * ClassName: GetExchRspServiceImpl.java
 * date: 2016年12月14日下午5:01:56
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class GetExchRspServiceImpl implements GetExchRspService{

	/**
	 *  
	 * ClassName: GetExchRspServiceImpl.java
	 * date: 2016年12月14日下午5:02:54
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CacheServiceImpl cacheServiceImpl;
	@Override
	public String getRspMsg(String serialNo) throws Exception {
		String msg = "";
		int i=100;
		while(StringUtils.isEmpty(msg)&&i>0){
			try {
				Thread.sleep(100);
				i--;
				msg = cacheServiceImpl.getCenter_Msg(serialNo);
			} catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
            	if(i==0){
            		System.out.println("*交易所回应超时*");
            	}
            }
		}
		if(StringUtils.isEmpty(msg)){
			throw new Exception("*未获取到交易所反馈信息*");
		}
		return msg;
	}

}
