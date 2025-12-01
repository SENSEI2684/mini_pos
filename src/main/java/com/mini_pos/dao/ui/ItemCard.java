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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.mini_pos.dao.etinity.Items;

public class ItemCard extends JPanel {

    public ItemCard(Items item, Runnable addToCartAction) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Rounded corners + shadow
        setOpaque(false);

        JPanel innerPanel = new RoundedPanel();
        innerPanel.setLayout(new BorderLayout());
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ================= IMAGE =================
        JLabel lblImg = new JLabel("", SwingConstants.CENTER);

        try {
            URL imgUrl = getClass().getClassLoader()
                    .getResource("static/images/" + item.photo());

            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaled = icon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaled));
            }
        } catch (Exception ignored) {}

        innerPanel.add(lblImg, BorderLayout.NORTH);

        // ================= INFO PANEL =================
        JPanel info = new JPanel(new GridBagLayout());
        info.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        info.add(new JLabel("Name"), gbc);

        gbc.gridx = 1;
        info.add(new JLabel(item.name()), gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 1;
        info.add(new JLabel("Price"), gbc);

        gbc.gridx = 1;
        info.add(new JLabel(item.price() + " Ks"), gbc);

        // Quantity
        gbc.gridx = 0; gbc.gridy = 2;
        info.add(new JLabel("Quantity"), gbc);

        gbc.gridx = 1;
        JSpinner spQty = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        spQty.setPreferredSize(new Dimension(60, 25));
        info.add(spQty, gbc);

        // Add-to-Cart Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton btnAdd = new JButton("Add to Cart");
        btnAdd.setPreferredSize(new Dimension(130, 30));
        info.add(btnAdd, gbc);

        btnAdd.addActionListener(e -> addToCartAction.run());

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
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Shadow
            g2.setColor(new Color(0, 0, 0, 20));
            g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 30, 30);

            // Background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 20, 20);

            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public boolean isOpaque() {
            return false;
        }
    }
}