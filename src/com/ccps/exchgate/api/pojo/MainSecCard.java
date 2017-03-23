package com.ccps.exchgate.api.pojo;

/**
 * 客户主副卡
 * @author jiangzz
 *
 */
public class MainSecCard {
		
	private Long initDate;
	
	private String fundAccount;
		
	private String exchangeId;
	
	private String exchangeFundAccount;	
	
	private String accountNoMain;
	
	public Long getInitDate() {
		return initDate;
	}
	
	public void setInitDate(Long initDate) {
		this.initDate = initDate;
	}
	
	public String getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	
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
	public String getExchangeFundAccount() {
		return exchangeFundAccount;
	}
	
	public void setExchangeFundAccount(String exchangeFundAccount) {
		this.exchangeFundAccount = exchangeFundAccount;
	}
	
	public String getAccountNoMain() {
		return accountNoMain;
	}
	
	public void setAccountNoMain(String accountNoMain) {
		this.accountNoMain = accountNoMain;
	}
	

}
