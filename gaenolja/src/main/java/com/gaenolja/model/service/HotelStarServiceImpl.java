package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HashtagDAO;
import com.gaenolja.model.dao.HotelStarDAO;
import com.gaenolja.model.dao.HotelpictureDAO;
import com.gaenolja.model.dao.HotelroomDAO;
import com.gaenolja.model.dao.ReviewDAO;
import com.gaenolja.model.dto.HotelStar;
import com.gaenolja.model.dto.Hotelroom;
import com.google.gson.Gson;

@Service
public class HotelStarServiceImpl implements HotelStarService{
	
	@Autowired
	private HotelStarDAO dao;
	
	@Autowired
	private HotelpictureDAO picturedao;
	
	@Autowired
	private HotelroomDAO roomdao;
	
	@Autowired
	private ReviewDAO reviewdao;
	
	@Autowired
	private HashtagDAO hashtagdao;
	
	@Autowired
	private ReservationService reservation;
	
	@Override
	public List<HotelStar> searchall(){
		try {
			List<HotelStar> hotelstar = dao.searchall();
			for (HotelStar star:hotelstar) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				String hashid = star.getHashid();
				List<String> list = new ArrayList<String>();
				for (int idx=0;idx<hashid.length();idx++) {
					if (hashtagdao.search(Character.toString(hashid.charAt(idx))) != null) {
						list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());						
					}
				}
				star.setMinprice(roomdao.minprice(hotelnumber));			
				star.setHashtag(list);

				if (!Objects.isNull(star.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> key = new ArrayList<>(jsonObject.keySet());
					List<String> value = new ArrayList<>(jsonObject.values());
					detailarray.add(key);
					detailarray.add(value);
					star.setDetail(detailarray);	
				}
			}
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<HotelStar> searchbyuserid(String userid){
		try {
			List<HotelStar> hotelstar = dao.searchbyuserid(userid);
			for (HotelStar star:hotelstar) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				String hashid = star.getHashid();
				List<String> list = new ArrayList<String>();
				for (int idx=0;idx<hashid.length();idx++) {
					list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());
				}
				star.setMinprice(roomdao.minprice(hotelnumber));					
				star.setHashtag(list);
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> key = new ArrayList<>(jsonObject.keySet());
				List<String> value = new ArrayList<>(jsonObject.values());
				detailarray.add(key);
				detailarray.add(value);
				star.setDetail(detailarray);
			}
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<HotelStar> searchbydistance(double latitude, double longitude, int distance){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("latitude", latitude);
			map.put("longitude", longitude);
			map.put("distance", distance);
			List<HotelStar> hotel = dao.searchbydistance(map);
			for (HotelStar star:hotel) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				String hashid = star.getHashid();
				List<String> list = new ArrayList<String>();
				for (int idx=0;idx<hashid.length();idx++) {
					list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());
				}
				star.setHashtag(list);
				star.setMinprice(roomdao.minprice(hotelnumber));	
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> key = new ArrayList<>(jsonObject.keySet());
				List<String> value = new ArrayList<>(jsonObject.values());
				detailarray.add(key);
				detailarray.add(value);
				star.setDetail(detailarray);
			}
			return hotel;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public HotelStar search(int hotelnumber) {
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			String hashid = hotelstar.getHashid();
			List<String> list = new ArrayList<String>();
			for (int idx=0;idx<hashid.length();idx++) {
				list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());
			}
			hotelstar.setHashtag(list);
			hotelstar.setMinprice(roomdao.minprice(hotelnumber));
			Gson gson = new Gson();
			HashMap<String, String> jsonObject = gson.fromJson(hotelstar.getDetail().toString(), HashMap.class);
			List<List<String>> detailarray = new ArrayList<List<String>>();
			List<String> key = new ArrayList<>(jsonObject.keySet());
			List<String> value = new ArrayList<>(jsonObject.values());
			detailarray.add(key);
			detailarray.add(value);
			hotelstar.setDetail(detailarray);
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<HotelStar> searchbyname(String hotelname, double latitude, double longitude, int distance){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelname", hotelname);
			map.put("latitude", latitude);
			map.put("longitude", longitude);
			map.put("distance", distance);
			List<HotelStar> hotelstar = dao.searchbyname(map);
			for (HotelStar star:hotelstar) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				String hashid = star.getHashid();
				List<String> list = new ArrayList<String>();
				for (int idx=0;idx<hashid.length();idx++) {
					list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());
				}
				star.setMinprice(roomdao.minprice(hotelnumber));
				star.setHashtag(list);
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> key = new ArrayList<>(jsonObject.keySet());
				List<String> value = new ArrayList<>(jsonObject.values());
				detailarray.add(key);
				detailarray.add(value);
				star.setDetail(detailarray);
			}
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<HotelStar> searchbyhashtag(String hashtag, double latitude, double longitude, int distance){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hashtag", hashtag);
			map.put("latitude", latitude);
			map.put("longitude", longitude);
			map.put("distance", distance);
			List<HotelStar> hotelstar = dao.searchbyhashtag(map);
			for (HotelStar star:hotelstar) {
				int hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				String hashid = star.getHashid();
				List<String> list = new ArrayList<String>();
				for (int idx=0;idx<hashid.length();idx++) {
					list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());
				}
				star.setMinprice(roomdao.minprice(hotelnumber));
				star.setHashtag(list);
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> key = new ArrayList<>(jsonObject.keySet());
				List<String> value = new ArrayList<>(jsonObject.values());
				detailarray.add(key);
				detailarray.add(value);
				star.setDetail(detailarray);
			}
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public HashMap<Object, Object> hoteldetail(int hotelnumber){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			String hashid = hotelstar.getHashid();
			List<String> list = new ArrayList<String>();
			for (int idx=0;idx<hashid.length();idx++) {
				list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());
			}
			hotelstar.setMinprice(roomdao.minprice(hotelnumber));
			hotelstar.setHashtag(list);
			Gson gson = new Gson();
			HashMap<String, String> jsonObject = gson.fromJson(hotelstar.getDetail().toString(), HashMap.class);
			List<List<String>> detailarray = new ArrayList<List<String>>();
			List<String> key = new ArrayList<>(jsonObject.keySet());
			List<String> value = new ArrayList<>(jsonObject.values());
			detailarray.add(key);
			detailarray.add(value);
			hotelstar.setDetail(detailarray);
			map.put("HotelStar", hotelstar);
			map.put("HotelPicture", picturedao.searchbyhotel(hotelnumber));
			map.put("HotelRoom", roomdao.searchbyhotel(hotelnumber));
			map.put("review", reviewdao.reviewbytime(hotelnumber));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public HashMap<Object, Object> hoteldetailbydate(int hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			String hashid = hotelstar.getHashid();
			List<String> list = new ArrayList<String>();
			for (int idx=0;idx<hashid.length();idx++) {
				list.add(hashtagdao.search(Character.toString(hashid.charAt(idx))).getName());
			}
			hotelstar.setMinprice(roomdao.minprice(hotelnumber));
			hotelstar.setHashtag(list);
			Gson gson = new Gson();
			HashMap<String, String> jsonObject = gson.fromJson(hotelstar.getDetail().toString(), HashMap.class);
			List<List<String>> detailarray = new ArrayList<List<String>>();
			List<String> key = new ArrayList<>(jsonObject.keySet());
			List<String> value = new ArrayList<>(jsonObject.values());
			detailarray.add(key);
			detailarray.add(value);
			hotelstar.setDetail(detailarray);
			List<Hotelroom> hotelroom = roomdao.searchbyhotel(hotelnumber);
			for(Hotelroom room:hotelroom) {
				int cnt = room.getCount() - reservation.countbydate(hotelnumber, roomname, startdate, finishdate);
				room.setCount(cnt);
			}
			map.put("HotelStar", hotelstar);
			map.put("HotelPicture", picturedao.searchbyhotel(hotelnumber));
			map.put("HotelRoom", roomdao.searchbyhotel(hotelnumber));
			map.put("review", reviewdao.reviewbytime(hotelnumber));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
