package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.model.Bank;
import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.*;
@Repository
public class BankDao extends BaseDaoImpl<Bank> {

	
	@Resource
	private BankMapper bankMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public BankDao() {
		super(Bank.class);
	}

	public BankMapper getBankMapper(){
		return bankMapper;
	};
	
	@Deprecated
	public long generateId() {
		return idGenerator.generateId("GENERAL_ID");
	}
}
