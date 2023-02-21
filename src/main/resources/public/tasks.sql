create table tasks
(
	id integer not null
		constraint task_pkey
			primary key,
	task_name varchar(100),
	deadline date,
	completed date,
	user_id integer
		constraint fk_user_id
			references users,
	status varchar(50),
	task_details varchar(300)
);

alter table tasks owner to postgres;

