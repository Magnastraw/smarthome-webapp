--create user,homes
INSERT INTO public.users
VALUES (DEFAULT, 'mymail@mail.ru', md5('123456d'), 'firstname', 'lastname', +79876543210, FALSE);

INSERT INTO public.data_types
VALUES (DEFAULT, 'String', 0),
  (DEFAULT, 'Link', 1);

INSERT INTO public.smart_homes
VALUES (DEFAULT, 'default home', '', 1),
  (DEFAULT, 'Smart home 1', '', 1),
  (DEFAULT, 'Smart home 2', '', 1);

INSERT INTO public.home_params
VALUES (DEFAULT, 1, 'Address', 'address', 1),
  (DEFAULT, 1, 'Link', 'http://www.outlink.com', 2);

-- add social services
INSERT INTO public.social_servicies (service_name, client_id, secret_key, service_type)
VALUES (
  'Google',
  '1073989536255-nbsn86e8818oianfu5dq8cod9kj4opin.apps.googleusercontent.com',
  'KGh55evCZ4QjTe8bbLlw1vFI',
  0
);

INSERT INTO public.social_servicies (service_name, client_id, secret_key, service_type)
VALUES (
  'Facebook',
  '140634186455346',
  '0c1f1e7967c96108dfc0943dd4f62643',
  1
);

INSERT INTO public.social_servicies (service_name, client_id, secret_key, service_type)
VALUES (
  'VK',
  '5899113',
  'BMOMj7CRdxPDu2lTNLmo',
  2
);

--add catalogs,specs,metrics,units
INSERT INTO public.catalogs (catalog_name, smart_home_id)
VALUES
  ('metricSpecsRootCatalog', 1),
  ('alarmSpecsRootCatalog', 1),
  ('policiesRootCatalog', 1),
  ('objectsRootCatalog', 1),
  ('metricSpecsRootCatalog', 2),
  ('alarmSpecsRootCatalog', 2),
  ('policiesRootCatalog', 2),
  ('objectsRootCatalog', 2),
  ('metricSpecsRootCatalog', 3),
  ('alarmSpecsRootCatalog', 3),
  ('policiesRootCatalog', 3),
  ('objectsRootCatalog', 3);

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('d_metricCat1', 1, 1),
  ('d_metricCat2', 1, 1),
  ('d_metricCat3', 1, 1),
  ('h1_metricCat1', 5, 2),
  ('h1_metricCat2', 5, 2),
  ('h1_metricCat3', 5, 2),
  ('h2_metricCat1', 9, 3),
  ('h2_metricCat2', 9, 3),
  ('h2_metricCat3', 9, 3);

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('d_metricSubcat1_1', 13, 1),
  ('d_metricSubcat2_1', 14, 1),
  ('d_metricSubcat2_2', 14, 1),
  ('d_metricSubcat2_3', 14, 1),
  ('d_metricSubcat3_1', 15, 1),
  ('h1_metricSubcat1_1', 16, 2),
  ('h1_metricSubcat2_1', 17, 2),
  ('h1_metricSubcat2_2', 17, 2),
  ('h1_metricSubcat2_3', 17, 2),
  ('h1_metricSubcat3_1', 18, 2),
  ('h2_metricSubcat1_1', 19, 3),
  ('h2_metricSubcat2_1', 20, 3),
  ('h2_metricSubcat2_2', 20, 3),
  ('h2_metricSubcat2_3', 20, 3),
  ('h2_metricSubcat3_1', 21, 3);

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('d_alarmCat1', 2, 1),
  ('d_alarmCat2', 2, 1),
  ('d_alarmCat3', 2, 1),
  ('h1_alarmCat1', 6, 2),
  ('h1_alarmCat2', 6, 2),
  ('h1_alarmCat3', 6, 2),
  ('h2_alarmCat1', 10, 3),
  ('h2_alarmCat2', 10, 3),
  ('h2_alarmCat3', 10, 3);

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('d_alarmSubcat1_1', 37, 1),
  ('d_alarmSubcat1_2', 37, 1),
  ('d_alarmSubcat2_1', 38, 1),
  ('d_alarmSubcat2_2', 38, 1),
  ('d_alarmSubcat3_1', 39, 1),
  ('h1_alarmSubcat1_1', 40, 2),
  ('h1_alarmSubcat1_2', 40, 2),
  ('h1_alarmSubcat2_1', 41, 2),
  ('h1_alarmSubcat2_2', 41, 2),
  ('h1_alarmSubcat3_1', 42, 2),
  ('h2_alarmSubcat1_1', 43, 3),
  ('h2_alarmSubcat1_2', 43, 3),
  ('h2_alarmSubcat2_1', 44, 3),
  ('h2_alarmSubcat2_2', 44, 3),
  ('h2_alarmSubcat3_1', 45, 3);


