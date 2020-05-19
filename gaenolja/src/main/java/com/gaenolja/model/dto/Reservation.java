package com.gaenolja.model.dto;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
	private int hotelnumber;
	private String userid;
	private Object dog;
	private Object roomname;
	private LocalDateTime startdate;
	private LocalDateTime finishdate;
	private Object paid;
	private int visit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHotelnumber() {
		return hotelnumber;
	}
	public void setHotelnumber(int hotelnumber) {
		this.hotelnumber = hotelnumber;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Object getDog() {
		return dog;
	}
	public void setDog(Object dog) {
		this.dog = dog;
	}
	public Object getRoomname() {
		return roomname;
	}
	public void setRoomname(Object roomname) {
		this.roomname = roomname;
	}
	public LocalDateTime getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDateTime startdate) {
		this.startdate = startdate;
	}
	public LocalDateTime getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(LocalDateTime finishdate) {
		this.finishdate = finishdate;
	}
	public Object getPaid() {
		return paid;
	}
	public void setPaid(Object paid) {
		this.paid = paid;
	}
	public int getVisit() {
		return visit;
	}
	public void setVisit(int visit) {
		this.visit = visit;
	}
	
}
