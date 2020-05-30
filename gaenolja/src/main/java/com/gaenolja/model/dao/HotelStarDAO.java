package com.gaenolja.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.HotelStar;

@Mapper
public interface HotelStarDAO {
	public List<HotelStar> searchall();
	public List<HotelStar> searchbydistance(HashMap<Object, Object> map);
	public HotelStar search(int hotelnumber);
	public List<HotelStar> searchbyuserid(String userid);
	public List<HotelStar> searchbyname(HashMap<Object, Object> map);
	public List<HotelStar> searchbyhashtag(HashMap<Object, Object> map);
}
