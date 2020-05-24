package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hotel;

public interface HotelService {
	public List<Hotel> searchall();
	public Hotel search(int hotelnumber);
	public List<Hotel> searchbyname(String hotelname);
	public List<Hotel> searchbyhashtag(String hashtag);
	public void insert(Hotel hotel);
	public void update(Hotel hotel);
	public void delete(int hotelnumber);
}
