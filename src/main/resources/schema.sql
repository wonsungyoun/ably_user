DROP TABLE IF EXISTS CUSTOMER;

create table CUSTOMER (
    email varchar(100) not null ,
    name varchar(10) not null,
    nick_name varchar(10) not null ,
    password varchar(255) not null ,
    phone_number varchar(10) not null ,
    reg_date_time timestamp,
    last_login_date_time timestamp,
    login_yn char(1),
    primary key (email)
);

DROP TABLE IF EXISTS SMS_SEND_HISTORY;

create table SMS_SEND_HISTORY(
    NO BIGINT AUTO_INCREMENT,
    email varchar(100) not null,
    phone_number varchar(10) not null ,
    certification_number int(5) not null,
    send_date_time timestamp,
    primary key (NO)
);