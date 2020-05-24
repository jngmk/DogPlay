package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hotelroom;

public interface HotelroomService {
	public List<Hotelroom> searchall();
	public Hotelroom search(int id);
	public List<Hotelroom> searchbyhotel(int hotelnumber);
	public Hotelroom searchbyhotelandroom(String roomname, int hotelnumber);
	public List<Hotelroom> searchbyprice(int price);
	public List<Hotelroom> searchbysize(int size);
	public void insert(Hotelroom room);
	public void update(Hotelroom room);
	public void delete(int id);
}
