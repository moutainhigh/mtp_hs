package com.ccps.exchgate.api.t2.param;

import com.ccps.exchgate.api.pojo.BaseReq;

@SuppressWarnings("serial")
public class FundCommonReq extends BaseReq{
	private String localTransCode;			//本地交易码
	private String exchangeId;				//交易所编号
	private long initDate;					//业务日期
	private String settlementOrderId;		//结算指令编号
	private String fundAccount;				//资金账号
	private String accountType;				//账号类型
	private String tradeMemCode;			//交易所会员编码
	private String memCode;					//交易所会员编码
	private String type;					//操作类型
	private String bankProCode;				//银行产品代码
	private long settlementDate;			//结算日期
	private String exchangeFundAccount;		//交易所资金账号
	private String marketType;				//交易市场类型
	private String accountOrderNo;			//记账请求编号
	private	String bindStatus;				//绑卡状态
	private Long freeInfoId;				//冻结记录编号
	private String accountNoMain;			//主卡账号
	private String accountNoSecondStr;		//副卡账号串，对个副卡间以英文逗号隔开
	private String inParam;					//
	private String accountUseType;			//
	private String billOrderNo;				//记账指令编号
	private String exchangeMemCode;			//交易所会员编号
	private Long feeAmount;					//费用金额
	private String currency;				//币种
	private String feeType;					//费用类型
	private Integer	createDate;				//创建时间
	private long endDate;					//日终完成日期
	private String isBankHas;				//是否存在银行记录
	private BindAccount bindAccountObje;//绑卡信息
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BindAccount getBindAccountObje() {
		return bindAccountObje;
	}
	public void setBindAccountObje(BindAccount bindAccountObje) {
		this.bindAccountObje = bindAccountObje;
	}
	public String getIsBankHas() {
		return isBankHas;
	}
	public void setIsBankHas(String isBankHas) {
		this.isBankHas = isBankHas;
	}
	
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	public String getLocalTransCode() {
		return localTransCode;
	}
	public void setLocalTransCode(String localTransCode) {
		this.localTransCode = localTransCode;
	}
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	public long getInitDate() {
		return initDate;
	}
	public void setInitDate(long initDate) {
		this.initDate = initDate;
	}
	public String getSettlementOrderId() {
		return settlementOrderId;
	}
	public void setSettlementOrderId(String settlementOrderId) {
		this.settlementOrderId = settlementOrderId;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getTradeMemCode() {
		return tradeMemCode;
	}
	public void setTradeMemCode(String tradeMemCode) {
		this.tradeMemCode = tradeMemCode;
	}
	public String getMemCode() {
		return memCode;
	}
	public void setMemCode(String memCode) {
		this.memCode = memCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBankProCode() {
		return bankProCode;
	}
	public void setBankProCode(String bankProCode) {
		this.bankProCode = bankProCode;
	}
	public long getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(long settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getExchangeFundAccount() {
		return exchangeFundAccount;
	}
	public void setExchangeFundAccount(String exchangeFundAccount) {
		this.exchangeFundAccount = exchangeFundAccount;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getAccountOrderNo() {
		return accountOrderNo;
	}
	public void setAccountOrderNo(String accountOrderNo) {
		this.accountOrderNo = accountOrderNo;
	}
	public String getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}
	public Long getFreeInfoId() {
		return freeInfoId;
	}
	public void setFreeInfoId(Long freeInfoId) {
		this.freeInfoId = freeInfoId;
	}
	public String getAccountNoMain() {
		return accountNoMain;
	}
	public void setAccountNoMain(String accountNoMain) {
		this.accountNoMain = accountNoMain;
	}
	public String getAccountNoSecondStr() {
		return accountNoSecondStr;
	}
	public void setAccountNoSecondStr(String accountNoSecondStr) {
		this.accountNoSecondStr = accountNoSecondStr;
	}
	public String getInParam() {
		return inParam;
	}
	public void setInParam(String inParam) {
		this.inParam = inParam;
	}
	public String getAccountUseType() {
		return accountUseType;
	}
	public void setAccountUseType(String accountUseType) {
		this.accountUseType = accountUseType;
	}
	public String getBillOrderNo() {
		return billOrderNo;
	}
	public void setBillOrderNo(String billOrderNo) {
		this.billOrderNo = billOrderNo;
	}
	public String getExchangeMemCode() {
		return exchangeMemCode;
	}
	public void setExchangeMemCode(String exchangeMemCode) {
		this.exchangeMemCode = exchangeMemCode;
	}
	public Long getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Integer getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Integer createDate) {
		this.createDate = createDate;
	}
	
}
