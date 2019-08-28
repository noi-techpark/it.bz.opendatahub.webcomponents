alter table webcomponent
    drop column configuration;

alter table webcomponent
    drop column dist;

alter table webcomponent_version
    add configuration jsonb default '{}'::jsonb NOT NULL;

alter table webcomponent_version
    add dist jsonb default '[]'::jsonb NOT NULL;