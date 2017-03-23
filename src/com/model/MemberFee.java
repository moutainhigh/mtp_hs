package com.model;

public class MemberFee {
    private Long memberFeeId;

    private String initDate;

    private String serialNo;

    private String exchangeId;

    private String exchangeMarketType;

    private String bizType;

    private String exchangeFeesType;

    private String feesBalance;

    private String payerMemCode;

    private String payerFundAccount;

    private String payeeMemCode;

    private String payeeFundAccount;

    private String relatedBillType;

    private String relatedBillNo;

    private String remark;

    private String busiDatetime;

    public Long getMemberFeeId() {
        return memberFeeId;
    }

    public void setMemberFeeId(Long memberFeeId) {
        this.memberFeeId = memberFeeId;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate == null ? null : initDate.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId == null ? null : exchangeId.trim();
    }

    public String getExchangeMarketType() {
        return exchangeMarketType;
    }

    public void setExchangeMarketType(String exchangeMarketType) {
        this.exchangeMarketType = exchangeMarketType == null ? null : exchangeMarketType.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getExchangeFeesType() {
        return exchangeFeesType;
    }

    public void setExchangeFeesType(String exchangeFeesType) {
        this.exchangeFeesType = exchangeFeesType == null ? null : exchangeFeesType.trim();
    }

    public String getFeesBalance() {
        return feesBalance;
    }

    public void setFeesBalance(String feesBalance) {
        this.feesBalance = feesBalance == null ? null : feesBalance.trim();
    }

    public String getPayerMemCode() {
        return payerMemCode;
    }

    public void setPayerMemCode(String payerMemCode) {
        this.payerMemCode = payerMemCode == null ? null : payerMemCode.trim();
    }

    public String getPayerFundAccount() {
        return payerFundAccount;
    }

    public void setPayerFundAccount(String payerFundAccount) {
        this.payerFundAccount = payerFundAccount == null ? null : payerFundAccount.trim();
    }

    public String getPayeeMemCode() {
        return payeeMemCode;
    }

    public void setPayeeMemCode(String payeeMemCode) {
        this.payeeMemCode = payeeMemCode == null ? null : payeeMemCode.trim();
    }

    public String getPayeeFundAccount() {
        return payeeFundAccount;
    }

    public void setPayeeFundAccount(String payeeFundAccount) {
        this.payeeFundAccount = payeeFundAccount == null ? null : payeeFundAccount.trim();
    }

    public String getRelatedBillType() {
        return relatedBillType;
    }

    public void setRelatedBillType(String relatedBillType) {
        this.relatedBillType = relatedBillType == null ? null : relatedBillType.trim();
    }

    public String getRelatedBillNo() {
        return relatedBillNo;
    }

    public void setRelatedBillNo(String relatedBillNo) {
        this.relatedBillNo = relatedBillNo == null ? null : relatedBillNo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBusiDatetime() {
        return busiDatetime;
    }

    public void setBusiDatetime(String busiDatetime) {
        this.busiDatetime = busiDatetime == null ? null : busiDatetime.trim();
    }
}