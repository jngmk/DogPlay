package com.gaenolja.model.dto;

import java.time.LocalDate;

public class Doginfo {
    private int id;
	private String userid;
	private String dogname;
	private int speciesId;
	private int size;
	private LocalDate age;
	private int gender;
	private String picture;
	private Object detail;
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
	public String getDogname() {
		return dogname;
	}
	public void setDogname(String dogname) {
		this.dogname = dogname;
	}
	public int getSpeciesId() {
		return speciesId;
	}
	public void setSpeciesId(int speciesId) {
		this.speciesId = speciesId;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public LocalDate getAge() {
		return age;
	}
	public void setAge(LocalDate age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Object getDetail() {
		return detail;
	}
	public void setDetail(Object detail) {
		this.detail = detail;
	}
	
}
