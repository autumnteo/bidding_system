-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 14, 2021 at 03:30 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bidapplication`
--
CREATE DATABASE IF NOT EXISTS `bidapplication` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `bidapplication`;

-- --------------------------------------------------------

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
CREATE TABLE IF NOT EXISTS `bid` (
  `bidId` bigint(20) NOT NULL AUTO_INCREMENT,
  `bidPrice` double NOT NULL,
  `bidStatus` varchar(255) DEFAULT NULL,
  `bidSubmissionDate` varchar(255) DEFAULT NULL,
  `request` varchar(255) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `requestId` varchar(255) DEFAULT NULL,
  `resourceId` varchar(255) DEFAULT NULL,
  `resourceAvailable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bidId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `confirmationtoken`
--

DROP TABLE IF EXISTS `confirmationtoken`;
CREATE TABLE IF NOT EXISTS `confirmationtoken` (
  `tokenId` int(11) NOT NULL AUTO_INCREMENT,
  `confirmationToken` varchar(255) NOT NULL,
  `createdDate` date NOT NULL,
  `userId` int(11) NOT NULL,
  `tokenType` varchar(2) NOT NULL,
  PRIMARY KEY (`tokenId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `jobNumber` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `jobStatus` varchar(20) DEFAULT 'OPEN',
  `jobCreationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `jobCompleteTime` time DEFAULT NULL,
  `requestNumber` int(11) NOT NULL,
  `requestorUserId` int(11) NOT NULL,
  `bidId` int(11) NOT NULL,
  `partnerUserId` int(11) NOT NULL,
  PRIMARY KEY (`jobNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
CREATE TABLE IF NOT EXISTS `request` (
  `requestNumber` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT 'Request Number',
  `requestStatus` varchar(20) DEFAULT 'Draft',
  `requestSubmissionTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Request Submitted Time',
  `requestLastEditTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last Edit Time',
  `requesterId` int(12) DEFAULT NULL,
  `equipmentCategory` varchar(255) DEFAULT NULL,
  `equipmentLength` double DEFAULT NULL,
  `equipmentWidth` double DEFAULT NULL,
  `equipmentHeight` double DEFAULT NULL,
  `equipmentQuantity` int(11) DEFAULT NULL,
  `typeOfTransporting` varchar(20) DEFAULT NULL,
  `capacityOfTransport` double DEFAULT NULL,
  `rentalByTrip` tinyint(1) DEFAULT NULL,
  `rentalDuration` double DEFAULT NULL,
  `startLocationPostalCode` int(6) DEFAULT NULL,
  `endLocationPostalCode` int(6) DEFAULT NULL,
  `routeDistance` float DEFAULT NULL,
  `startDatetime` datetime(6) DEFAULT NULL,
  `endDatetime` datetime(6) DEFAULT NULL,
  `specialRequest` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`requestNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `resourcetimetable`
--

DROP TABLE IF EXISTS `resourcetimetable`;
CREATE TABLE IF NOT EXISTS `resourcetimetable` (
  `resourceId` bigint(20) NOT NULL,
  `bidId` bigint(20) NOT NULL,
  `requestNumber` bigint(20) NOT NULL,
  `startTime` varchar(255) DEFAULT NULL,
  `endTime` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT 'blocked',
  `resourceOwner` int(11) NOT NULL,
  `resourceRenter` int(11) NOT NULL,
  PRIMARY KEY (`resourceId`,`bidId`,`requestNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `resourceunit`
--

DROP TABLE IF EXISTS `resourceunit`;
CREATE TABLE IF NOT EXISTS `resourceunit` (
  `resourceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `equipmentName` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `manufactureYear` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `weight` double DEFAULT '0',
  `height` double DEFAULT '0',
  `width` double DEFAULT '0',
  `length` int(11) DEFAULT '0',
  `payload` double DEFAULT '0',
  `centrifugalForce` double DEFAULT '0',
  `workingCapacity` int(11) DEFAULT '0',
  `priceDaily` int(11) DEFAULT '0',
  `priceWeekly` int(11) DEFAULT '0',
  `priceMonthly` int(11) DEFAULT '0',
  `pricePerTrip` double DEFAULT '0',
  `priceperKm` double DEFAULT '0',
  `extraPricePerHour` double DEFAULT '0',
  `fullDayPrice` double DEFAULT '0',
  `weekendRatePerHour` double DEFAULT '0',
  `extraCost` double DEFAULT '0',
  PRIMARY KEY (`resourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `email` text NOT NULL,
  `password` varchar(200) NOT NULL,
  `fullName` text NOT NULL,
  `companyName` text NOT NULL,
  `position` text NOT NULL,
  `uen` varchar(100) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `isEnabled` tinyint(4) NOT NULL,
  `userType` varchar(200) NOT NULL,
  `username` varchar(500) NOT NULL,
  `isRequestingPartner` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `email`, `password`, `fullName`, `companyName`, `position`, `uen`, `phoneNumber`, `address`, `isEnabled`, `userType`, `username`, `isRequestingPartner`) VALUES
(1, 'antbuildz.noreply@gmail.com', '$2a$10$KK4P6BDP6QjW4MfCu6HPJOUePRTjUJxNzjYfISE/kh6KjsqNOfR0G', 'AntBuildz Admin', 'AntBuildz', 'ADMIN', 'NA', 'NA', 'NA', 1, 'ROLE_USER,ROLE_ADMIN', 'admin', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
