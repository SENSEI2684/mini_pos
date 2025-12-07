package com.mini_pos.helper_function;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class PasswordHide {

	public static void setupPasswordToggle(JLabel eyeLabel, JPasswordField passwordField, ImageIcon showIcon, ImageIcon hideIcon , boolean[] stateHolder) {
		
		eyeLabel.setIcon(hideIcon);
		passwordField.setEchoChar('•');
		
		eyeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent e) {
	            if (stateHolder[0]) {
	            	passwordField.setEchoChar((char) 0);
	                eyeLabel.setIcon(showIcon);
	            } else {
	            	passwordField.setEchoChar('•');
	                eyeLabel.setIcon(hideIcon);
	            }
	            stateHolder[0] = !stateHolder[0];
	        }
	    });

	}
}
