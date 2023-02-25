create table roles
(
	id integer not null
		constraint roles_pkey
			primary key,
	role_name varchar(30)
);

alter table roles owner to postgres;

