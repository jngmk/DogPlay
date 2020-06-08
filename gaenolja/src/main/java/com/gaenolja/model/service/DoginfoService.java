package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Doginfo;

public interface DoginfoService {
	public List<Doginfo> searchall();
	public List<Doginfo> searchbyuserid(String userid);
	public Doginfo search(int id);
	public int insert(Doginfo doginfo);
	public boolean update(Doginfo doginfo);
	public boolean delete(int id);
}
