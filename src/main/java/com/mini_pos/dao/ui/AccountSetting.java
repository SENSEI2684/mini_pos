/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mini_pos.dao.ui;

import java.awt.Image;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mini_pos.dao.etinity.Role;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.service.UserService;
import com.mini_pos.dao.service.UserServiceImpl;
import com.mini_pos.helper_function.PasswordHide;


/**
 *
 * @author User
 */
public class AccountSetting extends javax.swing.JFrame {

	/**
	 * Creates new form AccountSettingFrame
	 */
	private ImageIcon eyeIcon;
	private ImageIcon eyeHideIcon;
	private ImageIcon eyeIcon1;
	private ImageIcon eyeHideIcon1;
	private boolean passwordHidden = true;
	private boolean repasswordHidden = true;

	Integer selectedID;

	UserService userService = new UserServiceImpl();

	public AccountSetting() {
		initComponents();
		initPasswordFeatures();
		reloadAllItems();
		valueSelect();
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//password hide and show function***************************************************************

	private ImageIcon resizeIcon(ImageIcon icon, int w, int h) {
		Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}


	public void initPasswordFeatures() {
		eyeIcon = resizeIcon(new ImageIcon(getClass().getResource("/static/logo/show.png")), 20, 20);
		eyeHideIcon = resizeIcon(new ImageIcon(getClass().getResource("/static/logo/hide.png")), 20, 20);

		eyeIcon1 = resizeIcon(new ImageIcon(getClass().getResource("/static/logo/show.png")), 20, 20);
		eyeHideIcon1 = resizeIcon(new ImageIcon(getClass().getResource("/static/logo/hide.png")), 20, 20);

	    boolean[] passHidden = { true };
	    boolean[] rePassHidden = { true };

	    PasswordHide.setupPasswordToggle(lbleyeicon, txtPassword, eyeIcon, eyeHideIcon, passHidden);
	    PasswordHide.setupPasswordToggle(lbleyeicon1, txtrepassword, eyeIcon, eyeHideIcon, rePassHidden);

	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	    

	// Select row
	// function******************************************************************************

	public void valueSelect() {
		this.tblAccounts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// do some actions here, for example
				// print first column value from selected row
				if (!event.getValueIsAdjusting()) {// getValueIsAdjusting() is true while the selection is still
//														// changing, so skipping when true avoids weird duplicate
//														// selections.
					int row = tblAccounts.getSelectedRow();
					if (row != -1) {
//						Integer id = (Integer) tblAccounts.getValueAt(row, 0);
//						selectedID = id;
						String username = (String) tblAccounts.getValueAt(row, 0);
						txtUsername.setText(username);
						String password = (String) tblAccounts.getValueAt(row, 1);
						txtPassword.setText(password);
//						String rolestr = (String) tblAccounts.getValueAt(row, 2);
//						comAccountType.setSelectedItem(rolestr);
						
						Role role = (Role) tblAccounts.getValueAt(row, 2);
						comAccountType.setSelectedItem(role.name().toString());
						
					
				
					}
//						System.out.println("Table row" + row);
				} // this code is for role selected put into constructor bec want to select even
//						// after program start run
			}
		});
	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	   	    

	// Show Items data in table
	// Function***************************************************************

	void LoadallItemsData() {
		List<Users> users = this.userService.getAllUsers();
		DefaultTableModel model = (DefaultTableModel) this.tblAccounts.getModel();
		for (Users user : users) {
			Object[] row = new Object[5];
			
			row[0] = user.username();
			row[1] = user.password();
			row[2] = user.role();
			row[3] = user.approved();
			row[4] = user.created_at();
			model.addRow(row);
		}
	}

