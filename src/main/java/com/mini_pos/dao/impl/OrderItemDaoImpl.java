package com.mini_pos.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.OrderDao;
import com.mini_pos.dao.OrderItemDao;
import com.mini_pos.dao.etinity.Order;
import com.mini_pos.dao.etinity.OrderItem;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {

	

	@Override
	public boolean saveOrderItem(OrderItem item) {
		String sql = "INSERT INTO order_items(order_id,item_id,quantity,price) values(?,?,?,?);";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {// this Statement is created for talk
																					// to sql

			stmt.setInt(1, item.orderId());
			stmt.setInt(2, item.itemId());
			stmt.setInt(3, item.quantity());
			stmt.setInt(4, item.price());
			

			int row = stmt.executeUpdate();// this must use .executeUpdate bec we make insert changes

			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
