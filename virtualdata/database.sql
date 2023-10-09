/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - virtual doctor with smart pharmacy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`virtual doctor with smart pharmacy` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `virtual doctor with smart pharmacy`;

/*Table structure for table `booking` */

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `booking_id` int(100) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(100) NOT NULL,
  `user_id` int(100) NOT NULL,
  `date` varchar(80) NOT NULL,
  `booking_status` varchar(100) NOT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `booking` */

insert  into `booking`(`booking_id`,`doctor_id`,`user_id`,`date`,`booking_status`) values 
(1,40,20,'12-2-2021','accept'),
(2,39,20,'13-2-2001','accept'),
(3,33,20,'12/12/2021','reject');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `Complaint_id` int(100) NOT NULL AUTO_INCREMENT,
  `login_id` int(100) NOT NULL,
  `Complaint` varchar(200) NOT NULL,
  `date` date NOT NULL,
  `Reply` varchar(200) NOT NULL,
  PRIMARY KEY (`Complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`Complaint_id`,`login_id`,`Complaint`,`date`,`Reply`) values 
(1,20,'torchuring','0000-00-00','pending'),
(2,32,'easzrftgsfg','2022-01-07','pending'),
(3,32,'gbgbgnhgjjngfb','2022-01-07','pending');

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `Department_id` int(100) NOT NULL AUTO_INCREMENT,
  `login_id` int(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Description` varchar(100) NOT NULL,
  PRIMARY KEY (`Department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `department` */

insert  into `department`(`Department_id`,`login_id`,`Name`,`Description`) values 
(5,22,'bilal','ariyallur'),
(6,22,'baby','gzs'),
(7,22,'fdzgfs','fsrsuy');

/*Table structure for table `disease` */

DROP TABLE IF EXISTS `disease`;

CREATE TABLE `disease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disease_name` varchar(40) DEFAULT NULL,
  `discription` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `disease` */

/*Table structure for table `doctor` */

DROP TABLE IF EXISTS `doctor`;

CREATE TABLE `doctor` (
  `Doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `Login_id` int(100) NOT NULL,
  `Fname` varchar(100) NOT NULL,
  `Lname` varchar(100) NOT NULL,
  `Gender` varchar(20) NOT NULL,
  `DOB` varchar(20) NOT NULL,
  `Qualification` varchar(100) NOT NULL,
  `Specification` varchar(100) NOT NULL,
  `Place` varchar(100) NOT NULL,
  `Post` varchar(100) NOT NULL,
  `Pin` int(10) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone` bigint(20) NOT NULL,
  `Hid` int(11) DEFAULT NULL,
  PRIMARY KEY (`Doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `doctor` */

insert  into `doctor`(`Doctor_id`,`Login_id`,`Fname`,`Lname`,`Gender`,`DOB`,`Qualification`,`Specification`,`Place`,`Post`,`Pin`,`Email`,`Phone`,`Hid`) values 
(9,32,'niya sam','niya sam','male','female','mbbs md','nuerology','kalpetta','673122',356677,'9344343951',9344343951,22),
(10,38,'niya','sam','6/06/2001','female','mbbs','nuerology','kalpetta','673122',0,'9344343951',9344343951,22),
(11,39,'niya','sam','6/06/2001','female','mbbs','nuerology','kalpetta','673122',0,'9344343951',9344343951,22),
(12,40,'niya','sam','6/06/2001','female','mbbs','nuerology','kalpetta','673122',0,'9344343951',9344343951,22),
(13,41,'niya','sam','6/06/2001','female','mbbs','nuerology','kalpetta','673122',0,'9344343951',9344343951,22),
(14,42,'manu','sam','6/06/2001','female','mbbs','skin','kalpetta','673122',0,'9344343951',9344343951,22);

/*Table structure for table `facility` */

DROP TABLE IF EXISTS `facility`;

CREATE TABLE `facility` (
  `Facility_id` int(100) NOT NULL AUTO_INCREMENT,
  `Login_id` int(100) NOT NULL,
  `Facility` varchar(100) NOT NULL,
  `Description` varchar(100) NOT NULL,
  PRIMARY KEY (`Facility_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `facility` */

insert  into `facility`(`Facility_id`,`Login_id`,`Facility`,`Description`) values 
(1,22,'',''),
(2,22,'ariyallur','bilal'),
(3,22,'ariyallur','bilal'),
(4,22,'ariyallur','bilal'),
(5,22,'ariyallur','bilal'),
(6,22,'ariyallur','anu'),
(7,22,'ariyallur','bilal'),
(8,22,'ariyallur','bilal'),
(9,22,'',''),
(10,22,'',''),
(11,22,'',''),
(12,22,'',''),
(13,22,'',''),
(14,22,'ariyallur','bilal');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `Feedback_id` int(100) NOT NULL AUTO_INCREMENT,
  `Login_id` varchar(100) NOT NULL,
  `Feedback` varchar(100) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`Feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`Feedback_id`,`Login_id`,`Feedback`,`Date`) values 
(1,'20','bad','1970-01-01'),
(2,'32','sjsdhdhjd','2022-01-07');

/*Table structure for table `hospital` */

DROP TABLE IF EXISTS `hospital`;

CREATE TABLE `hospital` (
  `Hospital_id` int(100) NOT NULL AUTO_INCREMENT,
  `Login_id` int(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Place` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone` bigint(100) NOT NULL,
  `Post` varchar(100) NOT NULL,
  `Pin` int(100) NOT NULL,
  PRIMARY KEY (`Hospital_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `hospital` */

insert  into `hospital`(`Hospital_id`,`Login_id`,`Name`,`Place`,`Email`,`Phone`,`Post`,`Pin`) values 
(2,4,'vinaya','kuthiravattam','kuthiravattam',673122,'',0),
(3,5,'vinaya','kuthiravattam','kuthiravattam',673122,'',0),
(4,6,'vinaya','kuthiravattam','kuthiravattam',673122,'',0),
(5,7,'vinaya','kuthiravattam','kuthiravattam',673122,'',0),
(6,8,'vinaya','kuthiravattam','kuthiravattam',673122,'',0),
(11,22,'baby','clt','babymemorial@gmail.com',9876543211,'clt',678954);

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `location_id` int(100) NOT NULL AUTO_INCREMENT,
  `Pharmacy_id` int(100) NOT NULL,
  `latitude` int(100) NOT NULL,
  `Longitude` int(100) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `location` */

insert  into `location`(`location_id`,`Pharmacy_id`,`latitude`,`Longitude`) values 
(1,50,12,34);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `usertype` varchar(50) NOT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'ammu123@gmail.com','8234564667','hospital'),
(3,'ammu123@gmail.com','8234564667','hospital'),
(4,'ammu123@gmail.com','8234564667','hospital'),
(5,'ammu123@gmail.com','8234564667','hospital'),
(6,'ammu123@gmail.com','8234564667','hospital'),
(7,'ammu123@gmail.com','8234564667','hospital'),
(8,'ammu123@gmail.com','8234564667','hospital'),
(9,'ammu123@gmail.com','8234564667','hospital'),
(10,'','','hospital'),
(11,'thavala12@gamil.com','8848387238','hospital'),
(12,'admin','sru123','pending'),
(13,'admin','sru123','pending'),
(14,'','','pending'),
(15,'','','pending'),
(16,'3','4','pending'),
(17,'3','4','pending'),
(18,'3','4','pharmacy'),
(19,'3','4','reject'),
(20,'sruthi','sru444','user'),
(21,'blossom123','fgdfg','hospital'),
(22,'baby','baby','hospital'),
(23,'admin','admin','doctor'),
(24,'admin','admin','doctor'),
(25,'baby','baby','doctor'),
(26,'admin','admin','doctor'),
(27,'admin','admin','doctor'),
(28,'admin','admin','doctor'),
(29,'baby','baby','doctor'),
(30,'baby','baby','doctor'),
(32,'niya','niya','doctor'),
(33,'niya','niya','doctor'),
(37,'baby','baby','doctor'),
(38,'baby','baby','doctor'),
(39,'baby','baby','doctor'),
(40,'baby','baby','doctor'),
(41,'baby','baby','doctor'),
(42,'baby','baby','doctor'),
(43,'vinaya','vinaya','pharmacy'),
(44,'','','pending'),
(45,'3','4','pending'),
(46,'3','4','pending'),
(47,'9','10','pending'),
(48,'3','4','pending'),
(49,'13','24','pending'),
(50,'ria','ria','pharmacy');

/*Table structure for table `medicine` */

DROP TABLE IF EXISTS `medicine`;

CREATE TABLE `medicine` (
  `medicine_id` int(100) NOT NULL AUTO_INCREMENT,
  `medicine_name` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `price` int(100) NOT NULL,
  `expiry_date` varchar(50) NOT NULL,
  `dosage` varchar(100) NOT NULL,
  `ph_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `medicine` */

insert  into `medicine`(`medicine_id`,`medicine_name`,`description`,`price`,`expiry_date`,`dosage`,`ph_id`) values 
(1,'freerf','sxfu',45778,'43009-02-05','c ggggg',50);

/*Table structure for table `notifiication` */

DROP TABLE IF EXISTS `notifiication`;

CREATE TABLE `notifiication` (
  `Notification_id` int(100) NOT NULL AUTO_INCREMENT,
  `Notification` varchar(100) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`Notification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `notifiication` */

insert  into `notifiication`(`Notification_id`,`Notification`,`Date`) values 
(3,'niya','2022-01-04'),
(4,'awfae','2022-01-05');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `Order_id` int(11) NOT NULL AUTO_INCREMENT,
  `User_id` int(11) DEFAULT NULL,
  `Med_id` int(11) DEFAULT NULL,
  `Date` varchar(45) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `orders` */

/*Table structure for table `pharmacy` */

DROP TABLE IF EXISTS `pharmacy`;

CREATE TABLE `pharmacy` (
  `Phartmacy_id` int(100) NOT NULL AUTO_INCREMENT,
  `Login_id` int(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Place` varchar(100) NOT NULL,
  `Post` varchar(100) NOT NULL,
  `Pin` int(10) NOT NULL,
  `E-mail` varchar(100) NOT NULL,
  `Phone` bigint(20) NOT NULL,
  `Lat` varchar(50) NOT NULL,
  `Lon` varchar(50) NOT NULL,
  PRIMARY KEY (`Phartmacy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `pharmacy` */

insert  into `pharmacy`(`Phartmacy_id`,`Login_id`,`Name`,`Place`,`Post`,`Pin`,`E-mail`,`Phone`,`Lat`,`Lon`) values 
(1,18,'thavala','oolampara','kuthiravattam',320721,'thavala12@gamil.com',8848387238,'aaaa','1111'),
(2,19,'nenju','anappara','kuthiravattm',673642,'nenju34@gmail.com',9876543210,'admin','admin'),
(3,44,'','','',0,'',0,'',''),
(4,45,'anurenj','vazhavatta','vazhavatta',673875,'anurenj@123',9064668833,'pharmacy','pharmacy'),
(5,46,'messi','ooty','pattanam',673596,'messiduck@gmail.com',8848487363,'pharmacy','pharmacy'),
(6,47,'unnikuttan ','koliyadi','madakkara',672133,'unnimon@gmail.com',987779999,'pharmacy','pharmacy'),
(7,48,'shibu','kurukkanmoola','pannimunda',9888898,'shibu@gmail.com',9876543210,'pharmacy','pharmacy'),
(8,49,'seva','calicut','calicut',899,'seva@gmail.com',987664534,'seva','seva'),
(9,50,'ria','kalpetta','kalpetta',673642,'ria@gmail.com',6238554079,'3','4');

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `Staff_id` int(100) NOT NULL AUTO_INCREMENT,
  `Login_id` int(100) NOT NULL,
  `Fname` varchar(100) NOT NULL,
  `Lname` varchar(100) NOT NULL,
  `Gender` char(10) NOT NULL,
  `DOB` int(10) NOT NULL,
  `Qualification` varchar(100) NOT NULL,
  `Place` varchar(100) NOT NULL,
  `Post` varchar(100) NOT NULL,
  `Pin` int(10) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone` int(100) NOT NULL,
  PRIMARY KEY (`Staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `staff` */

/*Table structure for table `symptoms` */

DROP TABLE IF EXISTS `symptoms`;

CREATE TABLE `symptoms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Did` int(11) DEFAULT NULL,
  `Synptoms` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `symptoms` */

/*Table structure for table `user register` */

DROP TABLE IF EXISTS `user register`;

CREATE TABLE `user register` (
  `User_id` int(100) NOT NULL AUTO_INCREMENT,
  `Login_id` int(100) NOT NULL,
  `Fname` varchar(100) NOT NULL,
  `Lname` varchar(100) NOT NULL,
  `Gender` char(10) NOT NULL,
  `DOB` int(10) NOT NULL,
  `Place` varchar(100) NOT NULL,
  `Post` varchar(100) NOT NULL,
  `Pin` int(10) NOT NULL,
  `Phone` bigint(100) NOT NULL,
  PRIMARY KEY (`User_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `user register` */

insert  into `user register`(`User_id`,`Login_id`,`Fname`,`Lname`,`Gender`,`DOB`,`Place`,`Post`,`Pin`,`Phone`) values 
(1,20,'achu','pp','female',14,'ooty','nilgiri',673122,9456789);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
