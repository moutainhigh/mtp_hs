package com.ccps.exchgate.api.pojo;

/**
 * 银行签到签退
 * @author jiangzz
 *
 */
public class SignAndOut {
	
	private String exchangeId;
	
	
	private Long initDate;
	
	private String bankNo;
	
	private String bankProCode;
	
	private String signInType;
	
	private String busiDatetime;
	
	private String operatingStatus;
	
	
	/*public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}*/
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	public Long getInitDate() {
		return initDate;
	}
	public void setInitDate(Long initDate) {
		this.initDate = initDate;
	}
	/*public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankProductCode() {
		return bankProductCode;
	}
	public void setBankProductCode(String bankProductCode) {
		this.bankProductCode = bankProductCode;
	}*/
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankProCode() {
		return bankProCode;
	}
	public void setBankProCode(String bankProCode) {
		this.bankProCode = bankProCode;
	}
	public String getSignInType() {
		return signInType;
	}
	public void setSignInType(String signInType) {
		this.signInType = signInType;
	}
	public String getBusiDatetime() {
		return busiDatetime;
	}
	public void setBusiDatetime(String busiDatetime) {
		this.busiDatetime = busiDatetime;
	}
	public String getOperatingStatus() {
		return operatingStatus;
	}
	public void setOperatingStatus(String operatingStatus) {
		this.operatingStatus = operatingStatus;
	}

}
