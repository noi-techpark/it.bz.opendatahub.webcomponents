create table if not exists webcomponent_version
(
    webcomponent_uuid varchar(64) not null
        constraint webcomponent_version_pk
            primary key,
    version_tag       varchar(32) not null,
    release_timestamp timestamp
);

create unique index if not exists webcomponent_version_webcomponent_uuid_version_tag_uindex
    on webcomponent_version (webcomponent_uuid, version_tag);
