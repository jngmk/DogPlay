package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.HotelLikes;
import com.gaenolja.model.dto.Likes;

public interface LikesService {
	public List<Likes> searchall();
	public List<Likes> searchbyuserid(String userid);
	public List<Likes> searchbyhotelnumber(String hotelnumber);
	public List<HotelLikes> searchhotelbyuserid(String visitor);
	public boolean insert(Likes likes);
	public boolean delete(String userid, String hotelnumber);
}
