package com.dao;

import com.model.SendMsg;

public interface SendMsgMapper {
    int deleteByPrimaryKey(Long sendMsgId);

    int insert(SendMsg record);

    int insertSelective(SendMsg record);

    SendMsg selectByPrimaryKey(Long sendMsgId);

    int updateByPrimaryKeySelective(SendMsg record);

    int updateByPrimaryKey(SendMsg record);
}