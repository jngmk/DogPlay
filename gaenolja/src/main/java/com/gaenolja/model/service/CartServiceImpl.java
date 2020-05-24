package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.CartDAO;
import com.gaenolja.model.dto.Cart;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartDAO dao;
	
	@Override
	public List<Cart> searchall(){
		try {
			List<Cart> cart = dao.searchall();
			return cart;	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Cart search(int id) {
		try {
			Cart cart = dao.search(id);
			return cart;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Cart searchbyuser(String userid) {
		try {
			Cart cart = dao.searchbyuser(userid);
			return cart;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Cart cart) {
		try {
			dao.insert(cart);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Cart cart) {
		try {
			dao.update(cart);
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
