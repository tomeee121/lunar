CREATE TABLE fuel_type (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           base_unit VARCHAR(20) NOT NULL,
                           carry_capacity NUMERIC NOT NULL,
                           price NUMERIC NOT NULL
);

CREATE TABLE spaceship (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           booster VARCHAR(100) NOT NULL,
                           weight NUMERIC NOT NULL,
                           maximum_capacity NUMERIC NOT NULL,
                           fuel_type_id INT NOT NULL REFERENCES fuel_type(id)
);

CREATE TABLE flight (
                        id SERIAL PRIMARY KEY,
                        flight_date DATE NOT NULL,
                        booked_capacity NUMERIC DEFAULT 0,
                        spaceship_id INT NOT NULL REFERENCES spaceship(id)
);

CREATE TABLE booking (
                         id SERIAL PRIMARY KEY,
                         passenger_name VARCHAR(100) NOT NULL,
                         passengers_count INT NOT NULL,
                         created_at TIMESTAMP DEFAULT now(),
                         flight_id INT NOT NULL REFERENCES flight(id)
);

INSERT INTO fuel_type (name, base_unit, carry_capacity, price)
VALUES
    ('LOX/RP-1', 'kg', 0.5, 0.202),
    ('LOX/LH2', 'kg', 0.4, 0.25),
    ('NTO/RP-1', 'kg', 0.6, 0.18);

INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id)
VALUES
    ('Dragon', 'Falcon 9', 549045, 22800, 1),
    ('Big Dragon', 'Falcon Heavy', 1420788, 63800, 1),
    ('Feather', 'Falcon 9', 1104534, 45000, 3),
    ('New Moon', 'Atlas IV', 612487, 25465, 2),
    ('Full Moon', 'Titan II', 1242447, 57890, 1);