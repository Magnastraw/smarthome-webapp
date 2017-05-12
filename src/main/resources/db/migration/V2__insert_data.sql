--create user,homes
INSERT INTO public.users
VALUES
  (0, 'system@system.com', md5('system'), 'system', null, null, FALSE),
  (DEFAULT, 'smarthome.user@yandex.ru', md5('123456d'), 'firstname', 'lastname', +79876543210, FALSE);

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

-- add policies
insert into public.policies(name, description, catalog_id, status)
values
  ('High temperature checks template', '', 25, 1),
  ('Humidity inline checks', '', 25, 1),
  ('Signalization', '', 25, 1),
  ('Complex', '', 25, 0)
;

insert into public.rules(policy_id, name)
values
  (1, 'Raise WARN'),
  (1, 'Raise CRITICAL'),
  (1, 'Clear'),
  (1, 'Notify when raise'),
  (1, 'Notify when clear'),
  (2, 'Raise WARN and notify'),
  (2, 'Clear'),
  (2, 'Notify when clear'),
  (3, 'Raise CRITICAL and notify when open'),
  (3, 'Notify when clear'),
  (4, 'Complex condition')
;

insert into public.conditions(rule_id, type_id, parent_node_id, operator)
values
  (5, 6, null, null),
  (6, 3, null, null),
  (7, 6, null, null),
  (8, null, null, 1),
  (8, 7, 19, null),
  (8, 7, 19, null),
  (9, 7, null, null),
  (10, 3, null, null),
  (11, 6, null, null),
  (12, 7, null, null),
  (13, 7, null, null),
  (14, 7, null, null),
  (15, null, null, 0),
  (15, 5, 28, null),
  (15, 5, 28, null),
  (15, null, 28, 0),
  (15, 1, 31, null),
  (15, 4, 31, null)
;

insert into public.condition_params(condition_id, name, value)
values
  (16, 'policy', '1'),
  (16, 'metric', '1'),
  (16, 'minValue', '40'),
  (16, 'maxValue', '45'),
  (17, 'policy', '1'),
  (17, 'metric', '1'),
  (17, 'value', '45'),
  (18, 'policy', '1'),
  (18, 'metric', '1'),
  (18, 'minValue', '20'),
  (18, 'maxValue', '30'),
  (20, 'type', 'alarm'),
  (20, 'spec', '5'),
  (20, 'severity', 'WARN'),
  (21, 'type', 'alarm'),
  (21, 'spec', '5'),
  (21, 'severity', 'CRITICAL'),
  (22, 'type', 'alarm'),
  (22, 'spec', '5'),
  (22, 'severity', 'CLEAR'),
  (23, 'policy', '2'),
  (23, 'metric', '2'),
  (23, 'value', '70'),
  (23, 'object', '3'),
  (24, 'policy', '2'),
  (24, 'metric', '2'),
  (24, 'minValue', '68'),
  (24, 'maxValue', '70'),
  (24, 'object', '3'),
  (25, 'type', 'alarm'),
  (25, 'spec', '6'),
  (25, 'severity', 'CLEAR'),
  (26, 'type', 'event'),
  (26, 'spec', '3'),
  (27, 'type', 'alarm'),
  (27, 'spec', '3'),
  (27, 'severity', 'CLEAR'),
  (29, 'policy', '4'),
  (29, 'metric', '1'),
  (29, 'minValue', '20'),
  (29, 'maxValue', '30'),
  (29, 'object', '13'),
  (30, 'policy', '4'),
  (30, 'metric', '1'),
  (30, 'minValue', '20'),
  (30, 'maxValue', '30'),
  (30, 'object', '18'),
  (32, 'policy', '4'),
  (32, 'metric', '1'),
  (32, 'value', '20'),
  (32, 'object', '20'),
  (33, 'policy', '4'),
  (33, 'metric', '1'),
  (33, 'value', '30'),
  (33, 'object', '20')
;

insert into public.actions(rule_id, type, action_class, action_order)
values
  (5, 'true', 'com.netcracker.smarthome.business.policy.actions.RaiseAlarmAction', 1),
  (6, 'true', 'com.netcracker.smarthome.business.policy.actions.RaiseAlarmAction', 1),
  (7, 'true', 'com.netcracker.smarthome.business.policy.actions.RaiseAlarmAction', 1),
  (8, 'true', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 1),
  (9, 'true', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 1),
  (10, 'true', 'com.netcracker.smarthome.business.policy.actions.RaiseAlarmAction', 1),
  (10, 'true', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 2),
  (11, 'true', 'com.netcracker.smarthome.business.policy.actions.RaiseAlarmAction', 1),
  (12, 'true', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 1),
  (13, 'true', 'com.netcracker.smarthome.business.policy.actions.RaiseAlarmAction', 1),
  (13, 'true', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 2),
  (14, 'true', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 1),
  (15, 'true', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 1),
  (15, 'false', 'com.netcracker.smarthome.business.policy.actions.SendNotificationAction', 1)
;

insert into public.action_params(action_id, name, value)
values
  (34, 'spec', '5'),
  (34, 'severity', 'WARN'),
  (35, 'spec', '5'),
  (35, 'severity', 'CRITICAL'),
  (36, 'spec', '5'),
  (36, 'severity', 'CLEAR'),
  (37, 'message', 'High temperature!'),
  (37, 'preferredChannel', 'Email'),
  (38, 'message', 'Temperature normalized.'),
  (38, 'preferredChannel', 'Email'),
  (39, 'spec', '6'),
  (39, 'severity', 'WARN'),
  (40, 'message', 'Abnormal humidity!'),
  (40, 'preferredChannel', 'Email'),
  (41, 'spec', '6'),
  (41, 'severity', 'CLEAR'),
  (42, 'message', 'Humidity normalized.'),
  (42, 'preferredChannel', 'Email'),
  (43, 'spec', '3'),
  (43, 'severity', 'CRITICAL'),
  (44, 'message', 'Break-in attempt!'),
  (44, 'preferredChannel', 'Email'),
  (45, 'message', 'Alarm caused by break-in attempt cleared.'),
  (45, 'preferredChannel', 'Email'),
  (46, 'message', 'Temperature in house is normal.'),
  (47, 'message', 'Temperature in the house is not complied with the specified norms!')
;

-- add assignments
-- insert into public.assignments(policy_id, object_id)
-- values
--   (1, 13),
--   (1, 18),
--   (1, 20),
--   (3, 7),
--   (3, 11)
-- ;

--add metrics,objects
INSERT INTO public.object_types
VALUES
  (DEFAULT, 'RootController', 'RootController'),
  (DEFAULT, 'Controller', 'Controller'),
  (DEFAULT, 'Sensor', 'Sensor');
