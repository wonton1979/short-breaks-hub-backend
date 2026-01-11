ALTER TABLE public.itinerary_transport_tip
    ADD COLUMN day_moves JSONB NOT NULL DEFAULT '[]'::jsonb;