package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.UserDao;
import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.impl.UserDaoImpl;

public class UserServiceImpl implements UserService{

	UserDao userdao = new UserDaoImpl();
	
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public boolean registerUser(Users users) throws Exception { 
		boolean isExisting = userdao.isUserExist(users.username()); // that is business logic code only reqister when there is no same username;
		
		
		if(isExisting  )
		{
			throw new Exception("UserName already existed");
		}
		else
		{
			this.userdao.saveUser(users);
		}
		return true;
	}

	@Override
	public boolean loginUser(String username, String password ) throws Exception{
		boolean loginOK = this.userdao.loginUser(username, password);
	
		if(!loginOK)
		{
			System.out.println(username + password);
			throw new Exception("Invalid username/password OR user not approved by admin");
		}
		else {
			return loginOK;
		}
	}
	

	@Override
	public boolean UserApproved( String username) throws Exception {
		 boolean alreadyApproved = userdao.isUserApproved(username);

		    if (alreadyApproved) 
		    {
		    	throw new Exception("User Already Approve");
		    }
		    else
		    {
		    	 return userdao.approve(true, username);
		    }
		   
		}




	@Override
	public boolean isUserExist(String username) {
		// TODO Auto-generated method stub
		return userdao.isUserExist(username);
	}

	@Override
	public boolean deleteaAccWithUsername(String username) {
		// TODO Auto-generated method stub
		return userdao.deleteaAccWithUsername(username);
	}

	@Override
	public boolean updateUser(String username, String password, Role role) {
		// TODO Auto-generated method stub
		
		return userdao.updateUser(username, password,role);
	}

	@Override
	public List<Users> getAllUsers() {
		// TODO Auto-generated method stub
		return userdao.getAllUsers();
	}

	@Override
	public  Users getUserByUsernameAndPassword(String username, String password){
		return userdao.getUserByUsernameAndPassword(username, password);
		
	}




}
