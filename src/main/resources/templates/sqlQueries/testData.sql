-- ============================================================
-- TESTDATA
-- ============================================================

INSERT INTO role (title)
VALUES ('ADMIN'),
       ('DATA_REGISTRATION'),
       ('DAMAGE_AND_REPAIR'),
       ('BUSINESS_DEVELOPER');

INSERT INTO car_status (status_code)
VALUES ('AVAILABLE'),
       ('RENTED'),
       ('RESERVED'),
       ('RETURNED'),
       ('DAMAGED'),
       ('UNDER_REPAIR'),
       ('READY_FOR_PICKUP'),
       ('SOLD');

INSERT INTO rental_period (months)
VALUES (3),
       (6),
       (12),
       (5); -- 5 = Limited (150 dage)

INSERT INTO zip_code (zip_code, city)
VALUES ('2200', 'København N'),
       ('2600', 'Glostrup'),
       ('2605', 'Brøndby'),
       ('2300', 'København S'),
       ('5000', 'Odense C'),
       ('8000', 'Aarhus C'),
       ('9000', 'Aalborg');

INSERT INTO car_brand (name)
VALUES ('Peugeot'),
       ('DS'),
       ('Toyota'),
       ('BMW'),
       ('Ford'),
       ('Kia'),
       ('Volvo'),
       ('Renault'),
       ('Audi'),
       ('Bentley'),
       ('Bugatti'),
       ('Chevrolet'),
       ('Citroën'),
       ('Ferrari'),
       ('Volkswagen'),
       ('Skoda');

INSERT INTO car_model (name, model_year, car_brand_id)
VALUES ('208', 2025, 1),
       ('DS 4', 2025, 2),
       ('bZ4X', 2023, 3),
       ('Elroq', 2025, 16);

-- password = 'demo' (BCrypt, virker med Spring Securitys BCryptPasswordEncoder)
INSERT INTO employee (first_name, last_name, username, password_hash, role_id)
VALUES ('Mads', 'Johansen', 'mads', '$2a$10$Y/lmprXp2F9azH1kYbZ5sOB/nXUS2b6vTAgZX8QIjD1UXkPlcrCRK', 1),
       ('Gustav', 'Clement', 'gustav', '$2a$10$Y/lmprXp2F9azH1kYbZ5sOB/nXUS2b6vTAgZX8QIjD1UXkPlcrCRK', 1),
       ('Al', 'Agami', 'al', '$2a$10$Y/lmprXp2F9azH1kYbZ5sOB/nXUS2b6vTAgZX8QIjD1UXkPlcrCRK', 1),
       ('Rasmus', 'Gøtze', 'rasmus', '$2a$10$Y/lmprXp2F9azH1kYbZ5sOB/nXUS2b6vTAgZX8QIjD1UXkPlcrCRK', 1);

INSERT INTO customer (first_name, last_name, email, phone_number, street_address, zip_code_id)
VALUES ('Bjørn', 'Programmering', 'bjoern@example.com', '+4511223344', 'Guldbergsgade 29', 1),
       ('Oskar', 'It&Forret', 'oskar@example.com', '+4522334455', 'Guldbergsgade 29', 1),
       ('Jaron', 'Systemudvikling', 'jaron@example.com', '+4533445566', 'Guldbergsgade 29', 1);

INSERT INTO location (name, street_address, zip_code_id)
VALUES ('Bilabonnement Glostrup', 'Hovedvejen 1', 2);

INSERT INTO car (vin, car_model_id, trim_level, mileage, base_price, co2_emission, license_plate, current_location_id)
VALUES ('VF3ABCDEF12345678', 1, 'Allure', 1200, 189900.00, 112.0, 'AB12345', 1),
       ('VR1ABCDEF87654321', 2, 'Rivoli', 0, 299900.00, 125.5, 'CD67890', 1);

INSERT INTO rental_agreement (customer_id, car_id, location_id, employee_id, rental_period_id, start_date,
                              monthly_price)
VALUES (1, 1, 1, 1, 2, '2026-11-01', 3999.00);

-- Statushistorik (bil 1: AVAILABLE -> RENTED, bil 2: AVAILABLE)
INSERT INTO status_change (car_id, car_status_id, changed_at)
VALUES (1, 1, '2026-10-15 09:00:00'),
       (2, 1, '2026-10-15 09:00:00'),
       (1, 2, '2026-11-01 10:00:00');
