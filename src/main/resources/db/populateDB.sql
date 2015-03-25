DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

-- password
INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');
-- admin
INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 100000);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_ADMIN', 100001);

-- meals
INSERT INTO meals (meal, calories, date, user_id)
VALUES ('Салат цезарь', 303, (timestamp '2015-03-16 19:54'), 100000);
INSERT INTO meals (meal, calories, date, user_id)
VALUES ('Семга на гриле', 230, (timestamp '2015-03-16 20:14'), 100000);