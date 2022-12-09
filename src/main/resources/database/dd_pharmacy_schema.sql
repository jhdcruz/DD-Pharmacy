DROP DATABASE IF EXISTS `dd_pharmacy`;
CREATE DATABASE IF NOT EXISTS `dd_pharmacy`
    /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */;

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
    `pid`             INT AUTO_INCREMENT,
    `product_code`    VARCHAR(45)  NOT NULL,
    `product_name`    VARCHAR(45)  NOT NULL,
    `description`     VARCHAR(300) NOT NULL,
    `cost_price`      DOUBLE       NOT NULL,
    `sell_price`      DOUBLE       NOT NULL,
    `quantity`        INT          NOT NULL,
    `supplied_by`     VARCHAR(45)  NOT NULL,
    `expiration_date` DATETIME     NOT NULL,
    `last_updated`    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`pid`),
    UNIQUE KEY `product_code_UNIQUE` (`product_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9000
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
    `cid`           INT         NOT NULL AUTO_INCREMENT,
    `customer_code` VARCHAR(45) NOT NULL UNIQUE,
    `full_name`     VARCHAR(45) NOT NULL,
    `location`      VARCHAR(45) NOT NULL,
    `phone`         VARCHAR(45) NOT NULL,
    `last_updated`  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`cid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
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
    `purchase_id`   INT         NOT NULL AUTO_INCREMENT,
    `supplier_code` VARCHAR(45) NOT NULL,
    `product_code`  VARCHAR(45) NOT NULL,
    `date`          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `quantity`      INT         NOT NULL,
    `total_cost`    DOUBLE      NOT NULL,
    PRIMARY KEY (`purchase_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4000
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
    `sales_id`      INT         NOT NULL AUTO_INCREMENT,
    `product_code`  VARCHAR(45) NOT NULL,
    `customer_code` VARCHAR(45) NOT NULL,
    `quantity`      INT         NOT NULL,
    `revenue`       DOUBLE      NOT NULL,
    `sold_by`       VARCHAR(45) NOT NULL,
    `date`          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`sales_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3000
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
    `sid`           INT         NOT NULL AUTO_INCREMENT,
    `supplier_code` VARCHAR(45) NOT NULL UNIQUE,
    `full_name`     VARCHAR(45) NOT NULL,
    `location`      VARCHAR(45) NOT NULL,
    `contact`       VARCHAR(13) NOT NULL,
    `last_updated`  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`sid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7000
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
    `username` VARCHAR(45) NOT NULL,
    `in_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `out_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    `id`        INT          NOT NULL AUTO_INCREMENT UNIQUE,
    `username`  VARCHAR(20)  NOT NULL UNIQUE,
    `password`  VARCHAR(200) NOT NULL,
    `name`      VARCHAR(45)  NOT NULL,
    `phone`     VARCHAR(12)  NOT NULL,
    `user_type` VARCHAR(45)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5000
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Default accounts
-- >> This should be removed after a new accounts are created.
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES (5000, 'admin', 'admin', 'Admin', '000000000000', 'Administrator'),
       (5001, 'emp', 'emp', 'Default Employee', '000000000000', 'Employee');
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
