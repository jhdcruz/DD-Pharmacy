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
-- Table structure for table `medicines`
--
DROP TABLE IF EXISTS `medicines`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `medicines`
(
    `pid`             INT AUTO_INCREMENT,
    `medicine_code`   VARCHAR(45)   NOT NULL,
    `medicine_name`   VARCHAR(100)  NOT NULL,
    `description`     VARCHAR(1200) NOT NULL,
    `cost_price`      DOUBLE        NOT NULL,
    `sell_price`      DOUBLE        NOT NULL,
    `quantity`        INT           NOT NULL,
    `supplied_by`     VARCHAR(100)  NOT NULL,
    `expiration_date` DATETIME      NOT NULL,
    `last_updated`    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`pid`),
    UNIQUE KEY `medicine_code_UNIQUE` (`medicine_code`)
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
    `cid`           INT           NOT NULL AUTO_INCREMENT,
    `customer_code` VARCHAR(20)   NOT NULL UNIQUE,
    `last_name`     VARCHAR(200)  NOT NULL,
    `first_name`    VARCHAR(200)  NOT NULL,
    `middle_name`   VARCHAR(200)           DEFAULT 'N/A',
    `conditions`    VARCHAR(1000) NOT NULL DEFAULT 'N/A',
    `phone`         VARCHAR(13)   NOT NULL,
    `last_updated`  DATETIME               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`cid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `purchaseinfo`
--
DROP TABLE IF EXISTS `restock`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `restock`
(
    `purchase_id`   INT         NOT NULL AUTO_INCREMENT,
    `supplier_code` VARCHAR(20) NOT NULL,
    `medicine_code` VARCHAR(20) NOT NULL,
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
-- Table structure for table `suppliers`
--
DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `suppliers`
(
    `sid`           INT          NOT NULL AUTO_INCREMENT,
    `supplier_code` VARCHAR(20)  NOT NULL UNIQUE,
    `full_name`     VARCHAR(100) NOT NULL,
    `location`      VARCHAR(100) NOT NULL,
    `contact`       VARCHAR(13)  NOT NULL,
    `last_updated`  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`sid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7000
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timesheet`
--
DROP TABLE IF EXISTS `timesheet`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `timesheet`
(
    `username` VARCHAR(45)  NOT NULL,
    `name`     VARCHAR(100) NOT NULL,
    `in_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `out_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logs`
