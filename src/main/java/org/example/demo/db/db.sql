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

);
create table orders(
                       id              varchar(255) primary key,
                       date            date not null,
                       discount_value  decimal(10,2) not null,
                       sub_total       decimal(10,2) not null,
                       customer_id     varchar(255) not null,
                       constraint foreign key (customer_id) references customer(customer_id)
                           on update cascade on delete cascade
);

create table order_details(
                              order_id            varchar(255) not null ,
                              item_id             varchar(255) not null,
                              qty                 int not null,
                              unit_price          decimal(10,2) not null,
                              total               decimal(10,2) not null,
                              constraint foreign key (order_id) references orders(id)
                                  on update cascade on delete cascade,
                              constraint foreign key (item_id) references item(item_id)
                                  on update cascade on delete cascade
);