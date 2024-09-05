-- create table country
CREATE TABLE IF NOT EXISTS country (
                                       country_id INT AUTO_INCREMENT PRIMARY KEY,
                                       country_name VARCHAR(100) NOT NULL,
    country_abb_name VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- insert mock data tp table country
INSERT INTO country (country_name, country_abb_name, status)
VALUES
    ('United States', 'USA', 'Active'),
    ('Canada', 'CAN', 'Active'),
    ('United Kingdom', 'UK', 'Active'),
    ('Australia', 'AUS', 'Active'),
    ('India', 'IND', 'Active'),
    ('Germany', 'DEU', 'Active'),
    ('France', 'FRA', 'Active'),
    ('Japan', 'JPN', 'Active'),
    ('Brazil', 'BRA', 'Active'),
    ('South Africa', 'ZAF', 'Active');


-- Create Product Table
CREATE TABLE product (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price DECIMAL(10, 2) NOT NULL,
                         view_count BIGINT DEFAULT 0,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert mock data into Product Table
INSERT INTO product (name, description, price, view_count) VALUES
                                                               ('Laptop', 'A high-performance laptop suitable for gaming and work.', 999.99, 50),
                                                               ('Smartphone', 'Latest model smartphone with cutting-edge technology.', 699.99, 120),
                                                               ('Headphones', 'Noise-cancelling headphones with superior sound quality.', 199.99, 30),
                                                               ('Smartwatch', 'A feature-packed smartwatch for fitness tracking.', 299.99, 75),
                                                               ('Tablet', 'Lightweight tablet with a sharp display and long battery life.', 499.99, 65),
                                                               ('Camera', 'Professional-grade camera for photography enthusiasts.', 1199.99, 25),
                                                               ('Gaming Console', 'Next-gen gaming console for immersive entertainment.', 399.99, 90),
                                                               ('Monitor', '4K monitor with stunning visuals for work and gaming.', 249.99, 45),
                                                               ('Keyboard', 'Mechanical keyboard with customizable RGB lighting.', 129.99, 60),
                                                               ('Mouse', 'Wireless ergonomic mouse with a long battery life.', 49.99, 35);