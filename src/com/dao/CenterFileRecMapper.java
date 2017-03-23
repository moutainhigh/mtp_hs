package com.dao;

import com.model.CenterFileRec;

public interface CenterFileRecMapper {
    int deleteByPrimaryKey(Long centerFileRecId);

    int insert(CenterFileRec record);

    int insertSelective(CenterFileRec record);

    CenterFileRec selectByPrimaryKey(Long centerFileRecId);

    int updateByPrimaryKeySelective(CenterFileRec record);

    int updateByPrimaryKey(CenterFileRec record);
}