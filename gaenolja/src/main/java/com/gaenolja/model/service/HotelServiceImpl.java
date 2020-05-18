package com.gaenolja.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelDAO;

@Service
public class HotelServiceImpl {
	
	@Autowired
	HotelDAO dao;
	
	public void insert() {
		try {
			dao.insert();
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	public void update() {
		try {
			dao.update();
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	public void delete() {
		try {
			dao.delete();
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
}
