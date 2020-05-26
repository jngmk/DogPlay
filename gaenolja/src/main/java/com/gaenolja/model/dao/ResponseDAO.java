package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Response;

@Mapper
public interface ResponseDAO {
	public List<Response> searchall();
	public List<Response> searchbyhotelnumber(int hotelnumber);
	public List<Response> searchbyuserid(String userid);
	public Response search(int id);
	public Response searchbyreview(int reviewid);
	public void insert(Response response);
	public void update(Response response);
	public void delete(int id);
}
