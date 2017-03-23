package com.dao.Impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.dao.AccountMapper;
import com.model.Account;
import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;

@Repository
public class AccountDao extends BaseDaoImpl<Account> {

	@Resource
	private AccountMapper accountMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public AccountDao() {
		super(Account.class);
	}

	public AccountMapper getAccountMapper(){
		return accountMapper;
	};

	public long generateId() {
		return idGenerator.generateId("ACCOUNT_ID");
	}
}
