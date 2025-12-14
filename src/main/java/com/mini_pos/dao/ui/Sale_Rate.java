/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mini_pos.dao.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.awt.print.PrinterException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import com.mini_pos.dao.etinity.SaleReport;
import com.mini_pos.dao.etinity.Users;
import com.mini_pos.dao.service.SaleReportService;
import com.mini_pos.dao.service.SaleReportServiceImpl;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

/**
 *
 * @author User
 */
public class Sale_Rate extends javax.swing.JFrame {

    /**
     * Creates new form Sale_Rate
     */
	
	private SaleReportService saleRpService = new SaleReportServiceImpl();
	NumberFormat formatter = NumberFormat.getInstance();
	
	public Sale_Rate() {
	    initComponents();
	    normalSearch();

		JDateChooser datePicker = new JDateChooser();
		datePicker.setDateFormatString("yyyy-MM-dd");
		pnl_record_mgmt.add(datePicker); // add to your JPanel
		pnl_record_mgmt.revalidate(); // tell Swing layout changed
		pnl_record_mgmt.repaint();
	}
    




//----------------------------------------------------------------------------------------------------------------------------------------------------------    
	//Load sale Report******************************************************
    
   
    
	private void LoadAllItemReport(List<SaleReport> data) {

	    DefaultTableModel model = (DefaultTableModel) this.tbl_all_sale.getModel();       
	    model.setRowCount(0);

	    DateTimeFormatter fmt =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       
	    try {
//	    	List<SaleReport> users = this.saleRpService.getAllReport();
	    	for (SaleReport sr : data) {
		        Object[] row = new Object[6];

		        row[0] = sr.saleDate() != null 
		                ? sr.saleDate().format(fmt) 
		                : "TOTAL";
		        row[1] = sr.category();
		        row[2] = sr.itemName();
		        row[3] = sr.totalQuantity();
		        row[4] =  formatter.format(sr.Price());    
		        row[5] = formatter.format(sr.totalPrice());

		        model.addRow(row);
	    }
   
	    } catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Unexpected error while loading items!", "Error",JOptionPane.ERROR_MESSAGE);					
			e.printStackTrace();
		}
	}
    
    
	private void LoadaCategoryReport(List<SaleReport> data) {
	    DefaultTableModel model = (DefaultTableModel) this.tbl_category_sale.getModel();       
	    model.setRowCount(0);

	    DateTimeFormatter fmt =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       
	    try {
//	    	List<SaleReport> users = this.saleRpService.getCategoryReport();
	    	for (SaleReport sr : data) {
		        Object[] row = new Object[4];

		        row[0] = sr.saleDate() != null 
		                ? sr.saleDate().format(fmt) 
		                : "TOTAL";
		        row[1] = sr.category();
		        row[2] = sr.totalQuantity();
		        row[3] = formatter.format(sr.totalPrice());

		        model.addRow(row);
	    }
   
	    } catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Unexpected error while loading items!", "Error",JOptionPane.ERROR_MESSAGE);					
			e.printStackTrace();
		}
	}
    



	private void LoadaSummaryReport(List<SaleReport> data) {
	    DefaultTableModel model = (DefaultTableModel) this.tbl_total_sale.getModel();       
	    model.setRowCount(0);

	    DateTimeFormatter fmt =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       
	    try {
//	    	List<SaleReport> users = this.saleRpService.getSummaryReport();
	    	for (SaleReport sr : data) {
		        Object[] row = new Object[3];

		        row[0] = sr.saleDate() != null 
		                ? sr.saleDate().format(fmt) 
		                : "TOTAL";
	
		        row[1] = sr.totalQuantity();
		        row[2] = formatter.format(sr.totalPrice());

		        model.addRow(row);
	    }
   
	    
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Unexpected error while loading items!", "Error",JOptionPane.ERROR_MESSAGE);					
			e.printStackTrace();
		}
	}

	private void reloadAllReport(List<SaleReport> data) {
	    LoadAllItemReport(data);
	}

	private void reloadCategoryReport(List<SaleReport> data) {
	    LoadaCategoryReport(data);
	}

	private void reloadSummaryReport(List<SaleReport> data) {
	    LoadaSummaryReport(data);
	}	
//----------------------------------------------------------------------------------------------------------------------------------------------------------  
	
	private void normalSearch() {
	    try {
	        List<SaleReport> data = saleRpService.getAllReport();
	        List<SaleReport> categoryData = saleRpService.getCategoryReport();
	        List<SaleReport> summaryData = saleRpService.getSummaryReport();
	        
	        reloadAllReport(data);
	        reloadCategoryReport(categoryData);
	        reloadSummaryReport(summaryData);
	      
	    } catch (DaoException de) {
			JOptionPane.showMessageDialog(this, de.getMessage(), "DataBase Error", JOptionPane.ERROR_MESSAGE);
			de.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Unexpected error while loading items!", "Error",JOptionPane.ERROR_MESSAGE);					
			e.printStackTrace();
		}
	}
