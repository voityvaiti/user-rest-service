CREATE TABLE usr
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(50) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    birth_date DATE        NOT NULL,
    address    VARCHAR(100),
    tel_number VARCHAR(20)
);