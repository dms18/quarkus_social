CREATE DATABASE quarkus-social;

CREATE TABLE USERS( id int not null PRIMARY KEY AUTO_INCREMENT, name varchar(100) not null, age int not null );

CREATE TABLE POSTS( id int not null PRIMARY KEY AUTO_INCREMENT, user_id int not null,dataHora datetime, content varchar(255) not null, FOREIGN KEY (user_id) REFERENCES USERS(id) );