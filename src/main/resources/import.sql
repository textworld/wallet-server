
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Records of t_account
-- ----------------------------
BEGIN;
INSERT INTO `t_account` (`id`, `gmt_create`, `gmt_modify`, `comment`, `name`, `type`, `active`) VALUES (1, '2019-08-31 14:04:04', '2019-08-31 14:02:00', '现金', '现金', 1, 1);
INSERT INTO `t_account` (`id`, `gmt_create`, `gmt_modify`, `comment`, `name`, `type`, `active`) VALUES (2, '2019-08-31 14:02:00', '2019-08-31 14:02:00', '信用卡', '交通银行信用卡', 2, 1);
INSERT INTO `t_account` (`id`, `gmt_create`, `gmt_modify`, `comment`, `name`, `type`, `active`) VALUES (3, '2019-08-31 14:02:00', '2019-08-31 14:02:00', '信用卡', '招商银行信用卡', 2, 1);
INSERT INTO `t_account` (`id`, `gmt_create`, `gmt_modify`, `comment`, `name`, `type`, `active`) VALUES (4, '2019-08-31 14:02:00', '2019-08-31 14:02:00', '借记卡', '招商银行借记卡', 1, 1);
INSERT INTO `t_transaction` VALUES (1, '2019-08-31 16:00:00', '2019-08-31 16:00:00', 0, 0, NULL, 0, 1);
INSERT INTO `t_account_record` (`id`, `gmt_create`, `gmt_modify`, `account_id`, `transaction_id`, `money`, `gmt_transaction`) VALUES (1, '2019-09-01 15:00:00', '2019-09-01 15:00:00', 1, 1, 22.3, '2019-09-01 15:00:00');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;