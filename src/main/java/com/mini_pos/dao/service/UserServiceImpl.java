package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.UserDao;
import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.impl.UserDaoImpl;

public class UserServiceImpl implements UserService{

	UserDao userdao = new UserDaoImpl();
	
	@Override
	public void registerUser(Users users) throws Exception { 
		boolean isExisting = userdao.isUserExist(users.username()); // that is business logic code only reqister when there is no same username;
		if(isExisting)
		{
			throw new Exception("User already existed");
		}
		else
		{
			this.userdao.saveUser(users);
		}
		
	}

	@Override
	public boolean loginUser(String username, String password ) throws Exception{
		Boolean loginOK = this.userdao.loginUser(username, password);
		
		
		if(!loginOK)
		{
			System.out.println(username + password);
			   throw new Exception("Invalid username/password OR user not approved by admin");
		}
		else {
			return loginOK;
		}
	}
	
//	@Override
//	public boolean loginUser(String username, String password) throws Exception {
//	    
//	    Users user = this.userdao.loginUser(username, password);
//
//	    if (user == null) {
//	        throw new Exception("Invalid Username or Password");
//	    }
//
//	    if (!user.isApproved()) {  // <- use isApproved() for class
//	        throw new Exception("User not approved by Admin");
//	    }
//
//	    return true;
//	}



	@Override
	public boolean isUserExist(String username) {
		// TODO Auto-generated method stub
		return isUserExist(username);
	}

	@Override
	public boolean deleteaAccWithUsername(String username) {
		// TODO Auto-generated method stub
		return deleteaAccWithUsername(username);
	}

	@Override
	public boolean updateUsers(String username, String password) {
		// TODO Auto-generated method stub
		return updateUsers(username, password);
	}

	@Override
	public List<Users> getAllUsers() {
		// TODO Auto-generated method stub
		return getAllUsers();
	}

}
