package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.RecvMsgMapper;
import com.model.RecvMsg;

@Repository
public class RecvMsgDao extends BaseDaoImpl<RecvMsg> {

	@Resource
	private RecvMsgMapper recvMsgMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public RecvMsgDao() {
		super(RecvMsg.class);
	}

	public RecvMsgMapper getRecvMsgMapper(){
		return recvMsgMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("RECV_MSG_ID");
	}
}
