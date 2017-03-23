package com.ccps.exchgate.api.t2.param;

import java.math.BigDecimal;

import com.ccps.exchgate.api.pojo.BaseReq;

public class SurrFrozenThrawReq extends BaseReq{
/**
 * 周边冻结解冻请求参数JSON对象
 * */	
	private Integer initDate;         //业务发生日期
	private String exchangeId;        //交易所代码  
	private String memCode;           //会员编码(原交易所)
	private String tradeAccount;      //交易账号(原交易所)
	private String holdId;            //持仓编号
	private String productCode;       //产品代码
	private String frozenType;	      //冻结解冻类型（1:冻结，2:解冻）
	private BigDecimal shareQuantity; //冻结、解冻数量
	private String frozenReason;	  //冻结原因
	private String busiDatetime;      //业务发生时间(yyyyMMddHHmmss)
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
	public String getMemCode() {
		return memCode;
	}
	public void setMemCode(String memCode) {
		this.memCode = memCode;
	}
	public String getTradeAccount() {
		return tradeAccount;
	}
	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}
	public String getHoldId() {
		return holdId;
	}
	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getFrozenType() {
		return frozenType;
	}
	public void setFrozenType(String frozenType) {
		this.frozenType = frozenType;
	}
	public BigDecimal getShareQuantity() {
		return shareQuantity;
	}
	public void setShareQuantity(BigDecimal shareQuantity) {
		this.shareQuantity = shareQuantity;
	}
	public String getFrozenReason() {
		return frozenReason;
	}
	public void setFrozenReason(String frozenReason) {
		this.frozenReason = frozenReason;
	}
	public String getBusiDatetime() {
		return busiDatetime;
	}
	public void setBusiDatetime(String busiDatetime) {
		this.busiDatetime = busiDatetime;
	}	
}
