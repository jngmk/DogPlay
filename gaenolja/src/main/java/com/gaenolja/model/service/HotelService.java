package com.gaenolja.model.service;

import com.gaenolja.model.dto.Hotel;

public interface HotelService {
	public void insert(Hotel hotel);
	public void update(Hotel hotel);
	public void delete(int hotelnumber);
}
