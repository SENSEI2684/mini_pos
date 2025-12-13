package com.mini_pos.dao.etinity;

import java.time.LocalDateTime;

public record SaleReport(      
		String category,
        String itemName,
        int totalQuantity,
        int Price,       
        int totalPrice,
        LocalDateTime saleDate) {
}
