package com.dao;

import com.model.SignRec;

public interface SignRecMapper {
    int deleteByPrimaryKey(Long signRecId);

    int insert(SignRec record);

    int insertSelective(SignRec record);

    SignRec selectByPrimaryKey(Long signRecId);

    int updateByPrimaryKeySelective(SignRec record);

    int updateByPrimaryKey(SignRec record);
    
    SignRec selectByBankSeq(String bankSeq);
}