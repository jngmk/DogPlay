package com.gaenolja.model.dto;


public class Hotel {
    private String hotelnumber;
	private String userid;
	private String hotelname;
	private double latitude;
	private double longitude;
	private String address;
	private String contact;
	private String info;
	private Object detail;
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
	public String getHotelname() {
		return hotelname;
	}
	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Object getDetail() {
		return detail;
	}
	public void setDetail(Object detail) {
		this.detail = detail;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
