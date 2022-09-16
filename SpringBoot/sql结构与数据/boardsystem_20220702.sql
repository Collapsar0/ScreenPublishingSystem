/*
Navicat MySQL Data Transfer

Source Server         : double
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : boardsystem

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2022-07-02 14:13:55
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
  `systemUpdate` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '系统更新',
  `sendingEdition` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '信发版本',
  `nowPlan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前计划',
  `equipmentGroupID` int(11) DEFAULT NULL COMMENT '所属分组',
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES ('2', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', null, '1.3.1', '--', '2', 'T232BS200721000123', null, null, null, null, null, '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 11:35:50');
INSERT INTO `equipment` VALUES ('3', 'EQUC', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', null, '1.3.1', '--', '4', 'T232BS200721000123', null, null, '2022-06-27 13:28:26', '2022-06-27 13:28:26', '2022-06-27 13:28:26', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 13:28:26');
INSERT INTO `equipment` VALUES ('4', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', null, '1.3.1', '--', '2', 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 13:30:44', '2022-06-27 13:30:44', '2022-06-27 13:30:44', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 13:30:44');
INSERT INTO `equipment` VALUES ('5', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', null, '1.3.1', '--', '2', 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 13:39:32', '2022-06-27 13:39:32', '2022-06-27 13:39:32', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 13:39:32');
INSERT INTO `equipment` VALUES ('6', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', null, '1.3.1', '--', '4', 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 14:00:41', '2022-06-27 14:00:41', '2022-06-27 14:00:41', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 14:00:41');
INSERT INTO `equipment` VALUES ('7', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', null, '1.3.1', '--', '4', 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-27 14:07:43', '2022-06-27 14:07:43', '2022-06-27 14:07:43', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-27 14:07:43');
INSERT INTO `equipment` VALUES ('8', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '已是最新', '1.3.1', '--', '1', 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-06-28 11:32:04', '2022-06-28 11:32:04', '2022-06-28 11:32:04', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-06-28 11:32:04');
INSERT INTO `equipment` VALUES ('9', 'TEST设备', 'HiDPTAndroid Hi3751V553', '1', '192.168.113.212', 'A0BB3ED3861D', 'A0BB3ED2F376', '1920x1080', '离线', 'BOE_iGallery32_V13103_V5.2.0', '已是最新', '1.3.1', '--', '1', 'T232BS200721000123', '0.96GB', '4.70GB可用(共5.39GB)', '2022-07-02 10:44:56', '2022-07-02 10:44:56', '2022-07-02 10:44:56', '横屏', '中国浙江省杭州市拱墅区上塘街道东教路', '2022-07-02 10:44:56');

-- ----------------------------
-- Table structure for equipmentgroup
-- ----------------------------
DROP TABLE IF EXISTS `equipmentgroup`;
CREATE TABLE `equipmentgroup` (
  `equipmentGroupID` int(255) NOT NULL AUTO_INCREMENT COMMENT '分组ID',
  `groupName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分组名称',
  `organization` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属机构',
  `equipmentCount` int(255) DEFAULT NULL COMMENT '设备数量',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `equipmentIDList` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组内设备ID列表',
  PRIMARY KEY (`equipmentGroupID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of equipmentgroup
-- ----------------------------
INSERT INTO `equipmentgroup` VALUES ('1', 'TESTn10', 'zju', '2', 'GZJisZLGsHandsbend', null);
INSERT INTO `equipmentgroup` VALUES ('2', 'TESTn101', 'zju', '3', 'GZJisZLGsHandsbend', null);
INSERT INTO `equipmentgroup` VALUES ('4', '1150', 'zju', '3', 'GZJisZLGsHandsbend', null);
INSERT INTO `equipmentgroup` VALUES ('5', 'TEST30', 'zju', '0', 'GZJisZLGsHandsbend', null);
INSERT INTO `equipmentgroup` VALUES ('6', 'groupTest4', 'zju', '0', '2121', null);
INSERT INTO `equipmentgroup` VALUES ('7', 'groupTest7', 'zju', '0', '2121', null);
INSERT INTO `equipmentgroup` VALUES ('8', 'groupTest8', 'zju', '0', '2121', null);
INSERT INTO `equipmentgroup` VALUES ('9', 'groupTest9', 'zju', '0', '2121', null);

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `materialID` int(11) NOT NULL AUTO_INCREMENT COMMENT '素材id',
  `materialName` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '素材名',
  `materialType` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '素材类型',
  `resolvingPower` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '分辨率',
  `materialSize` double(255,2) DEFAULT NULL COMMENT '素材大小',
  `programCount` int(255) DEFAULT NULL COMMENT '引用节目数目',
  `author` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '作者',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `url` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '素材地址',
  `materialGroupID` int(11) DEFAULT NULL COMMENT '素材分组',
  PRIMARY KEY (`materialID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES ('1', 'new素材', '图片', '100x100', '100.00', '0', 'admin2', '2022-06-29 11:53:39', 'url', '1');

-- ----------------------------
-- Table structure for materialgroup
-- ----------------------------
DROP TABLE IF EXISTS `materialgroup`;
CREATE TABLE `materialgroup` (
  `materialGroupID` int(11) NOT NULL AUTO_INCREMENT COMMENT '素材组id',
  `materialGroupName` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '素材组名',
  `materialGroupType` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '素材组类型',
  PRIMARY KEY (`materialGroupID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of materialgroup
-- ----------------------------
INSERT INTO `materialgroup` VALUES ('1', 'TEST', '图片');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeID` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `noticeTitle` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公告标题',
  `noticeContent` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公告内容',
  `noticeState` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公告状态',
  `author` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公告作者',
  `playbackTime` datetime DEFAULT NULL COMMENT '播放时间',
  `startTime` datetime DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  `playbackMode` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '播放模式',
  `playbackSpeed` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '播放速度',
  `textPosition` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '文字位置',
  `textColor` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '文字颜色',
  `textSize` int(11) DEFAULT NULL COMMENT '文字字号',
  `backgroundColor` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '背景颜色',
  `backgroundHeight` int(11) DEFAULT NULL COMMENT '背景高度',
  `equipmentIDList` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备id列表',
  PRIMARY KEY (`noticeID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', null, '待发布', null, 'admin2', null, null, null, '持续播放', '快', '上', 'FFFFF0', '5', 'FFFFFF', '10', null);
INSERT INTO `notice` VALUES ('2', null, '待发布', null, 'admin2', null, '2022-01-01 00:10:00', '2022-01-01 00:11:00', '时段播放', '快', '上', 'FFFFF0', '5', 'FFFFFF', '10', null);
INSERT INTO `notice` VALUES ('3', null, '待发布', null, 'admin2', null, '2022-10-01 10:00:08', '2022-11-01 10:00:08', '时段播放', '快', '上', 'FFFFF0', '5', 'FFFFFF', '10', null);
INSERT INTO `notice` VALUES ('4', null, 'test', '待发布', 'admin2', null, '2022-10-01 10:00:08', '2022-11-01 10:00:08', '时段播放', '快', '上', 'FFFFF0', '5', 'FFFFFF', '10', null);
INSERT INTO `notice` VALUES ('5', null, 'test', '待发布', 'admin2', '2022-10-01 10:00:08', '2022-10-01 10:00:08', '2022-11-01 10:00:08', '时段播放', '快', '上', 'FFFFF0', '5', 'FFFFFF', '10', null);
INSERT INTO `notice` VALUES ('6', null, 'test', '发布中', 'admin2', '2022-10-01 10:00:08', '2022-10-01 10:00:08', '2022-11-01 10:00:08', '时段播放', '快', '上', 'FFFFF0', '5', 'FFFFFF', '10', '[1]');
INSERT INTO `notice` VALUES ('7', null, 'test', '发布中', 'admin2', '2022-10-01 10:00:08', '2022-10-01 10:00:08', '2022-11-01 10:00:08', '时段播放', '快', '上', 'FFFFF0', '5', 'FFFFFF', '10', '[1, 2, 3]');

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
  `planExamineID` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划审核序号',
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
  `programID` int(11) NOT NULL AUTO_INCREMENT COMMENT '节目ID',
  `programPic` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目缩略图',
  `programName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目名称',
  `resolvingPower` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分辨率',
  `programDuration` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目时长',
  `programData` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目大小',
  `programState` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目状态',
  `author` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `programContent` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节目内容',
  PRIMARY KEY (`programID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of program
-- ----------------------------
INSERT INTO `program` VALUES ('1', 'url', '节目test', '100x100', '26', '100.0', '使用中', 'test2', '2022-07-01 11:29:13', '[{\"materialID\":1,\"playbackTime\":26}]');
INSERT INTO `program` VALUES ('2', 'url', '节目test', '100x100', null, '16', '使用中', 'admin2', '2022-07-01 09:50:15', '[{\"materialID\":1,\"playbackTime\":16}]');
INSERT INTO `program` VALUES ('3', 'url', 'updateTest', '100x100', '16', '1024', '使用中', 'admin2', '2022-07-01 09:51:52', '[{\"materialID\":1,\"playbackTime\":16}]');
INSERT INTO `program` VALUES ('4', 'url', '节目test', '100x100', '32s', '1024', '使用中', 'admin2', '2022-07-01 09:56:59', '[{\"materialID\":1,\"playbackTime\":16},{\"materialID\":1,\"playbackTime\":16}]');
INSERT INTO `program` VALUES ('5', 'url', '节目test', '100x100', '24s', '1024', '使用中', 'admin2', '2022-07-01 09:57:09', '[{\"materialID\":1,\"playbackTime\":16},{\"materialID\":1,\"playbackTime\":8}]');
INSERT INTO `program` VALUES ('7', 'url', '节目test55', '100x100', '24s', '200.0', '使用中', 'admin2', '2022-07-01 11:18:11', '[{\"materialID\":1,\"playbackTime\":16},{\"materialID\":1,\"playbackTime\":8}]');
INSERT INTO `program` VALUES ('8', 'url', '节目test550', '100x100', '24s', '200.0', '使用中', 'admin2', '2022-07-01 14:50:35', '[{\"materialID\":1,\"playbackTime\":16},{\"materialID\":1,\"playbackTime\":8}]');
INSERT INTO `program` VALUES ('9', 'url', '节目test12', '100x100', '4s', '200.0', '使用中', 'admin2', '2022-07-01 15:13:32', '[{\"materialID\":1,\"playbackTime\":\"1\"},{\"materialID\":1,\"playbackTime\":\"3\"}]');

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
INSERT INTO `user` VALUES ('2', 'admin2', 'c84258e9c39059a89ab77d846ddab909', '已启用', 'zucc', 'double', 'frx', '15555555555', '31901227@stu.zucc.edu.cn', '2022-06-29 09:19:36', 'student');
INSERT INTO `user` VALUES ('5', 'test2', '5e9d11a14ad1c8dd77e98ef9b53fd1ba', '已启用', 'zucc', 'double', 'frx', '15555555555', '31901227@stu.zucc.edu.cn', '2022-06-29 09:18:42', null);
INSERT INTO `user` VALUES ('6', 'test3', '8ad8757baa8564dc136c1e07507f4a98', '已启用', 'zucc', 'double', 'frx', '15555555555', '31901227@stu.zucc.edu.cn', '2022-06-29 09:19:16', null);
INSERT INTO `user` VALUES ('7', 'a', '5e9d11a14ad1c8dd77e98ef9b53fd1ba', '已启用', 'zucc', 'double', 'frx', '15555555555', '31901227@stu.zucc.edu.cn', '2022-06-29 09:11:49', null);
INSERT INTO `user` VALUES ('8', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'ok', 'update', 'double', 'update', '3306', '2392500902@qq.com', '2022-06-27 15:07:09', null);
