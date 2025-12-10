package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Categories;



public interface CategoriesDao {
	List<Categories> getAllCategories();
	boolean saveCategories(Categories cate);
	boolean updateCategories(Integer id,String name);
	boolean isCategoryExist(String name, int id);
	
}
