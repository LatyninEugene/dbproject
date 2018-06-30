create type type_of_transaction as enum('add','remove');
create table history(
  id serial primary key,
  id_list int NOT NULL,
  id_user int NOT NULL,
  type type_of_transaction NOT NULL,
  date timestamp NOT NULL,
  id_transport int NOT NULL
);