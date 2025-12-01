package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;
import com.mini_pos.dao.impl.CartDaoImpl;
import com.mini_pos.dao.CartDao;

public class CartServiceImpl implements CartService {

	CartDao cartdao = new CartDaoImpl();


	
	@Override
	public boolean resetCart() {
		return this.cartdao.resetCart();
	}



	@Override
	public List<CartWithItems> showcartdata() {
		
		
		return cartdao.selectCartWithItemName();
	}


	@Override
	public boolean addToCart(Cart cart) {
		
		return this.cartdao.saveCart(cart);
	}

}
