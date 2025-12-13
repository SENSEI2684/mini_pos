package com.mini_pos.dao.service;

import com.mini_pos.dao.impl.OrderDaoImpl;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;
import com.mini_pos.dao.OrderDao;
import com.mini_pos.dao.etinity.Order;

public class OrderServiceImpl implements OrderService {

	OrderDao orderDao = new OrderDaoImpl();
	
	@Override
	public int saveOrder(int totalAmount) throws ValidationException, DaoException{
		
		if( totalAmount < 0) {
			throw new ValidationException("The TotalAmount is invalid Amount");
		}
		
		try {
			int saveOK = orderDao.saveOrder(totalAmount);
			if(saveOK <= 0) {
				throw new DaoException("Failed to insert data into database", null);
			}
			return saveOK;
		}catch(Exception e) {
			throw new DaoException("Error while saving Order", e);
		}
	}

}
