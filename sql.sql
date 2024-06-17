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
    user_real_name    varchar(255),
    matching_password varchar(255)
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
    good_photo       varchar(255)[] not null
);

create table prices
(
    price_id       bigint           not null
        primary key,
    bought_amount  integer          not null,
    client_price   double precision not null,
    created_at     date,
    deleted_at     date,
    sold_amount    integer,
    supplier_price double precision not null,
    good_id        bigint
        constraint fkglvb0aarlmr3hwh6jbt4m5cai
            references goods
);

create table card
(
    id       bigint not null
        primary key,
    user_id  bigint not null
        constraint fkq5apcc4ddrab8t48q2uqvyquq
            references users,
    price_id bigint not null
        constraint fkq0hw9vg3h711qfugoof7y647c
            references prices
);

