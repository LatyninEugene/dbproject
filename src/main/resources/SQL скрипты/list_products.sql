create table list_products(
  id SERIAL PRIMARY KEY,
  title VARCHAR(64) NOT NULL UNIQUE
);