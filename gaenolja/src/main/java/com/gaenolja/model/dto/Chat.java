package com.gaenolja.model.dto;

import java.time.LocalDateTime;

public class Chat {
	private int id;
	private int chatid;
	private String receive;
	private String send;
	private String picture;
	private String message;
	private LocalDateTime created;
	private int readmessage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public int getChatid() {
		return chatid;
	}
	public void setChatid(int chatid) {
		this.chatid = chatid;
	}
	public int getReadmessage() {
		return readmessage;
	}
	public void setReadmessage(int readmessage) {
		this.readmessage = readmessage;
	}
	
}
