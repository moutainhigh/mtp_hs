package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.DealInfoMapper;
import com.model.DealInfo;

/**
 *  
 * ClassName: DealInfoDao.java
 * date: 2016年12月26日下午5:05:45
 * @author yu.jian
 * @version
 */
public class DealInfoDao extends BaseDaoImpl<DealInfo>{
	@Resource
	private DealInfoMapper dealInfoMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public DealInfoDao() {
		super(DealInfo.class);
	}

	public DealInfoMapper getDealInfoMapper(){
		return dealInfoMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Deal_Info_ID");
	}
}
