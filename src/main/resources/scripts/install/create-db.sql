CREATE SEQUENCE public.condition_types_type_id_seq;

CREATE TABLE public.condition_types (
  type_id BIGINT NOT NULL DEFAULT nextval('public.condition_types_type_id_seq'),
  name VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  condition_class VARCHAR NOT NULL,
  CONSTRAINT condition_types_pk PRIMARY KEY (type_id)
);


ALTER SEQUENCE public.condition_types_type_id_seq OWNED BY public.condition_types.type_id;

CREATE SEQUENCE public.data_types_type_id_seq;

CREATE TABLE public.data_types (
  type_id BIGINT NOT NULL DEFAULT nextval('public.data_types_type_id_seq'),
  name VARCHAR NOT NULL,
  type BIGINT NOT NULL,
  CONSTRAINT data_types_pk PRIMARY KEY (type_id)
);


ALTER SEQUENCE public.data_types_type_id_seq OWNED BY public.data_types.type_id;

CREATE SEQUENCE public.object_types_type_id_seq;

CREATE TABLE public.object_types (
  object_type_id BIGINT NOT NULL DEFAULT nextval('public.object_types_type_id_seq'),
  name VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  CONSTRAINT object_types_pk PRIMARY KEY (object_type_id)
);


ALTER SEQUENCE public.object_types_type_id_seq OWNED BY public.object_types.object_type_id;

CREATE SEQUENCE public.units_unit_id_seq;

CREATE TABLE public.units (
  unit_id BIGINT NOT NULL DEFAULT nextval('public.units_unit_id_seq'),
  unit_name VARCHAR NOT NULL,
  base_factor NUMERIC NOT NULL,
  label VARCHAR DEFAULT '' NOT NULL,
  parent_unit_id BIGINT,
  CONSTRAINT units_pk PRIMARY KEY (unit_id)
);


ALTER SEQUENCE public.units_unit_id_seq OWNED BY public.units.unit_id;

CREATE SEQUENCE public.groups_group_id_seq;

CREATE TABLE public.groups (
  group_id BIGINT NOT NULL DEFAULT nextval('public.groups_group_id_seq'),
  group_name VARCHAR NOT NULL,
  CONSTRAINT groups_pk PRIMARY KEY (group_id)
);


ALTER SEQUENCE public.groups_group_id_seq OWNED BY public.groups.group_id;

CREATE SEQUENCE public.social_servicies_service_id_seq;

CREATE TABLE public.social_servicies (
  service_id BIGINT NOT NULL DEFAULT nextval('public.social_servicies_service_id_seq'),
  service_name VARCHAR NOT NULL,
  client_id VARCHAR NOT NULL,
  secret_key VARCHAR NOT NULL,
  CONSTRAINT social_servicies_pk PRIMARY KEY (service_id)
);


ALTER SEQUENCE public.social_servicies_service_id_seq OWNED BY public.social_servicies.service_id;

CREATE SEQUENCE public.users_user_id_seq;

CREATE TABLE public.users (
  user_id BIGINT NOT NULL DEFAULT nextval('public.users_user_id_seq'),
  email VARCHAR NOT NULL,
  encr_password VARCHAR NOT NULL,
  first_name VARCHAR,
  last_name VARCHAR,
  phone_number VARCHAR(12),
  is_two_factor_auth BOOLEAN DEFAULT false NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY (user_id)
);
COMMENT ON COLUMN public.users.email IS 'unique';

CREATE UNIQUE INDEX users_uk
  ON public.users (email);

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;

CREATE TABLE public.groups_members (
  group_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT groups_members_pk PRIMARY KEY (group_id, user_id)
);


CREATE SEQUENCE public.permissions_permission_id_seq;

CREATE TABLE public.permissions (
  permission_id BIGINT NOT NULL DEFAULT nextval('public.permissions_permission_id_seq'),
  actions_mask BIT(16) NOT NULL,
  admin_id BIGINT NOT NULL,
  user_id BIGINT,
  group_id BIGINT,
  perm_object BIGINT NOT NULL,
  CONSTRAINT permissions_pk PRIMARY KEY (permission_id)
);


ALTER SEQUENCE public.permissions_permission_id_seq OWNED BY public.permissions.permission_id;

CREATE SEQUENCE public.smart_homes_home_id_seq;

