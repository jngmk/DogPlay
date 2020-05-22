package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Response;
import com.gaenolja.model.dto.User;

@Mapper
public interface ResponseDAO {
	public List<Response> searchall();
	public Response searchbyhotelnumber(int hotelnumber);
	public Response searchbyuserid(String userid);
	public Response search(int id);
	public void insert(Response response);
	public void update(Response response);
	public void delete(int id);
}