	void reloadAllItems() {
		DefaultTableModel model = (DefaultTableModel) this.tblAccounts.getModel();
		model.setRowCount(0);
		this.LoadallItemsData();
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------		 
	// CRUD Functions

	private void addItems()// use CRUD savemethod and ui add section
	{
		String username = this.txtUsername.getText();
		String password = new String(txtPassword.getPassword());
		String roleStr = (String) this.comAccountType.getSelectedItem();
		
		Role role = Role.valueOf(roleStr.toUpperCase());

		Users users = new Users(0, username, password, role, true, null);
		
		try {
			this.userService.registerUser(users);
			JOptionPane.showMessageDialog(this, "Items Save Successfully");// for to show popup message
			this.reloadAllItems();
			this.clearItemsInput();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to save user");
		}

	}

	private void deleteItems() {
		int row = tblAccounts.getSelectedRow();
		if (row != -1) {

			String username = (String) tblAccounts.getValueAt(row, 1);
			txtUsername.setText(username);

			int result = JOptionPane.showConfirmDialog(this, "Confirm that you want to Delete item_code " + username,
					"Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				// user clicked YES
				this.userService.deleteaAccWithUsername(username);
				this.reloadAllItems();
			} else {
				// user clicked NO or closed
				System.out.println("Delete canceled");
			}
			this.clearItemsInput();
		}
		

		}

	
	private void updateAccount() {
		String username = this.txtUsername.getText();
		String password = new String(txtPassword.getPassword()); 
		
//		Integer cat_id = Integer.parseInt(this.cboCategory_id.getSelectedItem().toString());// need to change to Integer

		String rolestr = (String) comAccountType.getSelectedItem();
		Role role = Role.valueOf(rolestr); 
		
		this.userService.updateUser(username, password, role);
		

		this.reloadAllItems();
		JOptionPane.showMessageDialog(this, "Account Update Successfully!!");// for to show popup message
		this.clearItemsInput();
	}
	
	private void approve() {
		
		
		String username = this.txtUsername.getText();
		
		if (username == null || username.trim().isEmpty()) 
		{
		    JOptionPane.showMessageDialog(this, "Please select a user first!");
		    return;
		}
		else
		{
			this.userService.approveUser(true, username);
			this.reloadAllItems();
			JOptionPane.showMessageDialog(this, "Account Approve Successfully!!");// for to show popup message
			this.clearItemsInput();
		}
		
		
	}
//
	void clearItemsInput()
	{
		this.txtUsername.setText("");
		this.txtPassword.setText("");
		this.txtrepassword.setText("");
		this.comAccountType.setSelectedIndex(0);
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------		
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAccSettingMainForm = new javax.swing.JPanel();
        pnlAccLabel = new javax.swing.JPanel();
        lblAccountSetting = new javax.swing.JLabel();
        pnlAccSetting = new javax.swing.JPanel();
        pnlinputdata = new javax.swing.JPanel();
        lblusername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtrepassword = new javax.swing.JPasswordField();
        lbleyeicon1 = new javax.swing.JLabel();
        lbleyeicon = new javax.swing.JLabel();
        comAccountType = new javax.swing.JComboBox<>();
        btnCreateAccount = new javax.swing.JButton();
        btnApproved = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        pnlAcctable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAccounts = new javax.swing.JTable();

        pnlAccSettingMainForm.setBackground(new java.awt.Color(153, 153, 153));

        pnlAccLabel.setBackground(new java.awt.Color(153, 153, 153));
        pnlAccLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 3));

        lblAccountSetting.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAccountSetting.setText("Account Setting");