CREATE TABLE public.smart_homes (
  smart_home_id BIGINT NOT NULL DEFAULT nextval('public.smart_homes_home_id_seq'),
  name VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT smart_homes_id PRIMARY KEY (smart_home_id)
);


ALTER SEQUENCE public.smart_homes_home_id_seq OWNED BY public.smart_homes.smart_home_id;

CREATE SEQUENCE public.catalogs_catalog_id_seq;

CREATE TABLE public.catalogs (
  catalog_id BIGINT NOT NULL DEFAULT nextval('public.catalogs_catalog_id_seq'),
  catalog_name VARCHAR NOT NULL,
  parent_catalog_id BIGINT,
  smart_home_id BIGINT NOT NULL,
  CONSTRAINT catalogs_pk PRIMARY KEY (catalog_id)
);


ALTER SEQUENCE public.catalogs_catalog_id_seq OWNED BY public.catalogs.catalog_id;

CREATE SEQUENCE public.metric_specs_spec_id_seq;

CREATE TABLE public.metric_specs (
  spec_id BIGINT NOT NULL DEFAULT nextval('public.metric_specs_spec_id_seq'),
  spec_name VARCHAR NOT NULL,
  unit_id BIGINT,
  max_value NUMERIC,
  min_value NUMERIC,
  metric_type VARCHAR NOT NULL,
  assigned_to_object VARCHAR,
  catalog_id BIGINT NOT NULL,
  CONSTRAINT metric_specs_pk PRIMARY KEY (spec_id)
);


ALTER SEQUENCE public.metric_specs_spec_id_seq OWNED BY public.metric_specs.spec_id;

CREATE SEQUENCE public.policies_policy_id_seq;

CREATE TABLE public.policies (
  policy_id BIGINT NOT NULL DEFAULT nextval('public.policies_policy_id_seq'),
  name VARCHAR NOT NULL,
  status INTEGER NOT NULL,
  description VARCHAR,
  catalog_id BIGINT NOT NULL,
  CONSTRAINT policies_pk PRIMARY KEY (policy_id)
);


ALTER SEQUENCE public.policies_policy_id_seq OWNED BY public.policies.policy_id;

CREATE SEQUENCE public.rules_rule_id_seq;

CREATE TABLE public.rules (
  rule_id BIGINT NOT NULL DEFAULT nextval('public.rules_rule_id_seq'),
  name VARCHAR NOT NULL,
  policy_id BIGINT NOT NULL,
  CONSTRAINT rules_pk PRIMARY KEY (rule_id)
);


ALTER SEQUENCE public.rules_rule_id_seq OWNED BY public.rules.rule_id;

CREATE SEQUENCE public.actions_action_id_seq;

CREATE TABLE public.actions (
  action_id BIGINT NOT NULL DEFAULT nextval('public.actions_action_id_seq'),
  rule_id BIGINT NOT NULL,
  type BIT NOT NULL,
  action_class VARCHAR NOT NULL,
  action_order BIGINT NOT NULL,
  CONSTRAINT actions_pk PRIMARY KEY (action_id)
);


ALTER SEQUENCE public.actions_action_id_seq OWNED BY public.actions.action_id;

CREATE SEQUENCE public.action_params_param_id_seq;

CREATE TABLE public.action_params (
  param_id BIGINT NOT NULL DEFAULT nextval('public.action_params_param_id_seq'),
  name VARCHAR NOT NULL,
  value VARCHAR NOT NULL,
  action_id BIGINT NOT NULL,
  CONSTRAINT action_params_pk PRIMARY KEY (param_id)
);


ALTER SEQUENCE public.action_params_param_id_seq OWNED BY public.action_params.param_id;

CREATE SEQUENCE public.conditions_condition_id_seq;

CREATE TABLE public.conditions (
  condition_id BIGINT NOT NULL DEFAULT nextval('public.conditions_condition_id_seq'),
  rule_id BIGINT NOT NULL,
  type_id BIGINT NOT NULL,
  next_condition_id BIGINT,
  operator INTEGER,
  CONSTRAINT conditions_pk PRIMARY KEY (condition_id)
);


ALTER SEQUENCE public.conditions_condition_id_seq OWNED BY public.conditions.condition_id;

CREATE SEQUENCE public.condition_params_param_id_seq;

CREATE TABLE public.condition_params (
  param_id BIGINT NOT NULL DEFAULT nextval('public.condition_params_param_id_seq'),
  name VARCHAR NOT NULL,
  value VARCHAR NOT NULL,
  condition_id BIGINT NOT NULL,
  CONSTRAINT condition_params_pk PRIMARY KEY (param_id)
);


