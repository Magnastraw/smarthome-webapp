-- first reg 3 new users
INSERT INTO object_types
VALUES
  (DEFAULT ,'tempreture', 'tempreture'),
  (DEFAULT ,'humidity', 'humidity');

INSERT INTO catalogs
VALUES
  (DEFAULT,'metric catalog', NULL, 1),
  (DEFAULT,'metric catalog', NULL, 2),
  (DEFAULT, 'metric catalog', NULL, 3);

INSERT INTO metric_specs
VALUES
  (DEFAULT ,'tempreture', NULL, NULL, NULL, 'common', NULL, 1),
  (DEFAULT ,'humidity', NULL, NULL, NULL, 'common', NULL, 2);

INSERT INTO objects
VALUES
  (DEFAULT ,'sensor1', 'sensor tempreture', 1, NULL, 1, 1),
  (DEFAULT ,'sensor2', 'sensor tempreture', 1, NULL, 1, 1),
  (DEFAULT ,'sensor3', 'sensor humidity', 2, NULL, 1, 1),
  (DEFAULT ,'sensor4', 'sensor humidity', 2, NULL, 1, 1),
  (DEFAULT ,'sensor1', 'sensor tempreture', 1, NULL, 1, 2),
  (DEFAULT ,'sensor2', 'sensor tempreture', 1, NULL, 1, 2),
  (DEFAULT ,'sensor1', 'sensor tempreture', 1, NULL, 1, 3),
  (DEFAULT, 'sensor2', 'sensor tempreture', 1, NULL, 1, 3);

INSERT INTO metrics
VALUES
  (generate_series(1,100),
   1 + (random() * 7) :: INT,
   null,
   1 + (random() * 1) :: INT,
   1 + (random() * 2) :: INT);

INSERT INTO metrics_history
VALUES
  (DEFAULT,
   generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-10 00:00', INTERVAL '5 min'),
   (random() * 35) :: DECIMAL,
   1 + (random() * 99) :: INT);