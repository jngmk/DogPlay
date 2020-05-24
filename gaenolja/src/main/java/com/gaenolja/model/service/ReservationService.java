package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Reservation;

public interface ReservationService {
	public List<Reservation> searchall();
	public List<Reservation> searchbyuserid(String userid);
	public List<Reservation> searchbyhotel(int hotelnumber);
	public List<Reservation> searchbyhotelandroom(int hotelnumber, String roomname);
	public int countbyhotelandroom(int hotelnumber, String roomname);
	public Reservation search(int id);
	public void insert(Reservation reservation);
	public void update(Reservation reservation);
	public void delete(int id);
}
