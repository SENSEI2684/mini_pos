package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;

public interface CartDao {
	public boolean saveCart(Cart cart);
	List <CartWithItems> selectCartWithItemName();
	public boolean resetCart();
	
}
