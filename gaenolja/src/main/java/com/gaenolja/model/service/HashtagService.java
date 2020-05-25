package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hashtag;

public interface HashtagService {
	public List<Hashtag> searchall();
	public Hashtag search(String id);
	public boolean insert(Hashtag hashtag);
	public boolean update(Hashtag hashtag);
	public boolean delete(String id);
}