ALTER SEQUENCE public.condition_params_param_id_seq OWNED BY public.condition_params.param_id;

CREATE SEQUENCE public.alarm_specs_spec_id_seq;

CREATE TABLE public.alarm_specs (
  spec_id BIGINT NOT NULL DEFAULT nextval('public.alarm_specs_spec_id_seq'),
  spec_name VARCHAR NOT NULL,
  object_type VARCHAR NOT NULL,
  catalog_id BIGINT NOT NULL,
  CONSTRAINT alarm_specs_pk PRIMARY KEY (spec_id)
);


ALTER SEQUENCE public.alarm_specs_spec_id_seq OWNED BY public.alarm_specs.spec_id;

CREATE SEQUENCE public.objects_object_id_seq;

CREATE TABLE public.objects (
  smart_object_id BIGINT NOT NULL DEFAULT nextval('public.objects_object_id_seq'),
  name VARCHAR NOT NULL,
  description VARCHAR,
  object_type_id BIGINT NOT NULL,
  parent_smart_object_id BIGINT,
  catalog_id BIGINT NOT NULL,
  smart_home_id BIGINT NOT NULL,
  CONSTRAINT objects_pk PRIMARY KEY (smart_object_id)
);


ALTER SEQUENCE public.objects_object_id_seq OWNED BY public.objects.smart_object_id;

CREATE SEQUENCE public.object_params_param_id_seq;

CREATE TABLE public.object_params (
  param_id BIGINT NOT NULL DEFAULT nextval('public.object_params_param_id_seq'),
  type_id BIGINT NOT NULL,
  name VARCHAR NOT NULL,
  value VARCHAR NOT NULL,
  smart_object_id BIGINT NOT NULL,
  CONSTRAINT object_params_pk PRIMARY KEY (param_id)
);


ALTER SEQUENCE public.object_params_param_id_seq OWNED BY public.object_params.param_id;

CREATE SEQUENCE public.events_event_id_seq;

CREATE TABLE public.events (
  event_id BIGINT NOT NULL DEFAULT nextval('public.events_event_id_seq'),
  object_id BIGINT NOT NULL,
  subobject_id BIGINT,
  event_type VARCHAR NOT NULL,
  smart_home_id BIGINT NOT NULL,
  CONSTRAINT events_pk PRIMARY KEY (event_id)
);


ALTER SEQUENCE public.events_event_id_seq OWNED BY public.events.event_id;

CREATE SEQUENCE public.alarms_alarm_id_seq;

CREATE TABLE public.alarms (
  alarm_id BIGINT NOT NULL DEFAULT nextval('public.alarms_alarm_id_seq'),
  event_id BIGINT NOT NULL,
  object_id BIGINT NOT NULL,
  subobject_id BIGINT,
  spec_id BIGINT NOT NULL,
  parent_alarm_id BIGINT,
  cleared_user_id BIGINT NOT NULL,
  alarm_name VARCHAR NOT NULL,
  alarm_description VARCHAR DEFAULT '' NOT NULL,
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  severity INTEGER NOT NULL,
  severity_change_time TIMESTAMP NOT NULL,
  CONSTRAINT alarms_pk PRIMARY KEY (alarm_id)
);


ALTER SEQUENCE public.alarms_alarm_id_seq OWNED BY public.alarms.alarm_id;

CREATE SEQUENCE public.events_history_id_seq;

CREATE TABLE public.events_history (
  history_id BIGINT NOT NULL DEFAULT nextval('public.events_history_id_seq'),
  event_id BIGINT NOT NULL,
  read_date TIMESTAMP NOT NULL,
  event_description VARCHAR NOT NULL,
  severity INTEGER NOT NULL,
  event_parameters VARCHAR,
  CONSTRAINT events_history_pk PRIMARY KEY (history_id)
);


ALTER SEQUENCE public.events_history_id_seq OWNED BY public.events_history.history_id;

CREATE SEQUENCE public.metrics_metric_id_seq;

CREATE TABLE public.metrics (
  metric_id BIGINT NOT NULL DEFAULT nextval('public.metrics_metric_id_seq'),
  object_id BIGINT NOT NULL,
  subobject_id BIGINT,
  spec_id BIGINT NOT NULL,
  smart_home_id BIGINT NOT NULL,
  CONSTRAINT metrics_pk PRIMARY KEY (metric_id)
);


