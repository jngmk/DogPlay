package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Hotelpicture;

public interface HotelpictureService {
	public List<Hotelpicture> searchall();
	public List<Hotelpicture> searchbyhotel(String hotelnumber);
	public List<Hotelpicture> searchbyhotelandname(String hotelnumber, String name);
	public Hotelpicture search(int id);
	public boolean insert(Hotelpicture hotelpicture);
	public boolean update(Hotelpicture hotelpicture);
	public boolean delete(int id);
}
