package com.gaenolja.model.dto;

public class HotelStar {
    private int hotelnumber;
	private String userid;
	private int hashid;
	private String hotelname;
	private double latitude;
	private double longitude;
	private String address;
	private Object contact;
	private String info;
	private Object detail;
	private double star;
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
	public int getHashid() {
		return hashid;
	}
	public void setHashid(int hashid) {
		this.hashid = hashid;
	}
	public String getHotelname() {
		return hotelname;
	}
	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Object getContact() {
		return contact;
	}
	public void setContact(Object contact) {
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
	public double getStar() {
		return star;
	}
	public void setStar(long star) {
		this.star = star;
	}
}
