-- 创建数据库
CREATE DATABASE IF NOT EXISTS sport_checkin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sport_checkin;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(64) NOT NULL COMMENT '微信小程序openid',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像URL',
  `gender` tinyint(1) DEFAULT 0 COMMENT '性别 0-未知 1-男 2-女',
  `status` tinyint(1) DEFAULT 0 COMMENT '用户状态 0-正常 1-禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 运动类型表
CREATE TABLE IF NOT EXISTS `sport_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '运动类型ID',
  `name` varchar(32) NOT NULL COMMENT '运动类型名称',
  `unit` varchar(16) NOT NULL COMMENT '运动单位',
  `score_rate` decimal(10,4) NOT NULL COMMENT '积分比率',
  `unit_label` varchar(16) NOT NULL COMMENT '单位标签',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动类型表';

-- 打卡记录表
CREATE TABLE IF NOT EXISTS `checkin_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `sport_type_id` bigint(20) NOT NULL COMMENT '运动类型ID',
  `amount` decimal(10,2) NOT NULL COMMENT '运动量',
  `score` int(11) NOT NULL COMMENT '获得积分',
  `checkin_date` date NOT NULL COMMENT '打卡日期',
  `image_url` varchar(255) DEFAULT NULL COMMENT '打卡图片URL',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_date` (`user_id`, `checkin_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打卡记录表';

-- 特殊情况申请表
CREATE TABLE IF NOT EXISTS `special_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `type` tinyint(1) NOT NULL COMMENT '申请类型 1-生理期 2-生病 3-其他',
  `reason` varchar(255) NOT NULL COMMENT '申请原因',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '申请状态 0-待审核 1-已通过 2-已拒绝',
  `review_remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='特殊情况申请表';

-- 初始化运动类型数据
INSERT INTO `sport_type` (`name`, `unit`, `score_rate`, `unit_label`) VALUES
('跑步', '公里', 15.0000, '公里'),
('跳绳', '分钟', 2.0000, '分钟'),
('羽毛球', '小时', 70.0000, '小时'),
('游泳', '千米', 70.0000, '千米'),
('徒步', '公里', 0.0025, '步');