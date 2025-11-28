package com.mini_pos.dao;



import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class BaseDao {

	Connection conn; // to connect Database need connection concept like socket connection 
	private static BaseDao singleton;
	static // need to add DataBase Driver on Static initializer
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// that is DataBase Driver
			  System.out.println("✅ MySQL JDBC Driver loaded successfully!");
		}catch(ClassNotFoundException ex){
			System.err.println("❌ Failed to load JDBC driver!");
			ex.printStackTrace();
		}
	}
		public BaseDao() {
			try {
	            Properties props = new Properties();

	            // Load config.properties
	            InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties");
	            props.load(in);

	            String url = props.getProperty("db.url");
	            String user = props.getProperty("db.user");
	            String pass = props.getProperty("db.password");
	            this.conn = DriverManager.getConnection(url, user, pass);//that code make connection with DB
				if (conn != null && !conn.isClosed()) {
	                System.out.println("✅ Database connected successfully!");
	            } else {
	                System.out.println("❌ Connection failed!");
	            }
			}//jdbc:mysq == protocal , 3306 = mysql port no, ecommerce_db = DBname , DB acc name , pw;
			catch(Exception e) {
				 System.err.println("❌ Failed to connect to database!");
				e.printStackTrace();
			}
		}
		public Connection getconnection() {
			return this.conn;
		}
		public static void main(String[] args) {
		    System.out.println("BaseDao main() started!");
		    BaseDao dao = new BaseDao();
		   		}
	}

