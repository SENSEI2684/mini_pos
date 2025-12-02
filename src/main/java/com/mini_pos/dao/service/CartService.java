package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;

public interface CartService {
	public List<CartWithItems> showcartdata( );
	public boolean addToCart(Cart cart);
	public boolean resetCart();
	public boolean deleteItemsByCartId(Integer id);
	
}
