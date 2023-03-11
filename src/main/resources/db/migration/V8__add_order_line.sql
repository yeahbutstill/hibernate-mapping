create table order_line (
    id bigserial not null primary key,
    quantity_ordered int,
    order_header_id bigint,
    created_date timestamp,
    last_modified_date timestamp,
    constraint order_header_pk foreign key(order_header_id) references order_header(id)
)