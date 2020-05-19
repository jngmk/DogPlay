package com.gaenolja.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelDAO;
import com.gaenolja.model.dto.Hotel;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	HotelDAO dao;
	
	@Override
	public void insert(Hotel hotel) {
		try {
			dao.insert(hotel);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void update(Hotel hotel) {
		try {
			dao.update(hotel);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void delete(int hotelnumber) {
		try {
			dao.delete(hotelnumber);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
}
