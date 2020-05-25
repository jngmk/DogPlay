package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelDAO;
import com.gaenolja.model.dto.Hotel;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	HotelDAO dao;
	
	@Override
	public List<Hotel> searchall(){
		try {
			List<Hotel> hotel = dao.searchall();
			return hotel;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Hotel search(int hotelnumber) {
		try {
			Hotel hotel = dao.search(hotelnumber);
			return hotel;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotel> searchbyname(String hotelname){
		try {
			List<Hotel> hotel = dao.searchbyname(hotelname);
			return hotel;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotel> searchbyhashtag(String hashtag){
		try {
			List<Hotel> hotel = dao.searchbyhashtag(hashtag);
			return hotel;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Hotel hotel) {
		try {
			dao.insert(hotel);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean update(Hotel hotel) {
		try {
			dao.update(hotel);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean delete(int hotelnumber) {
		try {
			dao.delete(hotelnumber);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
}
