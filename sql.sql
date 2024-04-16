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


