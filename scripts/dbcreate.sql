create database network
    with owner postgres;

create table nodes_info
(
    node_id     serial not null
        constraint nodes_info_pk
            primary key,
    type        varchar(40),
    name        varchar(40),
    description varchar(100),
    identifier  varchar(40)
);

alter table nodes_info
    owner to postgres;

create unique index nodes_info_node_id_uindex
    on nodes_info (node_id);

create table node_params
(
    id          serial not null
        constraint node_params_pk
            primary key,
    node_id     integer
        constraint node_params_nodes_info_node_id_fk
            references nodes_info
            on delete cascade,
    param_name  varchar(40),
    param_value varchar(40)
);

alter table node_params
    owner to postgres;

create unique index node_params_id_uindex
    on node_params (id);

create table hierarchy
(
    id        serial not null
        constraint table_name_pk
            primary key,
    node_id   integer
        constraint table_name_nodes_info_node_id_fk
            references nodes_info
            on delete cascade,
    parent_id integer,
    root_id   integer
        constraint hierarchy_nodes_info_node_id_fk
            references nodes_info
            on delete cascade
);

alter table hierarchy
    owner to postgres;

create unique index table_name_id_uindex
    on hierarchy (id);
