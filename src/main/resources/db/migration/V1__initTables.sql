CREATE TABLE IF NOT EXISTS SchoolDB.Courses
(
    сourse_id serial NOT NULL,
    course_name text UNIQUE NOT NULL,
    course_description text,
    CONSTRAINT Pk_course_id PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS SchoolDB.Groups
(
    group_id serial UNIQUE NOT NULL,
    group_name character(255) NOT NULL,
    CONSTRAINT Pk_group_id PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS SchoolDB.Students
(
    student_id serial NOT NULL,
    group_id integer NOT NULL,
    first_name character(80) NOT NULL,
    last_name character(80)  NOT NULL,
    CONSTRAINT pk_student_id PRIMARY KEY (student_id),
    CONSTRAINT fk_group_id FOREIGN KEY (group_id)
    REFERENCES SchoolDB.Groups (group_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS SchoolDB.Schedule
(
    student_id integer NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT fk_course_id FOREIGN KEY (course_id)
    REFERENCES SchoolDB.courses (course_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT fk_student_id FOREIGN KEY (student_id)
    REFERENCES SchoolDB.Students (student_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);
