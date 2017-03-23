package com.service.impl;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import com.common.constants.FinalConstants;
import com.common.util.TASProtoHelper;
import com.core.exception.ServiceException;
import com.core.service.impl.BaseServiceImpl;
import com.core.util.PropertiesUtil;
import com.model.SendMsg;
import com.service.SendMsgService;

/**
 * @ClassName: SendToSettleServiceImpl
 * @Description: TODO
 * @author: zhang.wei1
 * @date: 2016年8月27日 下午6:42:00
 */
@Service
public class SendMsgServiceImpl extends BaseServiceImpl<SendMsg> implements SendMsgService {

	private static final Logger LOGGER = Logger.getLogger(SendMsgServiceImpl.class);

	@Resource
	protected RabbitTemplate exchRabbit;
	@Resource
	protected RabbitTemplate centerRabbit;
	@Resource
	protected MessageConverter simpleMessageConverter;

	@Override
	public void send(SendMsg sendMsg, byte[] body) throws Exception {
		Integer recverType = sendMsg.getRecverType();

		//String routingKey = PropertiesUtil.getProperty(FinalConstants.SenderType.ROUTINGKEY.get(recverType));
		String routingKey = sendMsg.getRecver();

		if (routingKey.length() == 0) {
			LOGGER.error("==>RoutingKey没配置,recverType:" + recverType);
			throw new ServiceException("RoutingKey not found");
		}

		if (FinalConstants.SenderType.CENTER == recverType) {
			LOGGER.info(String.format("<==>{===============-发送消息到清算系统,功能码【%s】,routingkey【%s】", sendMsg.getMsgCode(), routingKey) +sendMsg);
			this.sendMsg2BS(centerRabbit, PropertiesUtil.getProperty(FinalConstants.Topic.CENTER_REQ), body);
		} else {
			LOGGER.info(String.format("<==>{===============-发送消息到交易所系统,功能码【%s】,routingkey【%s】", sendMsg.getMsgCode(), routingKey) + sendMsg);
			this.sendMsg2BS(exchRabbit, routingKey, body);
//			routingKey = sendMsg.getRecver()==null?"":sendMsg.getRecver() + routingKey;
//			LOGGER.info(String.format("<==>{===============-发送消息到交易所系统,功能码【%s】,routingkey【%s】", sendMsg.getMsgCode(), routingKey) + sendMsg);
//			this.sendMsg2BS(exchRabbit, routingKey, body);
		}
	}

	@Override
	public void sendMsg(int msgCode, SendMsg sendMsg, byte[] body) throws Exception {
		this.sendMsg(msgCode, sendMsg, body, 0);
	}

	@Override
	public void sendMsg(int msgCode, SendMsg sendMsg, byte[] body, int sessionId) throws Exception {
		byte[] bytes = TASProtoHelper.getNTAS(body, msgCode, sessionId);
		send(sendMsg, bytes);
	}

	/**
	 * @Title: sendMsg2BS
	 * @Description: TODO 发送消息
	 * @param routingKey
	 * @param body
	 * @throws AmqpException
	 * @return: void
	 */
	public void sendMsg2BS(RabbitTemplate rabbit, String routingKey, byte[] body) throws AmqpException {
		MessageProperties msgProperties = new MessageProperties();
		msgProperties.setReplyTo("replyTo");
		msgProperties.setHeader("messageType", "protoBuf");
		rabbit.send(routingKey, new Message(body, msgProperties));
	}

}
