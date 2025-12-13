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

		 String sql = 
				    "(SELECT DATE(o.order_date) AS sale_date, " +
				    "       c.category_name AS category, " +
				    "       i.name AS item_name, " +
				    "       SUM(oi.quantity) AS total_quantity, " +
				    "       oi.price AS price, " +
				    "       SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id " +
				    "JOIN items i ON oi.item_id = i.id " +
				    "JOIN categories c ON i.category_id = c.id " +
				    "GROUP BY DATE(o.order_date), c.category_name, i.name, oi.price) " +
				    "UNION ALL " +
				    "(SELECT 'TOTAL' AS sale_date, NULL AS category, NULL AS item_name, " +
				    "       SUM(oi.quantity) AS total_quantity, NULL AS price, SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id) " +
				    "ORDER BY sale_date, category, item_name";
				   

				   
			try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//		    		// ✔ Convert LocalDateTime → LocalDate → java.sql.Date
//			        ps.setDate(1, java.sql.Date.valueOf(date.toLocalDate()));
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					String saleDateStr = rs.getString("sale_date");
		        	LocalDate saleDate = null;
		        	 if (!"TOTAL".equals(saleDateStr)) {
			                saleDate = LocalDate.parse(saleDateStr); // convert to LocalDate
			            }
					
					String cat_name = rs.getString("category");
					String item_name = rs.getString("item_name");
					Integer qty = rs.getInt("total_quantity");
					Integer total_price = rs.getInt("total_price");
					Integer sub_price = rs.getInt("price");
					SaleReport sr = new SaleReport(cat_name, item_name, qty, sub_price, total_price,
							saleDate);

					list.add(sr);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

//		@Override
//		public List<SaleReport> getDailyReport(LocalDateTime date) {
//			List<SaleReport> list = new ArrayList<>();
//
//    String sql = "SELECT DATE(o.order_date) AS sale_date, " +
//            "       c.category_name AS category, " +
//            "       i.name AS item_name, " +
//            "       SUM(oi.quantity) AS total_quantity, " +
//            "       oi.price AS price, " +
//            "       SUM(oi.subtotal) AS total_price " +
//            "FROM orders o " +
//            "JOIN order_items oi ON o.id = oi.order_id " +
//            "JOIN items i ON oi.item_id = i.id " +
//            "JOIN categories c ON i.category_id = c.id " +
//            "WHERE DATE(o.order_date) = ? " +
//            "GROUP BY c.category_name, i.name, oi.price, sale_date " +
//            "ORDER BY sale_date, c.id, i.name";
//
//	try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//
//		ps.setDate(1, java.sql.Date.valueOf(date.toLocalDate()));
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next()) {
//
//			LocalDateTime saleDate = rs.getDate("sale_date").toLocalDate().atStartOfDay();
//			String category = rs.getString("category");
//			String itemName = rs.getString("item_name");
//			int price = rs.getInt("price");
//			int totalQty = rs.getInt("total_quantity");
//			int totalPrice = rs.getInt("total_price");
//
//			SaleReport report = new SaleReport(category, itemName, totalQty, price, totalPrice, saleDate);
//
//			list.add(report);
//		}
//
//		rs.close();
//
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//
//	return list;
//		}	
//
//		
//		@Override
//		public List<SaleReport> getMonthlyReport(YearMonth month) {
//
//		    List<SaleReport> list = new ArrayList<>();
//
//		    String sql =
//		        "SELECT DATE(o.order_date) AS sale_date, " +
//		        "       c.category_name AS category, " +
//		        "       i.name AS item_name, " +
//		        "       SUM(oi.quantity) AS total_quantity, " +
//		        "       oi.price AS price, " +
//		        "       SUM(oi.subtotal) AS total_price " +
//		        "FROM orders o " +
//		        "JOIN order_items oi ON o.id = oi.order_id " +
//		        "JOIN items i ON oi.item_id = i.id " +
//		        "JOIN categories c ON i.category_id = c.id " +
//		        "WHERE o.order_date >= ? " +
//		        "  AND o.order_date < ? " +
//		        "GROUP BY c.category_name, i.name, oi.price, sale_date " +
//		        "ORDER BY sale_date, c.id, i.name";
//
//		    LocalDate start = month.atDay(1);
//		    LocalDate end = month.plusMonths(1).atDay(1);
//
//		    try (Connection con = getConnection();
//		         PreparedStatement ps = con.prepareStatement(sql)) {
//
//		        ps.setTimestamp(1, Timestamp.valueOf(start.atStartOfDay()));
//		        ps.setTimestamp(2, Timestamp.valueOf(end.atStartOfDay()));
//
//		        ResultSet rs = ps.executeQuery();
//
//		        while (rs.next()) {
//		            SaleReport report = new SaleReport(
//		                rs.getString("category"),
//		                rs.getString("item_name"),
//		                rs.getInt("price"),
//		                rs.getInt("total_quantity"),
//		                rs.getInt("total_price"),
//		                rs.getDate("sale_date").toLocalDate().atStartOfDay()
//		            );
//		            list.add(report);
//		        }
//
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		    }
//
//		    return list;
//		}
		

