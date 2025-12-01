package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Categories;



public interface CategoriesService {
	List<Categories> getAllCategories();
	boolean saveCategories(Categories cate);
	boolean updateItems(Integer price,String items);
}
