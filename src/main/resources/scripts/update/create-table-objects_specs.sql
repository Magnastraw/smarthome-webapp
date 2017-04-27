ALTER TABLE public.objects
  ADD COLUMN external_key BIGINT;


CREATE TABLE public.objects_specs (
  smart_object_id BIGINT NOT NULL,
  spec_id BIGINT NOT NULL,
  CONSTRAINT objects_specs_pk PRIMARY KEY (smart_object_id)
);

ALTER TABLE public.objects_specs ADD CONSTRAINT objects_objects_specs_fk
FOREIGN KEY (smart_object_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.objects_specs ADD CONSTRAINT metric_specs_objects_specs_fk
FOREIGN KEY (spec_id)
REFERENCES public.metric_specs (spec_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;
