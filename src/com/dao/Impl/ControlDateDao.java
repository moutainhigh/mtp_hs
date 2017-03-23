package com.dao.Impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.dao.ControlDateMapper;
import com.model.ControlDate;
import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;

@Repository
public class ControlDateDao extends BaseDaoImpl<ControlDate> {

	@Resource
	private ControlDateMapper controlDateMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public ControlDateDao() {
		super(ControlDate.class);
	}

	public ControlDateMapper getControlDateMapper(){
		return controlDateMapper;
	};

}
