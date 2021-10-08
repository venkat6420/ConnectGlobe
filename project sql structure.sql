use notifier;
select * from dataUser;
select * from NoteBookTable;
show tables;
select * from customer_log;
drop table customer_log;
alter table customer_log  auto_increment=100;
select * from appointment_model;
select * from book_model;
select * from user_model;
drop table user_model;
insert into user_model(username,password,active,roles) values("venkat","Radha@6420",true,"ADMIN");
insert into user_model(username,password,active,roles) values("pavan","Anguluri@123",true,"USER");
insert into user_model(username,password,active,roles) values("venkat","Radha@6420",true,"ROLE_ADMIN");
insert into user_model(username,password,active,roles) values("pavan","Anguluri@123",true,"ROLE_USER");
select * from project_model;
insert into project_model(full_name,email,password,blood_group,gender,dob,mobile_number,state,district,city,pin_code,active,roles) values("Virtusa","virtusa@gmail.com","Demo@123","AB+","male","1998-09-21","9997755999","AP","EGDT","Kakinada","533324",true,"ROLE_ADMIN");
drop table project_model;
select * from request_model;
update project_model set roles="ROLE_ADMIN" where email="sai@gmail.com";
select * from credential_model;
select * from doctor_model;
drop table credential_model;
select * from patient_model;
delete from patient_model where id=3;
update credential_model set roles="ROLE_ADMIN" where username="dradha6420@gmail.com";
delete from credential_model where id=6;
update patient_model set roles="ROLE_ADMIN" where email="dradha6420@gmail.com";
select * from project_model;
select * from appointment_model;
insert into appointment_model (doctor_id,patient_id,patient_name,date,slot1,slot2,slot3,slot4,status) values(1,1,"pavan","2021-07-05",true,true,true,true,"Success");
drop table appointment_model;
select * from duplicate_appointment_model;
drop table duplicate_appointment_model;
select * from doctor_model;
select * from doctor_model where id=1;
select * from duplicate_appointment_model where doctor_id=1;
delete from duplicate_appointment_model where id=5;
select * from security_model;
select * from bank_security_model;
drop table bank_security_model;
select * from bank_details_model;
drop table bank_details_model;
alter table bank_details_model modify column profile_photo longblob;
describe bank_details_model;
drop table bank_details_model;
truncate table bank_details_model;
select * from media_details_model;
drop table media_details_model;
describe media_details_model;
alter table media_details_model modify column data longblob;
delete from media_details_model where id>=4;
create database virtusa;
use virtusa;
create table hotelManagement(
	id int,
    name varchar(99),
    aadharNumber varchar(99),
    mobileNumber varchar(99),
    roomNo int
    );
use virtusa;

select * from hotelManagement;
drop table hotelManagement;
create database training;
use training;
create table crud(
id int(11) auto_increment primary key,
fullname varchar(99),
email varchar(99),
password varchar(99),
mobileNumber varchar(10)
);
select * from crud;
insert into crud (fullname,email,password,mobileNumber) values("radha","radha@gmail.com","WERty@123","9147778855");
use training;
create table exams(
rollNumber varchar(12),
science int,
english int,
hindi int
);
select * from exams;
insert into exams values("17551A0427",94,92,99);
create database connectGlobe;
use connectGlobe;
create table registration(
userId int(11) auto_increment primary key,
fullname varchar(99),
email varchar(99),
password varchar(99),
mobileNumber varchar(99),
gender varchar(10),
roles varchar(99)
);
drop table registration;
select * from registration;
insert into registration (fullname,email,password,mobileNumber,gender,roles) values("Anguluri","pavankumar1@gmail.com","pavan@405","8712077727","Male","USER");
use notifier;
select * from media_details_model;
create table posts(
pId int auto_increment,
userId int not null,
fileStore longblob,
tagLine mediumText,
primary key(pId),
constraint posts foreign key(userId) references registration(userId)
);
drop table posts;
drop table answersTable;
create table answersTable(
cId int auto_increment,
pId int not null,
userId int not null,
reply Longtext,
primary key(cId),
constraint comments foreign key (pId) References posts(pId)
);
select * from posts;
delete from posts where pId>=1;
select fileStore from posts;
select * from answersTable;
create table issues(
rId int auto_increment,
userId int not null,
issue longtext,
primary key(rId),
constraint issues foreign key (userId) References registration(userId)
);
drop table issues;
create table suggestions(
sId int auto_increment,
rId int not null,
userId int not null,
suggestion longtext,
primary key(sId),
constraint suggestions foreign key(rId) References issues(rId)
);
select * from issues;
use notifier;
show tables;
select * from user_model;
use connectGlobe;
show tables;
select * from credential_model;
select * from user_model;
create database globe;
use globe;
show tables;
select * from user;


