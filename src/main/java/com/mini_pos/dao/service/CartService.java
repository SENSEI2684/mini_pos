package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

public interface CartService {
	public boolean addToCart(Cart cart);
	public void resetCart() throws DaoException;
	public void deleteItemsByCartItem_Id(Integer id) throws ValidationException, DaoException;
	public List<CartWithItems> showcartdata () throws DaoException;
	
}
