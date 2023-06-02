# OC_Project_7
OpenClassrooms Project 7.


To use the app, you have to create an user.
Here how to do it: 

Create a file called data.sql in 'src/main/resources'
Copy this : 

insert into users (id, fullname, password, role, username)
values (1, 'Admin', '$2a$10$yfgW8kQLB.38nBcTDVxXWeBzVeoXsOFz5Icvr81R6nm3icBE9tgIe', 'ADMIN', 'admin');


In application.properties add the following: 
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql


When you start the application, use the following account :
username : admin
password : Password12@


