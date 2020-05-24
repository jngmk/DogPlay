package com.gaenolja.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HotelpictureDAO;
import com.gaenolja.model.dto.Hotelpicture;

@Service
public class HotelpictureServiceImpl implements HotelpictureService{
	
	@Autowired
	private HotelpictureDAO dao;
	
	@Override
	public List<Hotelpicture> searchall(){
		try {
			List<Hotelpicture> picture = dao.searchall();
			return picture;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotelpicture> searchbyhotel(int hotelnumber){
		try {
			List<Hotelpicture> picture = dao.searchbyhotel(hotelnumber);
			return picture;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Hotelpicture> searchbyhotelandname(int hotelnumber, String name){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelnumber", hotelnumber);
			map.put("name", name);
			List<Hotelpicture> picture = dao.searchbyhotelandname(map);
			return picture;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Hotelpicture search(int id) {
		try {
			Hotelpicture picture = dao.search(id);
			return picture;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Hotelpicture hotelpicture) {
		try {
			dao.insert(hotelpicture);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Hotelpicture hotelpicture) {
		try {
			dao.update(hotelpicture);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(int id) {
		try {
			dao.delete(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
