package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.ClearPriceMapper;
import com.model.ClearPrice;

/**
 *  
 * ClassName: ClearPriceDao.java
 * date: 2016年12月26日下午5:05:08
 * @author yu.jian
 * @version
 */
public class ClearPriceDao extends BaseDaoImpl<ClearPrice>{
	@Resource
	private ClearPriceMapper clearPriceMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public ClearPriceDao() {
		super(ClearPrice.class);
	}

	public ClearPriceMapper getClearPriceMapper(){
		return clearPriceMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Clear_Price_ID");
	}
}
