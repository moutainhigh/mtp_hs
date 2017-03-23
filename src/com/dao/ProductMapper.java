package com.dao;

import com.model.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product Product);

    int insertSelective(Product Product);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(Product Product);

    int updateByPrimaryKey(Product Product);
    
    Product selectByCenterSeq(String centerSeq);
}