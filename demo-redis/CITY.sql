/*
Navicat MySQL Data Transfer

Source Server         : 172.16.16.36
Source Server Version : 50627
Source Host           : 172.16.16.36:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2019-04-29 20:00:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for CITY
-- ----------------------------
DROP TABLE IF EXISTS `CITY`;
CREATE TABLE `CITY` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `area_code` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
