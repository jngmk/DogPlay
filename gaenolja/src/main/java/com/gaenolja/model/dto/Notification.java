package com.gaenolja.model.dto;

public class Notification { 	
	private int id;
	private String userid;
	private String target;
	private String hotelnumber;
	private String subjects;
	private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getHotelnumber() {
		return hotelnumber;
	}
	public void setHotelnumber(String hotelnumber) {
		this.hotelnumber = hotelnumber;
	}
	public String getSubjects() {
		return subjects;
	}
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
