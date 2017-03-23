package com.ccps.exchgate.api.pojo;

import java.io.Serializable;

/**
 * 功能说明: 交易所报送文件类<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: jiangzz
 * 开发时间: <br>
 */
public class ExchFileNotifyReq implements Serializable {

	private static final long serialVersionUID = 6092814969757549674L;
	
	private Integer initDate;
	
	private String exchangeId;
	
	/**
	 * 1：出入金对账文件:2：开销户对帐文件;3:请算反馈文件;4:余额文件;5:总分监管文件；6：分账户余额文件
	 */
	private String bankFileType; 
	
	private String fileName;
	
	private String fileMd5;

	public Integer getInitDate() {
		return initDate;
	}

	public void setInitDate(Integer initDate) {
		this.initDate = initDate;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getBankFileType() {
		return bankFileType;
	}

	public void setBankFileType(String bankFileType) {
		this.bankFileType = bankFileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

}
