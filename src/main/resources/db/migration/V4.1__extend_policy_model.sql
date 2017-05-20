CREATE SEQUENCE public.condition_classes_class_id_seq;
CREATE TABLE public.condition_classes (
  class_id BIGINT NOT NULL DEFAULT nextval('public.condition_classes_class_id_seq'),
  condition_id BIGINT NOT NULL,
  context VARCHAR NOT NULL,
  name VARCHAR NOT NULL,
  CONSTRAINT condition_classes_pk PRIMARY KEY (class_id)
);
ALTER SEQUENCE public.condition_classes_class_id_seq OWNED BY public.condition_classes.class_id;

CREATE SEQUENCE public.action_classes_class_id_seq;
CREATE TABLE public.action_classes (
  class_id BIGINT NOT NULL DEFAULT nextval('public.action_classes_class_id_seq'),
  action_id BIGINT NOT NULL,
  context VARCHAR NOT NULL,
  name VARCHAR NOT NULL,
  CONSTRAINT action_classes_pk PRIMARY KEY (class_id)
);
ALTER SEQUENCE public.action_classes_class_id_seq OWNED BY public.action_classes.class_id;

ALTER TABLE public.condition_classes ADD CONSTRAINT condition_classes_conditions_fk
FOREIGN KEY (condition_id)
REFERENCES public.conditions (node_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.action_classes ADD CONSTRAINT action_classes_actions_fk
FOREIGN KEY (action_id)
REFERENCES public.actions (action_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.conditions
DROP COLUMN type_id;

ALTER TABLE public.actions
DROP COLUMN action_class;

DROP TABLE public.condition_types;