DELETE FROM SchoolDB.Schedule;

DELETE FROM SchoolDB.Students;

DELETE FROM SchoolDB.Courses;

DELETE FROM SchoolDB.Groups;

INSERT INTO SchoolDB.Groups (group_id,group_name) VALUES
                                                      (1,'AB-12'),
                                                      (2,'CD-34'),
                                                      (3,'EF-56'),
                                                      (4,'GH-78'),
                                                      (5,'IJ-90'),
                                                      (6,'KL-12'),
                                                      (7,'MN-34'),
                                                      (8,'OP-56'),
                                                      (9,'QR-78'),
                                                      (10,'ST-90');

INSERT INTO SchoolDB.Courses (course_id, course_name, course_description) VALUES
                                                                              (1,'Mathematics', 'Study of numbers, quantity, and space'),
                                                                              (2,'Biology', 'Study of living organisms and their interactions with each other and their environments'),
                                                                              (3,'Physics', 'Study of matter, energy, space, and time'),
                                                                              (4,'Chemistry', 'Study of the composition, structure, properties, and change of matter'),
                                                                              (5,'History', 'Study of the past events, particularly in human affairs'),
                                                                              (6,'Literature', 'Study of written works, especially those considered to have artistic or intellectual value'),
                                                                              (7,'Computer Science', 'Study of algorithms, data structures, and computing systems'),
                                                                              (8,'Geography', 'Study of Earths landscapes, environments, and the relationships between people and their environments'),
                                                                              (9,'Art', 'Expression or application of human creative skill and imagination'),
                                                                              (10,'Music', 'Art form whose medium is sound and silence');

INSERT INTO SchoolDB.Students (student_id, group_id, first_name, last_name)
VALUES
    (1, 1, 'John', 'Smith'),
    (2, 2, 'Emma', 'Johnson'),
    (3, 3, 'Michael', 'Williams'),
    (4, 4, 'Sophia', 'Jones'),
    (5, 5, 'William', 'Brown'),
    (6, 6, 'Olivia', 'Davis'),
    (7, 7, 'James', 'Miller'),
    (8, 8, 'Ava', 'Wilson'),
    (9, 9, 'Benjamin', 'Moore'),
    (10, 10, 'Isabella', 'Taylor');

INSERT INTO SchoolDB.Schedule (student_id, course_id) VALUES
                                                          (1,1),
                                                          (2,2),
                                                          (3,3),
                                                          (4,4),
                                                          (5,5),
                                                          (6,6),
                                                          (7,7),
                                                          (8,8),
                                                          (9,9),
                                                          (10,10);
