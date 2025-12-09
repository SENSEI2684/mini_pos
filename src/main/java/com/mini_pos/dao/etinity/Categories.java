package com.mini_pos.dao.etinity;

public record Categories(Integer id ,String category_name) {
    @Override
    public String toString() {
        return category_name;
    }
}
