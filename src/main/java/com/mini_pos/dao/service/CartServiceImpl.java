package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;
import com.mini_pos.dao.impl.CartDaoImpl;
import com.mini_pos.dao.CartDao;

public class CartServiceImpl implements CartService {

	 private static CartServiceImpl instance;

	    private CartServiceImpl() {}

	    public static CartServiceImpl getInstance() {
	        if (instance == null) {
	            instance = new CartServiceImpl();
	        }
	        return instance;
	    }
	
	CartDao cartdao = new CartDaoImpl();

	@Override
	public boolean resetCart() {
		return this.cartdao.resetCart();
	}

	@Override
	public List<CartWithItems> showcartdata() {

		return cartdao.selectCartWithJoinItem();
	}

	@Override
	public boolean addToCart(Cart cart) {

		return this.cartdao.addtoCart(cart);
	}

	@Override
	public boolean deleteItemsByCartItem_Id(Integer id) {
		
		return this.cartdao.deleteItemsByCartItem_Id(id);
	}

}
