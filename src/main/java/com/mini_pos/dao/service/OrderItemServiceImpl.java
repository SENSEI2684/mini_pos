package com.mini_pos.dao.service;

import com.mini_pos.dao.impl.OrderItemDaoImpl;
import com.mini_pos.dao.OrderItemDao;
import com.mini_pos.dao.etinity.OrderItem;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

public class OrderItemServiceImpl implements OrderItemService {

	OrderItemDao orderItemDao = new OrderItemDaoImpl();
	

		

	@Override
	public void saveOrderItem(OrderItem item) throws ValidationException, DaoException {

		if( item.orderId()<0
				||item.itemId()<0
				||item.quantity()<0
				||item.price()<0
				) {
			throw new ValidationException("The datas are not valid");
		}
		
		try {
			boolean saveOK = orderItemDao.saveOrderItem(item);
			if(!saveOK) {
				throw new DaoException("Failed to insert data into database", null);
			}
		}catch(Exception e) {
			throw new DaoException("Error while saving Orderitem", e);
		}
	}
	
	}


