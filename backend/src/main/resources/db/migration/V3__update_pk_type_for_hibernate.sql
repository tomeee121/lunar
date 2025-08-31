ALTER TABLE flight ALTER COLUMN id TYPE BIGINT;

ALTER TABLE fuel_type ALTER COLUMN id TYPE BIGINT;
ALTER TABLE spaceship ALTER COLUMN id TYPE BIGINT;
ALTER TABLE booking ALTER COLUMN id TYPE BIGINT;
ALTER TABLE hotel_rate ALTER COLUMN id TYPE BIGINT;

ALTER TABLE hotel_rate
ALTER COLUMN price_per_day TYPE numeric(12,2)
USING price_per_day::numeric(12,2);

ALTER TABLE spaceship
    ALTER COLUMN fuel_type_id TYPE BIGINT
  USING fuel_type_id::bigint;

ALTER TABLE flight
    ALTER COLUMN spaceship_id TYPE BIGINT
  USING spaceship_id::bigint;
