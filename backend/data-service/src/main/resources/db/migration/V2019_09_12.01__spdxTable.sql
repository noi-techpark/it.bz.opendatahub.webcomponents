create table spdx
(
    reference varchar(255) not null,
    is_deprecated_license_id boolean not null,
    details_url varchar(255) not null,
    reference_number varchar(255) not null,
    name varchar(255) not null,
    license_id varchar(32) not null
        constraint spdx_pk
            primary key,
    see_also jsonb default '[]'::json not null,
    is_osi_approved boolean not null
);