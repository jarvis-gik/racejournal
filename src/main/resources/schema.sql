create table races (
  id int primary key,
  name varchar(255),
  date date,
  city varchar(255),
  state varchar(255),
  race_type varchar(255)
);

create sequence RACE_SEQ start with 1;