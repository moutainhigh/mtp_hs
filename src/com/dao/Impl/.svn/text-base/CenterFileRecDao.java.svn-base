package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.CenterFileRecMapper;
import com.model.CenterFileRec;

@Repository
public class CenterFileRecDao extends BaseDaoImpl<CenterFileRec> {

	@Resource
	private CenterFileRecMapper centerFileRecMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public CenterFileRecDao() {
		super(CenterFileRec.class);
	}

	public CenterFileRecMapper getCenterFileRecMapper(){
		return centerFileRecMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("CENTER_FILE_REC_ID");
	}
}
