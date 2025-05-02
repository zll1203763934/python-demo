-- 初始化运动类型数据
INSERT INTO `sport_type` (`name`, `unit`, `score_rate`, `unit_label`) VALUES
('跑步', '公里', 15.0000, '公里'),
('跳绳', '分钟', 2.0000, '分钟'),
('羽毛球', '小时', 70.0000, '小时'),
('游泳', '千米', 70.0000, '千米'),
('徒步', '公里', 0.0025, '步'),
('健身', '小时', 50.0000, '小时'),
('骑行', '公里', 5.0000, '公里'),
('瑜伽', '分钟', 1.0000, '分钟');

-- 初始化管理员用户
INSERT INTO `user` (`openid`, `nick_name`, `gender`, `status`, `create_time`, `update_time`) VALUES
('admin_openid', '管理员', 1, 0, NOW(), NOW());