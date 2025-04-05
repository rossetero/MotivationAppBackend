drop table student_task;
drop table teacher_group;
drop table student_group;
drop table students;
drop table tasks;
drop table teachers;
drop table groups;


CREATE TABLE IF NOT EXISTS students (
    id bigserial PRIMARY KEY not null,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('STUDENT', 'TEACHER')),
    cf_handler VARCHAR(255),
    acmp_id VARCHAR(255)
);

create table IF NOT EXISTS tasks (
	id bigserial primary key not null,
	platform varchar(20) not null,
	title varchar(200) not null,
	link varchar(100) not null
);

create table IF NOT EXISTS student_task(
	id bigserial primary key not null,
	student_id BIGINT,
	task_id BIGINT,
	verdict varchar(10) check (verdict in ('SUCCESS','FAIL')),
	foreign key(student_id) references students (id),
	foreign key(task_id) references tasks (id)
);

create table IF NOT EXISTS groups(
	id bigserial primary key not null,
	group_goal integer
);

create table  IF NOT EXISTS teachers(
	id bigserial PRIMARY KEY not null,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('STUDENT', 'TEACHER'))
);

create table  IF NOT EXISTS teacher_group(
	teacher_id BIGINT,
	group_id BIGINT,
	foreign key(teacher_id) references teachers (id),
	foreign key(group_id) references groups (id)
);

create table IF NOT EXISTS student_group(
	id bigserial primary key not null,
	student_id BIGINT,
	group_id BIGINT,
	student_goal INTEGER,
	foreign key(student_id) references students (id),
	foreign key(group_id) references groups (id)
);

INSERT INTO students (login, password, name, role, cf_handler, acmp_id) VALUES
('student1', 'hashed_password1', 'Alice Johnson', 'STUDENT', 'alice_cf', '1001'),
('student2', 'hashed_password2', 'Bob Smith', 'STUDENT', 'bob_cf', '1002'),
('student3', 'hashed_password3', 'Charlie Brown', 'STUDENT', 'charlie_cf', '1003');

INSERT INTO teachers (login, password, name, role) VALUES
('teacher1', 'hashed_password4', 'Dr. John Doe', 'TEACHER'),
('teacher2', 'hashed_password5', 'Prof. Jane Roe', 'TEACHER');

INSERT INTO tasks (platform, title, link) VALUES
('CODEFORCES', 'Two Sum Problem', 'https://codeforces.com/problemset/problem/1/A'),
('ACMP', 'Binary Search Implementation', 'https://acmp.ru/?main=task&id=2'),
('LEETCODE', 'Longest Substring Without Repeating Characters', 'https://leetcode.com/problems/longest-substring-without-repeating-characters/');

INSERT INTO student_task (student_id, task_id, verdict) VALUES
(1, 1, 'SUCCESS'),
(1, 2, 'FAIL'),
(2, 1, 'SUCCESS'),
(2, 3, 'SUCCESS'),
(3, 2, 'FAIL'),
(3, 3, 'SUCCESS');

INSERT INTO groups (group_goal) VALUES
(110),
(150);

INSERT INTO teacher_group (teacher_id, group_id) VALUES
(1, 1),
(2, 2);

INSERT INTO student_group (student_id, group_id, student_goal) VALUES
(1, 1, 10),
(2, 1, 10),
(3, 2, 10),
(1, 2, 30),
(2, 2, 30),
(3, 1, 30);

SELECT * FROM students;
SELECT * FROM teachers;
SELECT * FROM tasks;
SELECT * FROM groups;
SELECT * FROM student_task;
SELECT * FROM teacher_group;
SELECT * FROM student_group;










