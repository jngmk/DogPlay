show databases;
drop database gaenolja;
create database gaenolja;
use gaenolja;
alter database gaenolja default character set utf8 collate utf8_general_ci;
show tables;

create table socialuser (
	userid varchar(200) primary key,
    nickname varchar(50) not null,
    phone varchar(50) not null,
    picture varchar(500),
    social integer not null,
    admin integer not null
);

create table species (
	id integer primary key auto_increment,
    name varchar(30) not null
);

create table doginfo (
	id integer primary key auto_increment,
    userid varchar(200) not null,
	dogname varchar(50) not null,
    speciesId integer not null,
    size integer,
    age date,
    gender integer,
    picture varchar(100),
    detail JSON,
    constraint FK_socialuser_doginfo foreign key(userid) references socialuser(userid)
    on update cascade,
	constraint FK_species_doginfo foreign key(speciesId) references species(id)
    on update cascade on delete cascade
);

create table hotel (
	hotelnumber integer primary key,
    userid varchar(200) not null,
    hotelname varchar(50) not null,
    lacation json not null,
    contact varchar(50) not null,
    info text not null,
    detail json not null,
    constraint FK_user_hotel foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade
);

create table hotelroom (
	id integer primary key auto_increment,
    hotelnumber integer not null,
    roomname varchar(30) not null,
    price integer not null,
    minsize integer not null,
    maxsize integer not null,
    count integer not null,
    constraint FK_hotel_room foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade
);

create table cart (
	id integer primary key,
    userid varchar(200) not null,
    hotelnumber integer not null,
    roomname varchar(30) not null,
    price integer not null,
	constraint FK_hotel_cart foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
    constraint FK_user_cart foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade
--     constraint FK_hotelroom_cart foreign key(roomname) references hotelroom(roomname)
--     on update cascade on delete cascade,
-- 	constraint FK_hotelroom_cart foreign key(price) references hotelroom(price)
--     on update cascade on delete cascade
);

create table reservation (
	id integer primary key auto_increment,
    hotelnumber integer not null,
    userid varchar(200) not null,
    dog json,
    roomname json not null,
    startdate datetime not null,
    finishdate datetime not null,
    paid json not null,
    visit integer not null,
    constraint FK_hotel_reservation foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
    constraint FK_user_reservation foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade
);

create table hotelpicture (
	id integer primary key auto_increment,
    hotelnumber integer not null,
    name varchar(30) not null,
    picture varchar(500) not null,
    constraint FK_hotel_picture foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade
);

create table review (
	hotelnumber integer primary key,
    userid varchar(200) primary key,
    visitid integer primary key,
    star float not null,
    content varchar(300),
	constraint FK_hotel_review foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
    constraint FK_user_review foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade,
    constraint FK_reservation_review foreign key(visitid) references reservation(visit)
    on update cascade on delete cascade
);

create table response (
	hotelnumber integer primary key,
    userid varchar(200) primary key,
    visitid integer primary key,
    heart integer not null,
    content varchar(300),
	constraint FK_hotel_response foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
    constraint FK_user_response foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade,
    constraint FK_reservation_reponse foreign key(visitid) references reservation(visit)
    on update cascade on delete cascade
);

create table notification (
	id integer primary key auto_increment,
    userid varchar(200) not null,
    target varchar(200),
    hotelnumber integer,
    subjects varchar(50) not null,
    content varchar(255) not null,
    constraint FK_hotel_notification foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
	constraint FK_user_notification_target foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade,
    constraint FK_user_notification foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade
);

create table chat (
	id integer primary key auto_increment,
    receive varchar(200) not null,
    send varchar(200) not null,
    picture varchar(500) null,
    message varchar(500) not null,
	constraint FK_user_chat_receive foreign key(receive) references socialuser(userid)
    on update cascade on delete cascade,
	constraint FK_user_chat_send foreign key(send) references socialuser(userid)
    on update cascade on delete cascade
);

create table likes (
	hotelnumber integer primary key,
    userid varchar(200) primary key,
    constraint FK_user_likes foreign key(userid) references socialuser(userid)
    on update cascade on delete cascade,
    constraint FK_hotel_likes foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade
);