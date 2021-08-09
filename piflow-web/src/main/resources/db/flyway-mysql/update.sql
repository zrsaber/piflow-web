ALTER TABLE `piflow_web_1.0`.`sys_user` 
DROP COLUMN `status`,
ADD COLUMN `status` tinyint(3) NOT NULL COMMENT '0 正常 1 冻结 2 注销' AFTER `username`;


ALTER TABLE `piflow_web_1.0`.`sys_user` 
ADD COLUMN `last_login_ip` varchar(63) NOT NULL COMMENT '最近一次登录IP地址' AFTER `status`;