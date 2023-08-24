INSERT INTO activity(id, user_id, activity_type, weight_in_kg, start_date) VALUES (1, 1, 'CYCLING', 90.5, '2018-08-08 08:00:00.0');
INSERT INTO activity(id, user_id, activity_type, weight_in_kg, start_date) VALUES (2, 1, 'WALKING', 100.5, '2018-08-09 08:00:00.0');

INSERT INTO activity_record(id, activity_id, activity_sequence_number, latitude, longitude, heart_rate, timestamp)
VALUES (1, 1, 0, 1, 1, 1, '2018-08-08 08:00:00.0');
INSERT INTO activity_record(id, activity_id, activity_sequence_number, latitude, longitude, heart_rate, timestamp)
VALUES (2, 1, 2, 3, 3, 3, '2018-08-08 10:00:00.0');
INSERT INTO activity_record(id, activity_id, activity_sequence_number, latitude, longitude, heart_rate, timestamp)
VALUES (3, 1, 1, 2, 2, 2, '2018-08-08 09:00:00.0');

INSERT INTO activity_record(id, activity_id, activity_sequence_number, latitude, longitude, heart_rate, timestamp)
VALUES (4, 2, 0, 4, 4, 4, '2018-08-09 11:00:00.0');