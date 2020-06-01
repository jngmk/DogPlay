package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Doginfo;

@Mapper
public interface DoginfoDAO {
	public List<Doginfo> searchall();
	public List<Doginfo> searchbyuserid(String userid);
	public Doginfo search(int id);
	public void insert(Doginfo doginfo);
	public void update(Doginfo doginfo);
	public void delete(int id);
}
