alter table webcomponent_version
    add dist_size_total_kb int default 0 not null;

alter table webcomponent_version
    add lighthouse_metrics_mobile_data jsonb;

alter table webcomponent_version
    add lighthouse_metrics_mobile_datetime timestamp;

alter table webcomponent_version
    add lighthouse_mobile_performance_rating int default 0 not null;

alter table webcomponent_version
    add lighthouse_metrics_desktop_data jsonb;

alter table webcomponent_version
    add lighthouse_metrics_desktop_datetime timestamp;

alter table webcomponent_version
    add lighthouse_desktop_performance_rating int default 0 not null;

alter table webcomponent_version
    add lighthouse_update_required bool default true not null;