package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.HotelHash;

@Mapper
public interface HotelHashDAO {
	public List<HotelHash> searchall();
	public HotelHash search(int id);
	public List<Integer> searchbyhotel(String hotelnumber);
	public List<String> searchbyhash(int hashtag);
	public void insert(HotelHash hotelhash);
	public void update(HotelHash hotelhash);
	public void delete(int id);
}
