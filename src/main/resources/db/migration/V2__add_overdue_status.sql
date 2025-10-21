alter table tasks drop constraint tasks_status_chk;
alter table tasks add constraint tasks_status_chk check (status in ('PENDING','DONE','OVERDUE'));
