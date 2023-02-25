create table users
(
	id integer not null
		constraint users_pkey
			primary key,
	user_name varchar(30),
	password varchar(255),
	email varchar(30),
	locked varchar
);

alter table users owner to postgres;

