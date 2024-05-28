create table if not exists users
(
    id bigserial not null,
    login text not null unique,
    password text not null,
    name text,
    surname text,
    birthday timestamp,
    gender text,
    interests text,
    city text,
    create_datetime timestamp default CURRENT_TIMESTAMP not null,
    primary key (id)
);