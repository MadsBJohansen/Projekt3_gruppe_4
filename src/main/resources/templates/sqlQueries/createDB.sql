DROP DATABASE IF EXISTS projekt3_gruppe_4_db;
CREATE DATABASE projekt3_gruppe_4_db;
USE projekt3_gruppe_4_db;


-- ------------------------------------------------------------
-- 1. Opslagstabeller (oprettes først, da andre peger på dem)
-- ------------------------------------------------------------

CREATE TABLE role
(
    role_id           INT          AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(50)  NOT NULL UNIQUE
);

CREATE TABLE zip_code
(
    zip_code_id       INT          AUTO_INCREMENT PRIMARY KEY,
    zip_code          CHAR(4)      NOT NULL UNIQUE,
    city              VARCHAR(50)  NOT NULL
);

CREATE TABLE car_brand
(
    car_brand_id      INT          AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(50)  NOT NULL UNIQUE
);

CREATE TABLE car_status
(
    car_status_id     INT          AUTO_INCREMENT PRIMARY KEY,
    status_code       VARCHAR(30)  NOT NULL UNIQUE
);

CREATE TABLE rental_period
(
    rental_period_id  INT          AUTO_INCREMENT PRIMARY KEY,
    months            INT      NOT NULL UNIQUE
);


-- ------------------------------------------------------------
-- 2. Bilmodeller og lokationer
-- ------------------------------------------------------------

CREATE TABLE car_model
(
    car_model_id      INT          AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(50)  NOT NULL,
    model_year        YEAR         NOT NULL,
    car_brand_id      INT          NOT NULL,

    CONSTRAINT fk_car_model_brand
        FOREIGN KEY (car_brand_id) REFERENCES car_brand (car_brand_id),
    CONSTRAINT uq_car_model
        UNIQUE (car_brand_id, name, model_year)
);

CREATE TABLE location
(
    location_id       INT           AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(100)  NOT NULL,
    street_address    VARCHAR(100)  NOT NULL,
    zip_code_id       INT           NOT NULL,

    CONSTRAINT fk_location_zip_code
        FOREIGN KEY (zip_code_id) REFERENCES zip_code (zip_code_id)
);


-- ------------------------------------------------------------
-- 3. Brugere og kunder
-- ------------------------------------------------------------

CREATE TABLE user
(
    user_id           INT           AUTO_INCREMENT PRIMARY KEY,
    first_name        VARCHAR(50)   NOT NULL,
    last_name         VARCHAR(50)   NOT NULL,
    username          VARCHAR(50)   NOT NULL UNIQUE,
    password_hash     VARCHAR(255)  NOT NULL,
    role_id           INT           NOT NULL,

    CONSTRAINT fk_user_role
        FOREIGN KEY (role_id) REFERENCES role (role_id)
);

CREATE TABLE customer
(
    customer_id       INT           AUTO_INCREMENT PRIMARY KEY,
    first_name        VARCHAR(50)   NOT NULL,
    last_name         VARCHAR(50)   NOT NULL,
    email             VARCHAR(255)  NOT NULL UNIQUE,
    phone_number      VARCHAR(20)   NOT NULL,
    street_address    VARCHAR(255)  NOT NULL,
    zip_code_id       INT           NOT NULL,

    CONSTRAINT fk_customer_zip_code
        FOREIGN KEY (zip_code_id) REFERENCES zip_code (zip_code_id)
);


-- ------------------------------------------------------------
-- 4. Biler og statushistorik
-- ------------------------------------------------------------

