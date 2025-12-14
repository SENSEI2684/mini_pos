-- Insert Admin user
INSERT INTO users (username, password, role, approved) 
VALUES ('Admin', SHA2(CONCAT('#@!*&456','Admin'), 256), 'ADMIN', 1);

-- Insert normal User
INSERT INTO users (username, password, role, approved) 
VALUES ('User', SHA2(CONCAT('#@!*&456','User'), 256), 'USER', 1);


INSERT INTO categories (category_name) VALUES
('Phones'),
('Laptops'),
('HeadPhone'),
('Console');

INSERT INTO items (item_code, name, price, photo, category_id) VALUES
('ACER001','Acer Aspire Lite 15',2550000,'Acer_Aspire_Lite_15.png',2),
('APP001','Apple iPhone 13',2320000,'AppleiPhone13.jpg',1),
('APP002','Apple iPhone 16',3560000,'AppleiPhone16.jpg',1),
('APP003','Apple iPhone 16E',3150000,'AppleiPhone16E.jpg',1),
('APP004','Apple iPhone 17',4000000,'AppleiPhone17.jpg',1),
('APP005','Apple iPhone 17ProMax',5200000,'AppleiPhone17ProMax.jpg',1),
('APP006','Apple iPhone Air',2500000,'iPhone14Plus.jpg',2),
('APP007','Apple iPhone 14Plus',4320000,'iPhone14Plus.jpg',1),
('ASUS001','Asus tuf gaming a15',1200000,'Asus-tuf-gaming-a15.jpg',2),
('ASUS002','ASUS Expertbook B1',1050000,'ASUSExpertbookB1B1403CVA.jpg',2),
('ASUS003','ASUS ROGStrix G18',2800000,'ASUSROGStrixG18G815LP.jpg',2),
('ASUS004','ASUS Vivobook Go 15 Oled',900000,'ASUSVivobookE1504GA-BQ333W.jpg',2),
('ASUS005','Asus Tuf A17',1500000,'Asus_Tuf_A17_Gaming.jpg',2),
('BTS001','BEAT studiopro',350000,'BEAT-studiopro.jpg',3),
('DEL001','Dell Inspiron 3530',1100000,'DellInspiron3530.jpg',2),
('DEL002','Dell Inspiron 5440',1300000,'DellInspiron5440.jpg',2),
('GDT001','Gadet Max GM16',650000,'Gadet_Max_GM16.jpg',1),
('GDT002','Gadget Max GM15',620000,'Gadget Max GM15.jpg',1),
('SAM001','Samsung Galaxy A06',300000,'Galaxy-A06.jpg',1),
('LEN001','Lenovo legion5',1800000,'Lenovo-legion5.jpg',2),
('LEN002','Lenovo IdeaPad Slim3',950000,'LenovoIdeaPadSlim3.jpg',2),
('LEN003','Lenovo Notebook V15',1050000,'LenovoNotebookV15G4ABP.jpg',2),
('APP008','M3 MacBook Air',2610000,'M3_MacBook_Air.jpg',2),
('APP009','M4 MacBook Air',3000000,'M4_MacBook_Air.jpg',2),
('APP010','M5 MacBook Pro',4500000,'M5-MacBook_Pro.jpg',2),
('SAM002','Samsung galaxy s23 Ultra',5000000,'mm-galaxy-s23-Ultra.png',1),
('MSI001','MSI Modern 15',1400000,'MSI_Modern-15-f13m.jpg',2),
('MSI002','MSI Modern 14',1200000,'MSI_Modern_14.jpg',2),
('MSI003','MSI Venturepro 16',4800000,'MSI_Venturepro-16.jpg',2),
('MSI004','MSI venturepro 17',5000000,'MSI_venturepro-17.jpg',2),
('ONE001','Oneplus Nord 4',1200000,'Oneplus Nord 4.jpg',1),
('ONE002','Oneplus 12',3500000,'Oneplus-12.jpg',1),
('ONE003','Oneplus 12R',3200000,'Oneplus-12R.jpg',1),
('ONE004','Oneplus 13',3800000,'Oneplus-13.jpg',1),
('ONE005','Oneplus Nord CE3 Lite',900000,'Oneplus-Nord-CE-3-Lite.jpg',1),
('RMX001','Remax RB950HB',120000,'Remax_RB_930HB.jpg',3),
('RMX002','Remax_RB930HB',110000,'Remax_RB_930HB.jpg',3),
('SAM003','Samsubg S25 Plus',1000000,'Samsung-A07.jpg',1),
('SAM004','Samsung A07',350000,'Samsung-A07.jpg',1),
('SAM005','Samsung A17',600000,'Samsung-A17.jpg',1),
('SAM006','Samsung Z Fold 6',5500000,'Samsung-Galaxy-Z-Fold-6.jpg',1),
('SAM007','Samsung S24 FE',1800000,'Samsung-S24-FE.jpg',1),
('SAM008','Samsung S25 FE',2200000,'Samsung-S25-FE.jpg',1),
('SAM009','Samsung S25 Ultra',5800000,'Samsung-S25-Ultra-4.jpg',1),
('SAM010','Samsung S25',2500000,'Samsung-S25.jpg',1),
('SAM011','Samsung Z Flip',4200000,'Samsung-Z-Flip.jpg',1),
('SAM012','Samsung Z Fold-7',6000000,'Samsung-Z-Fold-7.jpg',1),
('NSW001','Nintendo Switch 2',5000000,'Nintendo Switch 2.jpg',4),
('PS001','PlayStation 1',3500000,'PlayStation 1.jpg',4),
('PS002','PlayStation 2',3600000,'PlayStation 2.jpg',4),
('PS005','PlayStation 5',4500000,'PlayStation 5.jpg',4);


