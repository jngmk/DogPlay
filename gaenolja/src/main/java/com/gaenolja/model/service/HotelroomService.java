package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hotelroom;

public interface HotelroomService {
	public List<Hotelroom> searchall();
	public Hotelroom search(int id);
	public List<Hotelroom> searchbyhotel(String hotelnumber);
	public Hotelroom searchbyhotelandroom(String roomname, String hotelnumber);
	public List<Hotelroom> searchbyprice(int price);
	public List<Hotelroom> searchbysize(int size);
	public boolean insert(Hotelroom room);
	public boolean update(Hotelroom room);
	public boolean delete(int id);
}
