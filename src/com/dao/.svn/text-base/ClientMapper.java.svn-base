package com.dao;

import com.model.Client;

public interface ClientMapper {
    int deleteByPrimaryKey(Long clientId);

    int insert(Client record);

    int insertSelective(Client record);

    Client selectByPrimaryKey(Long clientId);

    int updateByPrimaryKeySelective(Client record);

    int updateByPrimaryKey(Client record);
    
    Client selectByTradeAcct(String tradeAcct);
}