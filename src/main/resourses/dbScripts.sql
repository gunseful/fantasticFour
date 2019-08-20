CREATE TABLE PRODUCTS
(
    ID    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    NAME  VARCHAR(255),
    PRICE INT NOT NULL
);

CREATE TABLE USERS
(
    ID        INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    NICKNAME  VARCHAR(70) NOT NULL UNIQUE,
    PASSWORD  VARCHAR(70) NOT NULL,
    NAME      VARCHAR(70) NOT NULL,
    IS_ADMIN   BIT DEFAULT NULL,
    IS_BLOCKED BIT DEFAULT FALSE
);

CREATE TABLE ORDERS
(
    ID  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_ID INT,
    CREATEDAT  DATE,
    STATE      ENUM ('PAID','NOT_ORDERED','ORDERED') DEFAULT 'NOT_ORDERED',
    FOREIGN KEY (CUSTOMER_ID) REFERENCES USERS (ID)

);
CREATE TABLE PRODUCTS_ORDERS
(
    PRODUCT_ID INT,
    ORDER_ID   INT,
    COUNT int default 1 CHECK (COUNT<=10),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS (ID),
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID),

);

