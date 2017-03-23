package com.model;

import java.math.BigDecimal;
import java.util.Date;

public class SignRec {
    private Long signRecId;

    private Long clientId;

    private String exchNo;

    private String tradeAcct;

    private String tranNo;

    private String tranDate;

    private String bankSeq;

    private String centerSeq;

    private String acct;

    private String acctName;

    private String currency;

    private String cardBankNo;

    private String cardAcct;

    private String cardName;

    private Integer acctType;

    private String certType;

    private String certCode;

    private String clientName;

    private String mobile;

    private String email;

    private String extendInfo;

    private Integer isForce;

    private Integer signFlag;

    private Integer changeType;

    private Integer senderType;

    private Integer dealStatus;

    private String dealDesc;

    private Integer isSendLink;

    private Integer isFirstSign;

    private BigDecimal signAmt;

    private Long recvMsgId;

    private Long sendMsgId;

    private Date sysTime;

    public Long getSignRecId() {
        return signRecId;
    }

    public void setSignRecId(Long signRecId) {
        this.signRecId = signRecId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getExchNo() {
        return exchNo;
    }

    public void setExchNo(String exchNo) {
        this.exchNo = exchNo == null ? null : exchNo.trim();
    }

    public String getTradeAcct() {
        return tradeAcct;
    }

    public void setTradeAcct(String tradeAcct) {
        this.tradeAcct = tradeAcct == null ? null : tradeAcct.trim();
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo == null ? null : tranNo.trim();
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate == null ? null : tranDate.trim();
    }

    public String getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(String bankSeq) {
        this.bankSeq = bankSeq == null ? null : bankSeq.trim();
    }

    public String getCenterSeq() {
        return centerSeq;
    }

    public void setCenterSeq(String centerSeq) {
        this.centerSeq = centerSeq == null ? null : centerSeq.trim();
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct == null ? null : acct.trim();
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName == null ? null : acctName.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getCardBankNo() {
        return cardBankNo;
    }

    public void setCardBankNo(String cardBankNo) {
        this.cardBankNo = cardBankNo == null ? null : cardBankNo.trim();
    }

    public String getCardAcct() {
        return cardAcct;
    }

    public void setCardAcct(String cardAcct) {
        this.cardAcct = cardAcct == null ? null : cardAcct.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public Integer getAcctType() {
        return acctType;
    }

    public void setAcctType(Integer acctType) {
        this.acctType = acctType;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode == null ? null : certCode.trim();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo == null ? null : extendInfo.trim();
    }

    public Integer getIsForce() {
        return isForce;
    }

    public void setIsForce(Integer isForce) {
        this.isForce = isForce;
    }

    public Integer getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public Integer getSenderType() {
        return senderType;
    }

    public void setSenderType(Integer senderType) {
        this.senderType = senderType;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getDealDesc() {
        return dealDesc;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc == null ? null : dealDesc.trim();
    }

    public Integer getIsSendLink() {
        return isSendLink;
    }

    public void setIsSendLink(Integer isSendLink) {
        this.isSendLink = isSendLink;
    }

    public Integer getIsFirstSign() {
        return isFirstSign;
    }

    public void setIsFirstSign(Integer isFirstSign) {
        this.isFirstSign = isFirstSign;
    }

    public BigDecimal getSignAmt() {
        return signAmt;
    }

    public void setSignAmt(BigDecimal signAmt) {
        this.signAmt = signAmt;
    }

    public Long getRecvMsgId() {
        return recvMsgId;
    }

    public void setRecvMsgId(Long recvMsgId) {
        this.recvMsgId = recvMsgId;
    }

    public Long getSendMsgId() {
        return sendMsgId;
    }

    public void setSendMsgId(Long sendMsgId) {
        this.sendMsgId = sendMsgId;
    }

    public Date getSysTime() {
        return sysTime;
    }

    public void setSysTime(Date sysTime) {
        this.sysTime = sysTime;
    }
}