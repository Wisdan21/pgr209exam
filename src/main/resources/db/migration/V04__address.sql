create table if not exists address
(
    address_id bigint,
    street  VARCHAR(100),
    city    VARCHAR(100),
    zipcode VARCHAR(20),
    customer_id bigint

);

create sequence address_seq;
