package com.model;

public class Product {

	private Long product_id;

    private String tran_no;

    private String bank_date;

    private String center_seq;

    private String exch_no;

    private Integer product_status;

    private Integer data_type;

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public String getTran_no() {
		return tran_no;
	}

	public void setTran_no(String tran_no) {
		this.tran_no = tran_no;
	}

	public String getBank_date() {
		return bank_date;
	}

	public void setBank_date(String bank_date) {
		this.bank_date = bank_date;
	}

	public String getCenter_seq() {
		return center_seq;
	}

	public void setCenter_seq(String center_seq) {
		this.center_seq = center_seq;
	}

	public String getExch_no() {
		return exch_no;
	}

	public void setExch_no(String exch_no) {
		this.exch_no = exch_no;
	}

	public Integer getProduct_status() {
		return product_status;
	}

	public void setProduct_status(Integer product_status) {
		this.product_status = product_status;
	}

	public Integer getData_type() {
		return data_type;
	}

	public void setData_type(Integer data_type) {
		this.data_type = data_type;
	}

    
}