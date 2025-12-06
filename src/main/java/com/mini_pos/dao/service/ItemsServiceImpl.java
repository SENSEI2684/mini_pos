package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.ItemsDao;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;
import com.mini_pos.dao.impl.ItemsDaoImpl;

;

public class ItemsServiceImpl implements ItemsService{

	ItemsDao itemsdao = new ItemsDaoImpl(); 
	@Override
	public List<Items> getAllItems() {
		return itemsdao.getAllItems();
	}

	@Override
	public List<Items> getItemsByCategoryCode(Integer id) {
		return itemsdao.getItemsByCategoryCode(id);
	}

	@Override
	public boolean saveItems(Items items) {
		return itemsdao.saveItems(items);
	}

	@Override
	public boolean updateItems(Integer price, String items) {
		return itemsdao.updateItems(price, items);
	}

	@Override
	public boolean deleteItemsByItemCode(String code) {
		return itemsdao.deleteItemsByItemCode(code);
	}

	@Override
	public List<ItemsWithCategories> getItemsBaseOnCategoryID(Integer id) {
		return itemsdao.getItemsBaseOnCategoryID(id);
	}

	@Override
	public List<Items> getItemsEachPage(int page, int limit) {
		return itemsdao.getItemsEachPage(page, limit);
	}

	@Override
	public List<Items> searchItemsWithName(String name) {
		return itemsdao.searchItemsWithName(name);
	}

	@Override
	public int getTotalItems() {
		return itemsdao.getTotalItems();
	}

}
