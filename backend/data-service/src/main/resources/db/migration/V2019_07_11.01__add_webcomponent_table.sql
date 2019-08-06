create table if not exists webcomponent
(
    uuid                 varchar(64)               not null
        constraint webcomponent_pk
            primary key,
    title                varchar(255)              not null,
    description          text,
    description_abstract text,
    license              varchar(255),
    authors              jsonb default '[]'::jsonb not null,
    search_tags          jsonb default '[]'::jsonb not null,
    configuration        jsonb
);
