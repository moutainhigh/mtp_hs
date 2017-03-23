package com.dao;

import com.model.Bank;

public interface BankMapper {
    int deleteByPrimaryKey(String bankNo);

    int insert(Bank record);

    int insertSelective(Bank record);

    Bank selectByPrimaryKey(String bankNo);

    int updateByPrimaryKeySelective(Bank record);

    int updateByPrimaryKey(Bank record);
}