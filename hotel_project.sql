-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel_project
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL,
  `btime` datetime DEFAULT NULL,
  `check_in` datetime DEFAULT NULL,
  `check_out` datetime DEFAULT NULL,
  `deposit` int DEFAULT NULL,
  `bstatus` int DEFAULT '0',
  `deleted` int DEFAULT '0',
  `paytime` datetime DEFAULT NULL,
  `total` mediumtext,
  PRIMARY KEY (`bid`),
  KEY `makh_fk_idx` (`cid`),
  CONSTRAINT `fk1_booking_customer` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (39,20,'2024-05-06 09:22:39','2024-05-06 14:00:00','2024-05-07 12:00:00',499000,2,0,'2024-05-06 09:23:55','698700'),(40,21,'2024-05-06 09:50:18','2024-05-06 14:00:00','2024-05-07 12:00:00',499000,2,0,'2024-05-06 09:51:46','748700'),(41,22,'2024-05-06 12:17:57','2024-05-06 14:00:00','2024-05-07 12:00:00',599000,2,0,'2024-05-06 12:22:26','878700'),(42,23,'2024-05-06 12:37:19','2024-05-03 14:00:00','2024-05-04 12:00:00',599000,2,0,'2024-05-06 12:41:45','2156400');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_room`
--

DROP TABLE IF EXISTS `booking_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_room` (
  `brid` int NOT NULL,
  `rid` int NOT NULL,
  `rprice` int DEFAULT NULL,
  `real_checkin` datetime DEFAULT NULL,
  `real_checkout` datetime DEFAULT NULL,
  `surcharge` int DEFAULT '0',
  PRIMARY KEY (`brid`,`rid`),
  KEY `maphong_fk_idx` (`rid`),
  CONSTRAINT `fk1_booking_room_booking` FOREIGN KEY (`brid`) REFERENCES `booking` (`bid`) ON DELETE CASCADE,
  CONSTRAINT `fk2_booking_room_room` FOREIGN KEY (`rid`) REFERENCES `room` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_room`
--

LOCK TABLES `booking_room` WRITE;
/*!40000 ALTER TABLE `booking_room` DISABLE KEYS */;
INSERT INTO `booking_room` VALUES (39,5,499000,'2024-05-06 09:22:50','2024-05-06 09:23:04',149700),(40,1,499000,'2024-05-06 09:50:51','2024-05-06 09:51:13',149700),(41,16,599000,'2024-05-06 12:18:32','2024-05-06 12:19:56',179700),(42,6,599000,'2024-05-03 12:37:38','2024-05-06 12:39:45',359400);
/*!40000 ALTER TABLE `booking_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_service`
--

DROP TABLE IF EXISTS `booking_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_service` (
  `bsid` int NOT NULL AUTO_INCREMENT,
  `bid` int DEFAULT NULL,
  `sid` int DEFAULT NULL,
  `bsprice` int DEFAULT NULL,
  `bstime` datetime DEFAULT NULL,
  `rid` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`bsid`),
  KEY `fk1_booking_idx` (`bid`),
  KEY `fk1_service_idx` (`sid`),
  CONSTRAINT `fk1_booking_service_booking` FOREIGN KEY (`bid`) REFERENCES `booking` (`bid`) ON DELETE CASCADE,
  CONSTRAINT `fk2_booking_service_service` FOREIGN KEY (`sid`) REFERENCES `service` (`sid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_service`
--

LOCK TABLES `booking_service` WRITE;
/*!40000 ALTER TABLE `booking_service` DISABLE KEYS */;
INSERT INTO `booking_service` VALUES (5,39,5,50000,'2024-05-06 00:00:00',5,1),(6,40,1,100000,'2024-05-06 00:00:00',1,1),(7,41,5,50000,'2024-05-06 12:20:35',16,2);
/*!40000 ALTER TABLE `booking_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(45) DEFAULT NULL,
  `cgender` tinyint DEFAULT '0',
  `cidentity` char(12) DEFAULT NULL,
  `caddress` varchar(100) DEFAULT NULL,
  `cphone` char(10) DEFAULT NULL,
  `deleted` varchar(45) DEFAULT '0',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `cmnd_UNIQUE` (`cidentity`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (20,'Bao Loc',0,'04124124343','Thu Duc','0913518753','0'),(21,'Bao An',0,'0212412','Thu Duc','0913518753','0'),(22,'Loc Cute',1,'01231242512','Thu Duc','0913518753','0'),(23,'Alice',1,'02934124034','Wolvesville','0913519763','0');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hosothuephong`
--

DROP TABLE IF EXISTS `hosothuephong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hosothuephong` (
  `mathuephong` int unsigned NOT NULL AUTO_INCREMENT,
  `madatphong` int unsigned DEFAULT NULL,
  `thucnhan` datetime DEFAULT NULL,
  `thuctra` datetime DEFAULT NULL,
  `tongthanhtoan` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`mathuephong`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hosothuephong`
--

LOCK TABLES `hosothuephong` WRITE;
/*!40000 ALTER TABLE `hosothuephong` DISABLE KEYS */;
/*!40000 ALTER TABLE `hosothuephong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `rname` varchar(45) DEFAULT NULL,
  `tid` int unsigned DEFAULT NULL,
  `current_booking` int DEFAULT '0',
  `rstatus` tinyint DEFAULT '1',
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `tenphong_UNIQUE` (`rname`),
  KEY `loaiphong_fk_idx` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'S1',1,0,1,0),(2,'S2',1,0,1,0),(3,'D1',2,0,1,0),(4,'S3',1,0,1,0),(5,'S4',1,0,1,0),(6,'D2',2,0,1,0),(7,'D3',2,0,1,0),(8,'T1',3,0,1,0),(9,'T2',3,0,1,0),(10,'T3',3,0,1,0),(11,'TW1',4,0,1,0),(12,'S5',1,0,1,0),(13,'T4',3,0,1,0),(14,'TW2',4,0,1,0),(15,'TW3',4,0,1,0),(16,'D4',2,0,1,0),(17,'TW4',4,0,1,0),(18,'S6',1,0,1,0);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_type`
--

DROP TABLE IF EXISTS `room_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_type` (
  `tid` int unsigned NOT NULL AUTO_INCREMENT,
  `tname` varchar(45) DEFAULT NULL,
  `tprice` int unsigned DEFAULT NULL,
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`tid`),
  UNIQUE KEY `tenloaiphong_UNIQUE` (`tname`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_type`
--

LOCK TABLES `room_type` WRITE;
/*!40000 ALTER TABLE `room_type` DISABLE KEYS */;
INSERT INTO `room_type` VALUES (1,'Single',499000,0),(2,'Double',599000,0),(3,'Triple',799000,0),(4,'Twin',699000,0);
/*!40000 ALTER TABLE `room_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `sname` varchar(45) DEFAULT NULL,
  `sunit` char(15) DEFAULT NULL,
  `sprice` int unsigned DEFAULT NULL,
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COMMENT='Dịch vụ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'Giặt ủi','Lượt',100000,0),(4,'Bữa sáng - Thường','Suất',30000,0),(5,'Bữa sáng - Cao cấp','Suất',50000,0),(6,'Kỹ nữ','Người',500000,0);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'hotel_project'
--

--
-- Dumping routines for database 'hotel_project'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-08 14:40:58
