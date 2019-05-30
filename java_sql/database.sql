drop database if exists db;
create database db;
use db;

create table airports(
	id integer auto_increment primary key,
    Name varchar(150) not null,
    Location varchar(150) not null,
    Runways integer not null
);

create table planes(
	id integer auto_increment primary key,
    model varchar(100),
    capacity integer not null,
    airportID integer,
    foreign key(airportID) references airports(id)
);

create table flights(
	id integer auto_increment primary key,
    originID integer not null,
    destinationID integer not null,
    duration integer not null,
    foreign key(originID) references airports(id),
    foreign key(destinationID) references airports(id)
);


create table passenger(
    id integer auto_increment primary key,
    Name varchar(150) not null,
    age integer not null,
    gender varchar(150) not null,
    location varchar(150) not null
);

alter table airports auto_increment=11;
alter table planes auto_increment=11;
alter table flights auto_increment=101;

