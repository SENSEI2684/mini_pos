package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Order;

public interface OrderDao {
	public int saveOrder(int totalAmount);
}
