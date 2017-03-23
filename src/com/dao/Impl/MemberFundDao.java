package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.MemberFundMapper;
import com.model.MemberFund;

/**
 *  
 * ClassName: MemberFundDao.java
 * date: 2016年12月26日下午5:07:27
 * @author yu.jian
 * @version
 */
public class MemberFundDao extends BaseDaoImpl<MemberFund>{
	@Resource
	private MemberFundMapper memberFundMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public MemberFundDao() {
		super(MemberFund.class);
	}

	public MemberFundMapper getMemberFundMapper(){
		return memberFundMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Member_Fund_ID");
	}
}
