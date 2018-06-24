create type type_of_access as enum('admin','user');
create TABLE users(
  id serial primary key,
  login varchar(18) NOT NULL UNIQUE,
  password varchar(18) NOT NULL,
  type type_of_access
);