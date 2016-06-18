-- MySQL dump 10.11
--
-- Host: mudfoot.doc.stu.mmu.ac.uk    Database: brindlej
-- ------------------------------------------------------
-- Server version	5.0.19-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `brindlej`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `brindlej` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `brindlej`;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `BookID` int(10) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Author` varchar(100) NOT NULL,
  `Category` varchar(100) NOT NULL,
  `Price` double(17,2) NOT NULL,
  PRIMARY KEY  (`BookID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Planet Eath: The Photographs','Alastair Fothergill','Photography',8.76),(2,'The Genius of Photography','Gerry Badger','Photography',12.49),(3,'French Dictionary','Collins','Foreign Languages',2.69),(4,'Organic Chemistry','Organic Chemistry','Science',30.39),(22,'The Escape','Charlie Rentwood','Horror',21.47),(210,'Concise Oxford English Dictionary','Oxford','English',15.00),(212,'The Frontier','Edwood Norway','Romance',12.99),(321,'Effective Java','Joshua Bloch','Computing',15.50),(333,'Molecular Biology of the Cell','Bruce Alberts','Biology',13.25),(556,'Java Concurrency In Practice','Brian Goetz','Computing',15.49),(564,'Atkins Physical Chemistry','Peter Atkins and Julio De Paula','Sceince',39.99),(641,'Head First Java (Head First)','Kathy Sierra and Bert Bates','Computing',9.58),(652,'Computer Networks (International Edition)','Andrew S. Tanenbaum','Computing',43.19),(664,' Biology 1 (Cambridge Advanced Sciences)','Mary Jones, Richard Fosbery, and Dennis Taylor','Science',15.50),(726,'Shakespeares Sonnets','William Shakespeare','English',3.80);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `courseID` int(5) NOT NULL auto_increment,
  `CourseName` varchar(40) NOT NULL,
  PRIMARY KEY  (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Bsc(hons) Computer Systems'),(2,'Bsc(hons) Computer Science'),(3,'BEng(hons) English'),(4,'BA(hons) Photography'),(5,'BA(hons) Art'),(6,'Bsc(hons) General Science'),(7,'Bsc(hons) Biology'),(8,'Mchem(hons) Chemistry'),(11,'Bsc(hons) Geography');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favourites`
--

DROP TABLE IF EXISTS `favourites`;
CREATE TABLE `favourites` (
  `favid` int(5) NOT NULL auto_increment,
  `bookid` int(10) NOT NULL,
  `username` varchar(40) NOT NULL,
  PRIMARY KEY  (`favid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `favourites`
--

LOCK TABLES `favourites` WRITE;
/*!40000 ALTER TABLE `favourites` DISABLE KEYS */;
/*!40000 ALTER TABLE `favourites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `OrderNumber` int(5) NOT NULL auto_increment,
  `username` varchar(30) NOT NULL,
  `BookID` int(5) NOT NULL,
  PRIMARY KEY  (`OrderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'jamiebrindle2',1),(2,'jamiebrindle2',4),(6,'jamiebrindle2',3),(11,'jamiebrindle5',24),(12,'jamiebrindle5',4),(13,'pretenduser',22),(15,'pretenduser',4),(16,'pretenduser',22);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reading_list`
--

DROP TABLE IF EXISTS `reading_list`;
CREATE TABLE `reading_list` (
  `Reading_list_ID` int(5) NOT NULL auto_increment,
  `BookID` int(5) NOT NULL,
  `CourseID` int(5) NOT NULL,
  PRIMARY KEY  (`Reading_list_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reading_list`
--

LOCK TABLES `reading_list` WRITE;
/*!40000 ALTER TABLE `reading_list` DISABLE KEYS */;
INSERT INTO `reading_list` VALUES (4,1,4),(5,2,4),(6,4,8),(7,4,6),(9,212,3),(10,321,1),(11,321,2),(12,333,6),(13,333,7),(14,556,1),(15,556,2),(16,564,8),(17,564,6),(18,641,1),(19,641,2),(20,652,2),(21,664,7),(22,664,6),(23,726,3),(24,2,5),(25,1,5);
/*!40000 ALTER TABLE `reading_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers` (
  `Name` varchar(100) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Department` varchar(100) NOT NULL,
  PRIMARY KEY  (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES ('Dr John Smith','drjohnsmith','123','Art'),('Pretend Teacher','pretendteacher','1234','A Pretend Department'),('Dr Robert Robinson','robrob','123','Chemistry and Biology'),('Tester Teacher','testerteacher','123','Test');
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `Name` varchar(50) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Address` varchar(250) default NULL,
  `Postcode` varchar(9) default NULL,
  PRIMARY KEY  (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Jamie Brin','$£E','eraef','32rQ£%Q£Das','sfd£Q%Q£'),('Jamie Brindle','jamiebrindle5','123','99 Marsden Hall Road, North','BB9 8JD'),('Pretend User','pretenduser','1234','An Address','A Post Co');
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

-- Dump completed on 2007-12-18  1:52:48
