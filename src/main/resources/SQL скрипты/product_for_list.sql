create table product_for_list(
  id serial primary key,
  id_list int NOT NULL,
  id_product int NOT NULL,
  tonnage int NOT NULL
);