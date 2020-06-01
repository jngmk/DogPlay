package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hashtag;
import com.gaenolja.model.dto.HotelHash;

public interface HashtagService {
	public List<Hashtag> searchall();
	public Hashtag search(int id);
	public boolean insert(Hashtag hashtag);
	public boolean update(Hashtag hashtag);
	public boolean delete(int id);
	public List<HotelHash> searchallhotelhash();
	public HotelHash searchhotelhash(int id);
	public List<Integer> searchhotelhashbyhotel(String hotelnumber);
	public List<String> searchhotelhashbyhash(int hashtag);
	public boolean inserthotelhash(HotelHash hotelhash);
	public boolean updatehotelhash(HotelHash hotelhash);
	public boolean deletehotelhash(int id);
}
