/**
 * 
 */
package com.core.model;
import java.io.Serializable;
import java.util.List;

import com.core.exception.ServiceException;


/**
 * 请求分页model
 * 
 * @author liu.bing
 *
 */
public class RequestPage implements Serializable {
    /**
     * 序列
     */
    private static final long   serialVersionUID = 1640782423747732545L;

    /**
     * 查询功能id
     */
    private String              statementId;

    /**
     * 查询页码
     */
    private Integer             pageNo;
    /**
     * 每页数量
     */
    private Integer             pageSize;
    /**
     * 是否需要总数 1-需要 0 -不需要
     */
    private Integer             isNeedCount;
    /**
     * 查询条件
     */
    private List<CriteriaModel> criteriaList;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<CriteriaModel> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<CriteriaModel> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public Integer getIsNeedCount() {
        return isNeedCount;
    }

    public void setIsNeedCount(Integer isNeedCount) {
        this.isNeedCount = isNeedCount;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    /**
     * 检测请求头是否有效
     * 
     * @param reqPage
     * @ create by yang.xinwen 20160307
     */
    public static void checkReqHead(RequestPage reqPage) {
        if (reqPage == null) throw new ServiceException("请求头为空");
        if (reqPage.getPageNo() == null || reqPage.getPageNo() < 1)
            throw new ServiceException("请求头请求页数设置错误");
        if (reqPage.getPageSize() == null || reqPage.getPageSize() < 1)
            throw new ServiceException("请求头请求每页数量设置错误");
        if (reqPage.getIsNeedCount() == null)
            throw new ServiceException("请求头是否回应总数为空");
    }

}
