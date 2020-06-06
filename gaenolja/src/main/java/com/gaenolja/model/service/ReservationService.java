package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.List;

import com.gaenolja.model.dto.Reservation;

public interface ReservationService {
	public List<Reservation> searchall();
	public List<Reservation> searchbyuserid(String userid);
	public List<Reservation> searchbyhotel(String hotelnumber);
	public List<Reservation> searchbyhotelandroom(String hotelnumber, String roomname);
	public int countbyhotelandroom(String hotelnumber, String roomname);
	public int countbydate(String hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate);
	public List<Reservation> searchbydate(String hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate);
	public Reservation search(int id);
	public List<Reservation> searchbypaidid(int paidid);
	public boolean insert(Reservation reservation);
	public boolean update(Reservation reservation);
	public boolean delete(int id);
}
