-- first reg 3 new users
INSERT INTO object_types
VALUES
  (DEFAULT, 'Temperature', 'Temperature'),
  (DEFAULT, 'Humidity', 'Humidity');

INSERT INTO catalogs
VALUES
  (DEFAULT, 'Metric Catalog', NULL, 1),
  (DEFAULT, 'Metric Catalog', NULL, 2),
  (DEFAULT, 'Metric Catalog', NULL, 3);

INSERT INTO units
VALUES
  (DEFAULT, 'Temperature', 1, '°C', NULL),
  (DEFAULT, 'Humidity', 1, '%', NULL);

INSERT INTO metric_specs
VALUES
  (DEFAULT, 'Temperature ', 1, NULL, NULL, 'common', NULL, 1),
  (DEFAULT, 'Humidity', 2, NULL, NULL, 'common', NULL, 2);

INSERT INTO objects
VALUES
  (DEFAULT, 'Kitchen Temperature/Humidity Sensor', 'Temperature/Humidity sensor', 1, NULL, 1, 1),
  (DEFAULT, 'Room Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 1),
  (DEFAULT, 'Kitchen humidity sensor', 'Humidity sensor', 2, NULL, 1, 1),
  (DEFAULT, 'Room humidity sensor', 'Humidity sensor', 2, NULL, 1, 1),
  (DEFAULT, 'Kitchen Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 2),
  (DEFAULT, 'Room Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 2),
  (DEFAULT, 'Kitchen Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 3),
  (DEFAULT, 'Room Temperature Sensor', 'Temperature sensor', 1, NULL, 1, 3);

INSERT INTO metrics
VALUES
  (generate_series(1, 100),1,NULL,1,1);
INSERT INTO metrics
VALUES
  (generate_series(101, 200),2,NULL,1,1);
INSERT INTO metrics
VALUES
  (generate_series(201, 300),3,NULL,2,1);
INSERT INTO metrics
VALUES
  (generate_series(301, 400),4,NULL,2,1);
INSERT INTO metrics
VALUES
  (generate_series(401, 500),5,NULL,1,2);
INSERT INTO metrics
VALUES
  (generate_series(501, 600),6,NULL,1,2);
INSERT INTO metrics
VALUES
  (generate_series(601, 700),7,NULL,1,3);
INSERT INTO metrics
VALUES
  (generate_series(701, 800),8,NULL,1,3);
INSERT INTO metrics
VALUES
  (generate_series(801, 900),1,NULL,2,1);


INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-10 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,1 + (random() * 199) :: INT);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-10 00:00', INTERVAL '5 min'),50+(random() * 35) :: DECIMAL,200 + (random() * 199) :: INT);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-10 00:00', INTERVAL '5 min'),15+(random() * 10) :: DECIMAL,400 + (random() * 199) :: INT);
INSERT INTO metrics_history
VALUES
  (DEFAULT,generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-10 00:00', INTERVAL '5 min'),50+(random() * 35) :: DECIMAL,799 + (random() * 99) :: INT);