//	private void mothSearch() {
//
//		
//		Integer mstr = (Integer) com_Month.getSelectedIndex();
//		String ystr = (String) com_Year.getSelectedItem();
//		Integer year = Integer.parseInt(ystr);
//		YearMonth ym = YearMonth.of(year, mstr);
//		LocalDate start = ym.atDay(1);
//		LocalDate end = ym.atEndOfMonth();
//
//		try {
//
//			List<SaleReport> data = saleRpService.getReportByInterval(start, end);
//
//			reloadAllReport(data); // reuse your table loader
////	        reloadCategoryReport(data);
////	        reloadSummaryReport(data);
//		} catch (DaoException e) {
//			e.printStackTrace();
//		}
//	}
	
//	private void oneDay() {
//		 try {
//		        LocalDate date = LocalDate.parse(txtDate.getText().trim());
//		        List<SaleReport> data = saleRpService.getReportByInterval(date, date);
//		        System.out.println("Searching for date: " + txtDate.getText());
//		        System.out.println("DAO returned " + data.size() + " records");
//		        reloadAllReport(data);
//		       
//		    } catch (Exception ex) {
//		        JOptionPane.showMessageDialog(this, "Invalid date or DB error: " + ex.getMessage());
//		    }
//	
//	}
	
	private void intervalSearch() { //daily can also search just put start and end same day 

	        String startText = txtstart.getText().trim();
	        String endText = txtend.getText().trim();
	        String itemcode = txt_item_code.getText().trim(); 

	        if (startText.isEmpty() || endText.isEmpty()) {
	        	JOptionPane.showMessageDialog(this, "Please input both start and end dates!", "Warrning", JOptionPane.WARNING_MESSAGE);
	        }

	        LocalDate start = LocalDate.parse(startText);
	        LocalDate end = LocalDate.parse(endText);
		
		try {
			List<SaleReport> allItems = saleRpService.getAllItemsReportByInterval(start, end,itemcode);
			List<SaleReport> category = saleRpService.getCategoryReportByInterval(start, end);
			List<SaleReport> summary = saleRpService.getSummaryReportByInterval(start, end);
			reloadAllReport(allItems);
			reloadCategoryReport(category);
	        reloadSummaryReport(summary);
	       
		} catch (DaoException de) {
			JOptionPane.showMessageDialog(this, de.getMessage(), "Warrning", JOptionPane.ERROR_MESSAGE);
			de.printStackTrace();
		} catch (ValidationException ve) {
			JOptionPane.showMessageDialog(this, ve.getMessage(), "DataBase Error", JOptionPane.WARNING_MESSAGE);
			ve.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Invalid date format! Use yyyy-MM-dd",   "Validation Error",  JOptionPane.WARNING_MESSAGE);
		}
	
	}
	
	private void itemReportSearch() {
		 String itemcode = txt_item_code.getText().trim();
		 try {
				List<SaleReport> allItems = saleRpService.getItemReport(itemcode);
				
				reloadAllReport(allItems);

		       
			} catch (DaoException de) {
				JOptionPane.showMessageDialog(this, de.getMessage(), "Warrning", JOptionPane.ERROR_MESSAGE);
				de.printStackTrace();
			} catch (ValidationException ve) {
				JOptionPane.showMessageDialog(this, ve.getMessage(), "DataBase Error", JOptionPane.WARNING_MESSAGE);
				ve.printStackTrace();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Invalid Item_code!",   "Validation Error",  JOptionPane.WARNING_MESSAGE);
			}
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

        jPanel1 = new javax.swing.JPanel();
        tabbedPaneCustom1 = new com.mini_pos.helper_function.TabbedPaneCustom();
        pnl_all_sale = new javax.swing.JPanel();
        pnl_all_base = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_all_sale = new javax.swing.JTable();
        pnl_category_sale = new javax.swing.JPanel();
        pnl_category_base = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_category_sale = new javax.swing.JTable();
        pnl_total_sale = new javax.swing.JPanel();
        pnl_total_base = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_total_sale = new javax.swing.JTable();
        pnl_record_mgmt = new javax.swing.JPanel();
        btn_ALL_serach = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtstart = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtend = new javax.swing.JTextField();
        txt_interval_date_search = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_item_code = new javax.swing.JTextField();
        btn_item_search = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 177, 28));

        jPanel1.setBackground(new java.awt.Color(181, 165, 130));

        tabbedPaneCustom1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tabbedPaneCustom1.setSelectedColor(new java.awt.Color(49, 173, 248));
        tabbedPaneCustom1.setUnselectedColor(new java.awt.Color(194, 226, 243));

        pnl_all_sale.setBackground(new java.awt.Color(181, 165, 130));

        pnl_all_base.setBackground(new java.awt.Color(204, 204, 204));
        pnl_all_base.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tbl_all_sale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "sale_date", "category", "item_name", "total_quantity", "price", "total_price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_all_sale);

        javax.swing.GroupLayout pnl_all_baseLayout = new javax.swing.GroupLayout(pnl_all_base);
        pnl_all_base.setLayout(pnl_all_baseLayout);
        pnl_all_baseLayout.setHorizontalGroup(
            pnl_all_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_all_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_all_baseLayout.setVerticalGroup(
            pnl_all_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_all_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_all_saleLayout = new javax.swing.GroupLayout(pnl_all_sale);
        pnl_all_sale.setLayout(pnl_all_saleLayout);
        pnl_all_saleLayout.setHorizontalGroup(
            pnl_all_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_all_base, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_all_saleLayout.setVerticalGroup(
            pnl_all_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_all_saleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_all_base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Each Item Sale", pnl_all_sale);

        pnl_category_base.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tbl_category_sale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "sale_date", "category", "total_quantity", "total_price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_category_sale);

        javax.swing.GroupLayout pnl_category_baseLayout = new javax.swing.GroupLayout(pnl_category_base);
        pnl_category_base.setLayout(pnl_category_baseLayout);
        pnl_category_baseLayout.setHorizontalGroup(
            pnl_category_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_category_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_category_baseLayout.setVerticalGroup(
            pnl_category_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_category_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_category_saleLayout = new javax.swing.GroupLayout(pnl_category_sale);
        pnl_category_sale.setLayout(pnl_category_saleLayout);
        pnl_category_saleLayout.setHorizontalGroup(
            pnl_category_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_category_base, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_category_saleLayout.setVerticalGroup(
            pnl_category_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_category_saleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_category_base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Category Sale", pnl_category_sale);

        pnl_total_base.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tbl_total_sale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "sale_date", "total_quantity", "total_price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_total_sale);

        javax.swing.GroupLayout pnl_total_baseLayout = new javax.swing.GroupLayout(pnl_total_base);
        pnl_total_base.setLayout(pnl_total_baseLayout);
        pnl_total_baseLayout.setHorizontalGroup(
            pnl_total_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_total_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_total_baseLayout.setVerticalGroup(
            pnl_total_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_total_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_total_saleLayout = new javax.swing.GroupLayout(pnl_total_sale);
        pnl_total_sale.setLayout(pnl_total_saleLayout);
        pnl_total_saleLayout.setHorizontalGroup(
            pnl_total_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_total_base, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_total_saleLayout.setVerticalGroup(
            pnl_total_saleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_total_saleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_total_base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Total Sale", pnl_total_sale);

        pnl_record_mgmt.setBackground(new java.awt.Color(204, 204, 204));
        pnl_record_mgmt.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_ALL_serach.setText("All Search");
        btn_ALL_serach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ALL_serachActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Start Date : yyyy-MM-dd");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("End Date : yyyy-MM-dd");

        txt_interval_date_search.setText("Interval Date Search");
        txt_interval_date_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_interval_date_searchActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Item Code :");

        btn_item_search.setText("Item Search");
        btn_item_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_item_searchActionPerformed(evt);
            }
        });

        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_record_mgmtLayout = new javax.swing.GroupLayout(pnl_record_mgmt);
        pnl_record_mgmt.setLayout(pnl_record_mgmtLayout);
        pnl_record_mgmtLayout.setHorizontalGroup(
            pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtstart)
                    .addComponent(txtend)
                    .addComponent(txt_item_code, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_item_search, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_interval_date_search)
                    .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                        .addComponent(btn_ALL_serach, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_refresh)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        pnl_record_mgmtLayout.setVerticalGroup(
            pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtstart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_interval_date_search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_item_search))
                .addGap(11, 11, 11)
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ALL_serach)
                    .addComponent(jLabel1)
                    .addComponent(txt_item_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_record_mgmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(pnl_record_mgmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ALL_serachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ALL_serachActionPerformed
        this.normalSearch();
        
     
    }//GEN-LAST:event_btn_ALL_serachActionPerformed

    private void txt_interval_date_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_interval_date_searchActionPerformed
    	this.intervalSearch();
    }//GEN-LAST:event_txt_interval_date_searchActionPerformed

    private void btn_item_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_item_searchActionPerformed
        this.itemReportSearch();
    }//GEN-LAST:event_btn_item_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        this.reloadCategoryReport(null);
        this.reloadCategoryReport(null);
        this.reloadSummaryReport(null);
    }//GEN-LAST:event_btn_refreshActionPerformed

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
            java.util.logging.Logger.getLogger(Sale_Rate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sale_Rate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sale_Rate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sale_Rate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sale_Rate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ALL_serach;
    private javax.swing.JButton btn_item_search;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnl_all_base;
    private javax.swing.JPanel pnl_all_sale;
    private javax.swing.JPanel pnl_category_base;
    private javax.swing.JPanel pnl_category_sale;
    private javax.swing.JPanel pnl_record_mgmt;
    private javax.swing.JPanel pnl_total_base;
    private javax.swing.JPanel pnl_total_sale;
    private com.mini_pos.helper_function.TabbedPaneCustom tabbedPaneCustom1;
    private javax.swing.JTable tbl_all_sale;
    private javax.swing.JTable tbl_category_sale;
    private javax.swing.JTable tbl_total_sale;
    private javax.swing.JButton txt_interval_date_search;
    private javax.swing.JTextField txt_item_code;
    private javax.swing.JTextField txtend;
    private javax.swing.JTextField txtstart;
    // End of variables declaration//GEN-END:variables
}
