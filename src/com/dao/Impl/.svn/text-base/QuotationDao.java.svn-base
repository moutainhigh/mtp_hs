package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.QuotationMapper;
import com.model.Quotation;

@Repository
public class QuotationDao extends BaseDaoImpl<Quotation> {

	@Resource
	private QuotationMapper quotationMapper;
	@Resource
	IdGenerator idGenerator;
	
	public QuotationDao() {
		super(Quotation.class);
	}

	public QuotationMapper getCorpMapper(){
		return quotationMapper;
	};
	
	public long generateId() {
		return idGenerator.generateId("QUOTATION_ID");
	}
}
