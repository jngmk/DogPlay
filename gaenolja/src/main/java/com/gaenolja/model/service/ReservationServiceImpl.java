package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ReservationDAO;
import com.gaenolja.model.dao.UserDAO;
import com.gaenolja.model.dto.Reservation;
import com.gaenolja.model.dto.User;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private ReservationDAO dao;
	
	@Autowired
	private UserDAO userdao;
	
	@Override
	public List<Reservation> searchall(){
		try {
			List<Reservation> reservation = dao.searchall();
			for (Reservation res:reservation) {
				String userid = res.getUserid();
				User user = userdao.search(userid);
				res.setPhone(user.getPhone());
			}
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
			for (Reservation res:reservation) {
				User user = userdao.search(userid);
				res.setPhone(user.getPhone());
			}
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reservation> searchbyhotel(String hotelnumber){
		try {
			List<Reservation> reservation = dao.searchbyhotel(hotelnumber);
			for (Reservation res:reservation) {
				String userid = res.getUserid();
				User user = userdao.search(userid);
				res.setPhone(user.getPhone());
			}
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reservation> searchbyhotelandroom(String hotelnumber, String roomname){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelnumber", hotelnumber);
			map.put("roomname", roomname);
			List<Reservation> reservation = dao.searchbyhotelandroom(map);
			for (Reservation res:reservation) {
				String userid = res.getUserid();
				User user = userdao.search(userid);
				res.setPhone(user.getPhone());
			}
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int countbyhotelandroom(String hotelnumber, String roomname) {
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
	public int countbydate(String hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate) {
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
	public List<Reservation> searchbydate(String hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelnumber", hotelnumber);
			map.put("roomname", roomname);
			map.put("startdate", startdate);
			map.put("finishdate", finishdate);
			List<Reservation> reservation = dao.searchbydate(map);
			for (Reservation res:reservation) {
				String userid = res.getUserid();
				User user = userdao.search(userid);
				res.setPhone(user.getPhone());
			}
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Reservation search(int id) {
		try {
			Reservation reservation = dao.search(id);
			String userid = reservation.getUserid();
			User user = userdao.search(userid);
			reservation.setPhone(user.getPhone());
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reservation> searchbypaidid(int paidid) {
		try {
			List<Reservation> reservation = dao.searchbypaidid(paidid);
			for (Reservation res:reservation) {
				String userid = res.getUserid();
				User user = userdao.search(userid);
				res.setPhone(user.getPhone());
			}
			return reservation;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Reservation reservation) {
		try {
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
