alter table webcomponent
    add highlighted boolean default false not null;

alter table webcomponent
    add theme text default "dark" not null;