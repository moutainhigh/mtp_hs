package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.RegisterRecMapper;
import com.model.RegisterRec;

@Repository
public class RegisterRecDao extends BaseDaoImpl<RegisterRec> {

	@Resource
	private RegisterRecMapper registerRecMapper;
	@Resource
	IdGenerator idGenerator;
	
	public RegisterRecDao() {
		super(RegisterRec.class);
	}

	public RegisterRecMapper getRegisterRecMapper(){
		return registerRecMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("REGISTER_REC_ID");
	}
}
