DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

-- password
INSERT INTO users (name, email, password, calories_per_day)
VALUES ('User', 'user@yandex.ru', '$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni', 2000);
-- admin
INSERT INTO users (name, email, password, calories_per_day)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju', 2500);

INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 100000);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_ADMIN', 100001);

-- meals
INSERT INTO meals (meal, calories, date, user_id)
VALUES ('Салат цезарь', 303, (timestamp '2015-03-16 19:54'), 100000);
INSERT INTO meals (meal, calories, date, user_id)
VALUES ('Семга на гриле', 230, (timestamp '2015-03-16 20:14'), 100000);
