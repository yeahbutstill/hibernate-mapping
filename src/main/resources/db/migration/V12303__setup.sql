create table category (
       id bigserial not null,
        created_date timestamp(6),
        last_modified_date timestamp(6),
        description varchar(255),
        primary key (id)
    );

create table customer (
       id bigserial not null,
        created_date timestamp(6),
        last_modified_date timestamp(6),
        address varchar(255),
        city varchar(255),
        state varchar(255),
        zip_code varchar(255),
        customer_name varchar(255),
        email varchar(255),
        phone varchar(255),
        primary key (id)
    );

create table order_approval (
       id bigserial not null,
        created_date timestamp(6),
        last_modified_date timestamp(6),
        approved_by varchar(255),
        primary key (id)
    );

create table order_header (
       id bigserial not null,
        created_date timestamp(6),
        last_modified_date timestamp(6),
        bill_to_address varchar(255),
        bill_to_city varchar(255),
        bill_to_state varchar(255),
        bill_to_zip_code varchar(255),
        order_status varchar(255),
        shipping_address varchar(255),
        shipping_city varchar(255),
        shipping_state varchar(255),
        shipping_zip_code varchar(255),
        customer_id bigint,
        order_approval_id bigint,
        primary key (id)
    );

create table order_line (
       id bigserial not null,
        created_date timestamp(6),
        last_modified_date timestamp(6),
        quantity_ordered integer,
        order_header_id bigint,
        product_id bigint,
        primary key (id)
    );

create table product (
       id bigserial not null,
        created_date timestamp(6),
        last_modified_date timestamp(6),
        description varchar(255),
        product_status varchar(255),
        primary key (id)
    );

create table product_category (
       product_id bigint not null,
        category_id bigint not null,
        primary key (product_id, category_id)
    );

alter table if exists order_header
       add constraint FKbkj7uhdpxqe8qb2b1g6poijwt
       foreign key (customer_id)
       references customer;

alter table if exists order_header
       add constraint FKby5nql12apc2yxa26ggmf108j
       foreign key (order_approval_id)
       references order_approval;

alter table if exists order_line
       add constraint FKoujl67v3lk4glhmln31imw1wo
       foreign key (order_header_id)
       references order_header;

alter table if exists order_line
       add constraint FKpf904tci8garypkvm32cqupye
       foreign key (product_id)
       references product;

alter table if exists product_category
       add constraint FKkud35ls1d40wpjb5htpp14q4e
       foreign key (category_id)
       references category;

alter table if exists product_category
       add constraint FK2k3smhbruedlcrvu6clued06x
       foreign key (product_id)
       references product;