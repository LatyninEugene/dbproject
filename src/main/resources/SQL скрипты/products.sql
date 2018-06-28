create type type_of_product as enum('vegetable','fruit');
create table products(
  id serial primary key,
  title varchar(24) not null unique,
  count int,
  type type_of_product
);
