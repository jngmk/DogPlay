package com.gaenolja.model.dto;

public class Hotelroom {
    private int id;
	private String hotelnumber;
	private String roomname;
	private int price;
	private int minsize;
	private int maxsize;
	private int count;
	private String info;
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
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
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMinsize() {
		return minsize;
	}
	public void setMinsize(int minsize) {
		this.minsize = minsize;
	}
	public int getMaxsize() {
		return maxsize;
	}
	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
