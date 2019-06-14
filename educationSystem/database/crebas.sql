/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/6/7 12:11:33                            */
/*==============================================================*/


drop table if exists TBL_XXGK;

drop table if exists TBL_XQDZ;

drop table if exists TBL_XXXGDZDW;

drop table if exists TBL_XXJXKYDW;

drop table if exists TBL_DATACOL;

drop table if exists TBL_FORM;

drop table if exists TBL_FORM_RULES;

drop table if exists TBL_USER;

--表1-1 学校概况


 CREATE TABLE TBL_XXGK 
   (	ID bigint not null auto_increment, 
	CREATOR bigint, 
	CREATE_TIME VARCHAR(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME NVARCHAR(50), 
	STATUS int, 
	TASK_ID bigint, 
	XXMC VARCHAR(100), 
	XXDM VARCHAR(100), 
	YWMC VARCHAR(200), 
	BXLX VARCHAR(20), 
	XXXZ VARCHAR(20), 
	JBZ VARCHAR(50), 
	ZGBM VARCHAR(50), 
	XXWZ VARCHAR(200), 
	ZSPC VARCHAR(500), 
	KBBKJYNF VARCHAR(4), 
	TJSJ VARCHAR(100), 
	TBFZRXM VARCHAR(50), 
	TBFZRLXDH VARCHAR(50), 
	TBFZRLXDZYX VARCHAR(100), 
	TASK_VER_ int,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--表1-2 校区及地址

  CREATE TABLE TBL_XQDZ 
   (	ID BIGINT not null auto_increment, 
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS int, 
	TASK_ID bigint, 
	TJSJ varchar(100), 
	XQMC varchar(100), 
	DZ varchar(200), 
	SSZZQ varchar(10), 
	SQZ varchar(200), 
	SFBDXQ varchar(20), 
	TASK_VER_ int,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--表1-3 学校相关党政单位

  CREATE TABLE TBL_XXXGDZDW 
   (	ID BIGINT not null auto_increment, 
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS int, 
	TASK_ID bigint, 
	TJSJ varchar(100), 
	DZDWMC varchar(200), 
	DWH varchar(20), 
	DWZN varchar(20), 
	DWFZR varchar(50), 
	TASK_VER_ int,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

   --表1-4 学校教学科研单位

     CREATE TABLE TBL_XXJXKYDW 
   (	ID BIGINT not null auto_increment, 
	CREATOR bigint, 
	CREATE_TIME varchar(50), 
	LAST_OPERATOR bigint, 
	LAST_OPERATE_TIME varchar(50), 
	STATUS int, 
	TASK_ID bigint, 
	TJSJ varchar(100), 
	JXKYDWMC varchar(200), 
	DWH varchar(20), 
	DWFZR varchar(50), 
	SFKH varchar(100), 
	DWZN varchar(50), 
	TASK_VER_ int,
	primary key (ID)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_DATACOL                                           */
/*==============================================================*/
create table TBL_DATACOL
(
   user_id              bigint,
   form_id              bigint not null,
   status               char,
   update_time          timestamp,
   primary key (form_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_FORM                                              */
/*==============================================================*/
create table TBL_FORM
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   tbl_name             varchar(255),
   bus_name             varchar(255),
   phsic_name           varchar(255),
   stats_time           date,
   form_type            varchar(255),
   is_null              char,
   dependency_form      bigint,
   template_loc         varchar(255),
   create_time          timestamp,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_FORM_RULES                                        */
/*==============================================================*/
create table TBL_FORM_RULES
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   form_id              bigint,
   rule_name            varchar(255),
   rule_class           int,
   rule_content         varchar(255),
   fail_information     varchar(255),
   rule_pattern         char,
   rule_seq             int,
   rule_active          char,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_USER                                              */
/*==============================================================*/
create table TBL_USER
(
   id                   bigint not null auto_increment,
   user_name            varchar(255),
   password             varchar(255),
   create_time          timestamp,
   last_login           timestamp,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table TBL_DATACOL add constraint FK_Reference_2 foreign key (form_id)
      references TBL_FORM (id) on delete restrict on update restrict;

alter table TBL_FORM add constraint FK_Reference_1 foreign key (user_id)
      references TBL_USER (id) on delete restrict on update restrict;

alter table TBL_FORM_RULES add constraint FK_Reference_3 foreign key (form_id)
      references TBL_FORM (id) on delete restrict on update restrict;

alter table TBL_FORM_FILEDS add constraint FK_Reference_4 foreign key (form_id)
      references TBL_FORM (id) on delete restrict on update restrict;
