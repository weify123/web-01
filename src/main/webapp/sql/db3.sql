/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : db3

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2016-11-09 14:32:36
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