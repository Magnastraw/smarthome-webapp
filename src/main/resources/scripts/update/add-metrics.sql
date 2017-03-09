-- first reg 3 new users
INSERT INTO object_types
VALUES
  (DEFAULT, 'Temperature', 'Temperature'),
  (DEFAULT, 'Humidity', 'Humidity');

INSERT INTO objects
VALUES
  (DEFAULT, 'Kitchen Temperature/Humidity Sensor', 'Temperature/Humidity sensor', 1, NULL, 1, 1),
  (DEFAULT, 'Room Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 1),
  (DEFAULT, 'Kitchen humidity sensor', 'Humidity sensor', 2, NULL, 1, 1),
  (DEFAULT, 'Room humidity sensor', 'Humidity sensor', 2, NULL, 1, 1),
  (DEFAULT, 'Kitchen Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 2),
  (DEFAULT, 'Room Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 2),
  (DEFAULT, 'Kitchen Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 3),
  (DEFAULT, 'Room Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 3),
  (DEFAULT, 'Complex sensor', 'Complex sensor', 1, NULL, 1, 1),
  (DEFAULT, 'Sensor 1', 'Complex sensor', 1, 9, 1, 1),
  (DEFAULT, 'Sensor 2', 'Complex sensor', 1, 9, 1, 1);

INSERT INTO metrics
VALUES
  (DEFAULT ,1,NULL,1,1),
  (DEFAULT ,1,NULL,2,1),
  (DEFAULT ,2,NULL,1,1),
  (DEFAULT ,3,NULL,2,1),
  (DEFAULT ,4,NULL,2,1),
  (DEFAULT ,5,NULL,1,2),
  (DEFAULT ,6,NULL,1,2),
  (DEFAULT ,7,NULL,1,3),
  (DEFAULT ,8,NULL,1,3),
  (DEFAULT ,1,NULL,2,1),
  (DEFAULT ,10,NULL,1,1),
  (DEFAULT ,11,NULL,2,1);


INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,1);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),50+(random() * 35) :: DECIMAL,2);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,3);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),50+(random() * 35) :: DECIMAL,4);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),50+(random() * 35) :: DECIMAL,5);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,6);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,7);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,8);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,9);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,11);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),50+(random() * 35) :: DECIMAL,12);