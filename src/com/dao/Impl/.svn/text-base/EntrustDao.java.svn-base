package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.EntrustMapper;
import com.model.Entrust;

/**
 *  
 * ClassName: EntrustDao.java
 * date: 2016年12月26日下午5:06:17
 * @author yu.jian
 * @version
 */
public class EntrustDao extends BaseDaoImpl<Entrust>{
	@Resource
	private EntrustMapper entrustMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public EntrustDao() {
		super(Entrust.class);
	}

	public EntrustMapper getEntrustMapper(){
		return entrustMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Entrust_ID");
	}
}
