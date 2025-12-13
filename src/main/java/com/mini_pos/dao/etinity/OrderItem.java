package com.mini_pos.dao.etinity;

public record OrderItem(
		int orderId,
		int itemId,
		int quantity,
		int price
       ) {
}
