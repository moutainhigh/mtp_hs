package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.ExchMapper;
import com.model.Exch;

@Repository
public class ExchDao extends BaseDaoImpl<Exch> {

	@Resource
	private ExchMapper exchMapper;
	@Resource
	IdGenerator idGenerator;
	
	public ExchDao() {
		super(Exch.class);
	}

	public ExchMapper getExchMapper(){
		return exchMapper;
	};
	
	@Deprecated
	public long generateId() {
		return idGenerator.generateId("EXCH_ID");
	}
}
