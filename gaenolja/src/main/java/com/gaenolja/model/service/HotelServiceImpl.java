package com.gaenolja.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelDAO;
import com.gaenolja.model.dto.Hotel;
import com.google.gson.Gson;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	HotelDAO dao;
	
	@Override
	public List<Hotel> searchall(){
		try {
			List<Hotel> hotel = dao.searchall();
			for (Hotel h:hotel) {
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(h.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> key = new ArrayList<>(jsonObject.keySet());
				List<String> value = new ArrayList<>(jsonObject.values());
				detailarray.add(key);
				detailarray.add(value);
				h.setDetail(detailarray);
			}
			return hotel;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Hotel search(String hotelnumber) {
		try {
			Hotel hotel = dao.search(hotelnumber);
			Gson gson = new Gson();
			HashMap<String, String> jsonObject = gson.fromJson(hotel.getDetail().toString(), HashMap.class);
			List<List<String>> detailarray = new ArrayList<List<String>>();
			List<String> key = new ArrayList<>(jsonObject.keySet());
			List<String> value = new ArrayList<>(jsonObject.values());
			detailarray.add(key);
			detailarray.add(value);
			hotel.setDetail(detailarray);
			return hotel;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotel> searchbyname(String hotelname){
		try {
			List<Hotel> hotel = dao.searchbyname(hotelname);
			for (Hotel h:hotel) {
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(h.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> key = new ArrayList<>(jsonObject.keySet());
				List<String> value = new ArrayList<>(jsonObject.values());
				detailarray.add(key);
				detailarray.add(value);
				h.setDetail(detailarray);
			}
			return hotel;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public List<Hotel> searchbydistance(double latitude, double longitude, int distance){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("latitude", latitude);
			map.put("longitude", longitude);
			map.put("distance", distance);
			List<Hotel> hotel = dao.searchbydistance(map);
			for (Hotel h:hotel) {
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(h.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> key = new ArrayList<>(jsonObject.keySet());
				List<String> value = new ArrayList<>(jsonObject.values());
				detailarray.add(key);
				detailarray.add(value);
				h.setDetail(detailarray);
			}
			return hotel;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Hotel hotel) {
		try {
			Object obj = hotel.getDetail();
			Gson gson = new Gson();
			String json = gson.toJson(obj);
			hotel.setDetail(json);
			dao.insert(hotel);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean update(Hotel hotel) {
		try {
			Object obj = hotel.getDetail();
			Gson gson = new Gson();
			String json = gson.toJson(obj);
			hotel.setDetail(json);
			dao.update(hotel);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean delete(String hotelnumber) {
		try {
			dao.delete(hotelnumber);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
}
