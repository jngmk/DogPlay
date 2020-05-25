package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.HotelStar;

@Mapper
public interface HotelStarDAO {
	public List<HotelStar> searchall();
	public HotelStar search(int hotelnumber);
	public List<HotelStar> searchbyname(String hotelname);
	public List<HotelStar> searchbyhashtag(String hashtag);
}
