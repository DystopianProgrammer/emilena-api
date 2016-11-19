alter table absence drop constraint FK8p80hfn0qcrep0hfjoi779xel;
alter table availability drop constraint FKj2chdcy99fvwbpo7yt83rm8kl;
alter table clients_staff drop constraint FKa8crjan2g6ek7ytcx8m2uauiq;
alter table clients_staff drop constraint FK7yxbva3utk6py3rjr2dik8mo2;
alter table PERSON drop constraint FK61rvdbphuu5b0ptt7rjbqw3nv;
alter table rota_entry drop constraint FKadsq4jno4v8kojdyplrs1oxk3;
alter table rota_entry drop constraint FK7ofulalwncq5igwbfgn15i1j8;
alter table rota_item drop constraint FKb7w7kv7y4wiih4edlf3gcen0u;
alter table rota_item drop constraint FK5y00iclwosfvuarmilceko3u1;
alter table system_user drop constraint FK8aa3l6bgfcnuu5h7as4kp2iba;
alter table tbl_roles drop constraint FKq45qsdxvocopwsx1h7qbuwrnm;

drop table if exists absence cascade;
drop table if exists availability cascade;
drop table if exists clients_staff cascade;
drop table if exists PERSON cascade;
drop table if exists rota cascade;
drop table if exists rota_entry cascade;
drop table if exists rota_item cascade;
drop table if exists system_user cascade;
drop table if exists tbl_roles cascade;

drop sequence hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;

create table absence (
    id int8 not null,
    absenceType int4,
    date bytea,
    reason varchar(255),
    person_id int8,
    primary key (id)
);

create table availability (
    id int8 not null,
    day_of_week varchar(255),
    from_time bytea not null,
    to_time bytea not null,
    person_id int8,
    primary key (id)
);

create table clients_staff (
    client_id int8 not null,
    staff_id int8 not null
);

create table PERSON (
    discriminator varchar(31) not null,
    id int8 not null,
    active boolean,
    first_line varchar(255),
    house_number varchar(255),
    post_code varchar(255),
    second_line varchar(255),
    town varchar(255),
    date_of_birth timestamp,
    email varchar(255),
    forename varchar(255) not null,
    personType varchar(255) not null,
    preferences varchar(1000),
    surname varchar(255) not null,
    telephone_number varchar(255),
    contractType varchar(255),
    contracted_hours int4,
    staffType varchar(255),
    system_user_id int8,
    primary key (id)
);

create table rota (
    id int8 not null,
    week_commencing bytea,
    primary key (id)
);

create table rota_entry (
    rota_id int8 not null,
    rota_item_id int8 not null
);

create table rota_item (
    id int8 not null,
    dayOfWeek varchar(255),
    finish_time bytea,
    start_time bytea,
    client_id int8,
    staff_id int8,
    primary key (id)
);

create table system_user (
    id int8 not null,
    password varchar(255) not null,
    user_name varchar(255) not null,
    staff_id int8,
    primary key (id)
);

create table tbl_roles (
    id int8 not null,
    roles varchar(255) not null
);

alter table rota_entry add constraint UK_bps1r273bqncc8kcngk2tcw53 unique (rota_item_id);
alter table system_user add constraint UK_204b9ercidw1baj3s3m9lnr33 unique (user_name);
alter table absence add constraint FK8p80hfn0qcrep0hfjoi779xel foreign key (person_id) references PERSON;
alter table availability add constraint FKj2chdcy99fvwbpo7yt83rm8kl foreign key (person_id) references PERSON;
alter table clients_staff add constraint FKa8crjan2g6ek7ytcx8m2uauiq foreign key (staff_id) references PERSON;
alter table clients_staff add constraint FK7yxbva3utk6py3rjr2dik8mo2 foreign key (client_id) references PERSON;
alter table PERSON add constraint FK61rvdbphuu5b0ptt7rjbqw3nv foreign key (system_user_id) references system_user;
alter table rota_entry add constraint FKadsq4jno4v8kojdyplrs1oxk3 foreign key (rota_item_id) references rota_item;
alter table rota_entry add constraint FK7ofulalwncq5igwbfgn15i1j8 foreign key (rota_id) references rota;
alter table rota_item add constraint FKb7w7kv7y4wiih4edlf3gcen0u foreign key (client_id) references PERSON;
alter table rota_item add constraint FK5y00iclwosfvuarmilceko3u1 foreign key (staff_id) references PERSON;
alter table system_user add constraint FK8aa3l6bgfcnuu5h7as4kp2iba foreign key (staff_id) references PERSON;
alter table tbl_roles add constraint FKq45qsdxvocopwsx1h7qbuwrnm foreign key (id) references system_user;

insert into system_user (id, password, user_name) values (1, 'superuser', 'superuser');
insert into tbl_roles (id, roles) values (1, 'STAFF');
insert into tbl_roles (id, roles) values (1, 'ADMIN');