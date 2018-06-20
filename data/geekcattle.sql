/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : geekcattle

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-06-20 17:16:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE "admin" (
  "uid" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  "username" varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  "password" varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  "state" tinyint(1) NOT NULL DEFAULT '1',
  "salt" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  "is_system" tinyint(1) NOT NULL DEFAULT '0',
  "created_at" datetime NOT NULL,
  "updated_at" datetime NOT NULL,
  PRIMARY KEY ("uid"),
  UNIQUE KEY "unique_username" ("username") USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1e67f167d99c43f897ec6566043ad6ec', 'flyshy', 'eb3a90502fbe02c2d8de91e0aa307268', '1', '5016b9942433201bf3fe61992eacba71', '0', '2016-12-07 13:24:17', '2017-11-02 09:27:37');
INSERT INTO `admin` VALUES ('ad313d38fe9447ce863fe8584743a010', 'admin', 'c5941c5f3bc693a75e6e863bd2c55ce3', '1', '1ab6d62faa91ae7deec76d6f13ef1600', '1', '2016-12-06 11:16:51', '2017-05-11 13:59:25');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE "admin_role" (
  "admin_id" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  "role_id" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY ("admin_id","role_id"),
  KEY "admin_role_foreign" ("role_id") USING BTREE,
  CONSTRAINT "admin_role_ibfk_1" FOREIGN KEY ("admin_id") REFERENCES "admin" ("uid"),
  CONSTRAINT "admin_role_ibfk_2" FOREIGN KEY ("role_id") REFERENCES "role" ("role_id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1e67f167d99c43f897ec6566043ad6ec', 'cbe8356d64a8433cb5dad5c7fccf8dce');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE "log" (
  "log_id" varchar(32) NOT NULL,
  "log_user" varchar(32) DEFAULT NULL,
  "log_time" datetime DEFAULT NULL,
  "log_ip" varchar(15) DEFAULT NULL,
  "log_action" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("log_id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

INSERT INTO `log` VALUES ('a7120b455e4a47cebe7638dfd7c8b97f', 'admin', '2018-06-20 13:06:12', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a75d456b409a4effb4d327dc76872c0f', 'admin', '2018-06-19 10:36:50', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a7b472be86b54c25aa30a5a12da9a236', 'admin', '2017-11-08 16:13:24', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a8be96493a1c4d2192c6addee2e34d67', 'admin', '2017-03-22 14:12:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('aa4eae8491f24e74b288169ec3f7a041', 'admin', '2017-03-09 15:43:33', '127.0.0.1', '');
INSERT INTO `log` VALUES ('aa9ef3b1ee3d45b4ae35ad9c2878f62a', 'admin', '2017-03-27 11:32:30', '127.0.0.1', '');
INSERT INTO `log` VALUES ('aae3edd3bf3a43aba61be99633377043', 'admin', '2017-05-11 13:53:14', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ac836f353ce94486ad3db1271bc59892', 'admin', '2017-12-06 14:26:29', '127.0.0.1', '');
INSERT INTO `log` VALUES ('aded3995156446e698ccac3a353af47a', 'admin', '2018-06-20 11:51:40', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b167154cce404d16aa35bd519f9b573d', 'admin', '2017-12-05 10:22:34', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b3c7a18fcd4145688e656539f4bd5491', 'admin', '2017-11-08 16:23:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b4399fc7cd1f4f0da2c7976e8b92acb5', 'admin', '2017-05-05 15:26:29', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b8f044f62d844a5e9ba70604c88cfdc2', 'admin', '2017-03-09 15:36:33', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b8f84af2fdef4b548f2f710dac3172fc', 'admin', '2017-12-05 14:31:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b928cb7db9a345dc9246cc51e342f7cc', 'admin', '2017-03-27 12:52:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b92a9d001e2d4244ab449e315b7957d2', 'admin', '2018-01-02 15:03:23', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bbb2d031687c4dd1971427f42d0ea0ff', 'admin', '2017-03-27 12:40:08', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bccc26f661544b86bcc27cb0c9d15717', 'admin', '2017-01-20 11:11:47', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bd41a70b6da1430a8002123acf18aab0', 'admin', '2017-01-20 13:59:41', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bf675b168c404d6fa321fb76988d76d1', 'admin', '2017-11-13 10:37:16', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c08f970bc33a47638762f34333328605', 'admin', '2018-02-02 16:35:14', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c48ae6f5241240538cedbc7354600b8c', 'admin', '2017-03-30 14:30:55', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c62fab3f9c2d4ddc8f998d8b04252367', 'admin', '2017-11-02 09:26:50', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c7fee8fcfac64eeeb7913d0188037925', 'admin', '2017-03-13 17:33:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c81a4be560c446188035d9b73e8270a9', 'admin', '2017-05-03 10:40:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c880c55225ac468f9a524c807eff9c85', 'admin', '2017-03-29 15:59:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c9d4581a85194f51ac70a4a260f52649', 'admin', '2017-01-06 16:45:41', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cb746a7b4c614dcab2aa436383923541', 'flyshy', '2017-05-09 16:05:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cbd0f1b689434170abf3a029cc0adfa2', 'admin', '2017-03-30 14:10:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cccc2e3b2e1b4b92b57bcd2b9a0895fc', 'admin', '2017-05-11 14:17:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cd28df269475442487aaf2a2ec1ffe49', 'admin', '2017-11-02 09:30:35', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cd8e06c8ab774613930741d1758bd80a', 'admin', '2017-05-05 12:57:52', '127.0.0.1', '');
INSERT INTO `log` VALUES ('cf1b0feabf5f4ec9b9fdf5a744f399e2', 'admin', '2017-05-09 16:20:23', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d35d4eb26d0645cd9682d78feb23ec9f', 'admin', '2017-03-30 13:20:08', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d39802fbcab946ef93b33b6018824a13', 'admin', '2017-03-31 11:40:41', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d82e3ceb83af499b8c35908964ed9fc9', 'admin', '2017-05-08 16:50:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d9279a72a58c4c99a9a3044a87a05bc4', 'admin', '2017-03-28 10:03:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('defb67f4b4f546be88c6de45d212bc13', 'admin', '2017-03-27 11:14:34', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e0b2f9c277324cb88aaca0ebfbc567a1', 'admin', '2017-11-02 11:21:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e1166b448304455ea26bba6c25438340', 'admin', '2017-12-05 10:36:34', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e11673c31bf0407ab6a41e7ac8c059da', 'admin', '2018-06-13 14:48:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e140e914809f470bb35a25ddca754689', 'admin', '2017-12-05 13:31:06', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e202939249a1446c85a3b9524cd706ea', 'admin', '2017-12-05 10:21:01', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e32b724bdaa54f10baa5e90c9950ac8a', 'admin', '2017-03-30 15:01:18', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e6fc377beaac4a6fa673c640b99dd047', 'admin', '2017-03-29 15:42:22', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e834f3e28fc44aef97305918d743de3a', 'admin', '2017-03-31 09:54:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e83aec2de702475d9e48b6df724f49b7', 'admin', '2017-03-27 11:59:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ea40bda49f0a443ab6e2fdd226899964', 'admin', '2017-01-13 15:16:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ea796e16ab3e4d12b9d6c6acf7c61f0e', 'admin', '2017-05-09 15:07:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('eb5e27d3d71d4bed954561b78ceabf7c', 'admin', '2018-06-13 13:58:06', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ee084caf2b654de5a279b0931f971417', 'admin', '2017-12-05 10:00:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ee3ee719f4a34537a8846a8617469ebb', 'admin', '2017-12-05 10:23:04', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ef6f15960b3b4005888e32fae06c7196', 'admin', '2017-01-13 11:30:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f0f96e8dd3f34f0e91278f241a4cb41e', 'admin', '2017-05-08 16:48:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f168f693f6594a63aea643abae8bad96', 'admin', '2017-12-05 13:26:47', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f235144fdd6e479a8c9981eadcbd38c5', 'admin', '2017-03-09 17:10:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f27215ea8fbb4ba2abdafbaed7ea2dc6', 'admin', '2017-11-13 10:40:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f2c68bad14484810bdea767127691d2d', 'admin', '2017-03-30 11:30:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f3d8298c400d4aeda9386804fbb2b493', 'admin', '2017-12-05 10:41:11', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f5c035b9b4dc440cbf6a80c97a67cdde', 'flyshy', '2017-05-11 13:46:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f64ce62722dd46d59194df0bf01d30d5', 'admin', '2017-01-13 10:09:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f663477fccd4424daf6436316fb949c9', 'admin', '2017-12-07 08:32:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f7d5248eb4e84e2083950b7903c78fc8', 'admin', '2017-11-02 08:23:29', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f832a07adfa74612947a592177ff2dd9', 'admin', '2017-03-22 11:30:15', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f8387964c5904d86a2d4effbefabbe97', 'admin', '2017-03-30 10:01:40', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f872a6d252714453b22c45c2ab9c6780', 'admin', '2017-03-09 17:17:07', '127.0.0.1', '');
INSERT INTO `log` VALUES ('fe21c16bfc2c466e9714b2f698496fd4', 'admin', '2017-03-28 11:15:48', '127.0.0.1', '');
INSERT INTO `log` VALUES ('fefbf5c03a25429b88a14ed90b147f88', 'admin', '2017-12-06 14:11:25', '127.0.0.1', '');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE "member" (
  "uid" varchar(32) NOT NULL,
  "account" varchar(32) NOT NULL,
  "password" varchar(100) NOT NULL,
  "state" tinyint(1) NOT NULL DEFAULT '1',
  "created_at" datetime NOT NULL,
  "updated_at" datetime NOT NULL,
  PRIMARY KEY ("uid","account")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('3eb011e6638b4184aef77c3b095883df', 'guest', '$2a$10$MPclaL4uacoMimw3Ia4xfefaRKUTrT.j7FyF4SnpuDZf3ugicOv9K', '1', '2017-03-14 09:44:35', '2017-03-14 09:44:35');
INSERT INTO `member` VALUES ('5da7ec20ffb74f62b0f4554bdc0a8124', 'test', '$2a$10$Xcq8UiTEVNvov1VYE87ikOnBi4jxap09ByJKKDqnSDz731j/VdmZq', '1', '2017-11-02 08:27:24', '2017-11-02 08:27:24');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE "menu" (
  "menu_id" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  "menu_name" varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  "menu_type" varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '资源类型，菜单或都按钮(menu,button)',
  "menu_url" varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  "menu_code" varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  "parent_id" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  "parent_ids" varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  "child_num" int(10) NOT NULL DEFAULT '0',
  "listorder" int(10) NOT NULL DEFAULT '0',
  "created_at" datetime NOT NULL,
  "updated_at" datetime NOT NULL,
  PRIMARY KEY ("menu_id")
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
INSERT INTO `menu` VALUES ('984909260a06410d9be37c300e3df09d', '会员管理', 'menu', '/console/member/default', 'member:default', '0', null, '1', '0', '2017-05-10 16:50:16', '2018-06-13 14:36:46');
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
CREATE TABLE "role" (
  "role_id" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  "role_name" varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  "role_desc" varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  "enable" tinyint(1) NOT NULL DEFAULT '1',
  "created_at" datetime NOT NULL,
  "updated_at" datetime NOT NULL,
  PRIMARY KEY ("role_id"),
  UNIQUE KEY "role_name_unique" ("role_name") USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '系统管理员', '系统管理员', '1', '2016-12-07 08:53:57', '2017-05-11 13:59:03');
INSERT INTO `role` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '普通管理员', '普通管理员', '1', '2016-12-07 13:21:21', '2017-05-05 12:58:38');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE "role_menu" (
  "role_id" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  "menu_id" varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY ("role_id","menu_id"),
  KEY "role_menu_foreign" ("menu_id") USING BTREE,
  CONSTRAINT "role_menu_ibfk_1" FOREIGN KEY ("menu_id") REFERENCES "menu" ("menu_id"),
  CONSTRAINT "role_menu_ibfk_2" FOREIGN KEY ("role_id") REFERENCES "role" ("role_id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '00dc5c51e4824f49a30013385f680b0c');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '984909260a06410d9be37c300e3df09d');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'e5f52fe2115e46229c60803e478d2e9a');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'e85b2fb3e6ee4d0a9711c577bc842821');
