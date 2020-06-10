package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.HotelLikes;
import com.gaenolja.model.dto.Likes;

@Mapper
public interface LikesDAO {
	public List<Likes> searchall();
	public List<Likes> searchbyuserid(String userid);
	public List<Likes> searchbyhotelnumber(String hotelnumber);
	public List<HotelLikes> searchhotelbyuserid(String visitor);
	public int check(Likes likes);
	public void insert(Likes likes);
	public void delete(Likes likes);
}
