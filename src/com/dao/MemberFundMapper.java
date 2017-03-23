package com.dao;

import com.model.MemberFund;

public interface MemberFundMapper {
    int deleteByPrimaryKey(Long memberFundId);

    int insert(MemberFund record);

    int insertSelective(MemberFund record);

    MemberFund selectByPrimaryKey(Long memberFundId);

    int updateByPrimaryKeySelective(MemberFund record);

    int updateByPrimaryKey(MemberFund record);
}