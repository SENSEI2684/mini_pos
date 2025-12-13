package com.mini_pos.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.OrderDao;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.Order;

public class OrderDaoImpl extends BaseDao implements OrderDao {

	@Override
	public int saveOrder(int totalAmount) {
		
			String sql = "INSERT INTO orders(total_amount) values(?);";
			try (Connection con = getConnection();
		             PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
																						

				stmt.setInt(1, totalAmount);

				stmt.executeUpdate();

				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					return rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
	}

}
