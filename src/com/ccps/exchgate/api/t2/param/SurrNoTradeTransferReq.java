package com.ccps.exchgate.api.t2.param;

import java.math.BigDecimal;

import com.ccps.exchgate.api.pojo.BaseReq;

public class SurrNoTradeTransferReq extends BaseReq{
   /**
    * 周边接口非交易过户输入参数转换后成为JSON对象
    **/
	private Integer initDate;          //业务发生日期
	private String  exchangeId;        //交易所代码
	
    private String openMemCode;        //会员编码((原交易所)发起方)
    private String openTradeAccount;   //交易账号((原交易所)发起方)
    private String openHoldId;         //持仓编号((原交易所)发起方)
   
    private String oppMemCode;         //会员编码((原交易所)对手方)
    private String oppTradeAccount;    //交易账号((原交易所)对手方)
    private String oppHoldId;          //持仓编号((原交易所)对手方)

    private String productCode;        //产品代码
    private String holdFlowType;       //流通股类型（1：流通股，2：非流通股）
	private BigDecimal shareQuantity;  //过户数量	
    private Long priceBuild;           //建仓价
    private Long holdPrice;            //平仓价
    private Long squarePrice;          //持仓价
	private String busiDatetime;       //业务发生时间(yyyyMMddHHmmss)
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
	public String getOpenMemCode() {
		return openMemCode;
	}
	public void setOpenMemCode(String openMemCode) {
		this.openMemCode = openMemCode;
	}
	public String getOpenTradeAccount() {
		return openTradeAccount;
	}
	public void setOpenTradeAccount(String openTradeAccount) {
		this.openTradeAccount = openTradeAccount;
	}
	public String getOpenHoldId() {
		return openHoldId;
	}
	public void setOpenHoldId(String openHoldId) {
		this.openHoldId = openHoldId;
	}
	public String getOppMemCode() {
		return oppMemCode;
	}
	public void setOppMemCode(String oppMemCode) {
		this.oppMemCode = oppMemCode;
	}
	public String getOppTradeAccount() {
		return oppTradeAccount;
	}
	public void setOppTradeAccount(String oppTradeAccount) {
		this.oppTradeAccount = oppTradeAccount;
	}
	public String getOppHoldId() {
		return oppHoldId;
	}
	public void setOppHoldId(String oppHoldId) {
		this.oppHoldId = oppHoldId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getHoldFlowType() {
		return holdFlowType;
	}
	public void setHoldFlowType(String holdFlowType) {
		this.holdFlowType = holdFlowType;
	}
	public BigDecimal getShareQuantity() {
		return shareQuantity;
	}
	public void setShareQuantity(BigDecimal shareQuantity) {
		this.shareQuantity = shareQuantity;
	}
	public Long getPriceBuild() {
		return priceBuild;
	}
	public void setPriceBuild(Long priceBuild) {
		this.priceBuild = priceBuild;
	}
	public Long getHoldPrice() {
		return holdPrice;
	}
	public void setHoldPrice(Long holdPrice) {
		this.holdPrice = holdPrice;
	}
	public Long getSquarePrice() {
		return squarePrice;
	}
	public void setSquarePrice(Long squarePrice) {
		this.squarePrice = squarePrice;
	}
	public String getBusiDatetime() {
		return busiDatetime;
	}
	public void setBusiDatetime(String busiDatetime) {
		this.busiDatetime = busiDatetime;
	}
		
}
