USE projekt3_gruppe_4_db;

INSERT INTO zip_code(zip_code,city)
VALUES      ('2200','København N'),
            ('2600','Glostrup'),
            ('2605','Brøndby'),
            ('2300','København S'),
            ('5000','Odense C'),
            ('8000','Aarhus C'),
            ('9000','Aalborg');


-- Password = 'demo' (BCrypt, virker med Spring Securitys BCryptPasswordEncoder)
SET @demo = '$2a$10$Y/lmprXp2F9azH1kYbZ5sOB/nXUS2b6vTAgZX8QIjD1UXkPlcrCRK';

INSERT INTO user(first_name, last_name, username, password_hash, role)
VALUES      ('Demo', 'Demosen', 'demo', @demo, 'Admin'),
            ('Mads', 'Johansen', 'mads', @demo, 'Admin'),
            ('Gustav', 'Clement', 'gustav', @demo, 'Business Developer'),
            ('Al', 'Agami', 'al', @demo, 'Data Registration'),
            ('Rasmus','Gøtze', 'rasmus', @demo, 'Damage And Repair');

INSERT INTO customer(first_name, last_name, email, phone_number, street_address, zip_code_id)
VALUES      ('Bjørn', 'Programmering', 'bjoern@example.com', '+4511223344', 'Guldbergsgade 29', 1),
            ('Oskar',	'It&Forret','oskar@example.com', '+4522334455', 'Guldbergsgade 29', 1),
            ('Jaron', 'Systemudvikling', 'jaron@example.com', '+4533445566', 'Guldbergsgade 29', 1);

INSERT INTO location(name, street_address, zip_code_id)
VALUES	('Bilabonnement A/S', 'Vibeholmsvej', 3);

INSERT INTO car (vin, trim_level, mileage, base_price, co2_emission,
                 license_plate, current_location_id, brand, model, year)
VALUES
    ('HBUSRJGF9CBFPR9BN' ,'allure', 61860, 140000.00, 105.0, 'AB12345', 1, 'Fiat', '500 Vita comfort 70 HK', 2023),
    ('2T1BURHE7JC019482' ,'rivoli',    27160, 330000.00, 0, 'CD67890', 1, 'Polestar', '2 Plus Standard Range Single Motor', 2022);

INSERT INTO contract(customer_id, car_id, location_id, user_id, start_date, monthly_price, period)
VALUES
    (1, 2, 1, 2, '2026-11-24', 3999.00, 6);

INSERT INTO car_status_log (car_id, changed_at, status)
VALUES (1, '2026-11-20 09:00:00', 'AVAILABLE'),
       (2, '2026-11-20 09:00:00', 'AVAILABLE'),
       (2, '2026-11-24 10:00:00', 'RENTED');
