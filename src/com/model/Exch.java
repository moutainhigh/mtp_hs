package com.model;

public class Exch {
    private String exchNo;

    private String exchName;

    private String queCode;

    private String encryptKey;

    private Integer platType;

    private Integer tranStatus;

    private Integer status;

    private Integer checkFileStyle;

    private String setDate;

    private String setTime;
    
    private int openCloseFlag;

    public int getOpenCloseFlag() {
		return openCloseFlag;
	}

	public void setOpenCloseFlag(int openCloseFlag) {
		this.openCloseFlag = openCloseFlag;
	}

	public String getExchNo() {
        return exchNo;
    }

    public void setExchNo(String exchNo) {
        this.exchNo = exchNo == null ? null : exchNo.trim();
    }

    public String getExchName() {
        return exchName;
    }

    public void setExchName(String exchName) {
        this.exchName = exchName == null ? null : exchName.trim();
    }

    public String getQueCode() {
        return queCode;
    }

    public void setQueCode(String queCode) {
        this.queCode = queCode == null ? null : queCode.trim();
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey == null ? null : encryptKey.trim();
    }

    public Integer getPlatType() {
        return platType;
    }

    public void setPlatType(Integer platType) {
        this.platType = platType;
    }

    public Integer getTranStatus() {
        return tranStatus;
    }

    public void setTranStatus(Integer tranStatus) {
        this.tranStatus = tranStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCheckFileStyle() {
        return checkFileStyle;
    }

    public void setCheckFileStyle(Integer checkFileStyle) {
        this.checkFileStyle = checkFileStyle;
    }

    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate == null ? null : setDate.trim();
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime == null ? null : setTime.trim();
    }
}