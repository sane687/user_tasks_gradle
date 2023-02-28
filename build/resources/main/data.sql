-- update tasks set status='EXPIRED' where deadline < current_date and completed is null;
insert into users values (1,'sasha@gmail.com',false,'$2a$12$HIUJJBHykFP/3U9AmkHtWOvd0EQyi8JKAUrWS8zNHt2gVp95Axxbe','sasha');
insert into users values (2,'dima@gmail.com',false,'$2a$12$nxIToafJSnsvgibehoACW.0lx7f8mNskOqnyNTIcIIOnl72niz02u','dima');
insert into roles values (1,'ADMIN');
insert into roles values (2,'MODERATOR');
insert into roles values (3,'USER');
insert into user_roles values (1,1);
insert into user_roles values (2,2);