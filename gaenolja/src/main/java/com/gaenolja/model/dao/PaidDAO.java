package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Paid;

@Mapper
public interface PaidDAO {
	public List<Paid> searchall();
	public Paid search(int id);
	public void insert(Paid paid);
	public void update(Paid paid);
	public void delete(int id);
	public Paid searchbyaid(String tid);
}
