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

insert into public.metric_specs (spec_name,metric_type,catalog_id)
	values
		('metricSpec1','temperature',8),
		('metricSpec2','temperature',8),
		('metricSpec3','temperature',9),
		('metricSpec4','temperature',9),
		('metricSpec5','temperature',9),
		('metricSpec6','temperature',10),
		('metricSpec7','temperature',10),
		('metricSpec8','temperature',11),
		('metricSpec9','temperature',11),
		('metricSpec10','temperature',12),
		('metricSpec11','temperature',12),
		('metricSpec12','temperature',12);

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

insert into public.units (unit_name,base_factor,label)
	values
	('Celsius',10,'label'),
	('mm Hg',10,'label'),
	('kW',1000,'label');