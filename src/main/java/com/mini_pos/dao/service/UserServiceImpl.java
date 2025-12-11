package com.mini_pos.dao.service;

import java.util.List;

import com.mini_pos.dao.UserDao;
import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.impl.UserDaoImpl;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;


public class UserServiceImpl implements UserService{

		
	UserDao userdao = new UserDaoImpl();
	

	
	@Override
	public boolean registerUser(Users users) throws ValidationException, DaoException{ 
			
		if(users.username() == null 
				|| users.username().trim().isEmpty()
		        || users.password() == null 
		        || users.password().trim().isEmpty()
		        || users.role() == null )
		
		{
			throw new ValidationException("Please input valid  UserName, Password!");
		}
		
		boolean isExisting = userdao.isUserExist(users.username()); // that is business logic code only reqister when
																	// there is no same username;
		if (isExisting) {
			throw new ValidationException("UserName already existed");
		}
		try {
			boolean save = userdao.saveUser(users);
			
			if (!save) 
			{
				throw new DaoException("Failed to insert Account into database.", null);
			}
			return save;
			
		} catch (Exception e) {
			throw new DaoException("Error while Register Account", e);
		}

	}
	

	@Override
	public boolean loginUser(String username, String password ) throws ValidationException, DaoException{
		if(username == null 
				|| username.trim().isEmpty()
				|| password == null
				|| password.trim().isEmpty())
		{
			throw new ValidationException("Please input valid  UserName, Password!");
		}
		
		try{
			boolean loginOK = this.userdao.loginUser(username, password);
			
			if(!loginOK)
			{		
				throw new DaoException("Invalid username/password OR user not approved by admin", null);
			}
			return loginOK;
		
		}catch(Exception e) {
			throw new DaoException("Error while login Account", e); 
		}		
	}
	

	@Override
	public void UserApproved(String username) throws ValidationException, DaoException {

		if (username == null || username.trim().isEmpty()) {
			throw new ValidationException("The Username is not valid!");
		}

		boolean alreadyApproved = userdao.isUserApproved(username);
		if (alreadyApproved) {
			throw new ValidationException("User Already Approve");
		}
		
		try {
			
			boolean approve = userdao.approve(true, username);
			if (!approve) {
				throw new DaoException("Failed to Approve this Account", null);
			}
		
		} catch (Exception e) {
			throw new DaoException("Error while Approve Account", e);
		}
	}


	@Override
	public void deleteaAccWithUsername(String username)throws ValidationException, DaoException {
	
		if(username == null || username.trim().isEmpty()) {
			throw new ValidationException("Username cannot be empty!");
		}
		try {
			boolean deleteOK = userdao.deleteaAccWithUsername(username);
			if(!deleteOK) {
				throw new DaoException("Failed to Delete this Account", null);
			}
		}
		catch(Exception e) {
			throw new DaoException("Error while Delete Account", e);
		}

	}

	
	@Override
	public void updateUser(String username, String password, Role role) throws ValidationException, DaoException {


		if (username == null || username.trim().isEmpty()) {
			throw new ValidationException("Username cannot be empty!");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new ValidationException("Password cannot be empty!");
		}
		if (role == null) {
			throw new ValidationException("Role is not valid!");
		}
		try {
			boolean updateOK = userdao.updateUser(username, password, role);

			if (!updateOK) {
				throw new DaoException("Failed to update user!", null);
			}
		} catch (Exception e) {
			throw new DaoException("Error while updating user", e);
		}
	}

	@Override
	public List<Users> getAllUsers() throws DaoException {
		 try {
		        return userdao.getAllUsers();
		    } catch (Exception e) {
		        throw new DaoException("Failed to fetch Accounts from database", e);
		    }
	}

	@Override
	public  Users getUserByUsernameAndPassword(String username, String password) throws ValidationException, DaoException{
		if(username == null 
				|| username.trim().isEmpty()
				|| password == null
				|| password.trim().isEmpty())
		{
			throw new ValidationException("Please input valid  UserName, Password!");
		}
		
		try {
			return userdao.getUserByUsernameAndPassword(username, password);
		}
		catch(Exception e) {
			throw new DaoException("Login Failed", e);
		}
	
	}

}