--
DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `logs`
(
    `user_id` int          NOT NULL,
    `event`   VARCHAR(500) NOT NULL,
    `date`    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    `id`         INT            NOT NULL AUTO_INCREMENT UNIQUE,
    `username`   VARCHAR(45)    NOT NULL UNIQUE,
    `password`   VARBINARY(100) NOT NULL,
    `name`       VARCHAR(200)   NOT NULL,
    `phone`      VARCHAR(12)    NOT NULL,
    `user_type`  VARCHAR(45)    NOT NULL,
    `secret_key` VARBINARY(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5000
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- ---------------------------------------------
--             SAMPLE DATA DUMP               --
-- ---------------------------------------------
--
-- Sample medicines
--
LOCK TABLES `medicines` WRITE;
/*!40000 ALTER TABLE `medicines`
    DISABLE KEYS */;
INSERT INTO `medicines`
VALUES (9000, 'UNILAB001', 'Acyclovir (200mg)',
        'Antiviral medication primarily used for the treatment of herpes simplex virus infections, chickenpox, shingles, prevention of cytomegalovirus infections following transplant and severe complications of Epstein–Barr virus infection. | 100 Capsules',
        220, 228, 42, 'Unilab, Inc.',
        '2023-06-13 00:00:00', '2022-12-12 21:51:18'),
       (9001, 'UNILAB002', 'Amoxicillin (500mg)',
        'Antibiotic medication used to treat a number of bacterial infections: middle ear infection, strep throat, pneumonia, skin infections, and urinary tract infections among others | 10 Capsules',
        30, 36, 70, 'Unilab, Inc.',
        '2023-07-26 00:00:00', '2022-12-12 22:13:53'),
       (9002, 'INL0249', 'Paracetamol (500mg)',
        'Medication used to treat fever and mild to moderate pain. Benefits of its use for unclear fever. | 10 Capsules',
        28, 35, 108, 'Interphil Laboratories, Inc.',
        '2023-12-13 00:00:00', '2022-12-12 21:48:56'),
       (9003, 'INL0250', 'Paracetamol (250mg)',
        'Medication used to treat fever and mild to moderate pain. Benefits of its use for unclear fever. | 10 Capsules',
        25, 28, 98, 'Interphil Laboratories, Inc.',
        '2023-12-13 00:00:00', '2022-12-12 21:48:28'),
       (9004, 'TELMC1292', 'Levothyroxine (100mg)',
        'Treat thyroid hormone deficiency, including hyperthyroidism and a severe form known as myxedema coma. | 20 Tablets',
        60, 65, 30, 'TELSTAR Manufacturing Corp.',
        '2023-06-07 00:00:00', '2022-12-12 21:48:10'),
       (9005, 'ADPC1208', 'Fluconazole (150mg)',
        'Antifungal medication used for a number of fungal infections: candidiasis, blastomycosis, coccidiodomycosis, cryptococcosis, histoplasmosis, dermatophytosis, and pityriasis versicolor. | 20 Capsules',
        100, 108, 72, 'ADP Pharma Corp.',
        '2023-12-13 00:00:00', '2022-12-12 21:49:01'),
       (9006, 'UNAHCO00834', 'Diosmin Plus (900mg)',
        'Treat circulatory issues, such as hemorrhoids and varicose veins. | 90 Capsules', 115, 120, 21, 'UNAHCO, Inc.',
        '2023-09-27 00:00:00', '2022-12-12 21:46:47'),
       (9007, 'ADPC1249', 'Tranexamic Acid (500mg)',
        'Treat or prevent excessive blood loss from major trauma, postpartum bleeding, surgery, tooth removal, nosebleeds, heavy menstruation, and hereditary angioedema. | 60 Tablets',
        238, 244, 19, 'ADP Pharma Corp.',
        '2023-05-16 00:00:00', '2022-12-12 21:55:29');
/*!40000 ALTER TABLE `medicines`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Default accounts
-- >> This should be removed after a new accounts are created.
-- admin:admin
-- emp:emp
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES (5000, 'admin', 0x76C8907651328EF476FF7A681161084093AE377BA0C97912F5C254F391D967F665, 'Admin', '000000000000',
        'Administrator', 0x6C05BF5747BEDE7C6DEBD22AAF06B85F);

/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Sample suppliers
--
LOCK TABLES `suppliers` WRITE;
/*!39999 ALTER TABLE `suppliers`
    DISABLE KEYS */;
INSERT INTO `suppliers`
VALUES (6999, 'SUPP0001', 'Unilab, Inc.', 'Mandaluyong, Manila,', '09175584567', DEFAULT),
       (7000, 'SUPP0002', 'UNAHCO, Inc.', 'Mandaluyong, Manila,', '09935890825', DEFAULT),
       (7001, 'SUPP0003', 'Interphil Laboratories, Inc.', 'Cabuyao, Manila,', '09329578937', DEFAULT),
       (7002, 'SUPP0004', 'ADP Pharma Corp.', 'Taguig, Manila,', '09152975832', DEFAULT),
       (7003, 'SUPP0005', 'TELSTAR Manufacturing Corp.', 'Sta. Rosa, Manila,', '09995278231', DEFAULT),
       (7004, 'SUPP0006', 'Ambicare Pharmaceuticals, Inc.', 'Paranaque, Manila,', '09157589379', DEFAULT);
/*!39999 ALTER TABLE `suppliers`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Sample suppliers
--
LOCK TABLES `customers` WRITE;
/*!39999 ALTER TABLE `customers`
    DISABLE KEYS */;
INSERT INTO `customers`
VALUES (1000, 'DDPAL100', 'Dela Cruz', 'Joshua Hero', 'Herrera', DEFAULT, '09175584567', DEFAULT),
       (1001, 'DDPAV502', 'Santos', 'Rhyz', DEFAULT, 'Asthma', '09935890825', DEFAULT),
       (1002, 'DDPAX903', 'Herrera', 'Avon', DEFAULT, DEFAULT, '09329578937', DEFAULT),
       (1003, 'DDPCC204', 'Herbert', 'Dean', 'Tano', DEFAULT, '09152975832', DEFAULT),
       (1004, 'DDPLD042', 'Barrantes', 'Kim', 'Logronio', 'Lung Cancer, Asthma', '09995278231', DEFAULT),
       (1005, 'DDPKK113', 'Almirante', 'Daniel', DEFAULT, DEFAULT, '09157589379', DEFAULT);
/*!39999 ALTER TABLE `customers`
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
