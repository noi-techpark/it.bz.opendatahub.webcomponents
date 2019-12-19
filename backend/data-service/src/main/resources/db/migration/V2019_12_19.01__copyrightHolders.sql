alter table webcomponent
    drop column copyright_holder;
alter table webcomponent
    add copyright_holders jsonb default '[]'::jsonb not null;