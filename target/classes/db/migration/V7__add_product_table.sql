drop table if exists product cascade;
create table product (
    id bigserial not null,
    description varchar(255),
    product_status varchar(20),
    created_date timestamp,
    last_modified_date timestamp,
    primary key (id)
)