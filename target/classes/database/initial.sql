/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : mjstyle

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2017-09-14 04:57:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gen_custom_obj
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_obj`;
CREATE TABLE `gen_custom_obj` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `label` varchar(64) DEFAULT NULL COMMENT '标签',
  `value` varchar(256) DEFAULT NULL COMMENT '完整类名',
  `dataurl` varchar(256) DEFAULT NULL COMMENT '数据接口',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `description` varchar(255) DEFAULT NULL COMMENT '说明',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `table_name` varchar(45) DEFAULT NULL COMMENT '物理表明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of gen_custom_obj
-- ----------------------------

-- ----------------------------
-- Table structure for gen_scheme
-- ----------------------------
DROP TABLE IF EXISTS `gen_scheme`;
CREATE TABLE `gen_scheme` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) DEFAULT NULL COMMENT '分类',
  `package_name` varchar(500) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `sub_module_name` varchar(30) DEFAULT NULL COMMENT '生成子模块名',
  `function_name` varchar(500) DEFAULT NULL COMMENT '生成功能名',
  `function_name_simple` varchar(100) DEFAULT NULL COMMENT '生成功能名（简写）',
  `function_author` varchar(100) DEFAULT NULL COMMENT '生成功能作者',
  `gen_table_id` varchar(200) DEFAULT NULL COMMENT '生成表编号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  `form_style` varchar(45) DEFAULT NULL COMMENT '表单风格',
  `projectpath` varchar(245) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`id`),
  KEY `gen_scheme_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of gen_scheme
-- ----------------------------
INSERT INTO `gen_scheme` VALUES ('000fbb2eef694532ab9df8c83632cba6', null, 'curd', 'com.thinkgem.jeesite.modules', 'test', 'test', 'test', 'test', 'test', '547be5f2504d4da8bf971ee0db0b625f', '8b454376c0434e2792a1dc57edf80dbd', '2016-01-03 20:30:28', '8b454376c0434e2792a1dc57edf80dbd', '2016-01-03 20:30:51', null, '0', null, null);
INSERT INTO `gen_scheme` VALUES ('2d5ed2d9f55842d0aadf8609f69084a5', null, 'curd', 'com.iuling.mods', 'gen', '', '数据库表单', '数据库表单', 'Sincere', 'd8d479aacdd8405fb487f733ea7c509b', '1', '2017-03-26 00:23:53', '1', '2017-03-26 00:23:53', null, '0', null, null);
INSERT INTO `gen_scheme` VALUES ('505b3e23c7234df79c14c0dce24af75a', null, 'curd', 'com.thinkgem.jeesite.modules', 'test', 'test', 'test', 'test', 'test', '7612365ac0c645008661aaf479c45b82', '8b454376c0434e2792a1dc57edf80dbd', '2016-01-03 20:27:12', '8b454376c0434e2792a1dc57edf80dbd', '2016-01-03 20:28:48', null, '0', null, null);

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100) DEFAULT NULL COMMENT '实体类名称',
  `parent_table` varchar(200) DEFAULT NULL COMMENT '关联父表',
  `parent_table_fk` varchar(100) DEFAULT NULL COMMENT '关联父表外键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  `issync` varchar(45) DEFAULT NULL COMMENT '同步',
  `table_type` varchar(45) DEFAULT NULL COMMENT '表类型',
  `old_name` varchar(200) DEFAULT NULL COMMENT '改变前表的名字',
  `old_comments` varchar(200) DEFAULT NULL COMMENT '改变前表的描述',
  `gen_id_type` varchar(45) DEFAULT NULL COMMENT '主键策略',
  `old_gen_id_type` varchar(45) DEFAULT NULL COMMENT '改变前主键策略',
  PRIMARY KEY (`id`),
  KEY `gen_table_name` (`name`) USING BTREE,
  KEY `gen_table_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `gen_table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `jdbc_type` varchar(100) DEFAULT NULL COMMENT '列的数据类型的字节长度',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1) DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200) DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `dict_type` varchar(200) DEFAULT NULL COMMENT '字典类型',
  `settings` varchar(2000) DEFAULT NULL COMMENT '其它设置（扩展字段JSON）',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  `is_form` varchar(45) DEFAULT NULL COMMENT '是否表单显示',
  `tableName` varchar(45) DEFAULT NULL COMMENT '管理的查询表名',
  `fieldLabels` varchar(512) DEFAULT NULL,
  `fieldKeys` varchar(512) DEFAULT NULL,
  `searchLabel` varchar(45) DEFAULT NULL,
  `searchKey` varchar(45) DEFAULT NULL,
  `validateType` varchar(45) DEFAULT NULL,
  `min_length` varchar(45) DEFAULT NULL,
  `max_length` varchar(45) DEFAULT NULL,
  `min_value` varchar(45) DEFAULT NULL,
  `max_value` varchar(45) DEFAULT NULL,
  `dataUrl` varchar(245) DEFAULT '数据接口',
  `old_name` varchar(200) DEFAULT NULL,
  `old_comments` varchar(500) DEFAULT NULL,
  `old_jdbc_type` varchar(45) DEFAULT NULL,
  `old_is_pk` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gen_table_column_table_id` (`gen_table_id`) USING BTREE,
  KEY `gen_table_column_name` (`name`) USING BTREE,
  KEY `gen_table_column_sort` (`sort`) USING BTREE,
  KEY `gen_table_column_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for gen_template
