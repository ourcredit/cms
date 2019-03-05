/*
 Navicat Premium Data Transfer

 Source Server         : 47.94.214.199
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 47.94.214.199:3306
 Source Schema         : crm

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 05/03/2019 20:00:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `parent` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作用域',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('0fa6304c8e0188393e32a24ad6988f60', '典当', '2019-03-04 20:39:07', 7, NULL, '渠道类型');
INSERT INTO `category` VALUES ('101303a16b50b3182f9aeb4bd5a5b8d4', '助贷', '2019-03-04 20:51:43', 7, NULL, '主营业务');
INSERT INTO `category` VALUES ('1383446e24fe407bc3d76659893c3e44', 'B', '2019-03-04 20:49:56', 7, NULL, '渠道等级');
INSERT INTO `category` VALUES ('386e6ff50bdeef0cb07eed94a884fa94', '质押', '2019-03-04 20:51:50', 7, NULL, '主营业务');
INSERT INTO `category` VALUES ('53b3da0afe349b3f642029597681bd79', '电销', '2019-03-04 20:38:36', 7, NULL, '渠道类型');
INSERT INTO `category` VALUES ('57543dbec417663dabda1f7e4e621ee4', 'A', '2019-03-04 20:49:49', 7, NULL, '渠道等级');
INSERT INTO `category` VALUES ('6c912ac107755ffee24bb913dda53328', '银行', '2019-03-04 20:39:15', 7, NULL, '渠道类型');
INSERT INTO `category` VALUES ('80ead6d97f901e066b25c8e153844aff', '抵押', '2019-03-04 20:39:29', 7, NULL, '渠道类型');
INSERT INTO `category` VALUES ('98b8719d062ad8fabe2eb7af6cd72a74', 'C', '2019-03-04 20:50:03', 7, NULL, '渠道等级');
INSERT INTO `category` VALUES ('9cf7252632c499090e6720bfd998d971', '质押', '2019-03-04 20:38:48', 7, NULL, '渠道类型');
INSERT INTO `category` VALUES ('af18681da7cea6b953265265ede8f0eb', '经济公司', '2019-03-04 20:39:00', 7, NULL, '渠道类型');

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `channelName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道名',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `level` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '级别',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `workUnit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `post` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位',
  `business` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
  `code` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creatorName` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `orgName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构名',
  `visitTime` datetime(0) NULL DEFAULT NULL COMMENT '拜访时间',
  `visitCount` int(11) NULL DEFAULT 0 COMMENT '拜访量',
  `lunchTime` datetime(0) NULL DEFAULT NULL COMMENT '约饭时间',
  `lunchCount` int(11) NULL DEFAULT 0 COMMENT '约饭量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户编码',
  `customerName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户姓名',
  `idCard` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `gender` int(11) NULL DEFAULT NULL COMMENT '性别',
  `maritalStatus` int(50) NULL DEFAULT NULL COMMENT '婚否',
  `level` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户级别',
  `from` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户来源',
  `channel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '渠道',
  `business` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `mobile2` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机2',
  `telphone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机',
  `mobile1` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机1\r\n',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creatorName` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `extendField` varchar(20000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展属性',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `size` bigint(11) NULL DEFAULT NULL COMMENT '大小',
  `ext` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `tenantId` int(11) NULL DEFAULT NULL COMMENT '租户id',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ix_id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `nextfollowTime` datetime(0) NULL DEFAULT NULL COMMENT '下次跟进时间',
  `visitContent` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跟进内容',
  `followTime` datetime(0) NULL DEFAULT NULL COMMENT '选定跟进时间',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型字段',
  `objectId` int(11) NULL DEFAULT NULL COMMENT '对应类型的主id',
  `time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `logDescription` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志类型',
  `actionArgs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志名称',
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `className` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类名称',
  `method` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `succeed` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否成功',
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '1 菜单 2按钮',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限码',
  `parentId` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `creationTime` datetime(0) NULL DEFAULT NULL,
  `creatorUserId` int(11) NULL DEFAULT NULL,
  `isDeleted` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `roleName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'name',
  `displayName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示名',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `isActive` tinyint(2) NULL DEFAULT NULL COMMENT '启用状态',
  `isStatic` tinyint(4) NULL DEFAULT NULL COMMENT '是否静态',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `isDeleted` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`, `roleName`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for roleMenu
-- ----------------------------
DROP TABLE IF EXISTS `roleMenu`;
CREATE TABLE `roleMenu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'guid',
  `roleId` int(11) NOT NULL,
  `menuId` int(11) NOT NULL,
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  INDEX `ix_roleId`(`roleId`) USING BTREE,
  INDEX `ix_menuId`(`menuId`) USING BTREE,
  CONSTRAINT `roleMenu_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


DROP TABLE IF EXISTS `tree`;
CREATE TABLE `tree`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名',
  `parentId` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `levelCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '级别code',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `account` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `userName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `isActive` tinyint(2) NULL DEFAULT NULL COMMENT '1启用  0禁用',
  `isDeleted` tinyint(2) NULL DEFAULT 0 COMMENT '软删除  ',
  `lastLoginTime` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `orgId` int(11) NULL DEFAULT NULL COMMENT '组织id',
  `orgName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ix_account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `creationTime` datetime(0) NULL DEFAULT NULL,
  `creatorUserId` int(11) NULL DEFAULT NULL,
  INDEX `ix_userId`(`userId`) USING BTREE,
  INDEX `ix_roleId`(`roleId`) USING BTREE,
  CONSTRAINT `userrole_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `userrole_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for visit
-- ----------------------------
DROP TABLE IF EXISTS `visit`;
CREATE TABLE `visit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `visitTime` datetime(0) NULL DEFAULT NULL COMMENT '拜访时间',
  `visitPlace` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拜访地点',
  `visitForce` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拜访目的',
  `visitType` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拜访方式',
  `visitResult` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拜访结果',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型字段',
  `objectId` int(11) NULL DEFAULT NULL COMMENT '对应类型的主id',
  `time` int(11) NULL DEFAULT NULL COMMENT '时长',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
