insert into system_user (id, forename, surname, password, user_name) values (1, 'Geoff', 'Perks', 'secret!!', 'geoff.perks@me.com');
insert into system_user (id, forename, surname, password, user_name) values (2, 'Emily', 'Boyle', 'foobar!!', 'emily.boyle@live.com');

insert into role (id, role_type) values (1, 'ADMIN');
insert into role (id, role_type) values (2, 'SYSTEM');

insert into systemuser_role (system_user_id, role_id) values (1, 1);
insert into systemuser_role (system_user_id, role_id) values (1, 2);
insert into systemuser_role (system_user_id, role_id) values (2, 2);