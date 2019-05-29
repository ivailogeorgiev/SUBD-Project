drop database if exists db;
create database db;
use db;

create table airports(
	id integer auto_increment primary key,
    Name varchar(150) not null,
    Location varchar(150) not null,
    Runways integer not null
);

create table flights(
	id integer auto_increment primary key,
    originID integer not null,
    destinationID integer not null,
    duration integer not null,
    foreign key(originID) references airports(id),
    foreign key(destinationID) references airports(id)
);

alter table airports auto_increment=11;
alter table flights auto_increment=101;
