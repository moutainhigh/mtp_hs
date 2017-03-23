package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.MemberFeeMapper;
import com.model.MemberFee;

/**
 *  
 * ClassName: MemberFeeDao.java
 * date: 2016年12月26日下午5:06:38
 * @author yu.jian
 * @version
 */
public class MemberFeeDao extends BaseDaoImpl<MemberFee>{
	@Resource
	private MemberFeeMapper memberFeeMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public MemberFeeDao() {
		super(MemberFee.class);
	}

	public MemberFeeMapper getMemberFeeMapper(){
		return memberFeeMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Member_Fee_ID");
	}
}
