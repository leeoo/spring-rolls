/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/9/17 14:41:25                           */
/*==============================================================*/


/*==============================================================*/
/* Table: t_exam                                                */
/*==============================================================*/
create table t_exam
(
   id                   int not null auto_increment,
   title                varchar(1000) not null,
   score                int not null,
   cn_proportion        int,
   en_proportion        int,
   pun_proportion       int,
   num_proportion       int,
   status               int not null,
   start_date           datetime not null,
   end_date             datetime not null,
   long_time            int not null,
   question_id          int not null,
   primary key (id)
);

/*==============================================================*/
/* Index: index_exam_question_id                                */
/*==============================================================*/
create index index_exam_question_id on t_exam
(
   question_id
);

/*==============================================================*/
/* Table: t_examinee                                            */
/*==============================================================*/
create table t_examinee
(
   id                   int not null auto_increment,
   code                 varchar(20) not null,
   username             varchar(20) not null,
   password             varchar(20) not null,
   reg_date             datetime not null,
   can_days             int,
   primary key (id)
);

/*==============================================================*/
/* Index: index_examinee_code                                   */
/*==============================================================*/
create unique index index_examinee_code on t_examinee
(
   code
);

/*==============================================================*/
/* Table: t_question                                            */
/*==============================================================*/
create table t_question
(
   id                   int not null auto_increment,
   title                varchar(1000) not null,
   choice               bit not null,
   audio_path           varchar(255),
   text_content         text not null,
   primary key (id)
);

/*==============================================================*/
/* Table: t_question_choice                                     */
/*==============================================================*/
create table t_question_choice
(
   id                   int not null auto_increment,
   title                varchar(2000) not null,
   proportion           int not null,
   a_option             varchar(1000) not null,
   b_option             varchar(1000) not null,
   c_option             varchar(1000),
   d_option             varchar(1000),
   e_option             varchar(1000),
   f_option             varchar(1000),
   g_option             varchar(1000),
   h_option             varchar(1000),
   answer               varchar(20) not null,
   multi                bit not null,
   question_id          int not null,
   primary key (id)
);

/*==============================================================*/
/* Index: Index_question_choice_qid                             */
/*==============================================================*/
create index Index_question_choice_qid on t_question_choice
(
   question_id
);

/*==============================================================*/
/* Table: t_score                                               */
/*==============================================================*/
create table t_score
(
   id                   int not null auto_increment,
   start_date           datetime not null,
   long_time            int not null,
   context              text not null,
   score                int not null,
   examinee_id          int not null,
   exam_id              int not null,
   primary key (id)
);

/*==============================================================*/
/* Index: Index_score_examinee_id                               */
/*==============================================================*/
create index Index_score_examinee_id on t_score
(
   examinee_id
);

/*==============================================================*/
/* Index: Index_score_exam_id                                   */
/*==============================================================*/
create index Index_score_exam_id on t_score
(
   exam_id
);

