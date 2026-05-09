
INSERT INTO books (title, author, price, stock) VALUES ('Clean Code',       'Robert C. Martin', 450.00, 10);
INSERT INTO books (title, author, price, stock) VALUES ('Head First Java',  'Kathy Sierra',     380.00,  8);
INSERT INTO books (title, author, price, stock) VALUES ('Spring in Action', 'Craig Walls',      520.00,  6);
INSERT INTO books (title, author, price, stock) VALUES ('Effective Java',   'Joshua Bloch',     490.00,  5);

INSERT INTO store_courses (name, code, instructor, duration, price) VALUES ('Java Web Development',  'WEB401', 'Dr. Ahmed', '6 weeks', 1200.00);
INSERT INTO store_courses (name, code, instructor, duration, price) VALUES ('Database Fundamentals', 'DB101',  'Dr. Sara',  '4 weeks',  900.00);
INSERT INTO store_courses (name, code, instructor, duration, price) VALUES ('Data Structures',       'CS201',  'Dr. Omar',  '8 weeks', 1500.00);
INSERT INTO store_courses (name, code, instructor, duration, price) VALUES ('Machine Learning 101',  'ML301',  'Dr. Hana',  '10 weeks',1800.00);

INSERT INTO students (id, name, email, phone, address, password, faculty, current_gpa, course_count, role)
VALUES (9999, 'Admin', 'admin', '00000000000', 'University HQ', 'admin123', 'Admin', 0.0, 0, 'ADMIN');

INSERT INTO students (id, name, email, phone, address, password, faculty, current_gpa, course_count, role)
VALUES (1, 'Jana',   'jana',          '01000000000', 'Cairo',        'jana123',   'CS',          3.5, 0, 'STUDENT');
INSERT INTO students (id, name, email, phone, address, password, faculty, current_gpa, course_count, role)
VALUES (2, 'Ahmed',  'ahmed@uni.edu', '01100000000', 'Alexandria',   'ahmed123',  'Engineering', 3.2, 0, 'STUDENT');
INSERT INTO students (id, name, email, phone, address, password, faculty, current_gpa, course_count, role)
VALUES (3, 'Sara',   'sara@uni.edu',  '01200000000', 'Giza',         'sara123',   'Business',    3.8, 0, 'STUDENT');

INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('CS101',  'Intro to CS',         'Dr. Ali',   3);
INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('CS201',  'Data Structures',     'Dr. Omar',  3);
INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('CS301',  'Operating Systems',   'Dr. Hana',  3);
INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('MATH101','Discrete Math',       'Dr. Sara',  3);
INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('ENG101', 'Engineering Drawing', 'Dr. Karim', 2);
INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('DB101',  'Database Systems',    'Dr. Nour',  3);
INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('BUS101', 'Intro to Management', 'Dr. Layla', 3);
INSERT INTO catalog_courses (course_code, course_name, instructor, credit_hours) VALUES ('BUS201', 'Marketing Basics',    'Dr. Mona',  3);

INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (1, 1, '1', 'A');
INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (1, 4, '1', 'B+');
INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (1, 2, '2', 'A-');
INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (1, 6, '2', 'B');

INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (2, 5, '1', 'A+');
INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (2, 4, '1', 'B');

INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (3, 7, '1', 'A');
INSERT INTO enrollments (student_id, catalog_course_id, semester, grade) VALUES (3, 8, '1', 'B+');

INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (1, 1, 'Sunday',    '8:30 - 10:00',  'Intro to CS',      'Hall A – B101');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (1, 4, 'Sunday',    '10:30 - 12:00', 'Discrete Math',    'Hall B – B202');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (1, 2, 'Monday',    '8:30 - 10:00',  'Data Structures',  'Lab 1 – C101');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (1, 6, 'Monday',    '12:30 - 2:00',  'Database Systems', 'Hall A – B101');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (1, 1, 'Tuesday',   '10:30 - 12:00', 'Intro to CS Lab',  'Lab 2 – C102');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (1, 2, 'Wednesday', '8:30 - 10:00',  'Data Structures',  'Hall B – B202');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (1, 6, 'Thursday',  '10:30 - 12:00', 'Database Systems', 'Lab 3 – C103');

INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (2, 5, 'Sunday',  '8:30 - 10:00',  'Engineering Drawing', 'Design Studio');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (2, 4, 'Monday',  '10:30 - 12:00', 'Discrete Math',       'Hall C – D101');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (2, 5, 'Wednesday','8:30 - 10:00', 'Engineering Drawing', 'Design Studio');

INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (3, 7, 'Sunday',  '10:30 - 12:00', 'Intro to Management', 'Hall D – E201');
INSERT INTO schedule_slots (student_id, catalog_course_id, day_name, time_slot, course_name, location)
VALUES (3, 8, 'Tuesday', '8:30 - 10:00',  'Marketing Basics',    'Hall E – E202');
