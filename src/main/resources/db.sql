INSERT INTO `spring-security-test`.role (role_id, role) VALUES (1, 'ADMIN');
INSERT INTO `spring-security-test`.user (user_id, active, email, last_name, name, password) VALUES (1, 1, 'wisletmichel@gmail.com', 'Michel', 'Wislet', '$2a$10$M02LFt7qSFjCthQjnnApweltBPmwyxruPYccFyNQrkif9pR554yF6');
INSERT INTO `spring-security-test`.user_role (user_id, role_id) VALUES (1, 1);