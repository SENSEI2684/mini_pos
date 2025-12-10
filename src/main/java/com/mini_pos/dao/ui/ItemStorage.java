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
import com.mini_pos.dao.service.CategoriesService;
import com.mini_pos.dao.service.CategoriesServiceImpl;
import com.mini_pos.dao.service.ItemsService;
import com.mini_pos.dao.service.ItemsServiceImpl;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ImageCache;
import com.mini_pos.helper_function.ValidationException;


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
	CategoriesService catService = new CategoriesServiceImpl();
	
    public ItemStorage( ) {
    	 
        initComponents();
        LoadallItemsData();
        valueSelect();
        catvalueSelect();
        loadCategories();
        LoadallCategoryData();
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    //Load Data to Table*****************************************
    
    private void LoadallItemsData() {
        DefaultTableModel model = (DefaultTableModel) this.tblItems.getModel();
        model.setRowCount(0);
        try {
            List<ItemsWithCategories> items = itemsService.getAllItemsAndCategoryName();

            for (ItemsWithCategories item : items) {
                Object[] row = new Object[8];
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

        } catch (DaoException de) {
          JOptionPane.showMessageDialog(this, de.getMessage(), "DataBase Error", JOptionPane.ERROR_MESSAGE);
          de.printStackTrace();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(this, "Unexpected error while loading items!", "Error", JOptionPane.ERROR_MESSAGE);
          e.printStackTrace();
        }
    }
    
    private void LoadallCategoryData() {
        DefaultTableModel model = (DefaultTableModel) this.tbl_Category.getModel();
        model.setRowCount(0);
        try {
            List<Categories> cats = catService.getAllCategories();

            for (Categories cat : cats) {
                Object[] row = new Object[2];
                row[0] = cat.id();
                row[1] = cat.category_name();
             
                model.addRow(row);
            }

        } catch (DaoException de) {
          JOptionPane.showMessageDialog(this, de.getMessage(), "DataBase Error", JOptionPane.ERROR_MESSAGE);
          de.printStackTrace();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(this, "Unexpected error while loading Categories!", "Error", JOptionPane.ERROR_MESSAGE);
          e.printStackTrace();
        }
    }
    
    
    
   private void reloadAllItems()
    {
	    reloadAllCategories();
    	DefaultTableModel model = (DefaultTableModel)this.tblItems.getModel();
    	model.setRowCount(0);
    	this.LoadallItemsData();
    	
    }
   
   private void reloadAllCategories()
   {
	   	DefaultTableModel model = (DefaultTableModel)this.tbl_Category.getModel();
    	model.setRowCount(0);
    	this.loadCategories();
    	this.LoadallCategoryData();
   }
    
 //----------------------------------------------------------------------------------------------------------------------------------------------------------
   
    //Data row select********************************************
   
 private void catvalueSelect() {
	 tbl_Category.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     this.tbl_Category.getSelectionModel().addListSelectionListener(new ListSelectionListener()
     {
     public void valueChanged(ListSelectionEvent event) 
	     {
	    	 try
	    	 {
	    		 if (!event.getValueIsAdjusting())
	    		 {
	 		     	int row = tbl_Category.getSelectedRow();
	 		     	
	 			     	if(row != -1) 
	 			     	{
	 			     		String cat_name = (String)tbl_Category.getValueAt(row, 1);
				     		txt_Category_Name.setText(cat_name);
	 			     	}
	    		 }
	    	 }
	    	 catch(Exception e)
	    	 {
	    		 e.printStackTrace();
	    	 }
	     } 
	  });
 }
   
   
	private void valueSelect() {
		tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tblItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				try {
					// do some actions here, for example
					// print first column value from selected row

					if (!event.getValueIsAdjusting()) // getValueIsAdjusting() is true while the selection is still
														// changing, so skipping when true avoids weird duplicate
														// selections.
					{
						int row = tblItems.getSelectedRow();

						if (row != -1) {
							Integer id = (Integer) tblItems.getValueAt(row, 0);
							selectedID = id;

							String item_code = (String) tblItems.getValueAt(row, 1);
							txtItem_code.setText(item_code);

							String name = (String) tblItems.getValueAt(row, 2);
							txtName.setText(name);

							Integer price = (Integer) tblItems.getValueAt(row, 3);
							txtPrice.setText(String.valueOf(price));

							Object quantity = tblItems.getValueAt(row, 4);
							spnQuantity.setValue(quantity);

//							String photoPath = (String) tblItems.getValueAt(row, 5);
//							if (photoPath != null && !photoPath.isEmpty()) {
//
//								ImageIcon icon = new ImageIcon(photoPath);
//								Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
//								lbl_photo.setIcon(new ImageIcon(img));
//							}

							String photoFileName = (String) tblItems.getValueAt(row, 5);
							if (photoFileName != null && !photoFileName.isEmpty()) {
							    ImageIcon icon = ImageCache.get(photoFileName); // <-- get from cache/project folder
							    if (icon != null) {
							        lbl_photo.setIcon(icon);
							    } else {
							        lbl_photo.setIcon(null);
							    }
							}
							
							Object categoryObj = tblItems.getValueAt(row, 6);
							if (categoryObj != null) {
								// Iterate through all combo box items
								for (int i = 0; i < cboCategory.getItemCount(); i++) {
									Categories cat = cboCategory.getItemAt(i);
									if (cat.category_name().equals(categoryObj.toString())) {
										cboCategory.setSelectedIndex(i);
										break;
									}
								}
							}

//			     		Object Category = tblItems.getValueAt(row, 6); 
//			     		if (Category != null) 
//			     		{ 
//			     			cboCategory.setSelectedItem(Category.toString());
//			     			System.out.println("Cat null");
//			     		}

						}
						System.out.println("Table row" + row);
					} // this code is for role selected put into constructor bec want to select even
						// after program start run

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

 //----------------------------------------------------------------------------------------------------------------------------------------------------------

  //Item CRUD Functions
 
 public void loadCategories() {
	    cboCategory.removeAllItems();

	  
	    cboCategory.addItem(new Categories(0, "None"));

	  
	    
	    List<Categories> categories;
		try {
			categories = catService.getAllCategories();
			 for (Categories cat : categories) 
			    {
			        cboCategory.addItem(cat); 
			    }
		} catch (DaoException de) {
	          JOptionPane.showMessageDialog(this, de.getMessage(), "DataBase Error", JOptionPane.ERROR_MESSAGE);
	          de.printStackTrace();
	        } catch (Exception e) {
	          JOptionPane.showMessageDialog(this, "Unexpected error while loading Categories!", "Error", JOptionPane.ERROR_MESSAGE);
	          e.printStackTrace();
	        }
	   
	}
 
	private void addCategory() {
		String cat_name = txt_Category_Name.getText().trim();
		Categories cat = new Categories(0, cat_name);

		try {
			catService.addCategories(cat);
			JOptionPane.showMessageDialog(this, "Category added successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			reloadAllItems();
			clearCategoryInput();
		} catch (ValidationException ve) {
			JOptionPane.showMessageDialog(this, ve.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		} catch (DaoException de) {
			JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			de.printStackTrace();
		}
	}
 
		
 private void addItems() {
	    String item_code = txtItem_code.getText().trim();
	    String itemName = txtName.getText().trim();
	    String priceText = txtPrice.getText().trim();
	    Integer qty = (Integer) spnQuantity.getValue();
	    Categories selected = (Categories) cboCategory.getSelectedItem();

	    int price;
	    try {
	        price = Integer.parseInt(priceText);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Price must be a number!", "Warning", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    Integer cat_id = selected != null ? selected.id() : 0;
	    Items item = new Items(0, item_code, itemName, price, qty, null, cat_id, null);

	    try {
	        itemsService.saveItems(item, path2);
	        JOptionPane.showMessageDialog(this, "Item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	        reloadAllItems();
	        clearItemsInput();
	    } catch (ValidationException ve) {
	        JOptionPane.showMessageDialog(this, ve.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
	    } catch (DaoException de) {
	        JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        de.printStackTrace();
	    }
	}

	private void deleteItems() {
		
			int row = tblItems.getSelectedRow();
			
			// 1️⃣ Check if user has selected a row
			if (row == -1 |  txtItem_code.getText().trim().isEmpty()) 
			{
				JOptionPane.showMessageDialog(this, "Please select an item to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
		        return;
			} 
			
		
		    String item_code;
		    Object codeValue = tbl_Category.getValueAt(row, 1);
		    if (codeValue == null || String.valueOf(codeValue).trim().isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Item Code is empty!", "Warning", JOptionPane.WARNING_MESSAGE);
		        return;
		    }
		    item_code = String.valueOf(codeValue);
		    
		    
		    
	        int result = JOptionPane.showConfirmDialog(
	                this,
	                "Confirm that you want to delete " + item_code + "?",
	                "Delete",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.WARNING_MESSAGE
	        );
	        
	    
	        if (result == JOptionPane.YES_OPTION) {
	        try {
	            itemsService.deleteItemsByItemCode(item_code);
	            JOptionPane.showMessageDialog(this, "Item deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            reloadAllItems();
	            clearItemsInput();
	        } catch (ValidationException ve) {
	            JOptionPane.showMessageDialog(this, ve.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
	        } catch (DaoException de) {
	            JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            de.printStackTrace();
	        }
	    }
	}
	
	
	private void updateCategory() {
		
		int row = tbl_Category.getSelectedRow();
		if (row == -1  ) {
	        JOptionPane.showMessageDialog(this, "Please select a category to update!",
	                "Warning", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	   
	    int id;	    
	    Object idValue =tbl_Category.getValueAt(row, 0);	    
	    if(idValue == null || String.valueOf(idValue).trim().isEmpty()) {
	    	 JOptionPane.showMessageDialog(this, "ID value is empty!", "Warning", JOptionPane.WARNING_MESSAGE);
	         return;
	    }
	    id = Integer.parseInt(String.valueOf(idValue));
//	    id = (Integer)idValue;
	    
	    String cat_name = (String)txt_Category_Name.getText().trim();
	    
	    
		try {
			int result = JOptionPane.showConfirmDialog(this, "Confirm update?", "Update",
	                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
	        if (result == JOptionPane.YES_OPTION) {
	            catService.updateCategories(id, cat_name);
	   
	            JOptionPane.showMessageDialog(this, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            reloadAllItems();
	            clearCategoryInput();
	            }
		}catch (ValidationException ve) {
	        JOptionPane.showMessageDialog(this, ve.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
	    } catch (DaoException de) {
	        JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        de.printStackTrace();
	    }
	}
	
	
	private void updateItems() {
	    String item_code = txtItem_code.getText().trim();
	    String priceText = txtPrice.getText().trim();
	    int price;

	    try {
	        price = Integer.parseInt(priceText);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, " Price must be a number!", "Warning", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    try {
	        int result = JOptionPane.showConfirmDialog(this, "Confirm update?", "Update",
	                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

	        if (result == JOptionPane.YES_OPTION) {
	            itemsService.updateItems(price, item_code);
	            JOptionPane.showMessageDialog(this, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            reloadAllItems();
	            clearItemsInput();
	        }
	    } catch (ValidationException ve) {
	        JOptionPane.showMessageDialog(this, ve.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
	    } catch (DaoException de) {
	        JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        de.printStackTrace();
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
    private void clearCategoryInput()
    {
    	this.txt_Category_Name.setText("");
    		
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
        btn_reset = new javax.swing.JButton();
        pnlCategotyAll = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Category = new javax.swing.JTable();
        lbl_Category_Name = new javax.swing.JLabel();
        txt_Category_Name = new javax.swing.JTextField();
        btn_Add_Category = new javax.swing.JButton();
        btn_Update_Category = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();

        pnlRoot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 250, 250)));

        pnlItemTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 3));

        javax.swing.GroupLayout pnlItemTableLayout = new javax.swing.GroupLayout(pnlItemTable);
        pnlItemTable.setLayout(pnlItemTableLayout);
        pnlItemTableLayout.setHorizontalGroup(
            pnlItemTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );
        pnlItemTableLayout.setVerticalGroup(
            pnlItemTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 647, Short.MAX_VALUE)
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

        btn_reset.setText("Reset Button");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
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
                            .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlItemControlLayout.createSequentialGroup()
                                    .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblItem_Code, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtItem_code, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlItemControlLayout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlItemControlLayout.createSequentialGroup()
                                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(69, 69, 69)
                                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btn_reset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                            .addComponent(btnAdd1)))
                    .addGroup(pnlItemControlLayout.createSequentialGroup()
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlItemControlLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lbl_photo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(pnlItemControlLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtItem_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblItem_Code)
                                    .addComponent(btnUpdate1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblQuantity)
                                    .addComponent(btnDelete1))
                                .addGap(26, 26, 26)))
                        .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrice)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_upload)
                            .addComponent(btn_reset))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pnlItemControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategory)
                    .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlCategotyAll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 3));

        tbl_Category.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Category_Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_Category);
        if (tbl_Category.getColumnModel().getColumnCount() > 0) {
            tbl_Category.getColumnModel().getColumn(0).setMinWidth(50);
            tbl_Category.getColumnModel().getColumn(0).setMaxWidth(50);
            tbl_Category.getColumnModel().getColumn(1).setMinWidth(200);
            tbl_Category.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        lbl_Category_Name.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_Category_Name.setText("Category Name");

        btn_Add_Category.setText("Add Category");
        btn_Add_Category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Add_CategoryActionPerformed(evt);
            }
        });

        btn_Update_Category.setText("Update Category");
        btn_Update_Category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Update_CategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCategotyAllLayout = new javax.swing.GroupLayout(pnlCategotyAll);
        pnlCategotyAll.setLayout(pnlCategotyAllLayout);
        pnlCategotyAllLayout.setHorizontalGroup(
            pnlCategotyAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCategotyAllLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCategotyAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Category_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Add_Category, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(txt_Category_Name)
                    .addComponent(btn_Update_Category, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlCategotyAllLayout.setVerticalGroup(
            pnlCategotyAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnlCategotyAllLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_Category_Name)
                .addGap(18, 18, 18)
                .addComponent(txt_Category_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btn_Add_Category)
                .addGap(29, 29, 29)
                .addComponent(btn_Update_Category)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItemTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRootLayout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(pnlItemTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRootLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))))
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
       this.updateItems();
        
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void txtItem_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItem_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItem_codeActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        this.deleteItems();
        
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btn_Add_CategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Add_CategoryActionPerformed
        this.addCategory();
    }//GEN-LAST:event_btn_Add_CategoryActionPerformed

    private void btn_Update_CategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Update_CategoryActionPerformed
        this.updateCategory();
    }//GEN-LAST:event_btn_Update_CategoryActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
       this.clearItemsInput();
       loadCategories();
    }//GEN-LAST:event_btn_resetActionPerformed

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

    	          
    	            File destFile = new File(imageDir, selectedFile.getName());

    	           
    	            Files.copy(
    	                    selectedFile.toPath(),
    	                    destFile.toPath(),
    	                    StandardCopyOption.REPLACE_EXISTING
    	            );

    	            
    	            BufferedImage bi = ImageIO.read(destFile);
    	            Image img = bi.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    	            lbl_photo.setIcon(new ImageIcon(img));

    	            
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
    private javax.swing.JButton btn_Add_Category;
    private javax.swing.JButton btn_Update_Category;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_upload;
    private javax.swing.JComboBox<Categories> cboCategory;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblItem_Code;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lbl_Category_Name;
    private javax.swing.JLabel lbl_photo;
    private javax.swing.JPanel pnlCategotyAll;
    private javax.swing.JPanel pnlItemControl;
    private javax.swing.JPanel pnlItemTable;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTable tblItems;
    private javax.swing.JTable tbl_Category;
    private javax.swing.JTextField txtItem_code;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txt_Category_Name;
    // End of variables declaration//GEN-END:variables
}
