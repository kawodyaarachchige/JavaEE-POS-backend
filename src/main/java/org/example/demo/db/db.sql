use AADpos;

create table customer(
                         customer_id varchar(255) primary key ,
                         name varchar(255) not null unique ,
                         address varchar(255),
                         phone bigint
)
create table item(
                     item_id varchar(255) primary key ,
                     description varchar(255) not null unique ,
                     price double(10,2),
                     quantity int

)