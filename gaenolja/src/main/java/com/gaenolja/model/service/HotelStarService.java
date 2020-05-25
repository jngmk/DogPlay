package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.HotelStar;

public interface HotelStarService {
	public List<HotelStar> searchall();
	public HotelStar search(int hotelnumber);
	public List<HotelStar> searchbyname(String hotelname);
	public List<HotelStar> searchbyhashtag(String hashtag);
}
