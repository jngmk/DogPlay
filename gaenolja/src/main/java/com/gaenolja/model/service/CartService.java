package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Cart;

public interface CartService {
	public List<Cart> searchall();
	public Cart search(int id);
	public Cart searchbyuser(String userid);
	public boolean insert(Cart cart);
	public boolean update(Cart cart);
	public boolean delete(int id);
}
