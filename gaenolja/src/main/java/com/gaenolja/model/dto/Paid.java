package com.gaenolja.model.dto;

public class Paid {
    private int id;
    private String tid;
    private String cid;
    private String aid;
    private int cancel_amount;
    private int cancel_tax_free_amount;
    private String userid;
    private String pg_token;
    private String partner_order_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public int getCancel_amount() {
		return cancel_amount;
	}
	public void setCancel_amount(int cancel_amount) {
		this.cancel_amount = cancel_amount;
	}
	public int getCancel_tax_free_amount() {
		return cancel_tax_free_amount;
	}
	public void setCancel_tax_free_amount(int cancel_tax_free_amount) {
		this.cancel_tax_free_amount = cancel_tax_free_amount;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPg_token() {
		return pg_token;
	}
	public void setPg_token(String pg_token) {
		this.pg_token = pg_token;
	}
	public String getPartner_order_id() {
		return partner_order_id;
	}
	public void setPartner_order_id(String partner_order_id) {
		this.partner_order_id = partner_order_id;
	}
}
