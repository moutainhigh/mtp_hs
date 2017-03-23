package com.ccps.exchgate.api.t2.param;

import java.util.List;

import com.ccps.exchgate.api.pojo.PayWay;

/**
 * 功能说明: 支付类型控制器<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: wujy10703@hundsun.com<br>
 * 开发时间: 2015-11-20<br>
 */
public class PayWayQuery extends BasePageQuery{
	
	private static final long serialVersionUID = 1L;
	private List<PayWay> items;// 查询结果集
	private PayWay item;//查询结果
	private PayWay query;//查询条件
	public List<PayWay> getItems() {
		return items;
	}
	public void setItems(List<PayWay> items) {
		this.items = items;
	}
	public PayWay getItem() {
		return item;
	}
	public void setItem(PayWay item) {
		this.item = item;
	}
	public PayWay getQuery() {
		if(query == null){
			query = new PayWay();
		}
		return query;
	}
	public void setQuery(PayWay query) {
		this.query = query;
	}
	
}