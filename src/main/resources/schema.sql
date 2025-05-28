drop table student_task;
drop table student_group;
drop table students;
drop table tasks;
drop table groups;
drop table teachers;
drop table users;

create table  IF NOT EXISTS users(
	id bigserial PRIMARY KEY not null,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('STUDENT', 'TEACHER'))
);

CREATE TABLE IF NOT EXISTS students (
    id bigint PRIMARY KEY not null,
    cf_handler VARCHAR(255),
    acmp_id VARCHAR(255),
    foreign key(id) references users (id)
);

create table IF NOT EXISTS tasks (
	id bigserial primary key not null,
	platform varchar(20) not null,
	title varchar(200) not null,
	difficulty real CHECK (difficulty >= 1 AND difficulty <= 100),
	link varchar(100) not null
);

create table IF NOT EXISTS student_task(
	id bigserial primary key not null,
	student_id BIGINT,
	task_id BIGINT,
	verdict varchar(10) check (verdict in ('SUCCESS','FAIL')),
	last_changed_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	foreign key(student_id) references students (id),
	foreign key(task_id) references tasks (id)
);



create table  IF NOT EXISTS teachers(
    id bigint PRIMARY KEY not null,
    foreign key(id) references users (id)
);

create table IF NOT EXISTS groups(
	id bigserial primary key not null,
    name VARCHAR(255) NOT NULL,
    group_goal integer,
	min_avg_difficulty real CHECK (min_avg_difficulty >= 1 AND min_avg_difficulty <= 100),
	goal_set_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	due_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	owner_id bigint not null,
	foreign key(owner_id) references teachers(id)
);

create table IF NOT EXISTS student_group(
	id bigserial primary key not null,
	student_id BIGINT UNIQUE,
	group_id BIGINT,
	student_goal INTEGER,
	student_current_score INTEGER,
	foreign key(student_id) references students (id),
	foreign key(group_id) references groups (id)
);

INSERT INTO users (login, password, name, role) VALUES
('teacher1', 'hashed_password4', 'Dr. John Doe', 'TEACHER'),
('teacher2', 'hashed_password5', 'Prof. Jane Roe', 'TEACHER'),
('student1', 'hashed_password1', 'Alice Aohnson', 'STUDENT'),
('student2', 'hashed_password2', 'Bob Bmith', 'STUDENT'),
('student3', 'hashed_password3', 'Charlie Crown', 'STUDENT'),
('student4', 'hashed_password1', 'Dick Dickson', 'STUDENT'),
('student5', 'hashed_password2', 'Eva Evan', 'STUDENT'),
('student6', 'hashed_password3', 'Frank Ford', 'STUDENT');


INSERT INTO students (id,cf_handler,acmp_id) VALUES
(3, 'alice_cf', '1001'),
(4, 'bob_cf', '1002'),
(5, 'charlie_cf', '1003'),
(6, 'dick_cf', '1004'),
(7, 'eva_cf', '1005'),
(8, 'frank_cf', '1006');

INSERT INTO teachers (id) VALUES
(1),
(2);

INSERT INTO tasks (platform, title,difficulty, link) VALUES
('CODEFORCES', 'Two Sum Problem',45, 'codeforces.com/problemset/problem/1/A'),
('ACMP', 'Binary Search Implementation',23, 'acmp.ru/?main=task&id=2'),
('LEETCODE', 'Longest Substring Without Repeating Characters',100, 'leetcode.com/problems/longest-substring-without-repeating-characters/');

INSERT INTO student_task (student_id, task_id, verdict) VALUES
(3, 1, 'SUCCESS'),
(3, 2, 'FAIL'),
(4, 1, 'SUCCESS'),
(4, 3, 'SUCCESS'),
(5, 2, 'FAIL'),
(5, 3, 'SUCCESS');

INSERT INTO groups (owner_id,name,group_goal,min_avg_difficulty) VALUES
(1,'Basic Course 2024', 110,1),
(1,'Basic Course 2025', 100,1),
(2,'Advanced Course 2025', 150,50),
(2,'СМЕШАРИКИ', 300,70);


INSERT INTO student_group (student_id, group_id, student_goal,student_current_score) VALUES
(3, 1, 10,6),
(4, 1, 10,4),
(5, 1, 10,11),
(6, 2, 30,23),
(7, 2, 30,66),
(8, 2, 30,12);

SELECT * FROM students;
SELECT * FROM users;
SELECT * FROM teachers;
SELECT * FROM tasks;
SELECT * FROM groups;
SELECT * FROM student_task;
SELECT * FROM student_group;











