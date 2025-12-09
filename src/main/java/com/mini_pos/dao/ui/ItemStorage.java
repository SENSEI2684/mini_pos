/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mini_pos.dao.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mini_pos.dao.CategoriesDao;
import com.mini_pos.dao.etinity.Categories;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.etinity.ItemsWithCategories;
import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.impl.CategoriesDaoImpl;
import com.mini_pos.dao.service.ItemsService;
import com.mini_pos.dao.service.ItemsServiceImpl;


/**
 *
 * @author User
 */
public class ItemStorage extends javax.swing.JFrame {

    /**
     * Creates new form ItemStorage
     */
	Integer selectedID;
	String path2 = null;
	
	ItemsService itemsService = new ItemsServiceImpl();
	CategoriesDao cateDao = new CategoriesDaoImpl();
	
    public ItemStorage() {
        initComponents();
        LoadallItemsData();
        valueSelect();
        loadCategories();
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    //Load Data to Table*****************************************
    
    private void LoadallItemsData()
    {
        List<ItemsWithCategories> items = this.itemsService.getAllItemsWithCategoryName();
        DefaultTableModel model = (DefaultTableModel)this.tblItems.getModel();
        for(ItemsWithCategories item : items)
        {
            Object [] row = new Object[8];
            row[0] = item.item().id();
            row[1] = item.item().item_code();
            row[2] = item.item().name();
            row[3] = item.item().price();
            row[4] = item.item().quantity();
            row[5] = item.item().photo();
            row[6] = item.category_name();
            row[7] = item.item().created_at();
            model.addRow(row);
        }
    }
    
   private void reloadAllItems()
    {
    	DefaultTableModel model = (DefaultTableModel)this.tblItems.getModel();
    	model.setRowCount(0);
    	this.LoadallItemsData();
    }
    
 //----------------------------------------------------------------------------------------------------------------------------------------------------------
   
    //Data row select********************************************
   
 private void valueSelect() {
	 tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     this.tblItems.getSelectionModel().addListSelectionListener(new ListSelectionListener()
     {
     public void valueChanged(ListSelectionEvent event) 
	     {
	    	 try
	    	 {
	             // do some actions here, for example
		         // print first column value from selected row
		    	 
		     	if (!event.getValueIsAdjusting()) //getValueIsAdjusting() is true while the selection is still changing, so skipping when true avoids weird duplicate selections.
		     	{
		     	int row = tblItems.getSelectedRow();
		     	
			     	if(row != -1) 
			     	{
			     		Integer id = (Integer)tblItems.getValueAt(row, 0);
			     		selectedID = id;
			     		
			     		String item_code = (String)tblItems.getValueAt(row, 1);
			     		txtItem_code.setText(item_code);
			     		
			     		String name = (String)tblItems.getValueAt(row, 2);
			     		txtName.setText(name);
			     		
			     		Object price = tblItems.getValueAt(row, 3);
			     		txtPrice.setText(price.toString());
			     		
		     		
			     		Object quantity = tblItems.getValueAt(row, 4);
			     		spnQuantity.setValue(quantity);
			     		
			     		String photoPath = (String)tblItems.getValueAt(row, 5);
			     		if (photoPath != null && !photoPath.isEmpty()) {

			     		    ImageIcon icon = new ImageIcon(photoPath);
			     		    Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			     		    lbl_photo.setIcon(new ImageIcon(img));
			     		}
			     		
			     		Object Category = tblItems.getValueAt(row, 6);
			     		if (Category != null) 
			     		{
			     		    cboCategory.setSelectedItem(Category.toString());
			     		}
			     	}
		         System.out.println("Table row" + row);
		     }// this code is for role selected put into constructor bec want to select even after program start run
		     	
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	
	   }
     });
 }
    
 //----------------------------------------------------------------------------------------------------------------------------------------------------------

  //Item CRUD Functions
 
 private void loadCategories() {
	    cboCategory.removeAllItems();

	  
	    cboCategory.addItem(new Categories(0, "None"));

	  
	    
	    List<Categories> categories = cateDao.getAllCategories();
	    for (Categories cat : categories) 
	    {
	        cboCategory.addItem(cat); 
	    }
	}
 
	private void addItems()// use CRUD savemethod and ui add section
	{
		String item_code = this.txtItem_code.getText();
		String itemName = this.txtName.getText();
		String priceText = this.txtPrice.getText().trim();
		Integer qty = (Integer)this.spnQuantity.getValue();

		Categories selected = (Categories) cboCategory.getSelectedItem();
		
		
//		ItemsWithCategories users = new ItemsWithCategories(items,category);
		
		try {
			
			if(itemName.trim().isEmpty() | priceText.trim().isEmpty() | item_code.trim().isEmpty() | selected == null || selected.id() == 0) 
			{
				JOptionPane.showMessageDialog(this, "Please Input ItemName and Price and Item_Code and valid category!");
				return;
			}			
			
			
			 int price;

			    try 
			    {
			        price = Integer.parseInt(priceText);
			    } 
			    catch (NumberFormatException e) 
			    {
			        JOptionPane.showMessageDialog(this, "Price must be a number!");
			        return;
			    }
			    
			    Integer cat_id = selected.id();
			    Items items = new Items(0,item_code,itemName,price,qty,null,cat_id,null);
			
			    try 
			    {
			        itemsService.saveItems(items, path2);
			        JOptionPane.showMessageDialog(this, "Items added successfully!");
			        reloadAllItems();
			        clearItemsInput();

			    } 
			    catch (Exception e) 
			    {
			        e.printStackTrace();
			        JOptionPane.showMessageDialog(this, "Item Code already exists!");
			    }
					
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Item Code Already Exist");
		}

	}

	private void deleteItems() {
		try {
			int row = tblItems.getSelectedRow();
			if (row == -1 |  txtItem_code.getText().trim().isEmpty()) 
			{
				JOptionPane.showMessageDialog(this, "Please Input Item_Code that u want to Delete!");
			} 
			
			String item_code = (String) tblItems.getValueAt(row,1);
	               
			
	        int result = JOptionPane.showConfirmDialog(
	                this,
	                "Confirm that you want to delete " + item_code + "?",
	                "Delete",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.WARNING_MESSAGE
	        );
	        if (result == JOptionPane.YES_OPTION)
	        {
	            itemsService.deleteItemsByItemCode(item_code);
	            JOptionPane.showMessageDialog(this, "Items deleted successfully");
	            reloadAllItems();
	            clearItemsInput();
	        }
			
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Failed to Delete Items");
			e.printStackTrace();
		}
	}

	
	

	
	private void updateAccount() {
		try 
		{
				String item_code = txtItem_code.getText();
				String priceText = this.txtPrice.getText().trim();
			  
			      
			    if (item_code.trim().isEmpty() |priceText.trim().isEmpty()) 
			    {
			        JOptionPane.showMessageDialog(this, "Please enter Item_Code & Price that you want to Update!");
			        return;
			    }
			   
				 int price;

				    try 
				    {
				        price = Integer.parseInt(priceText);
				    } 
				    catch (NumberFormatException e) 
				    {
				        JOptionPane.showMessageDialog(this, "Price must be a number!");
				        return;
				    }
			    
			    int result = JOptionPane.showConfirmDialog(this, "Confirm that you want to Update  " ,
						"Update", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

						
			    try 
			    {
			    	if (result == JOptionPane.YES_OPTION) 
					{
						
						itemsService.updateItems(price, item_code);
					    reloadAllItems();
					    JOptionPane.showMessageDialog(this, "Account Updated Successfully!");
					    clearItemsInput();
					}

			    } 
			    catch (Exception e) 
			    {
			        e.printStackTrace();
			        JOptionPane.showMessageDialog(this, "Item Code already exists!");
			    }
					
		    
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Failed to Update Item");
			e.printStackTrace();
		}
	}
	
	
 
    private void clearItemsInput()// this is for user experience after click add button i want to clear previous data write in text box
    {
    	this.txtName.setText("");
    	this.txtItem_code.setText("");
    	this.txtPrice.setText("");
    	this.cboCategory.setSelectedIndex(0);
    	this.txtPrice.setText("");
    	this.lbl_photo.setIcon(null);
    }
 
    
    
 //----------------------------------------------------------------------------------------------------------------------------------------------------------
   
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    	
    	
    	
        pnlRoot = new javax.swing.JPanel();
        pnlItemTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        pnlItemControl = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblItem_Code = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtItem_code = new javax.swing.JTextField();
        spnQuantity = new javax.swing.JSpinner();
        txtPrice = new javax.swing.JTextField();
        cboCategory = new javax.swing.JComboBox<>();
        btnAdd1 = new javax.swing.JButton();
        btnUpdate1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        lbl_photo = new javax.swing.JLabel();
        btn_upload = new javax.swing.JButton();
        pnlCategotyAll = new javax.swing.JPanel();

        pnlRoot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 250, 250)));

        pnlItemTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 3));

        tblItems.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Item_code", "Name", "Price", "Quantity", "Photo", "Category", "Created_at"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblItems);
        if (tblItems.getColumnModel().getColumnCount() > 0) {
            tblItems.getColumnModel().getColumn(0).setMinWidth(50);
            tblItems.getColumnModel().getColumn(0).setMaxWidth(50);
            tblItems.getColumnModel().getColumn(1).setMinWidth(100);
            tblItems.getColumnModel().getColumn(1).setMaxWidth(100);
            tblItems.getColumnModel().getColumn(2).setMinWidth(350);
            tblItems.getColumnModel().getColumn(2).setMaxWidth(350);
            tblItems.getColumnModel().getColumn(3).setMinWidth(200);
            tblItems.getColumnModel().getColumn(3).setMaxWidth(200);
            tblItems.getColumnModel().getColumn(4).setMinWidth(100);
            tblItems.getColumnModel().getColumn(4).setMaxWidth(100);
            tblItems.getColumnModel().getColumn(5).setMinWidth(300);
            tblItems.getColumnModel().getColumn(5).setMaxWidth(300);
            tblItems.getColumnModel().getColumn(6).setMinWidth(100);
            tblItems.getColumnModel().getColumn(6).setMaxWidth(100);
            tblItems.getColumnModel().getColumn(7).setMinWidth(200);
            tblItems.getColumnModel().getColumn(7).setMaxWidth(200);
        }

        javax.swing.GroupLayout pnlItemTableLayout = new javax.swing.GroupLayout(pnlItemTable);
        pnlItemTable.setLayout(pnlItemTableLayout);
        pnlItemTableLayout.setHorizontalGroup(
            pnlItemTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItemTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlItemTableLayout.setVerticalGroup(
            pnlItemTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItemTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );

        pnlItemControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 3));

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblName.setText("Name");

        lblItem_Code.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblItem_Code.setText("Item_code");

        lblQuantity.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblQuantity.setText("Quantity");

        lblPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPrice.setText("Price");

        lblCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCategory.setText("Category");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtItem_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItem_codeActionPerformed(evt);
            }
        });

        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        cboCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cboCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoryActionPerformed(evt);
            }
        });

        btnAdd1.setText("Add Item");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        btnUpdate1.setText("Update Price");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });

        btnDelete1.setText("Delete Item");
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        lbl_photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_photo.setPreferredSize(new java.awt.Dimension(120, 120));

        btn_upload.setText("Upload Photo");
        btn_upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_uploadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlItemControlLayout = new javax.swing.GroupLayout(pnlItemControl);
        pnlItemControl.setLayout(pnlItemControlLayout);
        pnlItemControlLayout.setHorizontalGroup(
            pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItemControlLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlItemControlLayout.createSequentialGroup()
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlItemControlLayout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(69, 69, 69)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlItemControlLayout.createSequentialGroup()
                                    .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblItem_Code, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtItem_code, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnDelete1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))))
                    .addGroup(pnlItemControlLayout.createSequentialGroup()
                        .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_photo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_upload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(87, 87, 87))
        );
        pnlItemControlLayout.setVerticalGroup(
            pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItemControlLayout.createSequentialGroup()
                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlItemControlLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd1))
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlItemControlLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblItem_Code)
                                    .addComponent(txtItem_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27))
                            .addGroup(pnlItemControlLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(btnUpdate1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblQuantity)
                            .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(btnDelete1))
                    .addGroup(pnlItemControlLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lbl_photo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrice)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_upload))))
                .addGap(24, 24, 24)
                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategory)
                    .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        pnlCategotyAll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 3));

        javax.swing.GroupLayout pnlCategotyAllLayout = new javax.swing.GroupLayout(pnlCategotyAll);
        pnlCategotyAll.setLayout(pnlCategotyAllLayout);
        pnlCategotyAllLayout.setHorizontalGroup(
            pnlCategotyAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
        );
        pnlCategotyAllLayout.setVerticalGroup(
            pnlCategotyAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlItemTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addComponent(pnlItemControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCategotyAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGroup(pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlItemControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCategotyAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItemTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void cboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCategoryActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // TODO add your handling code here:
    	this.addItems();
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
       this.updateAccount();
        
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void txtItem_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItem_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItem_codeActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        this.deleteItems();
        
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btn_uploadActionPerformed(java.awt.event.ActionEvent evt) {                                           
    	 JFileChooser chooser = new JFileChooser();

    	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

    	        File selectedFile = chooser.getSelectedFile();

    	        try {
    	            // ✅ Project target folder
    	            String projectPath = System.getProperty("user.dir");
    	            File imageDir = new File(projectPath + "/src/main/resources/static/images/");

    	            if (!imageDir.exists()) {
    	                imageDir.mkdirs();
    	            }

    	            // ✅ New file path in your project
    	            File destFile = new File(imageDir, selectedFile.getName());

    	            // ✅ Copy image to your project folder
    	            Files.copy(
    	                    selectedFile.toPath(),
    	                    destFile.toPath(),
    	                    StandardCopyOption.REPLACE_EXISTING
    	            );

    	            // ✅ Scale & preview
    	            BufferedImage bi = ImageIO.read(destFile);
    	            Image img = bi.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    	            lbl_photo.setIcon(new ImageIcon(img));

    	            // ✅ Save only file name (for DB)
    	            path2 = selectedFile.getName();

    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
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
            java.util.logging.Logger.getLogger(ItemStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemStorage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.JButton btn_upload;
    private javax.swing.JComboBox<Categories> cboCategory;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblItem_Code;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lbl_photo;
    private javax.swing.JPanel pnlCategotyAll;
    private javax.swing.JPanel pnlItemControl;
    private javax.swing.JPanel pnlItemTable;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtItem_code;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
