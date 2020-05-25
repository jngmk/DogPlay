package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Doginfo;

public interface DoginfoService {
	public List<Doginfo> searchall();
	public List<Doginfo> searchbyuserid(String userid);
	public Doginfo search(int id);
	public void insert(Doginfo doginfo);
	public void update(Doginfo doginfo);
	public void delete(int id);
}
