package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hotel;

public interface HotelService {
	public List<Hotel> searchall();
	public Hotel search(int hotelnumber);
	public List<Hotel> searchbyname(String hotelname);
	public List<Hotel> searchbyhashtag(String hashtag);
	public List<Hotel> searchbydistance(double latitude, double longitude, int distance);
	public boolean insert(Hotel hotel);
	public boolean update(Hotel hotel);
	public boolean delete(int hotelnumber);
}
