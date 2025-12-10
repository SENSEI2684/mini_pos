package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.ItemsDao;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.impl.ItemsDaoImpl;

import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

;

public class ItemsServiceImpl implements ItemsService{

	ItemsDao itemsDao = new ItemsDaoImpl(); 
	
	@Override
	public List<Items> getAllItems() throws DaoException {
	    try {
	        return itemsDao.getAllItems();
	    } catch (Exception e) {
	        throw new DaoException("Failed to fetch items from database", e);
	    }
	}
	
	@Override
	public List<ItemsWithCategories> getAllItemsAndCategoryName() throws DaoException {
	    try {
	        return itemsDao.getAllItemsAndCategoryName();
	    } catch (Exception e) {
	        throw new DaoException("Failed to load items with categories.", e);
	    }
	}



    @Override
    public void saveItems(Items item, String path2) throws ValidationException, DaoException {
        // 1️⃣ Validation in service
        if ( item.name() == null 
        		|| item.name().isEmpty()
                || item.item_code() == null 
                || item.item_code().isEmpty()
                || item.price() == null 
                || item.price() <= 0
                || item.category_id() == null 
                || item.category_id() == 0 
                
                ) 
		{
			throw new ValidationException("Please input valid Item Name, Price, Item Code, and Category!");
		}

        boolean isExisting = itemsDao.isItemExist(item.item_code(), item.name());
        if (isExisting) {
            throw new ValidationException("Item code or Item name already exists!");
        }

        // 3️⃣ Save item
        try {
            boolean inserted = itemsDao.saveItems(item, path2);
            if (!inserted) {
                throw new DaoException("Failed to insert item into database.", null);
            }
        } catch (Exception e) {
            throw new DaoException("Error while saving item", e);
        }
    }
    
    @Override
    public void updateItems(Integer price, String item_code) throws ValidationException, DaoException {
        if (item_code == null || item_code.isEmpty()) {
            throw new ValidationException("Item code cannot be empty for update.");
        }
        if (price == null || price <= 0) {
            throw new ValidationException("Price must be a positive number.");
        }
      
        try {
            boolean updated = itemsDao.updateItems(price, item_code);
            if (!updated) {
                throw new DaoException("Item code not found. Cannot update.", null);
            	}
        } catch (Exception e) {
            throw new DaoException("Error while updating item", e);
        }
    }


    
    @Override
    public void deleteItemsByItemCode(String code) throws ValidationException, DaoException {
     
        try {
            boolean deleted = itemsDao.deleteItemsByItemCode(code);
            if (!deleted) {
                throw new DaoException("Item not found or could not be deleted.", null);
            }
        } catch (Exception e) {
            throw new DaoException("Error while deleting item", e);
        }
    }

	@Override
	public List<ItemsWithCategories> getAllItemsAndCategoryNameByCat_Id(Integer id) {
		return itemsDao.getAllItemsAndCategoryNameByCat_Id(id);
	}

	@Override
	public List<Items> getItemsEachPage(int page, int limit) {
		return itemsDao.getItemsEachPage(page, limit);
	}

	@Override
	public List<Items> searchItemsWithName(String name) {
		return itemsDao.searchItemsWithName(name);
	}

	@Override
	public int getTotalItems() {
		return itemsDao.getTotalItems();
	}

	@Override
	public List<Items> getItemsByCategoryCode(Integer id) {
		return itemsDao.getItemsByCategoryCode(id);
	}

	

}
