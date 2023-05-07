/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : class_schedule

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 07/05/2023 20:58:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of administrator
-- ----------------------------


-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'root', '超级管理员');

-- ----------------------------
-- Table structure for schedule_collect_results
-- ----------------------------
DROP TABLE IF EXISTS `schedule_collect_results`;
CREATE TABLE `schedule_collect_results`  (
  `id` bigint NOT NULL,
  `task_id` bigint NULL DEFAULT NULL,
  `table_id` int NULL DEFAULT NULL,
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '周一|周二|周三|等',
  `user_id` bigint NULL DEFAULT NULL COMMENT '填写人ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '填写人名字',
  `one` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一天中第1节课情况',
  `two` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一天中第2节课情况',
  `three` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一天中第3节课情况',
  `four` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一天中第4节课情况',
  `five` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一天中第5节课情况',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_collect_results
-- ----------------------------
INSERT INTO `schedule_collect_results` VALUES (1655194486387388417, 1655194274231103489, 1, '周四', 17550356279, '全克顺', '0', '0', '0', '1', '0');
INSERT INTO `schedule_collect_results` VALUES (1655194490862710785, 1655194274231103489, 1, '周五', 17550356279, '全克顺', '0', '1', '0', '0', '0');
INSERT INTO `schedule_collect_results` VALUES (1655194492993417217, 1655194274231103489, 1, '周六', 17550356279, '全克顺', '0', '1', '0', '0', '0');
INSERT INTO `schedule_collect_results` VALUES (1655194496101396482, 1655194274231103489, 2, '周五', 17550356279, '全克顺', '0', '0', '0', '1', '0');
INSERT INTO `schedule_collect_results` VALUES (1655194497447768066, 1655194274231103489, 2, '周三', 17550356279, '全克顺', '0', '1', '0', '0', '0');
INSERT INTO `schedule_collect_results` VALUES (1655194499268096002, 1655194274231103489, 2, '周四', 17550356279, '全克顺', '1', '0', '0', '0', '0');
INSERT INTO `schedule_collect_results` VALUES (1655194501835010050, 1655194274231103489, 2, '周六', 17550356279, '全克顺', '0', '1', '0', '0', '0');
INSERT INTO `schedule_collect_results` VALUES (1655194558462308354, 1655194274231103489, 2, '周二', 17550356279, '全克顺', '0', '0', '0', '0', '1');

-- ----------------------------
-- Table structure for schedule_collect_task_tables
-- ----------------------------
DROP TABLE IF EXISTS `schedule_collect_task_tables`;
CREATE TABLE `schedule_collect_task_tables`  (
  `id` bigint NOT NULL,
  `task_id` bigint NOT NULL,
  `table_id` int NULL DEFAULT NULL COMMENT '某一任务下第几张课表',
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表的名字，如（第十四周课表）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_collect_task_tables
-- ----------------------------
INSERT INTO `schedule_collect_task_tables` VALUES (1655194274298212353, 1655194274231103489, 1, '13');
INSERT INTO `schedule_collect_task_tables` VALUES (1655194274331766785, 1655194274231103489, 2, '15');

-- ----------------------------
-- Table structure for schedule_collect_tasks
-- ----------------------------
DROP TABLE IF EXISTS `schedule_collect_tasks`;
CREATE TABLE `schedule_collect_tasks`  (
  `task_id` bigint NOT NULL,
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `is_publish` tinyint(1) NULL DEFAULT NULL COMMENT '是否发布（1是|0否）',
  `deadline` datetime NULL DEFAULT NULL COMMENT '截止时间',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_collect_tasks
-- ----------------------------
INSERT INTO `schedule_collect_tasks` VALUES (1655194274231103489, '最终测试', 1655145096108969986, '全克顺', '备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试备注测试', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1655145096108969986', '全克顺', '13204128', '$2a$10$FM95U.t.C8D.UMrYd.FkquzfXLNbIQlVoM9TUP3JTLOe/DDH0gBYS');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('0390590299', '0390590296', '1');
INSERT INTO `user_role` VALUES ('0390590384', '0390590296', '2');

SET FOREIGN_KEY_CHECKS = 1;
