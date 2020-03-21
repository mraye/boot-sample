
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT NULL,
  `at_deleted` tinyint(1) NULL DEFAULT NULL,
  `at_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `at_modify_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_user
-- test 密码：111111
-- ----------------------------
INSERT INTO `t_sys_user` VALUES (1, 'test', '$2a$10$Fqf.dsfti1exVWdPJQb2X.mITGLs8gS2ANKMac4OIcaPbQhXUATtm', 1, 0, '2019-05-25 22:43:34', '2019-05-26 09:17:58');

SET FOREIGN_KEY_CHECKS = 1;
