package com.mini_pos.dao.session;


import com.mini_pos.dao.etinity.Users;

public class Session {
    private static Session instance;
    private Users currentUser;
   

    public Session() {} // private constructor

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUser(Users user) {
        this.currentUser = user;
    }

    public Users getUser() {
        return currentUser;
    }
    public String getUsername() {
    	return currentUser.username();
    }
//
    public Integer getUserId() {
        if (currentUser != null) {
        
        	return currentUser.id();
        }
        else {
        	 throw new IllegalStateException("No user logged in");
        }
       
    }

    public void logout() {
        currentUser = null;
    }
}
