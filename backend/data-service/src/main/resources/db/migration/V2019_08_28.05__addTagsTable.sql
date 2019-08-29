create table origin_tag
(
    origin_uuid varchar(64),
    tag_name varchar(255),
    hash varchar(255) not null,
    deleted boolean default false not null,
    constraint origin_tag_pk
        primary key (origin_uuid, tag_name)
);