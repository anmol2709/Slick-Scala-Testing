CREATE TABLE employee(id INTEGER PRIMARY KEY ,name VARCHAR(200),experience INTEGER);
CREATE TABLE project(emp_id INTEGER , name VARCHAR(200) );
CREATE TABLE dependent(emp_id INTEGER  ,name VARCHAR(200),relation VARCHAR(200),age INTEGER);