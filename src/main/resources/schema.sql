DROP TABLE IF EXISTS CUSTOMER;

create table CUSTOMER (
    id varchar(10) not null,
    email varchar(10),
    name varchar(10),
    nick_name varchar(10),
    password varchar(255),
    phone_number varchar(10),
    reg_date_time timestamp,
    last_login_date_time timestamp,
    login_yn char(1),
    primary key (id)
)