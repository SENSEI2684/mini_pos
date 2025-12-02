package com.mini_pos.dao.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.mini_pos.dao.etinity.Cart;
import com.mini_pos.dao.etinity.Items;
import com.mini_pos.dao.service.CartService;
import com.mini_pos.dao.service.CartServiceImpl;
import com.mini_pos.dao.session.Session;

public class ItemCard extends JPanel {
	
	MainFrame mf = new MainFrame();
	private CartService cartService = new CartServiceImpl();
	private Session session = Session.getInstance();

    public ItemCard(Items item, Runnable afterAdd) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // smaller padding to remove top space
        setOpaque(false);

        JPanel innerPanel = new RoundedPanel();
        innerPanel.setLayout(new BorderLayout());
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ================= IMAGE PANEL (LEFT) =================
        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            URL imgUrl = getClass().getClassLoader()
                    .getResource("static/images/" + item.photo());

            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaled));
            }
        } catch (Exception ignored) {}

        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.setOpaque(false);
        imgPanel.add(lblImg, BorderLayout.CENTER);

        innerPanel.add(imgPanel, BorderLayout.WEST);

        // ================= INFO PANEL (RIGHT) =================
        JPanel info = new JPanel(new GridBagLayout());
        info.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        info.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        info.add(new JLabel(item.name()), gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 1;
        info.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        info.add(new JLabel(item.price() + " Ks"), gbc);

        // Quantity
        gbc.gridx = 0; gbc.gridy = 2;
        info.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        JSpinner spQty = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        spQty.setPreferredSize(new Dimension(60, 25));
        info.add(spQty, gbc);

        // Add-to-Cart Button
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton btnAdd = new JButton("Add to Cart");
        btnAdd.setPreferredSize(new Dimension(120, 30));
        
        btnAdd.addActionListener(e -> { // items add to cart code with JDBC is here 
        	Integer user_id = session.getUserId();
        	Integer item_id = item.id();
        	Integer qty =(Integer) spQty.getValue(); // ← get quantity

        	Cart cart = new Cart(0,user_id,item_id,qty,null); // Java UI table cannot refresh auto, that why need to write refresh function

            this.cartService.addToCart(cart);  // ← send to 
            System.out.println("add to cart button wor;!!!");
            
            if (afterAdd != null) 
            {
            	afterAdd.run(); // refresh UI , in here we dont need to use reloadAllItems of MainFrame method, this afterAdd buildin method auto refresh UI
            }
            
//            mf.reloadAllItems();
            
        });
        info.add(btnAdd, gbc);

        innerPanel.add(info, BorderLayout.CENTER);

        // ================= HOVER EFFECT =================
        innerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                innerPanel.setBackground(new Color(230, 230, 230));
                innerPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                innerPanel.setBackground(Color.WHITE);
                innerPanel.repaint();
            }
        });

        add(innerPanel, BorderLayout.CENTER);
    }

    // Rounded panel with shadow
    private static class RoundedPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Shadow
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillRoundRect(4, 4, width - 8, height - 8, 30, 30);

            // Background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, width - 8, height - 8, 20, 20);

            g2.dispose();
        }

        @Override
        public boolean isOpaque() {
            return false;
        }
    }
}