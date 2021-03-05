create table author
(
    id        int auto_increment primary key,
    email     varchar(255) not null,
    full_name varchar(255) not null
);

create table book
(
    id        int auto_increment primary key,
    name      varchar(255) not null,
    iban      varchar(255) not null,
    author_id int          not null,
    constraint book_author_fk
        foreign key (author_id) references author (id)
            on update cascade on delete cascade
);