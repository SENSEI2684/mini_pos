package com.mini_pos.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.UserDao;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;



public class UserDaoImpl extends BaseDao implements UserDao {

	public static final String SALT = "#@!*&456";
	
	
	
	@Override
	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<>();
		String sql = "Select * from users;";
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk to sql
			
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				Role role = Role.valueOf(rs.getString("role"));
				Boolean approved = rs.getBoolean("approved");
				LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
				Users user = new Users(id,username,password,role,approved,created_at);
				users.add(user);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	
	@Override
	public boolean saveUser(Users users) {
		String sql = "INSERT INTO users(username,password,role,approved) VALUES (?,SHA2(CONCAT(?,?), 256),?,?);";
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk
																					// to sql

			stmt.setString(1, users.username());
			stmt.setString(2, SALT);
			stmt.setString(3, users.password());
			stmt.setString(4,  users.role().name());
			stmt.setBoolean(5, users.approved());
			

			int row = stmt.executeUpdate();// this must use .executeUpdate bec we make insert changes

			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean loginUser(String username, String password) 
	{
		String sql = "SELECT * FROM users WHERE username = ? AND password = SHA2(CONCAT(?,?),256) AND approved = 1";
		System.out.println("SQL " + sql);
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk to sql

			stmt.setString(1, username);
			stmt.setString(2, SALT);
			stmt.setString(3, password);
			
			
			ResultSet res = stmt.executeQuery();
	
			return res.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean isUserExist(String username) { // this code is need for (Register)Business Logic Request
		String sql = "SELECT * FROM USERS WHERE username = ?";
		System.out.println("SQL " + sql);
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk to sql

			stmt.setString(1, username);			
			ResultSet res = stmt.executeQuery();
	
			return res.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;}
	
	@Override
	 public  Users getUserByUsernameAndPassword(String username, String password) {
		 String sql = "SELECT id, username, password, role, approved, created_at " +
                 "FROM users WHERE username = ? AND password = SHA2(CONCAT(?,?),256) AND approved = 1";
		System.out.println("SQL " + sql);
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk to sql

			
			stmt.setString(1, username);
			stmt.setString(2, SALT);
			stmt.setString(3, password);
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
	                Integer id = rs.getInt("id");
	                String uname = rs.getString("username");
	                String pwd = rs.getString("password");
	                String roleStr = rs.getString("role");
	                Boolean approved = rs.getBoolean("approved");
	                java.time.LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
	                Role role = Role.valueOf(rs.getString("role"));
	                
	                return new Users(id, uname, pwd, role, approved, createdAt);
	                
		}
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		return null;
	}
	
	@Override
	public boolean deleteaAccWithUsername(String username) {
		String sql = "Delete from users where username = ?";
		try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {// this Statement is created for talk
																					// to sql

			stmt.setString(1, username); // level

			int row = stmt.executeUpdate();// this must use .executeUpdate bec we make insert changes

			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean updateUser(String username, String password, Role role) {
	    String sql = "UPDATE users SET password = SHA2(CONCAT(?, ?), 256), role = ? WHERE username = ?";

	    try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {

	        stmt.setString(1, SALT);        
	        stmt.setString(2, password);    
	        stmt.setString(3, role.name());
	        stmt.setString(4, username);    

	        int row = stmt.executeUpdate();
	        return row>0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean approveUser(Boolean b,String username) {
		String sql = "UPDATE users SET approved = ? WHERE username = ?;";
		
		  try (PreparedStatement stmt = this.getconnection().prepareStatement(sql)) {

		        stmt.setBoolean(1, b);        		   
		        stmt.setString(2, username);    

		        int row = stmt.executeUpdate();
		        return row>0;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	
	
	public static void main(String [] args) {
		
		UserDao userdao = new UserDaoImpl();
//		List<Users> user = userdao.getAllUsers();
//		user.forEach(System.out::println);
		
		Users user = new Users(null,"Admin", "Admin", Role.ADMIN,true,null);
		userdao.saveUser(user);
//		userdao.deleteaAccWithUsername("Admin");
	}










}
