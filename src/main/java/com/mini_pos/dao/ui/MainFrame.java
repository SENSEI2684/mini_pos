/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mini_pos.dao.ui;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.CartWithItems;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.service.CartService;
import com.mini_pos.dao.service.CartServiceImpl;
import com.mini_pos.dao.service.ItemsService;
import com.mini_pos.dao.service.ItemsServiceImpl;
import com.mini_pos.dao.service.UserService;
import com.mini_pos.dao.service.UserServiceImpl;
import com.mini_pos.dao.ui.ItemCard;

/**
 *
 * @author User
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
	
	// Pagination variables
	private int currentPage = 1;
	private int itemsPerPage = 9; // 3x3 grid
	private int totalItems = 0;
	private boolean loginSucceeded = false;
    private ImageIcon eyeIcon;
    private ImageIcon eyeHideIcon;
	private boolean passwordHidden = true;
	private Integer userId;
	private Integer cartId;

	// DAO to load items
	private ItemsService itemService = new ItemsServiceImpl();
	private CartService cartService = new CartServiceImpl();
	private UserService userService = new UserServiceImpl();
	
	
    public MainFrame() {
        initComponents();
        initPasswordFeatures();   // Enable eye toggle
        setTime();
    }
 
//Item CardFunction   
 
    void loadCartTable() // to add sql data on ui table
    {
    	List<CartWithItems> cartwithItem = this.cartService.showcartdata();
        DefaultTableModel model = (DefaultTableModel)tblCart.getModel();//put create table in this method
        for(CartWithItems item : cartwithItem)
        {
            Object [] row = new Object[4];
            row[0] = item.cart().id();
            row[1] = item.item_name();
            row[2] = item.cart().quantity();
            row[3] = item.item_price();
            
           // add all columns in array and
            model.addRow(row); // put array in table
        }
    }

//    
 //----------------------------------------------------------------------------------------------------------------------------------------
 //loginUI
    public void loginForm() { //setup dlglogin box 
    	dialogin.setModal(true);// this code mean another step will appear only if this box close
    	dialogin.pack();
    	dialogin.setLocationRelativeTo(this); // center
    	Point location = dialogin.getLocation();
    	dialogin.setLocation(location.x + 10, location.y + 10); // shift
    	dialogin.setVisible(true);
    }
    
    private void login() { // login function when click login button take input String from textbox and check with UserDAO loginUser
		try 
    	{
    		String username = this.txtusername.getText();
    		String password = this.txtpassword.getText();
    		boolean loginResult = this.userService.loginUser(username, password);
    		
    		System.out.println("LoginResult " + loginResult +username + " " + password);
    		if(loginResult)
    		{
    			 this.loginSucceeded = true;
    			this.dialogin.setVisible(false);
    			
    			totalItems = itemService.getTotalItems();
    		    loadItemsToPanel();
    		    updatePageLabel();
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(this, "IN UI Invalid User", "Warnning", JOptionPane.CLOSED_OPTION);
    		}
    	}
    	catch(Exception e)
    	{
    		JOptionPane.showMessageDialog(this, "Invalid User", "Warnning", JOptionPane.CLOSED_OPTION);
    		e.printStackTrace();
    	}
	}

    private ImageIcon resizeIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void initPasswordFeatures() {
    	   eyeIcon = resizeIcon(new ImageIcon(getClass().getResource("/static/logo/show.png")), 20, 20);
    	    eyeHideIcon = resizeIcon(new ImageIcon(getClass().getResource("/static/logo/hide.png")), 20, 20);

        lbleye.setIcon(eyeHideIcon);     // start with password hidden
        txtpassword.setEchoChar('•');    // hide password

        lbleye.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                togglePassword();
            }
        });
    }
    
    private void togglePassword() {
        if (passwordHidden) {
        	txtpassword.setEchoChar((char) 0);  // show password
        	lbleye.setIcon(eyeIcon);
            passwordHidden = false;
        } else {
        	txtpassword.setEchoChar('•');       // hide password
        	lbleye.setIcon(eyeHideIcon);
            passwordHidden = true;
        }
    }
//-------------------------------------------------------------------------------------------
//MainFrame UI
    
    public void loadItemsToPanel() {
        try {
            pnlMainItem.removeAll();
            pnlMainItem.setPreferredSize(new Dimension(1000, 350));
            pnlMainItem.setMinimumSize(new Dimension(1000, 350));
            pnlMainItem.setLayout(new GridLayout(0, 3, 20, 20));  // 3 rows x 3 cols
           

            List<Items> items = itemService.getItemsEachPage(currentPage, itemsPerPage);
            
            
            
            for (Items item : items) {

                ItemCard card = new ItemCard(item, () -> {

                    Integer qty = 1; // default

                    // TODO: you can read qty from card later
                    System.out.println("Add to cart clicked: " + item.name());

                    // Add to cart logic
                    Cart cart = new Cart(cartId,userId,item.id(),qty,null);
                    cartService.addToCart(cart);

                    loadCartTable();
                });

                pnlMainItem.add(card);
            }

            pnlMainItem.revalidate();
            pnlMainItem.repaint();
            
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setTime() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Date date = new Date();
					SimpleDateFormat tf = new SimpleDateFormat("h:m:ss aa");
					SimpleDateFormat df = new SimpleDateFormat("EEE, dd-MM-yyyy");
					String time = tf.format(date);
					lbltime.setText(time.split(" ")[0] + " " + time.split(" ")[1]);
					lbldate.setText(df.format(date));

				}

			}

		}).start();
		;
	}


    private void updatePageLabel() {
        lblPageNumber.setText("Page " + currentPage);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    
    
    
    private void initComponents() {

        dialogin = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        lbleye = new javax.swing.JLabel();
        btnlogin = new javax.swing.JButton();
        txtpassword = new javax.swing.JPasswordField();
        diaregister = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtregiusername = new javax.swing.JTextField();
        txtregipassowrd = new javax.swing.JPasswordField();
        txtregirepassowrd = new javax.swing.JPasswordField();
        lblregieye = new javax.swing.JLabel();
        lblregieye1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        pnlMain = new javax.swing.JPanel();
        pnlMainItem = new javax.swing.JPanel();
        pnlMainTitle = new javax.swing.JPanel();
        pnlpagination = new javax.swing.JPanel();
        btnPrevPage = new javax.swing.JButton();
        btnNextPage = new javax.swing.JButton();
        lblPageNumber = new javax.swing.JLabel();
        lbnCart = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        pnlButtons = new javax.swing.JPanel();
        pnlDateTime = new javax.swing.JPanel();
        lbldate = new javax.swing.JLabel();
        lbltime = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jLabel1.setText("UserName");

        jLabel2.setText("Password");

        btnlogin.setText("Login");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialoginLayout = new javax.swing.GroupLayout(dialogin.getContentPane());
        dialogin.getContentPane().setLayout(dialoginLayout);
        dialoginLayout.setHorizontalGroup(
            dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialoginLayout.createSequentialGroup()
                .addGroup(dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialoginLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addGroup(dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(dialoginLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dialoginLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbleye, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialoginLayout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(btnlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        dialoginLayout.setVerticalGroup(
            dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialoginLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbleye, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(38, 38, 38)
                .addComponent(btnlogin)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jLabel3.setText("Username");

        jLabel4.setText("Password");

        jLabel5.setText("Reenter Password");

        txtregirepassowrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtregirepassowrdActionPerformed(evt);
            }
        });

        jButton1.setText("Register");

        javax.swing.GroupLayout diaregisterLayout = new javax.swing.GroupLayout(diaregister.getContentPane());
        diaregister.getContentPane().setLayout(diaregisterLayout);
        diaregisterLayout.setHorizontalGroup(
            diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diaregisterLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(diaregisterLayout.createSequentialGroup()
                        .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(86, 86, 86)
                        .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtregiusername)
                            .addComponent(txtregipassowrd, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                    .addGroup(diaregisterLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(txtregirepassowrd, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblregieye, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblregieye1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
            .addGroup(diaregisterLayout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        diaregisterLayout.setVerticalGroup(
            diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diaregisterLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblregieye1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(diaregisterLayout.createSequentialGroup()
                        .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtregiusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtregipassowrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblregieye, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(diaregisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtregirepassowrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addComponent(jButton1)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 3));

        pnlMainItem.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 235, 235), 2));

        javax.swing.GroupLayout pnlMainItemLayout = new javax.swing.GroupLayout(pnlMainItem);
        pnlMainItem.setLayout(pnlMainItemLayout);
        pnlMainItemLayout.setHorizontalGroup(
            pnlMainItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1041, Short.MAX_VALUE)
        );
        pnlMainItemLayout.setVerticalGroup(
            pnlMainItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlMainTitleLayout = new javax.swing.GroupLayout(pnlMainTitle);
        pnlMainTitle.setLayout(pnlMainTitleLayout);
        pnlMainTitleLayout.setHorizontalGroup(
            pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlMainTitleLayout.setVerticalGroup(
            pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        btnPrevPage.setText("Previous");
        btnPrevPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPageActionPerformed(evt);
            }
        });

        btnNextPage.setText("Next");
        btnNextPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPageActionPerformed(evt);
            }
        });

        lblPageNumber.setText("1");

        javax.swing.GroupLayout pnlpaginationLayout = new javax.swing.GroupLayout(pnlpagination);
        pnlpagination.setLayout(pnlpaginationLayout);
        pnlpaginationLayout.setHorizontalGroup(
            pnlpaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlpaginationLayout.createSequentialGroup()
                .addContainerGap(824, Short.MAX_VALUE)
                .addComponent(btnPrevPage)
                .addGap(20, 20, 20)
                .addComponent(lblPageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNextPage))
        );
        pnlpaginationLayout.setVerticalGroup(
            pnlpaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlpaginationLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlpaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrevPage)
                    .addComponent(btnNextPage)
                    .addComponent(lblPageNumber)))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMainItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlMainTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlpagination, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMainItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlpagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbnCart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 3));

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Items", "Quantity", "Total_Price"
            }
        ));
        jScrollPane1.setViewportView(tblCart);

        pnlButtons.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.gray, java.awt.Color.lightGray, null));

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );

        pnlDateTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 250, 250)));
        pnlDateTime.setForeground(new java.awt.Color(255, 255, 255));

        lbldate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        lbltime.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout pnlDateTimeLayout = new javax.swing.GroupLayout(pnlDateTime);
        pnlDateTime.setLayout(pnlDateTimeLayout);
        pnlDateTimeLayout.setHorizontalGroup(
            pnlDateTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDateTimeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbltime, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbldate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlDateTimeLayout.setVerticalGroup(
            pnlDateTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDateTimeLayout.createSequentialGroup()
                .addGroup(pnlDateTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbldate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout lbnCartLayout = new javax.swing.GroupLayout(lbnCart);
        lbnCart.setLayout(lbnCartLayout);
        lbnCartLayout.setHorizontalGroup(
            lbnCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
            .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(lbnCartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDateTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lbnCartLayout.setVerticalGroup(
            lbnCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbnCartLayout.createSequentialGroup()
                .addComponent(pnlDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbnCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbnCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPageActionPerformed
        // TODO add your handling code here:
    	if (currentPage * itemsPerPage < totalItems) {
            currentPage++;
            loadItemsToPanel();
            updatePageLabel();
        }
    }//GEN-LAST:event_btnNextPageActionPerformed

    private void txtregirepassowrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtregirepassowrdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtregirepassowrdActionPerformed

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	this.login();
    }

    private void btnPrevPageActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentPage > 1) {
            currentPage--;
            loadItemsToPanel();
            updatePageLabel();
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	 // 1. Create the login dialog (modal)
                MainFrame mainFrame = new MainFrame(); // create instance to pass as parent
               
                mainFrame.loginForm();
                

//                mainFrame.dlgLogin.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
//                mainFrame.dlgLogin.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0); // close program if user presses X
//                    }
//                });


                // check if dlgLogin is still visible   
                if (mainFrame.loginSucceeded) {
                    mainFrame.setVisible(true);
                } else {
                    System.exit(0);
                }    
            }
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNextPage;
    private javax.swing.JButton btnPrevPage;
    private javax.swing.JButton btnlogin;
    private javax.swing.JDialog dialogin;
    private javax.swing.JDialog diaregister;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageNumber;
    private javax.swing.JLabel lbldate;
    private javax.swing.JLabel lbleye;
    private javax.swing.JLabel lblregieye;
    private javax.swing.JLabel lblregieye1;
    private javax.swing.JLabel lbltime;
    private javax.swing.JPanel lbnCart;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlDateTime;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMainItem;
    private javax.swing.JPanel pnlMainTitle;
    private javax.swing.JPanel pnlpagination;
    private javax.swing.JTable tblCart;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JPasswordField txtregipassowrd;
    private javax.swing.JPasswordField txtregirepassowrd;
    private javax.swing.JTextField txtregiusername;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
