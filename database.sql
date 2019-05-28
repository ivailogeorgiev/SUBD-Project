drop database if exists db;
create database db;
use db;

create table airports(
	id integer auto_increment primary key,
    Name varchar(150) not null,
    Location varchar(150) not null,
    Runways integer not null
);