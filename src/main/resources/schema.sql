CREATE TABLE users
(
    id       serial NOT NULL UNIQUE PRIMARY KEY,
    email    text   NOT NULL UNIQUE,
    password text   NOT NULL,
    role     text   NOT NULL
);