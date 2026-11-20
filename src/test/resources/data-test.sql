INSERT INTO car_brand (car_brand_id, name)
VALUES (1, 'Peugeot');

INSERT INTO zip_code (zip_code_id, zip_code, city)
VALUES (1, '2200', 'København N');

INSERT INTO car_model
(car_model_id, name, model_year, car_brand_id)
VALUES (1, '208', 2025, 1);

INSERT INTO location
(location_id, name, street_address, zip_code_id)
VALUES (1, 'Bilabonnement Test', 'Testvej 1', 1);