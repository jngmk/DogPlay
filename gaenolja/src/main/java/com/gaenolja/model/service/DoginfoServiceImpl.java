package com.gaenolja.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.DoginfoDAO;
import com.gaenolja.model.dto.Doginfo;
import com.google.gson.Gson;

@Service
public class DoginfoServiceImpl implements DoginfoService {
	
	@Autowired
	DoginfoDAO dao;
	
	@Override
	public List<Doginfo> searchall(){
		try {
			List<Doginfo> doginfo = dao.searchall();
			for (Doginfo dog:doginfo) {
				if (!Objects.isNull(dog.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(dog.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> keys = new ArrayList<>(jsonObject.keySet());
					for (String key:keys) {
						List<String> details = new ArrayList<>();
						details.add(key);
						details.add(jsonObject.get(key));
						detailarray.add(details);
					}
					dog.setDetail(detailarray);
				}
			}
			return doginfo;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Doginfo> searchbyuserid(String userid) {
		try {
			List<Doginfo> doginfo = dao.searchbyuserid(userid);
			for (Doginfo dog:doginfo) {
				if (!Objects.isNull(dog.getDetail())) {
					Gson gson = new Gson();
					HashMap<String, String> jsonObject = gson.fromJson(dog.getDetail().toString(), HashMap.class);
					List<List<String>> detailarray = new ArrayList<List<String>>();
					List<String> keys = new ArrayList<>(jsonObject.keySet());
					for (String key:keys) {
						List<String> details = new ArrayList<>();
						details.add(key);
						details.add(jsonObject.get(key));
						detailarray.add(details);
					}
					dog.setDetail(detailarray);
				}
			}
			return doginfo;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Doginfo search(int id) {
		try {
			Doginfo doginfo = dao.search(id);
			if (!Objects.isNull(doginfo.getDetail())) {
				Gson gson = new Gson();
				HashMap<String, String> jsonObject = gson.fromJson(doginfo.getDetail().toString(), HashMap.class);
				List<List<String>> detailarray = new ArrayList<List<String>>();
				List<String> keys = new ArrayList<>(jsonObject.keySet());
				for (String key:keys) {
					List<String> details = new ArrayList<>();
					details.add(key);
					details.add(jsonObject.get(key));
					detailarray.add(details);
				}
				doginfo.setDetail(detailarray);
			}
			return doginfo;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public boolean insert(Doginfo doginfo) {
		try {
			Object obj = doginfo.getDetail();
			Gson gson = new Gson();
			String json = gson.toJson(obj);
			doginfo.setDetail(json);
			dao.insert(doginfo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean update(Doginfo doginfo) {
		try {
			Object obj = doginfo.getDetail();
			Gson gson = new Gson();
			String json = gson.toJson(obj);
			doginfo.setDetail(json);
			dao.insert(doginfo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};

	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
}
