create table Transport(
  id SERIAL PRIMARY KEY,
  number VARCHAR(10) NOT NULL UNIQUE,
  tonnage INT NOT NULL
);