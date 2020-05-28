package com.gaenolja.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Hotelroom;

@Mapper
public interface HotelroomDAO {
	public List<Hotelroom> searchall();
	public Hotelroom search(int id);
	public List<Hotelroom> searchbyhotel(int hotelnumber);
	public Hotelroom searchbyhotelandroom(HashMap<Object, Object> map);
	public List<Hotelroom> searchbyprice(int price);
	public List<Hotelroom> searchbysize(int size);
	public void insert(Hotelroom room);
	public void update(Hotelroom room);
	public void delete(int id);
	public int minprice(int hotelnumber);
}
