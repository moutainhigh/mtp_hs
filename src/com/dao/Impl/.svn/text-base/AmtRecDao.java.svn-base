package com.dao.Impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.model.AmtRec;
import com.dao.AmtRecMapper;

@Repository
public class AmtRecDao extends BaseDaoImpl<AmtRec> {

	@Resource
	private AmtRecMapper amtRecMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public AmtRecDao() {
		super(AmtRec.class);
	}

	public AmtRecMapper getAmtRecMapper(){
		return amtRecMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Amt_Rec_ID");
	}
}
