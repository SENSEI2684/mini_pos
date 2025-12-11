package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;
import com.mini_pos.dao.impl.CartDaoImpl;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;
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
	public void resetCart() throws DaoException {
		
		 try {
		         cartdao.resetCart();
		    } catch (Exception e) {
		        throw new DaoException("Failed to reset Cart Items from database", e);
		    }
		
	}

	@Override
	public List<CartWithItems> showcartdata() throws DaoException {

		 try {
		        return cartdao.selectCartWithJoinItem();
		    } catch (Exception e) {
		        throw new DaoException("Failed to fetch Cart Items from database", e);
		    }
		
	}

	@Override
	public boolean addToCart(Cart cart) {
		
		return this.cartdao.addtoCart(cart);
	}

	@Override
	public void deleteItemsByCartItem_Id(Integer id)throws ValidationException, DaoException {
		
		if(id == null || id < 0) {
			throw new ValidationException("Please Select Items!");
		}
		try {
			boolean deleteOk = cartdao.deleteItemsByCartItem_Id(id);
			if(!deleteOk) {
				throw new DaoException("Failed to delete cart row .", null);
			}
		} catch (Exception e) {
	        throw new DaoException("Failed to delete Cart row from database", e);
	    }
		
	}

	



	

}
