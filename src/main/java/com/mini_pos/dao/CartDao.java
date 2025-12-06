package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;

public interface CartDao {
	public boolean addtoCart(Cart cart);
	List <CartWithItems> selectCartWithJoinItem();
	public boolean resetCart();
	public boolean deleteItemsByCartItem_Id(Integer id);
	
}
