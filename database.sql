show databases;
drop database gaenolja;
create database gaenolja;
use gaenolja;
alter database gaenolja default character set utf8 collate utf8_general_ci;
show tables;

create table user (
	userid varchar(200) primary key,
    nickname varchar(50) not null,
    phone varchar(50),
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
    constraint FK_socialuser_doginfo foreign key(userid) references user(userid)
    on update cascade,
	constraint FK_species_doginfo foreign key(speciesId) references species(id)
    on update cascade on delete cascade
);

create table hashtag (
	id integer primary key auto_increment,
    name varchar(30) not null
);

create table hotel (
	hotelnumber varchar(50) primary key,
    userid varchar(200) not null,
    hotelname varchar(50) not null,
    latitude decimal(10, 6) not null,
    longitude decimal(10, 6) not null,
    address varchar(200) not null,
    contact varchar(50) not null,
    info text not null,
    detail json not null,
    constraint FK_user_hotel foreign key(userid) references user(userid)
    on update cascade on delete cascade
);

create table hotelhash (
	id integer primary key auto_increment,
    hashtag int not null,
    hotelnumber varchar(50) not null,
	constraint FK_hash_hotel foreign key(hashtag) references hashtag(id)
    on update cascade on delete cascade,
	constraint FK_hotel_hash foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade
);

create table hotelroom (
	id integer primary key auto_increment,
    hotelnumber varchar(50) not null,
    roomname varchar(30) not null,
    price integer not null,
    minsize integer not null,
    maxsize integer not null,
    count integer not null,
    info varchar(1000),
    constraint FK_hotel_room foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade
);

create table cart (
	id integer primary key auto_increment,
    userid varchar(200) not null,
    hotelnumber varchar(50) not null,
    roomname varchar(30) not null,
    price integer not null,
	constraint FK_hotel_cart foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
    constraint FK_user_cart foreign key(userid) references user(userid)
    on update cascade on delete cascade
);
drop table reservation;
create table reservation (
	id integer primary key auto_increment,
    paidid integer,
    hotelnumber varchar(50) not null,
    userid varchar(200) not null,
    dog varchar(100),
    roomname varchar(30) not null,
    startdate datetime not null,
    finishdate datetime not null,
    count integer not null,
    visit integer not null,
    constraint FK_hotel_reservation foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
    constraint FK_user_reservation foreign key(userid) references user(userid)
    on update cascade on delete cascade,
	constraint FK_paid_reservation foreign key(paidid) references paid(id)
    on delete cascade
);

create table paid (
	id integer primary key auto_increment,
    info varchar(1000) not null
);


create table hotelpicture (
	id integer primary key auto_increment,
    hotelnumber varchar(50) not null,
    name varchar(30) not null,
    picture varchar(500) not null,
    constraint FK_hotel_picture foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade
);

desc hotel;

create table review (
	id integer primary key auto_increment,
	hotelnumber varchar(50) not null,
    userid varchar(200) not null,
    visitid integer not null,
    star decimal(2, 1) not null,
    content varchar(300),
    created datetime not null default now(),
	constraint FK_hotel_review foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
    constraint FK_user_review foreign key(userid) references user(userid)
    on update cascade on delete cascade
);
drop table response;
create table response (
	id integer primary key auto_increment,
	reviewid integer,
    userid varchar(200),
    heart integer not null,
    content varchar(300),
    created datetime not null default now(),
    constraint FK_user_response foreign key(userid) references user(userid)
    on update cascade on delete cascade,
    constraint FK_review_response foreign key(reviewid) references review(id)
    on update cascade on delete cascade
);

create table notification (
	id integer primary key auto_increment,
    userid varchar(200) not null,
    target varchar(200),
    hotelnumber varchar(50),
    subjects varchar(50) not null,
    content varchar(255) not null,
    created datetime not null default now(),
    constraint FK_hotel_notification foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade,
	constraint FK_user_notification_target foreign key(userid) references user(userid)
    on update cascade on delete cascade,
    constraint FK_user_notification foreign key(userid) references user(userid)
    on update cascade on delete cascade
);

create table chatroom(
	id integer primary key auto_increment
);

create table chat (
	id integer primary key auto_increment,
    chatid integer not null,
    receive varchar(200) not null,
    send varchar(200) not null,
    picture varchar(500) null,
    message varchar(500) not null,
    created datetime not null default now(),
    readmessage integer not null,
	constraint FK_user_chat_receive foreign key(receive) references user(userid)
    on update cascade on delete cascade,
	constraint FK_user_chat_send foreign key(send) references user(userid)
    on update cascade on delete cascade,
	constraint FK_chat_room foreign key(chatid) references chat(id)
    on update cascade on delete cascade
);

create table likes (
-- 다중 primary key 직접 지정 
	hotelnumber varchar(50),
    userid varchar(200),
    constraint FK_user_likes foreign key(userid) references user(userid)
    on update cascade on delete cascade,
    constraint FK_hotel_likes foreign key(hotelnumber) references hotel(hotelnumber)
    on update cascade on delete cascade
);