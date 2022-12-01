INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER')
ON CONFLICT DO NOTHING;
INSERT INTO users (id, name, email, password)
VALUES (1, 'admin', 'admin@gmail.com',
        '$2a$12$yB0XwZlTKdxdRst54Wmhg.SVW.YwWiSYjKi0yFvKQYzXNK4eGQmE6')
ON CONFLICT DO NOTHING;
INSERT INTO user_role(user_id, role_id)
VALUES (1, 1),
       (1, 2)
ON CONFLICT DO NOTHING;