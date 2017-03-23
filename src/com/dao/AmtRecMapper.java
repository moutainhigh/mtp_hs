package com.dao;

import java.util.List;

import com.model.AmtRec;

public interface AmtRecMapper {
    int deleteByPrimaryKey(Long amtRecId);

    int insert(AmtRec record);

    int insertSelective(AmtRec record);

    AmtRec selectByPrimaryKey(Long amtRecId);

    int updateByPrimaryKeySelective(AmtRec record);

    int updateByPrimaryKey(AmtRec record);
    
    public List<AmtRec> selectByBean(AmtRec amtRec);
}