package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.CategoriesDao;
import com.mini_pos.dao.etinity.Categories;
import com.mini_pos.dao.impl.CategoriesDaoImpl;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;



public class CategoriesServiceImpl implements CategoriesService{

	CategoriesDao categoriesdao = new CategoriesDaoImpl();
	
	@Override
	public List<Categories> getAllCategories() throws DaoException {
		try {
	        return categoriesdao.getAllCategories();
	    } catch (Exception e) {
	        throw new DaoException("Failed to fetch categories from database", e);
	    }
	}
	

	@Override
	public void addCategories(Categories cate) throws ValidationException, DaoException {
		if (cate.category_name() == null || cate.category_name().isEmpty()) {
			throw new ValidationException("Please input valid Item Name, Price, Item Code, and Category!");
		}
		boolean isExist = categoriesdao.isCategoryExist(cate.category_name(),cate.id());

		if (isExist) {
			throw new ValidationException("Category Name already existed!");
		}

		try {
			boolean saved = categoriesdao.saveCategories(cate);

			if (!saved) {

				throw new DaoException("Failed to add Category. Category_Name might already exist.", null);
			}
		} catch (Exception e) {
			throw new DaoException("Error while saving item", e);
		}
	}     
	    
		

	@Override
	public void updateCategories(Integer id, String name) throws ValidationException, DaoException {
		if (name == null || name.isEmpty()) {
			throw new ValidationException("Category Name cannot be empty for update!");
		}
		 if (id == null || id <= 0) {
		        throw new ValidationException("Category ID is invalid!");
		    }
		boolean isExist = categoriesdao.isCategoryExist(name,id);
		if (isExist) {
			throw new ValidationException("Category Name already existed!");
		}

		try {
			boolean updated = categoriesdao.updateCategories(id, name);
			if (!updated) {
				throw new DaoException("Category ID not found. Cannot update.", null);
			}
		} catch (Exception e) {
			throw new DaoException("Error while updating category", e);
		}
	}
	
}
	




