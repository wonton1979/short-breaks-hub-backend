CREATE TABLE public.itinerary_planning_snapshot (
                                             id BIGSERIAL PRIMARY KEY,

                                             itinerary_id BIGINT NOT NULL UNIQUE,
                                             city TEXT NOT NULL,

                                             best_time_months TEXT NOT NULL,
                                             best_time_note  TEXT NOT NULL,

                                             worst_time_months TEXT NOT NULL,
                                             worst_time_note  TEXT NOT NULL,

                                             tips JSONB NOT NULL DEFAULT '[]'::jsonb,
                                             with_kids JSONB NOT NULL DEFAULT '[]'::jsonb,

                                             created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                                             updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                                             CONSTRAINT fk_snapshot_itinerary
                                                 FOREIGN KEY (itinerary_id)
                                                     REFERENCES itineraries(id)
                                                     ON DELETE CASCADE
);

CREATE INDEX idx_snapshot_itinerary_id ON itinerary_planning_snapshot(itinerary_id);
