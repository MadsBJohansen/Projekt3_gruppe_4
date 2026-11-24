DROP DATABASE IF EXISTS projekt3_gruppe_4_db;
CREATE DATABASE projekt3_gruppe_4_db;
USE projekt3_gruppe_4_db;

CREATE TABLE user
(
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(50)  NOT NULL,
    last_name     VARCHAR(50)  NOT NULL,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role
                  VARCHAR(30)  NOT NULL
);

CREATE TABLE zip_code
(
    zip_code_id INT AUTO_INCREMENT PRIMARY KEY,
    zip_code    CHAR(4)     NOT NULL UNIQUE,
    city        VARCHAR(50) NOT NULL
);

CREATE TABLE location
(
    location_id    INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(200) NOT NULL,
    street_address VARCHAR(200) NOT NULL,
    zip_code_id    INT          NOT NULL,

    CONSTRAINT fk_location_zip_code
        FOREIGN KEY (zip_code_id) REFERENCES zip_code (zip_code_id)
);

CREATE TABLE customer
(
    customer_id    INT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    email          VARCHAR(200) NOT NULL UNIQUE,
    phone_number   VARCHAR(20)  NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    zip_code_id    INT          NOT NULL,

    CONSTRAINT fk_customer_zip_code
        FOREIGN KEY (zip_code_id) REFERENCES zip_code (zip_code_id)
);

CREATE TABLE car
(
    car_id              INT AUTO_INCREMENT PRIMARY KEY,
    trim_level          VARCHAR(17)    NOT NULL,
    milage              INT            NOT NULL,
    base_price          DECIMAL(10, 2) NOT NULL,
    co2_emission        DECIMAL(5, 1)  NOT NULL,
    license_plate       VARCHAR(10)    NOT NULL UNIQUE,
    current_location_id INT            NOT NULL,
    car_brand           VARCHAR(50)    NOT NULL,
    car_model           VARCHAR(50)    NOT NULL,
    car_year            YEAR			NOT NULL,

    FOREIGN KEY (current_location_id) REFERENCES location (location_id)

);

CREATE TABLE car_status_log
(
    car_status_log_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id            INT         NOT NULL,
    changed_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    car_status        VARCHAR(30) NOT NULL,
        CONSTRAINT fk_car_status_log_car FOREIGN KEY (car_id) REFERENCES car (car_id)

);

CREATE TABLE contract
(
    contract_id     INT AUTO_INCREMENT PRIMARY KEY,
    customer_id     INT  NOT NULL,
    car_id          INT  NOT NULL,
    location_id     INT  NOT NULL,
    user_id         INT  NOT NULL,
    start_date      DATE NOT NULL,
    car_return_date DATE,
    canceled_at		DATETIME,
    contract_months INT  NOT NULL,
        CONSTRAINT fk_contract_customer_id FOREIGN KEY(customer_id) REFERENCES customer(customer_id),
CONSTRAINT fk_contract_car_id
FOREIGN KEY(car_id) REFERENCES car(car_id),
CONSTRAINT fk_contract_location_id
FOREIGN KEY(location_id) REFERENCES location(location_id),
CONSTRAINT fk_contract_user_id
FOREIGN KEY(user_id) REFERENCES user(user_id)

);

CREATE TABLE damage_report
(
    damage_report_id INT AUTO_INCREMENT PRIMARY KEY,
    contract_id      INT  NOT NULL,
    user_id          INT  NOT NULL,
    report_date      DATE NOT NULL,

    FOREIGN KEY (contract_id) REFERENCES contract (contract_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE damage
(
    damage_id        INT AUTO_INCREMENT PRIMARY KEY,
    damage_report_id INT            NOT NULL,
    description      VARCHAR(255)   NOT NULL,
    repair_cost      DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (damage_report_id) REFERENCES damage_report (damage_report_id)
);
