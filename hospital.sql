# Host: localhost  (Version 5.7.11)
# Date: 2019-12-08 21:46:30
# Generator: MySQL-Front 6.1  (Build 1.24)


#
# Structure for table "docresinfo"
#

CREATE TABLE `docresinfo` (
  `dr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '医生或科室可预约信息ID',
  `user_id` varchar(20) DEFAULT '' COMMENT '医生ID',
  `dr_date` varchar(20) DEFAULT NULL COMMENT '预约日期',
  `dr_time_slot` varchar(50) DEFAULT NULL COMMENT '预约时间段',
  `dr_resv_num` int(11) DEFAULT NULL COMMENT '可预约数量',
  `dr_max_resv_num` int(11) DEFAULT NULL COMMENT '可预约最大数量',
  `dr_department` varchar(100) DEFAULT NULL COMMENT '科室',
  PRIMARY KEY (`dr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "examitem"
#

CREATE TABLE `examitem` (
  `check_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL DEFAULT '',
  `check_user_name` varchar(50) DEFAULT NULL,
  `check_date` varchar(50) DEFAULT NULL,
  `check_time_slot` varchar(50) DEFAULT NULL,
  `check_item_name` varchar(100) DEFAULT NULL,
  `check_result` varchar(1024) DEFAULT NULL,
  `check_is_paid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`check_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "hospitalsetting"
#

CREATE TABLE `hospitalsetting` (
  `hosp_department` varchar(100) NOT NULL,
  `open_window_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`hosp_department`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Structure for table "item"
#

CREATE TABLE `item` (
  `item_name` varchar(100) NOT NULL,
  `item_price` float DEFAULT NULL,
  PRIMARY KEY (`item_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Structure for table "medicine"
#

CREATE TABLE `medicine` (
  `medicine_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `medicine_name` varchar(200) DEFAULT NULL,
  `medicine_storage` int(11) DEFAULT NULL,
  `medicine_price` float DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Structure for table "medicinelist"
#

CREATE TABLE `medicinelist` (
  `ml_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `ml_date` varchar(100) DEFAULT NULL,
  `med_id` bigint(20) DEFAULT NULL,
  `med_num` int(11) DEFAULT NULL,
  `ml_state` int(11) DEFAULT NULL,
  `ml_total_price` float DEFAULT NULL,
  `ml_is_paid` tinyint(1) DEFAULT NULL,
  `ml_time_slot` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ml_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "payment"
#

CREATE TABLE `payment` (
  `worker_id` varchar(20) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `payment_date` varchar(100) DEFAULT NULL,
  `payment_amount` float DEFAULT NULL,
  PRIMARY KEY (`worker_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "reservationinfo"
#

CREATE TABLE `reservationinfo` (
  `resv_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预约ID',
  `user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '病人ID',
  `resv_type` varchar(10) DEFAULT NULL COMMENT '预约类型：专家和科室',
  `resv_department` varchar(100) DEFAULT NULL COMMENT '预约科室',
  `resv_doctor_id` varchar(20) DEFAULT '' COMMENT '预约的医生ID',
  `resv_is_valid` tinyint(1) DEFAULT NULL COMMENT '预约号是否有效',
  `resv_num` int(11) DEFAULT NULL COMMENT '预约号',
  `resv_time_slot` varchar(100) DEFAULT NULL COMMENT '预约时间段',
  `resv_date` varchar(100) DEFAULT NULL COMMENT '预约日期',
  `resv_online` varchar(1) DEFAULT NULL COMMENT '1：线上 0：线下预约',
  PRIMARY KEY (`resv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Structure for table "user"
#

CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL DEFAULT '',
  `user_name` varchar(50) DEFAULT NULL,
  `user_password` varchar(100) DEFAULT NULL,
  `user_gender` varchar(4) DEFAULT NULL,
  `user_age` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Structure for table "usercase"
#

CREATE TABLE `usercase` (
  `case_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `case_date` varchar(100) DEFAULT NULL,
  `case_is_finish` tinyint(1) DEFAULT NULL,
  `case_result` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "worker"
#

CREATE TABLE `worker` (
  `user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '工作人员ID',
  `user_password` varchar(100) DEFAULT NULL COMMENT '用户密码',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名字',
  `user_gender` varchar(4) DEFAULT NULL COMMENT '用户性别',
  `user_age` varchar(4) DEFAULT NULL COMMENT '用户年龄',
  `worker_department` varchar(100) DEFAULT NULL COMMENT '所属部门',
  `worker_type` varchar(20) DEFAULT NULL COMMENT '工作类别',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
