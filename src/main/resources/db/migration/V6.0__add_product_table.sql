create table product
(
    id bigserial not null primary key,
    description varchar(100),
    product_status varchar(20),
    created_date timestamp,
    last_modified_date timestamp
);