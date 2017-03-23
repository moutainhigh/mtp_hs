package com.dao;

import com.model.SecretKey;

public interface SecretKeyMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(SecretKey record);

    int insertSelective(SecretKey record);

    SecretKey selectByPrimaryKey(Long keyId);

    int updateByPrimaryKeySelective(SecretKey record);

    int updateByPrimaryKey(SecretKey record);
    
}