        javax.swing.GroupLayout pnlAccLabelLayout = new javax.swing.GroupLayout(pnlAccLabel);
        pnlAccLabel.setLayout(pnlAccLabelLayout);
        pnlAccLabelLayout.setHorizontalGroup(
            pnlAccLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccLabelLayout.createSequentialGroup()
                .addComponent(lblAccountSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlAccLabelLayout.setVerticalGroup(
            pnlAccLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccLabelLayout.createSequentialGroup()
                .addComponent(lblAccountSetting)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pnlAccSetting.setBackground(new java.awt.Color(153, 153, 153));
        pnlAccSetting.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 3));

        pnlinputdata.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 2, true));

        lblusername.setText("Username");

        lblPassword.setText("Password");

        jLabel2.setText("RePassword");

        jLabel1.setText("Account Type");

        comAccountType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "USER" }));
        comAccountType.setToolTipText("");
        comAccountType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comAccountTypeActionPerformed(evt);
            }
        });

        btnCreateAccount.setText("Create Account");
        btnCreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountActionPerformed(evt);
            }
        });

        btnApproved.setText("Approved");
        btnApproved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApprovedActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlinputdataLayout = new javax.swing.GroupLayout(pnlinputdata);
        pnlinputdata.setLayout(pnlinputdataLayout);
        pnlinputdataLayout.setHorizontalGroup(
            pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinputdataLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblusername, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlinputdataLayout.createSequentialGroup()
                        .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPassword)
                            .addComponent(txtUsername)
                            .addComponent(txtrepassword, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbleyeicon1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbleyeicon, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(comAccountType, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnApproved, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                        .addComponent(btnCreateAccount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        pnlinputdataLayout.setVerticalGroup(
            pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinputdataLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblusername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlinputdataLayout.createSequentialGroup()
                        .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPassword)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtrepassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbleyeicon1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlinputdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(comAccountType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbleyeicon, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCreateAccount)
                .addGap(26, 26, 26)
                .addComponent(btnUpdate)
                .addGap(26, 26, 26)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnApproved)
                .addGap(75, 75, 75))
        );

        javax.swing.GroupLayout pnlAccSettingLayout = new javax.swing.GroupLayout(pnlAccSetting);
        pnlAccSetting.setLayout(pnlAccSettingLayout);
        pnlAccSettingLayout.setHorizontalGroup(
            pnlAccSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlinputdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlAccSettingLayout.setVerticalGroup(
            pnlAccSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccSettingLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(pnlinputdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlAcctable.setBackground(new java.awt.Color(153, 153, 153));
        pnlAcctable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        tblAccounts.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 2, true));
        tblAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "username", "password", "role", "approved", "created_at"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAccounts);
        if (tblAccounts.getColumnModel().getColumnCount() > 0) {
            tblAccounts.getColumnModel().getColumn(0).setMinWidth(200);
            tblAccounts.getColumnModel().getColumn(0).setMaxWidth(200);
            tblAccounts.getColumnModel().getColumn(2).setMinWidth(100);
            tblAccounts.getColumnModel().getColumn(2).setMaxWidth(100);
            tblAccounts.getColumnModel().getColumn(3).setMinWidth(65);
            tblAccounts.getColumnModel().getColumn(3).setMaxWidth(65);
        }

        javax.swing.GroupLayout pnlAcctableLayout = new javax.swing.GroupLayout(pnlAcctable);
        pnlAcctable.setLayout(pnlAcctableLayout);
        pnlAcctableLayout.setHorizontalGroup(
            pnlAcctableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcctableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAcctableLayout.setVerticalGroup(
            pnlAcctableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcctableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlAccSettingMainFormLayout = new javax.swing.GroupLayout(pnlAccSettingMainForm);
        pnlAccSettingMainForm.setLayout(pnlAccSettingMainFormLayout);
        pnlAccSettingMainFormLayout.setHorizontalGroup(
            pnlAccSettingMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccSettingMainFormLayout.createSequentialGroup()
                .addComponent(pnlAccLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlAccSettingMainFormLayout.createSequentialGroup()
                .addComponent(pnlAccSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAcctable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAccSettingMainFormLayout.setVerticalGroup(
            pnlAccSettingMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccSettingMainFormLayout.createSequentialGroup()
                .addComponent(pnlAccLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAccSettingMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAcctable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAccSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAccSettingMainForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAccSettingMainForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountActionPerformed
        this.addItems();
    }//GEN-LAST:event_btnCreateAccountActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        this.deleteItems();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnApprovedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApprovedActionPerformed
       this.approve();
    }//GEN-LAST:event_btnApprovedActionPerformed

	private void comAccountTypeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_comAccountTypeActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_comAccountTypeActionPerformed

	private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnUpdateActionPerformed
		this.updateAccount();
	}// GEN-LAST:event_btnUpdateActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(AccountSetting.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(AccountSetting.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(AccountSetting.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(AccountSetting.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new AccountSetting().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApproved;
    private javax.swing.JButton btnCreateAccount;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> comAccountType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAccountSetting;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lbleyeicon;
    private javax.swing.JLabel lbleyeicon1;
    private javax.swing.JLabel lblusername;
    private javax.swing.JPanel pnlAccLabel;
    private javax.swing.JPanel pnlAccSetting;
    private javax.swing.JPanel pnlAccSettingMainForm;
    private javax.swing.JPanel pnlAcctable;
    private javax.swing.JPanel pnlinputdata;
    private javax.swing.JTable tblAccounts;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JPasswordField txtrepassword;
    // End of variables declaration//GEN-END:variables
}
