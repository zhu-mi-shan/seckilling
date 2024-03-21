-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主机： db
-- 生成日期： 2023-10-28 23:57:35
-- 服务器版本： 8.0.27
-- PHP 版本： 7.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rbac
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- 数据库： `ssadmin`
--
-- 创建新数据库 ssadmin 并选择
CREATE DATABASE IF NOT EXISTS `ssadmin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `ssadmin`;

-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rbac
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `menu_id` int NOT NULL,
  `menu_parent_id` int NOT NULL,
  `path` varchar(50) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `component` varchar(50) NOT NULL,
  `icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,0,'/rbac/user','用户管理','Layout','el-icon-user'),(2,0,'/rbac/role','角色管理','Layout','el-icon-s-custom'),(3,0,'/rbac/permission','权限管理','Layout','el-icon-s-grid'),(4,0,'/seckill/ticket','活动管理','Layout','el-icon-s-ticket'),(11,1,'list','用户列表','User/List','el-icon-tickets'),(12,1,'add','添加用户','User/Add','el-icon-plus'),(21,2,'list','角色列表','Role/List','el-icon-tickets'),(22,2,'add','添加角色','Role/Add','el-icon-plus'),(31,3,'list','权限列表','Permission/List','el-icon-tickets'),(41,4,'list','活动列表','Ticket/List','el-icon-tickets'),(42,4,'add','添加活动','Ticket/Add','el-icon-plus'),(43,4,'admin','售票管理','Ticket/Admin','el-icon-setting');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `ticket_id` int NOT NULL,
  `order_status` int NOT NULL,
  `username` varchar(150) NOT NULL,
  `ticket_name` varchar(150) NOT NULL,
  `order_time` timestamp NOT NULL,
  `order_price` int NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `orders_ticket_ticket_id_fk` (`ticket_id`),
  KEY `orders_user_userid_fk` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=375 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (365,1,24,-3,'www','awd','2023-12-27 10:42:48',12),(366,1,19,-1,'www','党支部讲党课','2023-12-27 10:46:28',0),(367,1,24,-3,'www','awd','2023-12-27 11:49:01',12),(368,31,24,-3,'asd','awd','2023-12-27 12:13:54',12),(369,1,25,-3,'www','测试','2024-01-15 14:50:30',12),(370,1,25,-3,'www','测试','2024-01-15 14:56:18',12),(371,1,25,-3,'www','测试','2024-01-15 14:56:24',12),(372,1,26,-3,'www','testt','2024-01-15 15:07:41',23),(373,1,26,-3,'www','testt','2024-01-15 15:07:45',23),(374,33,26,-3,'jkl','testt','2024-01-15 15:08:06',23);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `per_id` int NOT NULL AUTO_INCREMENT,
  `per_name` varchar(100) NOT NULL,
  `per_des` text,
  `per_menu` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`per_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'访问主页','访问主页面',NULL),(2,'查看用户','查看用户列表','11'),(3,'查看角色','查看角色列表','21'),(4,'查看权限','查看权限列表','31'),(5,'编辑用户','编辑用户列表','12'),(6,'编辑角色','编辑角色列表','22'),(10,'查看活动','查看活动以及抢票','41'),(11,'管理活动','编辑活动','42'),(12,'活动数据','查看活动数据','43');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  `role_des` text,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'管理员','(默认)管理用户们'),(2,'普通用户','(默认)普通的用户'),(3,'VIP用户','(默认)高级用户VIP'),(6,'最高管理员','(默认)最高级的管理员'),(9,'test','test');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role_id` int DEFAULT NULL,
  `per_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (9,1),(9,4),(9,2),(6,1),(6,2),(6,3),(6,4),(6,5),(6,6),(6,10),(6,11),(6,12),(2,1),(2,10),(3,1),(3,2),(3,3),(3,10),(1,2),(1,3),(1,1),(1,5),(1,10),(1,11),(1,12);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `ticket_name` varchar(150) NOT NULL,
  `ticket_cnt` int NOT NULL,
  `ticket_price` int NOT NULL,
  `ticket_start_time` timestamp NOT NULL,
  `ticket_end_time` timestamp NOT NULL,
  `ticket_des` text NOT NULL,
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (19,'党支部讲党课',788,0,'2023-12-25 16:00:00','2023-12-30 16:00:00','党员、预备党员和入党积极分子参与'),(20,'桌游活动',12,50,'2023-12-14 16:00:00','2023-12-19 16:00:00','去桌游店玩桌游'),(21,'动物园游览',20,80,'2023-12-21 16:00:00','2023-12-25 16:00:00','去北京动物园玩'),(22,'歌剧鉴赏',0,100,'2023-12-22 16:00:00','2023-12-28 16:00:00','去欣赏歌剧'),(24,'awd',9,12,'2023-12-26 16:00:00','2023-12-29 16:00:00','add'),(25,'测试',120,12,'2024-01-13 16:00:00','2024-01-19 16:00:00','123');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `user_pwd` varchar(100) NOT NULL,
  `user_realname` varchar(100) NOT NULL,
  `user_phone_number` varchar(30) DEFAULT NULL,
  `user_sex` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'www','123','小王','13212345678','男'),(2,'ddd','1234','小丁','13545488978','女'),(24,'zzz','123','啧啧啧','1654165488','女'),(31,'asd','123','阿斯顿','123','男'),(32,'fff','123','烦烦烦','213','男'),(33,'jkl','123','解开了','12312312323','男');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `userid` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `User_Role_fk1` (`role_id`),
  KEY `User_Role_fk2` (`userid`),
  CONSTRAINT `User_Role_fk1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `User_Role_fk2` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (24,2),(1,3),(1,6),(1,1),(1,2),(2,2),(2,9),(32,2),(32,1),(31,2),(31,1),(33,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-15 23:30:51