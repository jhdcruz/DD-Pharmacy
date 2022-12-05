CREATE DATABASE IF NOT EXISTS `dd_pharmacy`
    /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */
    /*!80016 DEFAULT ENCRYPTION='N' */;

USE `dd_pharmacy`;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+08:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


--
-- Table structure for table `products`
--
DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `products`
(
    `pid`          int         NOT NULL AUTO_INCREMENT,
    `product_code` varchar(45) NOT NULL,
    `product_name` varchar(45) NOT NULL,
    `cost_price`   double      NOT NULL,
    `sell_price`   double      NOT NULL,
    `quantity`     int         NOT NULL,
    `brand`        varchar(45) NOT NULL,
    PRIMARY KEY (`pid`),
    UNIQUE KEY `product_code_UNIQUE` (`product_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 130
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customers`
--
DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `customers`
(
    `cid`           int         NOT NULL AUTO_INCREMENT,
    `customer_code` varchar(45) NOT NULL,
    `full_name`     varchar(45) NOT NULL,
    `location`      varchar(45) NOT NULL,
    `phone`         varchar(45) NOT NULL,
    PRIMARY KEY (`cid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 307
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchaseinfo`
--
DROP TABLE IF EXISTS `purchaseinfo`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `purchaseinfo`
(
    `purchase_id`   int         NOT NULL AUTO_INCREMENT,
    `supplier_code` varchar(45) NOT NULL,
    `product_code`  varchar(45) NOT NULL,
    `date`          varchar(45) NOT NULL,
    `quantity`      int         NOT NULL,
    `total_cost`    double      NOT NULL,
    PRIMARY KEY (`purchase_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1012
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `salesinfo`
--
DROP TABLE IF EXISTS `salesinfo`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `salesinfo`
(
    `sales_id`      int         NOT NULL AUTO_INCREMENT,
    `date`          varchar(45) NOT NULL,
    `product_code`  varchar(45) NOT NULL,
    `customer_code` varchar(45) NOT NULL,
    `quantity`      int         NOT NULL,
    `revenue`       double      NOT NULL,
    `sold_by`       varchar(45) NOT NULL,
    PRIMARY KEY (`sales_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2013
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `suppliers`
--
DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `suppliers`
(
    `sid`           int         NOT NULL AUTO_INCREMENT,
    `supplier_code` varchar(45) NOT NULL,
    `full_name`     varchar(45) NOT NULL,
    `location`      varchar(45) NOT NULL,
    `mobile`        varchar(10) NOT NULL,
    PRIMARY KEY (`sid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 409
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userlogs`
--
DROP TABLE IF EXISTS `userlogs`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `userlogs`
(
    `username` varchar(45) NOT NULL,
    `in_time`  varchar(45) NOT NULL,
    `out_time` varchar(45) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `users`
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `name`      varchar(45)  NOT NULL,
    `phone`     varchar(12)  NOT NULL,
    `username`  varchar(20)  NOT NULL,
    `password`  varchar(200) NOT NULL,
    `user_type` varchar(45)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Default admin account
-- >> This should be removed after a new admin account is created
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES (0, 'Admin', '00000000000', 'admin', 'admin', 'Administrator');
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
