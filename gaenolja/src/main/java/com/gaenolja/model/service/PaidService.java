package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Paid;

public interface PaidService {
	public List<Paid> searchall();
	public Paid search(int id);
	public boolean insert(Paid paid);
	public boolean update(Paid paid);
	public boolean delete(int id);
}
