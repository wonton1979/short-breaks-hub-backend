CREATE TABLE public.itinerary_transport_tip (
                                                    id BIGSERIAL PRIMARY KEY,

                                                    itinerary_id BIGINT NOT NULL UNIQUE,

                                                    airport_to_city JSONB NOT NULL DEFAULT '[]'::jsonb,
                                                    getting_around JSONB NOT NULL DEFAULT '[]'::jsonb,
                                                    day_trips JSONB NOT NULL DEFAULT '[]'::jsonb,
                                                    practical JSONB NOT NULL DEFAULT '[]'::jsonb,

                                                    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                                                    CONSTRAINT fk_transport_itinerary
                                                        FOREIGN KEY (itinerary_id)
                                                            REFERENCES itineraries(id)
                                                            ON DELETE CASCADE
);
