package com.model;

public class SecretKey {
    private Long keyId;

    private Long initDate;

    private String exchangeId;

    private String bankProCode;

    private String keyInfo;

	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Long getInitDate() {
		return initDate;
	}

	public void setInitDate(Long initDate) {
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

	public String getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

}