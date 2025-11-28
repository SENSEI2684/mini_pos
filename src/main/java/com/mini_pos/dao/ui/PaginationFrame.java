package com.mini_pos.dao.ui;

import javax.swing.*;

import com.mini_pos.dao.BaseDao;
import com.mini_pos.dao.etinity.Items;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class PaginationFrame extends JFrame {

    private List<Items> items;        // All items from DB
    private int currentPage = 0;     // Start at page 0
    private final int ITEMS_PER_PAGE = 9;

    private JPanel itemsPanel;       // Grid to display items
    private JButton btnPrev, btnNext;

    public PaginationFrame(List<Items> items) {
        this.items = items;
        setTitle("Mini POS - Pagination Demo");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        itemsPanel = new JPanel(new GridLayout(3, 3, 10, 10)); // 9 items
        add(itemsPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        btnPrev = new JButton("<< Previous");
        btnNext = new JButton("Next >>");

        footer.add(btnPrev);
        footer.add(btnNext);
        add(footer, BorderLayout.SOUTH);

        btnPrev.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updatePage();
            }
        });

        btnNext.addActionListener(e -> {
            int maxPage = (items.size() - 1) / ITEMS_PER_PAGE;
            if (currentPage < maxPage) {
                currentPage++;
                updatePage();
            }
        });

        updatePage();  // Load page 0
        setVisible(true);
    }

    private void updatePage() {
        itemsPanel.removeAll();

        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, items.size());

        for (int i = start; i < end; i++) {
            itemsPanel.add(createItemPanel(items.get(i)));
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private JPanel createItemPanel(Items item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Load image from classpath
        URL imageUrl = getClass().getClassLoader()
                .getResource("Static/Images/" + item.photo());

        JLabel imgLabel;

        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaled = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imgLabel = new JLabel(new ImageIcon(scaled));
        } else {
            // If not found, show placeholder
            imgLabel = new JLabel("No Image", SwingConstants.CENTER);
        }

        JLabel name = new JLabel(item.name(), SwingConstants.CENTER);
        JLabel price = new JLabel("Price: " + item.price(), SwingConstants.CENTER);

        panel.add(name, BorderLayout.NORTH);
        panel.add(imgLabel, BorderLayout.CENTER);
        panel.add(price, BorderLayout.SOUTH);

        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return panel;
    }

    public static List<Items> loadItems() throws SQLException {
        List<Items> list = new ArrayList<>();
        Connection conn = new BaseDao().getconnection();

        String sql = "SELECT name, price, photo FROM items";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
        	
    		String name = rs.getString("name");
    		Integer price = rs.getInt("price");
    		
    		String photo = rs.getString("photo");
    		
    		Items item = new Items(null, name, price,null, photo,null, null);
    		list.add(item);
        }
        return list;
    }

    public static void main(String[] args) throws SQLException {

        new PaginationFrame(loadItems());
    }
}