INSERT INTO orders (total_amount, order_date) VALUES
(3200000,'2024-01-05 09:12:00'),
(4100000,'2024-01-06 11:30:00'),
(1800000,'2024-01-07 12:45:00'),
(5000000,'2024-01-08 13:15:00'),
(2700000,'2024-01-09 14:20:00'),
(3300000,'2024-01-10 15:10:00'),
(4700000,'2024-01-11 16:55:00'),
(3500000,'2024-01-12 17:30:00'),
(4200000,'2024-01-13 18:10:00'),
(2600000,'2024-01-14 19:05:00'),
(1250000,'2024-01-15 09:12:00'),
(3500000,'2024-01-16 11:23:00'),
(2550000,'2024-01-17 15:45:00'),
(4200000,'2024-01-18 18:30:00'),
(1800000,'2024-01-19 12:10:00'),
(2750000,'2024-01-20 11:50:00'),
(3900000,'2024-01-21 16:40:00'),
(3100000,'2024-01-22 15:15:00'),
(500000,'2024-01-23 09:20:00'),
(2000000,'2024-01-24 13:45:00'),
(3300000,'2024-01-25 17:05:00'),
(1500000,'2024-01-26 10:34:00'),
(4200000,'2024-01-27 19:22:00'),
(2600000,'2024-01-28 14:11:00'),
(3800000,'2024-01-29 17:58:00'),
(4700000,'2024-01-30 16:21:00'),
(1250000,'2024-01-31 11:09:00'),
(350000,'2024-02-01 10:45:00'),
(2900000,'2024-02-02 12:02:00'),
(4100000,'2024-02-03 15:50:00'),
(2400000,'2024-02-04 09:33:00'),
(3650000,'2024-02-05 18:45:00'),
(1950000,'2024-02-06 14:50:00'),
(2800000,'2024-02-07 16:22:00'),
(4700000,'2024-02-08 13:13:00'),
(1550000,'2024-02-09 11:31:00'),
(2990000,'2024-02-10 17:42:00'),
(5100000,'2024-02-11 19:00:00'),
(2200000,'2024-02-12 12:56:00'),
(6000000,'2024-02-13 18:22:00'),
(3200000,'2024-02-14 10:12:00'),
(4100000,'2024-02-15 11:30:00'),
(1800000,'2024-02-16 12:45:00'),
(5000000,'2024-02-17 13:15:00'),
(2700000,'2024-02-18 14:20:00'),
(3300000,'2024-02-19 15:10:00'),
(4700000,'2024-02-20 16:55:00'),
(3500000,'2024-02-21 17:30:00'),
(4200000,'2024-02-22 18:10:00'),
(2600000,'2024-02-23 19:05:00'),
(1250000,'2024-02-24 09:12:00'),
(3500000,'2024-02-25 11:23:00'),
(2550000,'2024-02-26 15:45:00'),
(4200000,'2024-02-27 18:30:00'),
(1800000,'2024-02-28 12:10:00'),
(2750000,'2024-02-29 11:50:00'),
(3900000,'2024-03-01 16:40:00'),
(3100000,'2024-03-02 15:15:00'),
(500000,'2024-03-03 09:20:00'),
(2000000,'2024-03-04 13:45:00'),
(3300000,'2024-03-05 17:05:00'),
(1500000,'2024-03-06 10:34:00'),
(4200000,'2024-03-07 19:22:00'),
(2600000,'2024-03-08 14:11:00'),
(3800000,'2024-03-09 17:58:00'),
(4700000,'2024-03-10 16:21:00'),
(1250000,'2024-03-11 11:09:00'),
(350000,'2024-03-12 10:45:00'),
(2900000,'2024-03-13 12:02:00'),
(4100000,'2024-03-14 15:50:00'),
(2400000,'2024-03-15 09:33:00'),
(3650000,'2024-03-16 18:45:00'),
(1950000,'2024-03-17 14:50:00'),
(2800000,'2024-03-18 16:22:00'),
(4700000,'2024-03-19 13:13:00'),
(1550000,'2024-03-20 11:31:00'),
(2990000,'2024-03-21 17:42:00'),
(5100000,'2024-03-22 19:00:00'),
(2200000,'2024-03-23 12:56:00'),
(6000000,'2024-03-24 18:22:00'),
(3200000,'2025-04-01 10:12:00'),
(4100000,'2025-04-02 11:30:00'),
(1800000,'2025-04-03 12:45:00'),
(5000000,'2025-04-04 13:15:00'),
(2700000,'2025-04-05 14:20:00'),
(3300000,'2025-04-06 15:10:00'),
(4700000,'2025-04-07 16:55:00'),
(3500000,'2025-04-08 17:30:00'),
(4200000,'2025-04-09 18:10:00'),
(2600000,'2025-04-10 19:05:00'),
(1250000,'2025-04-11 09:12:00'),
(3500000,'2025-04-12 11:23:00'),
(2550000,'2025-04-13 15:45:00'),
(4200000,'2025-04-14 18:30:00'),
(1800000,'2025-04-15 12:10:00'),
(2750000,'2025-04-16 11:50:00'),
(3900000,'2025-04-17 16:40:00'),
(3100000,'2025-04-18 15:15:00'),
(500000,'2025-04-19 09:20:00'),
(2000000,'2025-04-20 13:45:00'),
(3300000,'2025-04-21 17:05:00'),
(1500000,'2025-04-22 10:34:00'),
(4200000,'2025-04-23 19:22:00'),
(2600000,'2025-04-24 14:11:00'),
(3800000,'2025-04-25 17:58:00'),
(4700000,'2025-04-26 16:21:00'),
(1250000,'2025-04-27 11:09:00'),
(350000,'2025-04-28 10:45:00'),
(2900000,'2025-04-29 12:02:00'),
(4100000,'2025-04-30 15:50:00');

INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(1,2,1,2320000),(1,4,1,4000000),
(2,5,1,5200000),(2,12,1,1050000),(2,15,2,350000),
(3,3,1,3150000),(3,7,1,4320000),
(4,18,1,650000),(4,21,1,3000000),(4,9,1,1050000),
(5,11,1,1500000),(5,14,1,1100000),
(6,8,1,1200000),(6,10,1,900000),(6,30,1,350000),
(7,23,1,4500000),(7,21,1,3000000),
(8,18,1,650000),(8,7,1,4320000),
(9,29,1,120000),(9,31,1,5000000),
(10,20,2,620000),(10,13,1,350000),
(11,6,1,2500000),(11,22,1,2610000),
(12,17,2,3600000),(12,24,1,4800000),
(13,16,1,1100000),(13,32,1,5800000),
(14,11,1,1500000),(14,5,1,5200000),
(15,7,1,4320000),(15,9,1,1050000),
(16,3,1,3150000),(16,2,1,2320000),(16,4,1,4000000),
(17,12,1,1050000),(17,15,1,350000),
(18,6,1,2500000),(18,10,1,900000),
(19,8,1,1200000),(19,30,1,350000),
(20,18,1,650000),(20,21,1,3000000);

INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(21,2,1,2320000),(21,7,1,4320000);

