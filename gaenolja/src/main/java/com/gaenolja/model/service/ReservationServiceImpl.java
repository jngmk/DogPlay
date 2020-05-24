package com.gaenolja.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ReservationDAO;
import com.gaenolja.model.dto.Reservation;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private ReservationDAO dao;
	
	@Override
	public List<Reservation> searchall(){
		try {
			List<Reservation> reservation = dao.searchall();
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reservation> searchbyuserid(String userid){
		try {
			List<Reservation> reservation = dao.searchbyuserid(userid);
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reservation> searchbyhotel(int hotelnumber){
		try {
			List<Reservation> reservation = dao.searchbyhotel(hotelnumber);
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reservation> searchbyhotelandroom(int hotelnumber, String roomname){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelnumber", hotelnumber);
			map.put("roomname", roomname);
			List<Reservation> reservation = dao.searchbyhotelandroom(map);
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int countbyhotelandroom(int hotelnumber, String roomname) {
		int count = 0;
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelnumber", hotelnumber);
			map.put("roomname", roomname);
			count = dao.countbyhotelandroom(map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public Reservation search(int id) {
		try {
			Reservation reservation = dao.search(id);
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Reservation reservation) {
		try {
			dao.insert(reservation);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Reservation reservation) {
		try {
			dao.update(reservation);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(int id) {
		try {
			dao.delete(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
