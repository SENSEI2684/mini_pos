
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mini_pos.dao.ui;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mini_pos.dao.etinity.CartWithItems;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.service.CartService;
import com.mini_pos.dao.service.CartServiceImpl;
import com.mini_pos.dao.service.ItemsService;
import com.mini_pos.dao.service.ItemsServiceImpl;
import com.mini_pos.dao.service.UserService;
import com.mini_pos.dao.service.UserServiceImpl;
import com.mini_pos.helper_function.Session;

/**
 *
 * @author User
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
	
	// Pagination variables
	private int currentPage = 1; //for pagination
	private int itemsPerPage = 9; // 3x3 grid , for pagination
	private int totalItems = 0;  // for items JDBC
	private boolean loginSucceeded = false; // for login
    private ImageIcon eyeIcon;   // for login eyelogo
    private ImageIcon eyeHideIcon; // for login eyelogo hide
	private boolean passwordHidden = true; // for login function
//	private Integer userId;
	private Integer cartId;   // for cart function
	private Integer selectedID; // for selection
	private Integer total;

	// DAO to load items
	private ItemsService itemService = new ItemsServiceImpl();
	private CartService cartService = CartServiceImpl.getInstance();
	private UserService userService = new UserServiceImpl();
	private Session session = Session.getInstance();
	NumberFormat formatter = NumberFormat.getInstance();
	private List<ItemCard> allCards = new ArrayList<>();
	private List<Items> allItems = new ArrayList<>();
	
	
    public MainFrame() {
        initComponents();
//       loadAllItemsOnce();
//       loadCartTable();
        initPasswordFeatures();   // Enable eye toggle
        setTime();
//        this.loadCartTable();
//        this.valueSelect();
     
    }
 
//    
 //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
 //loginUI ***************************************************************
    public void loginForm() { //setup dlglogin box 
    	dialogin.setModal(true);// this code mean another step will appear only if this box close
    	dialogin.pack();
    	dialogin.setLocationRelativeTo(this); // center
    	Point location = dialogin.getLocation();
    	dialogin.setLocation(location.x + 10, location.y + 10); // shift
    	dialogin.setVisible(true);
    }
    
    public void receipForm() { //setup dlglogin box 
    	diareceip.pack();
    	diareceip.setLocationRelativeTo(this); // center
    	Point location = diareceip.getLocation();
    	diareceip.setLocation(location.x + 10, location.y + 10); // shift
    	diareceip.setVisible(true);
//    	this.outreceip();
    }
    
 //login function ***************************************************************
    private void login() { // login function when click login button take input String from textbox and check with UserDAO loginUser
		try 
    	{
    		String username = this.txtusername.getText();
    		String password = this.txtpassword.getText();
    		boolean loginResult = this.userService.loginUser(username, password);
    		
    		System.out.println("LoginResult " + loginResult +username + " " + password);
    		if(loginResult)
    		{	
    			
    			Users user = userService.getUserByUsernameAndPassword(username, password); // write this method
    			
    			if (user == null) {
    			    System.out.println("Login failed!");
    			} else {
    			    Session.getInstance().setUser(user);   // save to session
    			    System.out.println("Saved to session: " + Session.getInstance().getUsername());
    			}
    		              
    		
    			
    			 this.loginSucceeded = true;
    			this.dialogin.setVisible(false);
    			
    			totalItems = itemService.getTotalItems();// this code need to write in login function bec after login success it will work with pagination next and previous button
    		    this.loadItemsToPanel(); //Main UI code 
    		    this.updatePageLabel();
    		    this.resetCart();
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

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
 //password hide and show function***************************************************************
    
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
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
//MainFrame UI***************************************************************
    
//    public void loadAllItemsOnce() {
//        try {
//            allItems = itemService.getAllItems();   // GET ALL items only once
//            allCards.clear();
//
//            // Create ItemCards just ONCE
//            for (Items item : allItems) {
//                allCards.add(new ItemCard(item, () -> {
//                    loadCartTable();
//                }));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public void loadItemsToPanel() {
//        try {
//            pnlMainItem.removeAll();
//            pnlMainItem.setPreferredSize(new Dimension(1000, 350));
//            pnlMainItem.setMinimumSize(new Dimension(1000, 350));
//            pnlMainItem.setLayout(new GridLayout(0, 3, 20, 20));
//
//            int start = (currentPage - 1) * itemsPerPage;
//            int end = Math.min(start + itemsPerPage, allCards.size());
//
//            // USE EXISTING ItemCards — DO NOT recreate
//            for (int i = start; i < end; i++) {
//                pnlMainItem.add(allCards.get(i));
//            }
//
//            pnlMainItem.revalidate();
//            pnlMainItem.repaint();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    
    public void loadItemsToPanel() {
        try {
            pnlMainItem.removeAll();
            pnlMainItem.setPreferredSize(new Dimension(1000, 350));
            pnlMainItem.setMinimumSize(new Dimension(1000, 350));
            pnlMainItem.setLayout(new GridLayout(0, 3, 20, 20));  // 3 rows x 3 cols
           
            List<Items> items = itemService.getItemsEachPage(currentPage, itemsPerPage);//for pagination function call JDBC code
   
            for (Items item : items) {

            	ItemCard card = new ItemCard(item, () -> { //inthis card there are function for adding items into cart via spinner and button
            	    loadCartTable();   // refresh after each add
            	});
            	
                pnlMainItem.add(card); // put all pagination keys and items data on MainItem panal
            }
            pnlMainItem.revalidate();
            pnlMainItem.repaint();
         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updatePageLabel() {
        lblPageNumber.setText("Page " + currentPage);
    }
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    
  //Show Item Card data in table Function***************************************************************
    
    void loadCartTable() // to add existing sql data on ui table
    {
    	List<CartWithItems> cartwithItem = this.cartService.showcartdata();
        DefaultTableModel model = (DefaultTableModel)this.tblCart.getModel();// create table in this method
        
        model.setRowCount(0); // without this code when add new items to cart previous data insert duplicate
        

        
        total = 0;
        
        for(CartWithItems item : cartwithItem)
        {
            Object [] row = new Object[5];
            row[0] = item.cart().id();
            row[1] = item.item_name();
            row[2] = formatter.format(item.item_price());
            row[3] = item.cart().quantity();
            row[4] = formatter.format(item.total_price());
         
           // add all columns in array and
            model.addRow(row); // put array in table
            total += item.total_price();   
            
        }    
        txtTotalPrice.setText(formatter.format(total)); //this code is for to look number is formattly
    }
    
    public void reloadAllItems()
    {
    	DefaultTableModel model = (DefaultTableModel)this.tblCart.getModel();
    	model.setRowCount(0);
    	this.loadCartTable();
    	
    }
    
  
 //-------------------------------------------------------------------------------------------------------------------------
    
 //money give back change function***************************************************************   
 
    private void updateChange() {
    	
    	
        total = Integer.parseInt(txtTotalPrice.getText().replaceAll(",", ""));
        String totalText = txtTotalPrice.getText().trim().replaceAll(",", "");
        
        String paidText = txtPaid.getText().trim();
        
        if (txtTotalPrice.getText().equals("0") ) {
            JOptionPane.showMessageDialog(this, "Please add items first");
            return;
        }
        
        if (txtPaid.getText().trim().isEmpty() & !txtTotalPrice.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Please Enter The Paid Amount");
            
            return;
        }
       
       
        if (!paidText.isEmpty()) {
            try {
                int paid = Integer.parseInt(paidText);

               
                if(paid >= total ) {
            		int change = paid - total;
                    txtChange.setText(formatter.format(change));   
                } else {
                	JOptionPane.showMessageDialog(this, "Insufficient Paid Balance");
                    txtChange.setText("0");
                }

            } catch (NumberFormatException e) {
            	 JOptionPane.showMessageDialog(this, "Invalid Paid Amount");
            }
        }
    }

    
 //-------------------------------------------------------------------------------------------------------------------------   
    
 //For time and date function and UI ***************************************************************
    public void setTime() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Date date = new Date();
						Thread.sleep(1000);
						SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa");
		                SimpleDateFormat df = new SimpleDateFormat("EEE, yyyy-MM-dd");

		                lbltime.setText(tf.format(date));
		                lbldate.setText(df.format(date));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}).start();		
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
 //Select row function******************************************************************************
    
//	public void valueSelect() {
//		this.tblCart.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent event) {
//				// do some actions here, for example
//				// print first column value from selected row
//				if (!event.getValueIsAdjusting()) {// getValueIsAdjusting() is true while the selection is still
//													// changing, so skipping when true avoids weird duplicate
//													// selections.
////					int row = tblCart.getSelectedRow();
////					if (row != -1) {
////						Integer id = (Integer) tblCart.getValueAt(row, 0);
////						selectedID = id;
////						String items = (String) tblCart.getValueAt(row, 1);
////						Integer qty = (Integer) tblCart.getValueAt(row, 2);
////						Integer price = (Integer) tblCart.getValueAt(row, 3);
////					}
////					System.out.println("Table row" + row);
//				} // this code is for role selected put into constructor bec want to select even
//					// after program start run
//			}
//		});
//	}
//    
//----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
// Reset Item in Cart function***************************************************************************
	
	 void removeItems()
	    {
		 
		 //for single select row remove
//	    	int row = tblCart.getSelectedRow();
//	    	if(row != -1) {
//	    		
//	    		Integer id = (Integer)tblCart.getValueAt(row, 0);
//	    		this.cartService.deleteItemsByCartId(id);
//	    		this.reloadAllItems();
		 
		 
		 //for multi select select row remove
		 int[] selectedRows = tblCart.getSelectedRows();
		 if(selectedRows.length != 0) {
			 for(int i = selectedRows.length -1; i >= 0; i--) {
				 int row = selectedRows[i];
				 Integer id =(Integer)tblCart.getValueAt(row, 0);
				 this.cartService.deleteItemsByCartId(id);
				 this.reloadAllItems();
			 }
		 }
	    	
	    }
	 
//----------------------------------------------------------------------------------------------------------------------------------------------------------------

	 //Reset the whole cart table function**************************************************************
	 
	 void resetCart()
	 {
	      this.cartService.resetCart();
	       this.reloadAllItems();
	       this.txtTotalPrice.setText("0");
	       this.txtPaid.setText("");
	       this.txtChange.setText("0");
	       this.txtarea.setText(null);
	       
	       this.txtTotalPrice.setEditable(false);
	       this.txtChange.setEditable(false);
	 }
	 
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
//	 public void outreceip() {
//		 
//		 List<CartWithItems> cartwithItem = this.cartService.showcartdata();
//		 String paid = formatter.format(txtPaid.getText());
//		 for(CartWithItems item : cartwithItem)
//	        {
//	            Object [] row = new Object[5];
//	            row[0] = item.cart().id();
//	            row[1] = item.item_name();
//	            row[2] = formatter.format(item.item_price());
//	            row[3] = item.cart().quantity();
//	            row[4] = formatter.format(item.total_price());
//	        
//		 
//			int id = 13400 + (int) (Math.random() * 2000);
//			txtarea.setText(
//			
//			"********************************* IT STORE ***********************************\n" +
//			"                                             Tel: +959 92500 64228\n\n" +
//
//			"Cashier ID:\t\t\t\t" + session.getUserId() + "\n" +
//			
//
//			"--------------------------------------------------------------------------------------------------\n" +
//			"Item\t\tPrice             Quantity\tAmount\n" +
//			
//			"--------------------------------------------------------------------------------------------------\n" +
//			item.item_name() +"\t" + formatter.format(item.item_price()) + "\t" + item.cart().quantity() +"\t" + formatter.format(item.total_price()) +"\n"+
//			"---------------------------------------------------------------------------------------------------\n\n" +
//
//			"SUB TOTAL:                                " + txtTotalPrice.getText() + "\n" +
//			"PAID:                                          " + paid + "\n" +
//			"CHANGE:                                   " + txtChange.getText() + "\n\n" +
//
//			"--------------------------------------------------------------------------------------------------\n" +
//			"Date: " + lbldate.getText() + "\t\t                                Time: " + lbltime.getText() + "\n" +
//			"*******************************************************************************\n" +
//			"                                     THANK YOU\n" +
//			"*******************************************************************************\n" +
//			"                             Program By Aung Khant Kyaw\n" +
//			"                          Contact : akkyaw.dev@gmail.com\n");
//	        }
//	        
//		}
	 public void outreceip() {
		 	
		 try {
			 if(txtTotalPrice.getText().trim().isEmpty() |
			            txtPaid.getText().trim().isEmpty() |
			            txtChange.getText().trim().isEmpty()) 
			 {
				 JOptionPane.showMessageDialog(this, "Please Put the Datas First","Missing Data",
		                    JOptionPane.WARNING_MESSAGE);
				 return;
			 }
			 
			 else {
				 this.receipForm();
				 List<CartWithItems> cartwithItem = this.cartService.showcartdata();
				    String paid = formatter.format(Double.parseDouble(txtPaid.getText()));

				    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
				    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");

				    String currentDate = sdfDate.format(new Date());
				    String currentTime = sdfTime.format(new Date());
				    
				    StringBuilder sb = new StringBuilder();

				    sb.append(" **********************************  IT STORE ***********************************\n");
				    sb.append("                                           Tel: +959 92500 64228\n\n");
				    sb.append(" Cashier ID:\t\t\t\t"+session.getUserId()).append("\n");
				    sb.append(" Receipt ID:\t\t\t\t").append(session.getUserId()).append("\n");
				    sb.append(" ---------------------------------------------------------------------------------------------------\n");
				    sb.append(" Item\t\tPrice\tQty\tAmount\n");
				    sb.append(" ---------------------------------------------------------------------------------------------------\n");

				    // ADD ALL ITEMS
				    for (CartWithItems item : cartwithItem) {
				        sb.append(" " +item.item_name()).append("\t")
				          .append(" " +formatter.format(item.item_price())).append("\t")
				          .append(" " +item.cart().quantity()).append("\t")
				          .append(" " +formatter.format(item.total_price())).append("\n");
				    }

				    sb.append(" ---------------------------------------------------------------------------------------------------\n");
				    sb.append(" SUB TOTAL:\t\t\t\t").append(txtTotalPrice.getText()).append("\n");
				    sb.append(" PAID:\t\t\t\t").append(paid).append("\n");
				    sb.append(" CHANGE:\t\t\t\t").append(txtChange.getText()).append("\n\n");

				    sb.append(" ---------------------------------------------------------------------------------------------------\n");
				    sb.append(" Date: ").append(currentDate)
				    .append("\t\t             Time: ").append(currentTime).append("\n");
				    sb.append(" **********************************************************************************\n");
				    sb.append("                                              	 THANK YOU\n");
				    sb.append(" **********************************************************************************\n");
				    sb.append("                                      Program By Aung Khant Kyaw\n");
				    sb.append("                                   Contact : akkyaw.dev@gmail.com\n");

				    txtarea.setText(sb.toString());
				    System.out.println( "TIME LABEL = " +lbldate.getText() + lbltime.getText());
				 
			 }
			 
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		    
		}

	 
//	    String receipt = String.format(
//	            "************************ IT Store*****************************\n" +
//	            "                   Tel: +959 92500 64228 \n" +
//	            "Cashier: %-80s\n" +
//	            "Manager: %-80s\n\n" +
//	            "-------------------------------------------------------------------------------------------\n" +
//	            "Items\t\tPrice\tQty\t\t Amount\n" +
//	            "-------------------------------------------------------------------------------------------\n" +
//	            "%-40s\t%-10s\t%-5s\t%-10s\n" +
//	            "-------------------------------------------------------------------------------------------\n\n" +
//	            "Sub Total\t\t\t\t\t\t\t\t%s\n" +
//	            "Paid\t\t\t\t\t\t\t\t%s\n" +
//	            "CHANGE\t\t\t\t\t\t\t\t%s\n\n" +
//	            "------------------------------------------------------------------------------------------\n\n" +
//	            "Date : %s\t\t\t\t\tTime: %s\n" +
//	            "************************************************************\n" +
//	            "                Thank You\n" +
//	            "************************************************************\n" +
//	            "                Program By Aung Khant Kyaw\n" +
//	            "                Contact : akkyaw.dev@gmail.com\n",
//	            cashierCode,
//	            managerCode,
//	            itemName, itemPrice, itemQty, itemAmount,
//	            subTotal, paid, change,
//	            date, time
//	        );

//	        System.out.println(receipt);
	 
	 
	 
	 
	 
	 
	 
	 
//
// 
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
        diareceip = new javax.swing.JDialog();
        pnlreceip = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtarea = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
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
        lblTotalPrice = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        lblPaid = new javax.swing.JLabel();
        txtPaid = new javax.swing.JTextField();
        lblChange = new javax.swing.JLabel();
        txtChange = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnPaid = new javax.swing.JButton();
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
                .addGroup(dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbleye, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dialoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)))
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

        txtarea.setColumns(20);
        txtarea.setRows(5);
        jScrollPane2.setViewportView(txtarea);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlreceipLayout = new javax.swing.GroupLayout(pnlreceip);
        pnlreceip.setLayout(pnlreceipLayout);
        pnlreceipLayout.setHorizontalGroup(
            pnlreceipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlreceipLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        pnlreceipLayout.setVerticalGroup(
            pnlreceipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlreceipLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlreceipLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(161, 161, 161))
        );

        javax.swing.GroupLayout diareceipLayout = new javax.swing.GroupLayout(diareceip.getContentPane());
        diareceip.getContentPane().setLayout(diareceipLayout);
        diareceipLayout.setHorizontalGroup(
            diareceipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diareceipLayout.createSequentialGroup()
                .addComponent(pnlreceip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );
        diareceipLayout.setVerticalGroup(
            diareceipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlreceip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGap(0, 0, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

            },
            new String [] {
                "Id", "Items", "Price", "Quantity", "Total_Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCart);
        if (tblCart.getColumnModel().getColumnCount() > 0) {
            tblCart.getColumnModel().getColumn(0).setMaxWidth(25);
            tblCart.getColumnModel().getColumn(1).setMaxWidth(300);
            tblCart.getColumnModel().getColumn(2).setMaxWidth(100);
            tblCart.getColumnModel().getColumn(3).setMaxWidth(60);
            tblCart.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        pnlButtons.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(102, 102, 102)));

        lblTotalPrice.setText("Total Price");

        txtTotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPriceActionPerformed(evt);
            }
        });

        lblPaid.setText("Paid");

        lblChange.setText("Change");

        txtChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChangeActionPerformed(evt);
            }
        });

        btnPrint.setText("Show Receip");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnPaid.setText("Paid");
        btnPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaidActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPaid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(lblChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPaid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtChange))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPaid)
                .addGap(12, 12, 12)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalPrice)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint))
                .addGap(8, 8, 8)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaid)
                    .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChange)
                    .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove)
                    .addComponent(btnPaid))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pnlDateTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 250, 250)));
        pnlDateTime.setForeground(new java.awt.Color(255, 255, 255));

        lbldate.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        lbltime.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        javax.swing.GroupLayout pnlDateTimeLayout = new javax.swing.GroupLayout(pnlDateTime);
        pnlDateTime.setLayout(pnlDateTimeLayout);
        pnlDateTimeLayout.setHorizontalGroup(
            pnlDateTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDateTimeLayout.createSequentialGroup()
                .addComponent(lbltime, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbldate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlDateTimeLayout.setVerticalGroup(
            pnlDateTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbltime, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
            .addComponent(lbldate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void txtTotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPriceActionPerformed

    private void txtChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChangeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChangeActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
//    	this.receipForm();
    	this.outreceip();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
       this.resetCart();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        this.removeItems();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaidActionPerformed
        
    	this.updateChange();
        
        
    }//GEN-LAST:event_btnPaidActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:sdf
    }//GEN-LAST:event_jButton2ActionPerformed

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
       

        // Build receipt
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	  //1. Create the login dialog (modal)
                MainFrame mainFrame = new MainFrame(); 
               
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
    private javax.swing.JButton btnPaid;
    private javax.swing.JButton btnPrevPage;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnlogin;
    private javax.swing.JDialog dialogin;
    private javax.swing.JDialog diareceip;
    private javax.swing.JDialog diaregister;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblPageNumber;
    private javax.swing.JLabel lblPaid;
    private javax.swing.JLabel lblTotalPrice;
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
    private javax.swing.JPanel pnlreceip;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextArea txtarea;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JPasswordField txtregipassowrd;
    private javax.swing.JPasswordField txtregirepassowrd;
    private javax.swing.JTextField txtregiusername;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
