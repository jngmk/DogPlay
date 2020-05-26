package com.gaenolja.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Hotel;

@Mapper
public interface HotelDAO {
	public List<Hotel> searchall();
	public Hotel search(int hotelnumber);
	public List<Hotel> searchbyname(String hotelname);
	public List<Hotel> searchbyhashtag(String hashtag);
	public List<Hotel> searchbydistance(HashMap<Object, Object> map);
	public void insert(Hotel hotel);
	public void update(Hotel hotel);
	public void delete(int hotelnumber);
}