-- Bilens status gemmes IKKE her. Den nuværende status er den
-- seneste række i status_change (se VIEW car_current_status).
-- En ny bil oprettes sammen med sin første status_change.
CREATE TABLE car
(
    car_id               INT            AUTO_INCREMENT PRIMARY KEY,
    vin                  CHAR(17)       NOT NULL UNIQUE,
    car_model_id         INT            NOT NULL,
    trim_level           VARCHAR(50)    NOT NULL,
    mileage              INT            NOT NULL,
    base_price           DECIMAL(10,2)  NOT NULL,
    co2_emission         DECIMAL(5,1)   NOT NULL,
    license_plate        VARCHAR(10)    NOT NULL UNIQUE,
    current_location_id  INT            NOT NULL,

    CONSTRAINT fk_car_model
        FOREIGN KEY (car_model_id) REFERENCES car_model (car_model_id),
    CONSTRAINT fk_car_location
        FOREIGN KEY (current_location_id) REFERENCES location (location_id),
    CONSTRAINT chk_car_base_price CHECK (base_price > 0),
    CONSTRAINT chk_car_mileage    CHECK (mileage >= 0)
);

-- Historik: hver statusændring er en ny række (ændres/slettes ikke).
CREATE TABLE status_change
(
    status_change_id  INT       AUTO_INCREMENT PRIMARY KEY,
    car_id            INT       NOT NULL,
    car_status_id     INT       NOT NULL,
    changed_at        DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_status_change_car (car_id, status_change_id),
    CONSTRAINT fk_status_change_car
        FOREIGN KEY (car_id) REFERENCES car (car_id),
    CONSTRAINT fk_status_change_status
        FOREIGN KEY (car_status_id) REFERENCES car_status (car_status_id)
);

-- Nuværende status pr. bil = seneste status_change (højeste id).
-- Et VIEW gemmer ingen data, så der er ingen redundans.
CREATE VIEW car_current_status AS
SELECT sc.car_id,
       sc.car_status_id,
       cs.status_code,
       sc.changed_at
FROM status_change sc
         JOIN car_status cs ON cs.car_status_id = sc.car_status_id
WHERE sc.status_change_id = (SELECT MAX(sc2.status_change_id)
                             FROM status_change sc2
                             WHERE sc2.car_id = sc.car_id);


-- ------------------------------------------------------------
-- 5. Lejeaftaler og skader
-- ------------------------------------------------------------

-- Forventet returdato beregnes: start_date + rental_period.months.
CREATE TABLE rental_agreement
(
    rental_agreement_id  INT            AUTO_INCREMENT PRIMARY KEY,
    customer_id          INT            NOT NULL,
    car_id               INT            NOT NULL,
    location_id          INT            NOT NULL,
    user_id              INT            NOT NULL,
    rental_period_id     INT            NOT NULL,
    start_date           DATE           NOT NULL,
    return_date          DATE           NULL,
    monthly_price        DECIMAL(10,2)  NOT NULL,

    CONSTRAINT fk_rental_customer
        FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT fk_rental_car
        FOREIGN KEY (car_id) REFERENCES car (car_id),
    CONSTRAINT fk_rental_location
        FOREIGN KEY (location_id) REFERENCES location (location_id),
    CONSTRAINT fk_rental_user
        FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT fk_rental_period
        FOREIGN KEY (rental_period_id) REFERENCES rental_period (rental_period_id),
    CONSTRAINT chk_rental_monthly_price CHECK (monthly_price > 0),
    CONSTRAINT chk_rental_return_date
        CHECK (return_date IS NULL OR return_date >= start_date)
);

CREATE TABLE damage_report
(
    damage_report_id     INT   AUTO_INCREMENT PRIMARY KEY,
    rental_agreement_id  INT   NOT NULL UNIQUE,
    user_id              INT   NOT NULL,
    report_date          DATE  NOT NULL,

    CONSTRAINT fk_damage_report_rental
        FOREIGN KEY (rental_agreement_id)
            REFERENCES rental_agreement (rental_agreement_id),
    CONSTRAINT fk_damage_report_user
        FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE damage
(
    damage_id         INT            AUTO_INCREMENT PRIMARY KEY,
    damage_report_id  INT            NOT NULL,
    description       VARCHAR(255)   NOT NULL,
    repair_cost       DECIMAL(10,2)  NOT NULL,

    CONSTRAINT fk_damage_report
        FOREIGN KEY (damage_report_id)
            REFERENCES damage_report (damage_report_id) ON DELETE CASCADE,
    CONSTRAINT chk_damage_repair_cost CHECK (repair_cost >= 0)
);