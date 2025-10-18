create extension if not exists "uuid-ossp";

create table users (
                       id uuid primary key,
                       email varchar(320) not null unique,
                       full_name varchar(255) not null,
                       created_at timestamptz not null
);

create table tasks (
                       id uuid primary key,
                       user_id uuid not null references users(id) on delete cascade,
                       title varchar(255) not null,
                       description text,
                       status varchar(32) not null,
                       deleted boolean not null,
                       created_at timestamptz not null,
                       target_date timestamptz,
                       constraint tasks_status_chk check (status in ('PENDING','DONE'))
);
create index idx_tasks_user on tasks(user_id, deleted, status);

create table notifications (
                               id uuid primary key,
                               user_id uuid not null references users(id) on delete cascade,
                               message text not null,
                               read_flag boolean not null,
                               created_at timestamptz not null
);
