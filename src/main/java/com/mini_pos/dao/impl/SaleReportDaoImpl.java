package com.mini_pos.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.OrderDao;
import com.mini_pos.dao.SaleReportDao;
import com.mini_pos.dao.etinity.SaleReport;

public class SaleReportDaoImpl extends BaseDao implements SaleReportDao {

	
	@Override
	public List<SaleReport> getAllReport( ) {
		 List<SaleReport> list = new ArrayList<>();

		 String sql = "SELECT DATE(o.order_date) AS sale_date, " +
				    "       c.category_name AS category, " +
				    "       i.name AS item_name, " +
				    "       SUM(oi.quantity) AS total_quantity, " +
				    "       oi.price AS price, " +
				    "       SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id " +
				    "JOIN items i ON oi.item_id = i.id " +
				    "JOIN categories c ON i.category_id = c.id " +
				    "GROUP BY c.category_name, i.name, oi.price, sale_date " +
				    "ORDER BY sale_date, c.id, i.name";
				   
			try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//		    		// ✔ Convert LocalDateTime → LocalDate → java.sql.Date
//			        ps.setDate(1, java.sql.Date.valueOf(date.toLocalDate()));
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					String cat_name = rs.getString("category");
					String item_name = rs.getString("item_name");
					Integer qty = rs.getInt("total_quantity");
					Integer total_price = rs.getInt("total_price");
					LocalDate sale_date = rs.getDate("sale_date").toLocalDate();
					Integer sub_price = rs.getInt("price");
					SaleReport sr = new SaleReport(cat_name, item_name, qty, sub_price, total_price,
							sale_date.atStartOfDay());

					list.add(sr);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		public List<SaleReport> getDailyReport(LocalDateTime date) {
			List<SaleReport> list = new ArrayList<>();

    String sql = "SELECT DATE(o.order_date) AS sale_date, " +
            "       c.category_name AS category, " +
            "       i.name AS item_name, " +
            "       SUM(oi.quantity) AS total_quantity, " +
            "       oi.price AS price, " +
            "       SUM(oi.subtotal) AS total_price " +
            "FROM orders o " +
            "JOIN order_items oi ON o.id = oi.order_id " +
            "JOIN items i ON oi.item_id = i.id " +
            "JOIN categories c ON i.category_id = c.id " +
            "WHERE DATE(o.order_date) = ? " +
            "GROUP BY c.category_name, i.name, oi.price, sale_date " +
            "ORDER BY sale_date, c.id, i.name";

	try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

		ps.setDate(1, java.sql.Date.valueOf(date.toLocalDate()));
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			LocalDateTime saleDate = rs.getDate("sale_date").toLocalDate().atStartOfDay();
			String category = rs.getString("category");
			String itemName = rs.getString("item_name");
			int price = rs.getInt("price");
			int totalQty = rs.getInt("total_quantity");
			int totalPrice = rs.getInt("total_price");

			SaleReport report = new SaleReport(category, itemName, totalQty, price, totalPrice, saleDate);

			list.add(report);
		}

		rs.close();

	} catch (Exception e) {
		e.printStackTrace();
	}

