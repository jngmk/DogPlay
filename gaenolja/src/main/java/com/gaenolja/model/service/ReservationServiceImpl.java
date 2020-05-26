package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ReservationDAO;
import com.gaenolja.model.dto.Reservation;
import com.google.gson.Gson;

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
	public int countbydate(int hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate) {
		int count = 0;
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelnumber", hotelnumber);
			map.put("roomname", roomname);
			map.put("startdate", startdate);
			map.put("finishdate", finishdate);
			count = dao.countbydate(map);
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
	public boolean insert(Reservation reservation) {
		try {
			Object obj1 = reservation.getDog();
			Object obj2 = reservation.getPaid();
			Object obj3 = reservation.getRoomname();
			Gson gson = new Gson();
			String dog = gson.toJson(obj1);
			String paid = gson.toJson(obj2);
			String roomname = gson.toJson(obj3);
			reservation.setDog(dog);
			reservation.setPaid(paid);
			reservation.setRoomname(roomname);
			dao.insert(reservation);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Reservation reservation) {
		try {
			Object obj1 = reservation.getDog();
			Object obj2 = reservation.getPaid();
			Object obj3 = reservation.getRoomname();
			Gson gson = new Gson();
			String dog = gson.toJson(obj1);
			String paid = gson.toJson(obj2);
			String roomname = gson.toJson(obj3);
			reservation.setDog(dog);
			reservation.setPaid(paid);
			reservation.setRoomname(roomname);
			dao.update(reservation);
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
}