ALTER SEQUENCE public.metrics_metric_id_seq OWNED BY public.metrics.metric_id;

CREATE SEQUENCE public.notifications_notification_id_seq;

CREATE TABLE public.notifications (
  notification_id BIGINT NOT NULL DEFAULT nextval('public.notifications_notification_id_seq'),
  smart_home_id BIGINT NOT NULL,
  notification_name VARCHAR NOT NULL,
  notification_status INTEGER NOT NULL,
  time TIME NOT NULL,
  require_confirm BOOLEAN NOT NULL,
  channel INTEGER NOT NULL,
  alarm_id BIGINT,
  event_id BIGINT,
  metric_id BIGINT,
  CONSTRAINT notifications_pk PRIMARY KEY (notification_id)
);


ALTER SEQUENCE public.notifications_notification_id_seq OWNED BY public.notifications.notification_id;

CREATE SEQUENCE public.metrics_history_id_seq;

CREATE TABLE public.metrics_history (
  history_id BIGINT NOT NULL DEFAULT nextval('public.metrics_history_id_seq'),
  read_date TIMESTAMP NOT NULL,
  value NUMERIC NOT NULL,
  metric_id BIGINT NOT NULL,
  CONSTRAINT metrics_history_pk PRIMARY KEY (history_id)
);


ALTER SEQUENCE public.metrics_history_id_seq OWNED BY public.metrics_history.history_id;

CREATE SEQUENCE public.home_params_param_id_seq;

CREATE TABLE public.home_params (
  param_id BIGINT NOT NULL DEFAULT nextval('public.home_params_param_id_seq'),
  smart_home_id BIGINT NOT NULL,
  name VARCHAR NOT NULL,
  value VARCHAR NOT NULL,
  type_id BIGINT NOT NULL,
  CONSTRAINT home_params_pk PRIMARY KEY (param_id)
);


ALTER SEQUENCE public.home_params_param_id_seq OWNED BY public.home_params.param_id;

CREATE TABLE public.social_profiles (
  user_id BIGINT NOT NULL,
  service_id BIGINT NOT NULL,
  user_social_id VARCHAR NOT NULL,
  CONSTRAINT social_profiles_pk PRIMARY KEY (user_id, service_id)
);


CREATE UNIQUE INDEX sp_profile_uk
  ON public.social_profiles
  ( user_social_id, service_id );

