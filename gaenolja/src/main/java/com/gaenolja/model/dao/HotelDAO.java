package com.gaenolja.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelDAO {
	public void insert();
	public void update();
	public void delete();
}
