package com.ccps.exchgate.api.t2.param;

/**
 * @ClassName: CustomerBankInfo 
 * @Description: 银行客户信息变更（交易所起调）
 * @author kexj12709
 * @date 2016年2月24日 下午8:13:05 
 */
public class CustomerBankInfo {
	Long initDate;	//业务发生日期
	String requestId;	//交易所流水
	String exchangeId;	//交易所代码
	String exchangeFundAccount;	//交易所资金账号
	String bankProCode;	//银行产品代码
	String bankAccount;	//银行账号
	String bankAccountName;	//银行账号名称
	String bankPassword;	//银行密码
	String fullName;	//会员全称
	
	public Long getInitDate() {
		return initDate;
	}
	public void setInitDate(Long initDate) {
		this.initDate = initDate;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankPassword() {
		return bankPassword;
	}
	public void setBankPassword(String bankPassword) {
		this.bankPassword = bankPassword;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}
