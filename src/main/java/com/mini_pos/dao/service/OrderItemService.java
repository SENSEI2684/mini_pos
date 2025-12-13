package com.mini_pos.dao.service;

import com.mini_pos.dao.etinity.OrderItem;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

public interface OrderItemService {
	public void saveOrderItem(OrderItem item) throws ValidationException, DaoException;
}
