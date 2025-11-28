package com.mini_pos.dao.etinity;

import java.time.LocalDateTime;

public record Items(String item_code,String name,Integer price,Integer quantity,String photo,Integer category_id, LocalDateTime created_at) {

}
