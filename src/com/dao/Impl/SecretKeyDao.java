package com.dao.Impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.dao.SecretKeyMapper;
import com.model.SecretKey;
import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;

@Repository
public class SecretKeyDao extends BaseDaoImpl<SecretKey> {

	@Resource
	private SecretKeyMapper secretKeyMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public SecretKeyDao() {
		super(SecretKey.class);
	}

	public SecretKeyMapper getSecretKeyMapper(){
		return secretKeyMapper;
	};

	public long generateId() {
		return idGenerator.generateId("KEY_ID");
	}
}
