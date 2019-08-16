alter table webcomponent_version drop constraint webcomponent_version_pk;

alter table webcomponent_version
    add constraint webcomponent_version_pk
        primary key (webcomponent_uuid, version_tag);