create table if not exists product
(
    product_id bigint,
    product_name varchar(100) not null,
    product_description TEXT,
    product_price DECIMAL(10, 2) NOT NULL,
    product_status VARCHAR(20),
    product_quantity_on_hand INT NOT NULL

);

create sequence product_seq;