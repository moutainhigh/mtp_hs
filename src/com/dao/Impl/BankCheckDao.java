package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.BankCheckMapper;
import com.model.BankCheck;

/**
 *  
 * ClassName: BankCheckDao.java
 * date: 2016年12月26日下午5:04:30
 * @author yu.jian
 * @version
 */
public class BankCheckDao extends BaseDaoImpl<BankCheck>{
	
	@Resource
	private BankCheckMapper bankCheckMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public BankCheckDao() {
		super(BankCheck.class);
	}

	public BankCheckMapper getBankCheckMapper(){
		return bankCheckMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Bank_Check_ID");
	}
}
