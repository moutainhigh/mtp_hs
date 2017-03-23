package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.ClientMapper;
import com.model.Client;

@Repository
public class ClientDao extends BaseDaoImpl<Client> {

	@Resource
	private ClientMapper clientMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public ClientDao() {
		super(Client.class);
	}

	public ClientMapper getClientMapper(){
		return clientMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("CLIENT_ID");
	}
}
