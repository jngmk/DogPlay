package com.gaenolja.model.dto;

import java.time.LocalDateTime;

public class Response { 	
	private int id;
	private int reviewid;
	private String userid;
	private int heart;
	private String content;
	private LocalDateTime created;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReviewid() {
		return reviewid;
	}
	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getHeart() {
		return heart;
	}
	public void setHeart(int heart) {
		this.heart = heart;
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
