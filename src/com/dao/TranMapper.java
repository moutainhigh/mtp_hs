package com.dao;

import com.model.Tran;

public interface TranMapper {
    int deleteByPrimaryKey(String tranNo);

    int insert(Tran record);

    int insertSelective(Tran record);

    Tran selectByPrimaryKey(String tranNo);

    int updateByPrimaryKeySelective(Tran record);

    int updateByPrimaryKey(Tran record);
    
    Tran selectByBankPro(String bankProcode);
}