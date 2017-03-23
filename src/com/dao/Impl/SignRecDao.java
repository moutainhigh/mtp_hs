package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.SignRecMapper;
import com.model.SignRec;

@Repository
public class SignRecDao extends BaseDaoImpl<SignRec> {

	@Resource
	private SignRecMapper signRecMapper;
	@Resource
	IdGenerator idGenerator;
	
	public SignRecDao() {
		super(SignRec.class);
	}

	public SignRecMapper getCorpMapper(){
		return signRecMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("SIGN_REC_ID");
	}
}
