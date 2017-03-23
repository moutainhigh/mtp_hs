package com.dao.Impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.core.dao.impl.BaseDaoImpl;
import com.core.service.IdGenerator;
import com.dao.ProductMapper;
import com.model.Product;

@Repository
public class ProductDao extends BaseDaoImpl<Product> {

	@Resource
	private ProductMapper productMapper;
	@Resource
	private IdGenerator idGenerator;
	
	public ProductDao() {
		super(Product.class);
	}

	public ProductMapper getProductMapper(){
		return productMapper;
	};
	
	@Deprecated
	public long generateId() {
		return idGenerator.generateId("GENERAL_ID");
	}
}
