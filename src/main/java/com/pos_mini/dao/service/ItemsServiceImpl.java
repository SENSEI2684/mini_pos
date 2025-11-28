package com.pos_mini.dao.service;

import java.util.List;

import com.mini_pos.dao.ItemsDao;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;
import com.pos_mini.dao.impl.ItemsDaoImpl;

;

public class ItemsServiceImpl implements ItemsService {

	ItemsDao itemsdao = new ItemsDaoImpl(); 
	@Override
	public List<Items> getAllItems() {
		return itemsdao.getAllItems();
	}

	@Override
	public List<Items> getItemsByCategoryCode(String code) {
		// TODO Auto-generated method stub
		return itemsdao.getItemsByCategoryCode(code);
	}

	@Override
	public boolean saveItems(Items items) {
		// TODO Auto-generated method stub
		return itemsdao.saveItems(items);
	}

	@Override
	public boolean updateItems(Integer price, String items) {
		// TODO Auto-generated method stub
		return itemsdao.updateItems(price, items);
	}

	@Override
	public boolean deleteItemsByItemCode(String code) {
		// TODO Auto-generated method stub
		return itemsdao.deleteItemsByItemCode(code);
	}

	@Override
	public List<ItemsWithCategories> getItemsBaseOnCategoryID(Integer id) {
		// TODO Auto-generated method stub
		return itemsdao.getItemsBaseOnCategoryID(id);
	}

}
