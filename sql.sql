create table role
(
    id   bigint not null
        primary key,
    name varchar(255)
);

create unique index unique_role_name
    on role (name);

create table users
(
    user_id           bigint       not null
        primary key,
    user_adress       varchar(255),
    user_password     varchar(255) not null,
    user_phone_number varchar(255),
    user_email        varchar(255) not null,
    user_real_name    varchar(255)
);

create unique index unique_email
    on users (user_email);

create table users_roles
(
    user_user_id bigint not null
        constraint fk27iuqlfirca39l6y61p4p4qo2
            references users,
    roles_id     bigint not null
        constraint fk15d410tj6juko0sq9k4km60xq
            references role,
    primary key (user_user_id, roles_id)
);

create table goods
(
    good_id          bigint         not null
        primary key,
    good_brand       varchar(255)   not null,
    good_description varchar(255)   not null,
    good_likes       integer,
    good_name        varchar(255)   not null,
    good_photo       varchar(255)[] not null,
    price_id         bigint         not null
);

create table prices
(
    price_id       bigint           not null
        primary key,
    bought_amount  integer          not null,
    client_price   double precision not null,
    created_at     varchar(255),
    deleted_at     varchar(255),
    sold_amount    integer,
    supplier_price double precision not null,
    good_id        bigint           not null
        constraint fkglvb0aarlmr3hwh6jbt4m5cai
            references goods
);

alter table goods
    add constraint fk2bgi34b5shv2w1w0ntsfp2e5p
        foreign key (price_id) references prices;

create table card
(
    id      bigint not null
        primary key,
    good_id bigint
        constraint fkc2panvvu6j74urelpb1uwuuo
            references goods,
    user_id bigint
        constraint fkq5apcc4ddrab8t48q2uqvyquq
            references users
);

create table types
(
    type_id    bigint       not null
        primary key,
    deleted_at varchar(255),
    type_name  varchar(255) not null
);

