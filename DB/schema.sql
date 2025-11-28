CREATE DATABASE IF NOT EXISTS mini_pos_db;
USE mini_pos_db;

CREATE TABLE users (
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    username     		  VARCHAR(50) UNIQUE NOT NULL,
    password       		  VARCHAR(255) NOT NULL,
    role                  ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
    approved              BOOLEAN DEFAULT FALSE,
    created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE categories (
    id  	              INT AUTO_INCREMENT PRIMARY KEY,
    category_name    	  VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE items (
    id        	         INT AUTO_INCREMENT PRIMARY KEY,
    item_code            VARCHAR(50) UNIQUE NOT NULL,
    name                 VARCHAR(100) NOT NULL,
    price                DECIMAL(10,2) NOT NULL,
    quantity             INT NOT NULL,
    photo                VARCHAR(255) DEFAULT NULL,
    category_id          INT NOT NULL,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_items_categories
    FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);


CREATE TABLE cart (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    user_id       	  INT NOT NULL,
    item_id           INT NOT NULL,
    quantity          INT NOT NULL,
    added_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_users 
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_cart_items
    FOREIGN KEY (item_id) REFERENCES items(id)
        ON DELETE CASCADE
);


CREATE TABLE orders (
    id     	          INT AUTO_INCREMENT PRIMARY KEY,
    user_id           INT NOT NULL,
    total_amount      DECIMAL(10,2) NOT NULL,
    order_date        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_users
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);



CREATE TABLE order_items (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    order_id      	  INT NOT NULL,
    item_id           INT NOT NULL,
    quantity          INT NOT NULL,
    price             DECIMAL(10,2) NOT NULL,    
    subtotal          DECIMAL(10,2) GENERATED ALWAYS AS (quantity * price) STORED,
   CONSTRAINT fk_order_items_orders 
   FOREIGN KEY (order_id) REFERENCES orders(id)
          ON DELETE CASCADE
          ON UPDATE CASCADE,
    CONSTRAINT fk_orders_items_items
    FOREIGN KEY (item_id) REFERENCES items(id)
          ON DELETE CASCADE
);


CREATE TABLE stock_logs (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    item_id             INT NOT NULL,
    change_type         ENUM('SALE','ADD','REMOVE') NOT NULL,
    quantity            INT NOT NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_stock_logs_items
    FOREIGN KEY (item_id) REFERENCES items(id)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);