/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : geekcattle

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-05-10 17:09:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `uid` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `salt` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_username` (`username`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1e67f167d99c43f897ec6566043ad6ec', 'flyshy', 'eb3a90502fbe02c2d8de91e0aa307268', '1', '5016b9942433201bf3fe61992eacba71', '0', '2016-12-07 13:24:17', '2017-05-09 16:05:34');
INSERT INTO `admin` VALUES ('ad313d38fe9447ce863fe8584743a010', 'admin', 'c5941c5f3bc693a75e6e863bd2c55ce3', '1', '1ab6d62faa91ae7deec76d6f13ef1600', '0', '2016-12-06 11:16:51', '2017-05-10 17:01:32');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `admin_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`),
  KEY `admin_role_foreign` (`role_id`) USING BTREE,
  CONSTRAINT `fk_ref_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`uid`),
  CONSTRAINT `fk_ref_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('ad313d38fe9447ce863fe8584743a010', '36f1dd1296674fc08484c5abf6a5806b');
INSERT INTO `admin_role` VALUES ('1e67f167d99c43f897ec6566043ad6ec', 'cbe8356d64a8433cb5dad5c7fccf8dce');
INSERT INTO `admin_role` VALUES ('ad313d38fe9447ce863fe8584743a010', 'cbe8356d64a8433cb5dad5c7fccf8dce');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `log_id` varchar(32) NOT NULL,
  `log_user` varchar(32) DEFAULT NULL,
  `log_time` datetime DEFAULT NULL,
  `log_ip` varchar(15) DEFAULT NULL,
  `log_action` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('024021f259444cbfbee7b40a2384d43b', 'admin', '2017-03-09 17:23:54', '127.0.0.1', '');
INSERT INTO `log` VALUES ('054d85212ecd4c7a9830075f1a5d764b', 'admin', '2017-03-31 09:54:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0912fa1c7f87460b9ff9fed5c515e29b', 'admin', '2017-05-05 13:47:14', '127.0.0.1', '');
INSERT INTO `log` VALUES ('09d3ea3e99574840a285fcf15642bdee', 'admin', '2017-05-09 16:31:40', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0b6a08723fae4ae2921886ec0a9c4dbc', 'admin', '2017-05-09 11:52:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0bc44c925bd0468c8fd7781d07e90b1d', 'admin', '2017-03-28 13:38:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1a727876dd324234b6f8eed3d1f5f524', 'admin', '2017-03-27 10:28:57', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1a839324ce544c219f017d41e98eb3e3', 'admin', '2017-03-30 17:09:16', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1b01125e81014f7889352e26add996c1', 'admin', '2017-03-28 10:59:16', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1bc633cb34134ec09342aa15492f1471', 'admin', '2017-03-28 10:45:03', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1c5433fce4f3452ca76b6e01bfd1d87b', 'admin', '2017-03-28 10:53:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1cdfc2ab89c448a19f8ccd022c8866f0', 'admin', '2017-05-10 16:54:38', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1ff5fd11c6d6424abe29553e2d85e9c0', 'admin', '2017-01-13 09:56:40', '127.0.0.1', '');
INSERT INTO `log` VALUES ('21f3566b599d4f20ade6cbe15bf3a522', 'admin', '2017-05-10 11:29:15', '127.0.0.1', '');
INSERT INTO `log` VALUES ('26494d3db75f4e7c987b59548931ea20', 'admin', '2017-03-30 13:35:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('2817a8eb85054b0ea0ca1a7330eb6e10', 'admin', '2017-05-09 15:37:16', '127.0.0.1', '');
INSERT INTO `log` VALUES ('2a8f34328e2348548f7e7d4930a47ecb', 'admin', '2017-04-10 16:58:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('2b2cf9707d65452596726d974713795a', 'admin', '2017-03-27 11:37:16', '127.0.0.1', '');
INSERT INTO `log` VALUES ('2dbaaa37e7374d26b5ea6d25f108054a', 'admin', '2017-01-13 14:01:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('2dc44a559a774c599911c7ce51c3e029', 'admin', '2017-03-09 17:15:01', '127.0.0.1', '');
INSERT INTO `log` VALUES ('33a12be5c426432f9bd5160c525c22ed', 'admin', '2017-03-30 13:57:53', '127.0.0.1', '');
INSERT INTO `log` VALUES ('399612844a16435396eea2632ef369c7', 'admin', '2017-05-09 16:16:03', '127.0.0.1', '');
INSERT INTO `log` VALUES ('3ab07587597947a8a13ea562babd46f4', 'admin', '2017-01-13 16:01:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('3f64ffa47aa04fc683da13135804bed1', 'admin', '2017-01-06 17:25:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('4091749e32ac4fcc9f367de19afd5ee5', 'admin', '2017-03-27 11:47:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('42717dfbc40a415d8e9760735e71ac24', 'admin', '2017-01-13 14:24:25', '127.0.0.1', '');
INSERT INTO `log` VALUES ('4d343a6054634dd488ab5a8f816f1449', 'admin', '2017-03-27 11:23:37', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5334039de3c44004bdfc07fc14ab15de', 'admin', '2017-03-27 11:35:29', '127.0.0.1', '');
INSERT INTO `log` VALUES ('54b7c2b45f774b6cb3bd43f7fe9533e9', 'admin', '2017-01-11 15:09:02', '127.0.0.1', '');
INSERT INTO `log` VALUES ('54c936b9689f4cae99d2bea7d24b586a', 'admin', '2017-01-13 14:29:47', '127.0.0.1', '');
INSERT INTO `log` VALUES ('58057203b4ef48f5b5ec96fc07fc49fe', 'admin', '2017-03-28 10:12:11', '127.0.0.1', '');
INSERT INTO `log` VALUES ('58c188f134f14e5d9f1cdacd0ca4bc74', 'admin', '2017-03-27 13:25:04', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5b4a6c2acdb444429efd526f28657e14', 'admin', '2017-05-05 15:19:14', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5b9eebdcee0346b79b1e50f39965c023', 'admin', '2017-05-02 17:32:31', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5c4e4c2229574eed9c47457250c5ca9a', 'admin', '2017-03-22 15:16:25', '127.0.0.1', '');
INSERT INTO `log` VALUES ('62dcaa14d8e6463880169f08dfaf52aa', 'admin', '2017-01-06 14:55:01', '127.0.0.1', '');
INSERT INTO `log` VALUES ('67bcab27df8f42efbfaa49876bb97ebe', 'admin', '2017-03-28 15:47:26', '127.0.0.1', '');
INSERT INTO `log` VALUES ('67e37b4a32db449eb410f37ad1b8bafe', 'admin', '2017-01-12 13:29:25', '127.0.0.1', '');
INSERT INTO `log` VALUES ('69244b03162d4496a52fd70dd95f230b', 'admin', '2017-03-09 15:52:09', '127.0.0.1', '');
INSERT INTO `log` VALUES ('69630868cc784b599d9da9aef94b1ccb', 'admin', '2017-03-27 11:43:01', '127.0.0.1', '');
INSERT INTO `log` VALUES ('6b65f3bb7f6a49a1892261f9164dfc5e', 'admin', '2017-03-27 13:02:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('6e7477b6c4d8413cac2d32d0684c3272', 'admin', '2017-03-27 11:13:02', '127.0.0.1', '');
INSERT INTO `log` VALUES ('726dec658b0943c49300138b1fafded4', 'admin', '2017-05-09 15:10:20', '127.0.0.1', '');
INSERT INTO `log` VALUES ('72a43c18f93e4022a60492bd37499e5a', 'admin', '2017-03-28 09:43:02', '127.0.0.1', '');
INSERT INTO `log` VALUES ('74f4c2157ae4419a98d466a92daf1f03', 'admin', '2017-05-10 16:31:57', '127.0.0.1', '');
INSERT INTO `log` VALUES ('79050be5a1704dcbb54125dfef3bcdb7', 'admin', '2017-03-02 14:08:10', '127.0.0.1', '');
INSERT INTO `log` VALUES ('7bf258e0203b477a8f5b887bc4ae06d3', 'admin', '2017-05-02 17:31:54', '127.0.0.1', '');
INSERT INTO `log` VALUES ('7e748e08ee3f419bbb858949110c39ee', 'admin', '2017-03-28 10:50:00', '127.0.0.1', '');
INSERT INTO `log` VALUES ('7fdb1d9ae8af4fdba39d4ae4f12e1890', 'admin', '2017-05-08 11:39:31', '127.0.0.1', '');
INSERT INTO `log` VALUES ('84224102927f47f4ad38a702543612da', 'admin', '2017-03-30 13:31:00', '127.0.0.1', '');
INSERT INTO `log` VALUES ('84d6f36763324c0cb17cbba37485fb6f', 'admin', '2017-05-04 14:29:08', '127.0.0.1', '');
INSERT INTO `log` VALUES ('84f3485a06ce43ea85b86a3b12bfee36', 'admin', '2017-01-06 14:12:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('882769bab0e143849c03dac9f7f50107', 'admin', '2017-03-31 13:43:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('88df3ef4f24d464e91d90aab84a02bfc', 'admin', '2017-03-27 13:32:45', '127.0.0.1', '');
INSERT INTO `log` VALUES ('8b1c213feab74be2a9ffcb8cee20b985', 'admin', '2017-03-27 10:45:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('8df9d5a62ebc439aa7f3c04e69b07326', 'admin', '2017-05-04 15:32:59', '127.0.0.1', '');
INSERT INTO `log` VALUES ('98e9769eaebc46ab8968d7ecabc7f884', 'admin', '2017-05-08 16:51:45', '127.0.0.1', '');
INSERT INTO `log` VALUES ('9cd0265d636c44aea5dc2ef462826553', 'admin', '2017-01-13 11:26:45', '127.0.0.1', '');
INSERT INTO `log` VALUES ('9dc4fd4a0bda4e2aa68c2d4768ea723b', 'admin', '2017-05-08 11:29:40', '127.0.0.1', '');
INSERT INTO `log` VALUES ('9f760ed834744a9793c87575cdb749b5', 'flyshy', '2017-05-10 17:02:59', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a09269ea2819422cafaeb1dff052b4b0', 'admin', '2017-03-09 15:40:47', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a3d113bc6d8449a98b9297b9be2dcb94', 'admin', '2017-05-10 16:37:20', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a8be96493a1c4d2192c6addee2e34d67', 'admin', '2017-03-22 14:12:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('aa4eae8491f24e74b288169ec3f7a041', 'admin', '2017-03-09 15:43:33', '127.0.0.1', '');
INSERT INTO `log` VALUES ('aa9ef3b1ee3d45b4ae35ad9c2878f62a', 'admin', '2017-03-27 11:32:30', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b4399fc7cd1f4f0da2c7976e8b92acb5', 'admin', '2017-05-05 15:26:29', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b8f044f62d844a5e9ba70604c88cfdc2', 'admin', '2017-03-09 15:36:33', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b928cb7db9a345dc9246cc51e342f7cc', 'admin', '2017-03-27 12:52:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bbb2d031687c4dd1971427f42d0ea0ff', 'admin', '2017-03-27 12:40:08', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bccc26f661544b86bcc27cb0c9d15717', 'admin', '2017-01-20 11:11:47', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bd41a70b6da1430a8002123acf18aab0', 'admin', '2017-01-20 13:59:41', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c48ae6f5241240538cedbc7354600b8c', 'admin', '2017-03-30 14:30:55', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c7fee8fcfac64eeeb7913d0188037925', 'admin', '2017-03-13 17:33:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c81a4be560c446188035d9b73e8270a9', 'admin', '2017-05-03 10:40:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c880c55225ac468f9a524c807eff9c85', 'admin', '2017-03-29 15:59:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c9d4581a85194f51ac70a4a260f52649', 'admin', '2017-01-06 16:45:41', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cb746a7b4c614dcab2aa436383923541', 'flyshy', '2017-05-09 16:05:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cbd0f1b689434170abf3a029cc0adfa2', 'admin', '2017-03-30 14:10:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cd8e06c8ab774613930741d1758bd80a', 'admin', '2017-05-05 12:57:52', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cf1b0feabf5f4ec9b9fdf5a744f399e2', 'admin', '2017-05-09 16:20:23', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d35d4eb26d0645cd9682d78feb23ec9f', 'admin', '2017-03-30 13:20:08', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d39802fbcab946ef93b33b6018824a13', 'admin', '2017-03-31 11:40:41', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d82e3ceb83af499b8c35908964ed9fc9', 'admin', '2017-05-08 16:50:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d9279a72a58c4c99a9a3044a87a05bc4', 'admin', '2017-03-28 10:03:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('defb67f4b4f546be88c6de45d212bc13', 'admin', '2017-03-27 11:14:34', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e32b724bdaa54f10baa5e90c9950ac8a', 'admin', '2017-03-30 15:01:18', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e6fc377beaac4a6fa673c640b99dd047', 'admin', '2017-03-29 15:42:22', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e834f3e28fc44aef97305918d743de3a', 'admin', '2017-03-31 09:54:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e83aec2de702475d9e48b6df724f49b7', 'admin', '2017-03-27 11:59:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ea40bda49f0a443ab6e2fdd226899964', 'admin', '2017-01-13 15:16:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ea796e16ab3e4d12b9d6c6acf7c61f0e', 'admin', '2017-05-09 15:07:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ef6f15960b3b4005888e32fae06c7196', 'admin', '2017-01-13 11:30:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f0f96e8dd3f34f0e91278f241a4cb41e', 'admin', '2017-05-08 16:48:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f235144fdd6e479a8c9981eadcbd38c5', 'admin', '2017-03-09 17:10:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f2c68bad14484810bdea767127691d2d', 'admin', '2017-03-30 11:30:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f64ce62722dd46d59194df0bf01d30d5', 'admin', '2017-01-13 10:09:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f832a07adfa74612947a592177ff2dd9', 'admin', '2017-03-22 11:30:15', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f8387964c5904d86a2d4effbefabbe97', 'admin', '2017-03-30 10:01:40', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f872a6d252714453b22c45c2ab9c6780', 'admin', '2017-03-09 17:17:07', '127.0.0.1', '');
INSERT INTO `log` VALUES ('fe21c16bfc2c466e9714b2f698496fd4', 'admin', '2017-03-28 11:15:48', '127.0.0.1', '');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `uid` varchar(32) NOT NULL,
  `account` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `salt` varchar(32) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`uid`,`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('3eb011e6638b4184aef77c3b095883df', 'flyshy', 'b2e61c4d4362ac061ad3bfe115b7b700', '6cfa0ce808a2ff68e61d248af75243a7', '1', '2017-03-14 09:44:35', '2017-03-14 09:44:35');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_type` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '资源类型，菜单或都按钮(menu,button)',
  `menu_url` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_code` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `parent_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `parent_ids` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `child_num` int(10) NOT NULL DEFAULT '0',
  `listorder` int(10) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('00dc5c51e4824f49a30013385f680b0c', '日志管理', 'auth', '/console/log/index', 'log:index', 'e5f52fe2115e46229c60803e478d2e9a', null, '0', '1', '2017-01-06 14:11:23', '2017-05-08 14:55:21');
INSERT INTO `menu` VALUES ('1cc3d9ad04e4424db1bb086d1678925e', '菜单删除', 'auth', '/console/menu/delete', 'menu:delete', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2017-05-10 16:45:30', '2017-05-10 16:45:30');
INSERT INTO `menu` VALUES ('2191c9efc2fa431bb427b81ad938e8aa', '角色保存', 'auth', '/console/role/save', 'role:save', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:41:21', '2017-05-10 16:41:21');
INSERT INTO `menu` VALUES ('362923d31e064f84adb8c23ba91e54d8', '管理员编辑', 'auth', '/console/admin/from', 'admin:edit', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-08 14:57:39', '2017-05-10 16:40:47');
INSERT INTO `menu` VALUES ('3ac96215e82f40b5bfe442e6828641df', '系统管理', 'menu', '/console/system/admin', 'system:admin', '0', null, '3', '1', '2016-12-07 16:00:00', '2017-05-10 16:46:27');
INSERT INTO `menu` VALUES ('6580896645d046a0acf3c1194d7bbf8e', '管理员删除', 'menu', '/console/admin/delete', 'admin:delete', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-10 16:39:44', '2017-05-10 16:39:44');
INSERT INTO `menu` VALUES ('6cda978dc9404ba2bf5854b74735b0bc', '角色管理', 'auth', '/console/role/index', 'role:index', '3ac96215e82f40b5bfe442e6828641df', null, '4', '2', '2016-12-07 16:47:40', '2016-12-07 16:47:40');
INSERT INTO `menu` VALUES ('736bdf0b9aec4c59928a530e34bd9aad', '菜单管理', 'auth', '/console/menu/index', 'menu:index', '3ac96215e82f40b5bfe442e6828641df', null, '3', '3', '2016-12-07 16:50:17', '2016-12-07 16:50:17');
INSERT INTO `menu` VALUES ('85dad2bd9023451fab632dcfc4357d3b', '管理员保存', 'auth', '/console/admin/save', 'admin:save', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-10 16:38:07', '2017-05-10 16:41:00');
INSERT INTO `menu` VALUES ('8a653e3fb15642d9be6aad13b02009fb', '角色授权', 'auth', '/console/role/grant', 'role:grant', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:42:37', '2017-05-10 16:42:37');
INSERT INTO `menu` VALUES ('984909260a06410d9be37c300e3df09d', '会员管理', 'menu', '/console/member/default', 'member:default', '0', null, '1', '0', '2017-05-10 16:50:16', '2017-05-10 16:51:06');
INSERT INTO `menu` VALUES ('9f41af1454d046b596023a2822c5078c', '角色编辑', 'auth', '/console/role/from', 'role:edit', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-08 14:59:25', '2017-05-08 14:59:25');
INSERT INTO `menu` VALUES ('aab7966c97db4643a36cb5afa24be38b', '角色删除', 'menu', '/console/role/delete', 'role:delete', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:43:37', '2017-05-10 16:43:37');
INSERT INTO `menu` VALUES ('c5cca135ee534bfeb482fb04b9311982', '菜单编辑', 'auth', '/console/menu/from', 'menu:from', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2016-12-07 16:51:31', '2017-05-08 15:00:02');
INSERT INTO `menu` VALUES ('e0dde3b9227c471eb3bd2ba0a7fab131', '管理员管理', 'auth', '/console/admin/index', 'admin:index', '3ac96215e82f40b5bfe442e6828641df', null, '3', '1', '2016-12-07 16:45:47', '2017-05-10 16:39:08');
INSERT INTO `menu` VALUES ('e5f52fe2115e46229c60803e478d2e9a', '扩展设置', 'menu', '/console/system/setting', 'system:setting', '0', null, '1', '3', '2016-12-07 16:36:42', '2017-05-10 16:50:00');
INSERT INTO `menu` VALUES ('e85b2fb3e6ee4d0a9711c577bc842821', '会员管理', 'auth', '/console/member/index', 'member:index', '984909260a06410d9be37c300e3df09d', null, '0', '0', '2017-05-10 16:51:20', '2017-05-10 16:51:20');
INSERT INTO `menu` VALUES ('f4237d06c0c94906bdc04f5ed19cbaeb', '菜单保存', 'auth', '/console/menu/save', 'menu:save', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2017-05-10 16:44:51', '2017-05-10 16:44:51');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `role_desc` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_unique` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '超级管理员', '超级管理员', '1', '2016-12-07 08:53:57', '2016-12-07 11:49:16');
INSERT INTO `role` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '普通管理员', '普通管理员', '1', '2016-12-07 13:21:21', '2017-05-05 12:58:38');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `role_menu_foreign` (`menu_id`) USING BTREE,
  CONSTRAINT `fk_ref_menu` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`),
  CONSTRAINT `fk_ref_role2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '00dc5c51e4824f49a30013385f680b0c');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '1cc3d9ad04e4424db1bb086d1678925e');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '2191c9efc2fa431bb427b81ad938e8aa');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '362923d31e064f84adb8c23ba91e54d8');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '3ac96215e82f40b5bfe442e6828641df');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '6580896645d046a0acf3c1194d7bbf8e');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '6cda978dc9404ba2bf5854b74735b0bc');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '736bdf0b9aec4c59928a530e34bd9aad');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '85dad2bd9023451fab632dcfc4357d3b');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '8a653e3fb15642d9be6aad13b02009fb');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '984909260a06410d9be37c300e3df09d');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '984909260a06410d9be37c300e3df09d');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '9f41af1454d046b596023a2822c5078c');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', 'aab7966c97db4643a36cb5afa24be38b');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', 'c5cca135ee534bfeb482fb04b9311982');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', 'e0dde3b9227c471eb3bd2ba0a7fab131');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', 'e5f52fe2115e46229c60803e478d2e9a');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'e5f52fe2115e46229c60803e478d2e9a');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'e85b2fb3e6ee4d0a9711c577bc842821');
INSERT INTO `role_menu` VALUES ('36f1dd1296674fc08484c5abf6a5806b', 'f4237d06c0c94906bdc04f5ed19cbaeb');
