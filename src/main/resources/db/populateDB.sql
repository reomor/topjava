DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, datetime, calories, user_id) VALUES
  ('Завтрак', '2015-05-30 10:00:00',  1000,   100000),
  ('Обед',    '2015-05-30 13:00:00',  500,    100000),
  ('Ужин',    '2015-05-30 20:00:00',  500,    100000),
  ('Завтрак', '2015-05-31 10:00:00',  1000,   100000),
  ('Обед',    '2015-05-31 13:00:00',  555,    100000),
  ('Ужин',    '2015-05-31 20:00:00',  450,    100000);
