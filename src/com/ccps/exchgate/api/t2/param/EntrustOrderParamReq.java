package com.ccps.exchgate.api.t2.param;

import java.util.ArrayList;
import java.util.List;

import com.ccps.exchgate.api.pojo.BaseReq;

/**
 * 
 * @author jiangzz
 *
 */
@SuppressWarnings("serial")
public class EntrustOrderParamReq extends BaseReq {
	
private List<EntrustOrderParam> items = new ArrayList<EntrustOrderParam>();
	
	public List<EntrustOrderParam> getItems() {
		return items;
	}

	public void setItems(List<EntrustOrderParam> items) {
		this.items = items;
	}
    
}