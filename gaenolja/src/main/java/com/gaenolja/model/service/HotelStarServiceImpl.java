package com.gaenolja.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelStarDAO;
import com.gaenolja.model.dao.HotelpictureDAO;
import com.gaenolja.model.dao.HotelroomDAO;
import com.gaenolja.model.dao.ReviewDAO;
import com.gaenolja.model.dto.HotelStar;

@Service
public class HotelStarServiceImpl implements HotelStarService{
	
	@Autowired
	private HotelStarDAO dao;
	
	@Autowired
	private HotelpictureDAO picturedao;
	
	@Autowired
	private HotelroomDAO roomdao;
	
	@Autowired
	private ReviewDAO reviewdao;
	
	@Override
	public List<HotelStar> searchall(){
		try {
			List<HotelStar> hotelstar = dao.searchall();
			for (HotelStar star:hotelstar) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			}
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
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
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
			for (HotelStar star:hotelstar) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			}
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
			for (HotelStar star:hotelstar) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			}
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public HashMap<Object, Object> hoteldetail(int hotelnumber){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			map.put("HotelStar", hotelstar);
			map.put("HotelPicture", picturedao.searchbyhotel(hotelnumber));
			map.put("HotelRoom", roomdao.searchbyhotel(hotelnumber));
			map.put("Good", reviewdao.goodreview(hotelnumber));
			map.put("Bad", reviewdao.badreview(hotelnumber));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
