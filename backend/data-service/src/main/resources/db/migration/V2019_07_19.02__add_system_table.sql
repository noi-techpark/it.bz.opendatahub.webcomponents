create table if not exists system
(
    key  varchar(32) not null
        constraint system_pk
            primary key,
    data jsonb default '{}'::jsonb
);

INSERT INTO system (key) VALUES ('ORIGIN') ON CONFLICT DO NOTHING;