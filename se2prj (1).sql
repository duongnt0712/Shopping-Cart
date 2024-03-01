-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: se2project
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (4),(4);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `main_category`
--

DROP TABLE IF EXISTS `main_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `main_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `main_category`
--

LOCK TABLES `main_category` WRITE;
/*!40000 ALTER TABLE `main_category` DISABLE KEYS */;
INSERT INTO `main_category` VALUES (5,'SHIRTS'),(6,'PANTS'),(7,'SHOES'),(8,'OTHERS');
/*!40000 ALTER TABLE `main_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `sub_category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKd9gfnsjgfwjtaxl57udrbocsp` (`sub_category_id`),
  CONSTRAINT `FKd9gfnsjgfwjtaxl57udrbocsp` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Deep Gray Silk Twill','The short-sleeved shirt reframes House elements with a modern lens. Crafted in deep gray silk twill, it reveals an all-over CD Diamond print.','dior.png',43349000,'CD Diamond Short-Sleeved Shirt',9),(2,'White','Classic outerwear pieces continue to be reimagined in creative ways through emblematic House symbols.','gucci.png',105682500,'Wool coat with pleats',11),(3,'Black','Created in partnership with senior instructor Alex Toussaint, this training collection showcases performance-ready pieces for on','puma1.jpg',1000000,'PUMA x ALEX TOUSSAINT Men\'s Performance Tank Top',12),(4,'Mauve and Sea Green Technical Silk Canvas','The blouson is part of the exclusive DIOR BY ERL collaboration.','dior2.png',93000000,'DIOR By Erl Blouson',9),(5,'Peach Pink','Pump up the cool with this stylish tee.Made from 100% cotton, with standout PUMA graphics, this tee is made for those who play...','puma2.png',4000000,'PUMA Cat Segment Women\'s Tee',12),(6,'Black','The sole is designed to be enlarged with large wavy serrations for better shape and balance','converse.png',2500000,'Run Star Motion high-collar unisex sneakers',16),(7,'Cloud White','Made with a series of recycled materials, this upper features at least 50% recycled content.','adidas.jpg',5000000,'X_PLRBOOST SHOES',15),(8,'Flat Pewter','Lace up and feel the legacy. Produced at the intersection of art, music and culture, this champion running shoe helped define the \'90s.','nike1.jpg',3900000,'Nike Air Max 90',14),(9,'Black','The premium Chuck 70 crafts its latest look in a design that keeps the planet in mind. ','converse4.png',1300000,'Unisex Converse Chuck 70 Mixed Textiles High Top Natural',16),(10,'Mix','Made with a series of recycled materials, and at least 60% recycled content, this product represents just one of our solutions to help end plastic waste.','adidas2.png',2800000,'TAILORED HIIT LUXE 45 SECONDS TRAINING 7/8 LEGGINGS',17),(11,'White','The Classics are PUMA at its pared back core, and these shorts, with a large graphic, are no exception.','puma4.png',27000000,'Classics GEN PUMA 8\" Men\'s Shorts',18),(12,'Birch Tree','Focus on your performance in these PUMA Fit training shorts.','puma3.png',25000000,'PUMA Fit Ultrabreathe Men\'s Training Shorts',18),(13,'Wonder Taupe ','Comfort and support through every move. That\'s exactly what the adidas Techfit 7/8 Tights offer.','adidas1.png',28400000,'TECHFIT 7/8 LEGGINGS',17),(14,'Chalk White','Light and airy women\'s shorts make lounging easy.','champion1.png',40000000,'FLEECE SHORTS, BUTTERFLY LOGO, 4\"',19),(15,'Mix','The Gucci bee, a historical symbol from the \'70s archives, is combined with a pink lacquered dial and steel bracelet.','gucci1.png',3300000,'G-Timeless watch with bees, 32 mm',20),(16,'Pink Oxford','With its easygoing lines, heritage athletics look and, of course, visible Nike Air cushioning, the Air Max SC SE is the perfect finish to any outfit.','nike.jpg',5000000,'Nike Air Max SC SE',21),(17,'Camo','Converse Striped Transition Backpack is designed from soft, thick Polyester (Recycled) material with good water resistance.','converse3.png',1400000,'Converse Striped Transition Backpack',22),(18,'Black','Sports style crossbody bag, fashionable rectangular shape','converse2.png',700000,'Comms Pouch 2.0 unisex crossbody bag',22),(19,'White Gold','Quilted motif, 18K white gold, diamonds','channel2.jpg',140000000,'COCO NECKLACEGE',24);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_category`
--

DROP TABLE IF EXISTS `sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `main_category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK753xnmtixdl7hf7c7h3hsjuam` (`main_category_id`),
  CONSTRAINT `FK753xnmtixdl7hf7c7h3hsjuam` FOREIGN KEY (`main_category_id`) REFERENCES `main_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category`
--

LOCK TABLES `sub_category` WRITE;
/*!40000 ALTER TABLE `sub_category` DISABLE KEYS */;
INSERT INTO `sub_category` VALUES (9,'Christian Dior',5),(10,'Nike',6),(11,'Gucci',5),(12,'Puma',5),(13,'Champion',5),(14,'Nike',7),(15,'Adidas',7),(16,'Converse',7),(17,'Adidas',6),(18,'Puma',6),(19,'Champion',6),(20,'Gucci',8),(21,'Nike',8),(22,'Converse',8),(23,'Louis Vuitton',8),(24,'Chanel',8);
/*!40000 ALTER TABLE `sub_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`),
  KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(1,2),(3,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@gmail.com','Admin','Admin','$2a$10$ItGPxwbz4mKW3WUIYJ1yXe.brqy6UCiQiXWVfm70jtAX/Lr4xW2Yu'),(2,'user@gmail.com','User','User','$2a$10$ItGPxwbz4mKW3WUIYJ1yXe.brqy6UCiQiXWVfm70jtAX/Lr4xW2Yu'),(3,'asdf@gmail.com','He','ho','$2a$10$.9ANaxUaaAi/SCG6E9GSUOk4144nTJMmLUrPV0bqAzfkUmYHGgMS2');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-29 13:31:17
