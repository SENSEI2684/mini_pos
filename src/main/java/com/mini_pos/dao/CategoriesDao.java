package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Categories;



public interface CategoriesDao {
	List<Categories> getAllCategories();
	boolean saveCategories(Categories cate);
	boolean updateItems(Integer price,String items);
	
}
