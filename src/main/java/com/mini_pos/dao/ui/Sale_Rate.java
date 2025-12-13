/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mini_pos.dao.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
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

/**
 *
 * @author User
 */
public class Sale_Rate extends javax.swing.JFrame {

    /**
     * Creates new form Sale_Rate
     */
	
	private SaleReportService saleRpService = new SaleReportServiceImpl();
	JDateChooser datePicker = new JDateChooser();

	
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

		        row[0] = sr.saleDate().format(fmt);
		        row[1] = sr.category();
		        row[2] = sr.itemName();
		        row[3] = sr.Price();
		        row[4] = sr.totalQuantity();
		        row[5] = sr.totalPrice();

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

		        row[0] = sr.saleDate().format(fmt);
		        row[1] = sr.category();
		        row[2] = sr.totalQuantity();
		        row[3] = sr.totalPrice();

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

		        row[0] = sr.saleDate().format(fmt);
	
		        row[1] = sr.totalQuantity();
		        row[2] = sr.totalPrice();

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
	private void mothSearch() {

		
		Integer mstr = (Integer) com_Month.getSelectedIndex();
		String ystr = (String) com_Year.getSelectedItem();
		Integer year = Integer.parseInt(ystr);
		YearMonth ym = YearMonth.of(year, mstr);
		LocalDate start = ym.atDay(1);
		LocalDate end = ym.atEndOfMonth();

		try {

			List<SaleReport> data = saleRpService.getReportByInterval(start, end);

			reloadAllReport(data); // reuse your table loader
//	        reloadCategoryReport(data);
//	        reloadSummaryReport(data);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	private void oneDay() {
		 try {
		        LocalDate date = LocalDate.parse(txtDate.getText().trim());
		        List<SaleReport> data = saleRpService.getReportByInterval(date, date);
		        System.out.println("Searching for date: " + txtDate.getText());
		        System.out.println("DAO returned " + data.size() + " records");
		        reloadAllReport(data);
		       
		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Invalid date or DB error: " + ex.getMessage());
		    }
	
	}
	
	private void interval() {
		try {
	        LocalDate start = LocalDate.parse(txtstart.getText().trim());
	        LocalDate end = LocalDate.parse(txtend.getText().trim());     

                   
List<SaleReport> rangeData = saleRpService.getReportByInterval(start, end);

reloadAllReport(rangeData);

		}
		catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Invalid date or DB error: " + ex.getMessage());
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
        com_Year = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        com_Month = new javax.swing.JComboBox<>();
        btn_month_search = new javax.swing.JButton();
        btn_ALL_serach = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        btn_day = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtstart = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtend = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(243, 243, 243));

        tabbedPaneCustom1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tabbedPaneCustom1.setSelectedColor(new java.awt.Color(49, 173, 248));
        tabbedPaneCustom1.setUnselectedColor(new java.awt.Color(194, 226, 243));

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_all_baseLayout.setVerticalGroup(
            pnl_all_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_all_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
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
                .addComponent(pnl_all_base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
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

        pnl_record_mgmt.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        com_Year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2024", "2025", "2026" }));

        jLabel1.setText("Year");

        jLabel2.setText("Month");

        com_Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec" }));

        btn_month_search.setText("Monthly Search");
        btn_month_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_month_searchActionPerformed(evt);
            }
        });

        btn_ALL_serach.setText("All Search");
        btn_ALL_serach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ALL_serachActionPerformed(evt);
            }
        });

        jLabel3.setText("Date : yy-mm-dd");

        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        btn_day.setText("Day Search");
        btn_day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dayActionPerformed(evt);
            }
        });

        jLabel4.setText("Start");

        jLabel5.setText("End");

        jButton1.setText("Interval serach");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_record_mgmtLayout = new javax.swing.GroupLayout(pnl_record_mgmt);
        pnl_record_mgmt.setLayout(pnl_record_mgmtLayout);
        pnl_record_mgmtLayout.setHorizontalGroup(
            pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_record_mgmtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                        .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(com_Month, 0, 105, Short.MAX_VALUE)
                            .addComponent(com_Year, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btn_month_search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_record_mgmtLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_day)
                        .addGap(291, 291, 291))
                    .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                        .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtend, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(txtstart))
                        .addGap(30, 30, 30)
                        .addComponent(jButton1)
                        .addGap(203, 203, 203)
                        .addComponent(btn_ALL_serach, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
        );
        pnl_record_mgmtLayout.setVerticalGroup(
            pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_record_mgmtLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_Year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_day))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(com_Month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_record_mgmtLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtstart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_month_search)
                        .addComponent(btn_ALL_serach))
                    .addGroup(pnl_record_mgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)
                        .addComponent(jLabel5)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_record_mgmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(pnl_record_mgmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_month_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_month_searchActionPerformed
        this.mothSearch();
    }//GEN-LAST:event_btn_month_searchActionPerformed

    private void btn_ALL_serachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ALL_serachActionPerformed
        this.normalSearch();
        
     
    }//GEN-LAST:event_btn_ALL_serachActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
         }//GEN-LAST:event_txtDateActionPerformed

    private void btn_dayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dayActionPerformed
    	this.oneDay(); 
    }//GEN-LAST:event_btn_dayActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    	this.interval();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton btn_day;
    private javax.swing.JButton btn_month_search;
    private javax.swing.JComboBox<String> com_Month;
    private javax.swing.JComboBox<String> com_Year;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtend;
    private javax.swing.JTextField txtstart;
    // End of variables declaration//GEN-END:variables
}
