create table t_system_user_info(id int not null IDENTITY(1,1) primary key, username varchar(50) unique not null, password varchar(50) not null, cnname varchar(50),email varchar(50),enabled int, account_expired int, account_locked int, credentials_expired int);
go
create table t_system_role_info(id int not null IDENTITY(1,1) primary key, name varchar(50) unique not null, cnname varchar(50));
go
create table t_system_resource_info(id int not null IDENTITY(1,1) primary key, name varchar(50) unique not null,path varchar(200) unique not null, cnname varchar(50));
go
create table t_system_user_role(user_id int,role_id int);
go
create table t_system_role_resource(role_id int,resource_id int);
go
create table t_template_info(id int not null IDENTITY(1,1) primary key,name varchar(50) unique not null,template_path varchar(100) not null,html_path varchar(100) not null,last_date datetime,description varchar(100));
go
create table t_template_data(id int not null IDENTITY(1,1) primary key,data_name varchar(50) not null,sql_value varchar(1000) not null,is_unique_result int,description varchar(100),template_id int not null);
go
create table t_task_info(id int not null IDENTITY(1,1) primary key,task_name varchar(50) not null,next_run_time datetime,run_unit int,enabled int,instance_name varchar(50) not null,description varchar(100));
go
create table t_task_run_history(id int not null IDENTITY(1,1) primary key,task_id int not null,run_time datetime,run_state int,run_type int,error_info varchar(1000));
go
create table t_logger_info(id int not null IDENTITY(1,1) primary key,log_info varchar(200),log_date datetime);
go