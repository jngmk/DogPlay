package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Cart;

public interface CartService {
	public List<Cart> searchall();
	public Cart search(int id);
	public Cart searchbyuser(String userid);
	public void insert(Cart cart);
	public void update(Cart cart);
	public void delete(int id);
}
