package com.mini_pos.helper_function;

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
import com.mini_pos.dao.etinity.ItemsWithCategories;
import com.mini_pos.dao.service.CartService;
import com.mini_pos.dao.service.CartServiceImpl;
import com.mini_pos.helper_function.ImageCache;
import com.mini_pos.helper_function.Session;

public class ItemCard extends JPanel {
	
	
	private CartService cartService = CartServiceImpl.getInstance();
	private Session session = Session.getInstance();

    public ItemCard(Items item, Runnable afterAdd) {
    	
    	System.out.println("Print Item Card Obj create");
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // smaller padding to remove top space
        setOpaque(false);

        JPanel innerPanel = new RoundedPanel();
        innerPanel.setLayout(new BorderLayout());
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 11));
        

        // ================= IMAGE PANEL (LEFT) =================
        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        try {
        	ImageIcon cached = ImageCache.get(item.photo());
        	if (cached != null) {
        	    lblImg.setIcon(cached);
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
        gbc.fill = GridBagConstraints.NONE;
        JSpinner spQty = new JSpinner(new SpinnerNumberModel(1, 0, 99, 1));
        ((JSpinner.DefaultEditor) spQty.getEditor()).getTextField().setColumns(3);
        spQty.setPreferredSize(new Dimension(60, 28));
        spQty.setMinimumSize(new Dimension(60, 28));
        spQty.setMaximumSize(new Dimension(60, 28));
        info.add(spQty, gbc);
        

        // Add-to-Cart Button
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton btnAdd = new JButton("Add to Cart");
        btnAdd.setPreferredSize(new Dimension(120, 30));
        
        btnAdd.addActionListener(e -> { // items add to cart code with JDBC is here 
        	Integer user_id = session.getUserId();
        	Integer item_id = item.id();
        	Integer qty =(Integer) spQty.getValue(); // ← get quantity

        	Cart cart = new Cart(0,user_id,item_id,qty,null); // Java UI table cannot refresh auto, that why need to write refresh function
//        	System.out.println(user_id + " " + item_id );
            this.cartService.addToCart(cart);  // ← send to 
            System.out.println("add to cart button wor;!!!");
            
            if (afterAdd != null) 
            {
            	afterAdd.run(); // refresh UI , in here we dont need to use reloadAllItems of MainFrame method, this afterAdd buildin method auto refresh UI
            }
            spQty.setValue(1); // to reset the spinner value to 0 after click the button
//            mf.reloadAllItems();
            
        });
        info.add(btnAdd, gbc);

        innerPanel.add(info, BorderLayout.CENTER);
//
//        // ================= HOVER EFFECT =================
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