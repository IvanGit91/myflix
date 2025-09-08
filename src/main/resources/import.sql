-- ----------------------------
-- USERS
-- ----------------------------
INSERT INTO myflix.public.users (id, active, address, email, name, password, phone, role) VALUES (1, true, '3200 West Road', 'customer1@email.com', 'customer1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', '123456789', 'ROLE_CUSTOMER');
INSERT INTO myflix.public.users (id, active, address, email, name, password, phone, role) VALUES (2, true, '2000 John Road', 'manager1@email.com', 'manager1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', '987654321', 'ROLE_MANAGER');
INSERT INTO myflix.public.users (id, active, address, email, name, password, phone, role) VALUES (3, true, '222 East Drive ', 'employee1@email.com', 'employee1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', '123123122', 'ROLE_EMPLOYEE');
INSERT INTO myflix.public.users (id, active, address, email, name, password, phone, role) VALUES (4, true, '3100 Western Road A', 'customer2@email.com', 'customer2', '$2a$10$0oho5eUbDqKrLH026A2YXuCGnpq07xJpuG/Qu.PYb1VCvi2VMXWNi', '2343456', 'ROLE_CUSTOMER');

-- ----------------------------
-- MyFlix
-- ----------------------------
-- Media Types
INSERT INTO myflix.public.media_types (id, name, display_name) VALUES (1, 'MOVIE', 'Movie');
INSERT INTO myflix.public.media_types (id, name, display_name) VALUES (2, 'SERIE', 'Serie');
INSERT INTO myflix.public.media_types (id, name, display_name) VALUES (3, 'ANIME', 'Anime');
INSERT INTO myflix.public.media_types (id, name, display_name) VALUES (4, 'DOCUMENTARY', 'Documentary');

-- Media
INSERT INTO myflix.public.media (id, genre, title, media_type_id) VALUES (1, 'Adventure', 'Naruto', 3);
INSERT INTO myflix.public.media (id, genre, title, media_type_id) VALUES (2, 'Mage', 'Black Clover', 3);
INSERT INTO myflix.public.media (id, genre, title, media_type_id) VALUES (3, 'Combat', 'Dragon Ball', 3);
INSERT INTO myflix.public.media (id, genre, title, media_type_id) VALUES (4, 'Pirate', 'One Piece', 3);

-- Characters
INSERT INTO myflix.public.characters (id, full_name, typology, media_id) VALUES (1, 'Goku', 'Buono', 3);
INSERT INTO myflix.public.characters (id, full_name, typology, media_id) VALUES (2, 'Vegeta', 'Buono/Cattivo', 3);
INSERT INTO myflix.public.characters (id, full_name, typology, media_id) VALUES (3, 'Gohan', 'Buono', 3);
INSERT INTO myflix.public.characters (id, full_name, typology, media_id) VALUES (4, 'Naruto', 'Buono', 1);
INSERT INTO myflix.public.characters (id, full_name, typology, media_id) VALUES (5, 'Sasuke', 'Buono', 1);
INSERT INTO myflix.public.characters (id, full_name, typology) VALUES (6, 'Crillin', 'Buono');
INSERT INTO myflix.public.characters (id, full_name, typology) VALUES (7, 'Freezer', 'Cattivo');
INSERT INTO myflix.public.characters (id, full_name, typology) VALUES (8, 'Luffy', 'Buono');
INSERT INTO myflix.public.characters (id, full_name, typology) VALUES (9, 'Zoro', 'Buono');
INSERT INTO myflix.public.characters (id, full_name, typology) VALUES (10, 'Sanji', 'Buono');
INSERT INTO myflix.public.characters (id, full_name, typology) VALUES (11, 'Yami', 'Buono');
INSERT INTO myflix.public.characters (id, full_name, typology) VALUES (12, 'Charmy', 'Buono');

-- Powers
INSERT INTO myflix.public.powers (id, name, effect, character_id) VALUES (1, 'Rasengan', 'Powerful', 4);
INSERT INTO myflix.public.powers (id, name, effect) VALUES (2, 'Sharingan', 'Hypnosis');
INSERT INTO myflix.public.powers (id, name, effect) VALUES (3, 'Fireball', 'Area');
INSERT INTO myflix.public.powers (id, name, effect) VALUES (4, 'Kungfu', 'Martial arts');
