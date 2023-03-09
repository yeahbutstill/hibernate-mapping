drop table if exists order_header cascade;

create table order_header (
    id bigserial not null,
    customer varchar(255),
    primary key (id)
);
