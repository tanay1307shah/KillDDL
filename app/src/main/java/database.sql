drop database if exists killddl;
create database killddl;
use killddl;

CREATE TABLE Users(
	userId INT(10) not null auto_increment unique,
    email varchar(500) not null unique,
    pwd varchar(100) not null,
    fullName VARCHAR(500) not null,
	gender VARCHAR(10) not null,
    imgUrl VARCHAR(1000) not null
);


create table EventsTable(
	eventId INT(10) not null auto_increment unique,
    userId INT(10) not null unique,
    title Varchar(100) not null,
    description varchar(500),
    eventDate date not null,
	notifyTime timestamp not null,
    color varchar(7) not null,
    importance INT(10) not null,
    frequency INT(10) not null,

	Foreign key (userId) REFERENCES Users(userId)
);

