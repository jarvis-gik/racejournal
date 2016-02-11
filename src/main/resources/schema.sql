create table races (
  id int primary key,
  name varchar(255),
  date date,
  city varchar(255),
  state varchar(255),
  race_type varchar(255)
);

create table riders (
  id int primary key,
  first_name varchar(255),
  last_name varchar(255),
  city varchar(255),
  state varchar(255),
  usac_number int,
  club varchar(255)
);

create table race_results (
  id int primary key,
  placing int,
  field_size int,
  race_result_type varchar(510),
  percentile int,
  description text,
  rider_id int,
  race_id int
--   foreign key (rider_id) references riders(id)
);

ALTER TABLE race_results ADD CONSTRAINT race_results_riders_fk FOREIGN KEY (rider_id) REFERENCES riders(id);
ALTER TABLE race_results ADD CONSTRAINT race_results_races_fk FOREIGN KEY (race_id) REFERENCES races(id);

create sequence RACE_SEQ start with 1;
create sequence RIDER_SEQ start with 1;
create sequence RACE_RESULT_SEQ start with 1;