ALTER TABLE public.conditions ADD CONSTRAINT condition_types_conditions_fk
FOREIGN KEY (type_id)
REFERENCES public.condition_types (type_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.object_params ADD CONSTRAINT object_types_object_params_fk
FOREIGN KEY (type_id)
REFERENCES public.data_types (type_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.home_params ADD CONSTRAINT data_types_home_params_fk
FOREIGN KEY (type_id)
REFERENCES public.data_types (type_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.objects ADD CONSTRAINT object_types_objects_fk
FOREIGN KEY (object_type_id)
REFERENCES public.object_types (object_type_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.units ADD CONSTRAINT units_units_fk
FOREIGN KEY (parent_unit_id)
REFERENCES public.units (unit_id)
ON DELETE SET NULL
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.metric_specs ADD CONSTRAINT units_metric_specs_fk
FOREIGN KEY (unit_id)
REFERENCES public.units (unit_id)
ON DELETE SET NULL
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.groups_members ADD CONSTRAINT groups_gr_members_fk
FOREIGN KEY (group_id)
REFERENCES public.groups (group_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.permissions ADD CONSTRAINT groups_permissions_fk
FOREIGN KEY (group_id)
REFERENCES public.groups (group_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.social_profiles ADD CONSTRAINT servicies_profiles_fk
FOREIGN KEY (service_id)
REFERENCES public.social_servicies (service_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.social_profiles ADD CONSTRAINT users_profiles_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.smart_homes ADD CONSTRAINT users_smart_home_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.permissions ADD CONSTRAINT users_perms_admin_fk
FOREIGN KEY (admin_id)
REFERENCES public.users (user_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.permissions ADD CONSTRAINT users_perms_user_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.groups_members ADD CONSTRAINT users_gp_members_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.home_params ADD CONSTRAINT smart_homes_home_params_fk
FOREIGN KEY (smart_home_id)
REFERENCES public.smart_homes (smart_home_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.metrics ADD CONSTRAINT smart_homes_metrics_fk
FOREIGN KEY (smart_home_id)
REFERENCES public.smart_homes (smart_home_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.notifications ADD CONSTRAINT smart_homes_notifications_fk
FOREIGN KEY (smart_home_id)
REFERENCES public.smart_homes (smart_home_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.events ADD CONSTRAINT smart_homes_events_fk
FOREIGN KEY (smart_home_id)
REFERENCES public.smart_homes (smart_home_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.objects ADD CONSTRAINT smart_homes_objects_fk
FOREIGN KEY (smart_home_id)
REFERENCES public.smart_homes (smart_home_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.catalogs ADD CONSTRAINT smart_homes_catalogs_fk
FOREIGN KEY (smart_home_id)
REFERENCES public.smart_homes (smart_home_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.alarm_specs ADD CONSTRAINT catalogs_alarm_specs_fk
FOREIGN KEY (catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.catalogs ADD CONSTRAINT catalogs_catalogs_fk
FOREIGN KEY (parent_catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.policies ADD CONSTRAINT catalogs_policies_fk
FOREIGN KEY (catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.metric_specs ADD CONSTRAINT catalogs_metric_specs_fk
FOREIGN KEY (catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.objects ADD CONSTRAINT catalogs_objects_fk
FOREIGN KEY (catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.metrics ADD CONSTRAINT metric_specs_metrics_fk
FOREIGN KEY (spec_id)
REFERENCES public.metric_specs (spec_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.rules ADD CONSTRAINT policies_rules_fk
FOREIGN KEY (policy_id)
REFERENCES public.policies (policy_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.conditions ADD CONSTRAINT rules_conditions_fk
FOREIGN KEY (rule_id)
REFERENCES public.rules (rule_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.actions ADD CONSTRAINT rules_actions_fk
FOREIGN KEY (rule_id)
REFERENCES public.rules (rule_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.action_params ADD CONSTRAINT actions_action_params_fk
FOREIGN KEY (action_id)
REFERENCES public.actions (action_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.condition_params ADD CONSTRAINT conditions_condition_params_fk
FOREIGN KEY (condition_id)
REFERENCES public.conditions (condition_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.conditions ADD CONSTRAINT conditions_conditions_fk
FOREIGN KEY (next_condition_id)
REFERENCES public.conditions (condition_id)
ON DELETE SET NULL
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.alarms ADD CONSTRAINT alarm_specs_alarms_fk
FOREIGN KEY (spec_id)
REFERENCES public.alarm_specs (spec_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.object_params ADD CONSTRAINT objects_object_params_fk
FOREIGN KEY (smart_object_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.objects ADD CONSTRAINT objects_objects_fk
FOREIGN KEY (parent_smart_object_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.metrics ADD CONSTRAINT objects_metrics_fk
FOREIGN KEY (object_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.metrics ADD CONSTRAINT objects_metrics_fk1
FOREIGN KEY (subobject_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.events ADD CONSTRAINT objects_events_fk
FOREIGN KEY (object_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.events ADD CONSTRAINT objects_events_fk1
FOREIGN KEY (subobject_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.alarms ADD CONSTRAINT objects_alarms_fk
FOREIGN KEY (object_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.alarms ADD CONSTRAINT objects_alarms_fk1
FOREIGN KEY (subobject_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.events_history ADD CONSTRAINT events_events_history_fk
FOREIGN KEY (event_id)
REFERENCES public.events (event_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.alarms ADD CONSTRAINT events_alarms_fk
FOREIGN KEY (event_id)
REFERENCES public.events (event_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.notifications ADD CONSTRAINT events_notifications_fk
FOREIGN KEY (event_id)
REFERENCES public.events (event_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.alarms ADD CONSTRAINT alarms_alarms_fk
FOREIGN KEY (parent_alarm_id)
REFERENCES public.alarms (alarm_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.notifications ADD CONSTRAINT alarms_notifications_fk
FOREIGN KEY (alarm_id)
REFERENCES public.alarms (alarm_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.metrics_history ADD CONSTRAINT metrics_m_history_fk
FOREIGN KEY (metric_id)
REFERENCES public.metrics (metric_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.notifications ADD CONSTRAINT metrics_notifications_fk
FOREIGN KEY (metric_id)
REFERENCES public.metrics (metric_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;