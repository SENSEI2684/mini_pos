package com.mini_pos.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.CartDao;
import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;
import com.mini_pos.dao.etinity.Items;



public class CartDaoImpl extends BaseDao implements CartDao{

	@Override
	public boolean addtoCart(Cart cart) {
		String sql = "INSERT INTO cart (user_id,item_id,quantity) VALUES(?,?,?);";
		try(PreparedStatement stmt = this.getconnection().prepareStatement(sql)){
			
			stmt.setInt(1, cart.user_id());
			stmt.setInt(2, cart.item_id());
			stmt.setInt(3, cart.quantity());
			
			int row = stmt.executeUpdate();
			
			return row > 0;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CartWithItems> selectCartWithJoinItem() {
		List<CartWithItems> cart = new ArrayList<CartWithItems>();
		String sql = "SELECT \r\n"
				+ "    c.id, \r\n"
				+ "    i.name, \r\n"
				+ "    i.price, \r\n"
				+ "    c.quantity, \r\n"
				+ "    i.price * c.quantity AS totalprice\r\n"
				+ "FROM cart c\r\n"
				+ "JOIN items i ON c.item_id = i.id;";
		
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk
																					// to sql
		
//			System.out.println("SQL" + sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name =rs.getString("name");
				Integer quantity = rs.getInt("quantity");
				Integer price = rs.getInt("price");
				Integer total_price = rs.getInt("totalprice");
				Cart c = new Cart(id,null,null,quantity,null);
				CartWithItems item = new CartWithItems(c,name,price,total_price);
				cart.add(item);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;
	}
	
	@Override
	public boolean deleteItemsByCartId(Integer id) {
		String sql = "Delete from cart where id = ?";
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk
																					// to sql

			stmt.setInt(1, id); // level

			int row = stmt.executeUpdate();// this must use .executeUpdate bec we make insert changes

			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean resetCart() {
		String sql = "TRUNCATE TABLE cart";
	    try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {
	        stmt.executeUpdate(); // no need to check return value
	        return true; // if no exception, truncate succeeded
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
		
	
	public static void main(String [] args) {
//		CartDao cdao = new CartDaoImpl();
//		List<CartWithItems> list = cdao.selectCartWithItemName();
//		list.forEach(System.out::println);
//		
//		System.out.println(cdao.resetCart());
//		list.forEach(System.out::println);
	}

	
	

}
