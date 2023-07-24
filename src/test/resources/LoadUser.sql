INSERT INTO _user(id, first_name, last_name, email, username, password)
VALUES (1, 'Jan', 'Kowalski', 'jankowalski@gmail.com', 'jan123', 'password1');

INSERT INTO authority(id, authorities) VALUES (1, 'USER_LOGGED');
INSERT INTO authority(id, authorities) VALUES (1, 'USER_PREMIUM');

INSERT INTO _user(id, first_name, last_name, email, username, password)
VALUES (2, 'Maciej', 'Nowak', 'maciejnowak@wp.pl', 'maciej321', 'password2');

INSERT INTO authority(id, authorities) VALUES (2, 'USER_LOGGED');
INSERT INTO authority(id, authorities) VALUES (2, 'USER_PREMIUM');