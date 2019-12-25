/*
 Navicat Premium Data Transfer

 Source Server         : 88
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : mmall

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 24/12/2019 21:52:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '外键用户id',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '商品id 外键',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `checked` int(11) NULL DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 178 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (174, 24, 26, 0, 1, '2019-12-23 11:47:10', '2019-12-23 11:47:10');
INSERT INTO `cart` VALUES (175, 24, 27, 0, 1, '2019-12-23 11:47:12', '2019-12-23 11:47:12');
INSERT INTO `cart` VALUES (176, 24, 28, 50, 1, '2019-12-23 11:47:14', '2019-12-23 11:47:14');
INSERT INTO `cart` VALUES (177, 24, 29, 50, 1, '2019-12-23 11:47:16', '2019-12-23 11:47:16');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '类别状态1-正常,2-已废弃',
  `sort_order` int(4) NULL DEFAULT NULL COMMENT '排序编号,同类展示顺序,数值相等则自然排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100036 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (100001, 100002, '家用电器', 1, NULL, '2017-03-25 16:46:00', '2017-03-25 16:46:00');
INSERT INTO `category` VALUES (100002, 100001, '数码3C', 1, NULL, '2017-03-25 16:46:21', '2017-03-25 16:46:21');
INSERT INTO `category` VALUES (100003, 0, '服装箱包', 1, NULL, '2017-03-25 16:49:53', '2017-03-25 16:49:53');
INSERT INTO `category` VALUES (100004, 0, '食品生鲜', 1, NULL, '2017-03-25 16:50:19', '2017-03-25 16:50:19');
INSERT INTO `category` VALUES (100005, 0, '酒水饮料', 1, NULL, '2017-03-25 16:50:29', '2017-03-25 16:50:29');
INSERT INTO `category` VALUES (100006, 100001, '冰箱', 1, NULL, '2017-03-25 16:52:15', '2017-03-25 16:52:15');
INSERT INTO `category` VALUES (100007, 100001, '电视', 1, NULL, '2017-03-25 16:52:26', '2017-03-25 16:52:26');
INSERT INTO `category` VALUES (100008, 100001, '洗衣机', 1, NULL, '2017-03-25 16:52:39', '2017-03-25 16:52:39');
INSERT INTO `category` VALUES (100009, 100001, '空调', 1, NULL, '2017-03-25 16:52:45', '2017-03-25 16:52:45');
INSERT INTO `category` VALUES (100010, 100001, '电热水器', 1, NULL, '2017-03-25 16:52:54', '2017-03-25 16:52:54');
INSERT INTO `category` VALUES (100011, 100002, '电脑', 1, NULL, '2017-03-25 16:53:18', '2017-03-25 16:53:18');
INSERT INTO `category` VALUES (100012, 100002, '手机', 1, NULL, '2017-03-25 16:53:27', '2017-03-25 16:53:27');
INSERT INTO `category` VALUES (100013, 100002, '平板电脑', 1, NULL, '2017-03-25 16:53:35', '2017-03-25 16:53:35');
INSERT INTO `category` VALUES (100014, 100002, '数码相机', 1, NULL, '2017-03-25 16:53:56', '2017-03-25 16:53:56');
INSERT INTO `category` VALUES (100015, 100002, '3C配件', 1, NULL, '2017-03-25 16:54:07', '2017-03-25 16:54:07');
INSERT INTO `category` VALUES (100016, 100003, '女装', 1, NULL, '2017-03-25 16:54:44', '2017-03-25 16:54:44');
INSERT INTO `category` VALUES (100017, 100003, '帽子', 1, NULL, '2017-03-25 16:54:51', '2017-03-25 16:54:51');
INSERT INTO `category` VALUES (100018, 100003, '旅行箱', 1, NULL, '2017-03-25 16:55:02', '2017-03-25 16:55:02');
INSERT INTO `category` VALUES (100019, 100003, '手提包', 1, NULL, '2017-03-25 16:55:09', '2017-03-25 16:55:09');
INSERT INTO `category` VALUES (100020, 100003, '保暖内衣', 1, NULL, '2017-03-25 16:55:18', '2017-03-25 16:55:18');
INSERT INTO `category` VALUES (100021, 100004, '零食', 1, NULL, '2017-03-25 16:55:30', '2017-03-25 16:55:30');
INSERT INTO `category` VALUES (100022, 100004, '生鲜', 1, NULL, '2017-03-25 16:55:37', '2017-03-25 16:55:37');
INSERT INTO `category` VALUES (100023, 100004, '半成品菜', 1, NULL, '2017-03-25 16:55:47', '2017-03-25 16:55:47');
INSERT INTO `category` VALUES (100024, 100004, '速冻食品', 1, NULL, '2017-03-25 16:55:56', '2017-03-25 16:55:56');
INSERT INTO `category` VALUES (100025, 100004, '进口食品', 1, NULL, '2017-03-25 16:56:06', '2017-03-25 16:56:06');
INSERT INTO `category` VALUES (100026, 100005, '白酒', 1, NULL, '2017-03-25 16:56:22', '2017-03-25 16:56:22');
INSERT INTO `category` VALUES (100027, 100005, '红酒', 1, NULL, '2017-03-25 16:56:30', '2017-03-25 16:56:30');
INSERT INTO `category` VALUES (100028, 100005, '饮料', 1, NULL, '2017-03-25 16:56:37', '2017-03-25 16:56:37');
INSERT INTO `category` VALUES (100029, 100005, '调制鸡尾酒', 1, NULL, '2017-03-25 16:56:45', '2017-03-25 16:56:45');
INSERT INTO `category` VALUES (100030, 100005, '进口洋酒', 1, NULL, '2017-03-25 16:57:05', '2017-03-25 16:57:05');
INSERT INTO `category` VALUES (100034, 100001, '奥力给', 1, NULL, '2019-12-06 03:38:13', '2019-12-06 03:38:13');
INSERT INTO `category` VALUES (100035, 100001, 'YY', 1, NULL, '2019-12-06 03:44:13', '2019-12-06 03:44:13');

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` int(11) NULL DEFAULT NULL,
  `order_no` bigint(20) NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_no_index`(`order_no`) USING BTREE,
  INDEX `order_no_user_id_index`(`user_id`, `order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (113, 1, 1491753014256, 26, 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', 6999.00, 2, 13998.00, '2017-04-09 23:50:14', '2017-04-09 23:50:14');
INSERT INTO `item` VALUES (114, 1, 1491830695216, 26, 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', 6999.00, 2, 13998.00, '2017-04-10 21:24:55', '2017-04-10 21:24:55');
INSERT INTO `item` VALUES (115, 1, 1492089528889, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 1, 3299.00, '2017-04-13 21:18:48', '2017-04-13 21:18:48');
INSERT INTO `item` VALUES (116, 1, 1492090946105, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', 4299.00, 2, 8598.00, '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `item` VALUES (117, 1, 1492090946105, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', 1999.00, 1, 1999.00, '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `item` VALUES (118, 1, 1492090946105, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 1, 3299.00, '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `item` VALUES (119, 1, 1492090946105, 26, 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', 6999.00, 2, 13998.00, '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `item` VALUES (120, 1, 1492091003128, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 2, 6598.00, '2017-04-13 21:43:23', '2017-04-13 21:43:23');
INSERT INTO `item` VALUES (121, 1, 1492091003128, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', 1999.00, 1, 1999.00, '2017-04-13 21:43:23', '2017-04-13 21:43:23');
INSERT INTO `item` VALUES (122, 1, 1492091051313, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', 1999.00, 1, 1999.00, '2017-04-13 21:44:11', '2017-04-13 21:44:11');
INSERT INTO `item` VALUES (123, 1, 1492091061513, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 2, 6598.00, '2017-04-13 21:44:21', '2017-04-13 21:44:21');
INSERT INTO `item` VALUES (124, 1, 1492091069563, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 1, 3299.00, '2017-04-13 21:44:29', '2017-04-13 21:44:29');
INSERT INTO `item` VALUES (125, 1, 1492091076073, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', 4299.00, 1, 4299.00, '2017-04-13 21:44:36', '2017-04-13 21:44:36');
INSERT INTO `item` VALUES (126, 1, 1492091083720, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 1, 3299.00, '2017-04-13 21:44:43', '2017-04-13 21:44:43');
INSERT INTO `item` VALUES (127, 1, 1492091089794, 26, 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', 6999.00, 1, 6999.00, '2017-04-13 21:44:49', '2017-04-13 21:44:49');
INSERT INTO `item` VALUES (128, 1, 1492091096400, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 2, 6598.00, '2017-04-13 21:44:56', '2017-04-13 21:44:56');
INSERT INTO `item` VALUES (129, 1, 1492091102371, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 1, 3299.00, '2017-04-13 21:45:02', '2017-04-13 21:45:02');
INSERT INTO `item` VALUES (130, 1, 1492091110004, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', 4299.00, 2, 8598.00, '2017-04-13 21:45:09', '2017-04-13 21:45:09');
INSERT INTO `item` VALUES (131, 1, 1492091141269, 26, 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', 6999.00, 1, 6999.00, '2017-04-13 21:45:41', '2017-04-13 21:45:41');
INSERT INTO `item` VALUES (132, 1, 1492091141269, 27, 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', 3299.00, 1, 3299.00, '2017-04-13 21:45:41', '2017-04-13 21:45:41');
INSERT INTO `item` VALUES (133, 1, 1492091141269, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', 4299.00, 2, 8598.00, '2017-04-13 21:45:41', '2017-04-13 21:45:41');
INSERT INTO `item` VALUES (134, 1, 1492091141269, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', 1999.00, 2, 3998.00, '2017-04-13 21:45:41', '2017-04-13 21:45:41');
INSERT INTO `item` VALUES (135, 24, 1576918046745, 26, '三星洗衣机', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1000.59, 119, 119070.21, '2019-12-21 21:00:53', '2019-12-22 21:00:40');
INSERT INTO `item` VALUES (136, 24, 1576918046745, 27, '三星洗衣机', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1000.63, 50, 50031.50, '2019-12-21 21:00:55', '2019-12-22 21:00:45');
INSERT INTO `item` VALUES (137, 24, 1576918046745, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1999.82, 20, 39996.40, '2019-12-21 21:00:59', '2019-12-22 21:00:47');
INSERT INTO `item` VALUES (138, 24, 1576918046745, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 4299.00, 50, 214950.00, '2019-12-21 21:01:01', '2019-12-22 21:00:49');
INSERT INTO `item` VALUES (139, 24, 1576918633736, 26, '三星洗衣机', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1000.59, 31, 31018.29, NULL, NULL);
INSERT INTO `item` VALUES (140, 24, 1576918633736, 27, '三星洗衣机', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1000.63, 50, 50031.50, NULL, NULL);
INSERT INTO `item` VALUES (141, 24, 1576918633736, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1999.82, 50, 99991.00, NULL, NULL);
INSERT INTO `item` VALUES (142, 24, 1576918633736, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 4299.00, 50, 214950.00, NULL, NULL);
INSERT INTO `item` VALUES (143, 24, 1576933353501, 26, '三星洗衣机', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1000.59, 0, 0.00, NULL, NULL);
INSERT INTO `item` VALUES (144, 24, 1576933353501, 27, '三星洗衣机', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1000.63, 0, 0.00, NULL, NULL);
INSERT INTO `item` VALUES (145, 24, 1576933353501, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1999.82, 50, 99991.00, NULL, NULL);
INSERT INTO `item` VALUES (146, 24, 1576933353501, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 4299.00, 50, 214950.00, NULL, NULL);
INSERT INTO `item` VALUES (147, 24, 1576933353501, 32, '三星洗衣机', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1000.00, 100, 100000.00, NULL, NULL);
INSERT INTO `item` VALUES (148, 24, 1576933566903, 26, '三星洗衣机', 'localhost:8081/uploads/8ea43e37338d49b591752c9f7761feb8_QQ头像2.jpg', 1000.59, 0, 0.00, '2019-12-21 21:06:07', '2019-12-21 21:06:07');
INSERT INTO `item` VALUES (149, 24, 1576933566903, 27, '三星洗衣机', 'localhost:8081/uploads/36d8d7acba3c4e01be7bdb580d04a1a5_灯.jpg', 1000.63, 0, 0.00, '2019-12-21 21:06:07', '2019-12-21 21:06:07');
INSERT INTO `item` VALUES (150, 24, 1576933566903, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 1999.82, 50, 99991.00, '2019-12-21 21:06:07', '2019-12-21 21:06:07');
INSERT INTO `item` VALUES (151, 24, 1576933566903, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 4299.00, 50, 214950.00, '2019-12-21 21:06:07', '2019-12-21 21:06:07');
INSERT INTO `item` VALUES (152, 24, 1577072556154, 26, '三星洗衣机', 'localhost:8081/uploads/c1adeca3d8514ed190d4a96d2d0a5905_QQ头像2.jpg', 1000.59, 0, 0.00, '2019-12-23 11:42:36', '2019-12-23 11:42:36');
INSERT INTO `item` VALUES (153, 24, 1577072556154, 27, '三星洗衣机', 'localhost:8081/uploads/93d71092f7564b7cb4aab28cbeaa5c04_QQ头像2.jpg', 1000.63, 0, 0.00, '2019-12-23 11:42:36', '2019-12-23 11:42:36');
INSERT INTO `item` VALUES (154, 24, 1577072556154, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'localhost:8081/uploads/ee987654c1c447838f0589a38efc9e23_QQ头像2.jpg', 1999.82, 50, 99991.00, '2019-12-23 11:42:36', '2019-12-23 11:42:36');
INSERT INTO `item` VALUES (155, 24, 1577072556154, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', 'localhost:8081/uploads/e7ac6c50096f41599f32616b795f1a4f_QQ头像2.jpg', 4299.00, 50, 214950.00, '2019-12-23 11:42:36', '2019-12-23 11:42:36');
INSERT INTO `item` VALUES (156, 24, 1577072718259, 26, '三星洗衣机', 'localhost:8081/uploads/c1adeca3d8514ed190d4a96d2d0a5905_QQ头像2.jpg', 1000.59, 0, 0.00, '2019-12-23 11:45:18', '2019-12-23 11:45:18');
INSERT INTO `item` VALUES (157, 24, 1577072718259, 27, '三星洗衣机', 'localhost:8081/uploads/93d71092f7564b7cb4aab28cbeaa5c04_QQ头像2.jpg', 1000.63, 0, 0.00, '2019-12-23 11:45:18', '2019-12-23 11:45:18');
INSERT INTO `item` VALUES (158, 24, 1577072718259, 28, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'localhost:8081/uploads/ee987654c1c447838f0589a38efc9e23_QQ头像2.jpg', 1999.82, 50, 99991.00, '2019-12-23 11:45:18', '2019-12-23 11:45:18');
INSERT INTO `item` VALUES (159, 24, 1577072718259, 29, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', 'localhost:8081/uploads/e7ac6c50096f41599f32616b795f1a4f_QQ头像2.jpg', 4299.00, 50, 214950.00, '2019-12-23 11:45:18', '2019-12-23 11:45:18');

-- ----------------------------
-- Table structure for ordered
-- ----------------------------
DROP TABLE IF EXISTS `ordered`;
CREATE TABLE `ordered`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `shipping_id` int(11) NULL DEFAULT NULL,
  `payment` decimal(20, 2) NULL DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) NULL DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `postage` int(10) NULL DEFAULT NULL COMMENT '运费,单位是元',
  `status` int(10) NULL DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime(0) NULL DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no_index`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ordered
-- ----------------------------
INSERT INTO `ordered` VALUES (103, 1491753014256, 1, 25, 13998.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-09 23:50:14', '2017-04-09 23:50:14');
INSERT INTO `ordered` VALUES (104, 1491830695216, 1, 26, 13998.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-10 21:24:55', '2017-04-10 21:24:55');
INSERT INTO `ordered` VALUES (105, 1492089528889, 1, 29, 3299.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:18:48', '2017-04-13 21:18:48');
INSERT INTO `ordered` VALUES (106, 1492090946105, 24, 29, 27894.00, 1, 0, 20, '2017-04-13 21:42:40', NULL, NULL, NULL, '2017-04-13 21:42:26', '2017-04-13 21:42:41');
INSERT INTO `ordered` VALUES (107, 1492091003128, 24, 29, 8597.00, 1, 0, 40, '2017-04-13 21:43:38', '2019-12-22 10:30:35', NULL, NULL, '2017-04-13 21:43:23', '2017-04-13 21:43:38');
INSERT INTO `ordered` VALUES (108, 1492091051313, 1, 29, 1999.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:44:11', '2017-04-13 21:44:11');
INSERT INTO `ordered` VALUES (109, 1492091061513, 1, 29, 6598.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:44:21', '2017-04-13 21:44:21');
INSERT INTO `ordered` VALUES (110, 1492091069563, 1, 29, 3299.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:44:29', '2017-04-13 21:44:29');
INSERT INTO `ordered` VALUES (111, 1492091076073, 1, 29, 4299.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:44:36', '2017-04-13 21:44:36');
INSERT INTO `ordered` VALUES (112, 1492091083720, 1, 29, 3299.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:44:43', '2017-04-13 21:44:43');
INSERT INTO `ordered` VALUES (113, 1492091089794, 1, 29, 6999.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:44:49', '2017-04-13 21:44:49');
INSERT INTO `ordered` VALUES (114, 1492091096400, 1, 29, 6598.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:44:56', '2017-04-13 21:44:56');
INSERT INTO `ordered` VALUES (115, 1492091102371, 1, 29, 3299.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2017-04-13 21:45:02', '2017-04-13 21:45:02');
INSERT INTO `ordered` VALUES (116, 1492091110004, 24, 29, 8598.00, 1, 0, 40, '2017-04-13 21:55:16', '2017-04-13 21:55:31', NULL, NULL, '2017-04-13 21:45:09', '2017-04-13 21:55:31');
INSERT INTO `ordered` VALUES (117, 1492091141269, 24, 29, 22894.00, 1, 0, 20, '2017-04-13 21:46:06', NULL, NULL, NULL, '2017-04-13 21:45:41', '2017-04-13 21:46:07');
INSERT INTO `ordered` VALUES (124, 1576918046745, 24, 29, 424048.11, 1, 0, 10, NULL, NULL, NULL, NULL, '2019-12-21 16:47:27', '2019-12-21 16:47:27');
INSERT INTO `ordered` VALUES (125, 1576918633736, 24, 7, 395990.79, 1, 0, 0, NULL, NULL, NULL, NULL, '2019-12-21 16:57:14', '2019-12-21 16:57:14');
INSERT INTO `ordered` VALUES (126, 1576933353501, 24, 8, 414941.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2019-12-21 21:02:33', '2019-12-21 21:02:33');
INSERT INTO `ordered` VALUES (127, 1576933566903, 24, 5, 314941.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2019-12-21 21:06:07', '2019-12-21 21:06:07');
INSERT INTO `ordered` VALUES (128, 1577072556154, 24, 5, 314941.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2019-12-23 11:42:36', '2019-12-23 11:42:36');
INSERT INTO `ordered` VALUES (129, 1577072718259, 24, 42, 314941.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2019-12-23 11:45:18', '2019-12-23 11:45:18');

-- ----------------------------
-- Table structure for pay
-- ----------------------------
DROP TABLE IF EXISTS `pay`;
CREATE TABLE `pay`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) NULL DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay
-- ----------------------------
INSERT INTO `pay` VALUES (53, 1, 1492090946105, 1, '2017041321001004300200116250', 'WAIT_BUYER_PAY', '2017-04-13 21:42:33', '2017-04-13 21:42:33');
INSERT INTO `pay` VALUES (54, 1, 1492090946105, 1, '2017041321001004300200116250', 'TRADE_SUCCESS', '2017-04-13 21:42:41', '2017-04-13 21:42:41');
INSERT INTO `pay` VALUES (55, 1, 1492091003128, 1, '2017041321001004300200116251', 'WAIT_BUYER_PAY', '2017-04-13 21:43:31', '2017-04-13 21:43:31');
INSERT INTO `pay` VALUES (56, 1, 1492091003128, 1, '2017041321001004300200116251', 'TRADE_SUCCESS', '2017-04-13 21:43:38', '2017-04-13 21:43:38');
INSERT INTO `pay` VALUES (57, 1, 1492091141269, 1, '2017041321001004300200116252', 'WAIT_BUYER_PAY', '2017-04-13 21:45:59', '2017-04-13 21:45:59');
INSERT INTO `pay` VALUES (58, 1, 1492091141269, 1, '2017041321001004300200116252', 'TRADE_SUCCESS', '2017-04-13 21:46:07', '2017-04-13 21:46:07');
INSERT INTO `pay` VALUES (59, 1, 1492091110004, 1, '2017041321001004300200116396', 'WAIT_BUYER_PAY', '2017-04-13 21:55:08', '2017-04-13 21:55:08');
INSERT INTO `pay` VALUES (60, 1, 1492091110004, 1, '2017041321001004300200116396', 'TRADE_SUCCESS', '2017-04-13 21:55:17', '2017-04-13 21:55:17');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '分类id,对应mmall_category表的主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片地址,json格式,扩展用',
  `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品详情',
  `price` decimal(20, 2) NOT NULL COMMENT '价格,单位-元保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) NULL DEFAULT 1 COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (26, 100001, '三星洗衣机', '三星大促销', 'localhost:8081/uploads/c1adeca3d8514ed190d4a96d2d0a5905_QQ头像2.jpg', 'test.jpg', 'detailtext', 1000.59, 0, 1, '2019-12-10 00:00:00', '2019-12-10 00:00:00');
INSERT INTO `product` VALUES (27, 100001, '三星洗衣机', '三星大促销', 'localhost:8081/uploads/93d71092f7564b7cb4aab28cbeaa5c04_QQ头像2.jpg', 'test.jpg', 'detailtext', 1000.63, 0, 1, '2017-04-13 18:51:54', '2019-12-10 03:44:15');
INSERT INTO `product` VALUES (28, 100012, '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'NOVA青春版1999元', 'localhost:8081/uploads/ee987654c1c447838f0589a38efc9e23_QQ头像2.jpg', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg,13da2172-4445-4eb5-a13f-c5d4ede8458c.jpeg,58d5d4b7-58d4-4948-81b6-2bae4f79bf02.jpeg', '<p><img alt=\"11TB2fKK3cl0kpuFjSsziXXa.oVXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/5c2d1c6d-9e09-48ce-bbdb-e833b42ff664.jpg\" width=\"790\" height=\"966\"><img alt=\"22TB2YP3AkEhnpuFjSZFpXXcpuXXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/9a10b877-818f-4a27-b6f7-62887f3fb39d.jpg\" width=\"790\" height=\"1344\"><img alt=\"33TB2Yyshk.hnpuFjSZFpXXcpuXXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/7d7fbd69-a3cb-4efe-8765-423bf8276e3e.jpg\" width=\"790\" height=\"700\"><img alt=\"TB2diyziB8kpuFjSspeXXc7IpXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/1d7160d2-9dba-422f-b2a0-e92847ba6ce9.jpg\" width=\"790\" height=\"393\"><br></p>', 1999.82, 9724, 1, '2017-04-13 18:57:18', '2017-04-13 21:45:41');
INSERT INTO `product` VALUES (29, 100008, 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '门店机型 德邦送货', 'localhost:8081/uploads/e7ac6c50096f41599f32616b795f1a4f_QQ头像2.jpg', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg,42b1b8bc-27c7-4ee1-80ab-753d216a1d49.jpeg,2f1b3de1-1eb1-4c18-8ca2-518934931bec.jpeg', '<p><img alt=\"1TB2WLZrcIaK.eBjSspjXXXL.XXa_!!2114960396.jpg\" src=\"http://img.happymmall.com/ffcce953-81bd-463c-acd1-d690b263d6df.jpg\" width=\"790\" height=\"920\"><img alt=\"2TB2zhOFbZCO.eBjSZFzXXaRiVXa_!!2114960396.jpg\" src=\"http://img.happymmall.com/58a7bd25-c3e7-4248-9dba-158ef2a90e70.jpg\" width=\"790\" height=\"1052\"><img alt=\"3TB27mCtb7WM.eBjSZFhXXbdWpXa_!!2114960396.jpg\" src=\"http://img.happymmall.com/2edbe9b3-28be-4a8b-a9c3-82e40703f22f.jpg\" width=\"790\" height=\"820\"><br></p>', 4299.00, 9693, 1, '2017-04-13 19:07:47', '2017-04-13 21:45:41');
INSERT INTO `product` VALUES (32, 100002, '三星洗衣机', '三星大促销', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 'test.jpg', 'detailtext', 1000.00, 0, 1, '2019-12-10 03:37:35', '2019-12-10 03:37:35');
INSERT INTO `product` VALUES (33, 100002, '三星洗衣机', '三星大促销', 'localhost:8081/uploads/7563467626014079ab2ad8906e417c66_QQ头像2.jpg', 'test.jpg', 'detailtext', 1000.00, 100, 1, '2019-12-10 03:41:49', '2019-12-10 03:41:49');
INSERT INTO `product` VALUES (35, 100001, '三星洗衣机', '三星大促销', 'localhost:8081/uploads/2f06e7f14e224ead9144e7d2a2adccd6_QQ头像2.jpg', 'test.jpg', 'detailtext', 1000.00, 100, 1, '2019-12-10 00:00:00', '2019-12-10 00:00:00');
INSERT INTO `product` VALUES (36, 100001, '三星洗衣机', '三星大促销', 'localhost:8081/uploads/350db77d2fdc40ca91251909c2135eef_QQ头像2.jpg', 'test.jpg', 'detailtext', 1000.00, 100, 1, '2019-12-10 14:42:16', '2019-12-10 14:42:16');
INSERT INTO `product` VALUES (37, 100001, '三星洗衣机', '三星大促销', 'localhost:8081/uploads/9c5b4e31ea5c459682c717d6b64c4734_QQ头像.jpg', 'test.jpg', 'detailtext', 1000.00, 100, 1, '2019-12-10 22:50:45', '2019-12-10 22:50:45');

-- ----------------------------
-- Table structure for shipping
-- ----------------------------
DROP TABLE IF EXISTS `shipping`;
CREATE TABLE `shipping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shipping
-- ----------------------------
INSERT INTO `shipping` VALUES (4, 13, 'geely', '010', '18688888888', '北京', '北京市', '海淀区', '中关村', '100000', '2017-01-22 14:26:25', '2017-01-22 14:26:25');
INSERT INTO `shipping` VALUES (7, 17, 'Rosen', '13800138000', '13800138000', '北京', '北京', NULL, '中关村', '100000', '2017-03-29 12:11:01', '2017-03-29 12:11:01');
INSERT INTO `shipping` VALUES (29, 1, '吉利', '13800138000', '13800138000', '北京', '北京', '海淀区', '海淀区中关村', '100000', '2017-04-09 18:33:32', '2017-04-09 18:33:32');
INSERT INTO `shipping` VALUES (38, 1, 'geely', '010', '18688888888', '北京', '北京市', NULL, '中关村', '100000', '2019-12-23 10:17:27', '2019-12-23 10:17:27');
INSERT INTO `shipping` VALUES (39, 24, 'geely', '010', '18688888888', '北京', '北京市', NULL, '中关村', '100000', '2019-12-23 11:05:41', '2019-12-23 11:05:41');
INSERT INTO `shipping` VALUES (40, 24, 'geely', '010', '18688888888', '北京', '北京市', NULL, '中关村', '100000', '2019-12-23 11:05:42', '2019-12-23 11:05:42');
INSERT INTO `shipping` VALUES (41, 24, 'geely', '010', '18688888888', '北京', '北京市', NULL, '中关村', '100000', '2019-12-23 11:05:43', '2019-12-23 11:05:43');
INSERT INTO `shipping` VALUES (42, 24, 'geely', '010', '18688888888', '北京', '北京市', NULL, '中关村', '100000', '2019-12-23 11:05:43', '2019-12-23 11:05:43');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `question` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色1-管理员,0-普通用户',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '最后一次更新时间',
  `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'token值-用于修改密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name_unique`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '427338237BD929443EC5D48E24FD2B1A', 'admin@happymmall.com', '13800138000', '问题', '答案', 1, '2016-11-06 16:56:45', '2017-04-04 19:27:36', NULL);
INSERT INTO `user` VALUES (13, 'geely', '08E9A6EA287E70E7E3F7C982BF7923AC', 'geely@happymmall.com', '13800138000', '问题', '答案', 0, '2016-11-19 22:19:25', '2016-11-19 22:19:25', NULL);
INSERT INTO `user` VALUES (17, 'rosen', '095AC193FE2212EEC7A93E8FEFF11902', 'rosen1@happymmall.com', '13800138000', '问题', '答案', 0, '2017-03-17 10:51:33', '2017-04-09 23:13:26', NULL);
INSERT INTO `user` VALUES (21, 'soonerbetter', 'DE6D76FE7C40D5A1A8F04213F2BEFBEE', 'test06@happymmall.com', '13800138000', '105204', '105204', 0, '2017-04-13 21:26:22', '2017-04-13 21:26:22', NULL);
INSERT INTO `user` VALUES (22, '666', '999996', NULL, NULL, NULL, NULL, 0, '2019-11-28 17:39:47', '2019-11-28 17:40:00', NULL);
INSERT INTO `user` VALUES (23, 'root', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, 0, '2019-11-28 21:04:13', '2019-11-28 21:04:20', NULL);
INSERT INTO `user` VALUES (24, 'liu', 'e10adc3949ba59abbe56e057f20f883e', '', '12345678902', 'Who are you', 'I am you', 1, '2019-12-03 14:12:41', '2019-12-04 09:00:07', '91c15327f760443c95b9b2e8be0b6c94-24');
INSERT INTO `user` VALUES (26, 'chao', 'e10adc3949ba59abbe56e057f20f883e', '123@qq.com', '15621153978', '你是谁', '我是你', 1, '2019-12-04 08:52:31', '2019-12-04 08:52:31', NULL);
INSERT INTO `user` VALUES (28, 'liuchao', 'e10adc3949ba59abbe56e057f20f883e', '123@qq.com', '15621153978', '你是谁', '我是你', 1, '2019-12-04 08:53:45', '2019-12-04 08:53:45', NULL);

SET FOREIGN_KEY_CHECKS = 1;
