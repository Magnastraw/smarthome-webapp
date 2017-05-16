--create user,homes
INSERT INTO public.users
VALUES
  (0, 'system@system.com', md5('system'), 'system', null, null, FALSE, DEFAULT ),
  (DEFAULT, 'smarthome.user@yandex.ru', md5('123456d'), 'firstname', 'lastname', +79876543210, FALSE, 1);

INSERT INTO public.data_types
  VALUES  (DEFAULT, 'String', 0),
          (DEFAULT, 'Link', 1);

INSERT INTO public.smart_homes
VALUES (DEFAULT, 'Apartment in the central district', 'TO DO', 1),
  (DEFAULT, 'Apartment', 'TO DO', 1)
;

INSERT INTO public.home_params
VALUES  (DEFAULT, 1, 'Address', 'Pl. Lenina 15, ap. 10', 1),
        (DEFAULT, 1, 'Home page', 'http://localhost:8083/', 2);

-- add social services
INSERT INTO public.social_services (service_name, client_id, secret_key, service_type)
VALUES (
  'Google',
  '1073989536255-nbsn86e8818oianfu5dq8cod9kj4opin.apps.googleusercontent.com',
  'KGh55evCZ4QjTe8bbLlw1vFI',
  0
);

INSERT INTO public.social_services (service_name, client_id, secret_key, service_type)
VALUES (
  'Facebook',
  '140634186455346',
  '0c1f1e7967c96108dfc0943dd4f62643',
  1
);


INSERT INTO public.social_services (service_name, client_id, secret_key, service_type)
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
  ('objectsRootCatalog', 2);

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('My metric catalog', 1, 1),
  ('My metric catalog', 5, 2)
;

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('Metric subcatalog', 9, 1),
  ('Metric subcatalog', 10, 2)
;

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('Event alarm specs', 2, 1),
  ('Metric alarm specs', 2, 1),
  ('Event alarm specs', 6, 2),
  ('Metric alarm specs', 6, 2)
;

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES
  ('New specs', 13, 1),
  ('Old specs', 13, 1),
  ('New specs', 14, 1),
  ('Old specs', 14, 1),
  ('New specs', 15, 1),
  ('Old specs', 15, 1),
  ('New specs', 16, 1),
  ('Old specs', 16, 1)
;

INSERT INTO public.catalogs (catalog_name, parent_catalog_id, smart_home_id)
VALUES ('Policies', 3, 1);


INSERT INTO public.units (unit_name, base_factor, label)
VALUES
  ('Celsius', 1, '°C'),
  ('Percent', 1, '%'),
  ('Cubic metre', 1, 'm³'),
  ('Kilowatt-hour', 1000, 'kWh');

INSERT INTO public.metric_specs (spec_name, unit_id, metric_type, catalog_id)
VALUES
  ('Temperature', 1, 'common', 11),
  ('Humidity', 2, 'common', 11),
  ('WaterFlow', 3, 'common', 11),
  ('Power', 4, 'common', 11),
  ('Temperature', 1, 'common', 12),
  ('Humidity', 2, 'common', 12),
  ('WaterFlow', 3, 'common', 12),
  ('Power', 4, 'common', 12)
;

INSERT INTO public.alarm_specs (spec_name, object_type, catalog_id)
VALUES
  ('On', 'ON event object', 17),
  ('Off', 'OFF event object', 17),
  ('Open', 'OPEN event object', 17),
  ('Close', 'CLOSE event object', 17),
  ('Abnormal temperature', 'temperature sensor', 19),
  ('Abnormal humidity', 'humidity sensor', 19),
  ('On', 'ON event object', 21),
  ('Off', 'OFF event object', 21),
  ('Open', 'OPEN event object', 21),
  ('Close', 'CLOSE event object', 21),
  ('Abnormal temperature', 'temperature sensor', 23),
  ('Abnormal humidity', 'humidity sensor', 23)
;

-- add condition types
insert into public.condition_types(name, description, condition_class)
values
  ('Equals or greater than', '>= value', 'com.netcracker.smarthome.business.policy.conditions.EqualsOrGreaterThanCondition'),
  ('Equals or less than', '<= value', 'com.netcracker.smarthome.business.policy.conditions.EqualsOrLessThanCondition'),
  ('Greater than', '> value', 'com.netcracker.smarthome.business.policy.conditions.GreaterThanCondition'),
  ('Less than', '< value', 'com.netcracker.smarthome.business.policy.conditions.LessThanCondition'),
  ('Exclusive between', 'value1 < … < value2', 'com.netcracker.smarthome.business.policy.conditions.StrictRangeCondition'),
  ('Inclusive between', 'value1 <= … <= value2', 'com.netcracker.smarthome.business.policy.conditions.NonstrictRangeCondition'),
  ('Is', 'Match event type', 'com.netcracker.smarthome.business.policy.conditions.EventCondition')
;

--add metrics,objects
INSERT INTO public.object_types
VALUES
  (DEFAULT, 'RootController', 'RootController'),
  (DEFAULT, 'Controller', 'Controller'),
  (DEFAULT, 'Sensor', 'Sensor');
