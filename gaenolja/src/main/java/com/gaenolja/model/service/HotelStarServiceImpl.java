package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelStarDAO;
import com.gaenolja.model.dto.HotelStar;

@Service
public class HotelStarServiceImpl implements HotelStarService{
	
	@Autowired
	private HotelStarDAO dao;
	
	@Override
	public List<HotelStar> searchall(){
		try {
			List<HotelStar> hotelstar = dao.searchall();
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public HotelStar search(int hotelnumber) {
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<HotelStar> searchbyname(String hotelname){
		try {
			List<HotelStar> hotelstar = dao.searchbyname(hotelname);
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<HotelStar> searchbyhashtag(String hashtag){
		try {
			List<HotelStar> hotelstar = dao.searchbyhashtag(hashtag);
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
