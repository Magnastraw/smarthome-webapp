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