package com.gaenolja.model.dto;

import java.time.LocalDateTime;

public class Review {
	private int id;
	private String hotelnumber;
	private String userid;
	private int visitid;
	private float star;
	private LocalDateTime created;
	private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHotelnumber() {
		return hotelnumber;
	}
	public void setHotelnumber(String hotelnumber) {
		this.hotelnumber = hotelnumber;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getVisitid() {
		return visitid;
	}
	public void setVisitid(int visitid) {
		this.visitid = visitid;
	}
	public float getStar() {
		return star;
	}
	public void setStar(float star) {
		this.star = star;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
}