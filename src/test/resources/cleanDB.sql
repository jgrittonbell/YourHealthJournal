-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: yourhealthjournal
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `foodentry`
--

DROP TABLE IF EXISTS `foodentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodentry` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `food_name` varchar(255) NOT NULL,
  `time_eaten` datetime NOT NULL,
  `meal_category` varchar(50) DEFAULT 'uncategorized',
  `fat` decimal(5,2) NOT NULL,
  `protein` decimal(5,2) NOT NULL,
  `carbs` decimal(5,2) NOT NULL,
  `calories` decimal(6,2) NOT NULL,
  `cholesterol` decimal(5,2) DEFAULT NULL,
  `sodium` decimal(5,2) DEFAULT NULL,
  `fiber` decimal(5,2) DEFAULT NULL,
  `sugar` decimal(5,2) DEFAULT NULL,
  `added_sugar` decimal(5,2) DEFAULT NULL,
  `vitamin_d` decimal(5,2) DEFAULT NULL,
  `calcium` decimal(5,2) DEFAULT NULL,
  `iron` decimal(5,2) DEFAULT NULL,
  `potassium` decimal(5,2) DEFAULT NULL,
  `notes` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodentry`
--

LOCK TABLES `foodentry` WRITE;
/*!40000 ALTER TABLE `foodentry` DISABLE KEYS */;
INSERT INTO `foodentry` VALUES (1,1,'Grilled Chicken Breast','2024-02-17 12:30:00','lunch',3.50,31.00,0.00,165.00,85.00,75.00,0.00,0.00,NULL,0.00,15.00,0.80,256.00,'Lean protein meal','2025-02-17 21:56:28','2025-02-17 21:56:28'),(2,2,'Oatmeal with Banana','2024-02-17 08:00:00','breakfast',3.00,6.00,27.00,154.00,0.00,2.00,4.00,9.00,NULL,1.00,15.00,1.50,164.00,'Healthy breakfast option','2025-02-17 21:56:28','2025-02-17 21:56:28'),(3,3,'Avocado Toast','2024-02-17 09:15:00','breakfast',14.00,5.00,18.00,200.00,0.00,150.00,7.00,2.00,NULL,0.00,20.00,0.90,300.00,'Good fats and fiber','2025-02-17 21:56:28','2025-02-17 21:56:28'),(4,4,'Cheeseburger','2024-02-16 19:30:00','dinner',18.00,22.00,34.00,450.00,65.00,780.00,3.00,5.00,2.00,0.50,100.00,2.50,250.00,'Cheat meal, high in calories','2025-02-17 21:56:28','2025-02-17 21:56:28'),(5,5,'Greek Yogurt with Honey','2024-02-16 10:00:00','snack',4.00,10.00,18.00,150.00,5.00,55.00,0.00,14.00,10.00,2.00,110.00,0.80,160.00,'Protein-rich snack with probiotics','2025-02-17 21:56:28','2025-02-17 21:56:28'),(6,6,'Caesar Salad with Chicken','2024-02-15 13:00:00','lunch',12.00,30.00,8.00,320.00,70.00,900.00,2.00,3.00,NULL,1.50,110.00,1.00,200.00,'Healthy meal but high sodium','2025-02-17 21:56:28','2025-02-17 21:56:28'),(7,7,'Spaghetti with Marinara Sauce','2024-02-15 18:45:00','dinner',5.00,12.00,50.00,320.00,15.00,600.00,4.00,8.00,NULL,0.00,40.00,3.00,300.00,'Carb-heavy meal with some protein','2025-02-17 21:56:28','2025-02-17 21:56:28'),(8,8,'Apple with Peanut Butter','2024-02-14 15:30:00','snack',9.00,7.00,22.00,210.00,0.00,150.00,4.00,16.00,3.00,0.00,30.00,0.60,250.00,'Balanced snack with healthy fats','2025-02-17 21:56:28','2025-02-17 21:56:28'),(9,9,'Protein Shake','2024-02-14 07:45:00','breakfast',2.00,25.00,8.00,160.00,10.00,180.00,0.00,2.00,1.00,3.00,200.00,2.50,450.00,'Post-workout meal replacement','2025-02-17 21:56:28','2025-02-17 21:56:28'),(10,10,'Salmon with Steamed Broccoli','2024-02-13 19:00:00','dinner',15.00,35.00,10.00,380.00,80.00,400.00,5.00,1.00,NULL,2.00,180.00,1.80,400.00,'High-protein dinner with omega-3','2025-02-17 21:56:28','2025-02-17 21:56:28');
/*!40000 ALTER TABLE `foodentry` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-17 21:59:46
