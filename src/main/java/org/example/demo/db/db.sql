use AADpos;

create table customer(
                         customer_id varchar(255) primary key ,
                         name varchar(255) not null unique ,
                         address varchar(255),
                         phone bigint
)