
create table if not exists orderdb
(
    order_id bigint primary key,
    customer_id bigint not null,
    shipping_charge DECIMAL(10, 2),
    total_price DECIMAL(10, 2),
    is_shipped BOOLEAN,
    shipping_address_id  bigint

);
create sequence order_seq;
