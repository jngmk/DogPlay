package com.gaenolja.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelroomDAO;
import com.gaenolja.model.dto.Hotelroom;

@Service
public class HotelroomServiceImpl implements HotelroomService{
	
	@Autowired
	private HotelroomDAO dao;
	
	@Override
	public List<Hotelroom> searchall(){
		try {
			List<Hotelroom> room = dao.searchall();
			return room;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Hotelroom search(int id) {
		try {
			Hotelroom room = dao.search(id);
			return room;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotelroom> searchbyhotel(int hotelnumber){
		try {
			List<Hotelroom> room = dao.searchbyhotel(hotelnumber);
			return room;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Hotelroom searchbyhotelandroom(String roomname, int hotelnumber) {
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("roomname", roomname);
			map.put("hotelnumber", hotelnumber);
			Hotelroom room = dao.searchbyhotelandroom(map);
			return room;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotelroom> searchbyprice(int price){
		try {
			List<Hotelroom> room = dao.searchbyprice(price);
			return room;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotelroom> searchbysize(int size){
		try {
			List<Hotelroom> room = dao.searchbysize(size);
			return room;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Hotelroom room) {
		try {
			dao.insert(room);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Hotelroom room) {
		try {
			dao.update(room);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		try {
			dao.delete(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
