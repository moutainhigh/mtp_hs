package com.dao.Impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.ClearDataMapper;
import com.model.ClearData;

@Repository
public class ClearDataDao extends BaseDaoImpl<ClearData> {

	@Resource
	private ClearDataMapper clearDataMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public ClearDataDao() {
		super(ClearData.class);
	}

	public ClearDataMapper getClearDataMapper(){
		return clearDataMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("CLEAR_DATA_ID");
	}
}
