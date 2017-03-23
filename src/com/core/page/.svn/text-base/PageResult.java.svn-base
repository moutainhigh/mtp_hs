package com.core.page;
import java.util.ArrayList;
import java.util.List;

import com.core.entity.BaseEntity;


/**
 * 
 * @ClassName: PageResult
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 闫志刚
 * @date 2016年3月24日 下午2:22:56
 * 
 * @param <T>
 */
public class PageResult<T> extends BaseEntity {
	private static final long serialVersionUID = 2505311294797637364L;
	// 当前页
	private int currentPage;
	// 总共记录条数
	private int totalSize;
	// 结果集
	private List<T> resultList = new ArrayList<T>();

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
