/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : db2

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2016-11-09 14:32:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `company_id` varchar(64) NOT NULL DEFAULT 'company',
  `company_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', 'hp');

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` varchar(64) NOT NULL,
  `customer_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for `customer_addr`
-- ----------------------------
DROP TABLE IF EXISTS `customer_addr`;
CREATE TABLE `customer_addr` (
  `customer_addr` varchar(64) NOT NULL,
  `customer_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`customer_addr`),
  KEY `custom` (`customer_id`),
  CONSTRAINT `custom` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_addr
-- ----------------------------

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employee_id` varchar(64) NOT NULL,
  `employee_name` varchar(64) DEFAULT NULL,
  `employee_address` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `good_id` varchar(64) NOT NULL,
  `good_name` varchar(64) DEFAULT NULL,
  `good_price` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`good_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------

-- ----------------------------
-- Table structure for `hotnews`
-- ----------------------------
DROP TABLE IF EXISTS `hotnews`;
CREATE TABLE `hotnews` (
  `hotnews_id` varchar(64) NOT NULL,
  `hotnews_an` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`hotnews_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotnews
-- ----------------------------
INSERT INTO `hotnews` VALUES ('1', 'hp');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `orders_id` varchar(64) NOT NULL,
  `customer_id` varchar(64) NOT NULL,
  PRIMARY KEY (`orders_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for `order_items`
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items` (
  `order_items_id` varchar(64) NOT NULL,
  `order_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`order_items_id`),
  KEY `order` (`order_id`),
  CONSTRAINT `order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`orders_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_items
-- ----------------------------

-- ----------------------------
-- Table structure for `travelrecord`
-- ----------------------------
DROP TABLE IF EXISTS `travelrecord`;
CREATE TABLE `travelrecord` (
  `travelrecord_id` varchar(255) NOT NULL,
  `travelrecord_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`travelrecord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of travelrecord
-- ----------------------------