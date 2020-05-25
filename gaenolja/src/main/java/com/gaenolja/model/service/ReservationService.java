package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.List;

import com.gaenolja.model.dto.Reservation;

public interface ReservationService {
	public List<Reservation> searchall();
	public List<Reservation> searchbyuserid(String userid);
	public List<Reservation> searchbyhotel(int hotelnumber);
	public List<Reservation> searchbyhotelandroom(int hotelnumber, String roomname);
	public int countbyhotelandroom(int hotelnumber, String roomname);
	public int countbydate(int hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate);
	public Reservation search(int id);
	public boolean insert(Reservation reservation);
	public boolean update(Reservation reservation);
	public boolean delete(int id);
}
