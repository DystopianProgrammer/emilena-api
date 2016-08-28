insert into system_user (su_id, password, user_name) values (1, 'secret!!', 'admin@admin.com');

insert into role (role_id, role_type) values (1, 'ADMIN');
insert into role (role_id, role_type) values (2, 'STAFF');

insert into system_user_role (system_user_id, role_id) values (1, 1);
insert into system_user_role (system_user_id, role_id) values (1, 2);
