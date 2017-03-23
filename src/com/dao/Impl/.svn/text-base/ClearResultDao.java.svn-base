package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.ClearResultMapper;
import com.model.ClearResult;

/**
 *  
 * ClassName: ClearResultDao.java
 * date: 2016年12月26日下午5:05:23
 * @author yu.jian
 * @version
 */
public class ClearResultDao extends BaseDaoImpl<ClearResult>{
	@Resource
	private ClearResultMapper clearResultMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public ClearResultDao() {
		super(ClearResult.class);
	}

	public ClearResultMapper getClearResultMapper(){
		return clearResultMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Clear_Result_ID");
	}
}
