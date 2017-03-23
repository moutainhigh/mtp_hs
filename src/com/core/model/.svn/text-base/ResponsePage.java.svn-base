/**
 * 
 */
package com.core.model;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 分页查询结果model
 * 
 * @author liu.bing
 *
 */

@JsonInclude(Include.NON_NULL)
public class ResponsePage<E> implements Serializable {

    /**
     * 序列
     */
    private static final long serialVersionUID = -5652492763592374971L;
    /**
     * 查询页码
     */
    private Integer           pageNo;
    /**
     * 每页数量
     */
    private Integer           pageSize;
    /**
     * 返回结果 0-成功 1-失败
     */
    private Integer           retCode;
    /**
     * 异常信息
     */
    private String            retDesc;
    /**
     * 结果总数
     */
    private Long              count;
    /**
     * 查询结果
     */
    private List<E>            resultList;

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }

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

    public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
