
insert into roles (role_name, description) values('ADMIN', "Administration Role");
insert into roles (role_name, description) values('USER', "User Role");
insert into roles (role_name, description) values('GUEST', "Guest Role");

insert into users (userid, password, first_name, second_name, is_enabled, is_confirm, is_locked)
    values('shieldguy@gmail.com', '$2a$10$BkhohUcuODqqMKhf/dR0guLR6Gr88FVI77a7L2frGdVKteWdygkMC', 'Simon', 'Kim', true, true, false);
insert into users (userid, password, first_name, second_name, is_enabled, is_confirm, is_locked)
    values('whoami@nospam.com', '$2a$10$BkhohUcuODqqMKhf/dR0guLR6Gr88FVI77a7L2frGdVKteWdygkMC', 'AmI', 'Who', true, true, false);
    
insert into users_roles (users_id, roles_id) values(1, 1);
insert into users_roles (users_id, roles_id) values(1, 2);
insert into users_roles (users_id, roles_id) values(2, 2);
