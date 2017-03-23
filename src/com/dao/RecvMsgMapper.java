package com.dao;

import com.model.RecvMsg;

public interface RecvMsgMapper {
    int deleteByPrimaryKey(Long recvMsgId);

    int insert(RecvMsg record);

    int insertSelective(RecvMsg record);

    RecvMsg selectByPrimaryKey(Long recvMsgId);

    int updateByPrimaryKeySelective(RecvMsg record);

    int updateByPrimaryKey(RecvMsg record);
}