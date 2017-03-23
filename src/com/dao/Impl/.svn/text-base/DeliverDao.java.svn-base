package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.DeliverMapper;
import com.model.Deliver;

/**
 *  
 * ClassName: DeliverDao.java
 * date: 2016年12月26日下午5:05:59
 * @author yu.jian
 * @version
 */
public class DeliverDao extends BaseDaoImpl<Deliver>{
	@Resource
	private DeliverMapper deliverMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public DeliverDao() {
		super(Deliver.class);
	}

	public DeliverMapper getDeliverMapper(){
		return deliverMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Deliver_ID");
	}
}
