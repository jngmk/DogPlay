package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Likes;

public interface LikesService {
	public List<Likes> searchall();
	public List<Likes> searchbyuserid(String userid);
	public List<Likes> searchbyhotelnumber(int hotelnumber);
	public boolean insert(Likes likes);
	public boolean delete(Likes likes);
}
