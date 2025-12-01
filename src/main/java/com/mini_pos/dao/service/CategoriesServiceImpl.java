package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.CategoriesDao;
import com.mini_pos.dao.etinity.Categories;
import com.mini_pos.dao.impl.CategoriesDaoImpl;



public class CategoriesServiceImpl implements CategoriesService{

	CategoriesDao categoriesdao = new CategoriesDaoImpl();
	@Override
	public List<Categories> getAllCategories() {
		// TODO Auto-generated method stub
		return categoriesdao.getAllCategories();
	}

	@Override
	public boolean saveCategories(Categories cate) {
		// TODO Auto-generated method stub
		return categoriesdao.saveCategories(cate);
	}

	@Override
	public boolean updateItems(Integer price, String items) {
		// TODO Auto-generated method stub
		return categoriesdao.updateItems(price, items);
	}

}
