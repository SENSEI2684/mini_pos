package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;

public interface UserDao {
	public boolean saveUser(Users users);
	public boolean loginUser(String username, String password);
	public boolean isUserExist(String username );
	public boolean deleteaAccWithUsername(String username);
	public boolean updateUser(String username, String password);
	public List<Users> getAllUsers();
	public  Users getUserByUsernameAndPassword(String username, String password);
}
