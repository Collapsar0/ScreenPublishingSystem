/*
Navicat MySQL Data Transfer

Source Server         : double
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : boardsystem

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2022-06-27 16:37:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
  `equipmentID` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `equipmentName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `equipmentType` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备型号',
  `organization` int(255) DEFAULT NULL COMMENT '所属机构',
  `ip` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备IP',
  `macAddressWired` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '有线Mac地址',
  `macAddressWireless` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '无线Mac地址',
  `resolvingPower` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分辨率',
  `equipmentState` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备状态',
  `systemEdition` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统版本',
  `sendingEdition` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '信发版本',
  `nowPlan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前计划',
  `groupName` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '所属分组',
  `SNNumber` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'SN号',
  `ram` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '运行内存',
  `rom` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '储存空间',
  `activationTime` datetime DEFAULT NULL COMMENT '激活时间',
  `registerTime` datetime DEFAULT NULL COMMENT '注册时间',
  `lastHeartbeatTime` datetime DEFAULT NULL COMMENT '最后心跳时间',
  `screenDisplay` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '屏显方式',
  `installationPosition` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '安装位置',
  `runTime` datetime DEFAULT NULL COMMENT '设备运行时长',
  PRIMARY KEY (`equipmentID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES ('2', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '1.3.1', '--', null, 'T232BS200721000123', null, null, null, null, null, '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 11:35:50');
INSERT INTO `equipment` VALUES ('3', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '1.3.1', '--', null, 'T232BS200721000123', null, null, '2022-06-27 13:28:26', '2022-06-27 13:28:26', '2022-06-27 13:28:26', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 13:28:26');
INSERT INTO `equipment` VALUES ('4', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '1.3.1', '--', null, 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 13:30:44', '2022-06-27 13:30:44', '2022-06-27 13:30:44', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 13:30:44');
INSERT INTO `equipment` VALUES ('5', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '1.3.1', '--', null, 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 13:39:32', '2022-06-27 13:39:32', '2022-06-27 13:39:32', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 13:39:32');
INSERT INTO `equipment` VALUES ('6', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '1.3.1', '--', null, 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 14:00:41', '2022-06-27 14:00:41', '2022-06-27 14:00:41', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 14:00:41');
INSERT INTO `equipment` VALUES ('7', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '1.3.1', '--', '123', 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 14:07:43', '2022-06-27 14:07:43', '2022-06-27 14:07:43', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 14:07:43');

-- ----------------------------
-- Table structure for equipmentgroup
-- ----------------------------
DROP TABLE IF EXISTS `equipmentgroup`;
CREATE TABLE `equipmentgroup` (
  `equipmentGroupID` int(255) NOT NULL COMMENT '分组ID',
  `groupName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分组名称',
  `organization` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属机构',
  `equipmentCount` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备数量',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `equipmentIDList` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组内设备ID列表',
  PRIMARY KEY (`equipmentGroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of equipmentgroup
-- ----------------------------

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `planID` int(11) NOT NULL COMMENT '计划ID',
  `planName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计划名称',
  `planPic` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计划缩略图',
  `planState` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计划状态',
  `playPattern` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '播放模式',
  `startDate` datetime DEFAULT NULL COMMENT '开始日期',
  `endDate` datetime DEFAULT NULL COMMENT '结束日期',
  `author` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者',
  `checker` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核人',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`planID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of plan
-- ----------------------------

-- ----------------------------
-- Table structure for planexamine
-- ----------------------------
DROP TABLE IF EXISTS `planexamine`;
CREATE TABLE `planexamine` (
  `planExamineID` int(11) NOT NULL COMMENT '计划审核序号',
  `planExaminePic` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '缩略图',
  `usedEquipmentCount` int(11) DEFAULT NULL COMMENT '使用设备数',
  `author` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`planExamineID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of planexamine
-- ----------------------------

-- ----------------------------
-- Table structure for program
-- ----------------------------
DROP TABLE IF EXISTS `program`;
CREATE TABLE `program` (
  `programID` int(11) NOT NULL COMMENT '节目ID',
  `programPic` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目缩略图',
  `programName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目名称',
  `resolvingPower` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分辨率',
  `programDuration` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目时长',
  `programData` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目大小',
  `programState` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目状态',
  `author` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `fileAddress` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目包含的文件的地址',
  PRIMARY KEY (`programID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of program
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `account` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号(账户名)',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `accountState` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号状态',
  `organization` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属组织',
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属角色',
  `realName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `telephone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `userType` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`userID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'admin2', 'admin2', '已停用', 'zucc', 'collapsar', 'BJX', '138172635211', 'zucc@edu.cn', '2022-06-24 14:15:54', 'student');
INSERT INTO `user` VALUES ('5', 'test2', '153', '已停用', 'zju', 'double', '153', '153', '@163.com', '2022-06-24 21:56:56', null);
INSERT INTO `user` VALUES ('6', 'test3', '153', 'ok', 'update', 'double', 'update', '3306', '2392500902@qq.com', '2022-06-27 14:39:46', null);
INSERT INTO `user` VALUES ('7', 'test4', 'b3e3e393c77e35a4a3f3cbd1e429b5dc', 'ok', 'update', 'double', 'update', '3306', '2392500902@qq.com', '2022-06-27 14:53:03', null);
INSERT INTO `user` VALUES ('8', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'ok', 'update', 'double', 'update', '3306', '2392500902@qq.com', '2022-06-27 15:07:09', null);
