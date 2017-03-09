--create user,homes
INSERT INTO public.users
  VALUES(DEFAULT ,'mymail@mail.ru',md5('123456d'),'firstname','lastname',+79876543210,false);

INSERT INTO public.data_types
  VALUES (DEFAULT ,'String',0),
          (DEFAULT ,'Link',1);

INSERT INTO public.smart_homes
  VALUES (DEFAULT ,'default home','',1),
          (DEFAULT,'Smart home 1','',1),
          (DEFAULT,'Smart home 2','',1);

INSERt INTO public.home_params
  VALUES (DEFAULT ,1,'Address','address',1),
         (DEFAULT ,1,'Link','http://www.outlink.com',2);

-- add social services
ALTER TABLE public.social_servicies
  ADD COLUMN service_type BIGINT NOT NULL,
  ADD CONSTRAINT service_uk UNIQUE (service_type);

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
insert into public.catalogs (catalog_name,smart_home_id)
  values
    ('metricSpecsRootCatalog',1),
    ('alarmSpecsRootCatalog',1),
    ('policiesRootCatalog',1),
    ('objectsRootCatalog',1);

insert into public.catalogs (catalog_name,parent_catalog_id,smart_home_id)
	values
		('metricCatalog1',1,1),
		('metricCatalog2',1,1),
		('metricCatalog3',1,1);

insert into public.catalogs (catalog_name,parent_catalog_id,smart_home_id)
	values
		('metricSubcatalog1_1',5,1),
		('metricSubcatalog2_1',6,1),
		('metricSubcatalog2_2',6,1),
		('metricSubcatalog2_3',6,1),
		('metricSubcatalog3_1',7,1);

insert into public.catalogs (catalog_name,parent_catalog_id,smart_home_id)
	values
		('alarmCatalog1',2,1),
		('alarmCatalog2',2,1),
		('alarmCatalog3',2,1);

insert into public.catalogs (catalog_name,parent_catalog_id,smart_home_id)
	values
		('alarmSubcatalog1_1',13,1),
		('alarmSubcatalog1_2',13,1),
		('alarmSubcatalog2_1',14,1),
		('alarmSubcatalog2_2',14,1),
		('alarmSubcatalog3_1',15,1);

insert into public.units (unit_name,base_factor,label)
	values
	('Temperature',10,'°C'),
	('Humidity',10,'%'),
	('kW',1000,'label');

insert into public.metric_specs (spec_name,unit_id,metric_type,catalog_id)
	values
		('Temperature',1,'common',8),
		('Humidity',2,'common',8),
		('metricSpec3',1,'temperature',9),
		('metricSpec4',1,'temperature',9),
		('metricSpec5',1,'temperature',9),
		('metricSpec6',1,'temperature',10),
		('metricSpec7',1,'temperature',10),
		('metricSpec8',1,'temperature',11),
		('metricSpec9',1,'temperature',11),
		('metricSpec10',1,'temperature',12),
		('metricSpec11',1,'temperature',12),
		('metricSpec12',1,'temperature',12);

insert into public.alarm_specs (spec_name,object_type,catalog_id)
	values
		('alarmSpec1','type1',13),
		('alarmSpec2','type1',16),
		('alarmSpec3','type1',16),
		('alarmSpec4','type1',17),
		('alarmSpec5','type1',17),
		('alarmSpec6','type2',17),
		('alarmSpec7','type2',18),
		('alarmSpec8','type2',18),
		('alarmSpec9','type2',19),
		('alarmSpec10','type3',19),
		('alarmSpec11','type3',19),
		('alarmSpec12','type3',20);

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

