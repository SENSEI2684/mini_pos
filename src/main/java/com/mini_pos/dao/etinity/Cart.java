package com.mini_pos.dao.etinity;

import java.time.LocalDateTime;

public record Cart(Integer id,Integer user_id,Integer item_id,Integer quantity,LocalDateTime added_at) {

}
