-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: ds_testing
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `questionId` int NOT NULL AUTO_INCREMENT,
  `text` varchar(250) NOT NULL,
  `imgSrc` varchar(500) DEFAULT NULL,
  `qTypeId` int NOT NULL,
  `moduleId` int DEFAULT NULL,
  `difficultId` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`questionId`),
  KEY `qTypeId` (`qTypeId`),
  KEY `moduleId` (`moduleId`),
  KEY `q_difficult_idx` (`difficultId`),
  CONSTRAINT `q_difficult` FOREIGN KEY (`difficultId`) REFERENCES `difficult` (`difficultId`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`qTypeId`) REFERENCES `questiontype` (`qTypeId`) ON DELETE CASCADE,
  CONSTRAINT `question_ibfk_2` FOREIGN KEY (`moduleId`) REFERENCES `module` (`moduleId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (2,'Який знак треба поставити замість х: 7-4 х 8+1',NULL,1,4,1),(3,'Який знак треба поставити замість х: 4+3 х 2+5',NULL,1,4,1),(4,'Уважно прочитайте, та дайте відповідь на питання','https://i.ibb.co/6r6yxCC/img1.png',1,5,1),(5,'Уважно прочитайте, та дайте відповідь на питання','https://i.ibb.co/ZNCDPdf/img2.png',1,5,1),(6,'8-3+5=',NULL,1,6,1),(7,'4+5-8=',NULL,1,6,1),(8,'Обери ту відповідь, у якій назви многокутників записані у правильному порядку','https://i.ibb.co/9GvncZP/img3.png',1,7,1),(9,'Скільки вершин має фігура','https://i.ibb.co/58k25Sb/img4.png',3,7,1),(28,'Для чого призначений елемент Memo','',1,38,1),(32,'Як створюється масив у мові програмування С++','',2,39,1),(33,'Як пройти по всім елементам масиву int arr[]={1,2,3}; ','',2,39,2),(34,'Які з цих чисел належать то типу int','',2,40,2),(35,'Що означає тип даних int','',1,40,2),(39,'Для чого призначений елемент Button','',1,38,1),(43,'5+5 х 7+3','',1,4,1);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-15 10:07:03
