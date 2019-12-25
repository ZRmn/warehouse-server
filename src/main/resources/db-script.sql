create database zrmn_db
    with owner postgres;

create sequence warehouse."place_ID_seq";

alter sequence warehouse."place_ID_seq" owner to postgres;

create table warehouse."user"
(
    id        integer not null
        constraint user_pk
            primary key,
    login     varchar not null,
    password  varchar not null,
    full_name varchar not null,
    role      varchar not null
);

alter table warehouse."user"
    owner to postgres;

create unique index user_login_uindex
    on warehouse."user" (login);

create table warehouse.product
(
    id          integer not null
        constraint product_pk
            primary key,
    code        bigint  not null,
    description varchar not null
);

alter table warehouse.product
    owner to postgres;

create unique index product_code_uindex
    on warehouse.product (code);

create table warehouse.box
(
    id             integer not null
        constraint box_pk
            primary key,
    products_count integer not null,
    product_id     integer not null
        constraint box_product_fk
            references warehouse.product
            on update cascade on delete cascade
);

alter table warehouse.box
    owner to postgres;

create table warehouse.address
(
    id      integer not null
        constraint address_pk
            primary key,
    address varchar not null
);

alter table warehouse.address
    owner to postgres;

create table warehouse."order"
(
    id          serial  not null
        constraint order_pk
            primary key,
    address_id  integer not null
        constraint order_address_fk
            references warehouse.address
            on update cascade on delete cascade,
    boxes_count integer not null,
    box_id      integer not null
        constraint order_box_fk
            references warehouse.box
            on update cascade on delete cascade,
    order_id    integer not null,
    date        timestamp default now()
);

alter table warehouse."order"
    owner to postgres;

create unique index address_address_uindex
    on warehouse.address (address);

create table warehouse.map
(
    id      serial  not null
        constraint map_pk
            primary key,
    rows    integer not null,
    columns integer not null
);

alter table warehouse.map
    owner to postgres;

create table warehouse.pallet
(
    id     integer not null
        constraint pallet_pk
            primary key,
    box_id integer not null
        constraint pallet_box_fk
            references warehouse.box
            on update cascade on delete cascade,
    count  integer not null
);

alter table warehouse.pallet
    owner to postgres;

create table warehouse.place
(
    id        serial             not null
        constraint place_pk
            primary key,
    position  varchar            not null,
    capacity  integer default 50 not null,
    fullness  integer default 0  not null,
    place_id  integer            not null,
    pallet_id integer
        constraint place_pallet_fk
            references warehouse.pallet
            on update cascade on delete cascade
);

alter table warehouse.place
    owner to postgres;

