package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.gaenolja.model.dto.HotelStar;

public interface HotelStarService {
	public List<HotelStar> searchall();
	public List<HotelStar> searchbydistance(double latitude, double longitude, int distance);
	public List<HotelStar> searchbyuserid(String userid);
	public HotelStar search(String hotelnumber);
	public List<HotelStar> searchbyname(String hotelname, double latitude, double longitude, int distance);
	public HashMap<Object, Object> hoteldetail(String hotelnumber);
	public HashMap<Object, Object> hoteldetailbydate(String hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate);
}
