
# Mini POS Program

This project is a cashier/Point of Sale (POS) system developed using Java and MySQL. The system allows cashiers to manually select items, manage cart transactions, and print digital receipts. It starts with a login session and supports user registration, which requires admin approval.

The system includes two account types: ADMIN and USER. USER accounts can register themselves and request access, while ADMIN accounts can create and manage both ADMIN and USER accounts.

Key functionalities include item selection, cart management, payment processing, receipt printing, and database storage. Admins have extended privileges such as item and category management, account management, and full access to sales reports.


## Features
**User Account Features (USER)**
- Registration request for USER accounts
- Login session after admin approval
- Select items, set quantities, and add to cart
- Remove unwanted items or reset the entire cart
- Process calculate change, and print receipt
- Automatic storage of orders and order items in the database

**Admin Account Features (ADMIN)**
- Full account management:
    - Create new accounts (ADMIN or USER)
    - Delete accounts
    - Change passwords
    - Approve new USER registrations
- Item management:
    - Create, update, delete items
    - Update prices
    - Search items by category
- Category management:
    - Add new categories
    - Update category names
- Refresh main sale panel to reflect new items instantly
- Refresh account panel to show newly registered accounts

**Sales & Reports**
- Sale record session with three types of reports:
    - Item-level sales report
    - Category-level sales report
    - Daily total sales report
- Reports can be filtered by:
    - Start and end time
    - Item code


## Tech Used
Java (Swing for UI)

MySQL (database for orders, items, categories, and accounts)

JDBC (database connectivity)

DAO & Service Layer design patterns


## Setup & Configuration
**1. Database Setup**

- In the `DB/` folder, you will find SQL files containing:
```
    - data.sql
    - schema.sql 
```    
- Import these SQL files into your MySQL database.

**2. Database Configuration**

- Under `src/main/resources/`, there is a `config.properties` file.

- Open the file and set your database credentials:
```
db.url=jdbc:mysql://localhost:3306/mini_pos_db
db.user=your_db_username
db.password=your_db_password
```

**3. Run the Program**
- After setting up the database and updating config.properties, run `MainFrame.java` under `src/main/java/com/mini_pos/dao/ui/` to start the application.

**4. Use Default Accounts to Login**
- ADMIN Role
    - Username: Admin
    - Password: Admin
- USER Role
    - Username: User
    - Username: User
      
## Project Structure
```
.
└── mini_pos_program/
    ├── src/main/
    │   ├── java/com/mini_pos/
    │   │   ├── dao/
    │   │   │   ├── etinity/
    │   │   │   │   ├── Cart.java
    │   │   │   │   ├── CartWithItems.java
    │   │   │   │   ├── Categories.java
    │   │   │   │   ├── Items.java
    │   │   │   │   ├── ItemsWithCategories.java
    │   │   │   │   ├── Order.java
    │   │   │   │   ├── OrderItem.java
    │   │   │   │   ├── Role.java
    │   │   │   │   ├── SaleReport.java
    │   │   │   │   └── Users.java
    │   │   │   ├── impl/
    │   │   │   │   ├── CartDaoImpl.java
    │   │   │   │   ├── CategoriesDaoImpl.java
    │   │   │   │   ├── ItemsDaoImpl.java
    │   │   │   │   ├── OrderDaoImpl.java
    │   │   │   │   ├── OrderItemDaoImpl.java
    │   │   │   │   ├── SaleReportDaoImpl.java
    │   │   │   │   └── UserDaoImpl.java
    │   │   │   ├── service/
    │   │   │   │   ├── CartService.java
    │   │   │   │   ├── CartServiceImpl.java
    │   │   │   │   ├── CategoriesService.java
    │   │   │   │   ├── CategoriesServiceImpl.java
    │   │   │   │   ├── ItemsService.java
    │   │   │   │   ├── ItemsServiceImpl.java
    │   │   │   │   ├── OrderItemService.java
    │   │   │   │   ├── OrderItemServiceImpl.java
    │   │   │   │   ├── OrderService.java
    │   │   │   │   ├── OrderServiceImpl.java
    │   │   │   │   ├── SaleReportService.java
    │   │   │   │   ├── SaleReportServiceImpl.java
    │   │   │   │   ├── UserService.java
    │   │   │   │   └── UserServiceImpl.java
    │   │   │   ├── ui/
    │   │   │   │   ├── AccountSetting.form
    │   │   │   │   ├── AccountSetting.java
    │   │   │   │   ├── ItemStorage.form
    │   │   │   │   ├── ItemStorage.java
    │   │   │   │   ├── MainFrame.form
    │   │   │   │   ├── MainFrame.java
    │   │   │   │   ├── Sale_Rate.form
    │   │   │   │   └── Sale_Rate.java
    │   │   │   ├── BaseDao.java
    │   │   │   ├── CartDao.java
    │   │   │   ├── CategoriesDao.java
    │   │   │   ├── ItemsDao.java
    │   │   │   ├── OrderDao.java
    │   │   │   ├── OrderItem.java
    │   │   │   ├── SaleReportDao.java
    │   │   │   └── UserDao.java
    │   │   └── helper_function/
    │   │       ├── DaoException.java
    │   │       ├── FileNameGet.java
    │   │       ├── ImageCache.java
    │   │       ├── ItemCard.java
    │   │       ├── PasswordHide.java
    │   │       ├── Session.java
    │   │       ├── ShadowRenderer.java
    │   │       ├── TabbedPaneCustom.java
    │   │       ├── TabbedPaneCustomUI.java
    │   │       └── ValidationException.java  
    │   └── resources/
    │       ├── Static/
    │       │   ├── images
    │       │   └── logo
    │       └── config.properties  
    ├── DB/
    │   ├── data.sql
    │   └── schema.sql
    └── pom.xml
```
