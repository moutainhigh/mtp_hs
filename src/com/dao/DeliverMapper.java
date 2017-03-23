package com.dao;

import com.model.Deliver;

public interface DeliverMapper {
    int deleteByPrimaryKey(Long deliverId);

    int insert(Deliver record);

    int insertSelective(Deliver record);

    Deliver selectByPrimaryKey(Long deliverId);

    int updateByPrimaryKeySelective(Deliver record);

    int updateByPrimaryKey(Deliver record);
}