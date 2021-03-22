/*
 Navicat Premium Data Transfer

 Source Server         : 132.232.64.25
 Source Server Type    : MySQL
 Source Server Version : 100412
 Source Host           : 132.232.64.25:3306
 Source Schema         : book-donate

 Target Server Type    : MySQL
 Target Server Version : 100412
 File Encoding         : 65001

 Date: 23/03/2021 02:31:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `receiver` varchar(32) NOT NULL COMMENT '接收人',
  `mobile` varchar(32) NOT NULL COMMENT '接收人手机号',
  `province` varchar(32) NOT NULL COMMENT '省份',
  `city` varchar(32) NOT NULL COMMENT '城市',
  `district` varchar(32) NOT NULL COMMENT '地区',
  `detail` varchar(128) NOT NULL COMMENT '详细地址',
  `extend` varchar(128) DEFAULT NULL COMMENT '扩展字段',
  `is_delete` int(11) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赠与地区表';

-- ----------------------------
-- Table structure for book_tags
-- ----------------------------
DROP TABLE IF EXISTS `book_tags`;
CREATE TABLE `book_tags` (
  `book_id` varchar(64) NOT NULL COMMENT '书籍ID',
  `tag` varchar(32) NOT NULL COMMENT '标签',
  KEY `uk_tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书的标签表 ';

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `isbn` varchar(64) NOT NULL COMMENT 'ISBN',
  `title` varchar(128) NOT NULL COMMENT '书名',
  `author` varchar(128) NOT NULL COMMENT '作者',
  `author_intro` text DEFAULT NULL COMMENT '作者简介',
  `tag` varchar(128) DEFAULT NULL COMMENT '标签',
  `num_raters` int(11) NOT NULL COMMENT '评分人数',
  `average` varchar(32) NOT NULL DEFAULT '0.0' COMMENT '评分',
  `binding` varchar(32) DEFAULT NULL COMMENT '精简装',
  `pages` varchar(32) NOT NULL COMMENT '页数',
  `publisher` varchar(128) NOT NULL COMMENT '出版社',
  `origin_title` text DEFAULT NULL COMMENT '图书原名 Url',
  `url` varchar(128) DEFAULT NULL COMMENT '豆瓣链接',
  `price` int(11) NOT NULL COMMENT '售价',
  `price_donate` int(11) NOT NULL COMMENT '捐赠价',
  `stock` int(11) NOT NULL COMMENT '库存',
  `content_summary` text NOT NULL DEFAULT '' COMMENT '内容摘要',
  `image_url` varchar(1024) NOT NULL COMMENT '图片',
  `extend` varchar(128) DEFAULT NULL COMMENT '扩展字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍表 书籍相关信息';

-- ----------------------------
-- Table structure for delivery
-- ----------------------------
DROP TABLE IF EXISTS `delivery`;
CREATE TABLE `delivery` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `order_id` varchar(64) NOT NULL COMMENT '订单ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `status` int(11) NOT NULL COMMENT '配送状态',
  `extend` varchar(128) DEFAULT NULL COMMENT '扩展字段',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`) USING BTREE COMMENT '订单唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配送状态 ';

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `order_id` varchar(64) NOT NULL COMMENT '订单ID',
  `item_id` varchar(64) NOT NULL COMMENT '商品id',
  `item_name` varchar(128) NOT NULL COMMENT '商品名称',
  `item_type` int(11) NOT NULL COMMENT '商品类型',
  `price` int(11) NOT NULL COMMENT '价格',
  `buy_counts` int(11) NOT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='捐赠订单关联商品表 取名叫items为了以后能捐赠更多类型的商品，不限于书籍';

-- ----------------------------
-- Table structure for order_status
-- ----------------------------
DROP TABLE IF EXISTS `order_status`;
CREATE TABLE `order_status` (
  `order_id` varchar(64) NOT NULL COMMENT '订单id',
  `order_status` int(11) NOT NULL COMMENT '订单状态\n10：待付款  20：已付款，待发货  30：已发货，待收货（7天自动确认）  40：交易成功（此时可以评价）50：交易关闭（待付款时，用户取消 或 长时间未付款，系统识别后自动关闭）\n退货/退货，此分支流程不做，所以不加入',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `deliver_time` datetime DEFAULT NULL COMMENT '发货时间',
  `success_time` datetime DEFAULT NULL COMMENT '交易成功时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单状态表 订单的每个状态更改都需要进行记录';

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` varchar(64) NOT NULL COMMENT '主键订单号',
  `delivery_id` varchar(64) DEFAULT NULL COMMENT '订单配送id',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `receive_name` varchar(32) NOT NULL COMMENT '收货人快照',
  `receive_mobile` varchar(32) NOT NULL COMMENT '收货人手机号快照',
  `receive_address` varchar(128) NOT NULL COMMENT '收获地址快照',
  `area_id` varchar(32) NOT NULL COMMENT '地区表ID',
  `total_amount` int(11) NOT NULL COMMENT '订单总价格',
  `real_pay_amount` int(11) NOT NULL COMMENT '实际支付价格',
  `pay_method` int(11) NOT NULL COMMENT '支付方式',
  `left_msg` varchar(1024) DEFAULT NULL COMMENT '捐助留言',
  `extend` varchar(128) DEFAULT NULL COMMENT '扩展字段',
  `is_delete` int(11) NOT NULL COMMENT '是否逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间（成交时间）',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='捐赠订单表 ';

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL COMMENT '主键',
  `url` varchar(128) NOT NULL COMMENT '豆瓣URL',
  `type` varchar(32) NOT NULL COMMENT '分类类型',
  `tag` varchar(32) NOT NULL COMMENT '标签名称',
  `item_type` int(11) NOT NULL DEFAULT 1 COMMENT '项目分类（1.书籍，10.其他）',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表 ';

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `realname` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `face` varchar(128) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表 ';

SET FOREIGN_KEY_CHECKS = 1;
