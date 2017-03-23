package com.ccps.exchgate.api.t2.param;

import com.ccps.exchgate.api.pojo.BaseReq;

/**
 * 
 * @author jiangzz
 *
 */
@SuppressWarnings("serial")
public class SettleFileServiceReq extends BaseReq {
	
	private Integer initDate;
	
	private String exchangeId;
	
	private String fileType;
	
	private String filePath;
	
	private String fileName;
	
	private String reissueFlag;
	
	private String fileMd5;
	
	private String busiDatetime;

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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReissueFlag() {
		return reissueFlag;
	}

	public void setReissueFlag(String reissueFlag) {
		this.reissueFlag = reissueFlag;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getBusiDatetime() {
		return busiDatetime;
	}

	public void setBusiDatetime(String busiDatetime) {
		this.busiDatetime = busiDatetime;
	}
	
}