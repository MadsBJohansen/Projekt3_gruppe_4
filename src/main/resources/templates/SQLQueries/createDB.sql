DROP DATABASE IF EXISTS projekt3_gruppe_4_db;
CREATE DATABASE projekt3_gruppe_4_db;
USE projekt3_gruppe_4_db;

CREATE TABLE user
(
    id       		INT 			AUTO_INCREMENT PRIMARY KEY,
    first_name    	VARCHAR(50) 	NOT NULL,
    last_name     	VARCHAR(50) 	NOT NULL,
    username      	VARCHAR(50)	NOT NULL UNIQUE,
    password_hash 	VARCHAR(255) 	NOT NULL,
    role			VARCHAR(30) 	NOT NULL
);

CREATE TABLE zip_code
(
    id 			INT 			AUTO_INCREMENT PRIMARY KEY,
    zip_code    		CHAR(4)     		NOT NULL UNIQUE,
    city        		VARCHAR(50) 	NOT NULL
);

CREATE TABLE location
(
    id   			INT 			AUTO_INCREMENT PRIMARY KEY,
    name           		VARCHAR(100) 	NOT NULL,
    street_address 	VARCHAR(100) 	NOT NULL,
    zip_code_id		INT          		NOT NULL,

    CONSTRAINT fk_location_zip_code
        FOREIGN KEY (zip_code_id) REFERENCES zip_code (id)
);

CREATE TABLE customer
(
    id    			INT 			AUTO_INCREMENT PRIMARY KEY,
    first_name    	VARCHAR(50)  	NOT NULL,
    last_name   		VARCHAR(50)  	NOT NULL,
    email          		VARCHAR(255) 	NOT NULL UNIQUE,
    phone_number   	VARCHAR(20)  	NOT NULL,
    street_address 	VARCHAR(255) 	NOT NULL,
    zip_code_id    	INT          		NOT NULL,

    CONSTRAINT fk_customer_zip_code
        FOREIGN KEY (zip_code_id) REFERENCES zip_code (id)
);

CREATE TABLE car
(
    id              		INT 			AUTO_INCREMENT PRIMARY KEY,
    vin 			VARCHAR(17) 	NOT NULL UNIQUE,
    trim_level          	VARCHAR(50)    	NOT NULL,
    mileage 		INT        	    	NOT NULL,
    base_price          	DECIMAL(10, 2) 	NOT NULL,
    co2_emission     	DECIMAL(5, 1)	NOT NULL,
    license_plate      	VARCHAR(10)  	NOT NULL UNIQUE,
    current_location_id	INT          		NOT NULL,
    brand           		VARCHAR(50)    	NOT NULL,
    model           	VARCHAR(50)    	NOT NULL,
    year            		YEAR          		NOT NULL,


    CONSTRAINT fk_car_current_location_id
        FOREIGN KEY (current_location_id) REFERENCES location (id),
    CONSTRAINT chk_car_base_price
        CHECK (base_price > 0),
    CONSTRAINT chk_car_mileage
        CHECK (mileage >= 0),
    CONSTRAINT chk_car_co2_emission
        CHECK (co2_emission >= 0),
    CONSTRAINT chk_car_vin
        CHECK (CHAR_LENGTH(vin) = 17)

);

CREATE TABLE car_status_log
(
    id 			INT 			AUTO_INCREMENT PRIMARY KEY,
    car_id            	INT         		NOT NULL,
    changed_at        	DATETIME    		NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status        		VARCHAR(30) 	NOT NULL,

    CONSTRAINT fk_car_status_log_car FOREIGN KEY (car_id) REFERENCES car (id)

);

CREATE TABLE contract
(
    id    			INT 			AUTO_INCREMENT PRIMARY KEY,
    customer_id   	INT  			NOT NULL,
    car_id         		INT  			NOT NULL,
    location_id     	INT  			NOT NULL,
    user_id         	INT  			NOT NULL,
    start_date      	DATE 			NOT NULL,
    car_return_date 	DATE,
    canceled_at    	DATETIME,
    `period`	 	INT  			NOT NULL,
    monthly_price	DECIMAL(10,2)	NOT NULL,
    CONSTRAINT fk_contract_customer_id
        FOREIGN KEY(customer_id) REFERENCES customer(id),
    CONSTRAINT fk_contract_car_id
        FOREIGN KEY(car_id) REFERENCES car(id),
    CONSTRAINT fk_contract_location_id
        FOREIGN KEY(location_id) REFERENCES location(id),
    CONSTRAINT fk_contract_user_id
        FOREIGN KEY(user_id) REFERENCES user(id),
    CONSTRAINT chk_contract_period
        CHECK(`period` = 3 OR `period` = 6 OR `period` = 12),
    CONSTRAINT chk_contract_monthly_price
        CHECK(monthly_price >= 0)

);

CREATE TABLE damage_report
(
    id 			INT 			AUTO_INCREMENT PRIMARY KEY,
    contract_id    	INT  			NOT NULL UNIQUE,
    user_id          	INT  			NOT NULL,
    date     	 	DATE 			NOT NULL,

    FOREIGN KEY (contract_id) REFERENCES contract (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE damage
(
    id        		INT 			AUTO_INCREMENT PRIMARY KEY,
    damage_report_id 	INT      		NOT NULL,
    description      	VARCHAR(255)   	NOT NULL,
    repair_cost      	DECIMAL(10, 2) 	NOT NULL,

    CONSTRAINT chk_repair_cost
        CHECK (repair_cost > 0),
    CONSTRAINT fk_damage_damage_report_id
        FOREIGN KEY (damage_report_id) REFERENCES damage_report (id)
);
