package com.mini_pos.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.ItemsDao;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;
import com.mysql.cj.jdbc.Blob;



public class ItemsDaoImpl extends BaseDao implements ItemsDao {

	@Override
	public List<Items> getAllItems() {
		List<Items> items = new ArrayList<>();
		
		String sql = "Select * from items;";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String item_code = rs.getString("item_code");
				String name = rs.getString("name");
				Integer price = rs.getInt("price");
				Integer quantity = rs.getInt("quantity");
				String photo = rs.getString("photo");
				Integer category_id = rs.getInt("category_id");
				LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
				Items item = new Items(id,item_code, name, price,quantity, photo,category_id, created_at);
				items.add(item);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	@Override
	public List<ItemsWithCategories> getAllItemsAndCategoryName() {
		List<ItemsWithCategories> itemWCat = new ArrayList<>();
		String sql = "SELECT i.id ,i.item_code, i.name, i.price, i.quantity, i.photo, c.category_name, i.created_at FROM items i JOIN categories c on i.category_id = c.id ORDER BY id ;";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {
			
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String item_code = rs.getString("item_code");
				String name = rs.getString("name");
				Integer price = rs.getInt("price");
				Integer quantity = rs.getInt("quantity");
				String photo = rs.getString("photo");
				
//				Blob image = rs.getBlob("photo");
//				String path = "/mini_pos_program/src/main/resources/static/images";
//				byte[] imageByte = image.getBytes(1, (int)image.length());
//				FileOutputStream fout = new FileOutputStream(path);
//				fout.write(imageByte);
//				ImageIcon icon = new ImageIcon(imageByte);
//				lblIcon.setIcon(icon);
				
				
//				Integer category_name = rs.getInt("category_id");
				LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
				Items item = new Items(id,item_code, name, price,quantity, photo,null, created_at);

				String cat_name = rs.getString("category_name");
				ItemsWithCategories item_cat = new ItemsWithCategories(item, cat_name);
				itemWCat.add(item_cat);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemWCat;
	}

//	@Override
//	public Movie getMovieByCode(String code) {
//		Movie movie = null;
//	String sql = "Select * from course where code = '"+code+"';";
//		try(Statement stmt = this.getconnection().createStatement()){//this Statement is created for talk to sql

//			System.out.println("SQL" + sql);
//			ResultSet rs = stmt.executeQuery(sql);
//			
//			while(rs.next()) {
//				String code2 = rs.getString("code");
//				String name = rs.getString("name");
//				String level = rs.getString("level");
//				Integer duration = rs.getInt("duration");
//				Integer fee = rs.getInt("fee");
//				String available = rs.getString("available");
//				
//				movie = new Movie(code2,name,level,duration,fee,available);
//				
//			}
//			rs.close();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return movie;
//	}//this code has problem called SQL injection 
	// dont make .createStatement() with String concut >> use PrepareStatement and
	// .prepareStatement() instead

	@Override
	public List<Items> getItemsByCategoryCode(Integer id) {
		List<Items> items = new ArrayList<Items>();
		String sql = "Select * from items where category_id = ?;";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {// this Statement is created for talk
																					// to sql
			stmt.setInt(1, id); // set parameter now ? become 1
			System.out.println("SQL" + sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Integer ids = rs.getInt("id");
				String item_code = rs.getString("item_code");
				String name = rs.getString("name");
				Integer price = rs.getInt("price");
				Integer quantity = rs.getInt("quantity");
				String photo = rs.getString("photo");
				Integer category_id = rs.getInt("category_id");
				LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
				Items item = new Items(ids,item_code, name, price,quantity, photo,category_id, created_at);
				items.add(item);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}// in this code there is no injection problem

//	public String generateNextItemCode() {
//		String nextCode = "ITM-0001"; // default if table is empty
//		String sql = "SELECT item_code FROM items ORDER BY id DESC LIMIT 1";
//
//		try (Statement stmt = this.getconnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//
//			if (rs.next()) {
//				String lastCode = rs.getString("item_code"); // e.g., ITM-0020
//				int number = Integer.parseInt(lastCode.substring(4)); // remove 'ITM-' prefix
//				number++;
//				nextCode = String.format("ITM-%04d", number); // ITM-0021
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return nextCode;
//	}

	@Override
	public boolean saveItems(Items item,String path2) {

//		String itemCode = generateNextItemCode();

		String sql = "insert into items(item_code,name,price,quantity,photo,category_id) values(?,?,?,?,?,?);";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {// this Statement is created for talk
																					// to sql

			stmt.setString(1, item.item_code());
			stmt.setString(2, item.name());
			stmt.setInt(3, item.price());
			stmt.setInt(4, item.quantity());
			stmt.setString(5, path2);
//			InputStream ins = new FileInputStream(new File(path2));
//			stmt.setBlob(5, ins);
			stmt.setInt(6, item.category_id());

			int row = stmt.executeUpdate();// this must use .executeUpdate bec we make insert changes

			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateItems(Integer price, String item_code) {
		String sql = "Update items set price = ? where item_code =?;";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {// this Statement is created for talk
																					// to sql

			stmt.setInt(1, price); // for price
			stmt.setString(2, item_code); // for item_code

			int row = stmt.executeUpdate();// this must use .executeUpdate bec we make insert changes

			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteItemsByItemCode(String code) {
		String sql = "Delete from items where item_code = ?";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {// this Statement is created for talk
																					// to sql

			stmt.setString(1, code); // level

			int row = stmt.executeUpdate();// this must use .executeUpdate bec we make insert changes

			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<ItemsWithCategories> getAllItemsAndCategoryNameByCat_Id(Integer ids) {
		List<ItemsWithCategories> itemWCat = new ArrayList<>();
		String sql = "select i.*, c.category_name from items i join categories c on i.category_id = c.id where i.category_id = ?;";
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, ids);
			System.out.println("SQL" + sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String item_code = rs.getString("item_code");
				String name = rs.getString("name");
				Integer price = rs.getInt("price");
				Integer quantity = rs.getInt("quantity");
				String photo = rs.getString("photo");
				Integer category_id = rs.getInt("category_id");
				LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
				Items item = new Items(id,item_code, name, price,quantity, photo,category_id, created_at);

				String cat_name = rs.getString("category_name");
				ItemsWithCategories item_cat = new ItemsWithCategories(item, cat_name);
				itemWCat.add(item_cat);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemWCat;
	}
	
	@Override
	public List<Items> getItemsEachPage(int page, int itemsPerPage) {
		 List<Items> items = new ArrayList<>();

	        int offset = (page - 1) * itemsPerPage;
	        String sql = "SELECT * FROM items ORDER BY id LIMIT ? OFFSET ?;";
	        try (Connection con = getConnection();
		             PreparedStatement stmt = con.prepareStatement(sql)) {

	            stmt.setInt(1, itemsPerPage);
	            stmt.setInt(2, offset);
	            ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Integer id = rs.getInt("id");
					String item_code = rs.getString("item_code");
					String name = rs.getString("name");
					Integer price = rs.getInt("price");
					Integer quantity = rs.getInt("quantity");
					String photo = rs.getString("photo");
					Integer category_id = rs.getInt("category_id");
					LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
					Items item = new Items(id,item_code, name, price,quantity, photo,category_id, created_at);
					items.add(item);
	            }
				rs.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return items;
	}

	@Override
	public List<Items> searchItemsWithName(String name) {
        List<Items> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE name LIKE ? ORDER BY id;";
		 try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {

	            stmt.setString(1, "%" + name + "%");	
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	            	Integer id = rs.getInt("id");
					String item_code = rs.getString("item_code");
					String names = rs.getString("name");
					Integer price = rs.getInt("price");
					Integer quantity = rs.getInt("quantity");
					String photo = rs.getString("photo");
					Integer category_id = rs.getInt("category_id");
					LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
					Items item = new Items(id,item_code, names, price,quantity, photo,category_id, created_at);
					items.add(item);
	            }
	            rs.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return items;
	}


	@Override
	public int getTotalItems() {
		String sql = "SELECT COUNT(*) FROM items;";
        try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {       	
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	return rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
	
	@Override
	public boolean isItemExist(String item_code,String name) { // this code is need for (Register)Business Logic Request
		String sql = "SELECT COUNT(*) FROM items WHERE item_code = ? AND name = ?";
		System.out.println("SQL " + sql);
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {// this Statement is created for talk to sql

			stmt.setString(1, item_code);
			stmt.setString(2, name);	
			ResultSet res = stmt.executeQuery();
	
			if (res.next()) {
	            int count = res.getInt(1);  // get the COUNT(*) value
	            return count > 0;           // true if name exists for another id
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		}
	

	public static void main(String[] args) {
		ItemsDao itemsdao = new ItemsDaoImpl();
//		List<Items> items = itemsdao.getAllItems();
//			items.forEach(System.out::println);// normal read
//		
//			System.out.println();

//		List<Items> item1 = itemsdao.getItemsByCategoryCode("2");
//		item1.forEach(System.out::println); // where clause read
//		
//		Items ItemsInsert = new Items(null,null,"Asus ROG Phone 7","Gaming phone with high refresh rate and big battery",36_000_000,1,null);//if there is autoincrement id ues 0L
//		itemsdao.saveItems(ItemsInsert);
//		List<Items> item2 = itemsdao.getAllItems();
//		item2.forEach(System.out::println);

//		itemsdao.updateItems(230_000,"ITM-0017");
//		List<Items> movies = itemsdao.getAllItems();
//		movies.forEach(System.out::println);

//		moviedao.deleteMovieByCode("C006");
//		List<Movie> movies = moviedao.getAllMovie();
//		movies.forEach(System.out::println);

//		List<ItemsWithCategories> items = itemsdao.getItemsBaseOnCategoryID(3);
//		items.forEach(System.out::println);

	}

}

