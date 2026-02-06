
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE IF NOT EXISTS maintenance_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id VARCHAR(50) NOT NULL,
    maintenance_type VARCHAR(100) NOT NULL,
    service_date DATE NOT NULL,
    service_center VARCHAR(100) NOT NULL,
    cost DECIMAL(12,2),
    notes VARCHAR(500),
    under_maintenance BOOLEAN NOT NULL DEFAULT FALSE,
    technician VARCHAR(100),
    estimated_readiness DATE
);