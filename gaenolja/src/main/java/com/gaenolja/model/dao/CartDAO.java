package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Cart;

@Mapper
public interface CartDAO {
	public List<Cart> searchall();
	public Cart search(int id);
	public List<Cart> searchbyuser(String userid);
	public int totalprice(String userid);
	public void insert(Cart cart);
	public void update(Cart cart);
	public void delete(int id);
	public void deletebyuserid(String userid);
}
