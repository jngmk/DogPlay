package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.gaenolja.model.dto.HotelStar;

public interface HotelStarService {
	public List<HotelStar> searchall();
	public List<HotelStar> searchbydistance(double latitude, double longitude, int distance);
	public HotelStar search(int hotelnumber);
	public List<HotelStar> searchbyname(String hotelname, double latitude, double longitude, int distance);
	public List<HotelStar> searchbyhashtag(String hashtag, double latitude, double longitude, int distance);
	public HashMap<Object, Object> hoteldetail(int hotelnumber);
	public HashMap<Object, Object> hoteldetailbydate(int hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate);
}
