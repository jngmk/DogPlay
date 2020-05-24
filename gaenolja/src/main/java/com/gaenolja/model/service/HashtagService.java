package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hashtag;

public interface HashtagService {
	public List<Hashtag> searchall();
	public Hashtag search(String id);
	public void insert(Hashtag hashtag);
	public void update(Hashtag hashtag);
	public void delete(String id);
}
