package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

public interface UserService {
	public boolean registerUser(Users users) throws ValidationException, DaoException ;
	public boolean loginUser(String username, String password ) throws ValidationException, DaoException ;
	public void deleteaAccWithUsername(String username) throws ValidationException, DaoException;
	public void updateUser(String username, String password, Role role) throws ValidationException, DaoException;
	public List<Users> getAllUsers() throws DaoException;
	public  Users getUserByUsernameAndPassword(String username, String password) throws ValidationException, DaoException;
	public void UserApproved(String username) throws ValidationException, DaoException;
	
	
}
