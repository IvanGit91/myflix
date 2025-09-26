-- ----------------------------
-- USERS
-- ----------------------------
-- psw: customer
INSERT INTO public.users (active, id, address, email, name, password, phone, role)
VALUES (true, 6, 'demo street', 'customer@test.com', 'customer',
        '$2a$10$QFmTt16AXBMilZFevzDJPuQepydSYLnlcHRzLYrTAilk8T257/BtS', '333123123', 'ROLE_CUSTOMER');
-- psw: employee
INSERT INTO public.users (active, id, address, email, name, password, phone, role)
VALUES (true, 7, 'demo street', 'employee@test.com', 'employee',
        '$2a$10$AG7.Qt57aFglvCGS3y3/8uJDEc4w5urCfoZaNXy77PiWN83V0Kizi', '333123123', 'ROLE_EMPLOYEE');
-- psw: manager
INSERT INTO public.users (active, id, address, email, name, password, phone, role)
VALUES (true, 5, 'demo street', 'manager@test.com', 'manager',
        '$2a$10$r4MbN00dPSeeVAt1RdtB0eRfmVmC0ccFusHqg/H/OlAgIaSmE23k6', '333123123', 'ROLE_MANAGER');
SELECT setval('users_id_seq', (SELECT max(id) FROM users));

-- ----------------------------
-- MyFlix
-- ----------------------------
-- Media Types
INSERT INTO media_types (id, name, display_name)
VALUES (1, 'MOVIE', 'Movie');
INSERT INTO media_types (id, name, display_name)
VALUES (2, 'SERIES', 'Serie');
INSERT INTO media_types (id, name, display_name)
VALUES (3, 'ANIME', 'Anime');
INSERT INTO media_types (id, name, display_name)
VALUES (4, 'DOCUMENTARY', 'Documentary');
SELECT setval('media_types_id_seq', (SELECT max(id) FROM media_types));

-- Media
INSERT INTO media (id, genre, title, media_type_id)
VALUES (1, 'Adventure', 'Naruto', 3);
INSERT INTO media (id, genre, title, media_type_id)
VALUES (2, 'Mage', 'Black Clover', 3);
INSERT INTO media (id, genre, title, media_type_id)
VALUES (3, 'Combat', 'Dragon Ball', 3);
INSERT INTO media (id, genre, title, media_type_id)
VALUES (4, 'Pirate', 'One Piece', 3);
SELECT setval('media_id_seq', (SELECT max(id) FROM media));

-- Characters
INSERT INTO characters (id, full_name, typology, media_id)
VALUES (1, 'Goku', 'Good', 3);
INSERT INTO characters (id, full_name, typology, media_id)
VALUES (2, 'Vegeta', 'Mixed', 3);
INSERT INTO characters (id, full_name, typology, media_id)
VALUES (3, 'Gohan', 'Good', 3);
INSERT INTO characters (id, full_name, typology, media_id)
VALUES (4, 'Naruto', 'Good', 1);
INSERT INTO characters (id, full_name, typology, media_id)
VALUES (5, 'Sasuke', 'Good', 1);
INSERT INTO characters (id, full_name, typology)
VALUES (6, 'Crillin', 'Good');
INSERT INTO characters (id, full_name, typology)
VALUES (7, 'Freezer', 'Bad');
INSERT INTO characters (id, full_name, typology)
VALUES (8, 'Luffy', 'Good');
INSERT INTO characters (id, full_name, typology)
VALUES (9, 'Zoro', 'Good');
INSERT INTO characters (id, full_name, typology)
VALUES (10, 'Sanji', 'Good');
INSERT INTO characters (id, full_name, typology)
VALUES (11, 'Yami', 'Good');
INSERT INTO characters (id, full_name, typology)
VALUES (12, 'Charmy', 'Good');
SELECT setval('characters_id_seq', (SELECT max(id) FROM characters));

-- Powers
INSERT INTO powers (id, name, effect, character_id)
VALUES (1, 'Rasengan', 'Powerful', 4);
INSERT INTO powers (id, name, effect)
VALUES (2, 'Sharingan', 'Hypnosis');
INSERT INTO powers (id, name, effect)
VALUES (3, 'Fireball', 'Area');
INSERT INTO powers (id, name, effect)
VALUES (4, 'Kungfu', 'Martial arts');
SELECT setval('powers_id_seq', (SELECT max(id) FROM powers));