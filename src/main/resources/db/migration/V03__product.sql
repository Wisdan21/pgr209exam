create table if not exists product
(
    product_id bigint,
    product_name varchar(100),
    product_description TEXT,
    product_price DECIMAL(10, 2),
    product_status VARCHAR(20),
    product_quantity_on_hand INT

);

create sequence product_seq;