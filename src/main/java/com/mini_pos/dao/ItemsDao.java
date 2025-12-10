package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;


public interface ItemsDao {
	List<Items> getAllItems();
	List<ItemsWithCategories> getAllItemsAndCategoryName();
	List<Items> getItemsByCategoryCode(Integer code);
	boolean saveItems(Items items,String path2);
	boolean updateItems(Integer price,String item_code);
	boolean deleteItemsByItemCode(String code);
	List<ItemsWithCategories> getAllItemsAndCategoryNameByCat_Id(Integer id);
	List<Items> getItemsEachPage(int page, int itemsPerPage);
	List<Items> searchItemsWithName(String name);
	int getTotalItems();
	public boolean isItemExist(String item_code,String name);
	
}

