package com.mini_pos.dao.etinity;

import java.time.LocalDateTime;

public record Items(Integer id,String item_code,String name,Integer price,String photo,Integer category_id, LocalDateTime created_at) {

}
