
CREATE SEQUENCE public.catalogs_catalog_id_seq;

CREATE TABLE public.catalogs (
                catalog_id BIGINT NOT NULL DEFAULT nextval('public.catalogs_catalog_id_seq'),
                catalog_name VARCHAR NOT NULL,
                parent_catalog_id BIGINT,
                CONSTRAINT catalogs_pk PRIMARY KEY (catalog_id)
);


ALTER SEQUENCE public.catalogs_catalog_id_seq OWNED BY public.catalogs.catalog_id;

CREATE SEQUENCE public.policies_policy_id_seq;

CREATE TABLE public.policies (
                policy_id BIGINT NOT NULL DEFAULT nextval('public.policies_policy_id_seq'),
                name VARCHAR NOT NULL,
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

CREATE SEQUENCE public.alarm_spec_id_seq;

CREATE TABLE public.alarm_spec (
                spec_id BIGINT NOT NULL DEFAULT nextval('public.alarm_spec_id_seq'),
                spec_name VARCHAR NOT NULL,
                object_type VARCHAR NOT NULL,
                catalog_id BIGINT NOT NULL,
                CONSTRAINT alarm_spec_pk PRIMARY KEY (spec_id)
);


ALTER SEQUENCE public.alarm_spec_id_seq OWNED BY public.alarm_spec.spec_id;

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

CREATE SEQUENCE public.metric_spec_id_seq;

CREATE TABLE public.metric_spec (
                spec_id BIGINT NOT NULL DEFAULT nextval('public.metric_spec_id_seq'),
                spec_name VARCHAR NOT NULL,
                unit_id BIGINT NOT NULL,
                max_value NUMERIC,
                min_value NUMERIC,
                metric_type VARCHAR NOT NULL,
                assigned_to_object VARCHAR,
                CONSTRAINT metric_spec_pk PRIMARY KEY (spec_id)
);


ALTER SEQUENCE public.metric_spec_id_seq OWNED BY public.metric_spec.spec_id;

CREATE SEQUENCE public.abstract_objects_object_id_seq;

CREATE TABLE public.abstract_objects (
                object_id BIGINT NOT NULL DEFAULT nextval('public.abstract_objects_object_id_seq'),
                name VARCHAR NOT NULL,
                description VARCHAR,
                CONSTRAINT abstract_objects_pk PRIMARY KEY (object_id)
);


ALTER SEQUENCE public.abstract_objects_object_id_seq OWNED BY public.abstract_objects.object_id;

CREATE SEQUENCE public.permissions_permission_id_seq;

CREATE TABLE public.permissions (
                permission_id BIGINT NOT NULL DEFAULT nextval('public.permissions_permission_id_seq'),
                object_id BIGINT NOT NULL,
                CONSTRAINT permissions_pk PRIMARY KEY (permission_id)
);


ALTER SEQUENCE public.permissions_permission_id_seq OWNED BY public.permissions.permission_id;

CREATE INDEX perms_object_idx
 ON public.permissions
 ( object_id );

CREATE SEQUENCE public.groups_group_id_seq;

CREATE TABLE public.groups (
                group_id BIGINT NOT NULL DEFAULT nextval('public.groups_group_id_seq'),
                group_name VARCHAR NOT NULL,
                CONSTRAINT groups_pk PRIMARY KEY (group_id)
);


ALTER SEQUENCE public.groups_group_id_seq OWNED BY public.groups.group_id;

CREATE TABLE public.groups_permissions (
                permission_id BIGINT NOT NULL,
                action CHAR(1) DEFAULT 'r' NOT NULL,
                group_id BIGINT NOT NULL,
                CONSTRAINT groups_permissions_pk PRIMARY KEY (permission_id, action)
);


CREATE INDEX gp_group_idx
 ON public.groups_permissions
 ( group_id );

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
                first_name VARCHAR NOT NULL,
                last_name VARCHAR NOT NULL,
                phone_number VARCHAR(12),
                email VARCHAR NOT NULL,
                encr_password VARCHAR NOT NULL,
                is_two_factor_auth BOOLEAN DEFAULT false NOT NULL,
                CONSTRAINT users_pk PRIMARY KEY (user_id)
);
COMMENT ON COLUMN public.users.email IS 'unique';


ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;

CREATE UNIQUE INDEX users_email_uk
 ON public.users
 ( email );

CREATE SEQUENCE public.events_event_id_seq;

CREATE TABLE public.events (
                event_id BIGINT NOT NULL DEFAULT nextval('public.events_event_id_seq'),
                object_id BIGINT NOT NULL,
                event_type VARCHAR NOT NULL,
                subobject_id BIGINT,
                user_id BIGINT NOT NULL,
                CONSTRAINT events_pk PRIMARY KEY (event_id, object_id, event_type)
);


ALTER SEQUENCE public.events_event_id_seq OWNED BY public.events.event_id;

CREATE SEQUENCE public.alarms_alarm_id_seq;

CREATE TABLE public.alarms (
                alarm_id BIGINT NOT NULL DEFAULT nextval('public.alarms_alarm_id_seq'),
                event_id BIGINT NOT NULL,
                object_id BIGINT NOT NULL,
                event_type VARCHAR NOT NULL,
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
                object_id BIGINT NOT NULL,
                event_type VARCHAR NOT NULL,
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
                user_id BIGINT NOT NULL,
                spec_id BIGINT NOT NULL,
                CONSTRAINT metrics_pk PRIMARY KEY (metric_id)
);


ALTER SEQUENCE public.metrics_metric_id_seq OWNED BY public.metrics.metric_id;

CREATE SEQUENCE public.notifications_notification_id_seq;

