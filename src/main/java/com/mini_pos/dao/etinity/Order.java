package com.mini_pos.dao.etinity;


import java.time.LocalDateTime;

public record Order(int id,
        int totalAmount,
        LocalDateTime orderDate) {

}
