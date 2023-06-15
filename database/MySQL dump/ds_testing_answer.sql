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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `answerId` int NOT NULL AUTO_INCREMENT,
  `questionId` int NOT NULL,
  `text` varchar(50) NOT NULL,
  `isRight` tinyint DEFAULT NULL,
  PRIMARY KEY (`answerId`),
  UNIQUE KEY `un_idx_qId_text` (`questionId`,`text`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`questionId`) REFERENCES `question` (`questionId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,2,'>',0),(2,2,'<',1),(3,2,'=',0),(4,3,'<',0),(5,3,'>',0),(6,3,'=',1),(7,4,'На 10 качок більше',0),(8,4,'На 3 качки більше',1),(9,4,'На 5 качок більше',0),(10,5,'У павука на 4 ноги більше',0),(11,5,'У павука на 2 ноги більше',1),(12,5,'У павука на 3 ноги більше',0),(13,6,'10',1),(14,6,'7',0),(15,6,'0',0),(16,7,'9',0),(17,7,'4',0),(18,7,'1',1),(19,8,'Шестикутник, трикутник, чотирикутник, п\'ятикутник',0),(20,8,'П\'ятикутник, шестикутник, трикутник, чотирикутник',0),(21,8,'Трикутник, чотирикутник, п\'ятикутник, шестикутник',1),(73,28,'Для редагування багаторядкового тексту',1),(74,28,'Для відображення малюнків',0),(75,28,'Для введення даних',0),(82,32,'int[] array_name;',0),(83,32,'int array_name[10];',1),(84,32,'int array_name = {2, 3, 4};',0),(85,32,'int array_name[ ] = {2, 3, 4}',1),(86,32,'int array_name[3] = {2, 3, 4}',1),(87,33,'for(int i=0; i<=3; i++){//do something}',0),(88,33,'for(int i=0; i<3; i++){//do something}',1),(89,33,'for(int i:arr){//do somethig}',1),(90,33,'for(int i of arr){//do something}',0),(91,34,'2',1),(92,34,'1.2',0),(93,34,'1.25',0),(94,34,'1',1),(95,34,'-5',1),(96,34,'-6.023',0),(97,35,'Цілочислений тип даних',1),(98,35,'Будь-який числовий тип даних',0),(99,35,'Об\'єкт-прокладка',0),(106,34,'3',1),(114,34,'2.5',0),(123,34,'4',1),(133,34,'0.3012',0),(141,39,'Для створення підпису',0),(142,39,'Для створення кнопки',1),(171,43,'=',1),(172,43,'<',0),(173,43,'>',0),(183,9,'три',1),(184,9,'3',1),(185,9,'three',1);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-15 10:07:02
