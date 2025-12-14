package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;



public interface ItemsService  {
	List<Items> getAllItems() throws DaoException;
	List<ItemsWithCategories> getAllItemsAndCategoryName() throws DaoException;
	List<Items> getItemsByCategoryCode(Integer id);
	void saveItems(Items items,String path2) throws ValidationException, DaoException;
	void updateItems(Integer price,String item_code,String name) throws ValidationException, DaoException;
	void deleteItemsByItemCode(String code) throws ValidationException, DaoException;
	List<ItemsWithCategories> getAllItemsAndCategoryNameByCat_Id(Integer id) throws ValidationException, DaoException;
	List<Items> getItemsEachPage(int page, int limit);
	List<Items> searchItemsWithName(String name);
	int getTotalItems();
}
