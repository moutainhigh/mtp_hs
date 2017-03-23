package com.dao;

import java.util.List;

import com.model.Exch;

public interface ExchMapper {
    int deleteByPrimaryKey(String exchNo);

    int insert(Exch record);

    int insertSelective(Exch record);

    Exch selectByPrimaryKey(String exchNo);

    int updateByPrimaryKeySelective(Exch record);

    int updateByPrimaryKey(Exch record);
    
    public List<Exch> selectByStatus(Integer status);
}