package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;



public interface ItemsService  {
	List<Items> getAllItems();
	List<ItemsWithCategories> getAllItemsWithCategoryName();
	List<Items> getItemsByCategoryCode(Integer id);
	boolean saveItems(Items items,String path2);
	boolean updateItems(Integer price,String items);
	boolean deleteItemsByItemCode(String code);
	List<ItemsWithCategories> getItemsBaseOnCategoryID(Integer id);
	List<Items> getItemsEachPage(int page, int limit);
	List<Items> searchItemsWithName(String name);
	int getTotalItems();
}
