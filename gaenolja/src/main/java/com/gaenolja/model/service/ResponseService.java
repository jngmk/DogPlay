package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Response;

public interface ResponseService {
	public List<Response> searchall();
	public List<Response> searchbyuserid(String userid);
	public Response search(int reivewid);
	public void insert(Response response);
	public void update(Response response);
	public void delete(int id);
}
