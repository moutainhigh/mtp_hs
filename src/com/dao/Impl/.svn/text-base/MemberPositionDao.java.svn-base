package com.dao.Impl;

import javax.annotation.Resource;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.MemberPositionMapper;
import com.model.MemberPosition;

/**
 *  
 * ClassName: MemberPositionDao.java
 * date: 2016年12月26日下午5:07:49
 * @author yu.jian
 * @version
 */
public class MemberPositionDao extends BaseDaoImpl<MemberPosition>{
	@Resource
	private MemberPositionMapper memberPositionMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public MemberPositionDao() {
		super(MemberPosition.class);
	}

	public MemberPositionMapper getMemberPositionMapper(){
		return memberPositionMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("Member_Position_ID");
	}
}