INSERT INTO public.units (unit_name, base_factor, label)
VALUES
  ('Temperature', 10, '°C'),
  ('Humidity', 10, '%'),
  ('kW', 1000, 'label');

INSERT INTO public.metric_specs (spec_name, unit_id, metric_type, catalog_id)
VALUES
  ('Temperature', 1, 'common', 22),
  ('Humidity', 2, 'common', 22),
  ('metricSpec3', 1, 'temperature', 23),
  ('metricSpec4', 1, 'temperature', 23),
  ('metricSpec5', 1, 'temperature', 24),
  ('metricSpec6', 1, 'temperature', 24),
  ('metricSpec7', 1, 'temperature', 28),
  ('metricSpec8', 1, 'temperature', 33),
  ('metricSpec9', 1, 'temperature', 34),
  ('metricSpec10', 1, 'temperature', 34),
  ('metricSpec11', 1, 'temperature', 36),
  ('metricSpec12', 1, 'temperature', 36);

INSERT INTO public.alarm_specs (spec_name, object_type, catalog_id)
VALUES
  ('alarmSpec1', 'type1', 13),
  ('alarmSpec2', 'type1', 16),
  ('alarmSpec3', 'type1', 16),
  ('alarmSpec4', 'type1', 17),
  ('alarmSpec5', 'type1', 17),
  ('alarmSpec6', 'type2', 17),
  ('alarmSpec7', 'type2', 18),
  ('alarmSpec8', 'type2', 18),
  ('alarmSpec9', 'type2', 19),
  ('alarmSpec10', 'type3', 19),
  ('alarmSpec11', 'type3', 19),
  ('alarmSpec12', 'type3', 20);

--add metrics,objects
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
  (DEFAULT, 1, NULL, 1, 1),
  (DEFAULT, 1, NULL, 2, 1),
  (DEFAULT, 2, NULL, 1, 1),
  (DEFAULT, 3, NULL, 2, 1),
  (DEFAULT, 4, NULL, 2, 1),
  (DEFAULT, 5, NULL, 1, 2),
  (DEFAULT, 6, NULL, 1, 2),
  (DEFAULT, 7, NULL, 1, 3),
  (DEFAULT, 8, NULL, 1, 3),
  (DEFAULT, 1, NULL, 2, 1),
  (DEFAULT, 10, NULL, 1, 1),
  (DEFAULT, 11, NULL, 2, 1);


INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   15 + (random() * 10) :: DECIMAL, 1);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   50 + (random() * 35) :: DECIMAL, 2);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   15 + (random() * 10) :: DECIMAL, 3);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   50 + (random() * 35) :: DECIMAL, 4);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   50 + (random() * 35) :: DECIMAL, 5);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   15 + (random() * 10) :: DECIMAL, 6);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   15 + (random() * 10) :: DECIMAL, 7);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   15 + (random() * 10) :: DECIMAL, 8);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   15 + (random() * 10) :: DECIMAL, 9);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   15 + (random() * 10) :: DECIMAL, 11);
INSERT INTO metrics_history
VALUES
  (DEFAULT, generate_series(TIMESTAMP '2017-01-01 00:00', TIMESTAMP '2017-01-20 00:00', INTERVAL '5 min'),
   50 + (random() * 35) :: DECIMAL, 12);

