package com.model;

import java.util.Date;

public class CenterFileRec {
    private Long centerFileRecId;

    private String tranDate;

    private String centerSeq;

    private Integer recvSendType;

    private String fileType;

    private String filePath;

    private String fileName;

    private Integer isResend;

    private String md5Str;

    private Integer dealStatus;

    private String dealDesc;

    private Long recvMsgId;

    private Long sendMsgId;

    private Date sysTime;

    public Long getCenterFileRecId() {
        return centerFileRecId;
    }

    public void setCenterFileRecId(Long centerFileRecId) {
        this.centerFileRecId = centerFileRecId;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate == null ? null : tranDate.trim();
    }

    public String getCenterSeq() {
        return centerSeq;
    }

    public void setCenterSeq(String centerSeq) {
        this.centerSeq = centerSeq == null ? null : centerSeq.trim();
    }

    public Integer getRecvSendType() {
        return recvSendType;
    }

    public void setRecvSendType(Integer recvSendType) {
        this.recvSendType = recvSendType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Integer getIsResend() {
        return isResend;
    }

    public void setIsResend(Integer isResend) {
        this.isResend = isResend;
    }

    public String getMd5Str() {
        return md5Str;
    }

    public void setMd5Str(String md5Str) {
        this.md5Str = md5Str == null ? null : md5Str.trim();
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