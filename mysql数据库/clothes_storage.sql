/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : clothes_storage

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2019-04-25 18:44:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(60) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `address` varchar(90) DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('1', '刘萌萌', '13948324394', '四川成都红星路13号', '美女客户');

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `departmentNo` varchar(20) NOT NULL,
  `departmentName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('BM001', '市场部');

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employeeNo` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `departmentObj` varchar(20) DEFAULT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `employeeDesc` longtext,
  PRIMARY KEY (`employeeNo`),
  KEY `FK1A9BE39F97E0739` (`departmentObj`),
  CONSTRAINT `FK1A9BE39F97E0739` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('EM001', '123', '双鱼林', '男', 'BM001', '2019-04-25', '15394920843', '不错');
INSERT INTO `t_employee` VALUES ('EM002', '123', '刘雅', '女', 'BM001', '2019-04-25', '13487493742', '新来的');

-- ----------------------------
-- Table structure for `t_exchange`
-- ----------------------------
DROP TABLE IF EXISTS `t_exchange`;
CREATE TABLE `t_exchange` (
  `exchangeId` int(11) NOT NULL AUTO_INCREMENT,
  `productObj` varchar(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `customerObj` int(11) DEFAULT NULL,
  `exchangeDate` varchar(20) DEFAULT NULL,
  `handleRes` longtext,
  `employeeObj` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`exchangeId`),
  KEY `FK312065AEA2CBF239` (`employeeObj`),
  KEY `FK312065AEDFAF2439` (`customerObj`),
  KEY `FK312065AE1D6F5E55` (`productObj`),
  CONSTRAINT `FK312065AE1D6F5E55` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productNo`),
  CONSTRAINT `FK312065AEA2CBF239` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`),
  CONSTRAINT `FK312065AEDFAF2439` FOREIGN KEY (`customerObj`) REFERENCES `t_customer` (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_exchange
-- ----------------------------
INSERT INTO `t_exchange` VALUES ('1', 'SP001', '1', '1', '2019-04-25', '已经处理完毕', 'EM001');
INSERT INTO `t_exchange` VALUES ('2', 'SP001', '1', '1', '2019-04-25', '因为尺寸不对已换', 'EM001');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(80) DEFAULT NULL,
  `content` longtext,
  `noticeDate` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '超市系统上线', '超市系统上线', '2019-04-25');

-- ----------------------------
-- Table structure for `t_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `productNo` varchar(20) NOT NULL,
  `productClassObj` int(11) DEFAULT NULL,
  `productName` varchar(50) DEFAULT NULL,
  `productPhoto` varchar(50) DEFAULT NULL,
  `productDesc` varchar(2000) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `stockCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`productNo`),
  KEY `FKC58580416092339` (`productClassObj`),
  CONSTRAINT `FKC58580416092339` FOREIGN KEY (`productClassObj`) REFERENCES `t_productclass` (`productClassId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('SP001', '1', '烟酒1', 'upload/f244ea17-1491-443c-8aaa-f3d4679d2f7c.jpg', '发发发', '263.5', '153');
INSERT INTO `t_product` VALUES ('SP002', '2', '食品1', 'upload/42addc06-80d8-460a-9e45-8190752e3cdc.jpg', '食品有很多哦', '70', '10');

-- ----------------------------
-- Table structure for `t_productclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_productclass`;
CREATE TABLE `t_productclass` (
  `productClassId` int(11) NOT NULL AUTO_INCREMENT,
  `productClassName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`productClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_productclass
-- ----------------------------
INSERT INTO `t_productclass` VALUES ('1', '烟酒类');
INSERT INTO `t_productclass` VALUES ('2', '食品类');

-- ----------------------------
-- Table structure for `t_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `t_purchase`;
CREATE TABLE `t_purchase` (
  `purchaseId` int(11) NOT NULL AUTO_INCREMENT,
  `productObj` varchar(20) DEFAULT NULL,
  `supplierObj` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `purchaseDate` varchar(20) DEFAULT NULL,
  `memo` varchar(60) DEFAULT NULL,
  `employeeObj` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`purchaseId`),
  KEY `FK226FDC8CA2CBF239` (`employeeObj`),
  KEY `FK226FDC8CA4DCB579` (`supplierObj`),
  KEY `FK226FDC8C1D6F5E55` (`productObj`),
  CONSTRAINT `FK226FDC8C1D6F5E55` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productNo`),
  CONSTRAINT `FK226FDC8CA2CBF239` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`),
  CONSTRAINT `FK226FDC8CA4DCB579` FOREIGN KEY (`supplierObj`) REFERENCES `t_supplier` (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_purchase
-- ----------------------------
INSERT INTO `t_purchase` VALUES ('1', 'SP001', '1', '60', '20', '2019-04-25', '测试', 'EM001');
INSERT INTO `t_purchase` VALUES ('2', 'SP002', '1', '28', '20', '2019-04-25', 'test', 'EM002');
INSERT INTO `t_purchase` VALUES ('3', 'SP001', '1', '90', '20', '2019-04-25', '进货20', 'EM002');

-- ----------------------------
-- Table structure for `t_sale`
-- ----------------------------
DROP TABLE IF EXISTS `t_sale`;
CREATE TABLE `t_sale` (
  `saleId` int(11) NOT NULL AUTO_INCREMENT,
  `productObj` varchar(20) DEFAULT NULL,
  `customerObj` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `saleDate` varchar(20) DEFAULT NULL,
  `memo` varchar(60) DEFAULT NULL,
  `employeeObj` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`saleId`),
  KEY `FKCB541552A2CBF239` (`employeeObj`),
  KEY `FKCB541552DFAF2439` (`customerObj`),
  KEY `FKCB5415521D6F5E55` (`productObj`),
  CONSTRAINT `FKCB5415521D6F5E55` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productNo`),
  CONSTRAINT `FKCB541552A2CBF239` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`),
  CONSTRAINT `FKCB541552DFAF2439` FOREIGN KEY (`customerObj`) REFERENCES `t_customer` (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sale
-- ----------------------------
INSERT INTO `t_sale` VALUES ('1', 'SP001', '1', '199', '2', '2019-04-25', '销售登记', 'EM001');
INSERT INTO `t_sale` VALUES ('2', 'SP002', '1', '65', '1', '2019-04-25', '我销售的哦', 'EM002');
INSERT INTO `t_sale` VALUES ('3', 'SP001', '1', '195', '1', '2019-04-25', '测试服', 'EM002');

-- ----------------------------
-- Table structure for `t_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `supplierId` int(11) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(20) DEFAULT NULL,
  `connectPerson` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `address` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
INSERT INTO `t_supplier` VALUES ('1', '烟酒厂', '李哥', '13483913943', '028-83984134', '四川成都郫县');
