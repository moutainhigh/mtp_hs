package com.listener;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.common.constants.ExchFunCodeType;
import com.common.util.TASProtoHelper;
import com.service.FunCodeService;

/**
 * @ClassName: CenterListener
 * @Description: 监听队列：
 * @author: zhang.wei1
 * @date: 2016年8月25日 下午2:23:48
 */
public class CenterRspListener implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(CenterRspListener.class);

	@Resource
	private FunCodeService centerFunCodeServiceImpl;

	@Override
	public void onMessage(Message message) {
		try {
			LOGGER.info("=========>收到消息" + message.toString());
			deliveryFunCode(message);
		} catch (Exception e) {
			LOGGER.error("----银行服务监听清算系统队列异常:", e);
		}
	}

	private void deliveryFunCode(Message message) {
		int funCode = -1;
		try {
			funCode = TASProtoHelper.getFunCodeForNTAS(message.getBody());
			if (ExchFunCodeType.Heat.getType() == funCode) {
				return;
			}
			byte[] body = TASProtoHelper.getProtoForNTAS(message.getBody());

			centerFunCodeServiceImpl.execute(funCode, body);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
}
