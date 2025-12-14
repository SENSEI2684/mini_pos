package com.mini_pos.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.CategoriesDao;
import com.mini_pos.dao.etinity.Categories;



public class CategoriesDaoImpl extends BaseDao implements CategoriesDao{

	@Override
	public List<Categories> getAllCategories() {
		
		List<Categories> cates = new ArrayList<Categories>();
		
		try(Connection con = getConnection();
	        var stmt = con.createStatement()) {
			String sql = "SELECT * FROM categories ORDER BY id";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String category_name = rs.getString("category_name");
			
				Categories cate = new Categories(id,category_name);
				cates.add(cate);
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return cates;
	}

	
	@Override
	public boolean isCategoryExist(String name , int id) {
		String sql = "SELECT COUNT(*)  FROM Categories WHERE category_name = ? AND id != ?";
		System.out.println("SQL " + sql);
		try (Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {// this Statement is created for talk to sql

			stmt.setString(1, name);	
			stmt.setInt(2, id);
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
	
	
	@Override
	public boolean saveCategories(Categories cate) {
		String sql = "insert into categories (category_name) values(?);";
		try(Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)){
			
			
			stmt.setString(1, cate.category_name());
		
			
			int row = stmt.executeUpdate();
			
			return row>0;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCategories(Integer id, String name) {
		String sql =  "Update categories set category_name = ? where id = ?";
		try(Connection con = getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setString(1, name);
			stmt.setInt(2, id);
			
			int row = stmt.executeUpdate();
			
			return row > 0;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	
	public static void main(String [] args) {
//		CategoriesDao catedao = new CategoriesDaoImpl();
////		List<Categories> cate = catedao.getAllCategories();
////		cate.forEach(System.out::println);
//		
//		System.out.println();
////		Categories cate = new Categories(5,"EarPhone",null);
////		catedao.saveCategories(cate);
//		
////		catedao.updateItems(3,"Camera");
//		List<Categories> cate2 = catedao.getAllCategories();
//		cate2.forEach(System.out::println);
//		
//	}


}
}
