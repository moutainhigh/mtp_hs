package com.model;

public class Bank {
    private String bankNo;

    private String bankProCode;

    private String bankName;

    private Integer status;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    public String getBankProCode() {
        return bankProCode;
    }

    public void setBankProCode(String bankProCode) {
        this.bankProCode = bankProCode == null ? null : bankProCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}