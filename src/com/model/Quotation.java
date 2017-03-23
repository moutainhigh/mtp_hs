package com.model;

public class Quotation {
    private Long quotationId;

    private String initDate;

    private String priceType;

    private String exchangeId;

    private String exchangeMarketType;

    private String productCategoryId;

    private String productCode;

    private String moneyType;

    private String bitNum;

    private String openQuotationPrice;

    private String preclosePrice;

    private String askPrice;

    private String bidPrice;

    private String lastPrice;

    private String highPrice;

    private String lowPrice;

    private String timestamph;

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate == null ? null : initDate.trim();
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType == null ? null : priceType.trim();
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId == null ? null : exchangeId.trim();
    }

    public String getExchangeMarketType() {
        return exchangeMarketType;
    }

    public void setExchangeMarketType(String exchangeMarketType) {
        this.exchangeMarketType = exchangeMarketType == null ? null : exchangeMarketType.trim();
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId == null ? null : productCategoryId.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType == null ? null : moneyType.trim();
    }

    public String getBitNum() {
        return bitNum;
    }

    public void setBitNum(String bitNum) {
        this.bitNum = bitNum == null ? null : bitNum.trim();
    }

    public String getOpenQuotationPrice() {
        return openQuotationPrice;
    }

    public void setOpenQuotationPrice(String openQuotationPrice) {
        this.openQuotationPrice = openQuotationPrice == null ? null : openQuotationPrice.trim();
    }

    public String getPreclosePrice() {
        return preclosePrice;
    }

    public void setPreclosePrice(String preclosePrice) {
        this.preclosePrice = preclosePrice == null ? null : preclosePrice.trim();
    }

    public String getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(String askPrice) {
        this.askPrice = askPrice == null ? null : askPrice.trim();
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice == null ? null : bidPrice.trim();
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice == null ? null : lastPrice.trim();
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice == null ? null : highPrice.trim();
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice == null ? null : lowPrice.trim();
    }

    public String getTimestamph() {
        return timestamph;
    }

    public void setTimestamph(String timestamph) {
        this.timestamph = timestamph == null ? null : timestamph.trim();
    }
}