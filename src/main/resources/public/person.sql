create table person
(
	id serial,
	name varchar(30),
	age integer,
	email varchar(30)
);

alter table person owner to postgres;

