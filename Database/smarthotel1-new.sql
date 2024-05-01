-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: smarthotel
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
-- Table structure for table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkout` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cus_name` varchar(100) NOT NULL,
  `cus_father` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `cus_nic` bigint NOT NULL,
  `cus_date` date NOT NULL,
  `out_date` date NOT NULL,
  `phone` bigint NOT NULL,
  `cus_country` varchar(50) NOT NULL,
  `cus_city` varchar(50) NOT NULL,
  `cus_adult` int NOT NULL,
  `cus_child` int NOT NULL,
  `room_type` varchar(100) NOT NULL,
  `room_no` int NOT NULL,
  `room_cost` float NOT NULL,
  `taxes` float NOT NULL,
  `total` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_room_checkout` (`room_no`),
  CONSTRAINT `fk_room_checkout` FOREIGN KEY (`room_no`) REFERENCES `room` (`room_number`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
INSERT INTO `checkout` VALUES (17,'thuat','Vo','123a duong 10',123456789,'2024-04-26','2024-04-26',923781378,'Haiti  ','None',2,0,'Single',106,100,10,110),(18,'Duy','Nguyen','123A',123456789,'2024-04-26','2024-04-26',1343536364,'Albania  ','None',2,0,'Luxury',103,500,10,510),(19,'Vu','Hoang','123A',123467890,'2024-04-26','2024-04-26',947487356,'Afghanistan  ','None',2,0,'Double',102,200,1,201);
/*!40000 ALTER TABLE `checkout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `father` varchar(100) NOT NULL,
  `idcard` bigint NOT NULL,
  `phone` bigint NOT NULL,
  `designation` varchar(100) NOT NULL,
  `salary` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (2,'Khaliq Dad','Ghazi Muhammad',5650311674779,3322996178,'IT Officer',65000),(3,'Zeeshan','Haji Sab',5245487554459,3322665598,'Manager',70000);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expenses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `nature` varchar(255) NOT NULL,
  `expens` double NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expenses`
--

LOCK TABLES `expenses` WRITE;
/*!40000 ALTER TABLE `expenses` DISABLE KEYS */;
INSERT INTO `expenses` VALUES (1,'Khaliq dad','tution fee for university',10000,'2018-11-04');
/*!40000 ALTER TABLE `expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cus_name` varchar(100) NOT NULL,
  `cus_father` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `cus_nic` bigint NOT NULL,
  `cus_date` date NOT NULL,
  `phone` bigint NOT NULL,
  `cus_country` varchar(50) NOT NULL,
  `cus_city` varchar(50) NOT NULL,
  `cus_adult` int NOT NULL,
  `cus_child` int NOT NULL,
  `room_type` varchar(100) NOT NULL,
  `room_no` int NOT NULL,
  `room_cost` float NOT NULL,
  `taxes` float NOT NULL,
  `total` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_room_guests` (`room_no`),
  CONSTRAINT `fk_room_guests` FOREIGN KEY (`room_no`) REFERENCES `room` (`room_number`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` VALUES (28,'Dung','Nguyen','123A',123456789,'2024-04-26',946356245,'Afghanistan  ','None',2,0,'Single',206,100,10,110),(31,'Hoang','Vo','123A',123456789,'2024-04-26',232464654435,'Cuba  ','None',2,0,'Delux',104,1000,10,1010);
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (2,'admin','03322','Administrator'),(3,'zeeshan','03322','admin'),(4,'aurang','aurang','admin'),(5,'khaliq','03322afg','Admin');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `phone` bigint NOT NULL,
  `roomtype` varchar(100) NOT NULL,
  `roomno` int NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_number` int NOT NULL,
  `room_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `floor` int NOT NULL,
  `status` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cost` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_room_number` (`room_number`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,101,'Single',1,'Occupied',100),(2,102,'Double',1,'Available',200),(3,103,'Luxury',1,'Available',500),(4,104,'Delux',1,'Occupied',1000),(5,105,'Family',1,'Available',750),(6,106,'Single',1,'Occupied',100),(7,107,'Double',1,'Available',200),(8,108,'Luxury',1,'Available',500),(9,109,'Delux',1,'Available',1000),(10,110,'Family',1,'Available',750),(11,201,'Single',2,'Occupied',100),(12,202,'Double',2,'Available',200),(13,203,'Luxury',2,'Available',500),(14,204,'Delux',2,'Available',1000),(15,205,'Family',2,'Available',750),(16,206,'Single',2,'Occupied',100),(17,207,'Double',2,'Available',200),(18,208,'Luxury',2,'Available',500),(19,209,'Delux',2,'Available',1000),(20,210,'Family',2,'Available',750),(21,301,'Single',3,'Available',100),(22,302,'Double',3,'Available',200),(23,303,'Luxury',3,'Available',500),(24,304,'Delux',3,'Available',1000),(25,305,'Family',3,'Available',750),(26,306,'Single',3,'Available',100),(27,307,'Double',3,'Available',200),(28,308,'Luxury',3,'Available',500),(29,309,'Delux',3,'Available',1000),(30,310,'Family',3,'Available',750),(31,401,'Single',4,'Available',100),(32,402,'Double',4,'Available',200),(33,403,'Luxury',4,'Available',500),(34,404,'Delux',4,'Available',1000),(35,405,'Family',4,'Available',750),(36,406,'Single',4,'Available',100),(37,407,'Double',4,'Available',200),(38,408,'Luxury',4,'Available',500),(39,409,'Delux',4,'Available',1000),(40,410,'Family',4,'Available',750);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_expenses`
--

DROP TABLE IF EXISTS `room_expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_expenses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` int NOT NULL,
  `expense_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk1_room_idx` (`room_id`),
  KEY `fk2_expense_idx` (`expense_id`),
  CONSTRAINT `fk1_room` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  CONSTRAINT `fk2_expense` FOREIGN KEY (`expense_id`) REFERENCES `expenses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_expenses`
--

LOCK TABLES `room_expenses` WRITE;
/*!40000 ALTER TABLE `room_expenses` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'smarthotel'
--

--
-- Dumping routines for database 'smarthotel'
--
/*!50003 DROP PROCEDURE IF EXISTS `GenerateRooms` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GenerateRooms`()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE j INT DEFAULT 1;
  DECLARE room_types VARCHAR(100);
  
  WHILE i <= 4 DO
    WHILE j <= 10 DO
      SET room_types = CASE FLOOR(RAND() * 5)
        WHEN 0 THEN 'Single'
        WHEN 1 THEN 'Double'
        WHEN 2 THEN 'Luxury'
        WHEN 3 THEN 'Delux'
        WHEN 4 THEN 'Family'
        ELSE 'Single' END;
      
      INSERT INTO room (room_number, room_type, floor, status)
      VALUES (j, room_types, i, 'Available');
      
      SET j = j + 1;
    END WHILE;
    SET j = 1;
    SET i = i + 1;
  END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-01 22:17:00
