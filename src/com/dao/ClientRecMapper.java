package com.dao;

import com.model.ClientRec;

public interface ClientRecMapper {
    int deleteByPrimaryKey(Long clientRecId);

    int insert(ClientRec record);

    int insertSelective(ClientRec record);

    ClientRec selectByPrimaryKey(Long clientRecId);

    int updateByPrimaryKeySelective(ClientRec record);

    int updateByPrimaryKey(ClientRec record);
}