CREATE TABLE public.notifications (
                notification_id BIGINT NOT NULL DEFAULT nextval('public.notifications_notification_id_seq'),
                notification_name VARCHAR NOT NULL,
                notification_status INTEGER NOT NULL,
                time TIME NOT NULL,
                confirm INTEGER NOT NULL,
                user_id BIGINT NOT NULL,
                alarm_id BIGINT,
                event_id BIGINT,
                object_id BIGINT,
                event_type VARCHAR,
                metric_id BIGINT NOT NULL,
                CONSTRAINT notifications_pk PRIMARY KEY (notification_id)
);
COMMENT ON COLUMN public.notifications.confirm IS '0 - doesn''t require confirming
1 - require confirming';


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

CREATE TABLE public.groups_members (
                group_id BIGINT NOT NULL,
                user_id BIGINT NOT NULL,
                is_admin BOOLEAN DEFAULT false NOT NULL,
                CONSTRAINT groups_members_pk PRIMARY KEY (group_id, user_id)
);


CREATE INDEX gm_group_idx
 ON public.groups_members
 ( group_id );

CREATE TABLE public.users_permissions (
                permission_id BIGINT NOT NULL,
                action CHAR(1) DEFAULT 'r' NOT NULL,
                admin_id BIGINT NOT NULL,
                user_id BIGINT NOT NULL,
                CONSTRAINT users_permissions_pk PRIMARY KEY (permission_id, action)
);


CREATE INDEX up_user_idx
 ON public.users_permissions
 ( user_id );

CREATE INDEX up_admin_idx
 ON public.users_permissions
 ( admin_id );

CREATE TABLE public.social_profiles (
                user_id BIGINT NOT NULL,
                service_id BIGINT NOT NULL,
                user_social_id VARCHAR NOT NULL,
                CONSTRAINT social_profiles_pk PRIMARY KEY (user_id, service_id)
);


CREATE UNIQUE INDEX sp_profile_uk
 ON public.social_profiles
 ( user_social_id, service_id );

ALTER TABLE public.alarm_spec ADD CONSTRAINT catalogs_alarm_spec_fk
FOREIGN KEY (catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.catalogs ADD CONSTRAINT catalogs_catalogs_fk
FOREIGN KEY (parent_catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.policies ADD CONSTRAINT catalogs_policies_fk
FOREIGN KEY (catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.rules ADD CONSTRAINT policies_rules_fk
FOREIGN KEY (policy_id)
REFERENCES public.policies (policy_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.alarms ADD CONSTRAINT alarm_spec_alarms_fk
FOREIGN KEY (spec_id)
REFERENCES public.alarm_spec (spec_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.units ADD CONSTRAINT units_units_fk
FOREIGN KEY (parent_unit_id)
REFERENCES public.units (unit_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.metric_spec ADD CONSTRAINT units_metric_spec_fk
FOREIGN KEY (unit_id)
REFERENCES public.units (unit_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.metrics ADD CONSTRAINT metric_spec_metrics_fk
FOREIGN KEY (spec_id)
REFERENCES public.metric_spec (spec_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.permissions ADD CONSTRAINT objects_perms_fk
FOREIGN KEY (object_id)
REFERENCES public.abstract_objects (object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.users_permissions ADD CONSTRAINT perms_u_perms_fk
FOREIGN KEY (permission_id)
REFERENCES public.permissions (permission_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.groups_permissions ADD CONSTRAINT perms_gr_perms_fk
FOREIGN KEY (permission_id)
REFERENCES public.permissions (permission_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.groups_permissions ADD CONSTRAINT groups_gr_perms_fk
FOREIGN KEY (group_id)
REFERENCES public.groups (group_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.groups_members ADD CONSTRAINT groups_gr_members_fk
FOREIGN KEY (group_id)
REFERENCES public.groups (group_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.social_profiles ADD CONSTRAINT servicies_profiles_fk
FOREIGN KEY (service_id)
REFERENCES public.social_servicies (service_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.social_profiles ADD CONSTRAINT users_profiles_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.users_permissions ADD CONSTRAINT users_u_perms_admin_fk
FOREIGN KEY (admin_id)
REFERENCES public.users (user_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.users_permissions ADD CONSTRAINT users_u_perms_user_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.groups_members ADD CONSTRAINT user_gp_member_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.metrics ADD CONSTRAINT users_metrics_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.events ADD CONSTRAINT users_events_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.notifications ADD CONSTRAINT users_notifications_fk
FOREIGN KEY (user_id)
REFERENCES public.users (user_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.events_history ADD CONSTRAINT events_events_history_fk
FOREIGN KEY (object_id, event_id, event_type)
REFERENCES public.events (object_id, event_id, event_type)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.alarms ADD CONSTRAINT events_alarms_fk
FOREIGN KEY (event_id, object_id, event_type)
REFERENCES public.events (event_id, object_id, event_type)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.notifications ADD CONSTRAINT events_notifications_fk
FOREIGN KEY (object_id, event_id, event_type)
REFERENCES public.events (object_id, event_id, event_type)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.alarms ADD CONSTRAINT alarms_alarms_fk
FOREIGN KEY (parent_alarm_id)
REFERENCES public.alarms (alarm_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.notifications ADD CONSTRAINT alarms_notifications_fk
FOREIGN KEY (alarm_id)
REFERENCES public.alarms (alarm_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.metrics_history ADD CONSTRAINT metrics_m_history_fk
FOREIGN KEY (metric_id)
REFERENCES public.metrics (metric_id)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;

ALTER TABLE public.notifications ADD CONSTRAINT metrics_notifications_fk
FOREIGN KEY (metric_id)
REFERENCES public.metrics (metric_id)
ON DELETE RESTRICT
ON UPDATE CASCADE
NOT DEFERRABLE;
