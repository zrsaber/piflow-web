ALTER TABLE `piflow_web_1.0`.`sys_user` 
DROP COLUMN `status`,
ADD COLUMN `status` tinyint(3) NOT NULL COMMENT '0 ���� 1 ���� 2 ע��' AFTER `username`;


ALTER TABLE `piflow_web_1.0`.`sys_user` 
ADD COLUMN `last_login_ip` varchar(63) NOT NULL COMMENT '���һ�ε�¼IP��ַ' AFTER `status`;