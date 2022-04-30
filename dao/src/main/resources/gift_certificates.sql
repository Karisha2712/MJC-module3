create table tags
(
    id   int auto_increment primary key,
    name varchar(45) not null
);

insert into tags (name)
values ('tag1');
insert into tags (name)
values ('tag2');
insert into tags (name)
values ('tag3');

create table certificates
(
    id                int auto_increment primary key,
    title             varchar(255) not null,
    description       varchar(255) null,
    price             int          not null,
    duration          int          not null,
    created_date      timestamp    not null,
    last_updated_date timestamp    not null
);

insert into certificates (title, description, price, duration, created_date, last_updated_date)
values ('certificate1', 'description1', 1005, 10, '2022-04-19 12:00:00', '2022-04-19 12:00:00');
insert into certificates (title, description, price, duration, created_date, last_updated_date)
values ('certificate2', 'description2', 105, 20, '2022-04-19 13:00:00', '2022-04-19 13:00:00');
insert into certificates (title, description, price, duration, created_date, last_updated_date)
values ('certificate3', 'description3', 200, 3, '2022-04-19 14:00:00', '2022-04-19 14:00:00');

create table certificates_has_tags
(
    certificates_id int not null,
    tags_id         int not null,
    primary key (certificates_id, tags_id),
    constraint certificates_has_tags_ibfk_1
        foreign key (certificates_id) references certificates (id) on delete cascade,
    constraint certificates_has_tags_ibfk_2
        foreign key (tags_id) references tags (id) on delete cascade
);

insert into certificates_has_tags (tags_id, certificates_id)
values (1, 1);
insert into certificates_has_tags (tags_id, certificates_id)
values (1, 2);
insert into certificates_has_tags (tags_id, certificates_id)
values (3, 3);
