package com.mini_pos.dao.ui;
import javax.swing.*;

import com.mini_pos.dao.etinity.SaleReport;
import com.mini_pos.dao.service.SaleReportService;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.time.*;
import java.util.List;

public class SaleSearchPanel extends JPanel {

    private JDateChooser datePicker;
    private JButton btnDaily, btnMonthly, btnCustom;

    private SaleReportService saleRpService; // your DAO/service

    public SaleSearchPanel(SaleReportService service) {
        this.saleRpService = service;
        initComponents();
    }

    private void initComponents() {
        // Set panel layout
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Date picker
        datePicker = new JDateChooser();
        datePicker.setDateFormatString("yyyy-MM-dd");
        datePicker.setPreferredSize(new Dimension(150, 25));
        add(datePicker);

        // Daily search button
        btnDaily = new JButton("Daily Search");
        btnDaily.addActionListener(e -> searchDaily());
        add(btnDaily);

        // Monthly search button
        btnMonthly = new JButton("Monthly Search");
        btnMonthly.addActionListener(e -> searchMonthly());
        add(btnMonthly);

        // Custom interval button
        btnCustom = new JButton("Custom Search");
        btnCustom.addActionListener(e -> searchCustom());
        add(btnCustom);
    }

    private void searchDaily() {
        if (datePicker.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date!");
            return;
        }

        LocalDate selectedDate = datePicker.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        try {
            List<SaleReport> data = saleRpService.getReportByInterval(selectedDate, selectedDate);
            showData(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void searchMonthly() {
        if (datePicker.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date!");
            return;
        }

        LocalDate selectedDate = datePicker.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        YearMonth ym = YearMonth.from(selectedDate);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        try {
            List<SaleReport> data = saleRpService.getReportByInterval(start, end);
            showData(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void searchCustom() {
        // Example: show an input dialog for start and end date
        String startStr = JOptionPane.showInputDialog(this, "Start date (yyyy-MM-dd):");
        String endStr = JOptionPane.showInputDialog(this, "End date (yyyy-MM-dd):");
        try {
            LocalDate start = LocalDate.parse(startStr);
            LocalDate end = LocalDate.parse(endStr);

            List<SaleReport> data = saleRpService.getReportByInterval(start, end);
            showData(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid input or DB error: " + ex.getMessage());
        }
    }

    private void showData(List<SaleReport> data) {
        // Here you can call your table reload methods
        // Example: Sale_Rate.reloadAllReport(data);
        System.out.println("Data size: " + data.size());
    }
}
