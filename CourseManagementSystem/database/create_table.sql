/**
    生成数据库
 */
create database if not exists CourseManagementSystem;
/**
    切换数据库
 */
use CourseManagementSystem;
#课程表
create table if not exists Course
(
    `id` bigint auto_increment primary key                                               comment 'id',
    `name` varchar(256) null                                                             comment '课程名字',
    `hours` Integer null                                                                 comment '学时',
    `description` text null                                                              comment '课程描述',
    `file_path` varchar(256) null                                                         comment '文件路径',
    `status` tinyint null                                                               comment '该课程是否开启',
    `teacher_id`    bigint null                                                           comment '课程所属教师id',
    `start_date` datetime default CURRENT_TIMESTAMP not null                              comment '开启时间',
    `end_date` datetime null                                                              comment '结束时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'

) comment 'course' collate = utf8mb4_unicode_ci;
#用户表
create table if not exists `user`
(
    `id`           bigint auto_increment comment 'id' primary key,
    `user_account`  varchar(256)                       not null comment '账号',
    `user_password` varchar(512)                       not null comment '密码',
    `user_name`     varchar(256)                       null comment '用户昵称',
    `user_role`     varchar(256)                       not null comment '用户角色：student/teacher/admin',
    `create_time`   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
) comment 'user' collate = utf8mb4_unicode_ci;


