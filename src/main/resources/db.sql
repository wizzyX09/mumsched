INSERT INTO role (role_id, role) VALUES (1, 'ADMIN');
INSERT INTO role (role_id, role) VALUES (2, 'FACULTY');
INSERT INTO role (role_id, role) VALUES (3, 'STUDENT');

INSERT INTO user (user_id, active, email, last_name, first_name, password) VALUES (1, 1, 'admin@mum.edu', 'Admin', 'Admin', '$2a$10$M02LFt7qSFjCthQjnnApweltBPmwyxruPYccFyNQrkif9pR554yF6');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);