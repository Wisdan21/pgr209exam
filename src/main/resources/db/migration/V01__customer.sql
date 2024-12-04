create table if not exists customer
(
    customer_id bigint,
    customer_firstname varchar(200),
    customer_lastname varchar(200),
    customer_phonenumber varchar(50),
    customer_email varchar(100)

);
create sequence customer_seq;
