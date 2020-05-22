package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Likes;

@Mapper
public interface LikesDAO {
	public List<Likes> searchall();
	public Likes searchbyuserid(String userid);
	public Likes searchbyhotelnumber(int hotelnumber);
	public void insert(Likes likes);
	public void delete(Likes likes);
}
