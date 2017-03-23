package com.dao;

import com.model.RegisterRec;

public interface RegisterRecMapper {
    int deleteByPrimaryKey(Long registerRecId);

    int insert(RegisterRec record);

    int insertSelective(RegisterRec record);

    RegisterRec selectByPrimaryKey(Long registerRecId);

    int updateByPrimaryKeySelective(RegisterRec record);

    int updateByPrimaryKey(RegisterRec record);
}