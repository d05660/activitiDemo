/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 24/12/2018 22:02:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  UNIQUE INDEX `permissionname_UNIQUE`(`permissionname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (8, 'permission1');
INSERT INTO `t_permission` VALUES (9, 'permission2');
INSERT INTO `t_permission` VALUES (1, '人事审批');
INSERT INTO `t_permission` VALUES (2, '出纳付款');
INSERT INTO `t_permission` VALUES (3, '总经理审批');
INSERT INTO `t_permission` VALUES (4, '财务审批');
INSERT INTO `t_permission` VALUES (5, '部门领导审批');
INSERT INTO `t_permission` VALUES (6, '采购审批');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  UNIQUE INDEX `rolename_UNIQUE`(`rolename`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (9, 'role01');
INSERT INTO `t_role` VALUES (1, '人事');
INSERT INTO `t_role` VALUES (2, '出纳员');
INSERT INTO `t_role` VALUES (3, '总经理');
INSERT INTO `t_role` VALUES (7, '职员');
INSERT INTO `t_role` VALUES (4, '财务管理员');
INSERT INTO `t_role` VALUES (5, '部門経理');
INSERT INTO `t_role` VALUES (6, '采购经理');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `rpid` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `permissionid` int(11) NOT NULL,
  PRIMARY KEY (`rpid`) USING BTREE,
  INDEX `a_idx`(`roleid`) USING BTREE,
  INDEX `b_idx`(`permissionid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES (1, 1, 1);
INSERT INTO `t_role_permission` VALUES (2, 2, 2);
INSERT INTO `t_role_permission` VALUES (3, 3, 3);
INSERT INTO `t_role_permission` VALUES (4, 4, 4);
INSERT INTO `t_role_permission` VALUES (5, 5, 5);
INSERT INTO `t_role_permission` VALUES (6, 6, 6);
INSERT INTO `t_role_permission` VALUES (11, 9, 8);
INSERT INTO `t_role_permission` VALUES (12, 9, 9);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `tel` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'zhansan', '1234', '110', 20);
INSERT INTO `t_user` VALUES (2, 'lisi', '1234', '138', 21);
INSERT INTO `t_user` VALUES (3, 'wangwu', '1234', '137', 22);
INSERT INTO `t_user` VALUES (4, 'zhaoliu', '1234', '131', 25);
INSERT INTO `t_user` VALUES (5, 'user01', '1234', '134', 44);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `urid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`urid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1, 7);
INSERT INTO `t_user_role` VALUES (2, 2, 5);
INSERT INTO `t_user_role` VALUES (3, 3, 1);
INSERT INTO `t_user_role` VALUES (7, 5, 9);

SET FOREIGN_KEY_CHECKS = 1;
