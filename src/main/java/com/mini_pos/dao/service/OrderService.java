package com.mini_pos.dao.service;

import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

public interface OrderService {
	public int saveOrder(int totalAmount) throws ValidationException, DaoException;
}
