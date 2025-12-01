package com.mini_pos.dao.etinity;

import java.time.LocalDateTime;

public record Users(String username, String password,Role role,Boolean approved, LocalDateTime created_at) {

    public boolean isApproved() {
        return approved;
    }
}
