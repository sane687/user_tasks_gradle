create table user_roles
(
	user_id integer
		constraint fk_user_id
			references users,
	role_id integer
		constraint fk_role_id
			references roles
);

alter table user_roles owner to postgres;

