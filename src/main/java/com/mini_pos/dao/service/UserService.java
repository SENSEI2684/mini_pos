package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;

public interface UserService {
	void registerUser(Users users) throws Exception ;
	public boolean loginUser(String username, String password ) throws Exception ;
	public boolean deleteaAccWithUsername(String username);
	public boolean updateUsers(String username, String password);
	public List<Users> getAllUsers();
	public boolean isUserExist(String username);
	
}
