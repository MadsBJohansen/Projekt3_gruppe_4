USE projekt3_gruppe_4_db;

INSERT INTO role (title) VALUES
('ADMIN'), ('DATA_REGISTRATION'), ('DAMAGE_AND_REPAIR'), ('BUSINESS_DEVELOPER');

INSERT INTO car_status (status_code) VALUES
('AVAILABLE'), ('RENTED'), ('RESERVED'), ('RETURNED'),
('DAMAGED'), ('UNDER_REPAIR'), ('READY_FOR_PICKUP'), ('SOLD');

INSERT INTO rental_period (months) VALUES
(3), (6), (12);

INSERT INTO zip_code (zip_code, city) VALUES
('2200', 'København N'), ('2600', 'Glostrup'), ('2605', 'Brøndby'),
('2300', 'København S'), ('5000', 'Odense C'), ('8000', 'Aarhus C'),
('9000', 'Aalborg');

INSERT INTO car_brand (name) VALUES
('Peugeot'), ('DS'), ('TOYOTA'), ('BMW'), ('Ford'), ('KIA'), ('VOLVO'),
('RENAULT'), ('Audi'), ('Betnley'), ('Bugatti'), ('Chevrolet'), ('Citroen'),
('Ferrari'), ('Volkswagen'), ('Skoda');


INSERT INTO car_model (name, model_year, car_brand_id) VALUES
('208', 2025, 1), ('DS 4', 2025, 2),
('Toyota bZ4X', 2023, 3), ('Skoda Elroq', 2025, 16)
;

-- password_hash skal være et rigtigt hash (fx BCrypt af 'demo'), ikke klartekst
INSERT INTO employee (first_name, last_name, username, password_hash, role_id) VALUES
('Mads', 'Johansen', 'mads', '', 1),
('Gustav', 'Clement', 'Gustav', '', 1),
('Al', 'Agami', 'Al', '', 1),
('Rasmus', 'Gøtze', 'rasmus', '',1);


INSERT INTO customer (first_name, last_name, email, phone_number, street_address, zip_code_id) VALUES
('Bjørn', 'Programmering', 'bjoern@example.com', '+4511223344', 'Guldbergsgade 29', 1),
('Oskar','It&Forret', 'oskar@example.com', '+4522334455', 'Guldbergsgade 29', 1),
('Jaron', 'Systemudvikling','jaron@example.com', '+4533445566','Guldbergsgade 29', 1);

INSERT INTO location (name, street_address, zip_code_id) VALUES
('Bilabonnement Glostrup', 'Hovedvejen 1', 2);

INSERT INTO car (vin, car_model_id, trim_level, mileage, base_price, co2_emission, license_plate, car_status_id) VALUES
('VF3ABCDEF12345678', 1, 'Allure', 1200, 189900.00, 112.0, 'AB12345', 2),
('VR1ABCDEF87654321', 2, 'Rivoli', 0, 299900.00, 125.5, 'CD67890', 1);

INSERT INTO rental_agreement (customer_id, car_id, location_id, employee_id, rental_period_id, start_date, monthly_price) VALUES
(1, 1, 1, 1, 2, '2026-11-01', 3999.00);

INSERT INTO status_change (car_id, car_status_id) VALUES
(1, 2);