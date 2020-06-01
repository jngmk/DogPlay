package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HashtagDAO;
import com.gaenolja.model.dto.Hashtag;
import com.gaenolja.model.dao.HotelHashDAO;
import com.gaenolja.model.dto.HotelHash;


@Service
public class HashtagServiceImpl implements HashtagService{
	
	@Autowired
	private HashtagDAO dao;
	
	@Autowired
	private HotelHashDAO hotelhashdao;
		
	@Override
	public List<Hashtag> searchall(){
		try {
			List<Hashtag> hashtag = dao.searchall();
			return hashtag;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Hashtag search(int id) {
		try {
			Hashtag hashtag = dao.search(id);
			return hashtag;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Hashtag hashtag) {
		try {
			dao.insert(hashtag);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Hashtag hashtag) {
		try {
			dao.update(hashtag);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<HotelHash> searchallhotelhash(){
		try {
			List<HotelHash> hotelhash = hotelhashdao.searchall();
			return hotelhash;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HotelHash searchhotelhash(int id) {
		try {
			HotelHash hotelhash = hotelhashdao.search(id);
			return hotelhash;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Integer> searchhotelhashbyhotel(String hotelnumber) {
		try {
			List<Integer> hashtag = hotelhashdao.searchbyhotel(hotelnumber);
			return hashtag;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> searchhotelhashbyhash(int hashtag) {
		try {
			List<String> hotelnumber = hotelhashdao.searchbyhash(hashtag);
			return hotelnumber;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean inserthotelhash(HotelHash hotelhash) {
		try {
			hotelhashdao.insert(hotelhash);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean updatehotelhash(HotelHash hotelhash) {
		try {
			hotelhashdao.update(hotelhash);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean deletehotelhash(int id) {
		try {
			hotelhashdao.delete(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
