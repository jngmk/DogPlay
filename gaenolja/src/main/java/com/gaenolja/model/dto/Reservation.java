package com.gaenolja.model.dto;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
	private int paidid;
	private int hotelnumber;
	private String userid;
	private String dog;
	private String roomname;
	private LocalDateTime startdate;
	private LocalDateTime finishdate;
	private int count;
	private int visit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPaidid() {
		return paidid;
	}
	public void setPaidid(int paidid) {
		this.paidid = paidid;
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
	public String getDog() {
		return dog;
	}
	public void setDog(String dog) {
		this.dog = dog;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getVisit() {
		return visit;
	}
	public void setVisit(int visit) {
		this.visit = visit;
	}	
}