	return list;
		}	

		
		@Override
		public List<SaleReport> getMonthlyReport(YearMonth month) {

		    List<SaleReport> list = new ArrayList<>();

		    String sql =
		        "SELECT DATE(o.order_date) AS sale_date, " +
		        "       c.category_name AS category, " +
		        "       i.name AS item_name, " +
		        "       SUM(oi.quantity) AS total_quantity, " +
		        "       oi.price AS price, " +
		        "       SUM(oi.subtotal) AS total_price " +
		        "FROM orders o " +
		        "JOIN order_items oi ON o.id = oi.order_id " +
		        "JOIN items i ON oi.item_id = i.id " +
		        "JOIN categories c ON i.category_id = c.id " +
		        "WHERE o.order_date >= ? " +
		        "  AND o.order_date < ? " +
		        "GROUP BY c.category_name, i.name, oi.price, sale_date " +
		        "ORDER BY sale_date, c.id, i.name";

		    LocalDate start = month.atDay(1);
		    LocalDate end = month.plusMonths(1).atDay(1);

		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setTimestamp(1, Timestamp.valueOf(start.atStartOfDay()));
		        ps.setTimestamp(2, Timestamp.valueOf(end.atStartOfDay()));

		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {
		            SaleReport report = new SaleReport(
		                rs.getString("category"),
		                rs.getString("item_name"),
		                rs.getInt("price"),
		                rs.getInt("total_quantity"),
		                rs.getInt("total_price"),
		                rs.getDate("sale_date").toLocalDate().atStartOfDay()
		            );
		            list.add(report);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return list;
		}
		

@Override
public List<SaleReport> getCategoryReport() {
	List<SaleReport> list = new ArrayList<>();

		 String sql = "SELECT DATE(o.order_date) AS sale_date, " +
				    "       c.category_name AS category, " +
				    "       SUM(oi.quantity) AS total_quantity, " +
				    "       SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id " +
				    "JOIN items i ON oi.item_id = i.id " +
				    "JOIN categories c ON i.category_id = c.id " +
				    "GROUP BY c.category_name, sale_date " +
				    "ORDER BY sale_date";
				   
		 
		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {	   

			        ResultSet rs = ps.executeQuery();
			        while (rs.next()) {
			            
			            String cat_name = rs.getString("category");	
			            String item_name = null;
			            Integer qty = rs.getInt("total_quantity");		            
			            Integer total_price = rs.getInt("total_price");
			            LocalDate  sale_date = rs.getDate("sale_date").toLocalDate();			 
			            
			            Integer sub_price = 0; 	
			            SaleReport sr = new SaleReport(cat_name,item_name,qty,sub_price,total_price,sale_date.atStartOfDay()); 

			            list.add(sr);			            
			        }
			        rs.close();
		    	}catch(Exception e) {
			        	e.printStackTrace();
			        }		    	
		    return list;
		    }
	

	@Override
	public List<SaleReport> getSummaryReport( ) {
		 List<SaleReport> list = new ArrayList<>();

		 String sql =  "SELECT DATE(o.order_date) AS sale_date, " +
				    "       SUM(oi.quantity) AS total_quantity, " +
				    "       SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id " +
				    "GROUP BY sale_date " +
				    "ORDER BY sale_date";

				   
		 
		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {	   

			        ResultSet rs = ps.executeQuery();
			        while (rs.next()) {
			            
			            String cat_name = null;		           
			            Integer qty = rs.getInt("total_quantity");		            
			            Integer total_price = rs.getInt("total_price");
			            LocalDate  sale_date = rs.getDate("sale_date").toLocalDate();			 
			            String item_name = null;
			            Integer sub_price ; 	
			            SaleReport sr = new SaleReport(cat_name,item_name,qty,0,total_price,sale_date.atStartOfDay()); 

			            list.add(sr);			            
			        }
			        rs.close();
		    	}catch(Exception e) {
			        	e.printStackTrace();
			        }		    	
		    return list;
		    }
	

	public List<SaleReport> getReportByInterval(LocalDate startDate, LocalDate endDate) {
	    List<SaleReport> list = new ArrayList<>();

	    String sql = "SELECT DATE(o.order_date) AS sale_date, " +
	                 "       c.category_name AS category, " +
	                 "       i.name AS item_name, " +
	                 "       SUM(oi.quantity) AS total_quantity, " +
	                 "       oi.price AS price, " +
	                 "       SUM(oi.subtotal) AS total_price " +
	                 "FROM orders o " +
	                 "JOIN order_items oi ON o.id = oi.order_id " +
	                 "JOIN items i ON oi.item_id = i.id " +
	                 "JOIN categories c ON i.category_id = c.id " +
	                 "WHERE DATE(o.order_date) BETWEEN ? AND ? " +
	                 "GROUP BY c.category_name, i.name, oi.price, sale_date " +
	                 "ORDER BY sale_date, c.id, i.name";

	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setDate(1, java.sql.Date.valueOf(startDate));
	        ps.setDate(2, java.sql.Date.valueOf(endDate));

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            SaleReport sr = new SaleReport(
	                rs.getString("category"),
	                rs.getString("item_name"),
	                rs.getInt("price"),
	                rs.getInt("total_quantity"),
	                rs.getInt("total_price"),
	                rs.getDate("sale_date").toLocalDate().atStartOfDay()
	            );
	            list.add(sr);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
}
	

		 






//if (!itemLevel) {
//    table.removeColumn(table.getColumnModel().getColumn(1)); // itemName
//    table.removeColumn(table.getColumnModel().getColumn(2)); // subPrice
//}
//OR set column width to zero:
//
//java
//Copy code
//TableColumn col = table.getColumnModel().getColumn(2);
//col.setMinWidth(0);
//col.setMaxWidth(0);
//col.setPreferredWidth(0);
