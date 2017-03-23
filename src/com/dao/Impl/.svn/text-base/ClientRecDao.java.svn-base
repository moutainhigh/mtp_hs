package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.ClientRecMapper;
import com.model.ClientRec;

@Repository
public class ClientRecDao extends BaseDaoImpl<ClientRec> {

	@Resource
	private ClientRecMapper clientRecMapper;
	@Resource
	private IdGenerator idGenerator;

	public ClientRecDao() {
		super(ClientRec.class);
	}

	public ClientRecMapper getClientRecMapper() {
		return clientRecMapper;
	};

	public long generateId() {
		return idGenerator.generateId("CLIENT_REC_ID");
	}
}
