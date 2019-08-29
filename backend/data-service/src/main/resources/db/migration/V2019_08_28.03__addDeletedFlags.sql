alter table origin
    add deleted boolean default false not null;

alter table webcomponent
    add deleted boolean default false not null;

alter table webcomponent_version
    add deleted boolean default false not null;
