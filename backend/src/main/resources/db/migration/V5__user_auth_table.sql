-- users (do logging in/JWT)
CREATE TABLE IF NOT EXISTS app_user (
    id           BIGINT PRIMARY KEY,
    email        VARCHAR(160) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    role         VARCHAR(30)  NOT NULL DEFAULT 'USER',
    created_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE booking ALTER COLUMN flight_id TYPE BIGINT;

ALTER TABLE booking ADD COLUMN IF NOT EXISTS package_code VARCHAR(20);
ALTER TABLE booking ADD COLUMN IF NOT EXISTS user_id BIGINT;

ALTER TABLE booking ADD CONSTRAINT fk_booking_flight FOREIGN KEY (flight_id) REFERENCES flight(id) ON DELETE RESTRICT;
ALTER TABLE booking ADD CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE RESTRICT;

ALTER TABLE flight ALTER COLUMN id TYPE BIGINT;

-- only one flight per day
CREATE UNIQUE INDEX IF NOT EXISTS ux_flight_ship_date
    ON flight (spaceship_id, flight_date);
