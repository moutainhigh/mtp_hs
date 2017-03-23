package com.dao;

import com.model.ControlDate;

public interface ControlDateMapper {
    int deleteByPrimaryKey(String date);

    int insert(ControlDate controlDate);

    int insertSelective(ControlDate controlDate);

    ControlDate selectByPrimaryKey(String date);

    int updateByPrimaryKeySelective(ControlDate controlDate);

    int updateByPrimaryKey(ControlDate controlDate);
    
}