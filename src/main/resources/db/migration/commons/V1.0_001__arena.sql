create table qa_arena
(
	id ${DB_TYPE_UUID} not null,
	name ${DB_TYPE_VARCHAR}(50) not null,
    seats ${DB_TYPE_BIGINT} not null,
    createUser ${DB_TYPE_VARCHAR}(100),
    createDateTime ${DB_TYPE_DATETIME},
	primary key (id)
);

create unique index qa_arena_lk
	on qa_arena (name);

${GO}

insert into qa_arena (id, name, seats, createUser, createDateTime)
values ('00000000-0000-0000-0000-000000000001', 'Underground', 200, 'admin', ${NOW});

insert into qa_arena (id, name, seats, createUser, createDateTime)
values ('00000000-0000-0000-0000-000000000002', 'Space', 1000, 'admin', ${NOW});



