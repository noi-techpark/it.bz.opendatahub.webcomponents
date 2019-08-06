create table if not exists searchtag
(
    name   varchar(64) not null
        constraint searchtag_pk
            primary key,
    hidden boolean default false
);