-- Order 22
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(22,8,1,1200000),(22,10,1,900000),(22,15,1,350000);

-- Order 23
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(23,3,1,3150000),(23,6,1,2500000);

-- Order 24
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(24,11,1,1500000),(24,12,1,1050000);

-- Order 25
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(25,5,1,5200000),(25,14,1,1100000);

-- Order 26
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(26,18,1,650000),(26,21,1,3000000);

-- Order 27
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(27,23,1,4500000),(27,24,1,4800000);

-- Order 28
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(28,16,1,1100000),(28,32,1,5800000);

-- Order 29
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(29,9,1,1050000),(29,10,1,900000),(29,30,1,350000);

-- Order 30
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(30,7,1,4320000),(30,11,1,1500000);

-- Order 31
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(31,5,1,5200000),(31,6,1,2500000);

-- Order 32
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(32,3,1,3150000),(32,8,1,1200000);

-- Order 33
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(33,12,1,1050000),(33,15,1,350000);

-- Order 34
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(34,18,1,650000),(34,21,1,3000000);

-- Order 35
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(35,23,1,4500000),(35,24,1,4800000);

-- Order 36
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(36,16,1,1100000),(36,32,1,5800000);

-- Order 37
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(37,9,1,1050000),(37,10,1,900000),(37,30,1,350000);

-- Order 38
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(38,7,1,4320000),(38,11,1,1500000);

-- Order 39
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(39,5,1,5200000),(39,6,1,2500000);

-- Order 40
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(40,3,1,3150000),(40,8,1,1200000);

-- Order 41
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(41,12,1,1050000),(41,15,1,350000);

-- Order 42
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(42,18,1,650000),(42,21,1,3000000);

-- Order 43
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(43,23,1,4500000),(43,24,1,4800000);

-- Order 44
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(44,16,1,1100000),(44,32,1,5800000);

-- Order 45
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(45,9,1,1050000),(45,10,1,900000),(45,30,1,350000);

-- Order 46
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(46,7,1,4320000),(46,11,1,1500000);

-- Order 47
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(47,5,1,5200000),(47,6,1,2500000);

-- Order 48
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(48,3,1,3150000),(48,8,1,1200000);

-- Order 49
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(49,12,1,1050000),(49,15,1,350000);

-- Order 50
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(50,18,1,650000),(50,21,1,3000000);

-- Order 51
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(51,23,1,4500000),(51,24,1,4800000);

-- Order 52
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(52,16,1,1100000),(52,32,1,5800000);

-- Order 53
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(53,9,1,1050000),(53,10,1,900000),(53,30,1,350000);

