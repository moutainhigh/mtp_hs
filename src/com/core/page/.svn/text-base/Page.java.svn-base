package com.core.page;
import java.util.Map;

import com.core.entity.BaseEntity;


/**
 * 
 * @ClassName: Page
 * @Description: 分页对象
 * @author 闫志刚
 * @date 2016年3月24日 下午2:22:17
 *
 */
public class Page extends BaseEntity {
	private static final long serialVersionUID = 2812277347460961243L;
	private Integer page; // 目前是第几页
	private Integer size; // 每页大小
	private Map params; // 传入的参数
	private String orderColumn;
	private String orderTurn = "ASC";

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderTurn() {
		return orderTurn;
	}

	public void setOrderTurn(String orderTurn) {
		this.orderTurn = orderTurn;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public Page() {
	}

	public Page(Integer page, Integer size) {
		super();
		this.page = page;
		this.size = size;
	}

}
