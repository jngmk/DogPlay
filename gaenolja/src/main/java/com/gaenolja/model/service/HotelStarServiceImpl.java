package com.gaenolja.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HashtagDAO;
import com.gaenolja.model.dao.HotelHashDAO;
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
	
	@Autowired
	private HotelHashDAO hotelhash;
	
	@Override
	public List<HotelStar> searchall(){
		try {
			List<HotelStar> hotelstar = dao.searchall();
			for (HotelStar star:hotelstar) {
				String hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				star.setMinprice(roomdao.minprice(hotelnumber));
				List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
				star.setHashid(hash);
				List<String> hashtag = new ArrayList<String>();
				for (int h:hash) {
					String tag = hashtagdao.search(h).getName();
					hashtag.add(tag);
				}
				star.setHashtag(hashtag);

				if (!Objects.isNull(star.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> keys = new ArrayList<>(jsonObject.keySet());
					for (String key:keys) {
						List<String> details = new ArrayList<>();
						details.add(key);
						details.add(jsonObject.get(key));
						detailarray.add(details);
					}
					star.setDetail(detailarray);
				}
				star.setPicture(picturedao.searchmain(hotelnumber));					
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
				String hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				star.setMinprice(roomdao.minprice(hotelnumber));
				List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
				star.setHashid(hash);
				List<String> hashtag = new ArrayList<String>();
				for (int h:hash) {
					String tag = hashtagdao.search(h).getName();
					hashtag.add(tag);
				}
				star.setHashtag(hashtag);
				if (!Objects.isNull(star.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> keys = new ArrayList<>(jsonObject.keySet());
					for (String key:keys) {
						List<String> details = new ArrayList<>();
						details.add(key);
						details.add(jsonObject.get(key));
						detailarray.add(details);
					}
					star.setDetail(detailarray);
				}
				star.setPicture(picturedao.searchmain(hotelnumber));					
			}
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<HotelStar> searchbyhashtag(int hashtages){
		List<HotelStar> hotelstar = new ArrayList<HotelStar>();
		try {
			List<String> hotel = hotelhash.searchbyhash(hashtages);
			for (String hotelnumber:hotel) {
				HotelStar star = dao.search(hotelnumber);
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				star.setMinprice(roomdao.minprice(hotelnumber));
				List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
				star.setHashid(hash);
				List<String> hashtag = new ArrayList<String>();
				for (int h:hash) {
					String tag = hashtagdao.search(h).getName();
					hashtag.add(tag);
				}
				star.setHashtag(hashtag);
				if (!Objects.isNull(star.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> keys = new ArrayList<>(jsonObject.keySet());
					for (String key:keys) {
						List<String> details = new ArrayList<>();
						details.add(key);
						details.add(jsonObject.get(key));
						detailarray.add(details);
					}
					star.setDetail(detailarray);
				}
				star.setPicture(picturedao.searchmain(hotelnumber));					
				hotelstar.add(star);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return hotelstar;
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
				String hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				star.setMinprice(roomdao.minprice(hotelnumber));
				List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
				star.setHashid(hash);
				List<String> hashtag = new ArrayList<String>();
				for (int h:hash) {
					String tag = hashtagdao.search(h).getName();
					hashtag.add(tag);
				}
				star.setHashtag(hashtag);
				if (!Objects.isNull(star.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> keys = new ArrayList<>(jsonObject.keySet());
					for (String key:keys) {
						List<String> details = new ArrayList<>();
						details.add(key);
						details.add(jsonObject.get(key));
						detailarray.add(details);
					}
					star.setDetail(detailarray);
				}
				star.setPicture(picturedao.searchmain(hotelnumber));					
			}
			return hotel;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public HotelStar search(String hotelnumber) {
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			hotelstar.setMinprice(roomdao.minprice(hotelnumber));
			List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
			hotelstar.setHashid(hash);
			List<String> hashtag = new ArrayList<String>();
			for (int h:hash) {
				String tag = hashtagdao.search(h).getName();
				hashtag.add(tag);
			}
			hotelstar.setHashtag(hashtag);
			if (!Objects.isNull(hotelstar.getDetail())) {
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(hotelstar.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> keys = new ArrayList<>(jsonObject.keySet());
				for (String key:keys) {
					List<String> details = new ArrayList<>();
					details.add(key);
					details.add(jsonObject.get(key));
					detailarray.add(details);
				}
				hotelstar.setDetail(detailarray);
			}
			hotelstar.setPicture(picturedao.searchmain(hotelnumber));					
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
				String hotelnumber = star.getHotelnumber();
				star.setCountreview(reviewdao.countreview(hotelnumber));
				star.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
				star.setMinprice(roomdao.minprice(hotelnumber));
				List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
				star.setHashid(hash);
				List<String> hashtag = new ArrayList<String>();
				for (int h:hash) {
					String tag = hashtagdao.search(h).getName();
					hashtag.add(tag);
				}
				star.setHashtag(hashtag);
				if (!Objects.isNull(star.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(star.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> keys = new ArrayList<>(jsonObject.keySet());
					for (String key:keys) {
						List<String> details = new ArrayList<>();
						details.add(key);
						details.add(jsonObject.get(key));
						detailarray.add(details);
					}
					star.setDetail(detailarray);
				}
				star.setPicture(picturedao.searchmain(hotelnumber));					
			}
			return hotelstar;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public HashMap<Object, Object> hoteldetail(String hotelnumber){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			hotelstar.setMinprice(roomdao.minprice(hotelnumber));
			List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
			hotelstar.setHashid(hash);
			List<String> hashtag = new ArrayList<String>();
			for (int h:hash) {
				String tag = hashtagdao.search(h).getName();
				hashtag.add(tag);
			}
			hotelstar.setHashtag(hashtag);
			if (!Objects.isNull(hotelstar.getDetail())) {
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(hotelstar.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> keys = new ArrayList<>(jsonObject.keySet());
				for (String key:keys) {
					List<String> details = new ArrayList<>();
					details.add(key);
					details.add(jsonObject.get(key));
					detailarray.add(details);
				}
				hotelstar.setDetail(detailarray);				
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
	
	@Override
	public HashMap<Object, Object> hoteldetailbydate(String hotelnumber, String roomname, LocalDateTime startdate, LocalDateTime finishdate){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			HotelStar hotelstar = dao.search(hotelnumber);
			hotelstar.setCountreview(reviewdao.countreview(hotelnumber));
			hotelstar.setCountstar(reviewdao.countbyhotelnumber(hotelnumber));
			hotelstar.setMinprice(roomdao.minprice(hotelnumber));
			List<Integer> hash = hotelhash.searchbyhotel(hotelnumber);
			hotelstar.setHashid(hash);
			List<String> hashtag = new ArrayList<String>();
			for (int h:hash) {
				String tag = hashtagdao.search(h).getName();
				hashtag.add(tag);
			}
			hotelstar.setHashtag(hashtag);
			if (!Objects.isNull(hotelstar.getDetail())) {
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(hotelstar.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> keys = new ArrayList<>(jsonObject.keySet());
				for (String key:keys) {
					List<String> details = new ArrayList<>();
					details.add(key);
					details.add(jsonObject.get(key));
					detailarray.add(details);
				}
				hotelstar.setDetail(detailarray);	
			}
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
