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

create table passenger(
    id integer auto_increment primary key,
    Name varchar(150) not null,
    age integer not null,
    gender varchar(150) not null,
    startingLocation varchar(150) not null
);

create table plane(
    id integer auto_increment primary key,
    Name varchar(150) not null,
    maxSeats int not null,
    freeSeats int not null
);

alter table airports auto_increment=11;
alter table flights auto_increment=101;


INSERT INTO airports(Name, Location, Runways) VALUES("stefanstefan2","suhodol",2);
INSERT INTO airports(Name, Location, Runways) VALUES("mitakamadafaka","getomilev",3);
INSERT INTO airports(Name, Location, Runways) VALUES("stefanMiGoLiza","suhatareka",1);

INSERT INTO flights(originID, destinationID, duration) VALUES(11, 12, 1);
INSERT INTO flights(originID, destinationID, duration) VALUES(13, 12, 1);
INSERT INTO flights(originID, destinationID, duration) VALUES(11, 13, 1);
INSERT INTO flights(originID, destinationID, duration) VALUES(12, 13, 1);


INSERT INTO passenger(Name, age, gender, startingLocation) VALUES("Stracimir", 12, "gey", "suhodol");

select f.id, a.Location, an.Location, f.duration from flights f
inner join airports a on f.originID = a.id
inner join airports an on f.destinationID = an.id
inner join passenger p on a.Location = p.startingLocation

