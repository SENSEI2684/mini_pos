package com.mini_pos.helper_function;


import java.io.File;

public class FileNameGet {
	public static void main(String [] args) {
		File folder = new File("C:\\Users\\User\\Downloads\\Images");
		File[] files = folder.listFiles();

		for (File file : files) {
		    if (file.isFile()) {
//		        System.out.println(file.getAbsolutePath()); // full path
		        System.out.println(file.getName()); // only filename
		    }
		}
	}
}
