package com.dao;

import com.model.DealInfo;

public interface DealInfoMapper {
    int deleteByPrimaryKey(Long dealInfoId);

    int insert(DealInfo record);

    int insertSelective(DealInfo record);

    DealInfo selectByPrimaryKey(Long dealInfoId);

    int updateByPrimaryKeySelective(DealInfo record);

    int updateByPrimaryKey(DealInfo record);
}