-- ----------------------------
DROP TABLE IF EXISTS `gen_template`;
CREATE TABLE `gen_template` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) DEFAULT NULL COMMENT '分类',
  `file_path` varchar(500) DEFAULT NULL COMMENT '生成文件路径',
  `file_name` varchar(200) DEFAULT NULL COMMENT '生成文件名',
  `content` text COMMENT '内容',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_template_del_falg` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of gen_template
-- ----------------------------
INSERT INTO `gen_template` VALUES ('0', '0', ',,', '0', '0', '0', null, null, '1', '2017-09-14 03:40:20', null, '0');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('org.springframework.scheduling.quartz.SchedulerFactoryBean#0', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('org.springframework.scheduling.quartz.SchedulerFactoryBean#0', 'TRIGGER_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('scheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('scheduler', 'TRIGGER_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('scheduler1', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('schedulerFactoryBean', 'Sincere1505335799067', '1505336234242', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '编号',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('38a795329d1040ba93aa7125eae4fd75', 'aa23de04b47c4af086f23d68b64383bf', '0,a9beb8c645ff448d89e71f96dc97bc09,aa23de04b47c4af086f23d68b64383bf,', '南京', '30', '003', '1', '1', '2017-04-04 22:32:20', '1', '2017-06-19 17:08:08', '', '0');
INSERT INTO `sys_area` VALUES ('47f82712f10949bd83963a0cf4fb125f', '38a795329d1040ba93aa7125eae4fd75', '0,a9beb8c645ff448d89e71f96dc97bc09,aa23de04b47c4af086f23d68b64383bf,38a795329d1040ba93aa7125eae4fd75,', '江宁', '30', '006', '1', '1', '2017-04-04 22:32:26', '1', '2017-06-19 17:08:43', '', '0');
INSERT INTO `sys_area` VALUES ('70bea8684c4142f79e93544ec104ae54', 'a9beb8c645ff448d89e71f96dc97bc09', '0,a9beb8c645ff448d89e71f96dc97bc09,', '北京', '30', '004', '1', '1', '2017-06-19 17:08:18', '1', '2017-06-19 17:08:18', '', '0');
INSERT INTO `sys_area` VALUES ('a9beb8c645ff448d89e71f96dc97bc09', '0', '0,', '中国', '30', '001', '1', '1', '2017-03-26 22:13:14', '1', '2017-06-19 17:07:49', '', '0');
INSERT INTO `sys_area` VALUES ('aa23de04b47c4af086f23d68b64383bf', 'a9beb8c645ff448d89e71f96dc97bc09', '0,a9beb8c645ff448d89e71f96dc97bc09,', '江苏', '30', '002', '1', '1', '2017-03-26 23:28:59', '1', '2017-06-19 17:07:58', '', '0');

-- ----------------------------
-- Table structure for sys_datarule
-- ----------------------------
DROP TABLE IF EXISTS `sys_datarule`;
CREATE TABLE `sys_datarule` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `menu_id` varchar(64) DEFAULT NULL COMMENT '所属菜单',
  `name` varchar(64) DEFAULT NULL COMMENT '数据规则名称',
  `t_field` varchar(64) DEFAULT NULL COMMENT '规则字段',
  `t_express` varchar(64) DEFAULT NULL COMMENT '规则条件',
  `t_value` varchar(64) DEFAULT NULL COMMENT '规则值',
  `sql_segment` varchar(1256) DEFAULT NULL COMMENT '自定义sql',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `className` varchar(145) DEFAULT NULL COMMENT '实体类名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_datarule
-- ----------------------------
INSERT INTO `sys_datarule` VALUES ('09876c1700864719a89aab9e69c3a761', '17', '所在公司数据', '', '', '', '(a.id = #{currentUser.company.id} OR (a.parent_id = #{currentUser.company.id} AND a.type = \'2\') OR a.id IS NULL)', '', '0', 'Office');
INSERT INTO `sys_datarule` VALUES ('0b40b8413b204ec98e975a9fd40257ad', '20', '所在部门及以下数据', '', '', '', '(o.id = #{currentUser.office.id} OR o.parent_ids LIKE concat(#{currentUser.office.parentIds},#{currentUser.office.id},\',%\') OR a.id = #{currentUser.id})', '', '0', 'User');
INSERT INTO `sys_datarule` VALUES ('102801d210cc48968ecbbf782a2e7dc1', '7', '仅本人数据', '', '', '', '(u.id = #{currentUser.id})', '', '0', 'Role');
INSERT INTO `sys_datarule` VALUES ('1759dc61849247f79d802dad44fc8bc5', '17', '所在部门及以下数据', '', '', '', '(a.id = #{currentUser.office.id} OR a.parent_ids LIKE concat(#{currentUser.office.parentIds},#{currentUser.office.id},\',%\') OR a.id IS NULL)', '', '0', 'Office');
INSERT INTO `sys_datarule` VALUES ('2e2eb541d6df45c6b0ca36bbc4471d59', '17', '所在公司及以下数据', '', '', '', '(a.id = #{currentUser.company.id} OR a.parent_ids LIKE concat(#{currentUser.company.parentIds},#{currentUser.company.id},\',%\') OR a.id IS NULL)', '', '0', 'Office');
INSERT INTO `sys_datarule` VALUES ('41090bc4161040128bdcaa113a3e34ec', '4f51b2fa967a4a7b86d4abc9e0fd5f7d', 'ee', '', '', '', '', '', '0', '');
INSERT INTO `sys_datarule` VALUES ('5f730a61f80a49c4a823d766570e7bda', '7fe0397a90214f49adc9bbbe48e5ab69', '222', '', '', '', '', '', '0', '');
INSERT INTO `sys_datarule` VALUES ('6af85a03b75643f4ae49ecaff0e0923b', '652ebb5d37d04516b27e222309651edd', 'dddd', '', '', '', '', '', '0', '');
INSERT INTO `sys_datarule` VALUES ('7154d705b59a4efe805bd2568e6abda2', '7', '所在公司数据', '', '', '', '(o.id = #{currentUser.company.id} OR (o.parent_id = #{currentUser.company.id} AND o.type = \'2\') OR u.id = #{currentUser.id})', '', '0', 'Role');
INSERT INTO `sys_datarule` VALUES ('7f4ea2f8f3c44169b0a6ce9078794ec6', '16f34cffe21a431e844895715bf7f1f8', 'yyy', '', '', '', '', '', '0', '');
INSERT INTO `sys_datarule` VALUES ('8505d7d249ab43a8953aa72dca01812a', '20', '所在部门数据', '', '', '', '(o.id = #{currentUser.office.id} OR a.id = #{currentUser.id})', '', '0', 'User');
INSERT INTO `sys_datarule` VALUES ('8871f109676c4680b9dedf2719f415a0', '20', '所在公司数据', '', '', '', '(o.id = #{currentUser.company.id} OR (o.parent_id = #{currentUser.company.id} AND o.type = \'2\') OR a.id = #{currentUser.id})', '', '0', 'User');
INSERT INTO `sys_datarule` VALUES ('8c0da837f3ff4da395f36dc204779115', '20', '仅本人数据', '', '', '', '(a.id = #{currentUser.id})', '', '0', 'User');
INSERT INTO `sys_datarule` VALUES ('92b085a627884d839567596fd378b63a', '20', '所在公司及以下数据', '', '', '', '(o.id = #{currentUser.company.id} OR o.parent_ids LIKE concat(#{currentUser.company.parentIds},#{currentUser.company.id},\',%\') OR a.id = #{currentUser.id})', '', '0', 'User');
INSERT INTO `sys_datarule` VALUES ('94e929fbe54c4584a26d50c80616b922', '9a43a835522f47f681b21ff22cfa5a60', 'eeee', '', '', '', '', '', '0', '');
INSERT INTO `sys_datarule` VALUES ('977263957557476ea76c41493a213444', '7', '所在部门数据', '', '', '', '(o.id = #{currentUser.office.id} OR u.id = #{currentUser.id})', '', '0', 'Role');
INSERT INTO `sys_datarule` VALUES ('99ad10382d8d4287b89412794ee7355f', '7', '所在部门及以下数据', '', '', '', '(o.id = #{currentUser.office.id} OR o.parent_ids LIKE concat(#{currentUser.office.parentIds},#{currentUser.office.id},\',%\') OR u.id = #{currentUser.id})', '', '0', 'Role');
INSERT INTO `sys_datarule` VALUES ('9b61bb267f3948b5944b8136ed2b5fda', '77e091aeb1a14032b2ecdd4a8334cf13', 'bbb', '', '', '', '', '', '0', '');
INSERT INTO `sys_datarule` VALUES ('a0b18ce9c2234816a3b2cc75a8ecad22', '17', '仅本人数据', '', '', '', '(a.id IS NULL)', '', '0', 'Office');
INSERT INTO `sys_datarule` VALUES ('cd133c59687444eaaea6db7742901984', '17', '所在部门数据', '', '', '', '(a.id = #{currentUser.office.id} OR a.id IS NULL)', '', '0', 'Office');
INSERT INTO `sys_datarule` VALUES ('dbc2c7a6d8e4409893cd517d23ce214f', '7', '所在公司及以下数据', '', '', '', '(o.id = #{currentUser.company.id} OR o.parent_ids LIKE concat(#{currentUser.company.parentIds},#{currentUser.company.id},\',%\') OR u.id = #{currentUser.id})', '', '0', 'Role');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`) USING BTREE,
  KEY `sys_dict_label` (`label`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('0a22f3278a624882a822e0820f27efcb', '1', '主表', 'table_type', '表类型', '20', '0', '1', '2016-01-05 21:47:14', '1', '2016-01-05 21:53:34', '', '0');
INSERT INTO `sys_dict` VALUES ('1', '0', '正常', 'del_flag', '删除标记', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('10', 'yellow', '黄色', 'color', '颜色值', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('100', 'java.util.Date', 'Date', 'gen_java_type', 'Java类型\0\0', '50', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('101', 'com.thinkgem.jeesite.modules.sys.entity.User', 'User', 'gen_java_type', 'Java类型\0\0', '60', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('102', 'com.thinkgem.jeesite.modules.sys.entity.Office', 'Office', 'gen_java_type', 'Java类型\0\0', '70', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('103', 'com.thinkgem.jeesite.modules.sys.entity.Area', 'Area', 'gen_java_type', 'Java类型\0\0', '80', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('104', 'Custom', 'Custom', 'gen_java_type', 'Java类型\0\0', '90', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('105', '1', '会议通告\0\0\0\0', 'oa_notify_type', '通知通告类型', '10', '0', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('106', '2', '奖惩通告\0\0\0\0', 'oa_notify_type', '通知通告类型', '20', '0', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('107', '3', '活动通告\0\0\0\0', 'oa_notify_type', '通知通告类型', '30', '0', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('108', '0', '草稿', 'oa_notify_status', '通知通告状态', '10', '0', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('109', '1', '发布', 'oa_notify_status', '通知通告状态', '20', '0', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('11', 'orange', '橙色', 'color', '颜色值', '50', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('110', '0', '未读', 'oa_notify_read', '通知通告状态', '10', '0', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('111', '1', '已读', 'oa_notify_read', '通知通告状态', '20', '0', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('12', 'default', '默认主题', 'theme', '主题方案', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('13', 'cerulean', '天蓝主题', 'theme', '主题方案', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('14', 'readable', '橙色主题', 'theme', '主题方案', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('17', '1', '国家', 'sys_area_type', '区域类型', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('18', '2', '省份、直辖市', 'sys_area_type', '区域类型', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('19', '3', '地市', 'sys_area_type', '区域类型', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('1c0ac576c33d41fcb4c16602bf4fad5d', 'post', 'post', 'interface_type', '接口类型', '20', '0', '1', '2015-11-30 15:52:25', '1', '2015-11-30 15:52:39', '', '0');
INSERT INTO `sys_dict` VALUES ('2', '1', '删除', 'del_flag', '删除标记', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('20', '4', '区县', 'sys_area_type', '区域类型', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('21', '1', '公司', 'sys_office_type', '机构类型', '60', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('22', '2', '部门', 'sys_office_type', '机构类型', '70', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('221a918bd1e149239a17ab0fdeaf5ecd', 'get', 'get', 'interface_type', '接口类型', '10', '0', '1', '2015-11-30 15:51:56', '1', '2015-11-30 15:51:56', '', '0');
INSERT INTO `sys_dict` VALUES ('23', '3', '小组', 'sys_office_type', '机构类型', '80', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('24', '4', '其它', 'sys_office_type', '机构类型', '90', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('25', '1', '综合部', 'sys_office_common', '快捷通用部门', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('26', '2', '开发部', 'sys_office_common', '快捷通用部门', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('27', '3', '人力部', 'sys_office_common', '快捷通用部门', '50', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('28', '1', '一级', 'sys_office_grade', '机构等级', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('29', '2', '二级', 'sys_office_grade', '机构等级', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('3', '1', '显示', 'show_hide', '显示/隐藏', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('30', '3', '三级', 'sys_office_grade', '机构等级', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('31', '4', '四级', 'sys_office_grade', '机构等级', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('32', '1', '所有数据', 'sys_data_scope', '数据范围', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('33', '2', '所在公司及以下数据', 'sys_data_scope', '数据范围', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('34', '3', '所在公司数据', 'sys_data_scope', '数据范围', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('35', '4', '所在部门及以下数据', 'sys_data_scope', '数据范围', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('36', '5', '所在部门数据', 'sys_data_scope', '数据范围', '50', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('37', '8', '仅本人数据', 'sys_data_scope', '数据范围', '90', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('38', '9', '按明细设置', 'sys_data_scope', '数据范围', '100', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('39', '1', '系统管理', 'sys_user_type', '用户类型', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('4', '0', '隐藏', 'show_hide', '显示/隐藏', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('40', '2', '部门经理', 'sys_user_type', '用户类型', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('41', '3', '普通用户', 'sys_user_type', '用户类型', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('42', 'basic', '基础主题', 'cms_theme', '站点主题', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('43', 'blue', '蓝色主题', 'cms_theme', '站点主题', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('43c9472f411c4d3eafb3bf5319ffe499', '2', '异常报告', 'report_type', '异常的报告', '20', '0', '1', '2015-12-08 17:49:57', '1', '2015-12-08 17:49:57', '', '0');
INSERT INTO `sys_dict` VALUES ('44', 'red', '红色主题', 'cms_theme', '站点主题', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('45', 'article', '文章模型', 'cms_module', '栏目模型', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('46', 'picture', '图片模型', 'cms_module', '栏目模型', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('47', 'download', '下载模型', 'cms_module', '栏目模型', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('48', 'link', '链接模型', 'cms_module', '栏目模型', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('49', 'special', '专题模型', 'cms_module', '栏目模型', '50', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('5', '1', '是', 'yes_no', '是/否', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('50', '0', '默认展现方式', 'cms_show_modes', '展现方式', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('51', '1', '首栏目内容列表', 'cms_show_modes', '展现方式', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('52', '2', '栏目第一条内容', 'cms_show_modes', '展现方式', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('53', '0', '发布', 'cms_del_flag', '内容状态', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('54', '1', '删除', 'cms_del_flag', '内容状态', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('55', '2', '审核', 'cms_del_flag', '内容状态', '15', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('56', '1', '首页焦点图', 'cms_posid', '推荐位', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('57', '2', '栏目页文章推荐', 'cms_posid', '推荐位', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('58', '1', '咨询', 'cms_guestbook', '留言板分类', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('59', '2', '建议', 'cms_guestbook', '留言板分类', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('5b899603552c48519e7ba8eb9da0b41f', '0', '单表', 'table_type', '表类型', '10', '0', '1', '2016-01-05 21:46:39', '1', '2016-01-05 21:53:50', '', '0');
INSERT INTO `sys_dict` VALUES ('6', '0', '否', 'yes_no', '是/否', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('60', '3', '投诉', 'cms_guestbook', '留言板分类', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('61', '4', '其它', 'cms_guestbook', '留言板分类', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('62', '1', '公休', 'oa_leave_type', '请假类型', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('63', '2', '病假', 'oa_leave_type', '请假类型', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('64', '3', '事假', 'oa_leave_type', '请假类型', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('65', '4', '调休', 'oa_leave_type', '请假类型', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('66', '5', '婚假', 'oa_leave_type', '请假类型', '60', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('67', '1', '接入日志', 'sys_log_type', '日志类型', '30', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('68', '2', '异常日志', 'sys_log_type', '日志类型', '40', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('680ddd8c91fe43588a7bb7aafe816633', '1', '正常报告', 'report_type', '正常的报告', '10', '0', '1', '2015-12-08 17:49:28', '1', '2015-12-08 17:49:28', '正常的报告', '0');
INSERT INTO `sys_dict` VALUES ('69', 'leave', '请假流程', 'act_type', '流程类型', '10', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('7', 'red', '红色', 'color', '颜色值', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('70', 'test_audit', '审批测试流程', 'act_type', '流程类型', '20', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('71', '1', '常用流程', 'act_category', '流程分类', '10', '0', '1', '2013-06-03 08:00:00', '1', '2016-06-19 22:15:01', '', '0');
INSERT INTO `sys_dict` VALUES ('71804c6b820048b09c9f6f2c11121113', 'ace', 'ACE风格', 'theme', '主题方案', '15', '0', '1', '2016-04-20 21:57:21', '1', '2016-04-20 21:57:21', '', '0');
INSERT INTO `sys_dict` VALUES ('72', '2', '办公流程', 'act_category', '流程分类', '20', '0', '1', '2013-06-03 08:00:00', '1', '2016-06-19 22:15:09', '', '0');
INSERT INTO `sys_dict` VALUES ('73', 'crud', '增删改查', 'gen_category', '代码生成分类', '10', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('74', 'crud_many', '增删改查（包含从表）', 'gen_category', '代码生成分类', '20', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('75', 'tree', '树结构', 'gen_category', '代码生成分类', '30', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('76', '=', '=', 'gen_query_type', '查询方式', '10', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('77', '!=', '!=', 'gen_query_type', '查询方式', '20', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('78', '&gt;', '&gt;', 'gen_query_type', '查询方式', '30', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('79', '&lt;', '&lt;', 'gen_query_type', '查询方式', '40', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('8', 'green', '绿色', 'color', '颜色值', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('80', 'between', 'Between', 'gen_query_type', '查询方式', '50', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('81', 'like', 'Like', 'gen_query_type', '查询方式', '60', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('82', 'left_like', 'Left Like', 'gen_query_type', '查询方式', '70', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('83', 'right_like', 'Right Like', 'gen_query_type', '查询方式', '80', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('84', 'input', '文本框', 'gen_show_type', '字段生成方案', '10', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('85', 'textarea', '文本域', 'gen_show_type', '字段生成方案', '20', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('86', 'select', '下拉框', 'gen_show_type', '字段生成方案', '30', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('87', 'checkbox', '复选框', 'gen_show_type', '字段生成方案', '40', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('88', 'radiobox', '单选框', 'gen_show_type', '字段生成方案', '50', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('89', 'dateselect', '日期选择', 'gen_show_type', '字段生成方案', '60', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('9', 'blue', '蓝色', 'color', '颜色值', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('90', 'userselect', '人员选择\0', 'gen_show_type', '字段生成方案', '70', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('91', 'officeselect', '部门选择', 'gen_show_type', '字段生成方案', '80', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('92', 'areaselect', '区域选择', 'gen_show_type', '字段生成方案', '90', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('93', 'String', 'String', 'gen_java_type', 'Java类型', '10', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('94', 'Long', 'Long', 'gen_java_type', 'Java类型', '20', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('95', 'dao', '仅持久层', 'gen_category', '代码生成分类\0\0\0\0\0\0', '40', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('96', '1', '男', 'sex', '性别', '10', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('97', '2', '女', 'sex', '性别', '20', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('98', 'Integer', 'Integer', 'gen_java_type', 'Java类型\0\0', '30', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('99', 'Double', 'Double', 'gen_java_type', 'Java类型\0\0', '40', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1');
INSERT INTO `sys_dict` VALUES ('bde6043665ef4571b85d0edab667cd15', '3', '树结构表', 'table_type', '表类型', '40', '0', '1', '2016-01-06 19:48:50', '1', '2016-01-06 19:48:50', '', '0');
INSERT INTO `sys_dict` VALUES ('cc94b0c5df554a46894991210a5fc486', '2', '附表', 'table_type', '表类型', '30', '0', '1', '2016-01-05 21:47:38', '1', '2016-01-05 21:53:44', '', '0');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `type` varchar(64) DEFAULT NULL COMMENT '类型',
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('01', 'act_category', '流程分类', '1', '2013-06-03 08:00:00', '1', '2017-01-16 16:12:04', '0');
INSERT INTO `sys_dict_type` VALUES ('02', 'act_type', '流程类型', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('03', 'color', '颜色值', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('04', 'del_flag', '删除标记', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('05', 'gen_category', '代码生成分类', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_type` VALUES ('06', 'gen_java_type', 'Java类型', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_type` VALUES ('07', 'gen_query_type', '查询方式', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_type` VALUES ('08', 'gen_show_type', '字段生成方案', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_type` VALUES ('09', 'interface_type', '接口类型', '1', '2015-11-30 15:52:25', '1', '2015-11-30 15:52:39', '0');
INSERT INTO `sys_dict_type` VALUES ('10', 'oa_leave_type', '请假类型', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('11', 'oa_notify_read', '通知通告状态', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('12', 'sex', '性别', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('13', 'show_hide', '显示/隐藏', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('14', 'sys_area_type', '区域类型', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('15', 'sys_data_scope', '数据范围', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('16', 'sys_log_type', '日志类型', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('17', 'sys_office_common', '快捷通用部门', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('18', 'sys_office_grade', '机构等级', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('19', 'sys_office_type', '机构类型', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('1984aea678274fc4a00413c15c142446', 'schedule_task_info', '定时任务通知', '1', '2017-02-04 17:25:52', '1', '2017-02-07 22:35:39', '0');
INSERT INTO `sys_dict_type` VALUES ('20', 'sys_user_type', '用户类型', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('21', 'table_type', '表类型', '1', '2016-01-05 21:47:14', '1', '2016-01-05 21:53:34', '0');
INSERT INTO `sys_dict_type` VALUES ('22', 'theme', '主题方案', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('23', 'yes_no', '是/否', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_type` VALUES ('23e3cabe5cd448c78223eb61c05e839a', 'schedule_task_group', '定时任务分组', '1', '2017-02-02 23:26:36', '1', '2017-02-02 23:26:36', '0');
INSERT INTO `sys_dict_type` VALUES ('24', 'oa_notify_type', '通知通告类型', '1', null, null, null, '0');
INSERT INTO `sys_dict_type` VALUES ('25', 'oa_notify_status', '通知通告状态', '1', null, null, null, '0');
INSERT INTO `sys_dict_type` VALUES ('60b53562e81144e29d3f67f3edb965f5', 'menu_type', '菜单类型', '1', '2017-04-07 23:23:19', '1', '2017-04-07 23:23:19', '0');
INSERT INTO `sys_dict_type` VALUES ('9392eee3b3f043d39324608c5355b5a6', 'form_style', '表单风格', '1', '2017-05-28 18:19:35', '1', '2017-05-28 18:19:49', '0');
INSERT INTO `sys_dict_type` VALUES ('e83129a6290c46e7926c402969dde632', 'tedian', '特点', '1', '2017-01-24 16:50:46', '1', '2017-01-24 16:50:46', '0');
INSERT INTO `sys_dict_type` VALUES ('ebdd474a9cab463db594c7bc325c7ff4', 't_express', '数据规则', '1', '2017-03-24 23:37:02', '1', '2017-03-24 23:37:02', '0');
INSERT INTO `sys_dict_type` VALUES ('f8ba7433c8954aa1be2b88acba7994ae', 'gen_id_type', '主键类型', '1', '2017-05-28 14:13:59', '1', '2017-05-28 14:13:59', '0');

-- ----------------------------
-- Table structure for sys_dict_value
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_value`;
CREATE TABLE `sys_dict_value` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `dict_type_id` varchar(64) DEFAULT NULL COMMENT '标签名',
  `label` varchar(64) DEFAULT NULL COMMENT '键值',
  `value` varchar(64) DEFAULT NULL COMMENT '排序',
  `sort` varchar(64) DEFAULT NULL COMMENT '外键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_dict_value
-- ----------------------------
INSERT INTO `sys_dict_value` VALUES ('0a22f3278a624882a822e0820f27efcb', '21', '主表', '1', '20', '1', '2016-01-05 21:47:14', '1', '2016-01-05 21:53:34', '0');
INSERT INTO `sys_dict_value` VALUES ('1', '04', '正常', '0', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('10', '03', '黄色', 'yellow', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('100', '06', 'Date', 'java.util.Date', '50', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('101', '06', 'User', 'com.jeeplus.modules.sys.entity.User', '60', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('102', '06', 'Office', 'com.jeeplus.modules.sys.entity.Office', '70', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('103', '06', 'Area', 'com.jeeplus.modules.sys.entity.Area', '80', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('104', '06', 'Custom', 'Custom', '90', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('105', '24', '会议通告\0\0\0\0', '1', '10', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('106', '24', '奖惩通告\0\0\0\0', '2', '20', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('107', '24', '活动通告\0\0\0\0', '3', '30', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('108', '25', '草稿', '0', '10', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('109', '25', '发布', '1', '20', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('10999522b031406b8b2c30e0f35f8978', '60b53562e81144e29d3f67f3edb965f5', '按钮', '2', '60', '1', '2017-04-07 23:23:50', '1', '2017-04-07 23:23:50', '0');
INSERT INTO `sys_dict_value` VALUES ('11', '03', '橙色', 'orange', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('110', '11', '未读', '0', '10', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('111', '11', '已读', '1', '20', '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('12', '22', '默认主题', 'default', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('13', '22', '天蓝主题', 'cerulean', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('14', '22', '橙色主题', 'readable', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('17', '14', '国家', '1', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('18', '14', '省份、直辖市', '2', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('19', '14', '地市', '3', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('1b265b5f6f274429a8298d8e5e40d286', '21', '左树(主表)', '4', '50', '1', '2017-05-28 11:57:19', '1', '2017-05-28 11:57:19', '0');
INSERT INTO `sys_dict_value` VALUES ('1c0ac576c33d41fcb4c16602bf4fad5d', '09', 'post', 'post', '20', '1', '2015-11-30 15:52:25', '1', '2015-11-30 15:52:39', '0');
INSERT INTO `sys_dict_value` VALUES ('2', '04', '删除', '1', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('20', '14', '区县', '4', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('21', '19', '公司', '1', '60', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('22', '19', '部门', '2', '70', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('221a918bd1e149239a17ab0fdeaf5ecd', '09', 'get', 'get', '10', '1', '2015-11-30 15:51:56', '1', '2015-11-30 15:51:56', '0');
INSERT INTO `sys_dict_value` VALUES ('23', '19', '小组', '3', '80', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('24', '19', '其它', '4', '90', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('25', '17', '综合部', '1', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('26', '17', '开发部', '2', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('27', '17', '人力部', '3', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('28', '18', '一级', '1', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('29', '18', '二级', '2', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('3', '13', '显示', '1', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('30', '18', '三级', '3', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('31', '18', '四级', '4', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('32', '15', '所有数据', '1', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('33', '15', '所在公司及以下数据', '2', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('34', '15', '所在公司数据', '3', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('35', '15', '所在部门及以下数据', '4', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('35ee2efb9daa45179328fe49542d8e64', 'ebdd474a9cab463db594c7bc325c7ff4', '大于等于', '&gt;=', '2', '1', '2017-04-01 23:24:37', '1', '2017-04-01 23:24:37', '0');
INSERT INTO `sys_dict_value` VALUES ('36', '15', '所在部门数据', '5', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('367fc7ccd7e5419d9f8a24020f56a99d', 'f8ba7433c8954aa1be2b88acba7994ae', '自增长', '2', '20', '1', '2017-05-28 14:14:22', '1', '2017-05-28 14:14:22', '0');
INSERT INTO `sys_dict_value` VALUES ('37', '15', '仅本人数据', '8', '90', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('38', '15', '按明细设置', '9', '100', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('39', '20', '系统管理', '1', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('39c83ee547644d7ca16e2ba1b1cf0ab7', 'ebdd474a9cab463db594c7bc325c7ff4', '等于', '=', '6', '1', '2017-04-01 23:26:16', '1', '2017-04-01 23:26:16', '0');
INSERT INTO `sys_dict_value` VALUES ('3f61584b8294498e9e95b9f523ed4184', 'e83129a6290c46e7926c402969dde632', '富', '2', '2', '1', '2017-01-24 16:51:08', '1', '2017-01-24 16:51:08', '0');
INSERT INTO `sys_dict_value` VALUES ('4', '13', '隐藏', '0', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('40', '20', '部门经理', '2', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('41', '20', '普通用户', '3', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('4a30f57483d943d48aca8322af6fb3ad', 'e83129a6290c46e7926c402969dde632', '帅', '3', '3', '1', '2017-01-24 16:51:16', '1', '2017-01-24 16:51:16', '0');
INSERT INTO `sys_dict_value` VALUES ('5', '23', '是', '1', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('5746b5cb0d0f424caf99681361d7cc8c', 'f8ba7433c8954aa1be2b88acba7994ae', 'UUID', '1', '10', '1', '2017-05-28 14:14:10', '1', '2017-05-28 14:14:10', '0');
INSERT INTO `sys_dict_value` VALUES ('5b4f93870ce14e449becc07280f31be9', '21', '右表(附表)', '5', '60', '1', '2017-05-28 11:57:48', '1', '2017-05-28 11:57:48', '0');
INSERT INTO `sys_dict_value` VALUES ('5b899603552c48519e7ba8eb9da0b41f', '21', '单表', '0', '10', '1', '2016-01-05 21:46:39', '1', '2016-01-05 21:53:50', '0');
INSERT INTO `sys_dict_value` VALUES ('6', '23', '否', '0', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('62', '10', '公休', '1', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('63', '10', '病假', '2', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('64', '10', '事假', '3', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('65', '10', '调休', '4', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('66', '10', '婚假', '5', '60', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('67', '16', '接入日志', '1', '30', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('68', '16', '异常日志', '2', '40', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('69', '02', '请假流程', 'leave', '10', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('7', '03', '红色', 'red', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('70', '02', '审批测试流程', 'test_audit', '20', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('71', '01', '常用流程', '1', '10', '1', '2013-06-03 08:00:00', '1', '2017-01-07 18:12:27', '0');
INSERT INTO `sys_dict_value` VALUES ('71804c6b820048b09c9f6f2c11121113', '22', 'ACE风格', 'ace', '15', '1', '2016-04-20 21:57:21', '1', '2016-04-20 21:57:21', '0');
INSERT INTO `sys_dict_value` VALUES ('72', '01', '办公流程', '2', '20', '1', '2013-06-03 08:00:00', '1', '2017-01-07 18:12:22', '0');
INSERT INTO `sys_dict_value` VALUES ('73', '05', '增删改查', 'crud', '10', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('74', '05', '增删改查（包含从表）', 'crud_many', '20', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('75', '05', '树结构', 'tree', '30', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('76', '07', '=', '=', '10', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('77', '07', '!=', '!=', '20', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('78', '07', '&gt;', '&gt;', '30', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('79', '07', '&lt;', '&lt;', '40', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('794b27f1d5b14a84a89f658ec1e2b957', 'e83129a6290c46e7926c402969dde632', '高', '1', '1', '1', '2017-01-24 16:50:59', '1', '2017-01-24 16:50:59', '0');
INSERT INTO `sys_dict_value` VALUES ('8', '03', '绿色', 'green', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('80', '07', 'Between', 'between', '50', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('81', '07', 'Like', 'like', '60', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('82', '07', 'Left Like', 'left_like', '70', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('821039da80dd4868adfb63d3f8d8b9ae', '9392eee3b3f043d39324608c5355b5a6', '表单风格', '1', '30', '1', '2017-05-28 18:23:07', '1', '2017-05-28 18:23:33', '0');
INSERT INTO `sys_dict_value` VALUES ('83', '07', 'Right Like', 'right_like', '80', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('84', '08', '文本框', 'input', '10', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('847ebe927e4a4da3820dde1143ad2b85', '9392eee3b3f043d39324608c5355b5a6', 'dialog风格', '2', '20', '1', '2017-05-28 18:23:26', '1', '2017-05-28 18:23:26', '0');
INSERT INTO `sys_dict_value` VALUES ('85', '08', '文本域', 'textarea', '20', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('86', '08', '下拉框', 'select', '30', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('87', '08', '复选框', 'checkbox', '40', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('88', '08', '单选框', 'radiobox', '50', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('89', '08', '日期选择', 'dateselect', '60', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('8a0dca98a94d46a486a796f4288a9863', 'ebdd474a9cab463db594c7bc325c7ff4', '模糊匹配', 'like', '8', '1', '2017-04-01 23:26:53', '1', '2017-04-01 23:26:53', '0');
INSERT INTO `sys_dict_value` VALUES ('9', '03', '蓝色', 'blue', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('90', '08', '人员选择\0', 'userselect', '70', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('91', '08', '部门选择', 'officeselect', '80', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('92', '08', '区域选择', 'areaselect', '90', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('93', '06', 'String', 'String', '10', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('94', '06', 'Long', 'Long', '20', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('95', '05', '仅持久层', 'dao', '40', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('96', '12', '男', '1', '10', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('97', '12', '女', '2', '20', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '0');
INSERT INTO `sys_dict_value` VALUES ('98', '06', 'Integer', 'Integer', '30', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('99', '06', 'Double', 'Double', '40', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', '1');
INSERT INTO `sys_dict_value` VALUES ('9de5be7eea4f492fb726a958cca1d4b0', 'ebdd474a9cab463db594c7bc325c7ff4', '小于', '&lt;', '3', '1', '2017-04-01 23:25:16', '1', '2017-04-01 23:25:16', '0');
INSERT INTO `sys_dict_value` VALUES ('a4de7bc6a5b94d54bac6c577b76e27a2', 'ebdd474a9cab463db594c7bc325c7ff4', '小于等于', '&lt;=', '4', '1', '2017-04-01 23:25:31', '1', '2017-04-01 23:25:31', '0');
INSERT INTO `sys_dict_value` VALUES ('b17aa24594b042319aa6af6564b5d278', '60b53562e81144e29d3f67f3edb965f5', '菜单', '1', '30', '1', '2017-04-07 23:23:40', '1', '2017-04-07 23:23:40', '0');
INSERT INTO `sys_dict_value` VALUES ('b2995896020e48fcac899f54e494e529', '1984aea678274fc4a00413c15c142446', '所有用户', '2', '20', '1', '2017-02-04 17:28:29', '1', '2017-02-04 17:28:29', '0');
INSERT INTO `sys_dict_value` VALUES ('b2b84bd17d8b4dafb0dd83c14c3c962d', 'ebdd474a9cab463db594c7bc325c7ff4', '大于', '&gt;', '1', '1', '2017-03-24 23:37:21', '1', '2017-03-24 23:37:21', '0');
INSERT INTO `sys_dict_value` VALUES ('bde6043665ef4571b85d0edab667cd15', '21', '树结构表', '3', '40', '1', '2016-01-06 19:48:50', '1', '2016-01-06 19:48:50', '0');
INSERT INTO `sys_dict_value` VALUES ('c51e8af316b24363bbcfbb8de1e61dc4', '23e3cabe5cd448c78223eb61c05e839a', '测试任务', '1', '1', '1', '2017-02-02 23:27:06', '1', '2017-02-02 23:27:06', '0');
INSERT INTO `sys_dict_value` VALUES ('cc94b0c5df554a46894991210a5fc486', '21', '附表', '2', '30', '1', '2016-01-05 21:47:38', '1', '2016-01-05 21:53:44', '0');
INSERT INTO `sys_dict_value` VALUES ('eac171acc70b40e59b436817752aae9b', '1984aea678274fc4a00413c15c142446', '超级管理员', '1', '10', '1', '2017-02-19 19:08:47', '1', '2017-02-19 19:10:19', '0');
INSERT INTO `sys_dict_value` VALUES ('f7556a086d264cd29e0ceb417535fe61', 'ebdd474a9cab463db594c7bc325c7ff4', '不等于', '&lt;&gt;', '5', '1', '2017-04-01 23:25:58', '1', '2017-04-01 23:25:58', '0');
INSERT INTO `sys_dict_value` VALUES ('f7c2d3753c77489e99c5e9a59e7ebd3d', 'ebdd474a9cab463db594c7bc325c7ff4', '包含', 'contain', '7', '1', '2017-04-01 23:26:28', '1', '2017-04-01 23:26:28', '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `sys_log_create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `menu_type` char(1) DEFAULT NULL COMMENT '0:功能菜单  1：菜单  2：按钮  3：数据权限',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_menu_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('02c03f3a6bb34f9fab7aeb7477d672e6', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '漏斗图', '15580', '', '', '', '1', '', '1', '2017-06-04 12:57:12', '1', '2017-06-04 12:57:12', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('09b08f1063504ab2ae04a9674903284c', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '柱状图', '15550', '', '', '', '1', '', '1', '2017-06-04 12:54:53', '1', '2017-06-04 12:54:53', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('1', '0', '0,', '功能菜单', '0', null, null, 'fa fa-home', '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('10', '3', '0,1,3,', '字典管理', '60', '/sys/dict/', '', '', '1', 'sys:dict:list', '1', '2013-05-27 08:00:00', '1', '2017-04-11 16:45:55', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('10a0b87c918a48d18d5080177973918a', '9e01c22444ce4ffca35911daae45d58a', '0,1,67,9e01c22444ce4ffca35911daae45d58a,', '删除', '60', null, null, null, '0', 'monitor:scheduleJob:del', '1', '2017-02-02 22:25:10', '1', '2017-02-02 22:25:10', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('10effdc6a8724b91967ae2ff90dab397', '268c55d12ee34c5c8b717aa24cc3af73', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,', '例一', '30', '/echarts/other/sample', '', '', '1', '', '1', '2017-06-04 13:05:00', '1', '2017-06-04 13:05:00', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('11', '10', '0,1,3,10,', '查看', '30', null, null, null, '0', 'sys:dict:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('12', '10', '0,1,3,10,', '修改', '40', null, null, null, '0', 'sys:dict:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('14', '3', '0,1,3,', '区域管理', '50', '/sys/area/', '', '', '1', 'sys:area:list', '1', '2013-05-27 08:00:00', '1', '2017-04-11 16:45:48', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('15', '14', '0,1,3,14,', '查看', '30', null, null, null, '0', 'sys:area:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('16', '14', '0,1,3,14,', '修改', '40', null, null, null, '0', 'sys:area:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('17', '3', '0,1,3,', '机构管理', '40', '/sys/office/list', '', '', '1', 'sys:office:list', '1', '2013-05-27 08:00:00', '1', '2017-04-11 16:45:30', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('18', '17', '0,1,3,17,', '查看', '30', null, null, null, '0', 'sys:office:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('19', '17', '0,1,3,17,', '修改', '40', null, null, null, '0', 'sys:office:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('1c00b2a3c3f84bc491ce72eaa92c4b20', 'fe358352e9bc4ce99a7a4a3169e6145e', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,fe358352e9bc4ce99a7a4a3169e6145e,', '删除', '60', null, null, null, '0', 'echarts:other:testPieClass:del', '1', '2017-06-04 16:18:33', '1', '2017-06-04 16:18:33', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('20', '3', '0,1,3,', '用户管理', '30', '/sys/user/index', '', '', '1', 'sys:user:index', '1', '2013-05-27 08:00:00', '1', '2017-04-11 16:45:18', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('21', '20', '0,1,3,20,', '查看', '30', null, null, null, '0', 'sys:user:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('22', '20', '0,1,3,20,', '修改', '40', null, null, null, '0', 'sys:user:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('268c55d12ee34c5c8b717aa24cc3af73', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '其它', '15760', '', '', '', '1', '', '1', '2017-06-04 13:04:40', '1', '2017-06-04 13:04:40', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('27', '1', '0,1,', '我的面板', '40', '', '', 'fa fa-columns', '1', '', '1', '2013-05-27 08:00:00', '1', '2017-04-04 22:20:15', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('29', '27', '0,1,27,', '个人信息', '90', '/sys/user/info', '', 'icon-adjust', '1', '', '1', '2013-05-27 08:00:00', '1', '2016-03-27 22:43:46', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('29c2c2445d5b448cb0857fd77c2f13be', '02c03f3a6bb34f9fab7aeb7477d672e6', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,02c03f3a6bb34f9fab7aeb7477d672e6,', '例一', '30', '/echarts/funnel/sample1', '', '', '1', '', '1', '2017-06-04 12:57:24', '1', '2017-06-04 12:57:24', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('2a0f940fbe304a05a6b4040ddf6df279', '20', '0,1,3,20,', '增加', '70', '', '', '', '0', 'sys:user:add', '1', '2015-12-19 21:47:00', '1', '2015-12-19 21:47:00', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('2c01eb462cb64b63a4d508391c2e610e', '9a6bd94036984f82890c2d10ac8a5880', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,9a6bd94036984f82890c2d10ac8a5880,', '例四', '120', '/echarts/line/sample4', '', '', '1', '', '1', '2017-06-04 13:00:22', '1', '2017-06-04 13:00:22', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('2ea63a7508904c37ae34e8ce7e99f95c', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '矩形图', '15730', '/echarts/treemap/sample', '', '', '1', '', '1', '2017-06-04 13:04:30', '1', '2017-06-04 13:04:30', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '0,1,', '系统设置', '60', '', '', 'fa fa-gear', '1', '', '1', '2013-05-27 08:00:00', '1', '2017-04-04 22:20:53', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('30b58767a99544209f7021655ca9352a', '9e01c22444ce4ffca35911daae45d58a', '0,1,67,9e01c22444ce4ffca35911daae45d58a,', '暂停', '120', '', '', '', '0', 'monitor:scheduleJob:stop', '1', '2017-02-02 22:25:10', '1', '2017-02-05 10:27:31', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('332dd194803145ed92d2b8b0c0e9999f', 'fe358352e9bc4ce99a7a4a3169e6145e', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,fe358352e9bc4ce99a7a4a3169e6145e,', '查看', '120', null, null, null, '0', 'echarts:other:testPieClass:view', '1', '2017-06-04 16:18:33', '1', '2017-06-04 16:18:33', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('3c1c639c76f14f6f9903b0143371ea09', '7', '0,1,3,7,', '添加', '70', '', '', '', '0', 'sys:role:add', '1', '2015-12-23 21:35:08', '1', '2015-12-23 21:36:18', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('3fa7d66022f94a9484b238225f528d04', '09b08f1063504ab2ae04a9674903284c', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,09b08f1063504ab2ae04a9674903284c,', '例 四', '120', '/echarts/bar/sample4', '', '', '1', '', '1', '2017-06-04 12:56:21', '1', '2017-06-04 12:56:21', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('4', '3', '0,1,3,', '菜单管理', '30', '/sys/menu/', '', '', '1', 'sys:menu:list', '1', '2013-05-27 08:00:00', '1', '2017-03-26 18:09:50', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('431c7a2425e9483098894aafb7db288c', '9a6bd94036984f82890c2d10ac8a5880', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,9a6bd94036984f82890c2d10ac8a5880,', '例一', '30', '/echarts/line/sample1', '', '', '1', '', '1', '2017-06-04 12:59:45', '1', '2017-06-04 12:59:45', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('487f0e3fd10d482a843fb706b7ce2ce0', '09b08f1063504ab2ae04a9674903284c', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,09b08f1063504ab2ae04a9674903284c,', '例一', '30', '/echarts/bar/sample1', '', '', '1', '', '1', '2017-06-04 12:55:29', '1', '2017-06-04 12:55:29', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('4f51b2fa967a4a7b86d4abc9e0fd5f7d', '4', '0,1,3,4,', '数据规则', '190', '', '', '', '0', 'sys:role:datarule', '1', '2017-04-08 21:59:25', '1', '2017-04-09 17:36:41', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('5', '4', '0,1,3,4,', '增加', '30', '', '', '', '0', 'sys:menu:add', '1', '2013-05-27 08:00:00', '1', '2015-12-20 19:00:22', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('5239527958e94d418997b584b85d8b80', '14', '0,1,3,14,', '删除', '100', '', '', '', '0', 'sys:area:del', '1', '2015-12-24 21:37:13', '1', '2015-12-24 21:37:13', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('56', '27', '0,1,27,', '文件管理', '31', '/../static/plugin/ckfinder/ckfinder.html', '', 'icon-zoom-out', '1', '', '1', '2013-05-27 08:00:00', '1', '2017-01-20 19:54:24', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('56e274e0ec1c41298e19ab46cf4e001f', '1', '0,1,', '常用工具', '140', '', '', 'fa fa-wrench', '1', '', '1', '2016-03-03 16:30:04', '1', '2017-04-04 22:21:29', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('57', '56', '0,1,27,40,56,', '查看', '40', null, null, null, '0', 'cms:ckfinder:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('57f1f00d6cb14819bef388acd10e6f5a', '68', '0,1,67,68,', '删除', '60', '', '', '', '0', 'sys:log:del', '1', '2015-12-25 20:25:55', '1', '2015-12-25 20:25:55', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('57f68b11091142b386bb4f28f1c01f57', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '日程图', '15520', '/echarts/heatmap/sample', '', '', '1', '', '1', '2017-06-04 12:59:02', '1', '2017-06-04 16:03:32', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('58', '56', '0,1,27,40,56,', '上传', '30', null, null, null, '0', 'cms:ckfinder:upload', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('59', '56', '0,1,27,40,56,', '修改', '50', null, null, null, '0', 'cms:ckfinder:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('6', '4', '0,1,3,4,', '修改', '40', null, null, null, '0', 'sys:menu:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('62ec1194dc224c989c286aa8e8999206', '9a6bd94036984f82890c2d10ac8a5880', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,9a6bd94036984f82890c2d10ac8a5880,', '例二', '60', '/echarts/line/sample2', '', '', '1', '', '1', '2017-06-04 12:59:56', '1', '2017-06-04 12:59:56', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('6509eed6eb634030a46723a18814035c', '7', '0,1,3,7,', '分配用户', '100', '', '', '', '0', 'sys:role:assign', '1', '2015-12-23 21:35:37', '1', '2015-12-23 21:53:23', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('67', '1', '0,1,', '系统监控', '70', '', '', 'fa fa-video-camera', '1', '', '1', '2013-06-03 08:00:00', '1', '2017-04-04 22:21:09', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('68', '67', '0,1,67,', '日志查询', '30', '/sys/log', '', '', '1', 'sys:log:list', '1', '2013-06-03 08:00:00', '1', '2017-04-11 16:46:10', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('68f9151151174868ab436e11e03bf548', '4', '0,1,3,4,', '删除', '70', '', '', '', '0', 'sys:menu:del', '1', '2015-12-20 19:01:16', '1', '2015-12-20 19:03:05', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('6a42111d5fc0449498bbcf3c78d81262', '09b08f1063504ab2ae04a9674903284c', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,09b08f1063504ab2ae04a9674903284c,', '例三', '90', '/echarts/bar/sample3', '', '', '1', '', '1', '2017-06-04 12:56:03', '1', '2017-06-04 12:56:03', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('6c672b854d2b4821b89297640df5fc26', '82', '0,1,79,82,', '同步数据库', '180', '', '', '', '0', 'gen:genTable:synchDb', '1', '2016-01-07 11:31:00', '1', '2017-04-11 09:46:32', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('6d3a6777693f47c98e9b3051cacbcfdb', '10', '0,1,3,10,', '增加', '70', '', '', '', '0', 'sys:dict:add', '1', '2015-12-24 22:23:39', '1', '2015-12-24 22:24:22', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('6e241160631d4dd4a36653cc83cd0a51', '9a6bd94036984f82890c2d10ac8a5880', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,9a6bd94036984f82890c2d10ac8a5880,', '例五', '150', '/echarts/line/sample5', '', '', '1', '', '1', '2017-06-04 13:00:36', '1', '2017-06-04 13:00:36', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('7', '3', '0,1,3,', '角色管理', '50', '/sys/role/', '', '', '1', 'sys:role:list', '1', '2013-05-27 08:00:00', '1', '2017-04-11 16:45:39', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('70a198c6ea674956adca8212b3f6291d', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '饼图', '15670', '', '', '', '1', '', '1', '2017-06-04 13:01:42', '1', '2017-06-04 13:01:42', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('79', '1', '0,1,', '代码生成', '30', '', '', 'fa fa-th', '1', '', '1', '2013-10-16 08:00:00', '1', '2017-04-04 22:19:16', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('79f0ffa47dbe43ffa8824d97612d344f', '4', '0,1,3,4,', '保存排序', '100', '', '', '', '0', 'sys:menu:updateSort', '1', '2015-12-20 19:02:08', '1', '2015-12-20 19:02:08', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('79fca849d3da4a82a4ade3f6b9f45126', '20', '0,1,3,20,', '删除', '100', '', '', '', '0', 'sys:user:del', '1', '2015-12-19 21:47:44', '1', '2015-12-19 21:48:52', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('7d4b83f739aa4caf81c4f4cf298fd8a6', '70a198c6ea674956adca8212b3f6291d', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,70a198c6ea674956adca8212b3f6291d,', '例一', '30', '/echarts/pie/sample1', '', '', '1', '', '1', '2017-06-04 13:01:51', '1', '2017-06-04 13:01:51', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('7d63df6a91ef4f258616cb58a556caaf', 'fe358352e9bc4ce99a7a4a3169e6145e', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,fe358352e9bc4ce99a7a4a3169e6145e,', '增加', '30', null, null, null, '0', 'echarts:other:testPieClass:add', '1', '2017-06-04 16:18:33', '1', '2017-06-04 16:18:33', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('7fe0397a90214f49adc9bbbe48e5ab69', '1', '0,1,', '统计报表', '10', '', '', 'fa fa-area-chart', '1', '', '1', '2016-05-26 08:55:24', '1', '2017-04-11 09:43:36', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('8', '7', '0,1,3,7,', '查看', '30', null, null, null, '0', 'sys:role:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('82', '79', '0,1,79,', '表单配置', '20', '/gen/genTable', '', '', '1', 'gen:genTable:list', '1', '2013-10-16 08:00:00', '1', '2017-03-17 22:26:25', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('84', '67', '0,1,67,', '连接池监视', '40', '/../druid', null, null, '1', null, '1', '2013-10-18 08:00:00', '1', '2013-10-18 08:00:00', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('864dd45b673545cda5722a81f2d8c64d', '9e01c22444ce4ffca35911daae45d58a', '0,1,67,9e01c22444ce4ffca35911daae45d58a,', '编辑', '90', null, null, null, '0', 'monitor:scheduleJob:edit', '1', '2017-02-02 22:25:10', '1', '2017-02-02 22:25:10', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('8930e4aad1ba4a1c958d303968d8c442', '17', '0,1,3,17,', '删除', '100', '', '', '', '0', 'sys:office:del', '1', '2015-12-20 21:19:16', '1', '2015-12-20 21:19:16', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('8dddb47ca808433fb59113fa9341a8d3', 'a8e8837f9e974dd797ac199f101047ee', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,a8e8837f9e974dd797ac199f101047ee,', '例一', '30', '/echarts/scatter/sample1', '', '', '1', '', '1', '2017-06-04 13:03:25', '1', '2017-06-04 13:03:25', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('8efc9686dc364f6fae71892305613b66', '9a6bd94036984f82890c2d10ac8a5880', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,9a6bd94036984f82890c2d10ac8a5880,', '例三', '90', '/echarts/line/sample3', '', '', '1', '', '1', '2017-06-04 13:00:08', '1', '2017-06-04 13:00:08', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('8ff1c0c8732b4a33980e0e7bc9f08282', '70a198c6ea674956adca8212b3f6291d', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,70a198c6ea674956adca8212b3f6291d,', '例二', '60', '/echarts/pie/sample2', '', '', '1', '', '1', '2017-06-04 13:02:01', '1', '2017-06-04 13:02:01', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('9', '7', '0,1,3,7,', '修改', '40', null, null, null, '0', 'sys:role:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('90b0b138bad0492d84fe09b91e410a09', '9e01c22444ce4ffca35911daae45d58a', '0,1,67,9e01c22444ce4ffca35911daae45d58a,', '增加', '30', null, null, null, '0', 'monitor:scheduleJob:add', '1', '2017-02-02 22:25:10', '1', '2017-02-02 22:25:10', null, '0', '2');
INSERT INTO `sys_menu` VALUES ('91aa429a6cdc4a9b954d84ff1456934b', '68', '0,1,67,68,', '查看', '30', '', '', '', '0', 'sys:log:view', '1', '2015-12-25 20:25:25', '1', '2015-12-25 20:25:25', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('91df0f548b9e4a149a627deaeeae1f16', 'fe358352e9bc4ce99a7a4a3169e6145e', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,fe358352e9bc4ce99a7a4a3169e6145e,', '导入', '150', null, null, null, '0', 'echarts:other:testPieClass:import', '1', '2017-06-04 16:18:33', '1', '2017-06-04 16:18:33', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('92a3ed30c82f4b3f814e4647bb9efa0b', 'fe358352e9bc4ce99a7a4a3169e6145e', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,fe358352e9bc4ce99a7a4a3169e6145e,', '编辑', '90', null, null, null, '0', 'echarts:other:testPieClass:edit', '1', '2017-06-04 16:18:33', '1', '2017-06-04 16:18:33', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('9a6bd94036984f82890c2d10ac8a5880', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '折线图', '15610', '', '', '', '1', '', '1', '2017-06-04 12:59:24', '1', '2017-06-04 12:59:24', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('9bc1aa1053144a608b73f6fbd841d1c6', '10', '0,1,3,10,', '删除', '100', '', '', '', '0', 'sys:dict:del', '1', '2015-12-24 22:24:04', '1', '2015-12-24 22:24:31', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('9e01c22444ce4ffca35911daae45d58a', '67', '0,1,67,', '定时任务', '130', '/monitor/scheduleJob', null, '', '1', 'monitor:scheduleJob:list', '1', '2017-02-02 22:25:10', '1', '2017-02-02 22:25:10', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('a3127f0822fb4b13b319930c3b1ec9fe', 'a8e8837f9e974dd797ac199f101047ee', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,a8e8837f9e974dd797ac199f101047ee,', '例三', '90', '/echarts/scatter/sample3', '', '', '1', '', '1', '2017-06-04 13:03:44', '1', '2017-06-04 13:03:44', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('a49d6b19fff2440388712b0fe6cfdaff', '9e01c22444ce4ffca35911daae45d58a', '0,1,67,9e01c22444ce4ffca35911daae45d58a,', '立即执行一次', '180', '', '', '', '0', 'monitor:scheduleJob:startNow', '1', '2017-02-02 22:25:10', '1', '2017-02-05 10:28:15', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('a4c3dcee6cbc4fc6a0bf617d8619edf3', '17', '0,1,3,17,', '增加', '70', '', '', '', '0', 'sys:office:add', '1', '2015-12-20 21:18:52', '1', '2015-12-20 21:18:52', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('a8e8837f9e974dd797ac199f101047ee', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '点状图', '15700', '', '', '', '1', '', '1', '2017-06-04 13:03:12', '1', '2017-06-04 13:03:12', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('aa38d6a6ad3e4daf88359365fac3eefb', '82', '0,1,79,82,', '自定义java对象', '240', '', '', '', '0', 'gen:genCustomObj:list,gen:genCustomObj:add,gen:genCustomObj:del,gen:genCustomObj:edit', '1', '2017-02-15 15:40:18', '1', '2017-04-11 09:46:43', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('ae8d0bb4f4154d348e8b95e4496e838a', '9e01c22444ce4ffca35911daae45d58a', '0,1,67,9e01c22444ce4ffca35911daae45d58a,', '恢复', '150', '', '', '', '0', 'monitor:scheduleJob:resume', '1', '2017-02-02 22:25:10', '1', '2017-02-05 10:27:50', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('b9a776f5d7194406bb466388e3af9d08', '20', '0,1,3,20,', '导出', '160', '', '', '', '0', 'sys:user:export', '1', '2015-12-19 21:48:34', '1', '2015-12-19 21:48:34', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('c3de25a76785419b8a6820db3935941d', '82', '0,1,79,82,', '导入', '150', '', '', '', '0', 'gen:genTable:importDb', '1', '2016-01-07 11:30:25', '1', '2017-04-11 09:46:27', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('c6e0080e06014abd9240f870aadf3200', '14', '0,1,3,14,', '增加', '70', '', '', '', '0', 'sys:area:add', '1', '2015-12-24 21:35:39', '1', '2015-12-24 21:35:39', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('cfed96f1c1a04a9cb97abffc858acb67', 'a8e8837f9e974dd797ac199f101047ee', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,a8e8837f9e974dd797ac199f101047ee,', '例二', '60', '/echarts/scatter/sample2', '', '', '1', '', '1', '2017-06-04 13:03:35', '1', '2017-06-04 13:03:35', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('d3f1b6f292904ef5b95f7800cc777a48', '82', '0,1,79,82,', '查看', '30', '', '', '', '0', 'gen:genTable:view,gen:genTableColumn:view', '1', '2016-01-07 11:26:42', '1', '2017-04-11 09:46:03', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('d64d25d7b3014f9ba7736867cb2ffc43', '82', '0,1,79,82,', '生成代码', '210', '', '', '', '0', 'gen:genTable:genCode', '1', '2016-01-07 11:31:24', '1', '2017-04-11 09:46:38', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('d75f64438d994fc4830b1b3d138cde32', '82', '0,1,79,82,', '删除', '120', '', '', '', '0', 'gen:genTable:del', '1', '2016-01-07 11:29:23', '1', '2017-04-11 09:46:22', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('de7c50d276454f80881c41a096ecf55c', '7', '0,1,3,7,', '删除', '160', '', '', '', '0', 'sys:role:del', '1', '2015-12-23 21:59:46', '1', '2015-12-23 21:59:46', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('e03f8b6a5e454addb04fc08033b6f60b', '82', '0,1,79,82,', '增加', '90', '', '', '', '0', 'gen:genTable:add', '1', '2016-01-07 11:28:59', '1', '2017-04-11 09:46:17', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('e25c7b2e1f0e4327896feb2736107355', '09b08f1063504ab2ae04a9674903284c', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,09b08f1063504ab2ae04a9674903284c,', '例二', '60', '/echarts/bar/sample2', '', '', '1', '', '1', '2017-06-04 12:55:50', '1', '2017-06-04 12:55:50', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('e46555e269b84e2697857bdbb73f6676', '56e274e0ec1c41298e19ab46cf4e001f', '0,1,56e274e0ec1c41298e19ab46cf4e001f,', 'swagger', '90', '/swagger-ui.html', '', '', '1', '', '1', '2016-03-05 10:00:01', '1', '2017-06-12 01:37:26', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('e824b7c20bb34c9ca9ad023e8873e67b', '82', '0,1,79,82,', '编辑', '60', '', '', '', '0', 'gen:genTable:edit,gen:genTableColumn:edit', '1', '2016-01-07 11:27:55', '1', '2017-04-11 09:46:11', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('ec7cf7a144a440cab217aabd4ffb7788', '4', '0,1,3,4,', '查看', '130', '', '', '', '0', 'sys:menu:view', '1', '2015-12-20 19:02:54', '1', '2015-12-20 19:02:54', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('edabaa21592247abb48d9d6b975a7aa3', 'fe358352e9bc4ce99a7a4a3169e6145e', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,fe358352e9bc4ce99a7a4a3169e6145e,', '导出', '180', null, null, null, '0', 'echarts:other:testPieClass:export', '1', '2017-06-04 16:18:33', '1', '2017-06-04 16:18:33', null, '0', '1');
INSERT INTO `sys_menu` VALUES ('f18fac5c4e6145528f3c1d87dbcb37d5', '67', '0,1,67,', '系统监控管理', '70', '/monitor/info', '', '', '1', '', '1', '2016-02-02 22:49:07', '1', '2016-02-02 23:15:07', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('f34887a78fa245c1977603ca7dc98e11', '20', '0,1,3,20,', '导入', '130', '', '', '', '0', 'sys:user:import', '1', '2015-12-19 21:48:13', '1', '2015-12-19 21:48:44', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('f49eb044c0784e4d846ef792b82dc590', '7fe0397a90214f49adc9bbbe48e5ab69', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,', '仪表图', '15490', '/echarts/gauge/sample', '', '', '1', '', '1', '2017-06-04 12:58:36', '1', '2017-06-04 12:58:36', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('f93f9a3a2226461dace3b8992cf055ba', '7', '0,1,3,7,', '权限设置', '130', '', '', '', '0', 'sys:role:auth', '1', '2015-12-23 21:36:06', '1', '2015-12-23 21:36:06', '', '0', '2');
INSERT INTO `sys_menu` VALUES ('fa803c5a6ef14128a197a467afc0c529', '02c03f3a6bb34f9fab7aeb7477d672e6', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,02c03f3a6bb34f9fab7aeb7477d672e6,', '例二', '60', '/echarts/funnel/sample2', '', '', '1', '', '1', '2017-06-04 12:57:45', '1', '2017-06-04 12:57:45', '', '0', '1');
INSERT INTO `sys_menu` VALUES ('fe358352e9bc4ce99a7a4a3169e6145e', '268c55d12ee34c5c8b717aa24cc3af73', '0,1,7fe0397a90214f49adc9bbbe48e5ab69,268c55d12ee34c5c8b717aa24cc3af73,', '综合报表', '15490', '/echarts/other/testPieClass', '', '', '1', 'echarts:other:testPieClass:list', '1', '2017-06-04 16:18:33', '1', '2017-06-04 17:19:40', '', '0', '1');

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `area_id` varchar(64) NOT NULL COMMENT '归属区域',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) NOT NULL COMMENT '机构类型',
  `grade` char(1) NOT NULL COMMENT '机构等级',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `USEABLE` varchar(64) DEFAULT NULL COMMENT '是否启用',
  `PRIMARY_PERSON` varchar(64) DEFAULT NULL COMMENT '主负责人',
  `DEPUTY_PERSON` varchar(64) DEFAULT NULL COMMENT '副负责人',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_office_del_flag` (`del_flag`) USING BTREE,
  KEY `sys_office_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '0', '0,', '总公司', '10', '4d8ec70b0e0c4c97af07b97c9a906c40', '100000', '1', '1', '', '', '', '', '', '', '1', '', '', '1', '2013-05-27 08:00:00', '1', '2015-11-11 17:40:49', '', '0');
INSERT INTO `sys_office` VALUES ('4', '1', '0,1,', '财务部', '30', 'aa23de04b47c4af086f23d68b64383bf', '100003', '2', '1', '', '', '', '', '', '', '1', '', '', '1', '2013-05-27 08:00:00', '1', '2017-04-04 21:04:25', 'ccc', '0');
INSERT INTO `sys_office` VALUES ('5', '1', '0,1,', '技术部', '40', '5', '100004', '2', '1', '', '', '', '', '', '', '1', '', '', '1', '2013-05-27 08:00:00', '1', '2017-02-06 12:38:31', 'xxxx1111', '0');
INSERT INTO `sys_office` VALUES ('6a642e140e40496a8d467c646b8e935e', '1', '0,1,', '市场部', '30', '17e8e72326574a0ea94b15d6eeddbb6d', '1000', '2', '1', '', '', '', '', '', '', '1', '', '', '1', '2015-12-03 18:10:13', '1', '2016-01-11 23:10:10', '', '0');
INSERT INTO `sys_office` VALUES ('e0ef8af9cae6416f8bb359714a1b4244', '1', '0,1,', '行政部', '30', '5', '', '2', '1', '', '', '', '', '', '', '1', '', '', '1', '2015-11-11 17:41:41', '1', '2017-01-17 21:35:43', 'xxx', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enname` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `is_sys` varchar(64) DEFAULT NULL COMMENT '是否系统数据',
  `useable` varchar(64) DEFAULT NULL COMMENT '是否可用',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`) USING BTREE,
  KEY `sys_role_enname` (`enname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '5', '管理员', 'system', 'assignment', '0', '1', '1', '2015-11-11 15:59:43', '1', '2017-04-11 15:40:55', '', '0');
INSERT INTO `sys_role` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '5', '部门管理员', 'depart', 'assignment', '0', '1', '1', '2015-11-13 10:54:36', '1', '2017-04-11 15:38:10', '', '0');

-- ----------------------------
-- Table structure for sys_role_datarule
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_datarule`;
CREATE TABLE `sys_role_datarule` (
  `role_id` varchar(64) NOT NULL DEFAULT '' COMMENT '角色id',
  `datarule_id` varchar(64) NOT NULL COMMENT '数据规则id',
  PRIMARY KEY (`role_id`,`datarule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_role_datarule
-- ----------------------------
INSERT INTO `sys_role_datarule` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '09876c1700864719a89aab9e69c3a761');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '10');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '11');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '12');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '14');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '15');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '16');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '17');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '18');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '19');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '20');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '21');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '22');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '27');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '29');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '2a0f940fbe304a05a6b4040ddf6df279');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '3');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '30');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '3945952858c54ff6b928265e76416aa2');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '4');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '485c63cf6af1448880bb35b7b3f2bb1b');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '5');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '54afcfd125024b6eaeab9c5c3cea0009');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '56');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '57');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '58');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '59');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '5dfd09e5790b41388b8691ea9993eba5');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '6');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '63');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '64');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '65');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '66');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '67');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '68');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '68f9151151174868ab436e11e03bf548');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '69');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '7');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '70');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '72');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '73');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '74');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '75');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '79');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '79f0ffa47dbe43ffa8824d97612d344f');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '79fca849d3da4a82a4ade3f6b9f45126');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '7bd4484bd13c441395e44ab86772da00');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '8');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '80');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '81');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '82');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '83');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '84');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '86');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '87');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '88');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '8930e4aad1ba4a1c958d303968d8c442');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', '9');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'a4c3dcee6cbc4fc6a0bf617d8619edf3');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'b9a776f5d7194406bb466388e3af9d08');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'c99753f4ad0a4a458337b255c3b49095');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'df7ce823c5b24ff9bada43d992f373e2');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'e2565667b9a745eb870debfd0830c46f');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'e3e131bb489f43dd865280e4f5932b6d');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'ec7cf7a144a440cab217aabd4ffb7788');
INSERT INTO `sys_role_menu` VALUES ('03fcd9c5c28948eb827fcd3b852494a6', 'f34887a78fa245c1977603ca7dc98e11');
INSERT INTO `sys_role_menu` VALUES ('1', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('1', '0fde32c6c8204f92a6846714821491f7');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '11');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '13');
INSERT INTO `sys_role_menu` VALUES ('1', '14');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '17');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '19');
INSERT INTO `sys_role_menu` VALUES ('1', '1dc794e30998444489db19eec02915c3');
INSERT INTO `sys_role_menu` VALUES ('1', '20');
INSERT INTO `sys_role_menu` VALUES ('1', '21');
INSERT INTO `sys_role_menu` VALUES ('1', '22');
INSERT INTO `sys_role_menu` VALUES ('1', '27');
INSERT INTO `sys_role_menu` VALUES ('1', '29');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '30');
INSERT INTO `sys_role_menu` VALUES ('1', '31');
INSERT INTO `sys_role_menu` VALUES ('1', '32');
INSERT INTO `sys_role_menu` VALUES ('1', '33');
INSERT INTO `sys_role_menu` VALUES ('1', '34');
INSERT INTO `sys_role_menu` VALUES ('1', '35');
INSERT INTO `sys_role_menu` VALUES ('1', '36');
INSERT INTO `sys_role_menu` VALUES ('1', '37');
INSERT INTO `sys_role_menu` VALUES ('1', '38');
INSERT INTO `sys_role_menu` VALUES ('1', '39');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '40');
INSERT INTO `sys_role_menu` VALUES ('1', '41');
INSERT INTO `sys_role_menu` VALUES ('1', '42');
INSERT INTO `sys_role_menu` VALUES ('1', '43');
INSERT INTO `sys_role_menu` VALUES ('1', '44');
INSERT INTO `sys_role_menu` VALUES ('1', '45');
INSERT INTO `sys_role_menu` VALUES ('1', '46');
INSERT INTO `sys_role_menu` VALUES ('1', '47');
INSERT INTO `sys_role_menu` VALUES ('1', '48');
INSERT INTO `sys_role_menu` VALUES ('1', '49');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '50');
INSERT INTO `sys_role_menu` VALUES ('1', '51');
INSERT INTO `sys_role_menu` VALUES ('1', '52');
INSERT INTO `sys_role_menu` VALUES ('1', '53');
INSERT INTO `sys_role_menu` VALUES ('1', '54');
INSERT INTO `sys_role_menu` VALUES ('1', '55');
INSERT INTO `sys_role_menu` VALUES ('1', '56');
INSERT INTO `sys_role_menu` VALUES ('1', '57');
INSERT INTO `sys_role_menu` VALUES ('1', '58');
INSERT INTO `sys_role_menu` VALUES ('1', '59');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '60');
INSERT INTO `sys_role_menu` VALUES ('1', '61');
INSERT INTO `sys_role_menu` VALUES ('1', '63');
INSERT INTO `sys_role_menu` VALUES ('1', '64');
INSERT INTO `sys_role_menu` VALUES ('1', '65');
INSERT INTO `sys_role_menu` VALUES ('1', '66');
INSERT INTO `sys_role_menu` VALUES ('1', '67');
INSERT INTO `sys_role_menu` VALUES ('1', '68');
INSERT INTO `sys_role_menu` VALUES ('1', '69');
INSERT INTO `sys_role_menu` VALUES ('1', '6d7e99b2edf44c96b8bbb9c1ef6dfe66');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '70');
INSERT INTO `sys_role_menu` VALUES ('1', '72');
INSERT INTO `sys_role_menu` VALUES ('1', '73');
INSERT INTO `sys_role_menu` VALUES ('1', '74');
INSERT INTO `sys_role_menu` VALUES ('1', '79');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '80');
INSERT INTO `sys_role_menu` VALUES ('1', '81');
INSERT INTO `sys_role_menu` VALUES ('1', '82');
INSERT INTO `sys_role_menu` VALUES ('1', '84');
INSERT INTO `sys_role_menu` VALUES ('1', '872d2edc3b61405eb1448355e8f2c556');
INSERT INTO `sys_role_menu` VALUES ('1', '88');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('1', 'dcea8b88d08c4723878d372d2139448d');
INSERT INTO `sys_role_menu` VALUES ('1', 'df7ce823c5b24ff9bada43d992f373e2');
INSERT INTO `sys_role_menu` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '1');
INSERT INTO `sys_role_menu` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '7fe0397a90214f49adc9bbbe48e5ab69');
INSERT INTO `sys_role_menu` VALUES ('2', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('2', '0fde32c6c8204f92a6846714821491f7');
INSERT INTO `sys_role_menu` VALUES ('2', '10');
INSERT INTO `sys_role_menu` VALUES ('2', '11');
INSERT INTO `sys_role_menu` VALUES ('2', '12');
INSERT INTO `sys_role_menu` VALUES ('2', '13');
INSERT INTO `sys_role_menu` VALUES ('2', '14');
INSERT INTO `sys_role_menu` VALUES ('2', '15');
INSERT INTO `sys_role_menu` VALUES ('2', '16');
INSERT INTO `sys_role_menu` VALUES ('2', '17');
INSERT INTO `sys_role_menu` VALUES ('2', '18');
INSERT INTO `sys_role_menu` VALUES ('2', '19');
INSERT INTO `sys_role_menu` VALUES ('2', '1dc794e30998444489db19eec02915c3');
INSERT INTO `sys_role_menu` VALUES ('2', '20');
INSERT INTO `sys_role_menu` VALUES ('2', '21');
INSERT INTO `sys_role_menu` VALUES ('2', '22');
INSERT INTO `sys_role_menu` VALUES ('2', '27');
INSERT INTO `sys_role_menu` VALUES ('2', '29');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '30');
INSERT INTO `sys_role_menu` VALUES ('2', '31');
INSERT INTO `sys_role_menu` VALUES ('2', '32');
INSERT INTO `sys_role_menu` VALUES ('2', '33');
INSERT INTO `sys_role_menu` VALUES ('2', '34');
INSERT INTO `sys_role_menu` VALUES ('2', '35');
INSERT INTO `sys_role_menu` VALUES ('2', '36');
INSERT INTO `sys_role_menu` VALUES ('2', '37');
INSERT INTO `sys_role_menu` VALUES ('2', '38');
INSERT INTO `sys_role_menu` VALUES ('2', '39');
INSERT INTO `sys_role_menu` VALUES ('2', '4');
INSERT INTO `sys_role_menu` VALUES ('2', '40');
INSERT INTO `sys_role_menu` VALUES ('2', '41');
INSERT INTO `sys_role_menu` VALUES ('2', '42');
INSERT INTO `sys_role_menu` VALUES ('2', '43');
INSERT INTO `sys_role_menu` VALUES ('2', '44');
INSERT INTO `sys_role_menu` VALUES ('2', '45');
INSERT INTO `sys_role_menu` VALUES ('2', '46');
INSERT INTO `sys_role_menu` VALUES ('2', '47');
INSERT INTO `sys_role_menu` VALUES ('2', '48');
INSERT INTO `sys_role_menu` VALUES ('2', '49');
INSERT INTO `sys_role_menu` VALUES ('2', '5');
INSERT INTO `sys_role_menu` VALUES ('2', '50');
INSERT INTO `sys_role_menu` VALUES ('2', '51');
INSERT INTO `sys_role_menu` VALUES ('2', '52');
INSERT INTO `sys_role_menu` VALUES ('2', '53');
INSERT INTO `sys_role_menu` VALUES ('2', '54');
INSERT INTO `sys_role_menu` VALUES ('2', '55');
INSERT INTO `sys_role_menu` VALUES ('2', '56');
INSERT INTO `sys_role_menu` VALUES ('2', '57');
INSERT INTO `sys_role_menu` VALUES ('2', '58');
INSERT INTO `sys_role_menu` VALUES ('2', '59');
INSERT INTO `sys_role_menu` VALUES ('2', '6');
INSERT INTO `sys_role_menu` VALUES ('2', '60');
INSERT INTO `sys_role_menu` VALUES ('2', '61');
INSERT INTO `sys_role_menu` VALUES ('2', '63');
INSERT INTO `sys_role_menu` VALUES ('2', '64');
INSERT INTO `sys_role_menu` VALUES ('2', '65');
INSERT INTO `sys_role_menu` VALUES ('2', '66');
INSERT INTO `sys_role_menu` VALUES ('2', '67');
INSERT INTO `sys_role_menu` VALUES ('2', '68');
INSERT INTO `sys_role_menu` VALUES ('2', '69');
INSERT INTO `sys_role_menu` VALUES ('2', '6d7e99b2edf44c96b8bbb9c1ef6dfe66');
INSERT INTO `sys_role_menu` VALUES ('2', '7');
INSERT INTO `sys_role_menu` VALUES ('2', '70');
INSERT INTO `sys_role_menu` VALUES ('2', '72');
INSERT INTO `sys_role_menu` VALUES ('2', '73');
INSERT INTO `sys_role_menu` VALUES ('2', '74');
INSERT INTO `sys_role_menu` VALUES ('2', '79');
INSERT INTO `sys_role_menu` VALUES ('2', '8');
INSERT INTO `sys_role_menu` VALUES ('2', '80');
INSERT INTO `sys_role_menu` VALUES ('2', '81');
INSERT INTO `sys_role_menu` VALUES ('2', '82');
INSERT INTO `sys_role_menu` VALUES ('2', '84');
INSERT INTO `sys_role_menu` VALUES ('2', '85');
INSERT INTO `sys_role_menu` VALUES ('2', '88');
INSERT INTO `sys_role_menu` VALUES ('2', '9');
INSERT INTO `sys_role_menu` VALUES ('2', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('2', 'dcea8b88d08c4723878d372d2139448d');
INSERT INTO `sys_role_menu` VALUES ('2', 'df7ce823c5b24ff9bada43d992f373e2');
INSERT INTO `sys_role_menu` VALUES ('3', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('3', '10');
INSERT INTO `sys_role_menu` VALUES ('3', '11');
INSERT INTO `sys_role_menu` VALUES ('3', '12');
INSERT INTO `sys_role_menu` VALUES ('3', '13');
INSERT INTO `sys_role_menu` VALUES ('3', '14');
INSERT INTO `sys_role_menu` VALUES ('3', '15');
INSERT INTO `sys_role_menu` VALUES ('3', '16');
INSERT INTO `sys_role_menu` VALUES ('3', '17');
INSERT INTO `sys_role_menu` VALUES ('3', '18');
INSERT INTO `sys_role_menu` VALUES ('3', '19');
INSERT INTO `sys_role_menu` VALUES ('3', '20');
INSERT INTO `sys_role_menu` VALUES ('3', '21');
INSERT INTO `sys_role_menu` VALUES ('3', '22');
INSERT INTO `sys_role_menu` VALUES ('3', '27');
INSERT INTO `sys_role_menu` VALUES ('3', '29');
INSERT INTO `sys_role_menu` VALUES ('3', '3');
INSERT INTO `sys_role_menu` VALUES ('3', '30');
INSERT INTO `sys_role_menu` VALUES ('3', '31');
INSERT INTO `sys_role_menu` VALUES ('3', '32');
INSERT INTO `sys_role_menu` VALUES ('3', '33');
INSERT INTO `sys_role_menu` VALUES ('3', '34');
INSERT INTO `sys_role_menu` VALUES ('3', '35');
INSERT INTO `sys_role_menu` VALUES ('3', '36');
INSERT INTO `sys_role_menu` VALUES ('3', '37');
INSERT INTO `sys_role_menu` VALUES ('3', '38');
INSERT INTO `sys_role_menu` VALUES ('3', '39');
INSERT INTO `sys_role_menu` VALUES ('3', '4');
INSERT INTO `sys_role_menu` VALUES ('3', '40');
INSERT INTO `sys_role_menu` VALUES ('3', '41');
INSERT INTO `sys_role_menu` VALUES ('3', '42');
INSERT INTO `sys_role_menu` VALUES ('3', '43');
INSERT INTO `sys_role_menu` VALUES ('3', '44');
INSERT INTO `sys_role_menu` VALUES ('3', '45');
INSERT INTO `sys_role_menu` VALUES ('3', '46');
INSERT INTO `sys_role_menu` VALUES ('3', '47');
INSERT INTO `sys_role_menu` VALUES ('3', '48');
INSERT INTO `sys_role_menu` VALUES ('3', '49');
INSERT INTO `sys_role_menu` VALUES ('3', '5');
INSERT INTO `sys_role_menu` VALUES ('3', '50');
INSERT INTO `sys_role_menu` VALUES ('3', '51');
INSERT INTO `sys_role_menu` VALUES ('3', '52');
INSERT INTO `sys_role_menu` VALUES ('3', '53');
INSERT INTO `sys_role_menu` VALUES ('3', '54');
INSERT INTO `sys_role_menu` VALUES ('3', '55');
INSERT INTO `sys_role_menu` VALUES ('3', '56');
INSERT INTO `sys_role_menu` VALUES ('3', '57');
INSERT INTO `sys_role_menu` VALUES ('3', '58');
INSERT INTO `sys_role_menu` VALUES ('3', '59');
INSERT INTO `sys_role_menu` VALUES ('3', '6');
INSERT INTO `sys_role_menu` VALUES ('3', '60');
INSERT INTO `sys_role_menu` VALUES ('3', '61');
INSERT INTO `sys_role_menu` VALUES ('3', '63');
INSERT INTO `sys_role_menu` VALUES ('3', '64');
INSERT INTO `sys_role_menu` VALUES ('3', '65');
INSERT INTO `sys_role_menu` VALUES ('3', '66');
INSERT INTO `sys_role_menu` VALUES ('3', '67');
INSERT INTO `sys_role_menu` VALUES ('3', '68');
INSERT INTO `sys_role_menu` VALUES ('3', '69');
INSERT INTO `sys_role_menu` VALUES ('3', '6d7e99b2edf44c96b8bbb9c1ef6dfe66');
INSERT INTO `sys_role_menu` VALUES ('3', '7');
INSERT INTO `sys_role_menu` VALUES ('3', '70');
INSERT INTO `sys_role_menu` VALUES ('3', '72');
INSERT INTO `sys_role_menu` VALUES ('3', '73');
INSERT INTO `sys_role_menu` VALUES ('3', '74');
INSERT INTO `sys_role_menu` VALUES ('3', '79');
INSERT INTO `sys_role_menu` VALUES ('3', '8');
INSERT INTO `sys_role_menu` VALUES ('3', '80');
INSERT INTO `sys_role_menu` VALUES ('3', '81');
INSERT INTO `sys_role_menu` VALUES ('3', '82');
INSERT INTO `sys_role_menu` VALUES ('3', '84');
INSERT INTO `sys_role_menu` VALUES ('3', '85');
INSERT INTO `sys_role_menu` VALUES ('3', '88');
INSERT INTO `sys_role_menu` VALUES ('3', '9');
INSERT INTO `sys_role_menu` VALUES ('3', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('3', 'dcea8b88d08c4723878d372d2139448d');
INSERT INTO `sys_role_menu` VALUES ('3', 'df7ce823c5b24ff9bada43d992f373e2');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '10');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '11');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '12');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '14');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '15');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '16');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '17');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '18');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '19');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '20');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '21');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '22');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '27');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '29');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '2a0f940fbe304a05a6b4040ddf6df279');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '3');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '30');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '3945952858c54ff6b928265e76416aa2');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '4');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '485c63cf6af1448880bb35b7b3f2bb1b');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '5');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '54afcfd125024b6eaeab9c5c3cea0009');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '56');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '57');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '58');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '59');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '5dfd09e5790b41388b8691ea9993eba5');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '6');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '63');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '64');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '65');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '66');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '67');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '68');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '68f9151151174868ab436e11e03bf548');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '69');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '7');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '70');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '72');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '73');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '74');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '75');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '79');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '79f0ffa47dbe43ffa8824d97612d344f');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '79fca849d3da4a82a4ade3f6b9f45126');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '7bd4484bd13c441395e44ab86772da00');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '8');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '80');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '81');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '82');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '83');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '84');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '85');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '86');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '87');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '88');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '8930e4aad1ba4a1c958d303968d8c442');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', '9');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'a4c3dcee6cbc4fc6a0bf617d8619edf3');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'b9a776f5d7194406bb466388e3af9d08');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'c99753f4ad0a4a458337b255c3b49095');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'df7ce823c5b24ff9bada43d992f373e2');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'e2565667b9a745eb870debfd0830c46f');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'e3e131bb489f43dd865280e4f5932b6d');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'ec7cf7a144a440cab217aabd4ffb7788');
INSERT INTO `sys_role_menu` VALUES ('78cf5521ff9e43ed80266adeff67d5ed', 'f34887a78fa245c1977603ca7dc98e11');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '10');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '11');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '12');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '14');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '15');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '16');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '17');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '18');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '19');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '20');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '21');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '22');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '27');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '29');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '2a0f940fbe304a05a6b4040ddf6df279');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '3');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '30');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '3945952858c54ff6b928265e76416aa2');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '3c1c639c76f14f6f9903b0143371ea09');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '4');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '485c63cf6af1448880bb35b7b3f2bb1b');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '5');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '54afcfd125024b6eaeab9c5c3cea0009');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '56');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '57');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '58');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '59');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '5dfd09e5790b41388b8691ea9993eba5');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '6');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '63');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '64');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '65');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '6509eed6eb634030a46723a18814035c');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '66');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '67');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '68');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '68f9151151174868ab436e11e03bf548');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '69');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '7');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '70');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '72');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '73');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '74');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '75');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '79');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '79f0ffa47dbe43ffa8824d97612d344f');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '79fca849d3da4a82a4ade3f6b9f45126');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '7bd4484bd13c441395e44ab86772da00');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '8');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '80');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '81');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '82');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '83');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '84');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '86');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '87');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '88');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '8930e4aad1ba4a1c958d303968d8c442');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', '9');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'a4c3dcee6cbc4fc6a0bf617d8619edf3');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'b9a776f5d7194406bb466388e3af9d08');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'c99753f4ad0a4a458337b255c3b49095');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'df7ce823c5b24ff9bada43d992f373e2');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'e2565667b9a745eb870debfd0830c46f');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'e3e131bb489f43dd865280e4f5932b6d');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'ec7cf7a144a440cab217aabd4ffb7788');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'f34887a78fa245c1977603ca7dc98e11');
INSERT INTO `sys_role_menu` VALUES ('91766cc228e34269a65f0564ba956bd7', 'f93f9a3a2226461dace3b8992cf055ba');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '0fde32c6c8204f92a6846714821491f7');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '10');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '11');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '12');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '13');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '14');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '15');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '16');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '17');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '18');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '19');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '1dc794e30998444489db19eec02915c3');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '20');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '21');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '22');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '27');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '29');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '3');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '30');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '31');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '32');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '33');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '34');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '35');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '36');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '37');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '38');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '39');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '4');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '40');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '41');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '42');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '43');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '44');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '45');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '46');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '47');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '48');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '49');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '5');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '50');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '51');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '52');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '53');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '54');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '55');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '56');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '57');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '58');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '59');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '5e72ae5a7d56478c8db2a7975883f367');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '6');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '60');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '61');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '63');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '64');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '65');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '66');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '67');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '68');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '69');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '6d7e99b2edf44c96b8bbb9c1ef6dfe66');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '7');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '70');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '72');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '73');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '74');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '79');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '8');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '80');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '81');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '82');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '84');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '88');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', '9');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', 'dcea8b88d08c4723878d372d2139448d');
INSERT INTO `sys_role_menu` VALUES ('a74b7da6e0e0458298316798e89e70ea', 'df7ce823c5b24ff9bada43d992f373e2');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '1');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '10');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '11');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '12');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '14');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '15');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '16');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '17');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '18');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '19');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '20');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '21');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '22');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '27');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '29');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '2a0f940fbe304a05a6b4040ddf6df279');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '3');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '3c1c639c76f14f6f9903b0143371ea09');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '4');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '4f51b2fa967a4a7b86d4abc9e0fd5f7d');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '5');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '5239527958e94d418997b584b85d8b80');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '56');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '57');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '58');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '59');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '6');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '6509eed6eb634030a46723a18814035c');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '68f9151151174868ab436e11e03bf548');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '6c672b854d2b4821b89297640df5fc26');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '6d3a6777693f47c98e9b3051cacbcfdb');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '7');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '79');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '79f0ffa47dbe43ffa8824d97612d344f');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '79fca849d3da4a82a4ade3f6b9f45126');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '7fe0397a90214f49adc9bbbe48e5ab69');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '8');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '82');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '8930e4aad1ba4a1c958d303968d8c442');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '9');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '9bc1aa1053144a608b73f6fbd841d1c6');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'a4c3dcee6cbc4fc6a0bf617d8619edf3');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'aa38d6a6ad3e4daf88359365fac3eefb');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'b9a776f5d7194406bb466388e3af9d08');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'c3de25a76785419b8a6820db3935941d');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'c6e0080e06014abd9240f870aadf3200');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'd3f1b6f292904ef5b95f7800cc777a48');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'd64d25d7b3014f9ba7736867cb2ffc43');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'd75f64438d994fc4830b1b3d138cde32');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'de7c50d276454f80881c41a096ecf55c');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'e03f8b6a5e454addb04fc08033b6f60b');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'e824b7c20bb34c9ca9ad023e8873e67b');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'ec7cf7a144a440cab217aabd4ffb7788');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'f34887a78fa245c1977603ca7dc98e11');
INSERT INTO `sys_role_menu` VALUES ('caacf61017114120bcf7bf1049b6d4c3', 'f93f9a3a2226461dace3b8992cf055ba');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '0b2ebd4d639e4c2b83c2dd0764522f24');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '0fde32c6c8204f92a6846714821491f7');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '10');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '11');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '12');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '13');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '14');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '15');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '16');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '17');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '18');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '19');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '1dc794e30998444489db19eec02915c3');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '20');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '21');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '22');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '27');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '29');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '3');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '30');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '31');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '32');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '33');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '34');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '35');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '36');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '37');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '38');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '39');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '4');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '40');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '41');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '42');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '43');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '44');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '45');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '46');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '47');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '48');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '49');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '5');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '50');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '51');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '52');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '53');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '54');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '55');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '56');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '57');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '58');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '59');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '6');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '60');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '61');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '63');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '64');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '65');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '66');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '67');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '68');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '69');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '6d7e99b2edf44c96b8bbb9c1ef6dfe66');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '7');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '70');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '72');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '73');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '74');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '79');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '8');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '80');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '81');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '82');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '84');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '88');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', '9');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', 'ba8092291b40482db8fe7fc006ea3d76');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', 'dcea8b88d08c4723878d372d2139448d');
INSERT INTO `sys_role_menu` VALUES ('f6d2f215ed734cc09273928acab6e136', 'df7ce823c5b24ff9bada43d992f373e2');

-- ----------------------------
-- Table structure for sys_schedule
-- ----------------------------
DROP TABLE IF EXISTS `sys_schedule`;
CREATE TABLE `sys_schedule` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '任务名',
  `t_group` varchar(64) DEFAULT NULL COMMENT '任务组',
  `expression` varchar(64) DEFAULT NULL COMMENT '定时规则',
  `status` varchar(64) DEFAULT NULL COMMENT '启用状态',
  `is_info` varchar(64) DEFAULT NULL COMMENT '通知用户',
  `classname` varchar(256) DEFAULT NULL COMMENT '任务类',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_schedule
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `company_id` varchar(64) DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `photo` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `qrcode` varchar(1000) DEFAULT NULL COMMENT '二维码',
  `sign` varchar(450) DEFAULT NULL COMMENT '个性签名',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`) USING BTREE,
  KEY `sys_user_login_name` (`login_name`) USING BTREE,
  KEY `sys_user_company_id` (`company_id`) USING BTREE,
  KEY `sys_user_update_date` (`update_date`) USING BTREE,
  KEY `sys_user_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', 'e0ef8af9cae6416f8bb359714a1b4244', 'admin', '0c0aedb49a6ec267780b1212342b275e4496f269af9bb61db8adf636', '1', 'admin', '', '123', '123', '/userfiles/1/images/iuling_logo.png', '127.0.0.1', '2017-09-14 01:50:32', '1', '1', '2013-05-27 08:00:00', '1', '2017-09-14 02:15:01', 'wwwwwwwwwwwwwwwww', '0', '/jeeplus/userfiles/1/qrcode/test.png', '你好啊111rrr');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('0414a338c137475e836df806ad3955cf', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('08b05703337a42ee963bae0561205825', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('0f7017853ec24648872e01fe9c930969', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('0fb8ebbff20a46029596806aa077d3c2', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('1', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('1', '5');
INSERT INTO `sys_user_role` VALUES ('1', '560744a781144a9f9775c346c32a44c4');
INSERT INTO `sys_user_role` VALUES ('1', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('1', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('10', 'f6d2f215ed734cc09273928acab6e136');
INSERT INTO `sys_user_role` VALUES ('106fa3a47e6240d48e2a55a5a7a725b6', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('10b794d4518945aca3dc38c6d287cc91', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('11', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('12', 'f6d2f215ed734cc09273928acab6e136');
INSERT INTO `sys_user_role` VALUES ('13', '5');
INSERT INTO `sys_user_role` VALUES ('14', '6');
INSERT INTO `sys_user_role` VALUES ('14951cd04b7d4d4ab5c27a70caa45253', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('14951cd04b7d4d4ab5c27a70caa45253', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('1624f3de35c9435a8f475d2ca584c510', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('1624f3de35c9435a8f475d2ca584c510', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('18c262ee56764d4fa1aac9bbaaa2b615', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('19e655ebcd844747aa0e46efe9870169', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('19e655ebcd844747aa0e46efe9870169', '5');
INSERT INTO `sys_user_role` VALUES ('19e655ebcd844747aa0e46efe9870169', '91766cc228e34269a65f0564ba956bd7');
INSERT INTO `sys_user_role` VALUES ('19e655ebcd844747aa0e46efe9870169', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('1c43a072d2b2414b8848df0724721ab2', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('1c43a072d2b2414b8848df0724721ab2', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('1cc1e9512af041628f8fdd05c8628a68', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('1e8149b33e774daa9a250f5a1a0ad23f', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('1e8149b33e774daa9a250f5a1a0ad23f', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('1e8149b33e774daa9a250f5a1a0ad23f', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('2', 'f6d2f215ed734cc09273928acab6e136');
INSERT INTO `sys_user_role` VALUES ('265c4165a7cf4dcb83795f46c9c84c56', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('297d4d628b3a442fb043cb4293adc9a7', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('3', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('3', 'f6d2f215ed734cc09273928acab6e136');
INSERT INTO `sys_user_role` VALUES ('3d674fcefb3143c5bac3a83d52b61d63', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('3f7ef2d4761f445e946413ab9bea23db', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('4', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('42f5435f943a4e8cbcd86ec07b1d922a', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('46154d5f996a4b6cbdfd35e4e7be864e', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('4639f877ec7149258cadcf145b4182a7', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('4639f877ec7149258cadcf145b4182a7', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('4b2209bfdb4b4f0b964f95c2c48ce429', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('5', 'f6d2f215ed734cc09273928acab6e136');
INSERT INTO `sys_user_role` VALUES ('5091ca619fbf49209bce020859103ba5', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('5091ca619fbf49209bce020859103ba5', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('51938e17b432476bbe489a2c523183a4', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('5ad0d1f9efdc44b481b02c26b67e832e', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('5ad0d1f9efdc44b481b02c26b67e832e', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('5b61eb41f80f43c5b8e485866178f63d', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('5b67a6d8e8804c96847cb480fa8dbd52', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('5b67a6d8e8804c96847cb480fa8dbd52', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('5ddb46e2efe4470cae80eacab1d51104', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('6', 'f6d2f215ed734cc09273928acab6e136');
INSERT INTO `sys_user_role` VALUES ('63d26c0fcf7c4b849fa2ce9c07346909', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('6474fda3f35c4d1580b646a42f190cf7', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('69440d1c90374c23a7bbce6c850d9980', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('69e52c0e9112442eb966a9f421913c2f', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('6d69972a880f4228b88f28254c3e417e', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('7', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('7', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('7374fe91d19a4b739ae649334c0cc273', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('7374fe91d19a4b739ae649334c0cc273', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('7374fe91d19a4b739ae649334c0cc273', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('8', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('89d3d0c45dc34fc89e95695a66ea3bab', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('8a867bb9ce8a40d6a1a01584139a7084', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('8b454376c0434e2792a1dc57edf80dbd', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('8cc6f5fe20d54a4aa09b357d34d2d190', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('9', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('9', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('95b3b09fa0b34ac6b95907237c744c55', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('9e23e56e3e464cc8870463e9039abdaf', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('a68dfe143da9485b9b6c0335c3a70618', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('a68dfe143da9485b9b6c0335c3a70618', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('a91be50880744e4787a77e0f97f8dbb5', '03fcd9c5c28948eb827fcd3b852494a6');
INSERT INTO `sys_user_role` VALUES ('a91be50880744e4787a77e0f97f8dbb5', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('a91be50880744e4787a77e0f97f8dbb5', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('a91be50880744e4787a77e0f97f8dbb5', 'f6d2f215ed734cc09273928acab6e136');
INSERT INTO `sys_user_role` VALUES ('afea970c30ae4a86be56a79a13df02a3', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('b2c24d5a8a4a4e74bbfe404d60c68038', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('b2c24d5a8a4a4e74bbfe404d60c68038', '5');
INSERT INTO `sys_user_role` VALUES ('b2c24d5a8a4a4e74bbfe404d60c68038', '560744a781144a9f9775c346c32a44c4');
INSERT INTO `sys_user_role` VALUES ('b2c24d5a8a4a4e74bbfe404d60c68038', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('b2c24d5a8a4a4e74bbfe404d60c68038', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('b93adca3b2b04259bc067a5cb97d6b86', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('b93adca3b2b04259bc067a5cb97d6b86', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('ba4ac26d5db348aaab30901a7f00ae0c', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('bb57f2b70ac34641be0d6c5e8e420ee0', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('bb57f2b70ac34641be0d6c5e8e420ee0', '5');
INSERT INTO `sys_user_role` VALUES ('bb57f2b70ac34641be0d6c5e8e420ee0', '560744a781144a9f9775c346c32a44c4');
INSERT INTO `sys_user_role` VALUES ('bb81a0c098a444e69028380e80852d91', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('bb81a0c098a444e69028380e80852d91', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('bddf9bc18d3f492e9772c5e2daac001d', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('c7a7e031423645a4b9270808a7c8635e', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('cafe38c3bfbe497a8de2d8c0c4212800', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('cb359e3089224f97adae46cf03c0ed94', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('d355e9e6b4ad48a59fa0a983e8b7b583', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('d42378c9621b4551a0c4c63c82f0aa33', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('d42378c9621b4551a0c4c63c82f0aa33', '91766cc228e34269a65f0564ba956bd7');
INSERT INTO `sys_user_role` VALUES ('d42378c9621b4551a0c4c63c82f0aa33', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('d8de25a1f872404e82f669c04091685e', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('da60c61cf2aa4cb2810c629c7045c538', '91766cc228e34269a65f0564ba956bd7');
INSERT INTO `sys_user_role` VALUES ('dc9663e71aaa42f0a2634770896ec74c', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('dc9663e71aaa42f0a2634770896ec74c', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('dfc6dd11f865440199a319fbe0c1235c', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('dfc6dd11f865440199a319fbe0c1235c', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('dfc6dd11f865440199a319fbe0c1235c', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('e64299628c59421f9c0cacdb612bdb54', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('eca1a51b803449d59ce4e2a3b0f4f787', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('eca1a51b803449d59ce4e2a3b0f4f787', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('f12330474f634e2897878e8f56a910ae', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('f4851f3c26884bd3af351d8c7a6a848c', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('f7cc1c7e6f494818adffe1de5f2282fb', '1c54e003c1fc4dcd9b087ef8d48abac3');
INSERT INTO `sys_user_role` VALUES ('f7cc1c7e6f494818adffe1de5f2282fb', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('f7cc1c7e6f494818adffe1de5f2282fb', 'caacf61017114120bcf7bf1049b6d4c3');
INSERT INTO `sys_user_role` VALUES ('f7d3808797d842b68787fe285fc99425', '781acb2361244e49aef509c8688c3ec2');
INSERT INTO `sys_user_role` VALUES ('f94feab9296e4b55957e9eefa029fa60', 'caacf61017114120bcf7bf1049b6d4c3');

-- ----------------------------
-- Table structure for test_pie_class
-- ----------------------------
DROP TABLE IF EXISTS `test_pie_class`;
CREATE TABLE `test_pie_class` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `class_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '班级',
  `num` int(11) DEFAULT NULL COMMENT '人数',
  `remarks` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of test_pie_class
-- ----------------------------
INSERT INTO `test_pie_class` VALUES ('19141118ea9e46c6b35d8baeb7f3fe94', '1', '2016-05-26 21:29:26', '1', '2016-05-26 21:35:06', '0', '2班', '22', '11');
INSERT INTO `test_pie_class` VALUES ('42b3824ef5dc455e917a3b1f6a8c1108', '1', '2016-05-26 21:35:26', '1', '2017-06-04 17:07:43', '0', '3班', '12', '');
INSERT INTO `test_pie_class` VALUES ('c99bd23b48ea4234a06138a38fba05f9', '1', '2017-06-04 17:07:56', '1', '2017-06-04 17:17:57', '0', '一班', '13', '3131');
