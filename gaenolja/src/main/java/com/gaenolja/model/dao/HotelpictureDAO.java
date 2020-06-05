package com.gaenolja.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Hotelpicture;

@Mapper
public interface HotelpictureDAO {
	public List<Hotelpicture> searchall();
	public List<Hotelpicture> searchbyhotel(String hotelnumber);
	public List<Hotelpicture> searchbyhotelandname(HashMap<Object, Object> map);
	public Hotelpicture search(int id);
	public void insert(Hotelpicture hotelpicture);
	public void update(Hotelpicture hotelpicture);
	public void delete(int id);
	public String searchmain(String hotelnumber);
}
