package com.mini_pos.dao;

import java.util.List;

import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.helper_function.DaoException;

public interface UserDao {
	public boolean saveUser(Users users);
	public boolean loginUser(String username, String password);
	public boolean isUserExist(String username );
	public boolean deleteaAccWithUsername(String username);
	public boolean updateUser(String username, String password, Role role);
	public List<Users> getAllUsers();
	public  Users getUserByUsernameAndPassword(String username, String password);
	public boolean approve(Boolean p ,String username);
	public boolean isUserApproved(String username) ;
}
