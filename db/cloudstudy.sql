/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26)
 Source Host           : localhost:3306
 Source Schema         : cloudstudy

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26)
 File Encoding         : 65001

 Date: 16/05/2026 17:20:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for db_book
-- ----------------------------
DROP TABLE IF EXISTS `db_book`;
CREATE TABLE `db_book`  (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_book
-- ----------------------------
INSERT INTO `db_book` VALUES (1, '深入理解Java虚拟机', 'JVM性能调优与底层原理');
INSERT INTO `db_book` VALUES (2, 'Spring Boot实战', '企业级应用开发指南');
INSERT INTO `db_book` VALUES (3, '数据库系统概论', '关系数据库与SQL基础');
INSERT INTO `db_book` VALUES (4, '算法导论', '经典算法与数据结构');
INSERT INTO `db_book` VALUES (5, '设计模式', '可复用面向对象软件基础');

-- ----------------------------
-- Table structure for db_borrow
-- ----------------------------
DROP TABLE IF EXISTS `db_borrow`;
CREATE TABLE `db_borrow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `bid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_bid_uid`(`uid`, `bid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of db_borrow
-- ----------------------------
INSERT INTO `db_borrow` VALUES (1, 1, 1);
INSERT INTO `db_borrow` VALUES (2, 1, 2);
INSERT INTO `db_borrow` VALUES (3, 2, 3);
INSERT INTO `db_borrow` VALUES (4, 3, 4);
INSERT INTO `db_borrow` VALUES (5, 3, 5);
INSERT INTO `db_borrow` VALUES (6, 4, 2);
INSERT INTO `db_borrow` VALUES (7, 5, 1);
INSERT INTO `db_borrow` VALUES (8, 2, 5);

-- ----------------------------
-- Table structure for db_user
-- ----------------------------
DROP TABLE IF EXISTS `db_user`;
CREATE TABLE `db_user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `age` int(11) NOT NULL,
  `sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_user
-- ----------------------------
INSERT INTO `db_user` VALUES (1, '张三', 22, '男');
INSERT INTO `db_user` VALUES (2, '李四', 25, '女');
INSERT INTO `db_user` VALUES (3, '王五', 30, '男');
INSERT INTO `db_user` VALUES (4, '赵六', 18, '女');
INSERT INTO `db_user` VALUES (5, '刘七', 28, '男');

SET FOREIGN_KEY_CHECKS = 1;
