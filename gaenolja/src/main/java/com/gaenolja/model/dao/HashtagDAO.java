package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Hashtag;

@Mapper
public interface HashtagDAO {
	public List<Hashtag> searchall();
	public Hashtag search(int id);
	public void insert(Hashtag hashtag);
	public void update(Hashtag hashtag);
	public void delete(int id);
}
