package com.ccps.exchgate.api.t2.param;

import com.ccps.exchgate.api.pojo.BaseReq;

public class SignInExchangeReq extends BaseReq {

	private static final long serialVersionUID = 1L;
	private long initDate;//业务发生日期
	private String exchangeId;//交易所编码
	private String bankProCode;//银行产品代码
	private String exchangeMarketNo;//交易市场编号
	private String operatingStatus;//营业状态 0:正常，1:日终，2:暂停
	
	public long getInitDate() {
		return initDate;
	}
	public void setInitDate(long initDate) {
		this.initDate = initDate;
	}
	
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	public String getBankProCode() {
		return bankProCode;
	}
	public void setBankProCode(String bankProCode) {
		this.bankProCode = bankProCode;
	}
	public String getExchangeMarketNo() {
		return exchangeMarketNo;
	}
	public void setExchangeMarketNo(String exchangeMarketNo) {
		this.exchangeMarketNo = exchangeMarketNo;
	}
	public String getOperatingStatus() {
		return operatingStatus;
	}
	public void setOperatingStatus(String operatingStatus) {
		this.operatingStatus = operatingStatus;
	}
}
