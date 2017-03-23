package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.SendMsgMapper;
import com.model.SendMsg;
@Repository
public class SendMsgDao extends BaseDaoImpl<SendMsg> {

	@Resource
	private SendMsgMapper sendMsgMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public SendMsgDao() {
		super(SendMsg.class);
	}

	public SendMsgMapper getSendMsgMapper(){
		return sendMsgMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("SEND_MSG_ID");
	}
}
