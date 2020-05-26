package com.gaenolja.model.dto;

import java.util.List;

public class HotelStar {
    private int hotelnumber;
	private String userid;
	private String hashid;
	private String hotelname;
	private double latitude;
	private double longitude;
	private String address;
	private Object contact;
	private String info;
	private Object detail;
	private double star;
	private int countstar;
	private int countreview;
	private List<String> hashtag;
	private double distance;
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
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
	public String getHashid() {
		return hashid;
	}
	public void setHashid(String hashid) {
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
	public void setStar(double star) {
		this.star = star;
	}
	public int getCountstar() {
		return countstar;
	}
	public void setCountstar(int countstar) {
		this.countstar = countstar;
	}
	public int getCountreview() {
		return countreview;
	}
	public void setCountreview(int countreview) {
		this.countreview = countreview;
	}
	public List<String> getHashtag() {
		return hashtag;
	}
	public void setHashtag(List<String> hashtag) {
		this.hashtag = hashtag;
	}
}
