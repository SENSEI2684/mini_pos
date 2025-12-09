package com.mini_pos.dao;



import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
	
//public class BaseDao {
//
//	 private static Connection conn; // SINGLE shared connection for whole program
//	private static BaseDao singleton;
//	static // need to add DataBase Driver on Static initializer
//	{
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");// that is DataBase Driver
//			  System.out.println("✅ MySQL JDBC Driver loaded successfully!");
//		}catch(ClassNotFoundException ex){
//			System.err.println("❌ Failed to load JDBC driver!");
//			ex.printStackTrace();
//		}
//	}
//		public BaseDao() {
//			try {
//				 // If connection already exists, reuse it
//				if(conn != null && !conn.isClosed())return;
//	
////				else {
////	                System.out.println("❌ Connection failed!");
////	            }
//					
//					 Properties props = new Properties();
//
//			            // Load config.properties
//			            InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties");
//			            props.load(in);
//
//			            String url = props.getProperty("db.url");
//			            String user = props.getProperty("db.user");
//			            String pass = props.getProperty("db.password");
//			            this.conn = DriverManager.getConnection(url, user, pass);//that code make connection with DB
//				
//	            
//			}//jdbc:mysq == protocal , 3306 = mysql port no, ecommerce_db = DBname , DB acc name , pw;
//			catch(Exception e) {
//				 System.err.println("❌ Failed to connect to database!");
//				e.printStackTrace();
//			}
//		} 
////		public static void closeConnection() {
////		    try {
////		        if (conn != null && !conn.isClosed()) {
////		            conn.close();
////		            System.out.println("✅ Connection closed");
////		        }
////		    } catch (Exception e) {
////		        e.printStackTrace();
////		    }
////		}
//		
//		public Connection getconnection() {
//			return this.conn;
//		}
//		
//		public static void main(String[] args) {
//		    System.out.println("BaseDao main() started!");
//		    BaseDao dao = new BaseDao();
//		   		}
//	}

public class BaseDao {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver Loaded!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver Load Failed!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            Properties props = new Properties();
            InputStream in = getClass().getClassLoader()
                    .getResourceAsStream("config.properties");

            props.load(in);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

