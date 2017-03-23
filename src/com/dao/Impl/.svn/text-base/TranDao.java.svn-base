package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.TranMapper;
import com.model.Tran;


@Repository
public class TranDao extends BaseDaoImpl<Tran> {

	@Resource
	private TranMapper tranMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public TranDao() {
		super(Tran.class);
	}

	public TranMapper getTranMapper(){
		return tranMapper;
	};
	
	@Deprecated
	public long generateId() {
		return idGenerator.generateId("GENERAL_ID");
	}
}
