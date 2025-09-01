-- more spaceships Earth-LEO
INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id) VALUES
('Starliner',        'Atlas V',      420000, 13000, (SELECT id FROM fuel_type WHERE name='LOX/RP-1')),
('Oryx',             'Falcon 9',     600000, 26000, (SELECT id FROM fuel_type WHERE name='LOX/RP-1')),
('Kestrel',          'Falcon Heavy', 1500000, 70000, (SELECT id FROM fuel_type WHERE name='LOX/RP-1')),
('Valkyrie',         'Falcon Heavy', 1450000, 62000, (SELECT id FROM fuel_type WHERE name='NTO/RP-1')),
('Aurora',           'Falcon 9',      900000, 35000, (SELECT id FROM fuel_type WHERE name='NTO/RP-1')),
('Skylark',          'Falcon 9',      720000, 30000, (SELECT id FROM fuel_type WHERE name='LOX/RP-1'));

-- more spaceships LLO-Moon
INSERT INTO spaceship (name, booster, weight, maximum_capacity, fuel_type_id) VALUES
('Luna Hopper',      'Atlas IV',      620000, 26000, (SELECT id FROM fuel_type WHERE name='LOX/LH2')),
('Selene',           'Titan II',     1250000, 58000, (SELECT id FROM fuel_type WHERE name='LOX/RP-1')),
('Artemis Shuttle',  'Atlas IV',      640000, 27000, (SELECT id FROM fuel_type WHERE name='LOX/LH2')),
('Moonrider',        'Titan II',     1260000, 59000, (SELECT id FROM fuel_type WHERE name='LOX/RP-1')),
('Cycler Companion', 'Atlas IV',      655000, 26500, (SELECT id FROM fuel_type WHERE name='LOX/LH2')),
('Titan Rover',      'Titan II',     1235000, 57500, (SELECT id FROM fuel_type WHERE name='LOX/RP-1'));
