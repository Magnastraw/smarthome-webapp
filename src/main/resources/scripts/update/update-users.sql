ALTER TABLE public.users ADD COLUMN prefer_channel integer;
ALTER TABLE public.users ALTER COLUMN prefer_channel SET NOT NULL;
ALTER TABLE public.users ALTER COLUMN prefer_channel SET DEFAULT 1;
