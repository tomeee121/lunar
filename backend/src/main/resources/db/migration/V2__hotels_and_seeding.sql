CREATE TABLE hotel_rate (
                            id BIGSERIAL PRIMARY KEY,
                            destination VARCHAR(50) NOT NULL,
                            place VARCHAR(20) NOT NULL,
                            room VARCHAR(50) NOT NULL,
                            price_per_day DOUBLE PRECISION NOT NULL
);

INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id) VALUES ('Dragon','Falcon 9',549045,22800,(SELECT id FROM fuel_type WHERE name='LOX/RP-1'));
INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id) VALUES ('Big Dragon','Falcon Heavy',1420788,63800,(SELECT id FROM fuel_type WHERE name='LOX/RP-1'));
INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id) VALUES ('Feather','Falcon 9',1104534,45000,(SELECT id FROM fuel_type WHERE name='NTO/RP-1'));
INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id) VALUES ('New Moon','Atlas IV',612487,25465,(SELECT id FROM fuel_type WHERE name='LOX/LH2'));
INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id) VALUES ('Full Moon','Titan II',1242447,57890,(SELECT id FROM fuel_type WHERE name='LOX/RP-1'));

INSERT INTO hotel_rate (destination, place, room, price_per_day) VALUES ('moon','CYCLER','standard',300);
INSERT INTO hotel_rate (destination, place, room, price_per_day) VALUES ('moon','CYCLER','panoramic',800);
INSERT INTO hotel_rate (destination, place, room, price_per_day) VALUES ('moon','CYCLER','penthouse',2000);

INSERT INTO hotel_rate (destination, place, room, price_per_day) VALUES ('moon','SURFACE','armstrong',300);
INSERT INTO hotel_rate (destination, place, room, price_per_day) VALUES ('moon','SURFACE','conrad',800);
INSERT INTO hotel_rate (destination, place, room, price_per_day) VALUES ('moon','SURFACE','aldrin',2000);

