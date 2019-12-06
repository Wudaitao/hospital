/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/12/5 21:38:26                           */
/*==============================================================*/


drop table if exists DoctorReservationInfo;

drop table if exists ExamItem;

drop table if exists HospitalSetting;

drop table if exists Item;

drop table if exists Medicine;

drop table if exists MedicineList;

drop table if exists Payment;

drop table if exists ReservationInfo;

drop table if exists User;

drop table if exists UserCase;

drop table if exists Worker;

/*==============================================================*/
/* Table: DoctorReservationInfo                                 */
/*==============================================================*/
create table DoctorReservationInfo
(
   dr_id                 int not null AUTO_INCREMENT,
   user_id               varchar(20) not null,
   dr_data               varchar(20),
   dr_time_slot          varchar(50),
   dr_resv_num           int,
   dr_max_resv_num       int,
   dr_department         varchar(100),
   primary key (dr_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: ExamItem                                              */
/*==============================================================*/
create table ExamItem
(
   check_item_id          bigint not null AUTO_INCREMENT,
   uer_id                 varchar(20) not null,
   check_user_name        varchar(50),
   check_date             varchar(50),
   check_time_slot        varchar(50),
   check_item_name        varchar(100),
   check_result           varchar(1024),
   check_is_paid          bool,
   primary key (check_item_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: HospitalSetting                                       */
/*==============================================================*/
create table HospitalSetting
(
   hosp_department        varchar(100) not null,
   open_window_num        int,
   primary key (hosp_department)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: Item                                                  */
/*==============================================================*/
create table Item
(
   item_name             varchar(100) not null,
   item_price            float,
   primary key (item_name)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: Medicine                                              */
/*==============================================================*/
create table Medicine
(
   medicine_id           bigint not null AUTO_INCREMENT,
   medicine_name         varchar(200),
   medicine_storage      int,
   medicine_price        float,
   primary key (medicine_id)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: MedicineList                                          */
/*==============================================================*/
create table MedicineList
(
   ml_id                 bigint not null AUTO_INCREMENT,
   user_id               varchar(20) not null,
   ml_time               varchar(100),
   med_id                bigint,
   med_num               int,
   ml_state              int,
   ml_total_price        float,
   ml_is_paid            bool,
   primary key (ml_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: Payment                                               */
/*==============================================================*/
create table Payment
(
   worker_id           varchar(20) not null,
   user_id               varchar(20) not null,
   payment_date          varchar(100),
   payment_amount        float,
   primary key (worker_id, user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: ReservationInfo                                       */
/*==============================================================*/
create table ReservationInfo
(
   resv_id               bigint not null AUTO_INCREMENT,
   user_id               varchar(20) not null,
   resv_type             varchar(10),
   resv_department       varchar(100),
   resv_doctor_id        int,
   resv_is_valid         bool,
   resv_num              int,
   resv_time_slot        varchar(100),
   resv_date             varchar(100),
   primary key (resv_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   user_id               varchar(20) not null,
   user_password         varchar(100),
   user_name             varchar(50),
   user_gender           varchar(4),
   user_age              int,
   primary key (user_id)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: UserCase                                              */
/*==============================================================*/
create table UserCase
(
   case_id                bigint not null AUTO_INCREMENT,
   user_id                varchar(20) not null,
   case_date              varchar(100),
   case_time_slot         varchar(100),
   case_is_finish         bool,
   case_result            varchar(1024),
   primary key (case_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: Worker                                                */
/*==============================================================*/
create table Worker
(
   user_id               varchar(20) not null,
   user_password         varchar(100),
   user_name             varchar(50),
   user_gender           varchar(4),
   user_age              int,
   worker_department     varchar(100),
   worker_type           varchar(20),
   primary key (user_id)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

alter table DoctorReservationInfo add constraint FK_DoctorReservation foreign key (user_id)
      references Worker (user_id) on delete restrict on update restrict;

alter table ExamItem add constraint FK_UserExams foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

alter table MedicineList add constraint FK_UserMedicineList foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

alter table Payment add constraint FK_Payment foreign key (worker_id)
      references Worker (user_id) on delete restrict on update restrict;

alter table Payment add constraint FK_Payment2 foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

alter table ReservationInfo add constraint FK_UserReservation foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

alter table UserCase add constraint FK_UserCases foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

alter table Worker add constraint FK_Inheritance foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

