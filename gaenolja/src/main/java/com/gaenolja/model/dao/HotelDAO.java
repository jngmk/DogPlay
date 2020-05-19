package com.gaenolja.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Hotel;

@Mapper
public interface HotelDAO {
	public void insert(Hotel hotel);
	public void update(Hotel hotel);
	public void delete(int hotelnumber);
}
