package com.mini_pos.dao;

import com.mini_pos.dao.etinity.OrderItem;

public interface OrderItemDao {
	
	public boolean saveOrderItem(OrderItem item);
}