@Override
public List<SaleReport> getCategoryReport() {
	List<SaleReport> list = new ArrayList<>();

	String sql = 
		    "(SELECT DATE(o.order_date) AS sale_date, " +
		    "       c.category_name AS category, " +
		    "       SUM(oi.quantity) AS total_quantity, " +
		    "       SUM(oi.subtotal) AS total_price " +
		    "FROM orders o " +
		    "JOIN order_items oi ON o.id = oi.order_id " +
		    "JOIN items i ON oi.item_id = i.id " +
		    "JOIN categories c ON i.category_id = c.id " +
		    "GROUP BY c.category_name, DATE(o.order_date)) " +
		    "UNION ALL " +
		    "(SELECT 'TOTAL' AS sale_date, NULL AS category, " +
		    "       SUM(oi.quantity) AS total_quantity, " +
		    "       SUM(oi.subtotal) AS total_price " +
		    "FROM orders o " +
		    "JOIN order_items oi ON o.id = oi.order_id) " +
		    "ORDER BY sale_date;";
				    
				   
		 
		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {	   

			        ResultSet rs = ps.executeQuery();
			        while (rs.next()) {
			        	String saleDateStr = rs.getString("sale_date");
			        	LocalDate saleDate = null;
			        	 if (!"TOTAL".equals(saleDateStr)) {
				                saleDate = LocalDate.parse(saleDateStr); // convert to LocalDate
				            }
			        	
			            String cat_name = rs.getString("category");	
			            String item_name = null;
			            Integer qty = rs.getInt("total_quantity");		            
			            Integer total_price = rs.getInt("total_price");
			            		 
			            
			            Integer sub_price = 0; 	
			            SaleReport sr = new SaleReport(cat_name,item_name,qty,sub_price,total_price,saleDate); 

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

		 String sql = 
				    "(SELECT DATE(o.order_date) AS sale_date, " +
				    "       SUM(oi.quantity) AS total_quantity, " +
				    "       SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id " +
				    "GROUP BY DATE(o.order_date)) " +
				    "UNION ALL " +
				    "(SELECT 'TOTAL' AS sale_date, " +
				    "       SUM(oi.quantity) AS total_quantity, " +
				    "       SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id) " +
				    "ORDER BY sale_date;";
		
				    	   
		 
		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {	   

			        ResultSet rs = ps.executeQuery();
			        while (rs.next()) {
			        	String saleDateStr = rs.getString("sale_date");
			        	LocalDate saleDate = null;
			        	 if (!"TOTAL".equals(saleDateStr)) {
				                saleDate = LocalDate.parse(saleDateStr); // convert to LocalDate
				            }
			            String cat_name = null;		           
			            Integer qty = rs.getInt("total_quantity");		            
			            Integer total_price = rs.getInt("total_price");
			           
			            String item_name = null;
			            Integer sub_price ; 	
			            SaleReport sr = new SaleReport(cat_name,item_name,qty,0,total_price,saleDate); 

			            list.add(sr);			            
			        }
			        rs.close();
		    	}catch(Exception e) {
			        	e.printStackTrace();
			        }		    	
		    return list;
		    }
	

	public List<SaleReport> getAllItemsReportByInterval(LocalDate startDate, LocalDate endDate) {//you can search daily monthly and any time interval with this
	    List<SaleReport> list = new ArrayList<>();

	    String sql = 
	    	    "(SELECT DATE(o.order_date) AS sale_date, " +
	    	    "        c.category_name AS category, " +
	    	    "        i.name AS item_name, " +
	    	    "        SUM(oi.quantity) AS total_quantity, " +
	    	    "        oi.price AS price, " +
	    	    "        SUM(oi.subtotal) AS total_price " +
	    	    " FROM orders o " +
	    	    " JOIN order_items oi ON o.id = oi.order_id " +
	    	    " JOIN items i ON oi.item_id = i.id " +
	    	    " JOIN categories c ON i.category_id = c.id " +
	    	    " WHERE o.order_date >= ? " +
	    	    "   AND o.order_date < ? " +
	    	    " GROUP BY c.category_name, i.name, oi.price, DATE(o.order_date)) " +
	    	    "UNION ALL " +
	    	    "(SELECT 'TOTAL' AS sale_date, NULL AS category, NULL AS item_name, " +
	    	    "        SUM(oi.quantity) AS total_quantity, NULL AS price, SUM(oi.subtotal) AS total_price " +
	    	    " FROM orders o " +
	    	    " JOIN order_items oi ON o.id = oi.order_id " +
	    	    " WHERE o.order_date >= ? " +
	    	    "   AND o.order_date < ?) " +
	    	    "ORDER BY sale_date, category, item_name";


	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	    	LocalDateTime startDateTime = startDate.atStartOfDay();
	    	LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
//	        ps.setDate(1, java.sql.Date.valueOf(startDateTime));
//	        ps.setDate(2, java.sql.Date.valueOf(endDateTime));
	        ps.setTimestamp(1, Timestamp.valueOf(startDateTime));
	        ps.setTimestamp(2, Timestamp.valueOf(endDateTime));
	        ps.setTimestamp(3, Timestamp.valueOf(startDateTime));
	        ps.setTimestamp(4, Timestamp.valueOf(endDateTime));

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	String saleDateStr = rs.getString("sale_date"); // read as string

	            LocalDate saleDate = null;
	            if (!"TOTAL".equals(saleDateStr)) {
	                saleDate = LocalDate.parse(saleDateStr); // convert to LocalDate
	            }

	            String cat_name = rs.getString("category");
				String item_name = rs.getString("item_name");
				Integer qty = rs.getInt("total_quantity");
				Integer total_price = rs.getInt("total_price");
				
				Integer sub_price = rs.getInt("price");
				SaleReport sr = new SaleReport(cat_name, item_name, qty, sub_price, total_price,
						saleDate);

				list.add(sr);	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	@Override
	public List<SaleReport> getCategoryReportByInterval(LocalDate startDate, LocalDate endDate) {
		
			List<SaleReport> list = new ArrayList<>();

			String sql = 
				    "(SELECT DATE(o.order_date) AS sale_date, " +
				    "        c.category_name AS category, " +
				    "        SUM(oi.quantity) AS total_quantity, " +
				    "        SUM(oi.subtotal) AS total_price " +
				    " FROM orders o " +
				    " JOIN order_items oi ON o.id = oi.order_id " +
				    " JOIN items i ON oi.item_id = i.id " +
				    " JOIN categories c ON i.category_id = c.id " +
				    " WHERE o.order_date >= ? " +
				    "   AND o.order_date < ? " +
				    " GROUP BY DATE(o.order_date), c.category_name) " +
				    "UNION ALL " +
				    "(SELECT 'TOTAL' AS sale_date, NULL AS category, " +
				    "        SUM(oi.quantity) AS total_quantity, " +
				    "        SUM(oi.subtotal) AS total_price " +
				    " FROM orders o " +
				    " JOIN order_items oi ON o.id = oi.order_id " +
				    " WHERE o.order_date >= ? " +
				    "   AND o.order_date < ?) " +
				    "ORDER BY sale_date, category";

						   
				 
				    try (Connection con = getConnection();
				         PreparedStatement ps = con.prepareStatement(sql)) {	
				    	
				    	LocalDateTime startDateTime = startDate.atStartOfDay();
				    	LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
				        ps.setTimestamp(1, Timestamp.valueOf(startDateTime));
				        ps.setTimestamp(2, Timestamp.valueOf(endDateTime));
				        ps.setTimestamp(3, Timestamp.valueOf(startDateTime));
				        ps.setTimestamp(4, Timestamp.valueOf(endDateTime));

					        ResultSet rs = ps.executeQuery();
					        while (rs.next()) {
					        	
						        	String saleDateStr = rs.getString("sale_date"); // read as string

						            LocalDate saleDate = null;
						            if (!"TOTAL".equals(saleDateStr)) {
						                saleDate = LocalDate.parse(saleDateStr); // convert to LocalDate
						            }
					            
					            String cat_name = rs.getString("category");	
					            String item_name = null;
					            Integer qty = rs.getInt("total_quantity");		            
					            Integer total_price = rs.getInt("total_price");
					            			 
					            
					            Integer sub_price = 0; 	
					            SaleReport sr = new SaleReport(cat_name,item_name,qty,sub_price,total_price,saleDate); 

					            list.add(sr);			            
					        }
					        rs.close();
				    	}catch(Exception e) {
					        	e.printStackTrace();
					        }		    	
				    return list;
				    }

	@Override
	public List<SaleReport> getSummaryReportByInterval(LocalDate startDate, LocalDate endDate) {
		 List<SaleReport> list = new ArrayList<>();

		 String sql = 
				    "SELECT " +
				    "    DATE(o.order_date) AS sale_date, " +
				    "    SUM(oi.quantity) AS total_quantity, " +
				    "    SUM(oi.subtotal) AS total_price " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id " +
				    "WHERE o.order_date >= ? " +
				    "  AND o.order_date < ? " +
				    "GROUP BY DATE(o.order_date) " +
				    "UNION ALL " +
				    "SELECT " +
				    "    'TOTAL', " +
				    "    SUM(oi.quantity), " +
				    "    SUM(oi.subtotal) " +
				    "FROM orders o " +
				    "JOIN order_items oi ON o.id = oi.order_id " +
				    "WHERE o.order_date >= ? " +
				    "  AND o.order_date < ? " +   // <- added space here
				    "ORDER BY sale_date";

				   
		 
		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {	   

		    	LocalDateTime startDateTime = startDate.atStartOfDay();
		    	LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
		        ps.setTimestamp(1, Timestamp.valueOf(startDateTime));
		        ps.setTimestamp(2, Timestamp.valueOf(endDateTime));
		        ps.setTimestamp(3, Timestamp.valueOf(startDateTime));
		        ps.setTimestamp(4, Timestamp.valueOf(endDateTime));
		    	
			        ResultSet rs = ps.executeQuery();
			        while (rs.next()) {
			        	String saleDateStr = rs.getString("sale_date"); // read as string

			            LocalDate saleDate = null;
			            if (!"TOTAL".equals(saleDateStr)) {
			                saleDate = LocalDate.parse(saleDateStr); // convert to LocalDate
			            }
			            
			            String cat_name = null;		           
			            Integer qty = rs.getInt("total_quantity");		            
			            Integer total_price = rs.getInt("total_price");
			  		 
			            String item_name = null;
			            Integer sub_price ; 	
			            SaleReport sr = new SaleReport(cat_name,item_name,qty,0,total_price,saleDate); 

			            list.add(sr);			            
			        }
			        rs.close();
		    	}catch(Exception e) {
			        	e.printStackTrace();
			        }		    	
		    return list;
		    }
}
	
		 






