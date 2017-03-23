package com.ccps.exchgate.api.pojo;

/**
 * 客户银行卡变更请求
 * @author jiangzz
 *
 */
public class BankCardInfoChangeReq {
	
	private Integer initDate;
	
	private String serialNo;
	
	private String exchangeId;
	
	private String exchangeFundAccount;
	
	private String bankProCode;
	
	private String oldBankAccount;
	
	private String bankAccount;
	
	private String oldBankAccountName;
	
	private String bankAccountName;
	
	private String fundPassword;

	public Integer getInitDate() {
		return initDate;
	}

	public void setInitDate(Integer initDate) {
		this.initDate = initDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getExchangeFundAccount() {
		return exchangeFundAccount;
	}

	public void setExchangeFundAccount(String exchangeFundAccount) {
		this.exchangeFundAccount = exchangeFundAccount;
	}

	public String getBankProCode() {
		return bankProCode;
	}

	public void setBankProCode(String bankProCode) {
		this.bankProCode = bankProCode;
	}

	public String getOldBankAccount() {
		return oldBankAccount;
	}

	public void setOldBankAccount(String oldBankAccount) {
		this.oldBankAccount = oldBankAccount;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getOldBankAccountName() {
		return oldBankAccountName;
	}

	public void setOldBankAccountName(String oldBankAccountName) {
		this.oldBankAccountName = oldBankAccountName;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getFundPassword() {
		return fundPassword;
	}

	public void setFundPassword(String fundPassword) {
		this.fundPassword = fundPassword;
	}

}
