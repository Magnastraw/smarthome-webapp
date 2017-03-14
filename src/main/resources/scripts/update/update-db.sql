-- update social services table
ALTER TABLE public.social_servicies
  ADD COLUMN service_type BIGINT NOT NULL,
  ADD CONSTRAINT service_uk UNIQUE (service_type);

-- create assignments table
CREATE TABLE public.assignments (
  policy_id BIGINT NOT NULL,
  object_id BIGINT NOT NULL,
  name VARCHAR NOT NULL DEFAULT '',
  catalog_id BIGINT NOT NULL,
  CONSTRAINT assignments_pk PRIMARY KEY (policy_id, object_id)
);
ALTER TABLE public.assignments ADD CONSTRAINT assignments_policies_fk
FOREIGN KEY (policy_id)
REFERENCES public.policies (policy_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE public.assignments ADD CONSTRAINT assignments_objects_fk
FOREIGN KEY (object_id)
REFERENCES public.objects (smart_object_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE public.assignments ADD CONSTRAINT assignments_catalogs_fk
FOREIGN KEY (catalog_id)
REFERENCES public.catalogs (catalog_id)
ON DELETE CASCADE
ON UPDATE CASCADE
DEFERRABLE INITIALLY IMMEDIATE;