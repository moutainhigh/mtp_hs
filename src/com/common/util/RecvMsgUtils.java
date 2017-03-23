//package com.muchinfo.mtp.common.util;
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.muchinfo.mtp.bankservice.model.RecvMsg;
//import com.muchinfo.mtp.bankservice.service.RecvMsgService;
///**
// * ClassName: RecvMsgUtils <br/>
// * Function: 来包表工具类<br/>
// * date: 2016年6月1日 下午6:47:58 <br/>
// * @author zhou.yao
// * @version
// */
//public class RecvMsgUtils {
//	private static final Logger LOGGER=Logger.getLogger(RecvMsgUtils.class);
//	@Autowired
//	private    static RecvMsgService recvMsgService;
//	public static void insert(RecvMsg recvMsg){
//		try {
//			recvMsgService.insert(recvMsg);
//		} catch (Exception e) {
//			LOGGER.error("RecvMsgUtils.insert:"+e.getMessage(), e);
//		}
//	}
//	
//	
//}
