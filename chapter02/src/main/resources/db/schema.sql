-- 创建token信息表
CREATE TABLE IF NOT EXISTS `tokens` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL COMMENT '授权唯一标识',
  `token` blob COMMENT 'token值',
  `build_time` varchar(20) DEFAULT NULL COMMENT '生成token时间（秒单位）',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='api请求token信息表，根据userId保存';

-- 创建用户信息表
CREATE TABLE IF NOT EXISTS `user_infos` (
  `id` varchar(100) NOT NULL COMMENT '授权唯一标识',
  `secret` blob NOT NULL COMMENT '授权密钥',
  `status` INTEGER NOT NULL DEFAULT 1 COMMENT '用户状态,1：正常，0：无效',
  `day_request_count` int(11) NOT NULL COMMENT '日请求量',
  `ip_address` varchar(100) DEFAULT NULL COMMENT '绑定IP地址多个使用“,”隔开',
  `mark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='api平台用户信息表';