-- Order 54
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(54,7,1,4320000),(54,11,1,1500000);

-- Order 55
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(55,5,1,5200000),(55,6,1,2500000);

-- Order 56
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(56,3,1,3150000),(56,8,1,1200000);

-- Order 57
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(57,12,1,1050000),(57,15,1,350000);

-- Order 58
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(58,18,1,650000),(58,21,1,3000000);

-- Order 59
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(59,23,1,4500000),(59,24,1,4800000);

-- Order 60
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(60,16,1,1100000),(60,32,1,5800000);

-- Order 61
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(61,9,1,1050000),(61,10,1,900000),(61,30,1,350000);

-- Order 62
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(62,7,1,4320000),(62,11,1,1500000);

-- Order 63
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(63,5,1,5200000),(63,6,1,2500000);

-- Order 64
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(64,3,1,3150000),(64,8,1,1200000);

-- Order 65
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(65,12,1,1050000),(65,15,1,350000);

-- Order 66
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(66,18,1,650000),(66,21,1,3000000);

-- Order 67
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(67,23,1,4500000),(67,24,1,4800000);

-- Order 68
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(68,16,1,1100000),(68,32,1,5800000);

-- Order 69
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(69,9,1,1050000),(69,10,1,900000),(69,30,1,350000);

-- Order 70
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(70,7,1,4320000),(70,11,1,1500000);

-- Order 71
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(71,5,1,5200000),(71,6,1,2500000);

-- Order 72
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(72,3,1,3150000),(72,8,1,1200000);

-- Order 73
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(73,12,1,1050000),(73,15,1,350000);

-- Order 74
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(74,18,1,650000),(74,21,1,3000000);

-- Order 75
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(75,23,1,4500000),(75,24,1,4800000);

-- Order 76
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(76,16,1,1100000),(76,32,1,5800000);

-- Order 77
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(77,9,1,1050000),(77,10,1,900000),(77,30,1,350000);

-- Order 78
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(78,7,1,4320000),(78,11,1,1500000);

-- Order 79
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(79,5,1,5200000),(79,6,1,2500000);

-- Order 80
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(80,3,1,3150000),(80,8,1,1200000);

-- Order 81
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(81,12,1,1050000),(81,15,1,350000);

-- Order 82
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(82,18,1,650000),(82,21,1,3000000);

-- Order 83
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(83,23,1,4500000),(83,24,1,4800000);

-- Order 84
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(84,16,1,1100000),(84,32,1,5800000);

-- Order 85
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(85,9,1,1050000),(85,10,1,900000),(85,30,1,350000);

-- Order 86
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(86,7,1,4320000),(86,11,1,1500000);

-- Order 87
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(87,5,1,5200000),(87,6,1,2500000);

-- Order 88
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(88,3,1,3150000),(88,8,1,1200000);

-- Order 89
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(89,12,1,1050000),(89,15,1,350000);

-- Order 90
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(90,18,1,650000),(90,21,1,3000000);

-- Order 91
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(91,23,1,4500000),(91,24,1,4800000);

-- Order 92
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(92,16,1,1100000),(92,32,1,5800000);

-- Order 93
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(93,9,1,1050000),(93,10,1,900000),(93,30,1,350000);

-- Order 94
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(94,7,1,4320000),(94,11,1,1500000);

-- Order 95
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(95,5,1,5200000),(95,6,1,2500000);

-- Order 96
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(96,3,1,3150000),(96,8,1,1200000);

-- Order 97
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(97,12,1,1050000),(97,15,1,350000);

-- Order 98
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(98,18,1,650000),(98,21,1,3000000);

-- Order 99
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(99,23,1,4500000),(99,24,1,4800000);

-- Order 100
INSERT INTO order_items (order_id, item_id, quantity, price) VALUES
(100,16,1,1100000),(100,32,1,5800000);


