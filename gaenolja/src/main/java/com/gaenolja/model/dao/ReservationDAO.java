package com.gaenolja.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Reservation;

@Mapper
public interface ReservationDAO {
	public List<Reservation> searchall();
	public List<Reservation> searchbyuserid(String userid);
	public List<Reservation> searchbyhotel(int hotelnumber);
	public List<Reservation> searchbyhotelandroom(HashMap<Object, Object> map);
	public int countbyhotelandroom(HashMap<Object, Object> map);
	public int countbydate(HashMap<Object, Object> map);
	public Reservation search(int id);
	public void insert(Reservation reservation);
	public void update(Reservation reservation);
	public void delete(int id);
}
