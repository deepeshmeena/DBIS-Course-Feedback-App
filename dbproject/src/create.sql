create table feedback(
   id               varchar(5),
   feedback_id serial primary key,
   course_id    varchar(8),
   sec_id     varchar(8), 
   semester   varchar(6),
   year     numeric(4,0),
    foreign key (course_id,sec_id, semester, year) references section
    on delete cascade,
   foreign key (id) references student(ID)
    on delete cascade   
      
        );


create table feedback_value(
feedback_id int primary key,
que_id1 int ,
que_id2 int ,
que_id3 int ,
que_id4 int ,
que_id5 int ,
view_permission int,
upvote int,
que_id10 varchar(1000),

 foreign key (feedback_id) references tryfeedback2
    on delete cascade

);




create table department
  (dept_name    varchar(20), 
   building   varchar(15), 
   budget           numeric(12,2) check (budget > 0),
   primary key (dept_name)
  );

create table course
  (course_id    varchar(8), 
   title      varchar(50), 
   dept_name    varchar(20),
   credits    numeric(2,0) check (credits > 0),
   primary key (course_id),
   foreign key (dept_name) references department
    on delete set null
  );

create table instructor
  (ID     varchar(5), 
   name     varchar(20) not null, 
   dept_name    varchar(20), 
   salary     numeric(8,2) check (salary > 29000),
   primary key (ID),
   foreign key (dept_name) references department
    on delete set null
  );

create table section
  (course_id    varchar(8), 
         sec_id     varchar(8),
   semester   varchar(6)
    check (semester in ('Fall', 'Winter', 'Spring', 'Summer')), 
   year     numeric(4,0) check (year > 1701 and year < 2100), 
   building   varchar(15),
   room_number    varchar(7),
   time_slot_id   varchar(4),
   primary key (course_id, sec_id, semester, year),
   foreign key (course_id) references course
    on delete cascade,
   foreign key (building, room_number) references classroom
    on delete set null
  );

create table teaches
  (ID     varchar(5), 
   course_id    varchar(8),
   sec_id     varchar(8), 
   semester   varchar(6),
   year     numeric(4,0),
   primary key (ID, course_id, sec_id, semester, year),
   foreign key (course_id,sec_id, semester, year) references section
    on delete cascade,
   foreign key (ID) references instructor
    on delete cascade
  );

create table student
  (ID     varchar(5), 
   name     varchar(20) not null, 
   dept_name    varchar(20), 
   tot_cred   numeric(3,0) check (tot_cred >= 0),
   primary key (ID),
   foreign key (dept_name) references department
    on delete set null
  );

create table takes
  (ID     varchar(5), 
   course_id    varchar(8),
   sec_id     varchar(8), 
   semester   varchar(6),
   year     numeric(4,0),
   feedbackStatus bit(1),
   grade            varchar(2),
   primary key (ID, course_id, sec_id, semester, year),
   foreign key (course_id,sec_id, semester, year) references section
    on delete cascade,
   foreign key (ID) references student
    on delete cascade
  );



create table current(
    sec_id      varchar(8), 
    semester    varchar(6),
    year      numeric(4,0)
    
);

create table superuser(
id int primary key,
password int
);

create table app_feedback(
ftext varchar(1000)
);