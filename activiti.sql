/*
 Navicat Premium Data Transfer

 Source Server         : 31
 Source Server Type    : MySQL
 Source Server Version : 50166
 Source Host           : 192.168.1.31:3306
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 50166
 File Encoding         : 65001

 Date: 27/12/2018 17:18:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for T_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `T_PERMISSION`;
CREATE TABLE `T_PERMISSION`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  UNIQUE INDEX `permissionname_UNIQUE`(`permission_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of T_PERMISSION
-- ----------------------------
INSERT INTO `T_PERMISSION` VALUES (1, 'L1', NULL);
INSERT INTO `T_PERMISSION` VALUES (2, 'L2', NULL);
INSERT INTO `T_PERMISSION` VALUES (3, 'L3', NULL);
INSERT INTO `T_PERMISSION` VALUES (4, 'L4', NULL);
INSERT INTO `T_PERMISSION` VALUES (5, 'L5', NULL);

-- ----------------------------
-- Table structure for T_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE`;
CREATE TABLE `T_ROLE`  (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  UNIQUE INDEX `rolename_UNIQUE`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of T_ROLE
-- ----------------------------
INSERT INTO `T_ROLE` VALUES (1, 'Employee', 'Employee');
INSERT INTO `T_ROLE` VALUES (2, 'GL', 'Group Leader');
INSERT INTO `T_ROLE` VALUES (3, 'PM', 'Project Manager');
INSERT INTO `T_ROLE` VALUES (4, 'DM', 'Department Manager');
INSERT INTO `T_ROLE` VALUES (5, 'GM', 'General Manager');
INSERT INTO `T_ROLE` VALUES (6, 'HR', 'Human Resource');
INSERT INTO `T_ROLE` VALUES (7, 'ADMIN', 'Administrator');

-- ----------------------------
-- Table structure for T_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE_PERMISSION`;
CREATE TABLE `T_ROLE_PERMISSION`  (
  `rp_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`rp_id`) USING BTREE,
  INDEX `a_idx`(`role_id`) USING BTREE,
  INDEX `b_idx`(`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of T_ROLE_PERMISSION
-- ----------------------------
INSERT INTO `T_ROLE_PERMISSION` VALUES (1, 7, 1);
INSERT INTO `T_ROLE_PERMISSION` VALUES (2, 7, 2);
INSERT INTO `T_ROLE_PERMISSION` VALUES (3, 7, 3);
INSERT INTO `T_ROLE_PERMISSION` VALUES (4, 7, 4);
INSERT INTO `T_ROLE_PERMISSION` VALUES (5, 7, 5);
INSERT INTO `T_ROLE_PERMISSION` VALUES (6, 4, 1);
INSERT INTO `T_ROLE_PERMISSION` VALUES (7, 5, 1);
INSERT INTO `T_ROLE_PERMISSION` VALUES (8, 5, 2);
INSERT INTO `T_ROLE_PERMISSION` VALUES (9, 6, 1);
INSERT INTO `T_ROLE_PERMISSION` VALUES (10, 6, 2);
INSERT INTO `T_ROLE_PERMISSION` VALUES (11, 6, 3);
INSERT INTO `T_ROLE_PERMISSION` VALUES (12, 7, 1);
INSERT INTO `T_ROLE_PERMISSION` VALUES (13, 7, 2);
INSERT INTO `T_ROLE_PERMISSION` VALUES (14, 7, 3);
INSERT INTO `T_ROLE_PERMISSION` VALUES (15, 7, 4);
INSERT INTO `T_ROLE_PERMISSION` VALUES (16, 1, 1);

-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER`  (
  `uid` int(11) NOT NULL,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tel` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `mail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of T_USER
-- ----------------------------
INSERT INTO `T_USER` VALUES (1, 'admin', '$shiro1$SHA-256$50000$/nacukVGSN3NoKBZD4RoVg==$9T8wSzsYm6ohLoUK78ly3ArSCpzVjDrzck6Th5GWNC0=', '110', 20, NULL);
INSERT INTO `T_USER` VALUES (2, 'test1', '$shiro1$SHA-256$50000$/nacukVGSN3NoKBZD4RoVg==$9T8wSzsYm6ohLoUK78ly3ArSCpzVjDrzck6Th5GWNC0=', '138', 21, NULL);
INSERT INTO `T_USER` VALUES (3, 'test2', '$shiro1$SHA-256$50000$/nacukVGSN3NoKBZD4RoVg==$9T8wSzsYm6ohLoUK78ly3ArSCpzVjDrzck6Th5GWNC0=', '137', 22, NULL);
INSERT INTO `T_USER` VALUES (4, 'test3', '$shiro1$SHA-256$50000$/nacukVGSN3NoKBZD4RoVg==$9T8wSzsYm6ohLoUK78ly3ArSCpzVjDrzck6Th5GWNC0=', '131', 25, NULL);
INSERT INTO `T_USER` VALUES (5, 'test4', '$shiro1$SHA-256$50000$/nacukVGSN3NoKBZD4RoVg==$9T8wSzsYm6ohLoUK78ly3ArSCpzVjDrzck6Th5GWNC0=', '134', 44, NULL);

-- ----------------------------
-- Table structure for T_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ROLE`;
CREATE TABLE `T_USER_ROLE`  (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`ur_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of T_USER_ROLE
-- ----------------------------
INSERT INTO `T_USER_ROLE` VALUES (1, 1, 7);
INSERT INTO `T_USER_ROLE` VALUES (2, 2, 1);
INSERT INTO `T_USER_ROLE` VALUES (3, 3, 4);
INSERT INTO `T_USER_ROLE` VALUES (7, 4, 5);
INSERT INTO `T_USER_ROLE` VALUES (8, 5, 6);

-- ----------------------------
-- Table structure for T_VACATION
-- ----------------------------
DROP TABLE IF EXISTS `T_VACATION`;
CREATE TABLE `T_VACATION`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_instance_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `start_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `end_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `leave_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reason` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `apply_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reality_start_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reality_end_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
