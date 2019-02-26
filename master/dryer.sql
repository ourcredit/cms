/*
 Navicat Premium Data Transfer

 Source Server         : hgj
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : rm-2zehp4p1df18szor22o.mysql.rds.aliyuncs.com:3306
 Source Schema         : dryer

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 09/01/2019 22:54:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sale_category
-- ----------------------------
DROP TABLE IF EXISTS `sale_category`;
CREATE TABLE `sale_category`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_chargeorder
-- ----------------------------
DROP TABLE IF EXISTS `sale_chargeorder`;
CREATE TABLE `sale_chargeorder`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
  `openId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `orderState` int(10) NULL DEFAULT NULL COMMENT '订单状态',
  `payState` int(10) NULL DEFAULT NULL COMMENT '支付状态',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `price` int(10) NULL DEFAULT NULL COMMENT '价格',
  `wechatOrder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信订单',
  `productName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `count` int(11) NULL DEFAULT NULL COMMENT '次数',
  `isPayFor` int(11) NULL DEFAULT 0 COMMENT '是否充值带支付',
  `machineOrder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机器订单号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_customer
-- ----------------------------
DROP TABLE IF EXISTS `sale_customer`;
CREATE TABLE `sale_customer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `openId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户唯一id',
  `nickName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `balance` int(11) NOT NULL DEFAULT 0 COMMENT '服务剩余次数',
  `unionId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatarUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_customerorder
-- ----------------------------
DROP TABLE IF EXISTS `sale_customerorder`;
CREATE TABLE `sale_customerorder`  (
  `id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `openId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户id',
  `dryType` int(10) NOT NULL COMMENT '烘干类型(纯棉 化纤 其他)',
  `customerName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户姓名',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机',
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区',
  `community` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小区',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '取件地址',
  `orderState` int(10) NULL DEFAULT NULL COMMENT '订单状态',
  `payState` int(10) NULL DEFAULT NULL COMMENT '支付状态',
  `operaterId` int(10) NULL DEFAULT NULL COMMENT '接单人员id',
  `isTime` int(11) NULL DEFAULT NULL COMMENT '是否次数支付',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `price` int(10) NULL DEFAULT NULL COMMENT '价格',
  `orderNum` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对接订单编号',
  `regionCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `backOrder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款订单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_device
-- ----------------------------
DROP TABLE IF EXISTS `sale_device`;
CREATE TABLE `sale_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `deviceName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点位名称',
  `deviceNum` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备编码',
  `passWord` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运维密码',
  `areaId` int(11) NULL DEFAULT NULL COMMENT '区域id',
  `pointName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `pointId` int(11) NULL DEFAULT NULL COMMENT '从属点位',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `isDeleted` tinyint(2) NULL DEFAULT 0 COMMENT '软删除  ',
  `version` int(11) NULL DEFAULT 1 COMMENT '设备版本号',
  `count` int(11) NULL DEFAULT 0 COMMENT '剩余次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_errorlog
-- ----------------------------
DROP TABLE IF EXISTS `sale_errorlog`;
CREATE TABLE `sale_errorlog`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
  `deviceNum` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备编号',
  `deviceName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `logType` int(11) NULL DEFAULT NULL COMMENT '日志类型 1运行 2故障',
  `Message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `pointName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点位名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sale_file
-- ----------------------------
DROP TABLE IF EXISTS `sale_file`;
CREATE TABLE `sale_file`  (
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
-- Table structure for sale_log
-- ----------------------------
DROP TABLE IF EXISTS `sale_log`;
CREATE TABLE `sale_log`  (
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sale_menu
-- ----------------------------
DROP TABLE IF EXISTS `sale_menu`;
CREATE TABLE `sale_menu`  (
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
-- Table structure for sale_operater
-- ----------------------------
DROP TABLE IF EXISTS `sale_operater`;
CREATE TABLE `sale_operater`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `openId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '企业用户唯一key',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `orderCount` int(255) NULL DEFAULT NULL COMMENT '订单数',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `region` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_order
-- ----------------------------
DROP TABLE IF EXISTS `sale_order`;
CREATE TABLE `sale_order`  (
  `id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '主键',
  `dryType` int(10) NOT NULL COMMENT '烘干类型(纯棉 化纤 其他)',
  `orderNum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对接订单',
  `orderState` int(10) NULL DEFAULT NULL COMMENT '订单状态',
  `payState` int(10) NULL DEFAULT NULL COMMENT '支付状态',
  `deviceNum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备num',
  `payType` int(10) NULL DEFAULT NULL COMMENT '支付类型  微信 支付宝',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `price` int(10) NULL DEFAULT NULL COMMENT '价格',
  `customerOrder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定客户订单信息',
  `backOrder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款订单',
  `deviceName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'name',
  `pointName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点位名',
  `orderFrom` int(10) NULL DEFAULT 0 COMMENT '订单来源 为了退款',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_point
-- ----------------------------
DROP TABLE IF EXISTS `sale_point`;
CREATE TABLE `sale_point`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `pointName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点位名',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'i奥数',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `isDeleted` tinyint(2) NULL DEFAULT 0 COMMENT '软删除  ',
  `x` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'x坐标',
  `y` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'y坐标',
  `areaId` int(11) NULL DEFAULT NULL COMMENT '区域id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_post
-- ----------------------------
DROP TABLE IF EXISTS `sale_post`;
CREATE TABLE `sale_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provence` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` int(11) NOT NULL,
  `creationTime` datetime(0) NULL DEFAULT NULL,
  `creatorUserId` int(11) NULL DEFAULT NULL,
  `provenceCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cityCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `countryCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_product
-- ----------------------------
DROP TABLE IF EXISTS `sale_product`;
CREATE TABLE `sale_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `productName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名',
  `productNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品编号',
  `productType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品类型',
  `price` int(10) NULL DEFAULT NULL COMMENT '默认价格',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `isDeleted` tinyint(2) NULL DEFAULT 0 COMMENT '软删除  ',
  `description` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `imageUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_region
-- ----------------------------
DROP TABLE IF EXISTS `sale_region`;
CREATE TABLE `sale_region`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'name',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `level` int(11) NULL DEFAULT NULL COMMENT '长度',
  `price` int(10) NULL DEFAULT NULL COMMENT '运费价格',
  `enable` int(10) NULL DEFAULT 0 COMMENT '启用禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_role
-- ----------------------------
DROP TABLE IF EXISTS `sale_role`;
CREATE TABLE `sale_role`  (
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
-- Table structure for sale_rolemenu
-- ----------------------------
DROP TABLE IF EXISTS `sale_rolemenu`;
CREATE TABLE `sale_rolemenu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'guid',
  `roleId` int(11) NOT NULL,
  `menuId` int(11) NOT NULL,
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  INDEX `ix_roleId`(`roleId`) USING BTREE,
  INDEX `ix_menuId`(`menuId`) USING BTREE,
  CONSTRAINT `sale_rolemenu_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `sale_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sale_serial
-- ----------------------------
DROP TABLE IF EXISTS `sale_serial`;
CREATE TABLE `sale_serial`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'guid',
  `order` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `price` int(11) NOT NULL COMMENT '退款单价格',
  `backOrder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '退款单号',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `productName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `payType` int(255) NULL DEFAULT NULL COMMENT '单号类型 1支付 2退款',
  `deviceId` int(11) NULL DEFAULT NULL COMMENT '设备id',
  `deviceName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `pointId` int(11) NULL DEFAULT NULL COMMENT '点位id',
  `pointName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点位名',
  `orderType` int(10) NULL DEFAULT NULL COMMENT '流水单类型 1小程序 2上位机 3充值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_tree
-- ----------------------------
DROP TABLE IF EXISTS `sale_tree`;
CREATE TABLE `sale_tree`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名',
  `parentId` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `creationTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creatorUserId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `levelCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '级别code',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_user
-- ----------------------------
DROP TABLE IF EXISTS `sale_user`;
CREATE TABLE `sale_user`  (
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
  `areaId` int(11) NULL DEFAULT NULL COMMENT '区域id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ix_account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sale_userrole
-- ----------------------------
DROP TABLE IF EXISTS `sale_userrole`;
CREATE TABLE `sale_userrole`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `creationTime` datetime(0) NULL DEFAULT NULL,
  `creatorUserId` int(11) NULL DEFAULT NULL,
  INDEX `ix_userId`(`userId`) USING BTREE,
  INDEX `ix_roleId`(`roleId`) USING BTREE,
  CONSTRAINT `sale_userrole_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `sale_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sale_userrole_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `sale_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- View structure for v_getallorders
-- ----------------------------
DROP VIEW IF EXISTS `v_getallorders`;
CREATE ALGORITHM = UNDEFINED DEFINER = `znhgj`@`%` SQL SECURITY DEFINER VIEW `v_getallorders` AS select `sale_order`.`id` AS `id`,(case `sale_order`.`payType` when 2 then (`sale_order`.`price` * 100) else `sale_order`.`price` end) AS `price`,`sale_order`.`creationTime` AS `creationTime`,`sale_order`.`payState` AS `payState`,`sale_order`.`orderState` AS `orderState`,`sale_order`.`payType` AS `payType` from `sale_order` where (not(`sale_order`.`customerOrder` in (select `sale_customerorder`.`id` from `sale_customerorder`))) union select `sale_chargeorder`.`id` AS `id`,`sale_chargeorder`.`price` AS `price`,`sale_chargeorder`.`creationTime` AS `creationTime`,`sale_chargeorder`.`payState` AS `payState`,`sale_chargeorder`.`orderState` AS `orderState`,1 AS `1` from `sale_chargeorder` where ((`sale_chargeorder`.`isPayFor` = 1) and (`sale_chargeorder`.`count` <> 1)) union select `sale_customerorder`.`id` AS `id`,`sale_customerorder`.`price` AS `price`,`sale_customerorder`.`creationTime` AS `creationTime`,`sale_customerorder`.`payState` AS `payState`,`sale_customerorder`.`orderState` AS `orderState`,1 AS `1` from `sale_customerorder`;

SET FOREIGN_KEY_CHECKS = 1;
