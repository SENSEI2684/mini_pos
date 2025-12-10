package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Categories;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;



public interface CategoriesService {
	List<Categories> getAllCategories() throws DaoException;
	void addCategories(Categories cate) throws ValidationException, DaoException;
	void updateCategories(Integer id,String name) throws ValidationException, DaoException;
}
