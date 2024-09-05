CREATE TABLE country (
                         country_id INT AUTO_INCREMENT PRIMARY KEY,
                         country_name VARCHAR(100) NOT NULL,
                         country_abb_name VARCHAR(10) NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);