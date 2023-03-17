drop table if exists order_header cascade;

create table order_header
(
    id        bigserial not null primary key,
    customer      varchar(255)
);