alter table webcomponent
    add dist jsonb default '[]'::jsonb;

alter table webcomponent
    add image varchar(255);

alter table webcomponent
    add repository_url varchar(255);

