
INSERT IGNORE INTO users (username, email, password, role, enabled) VALUES 
('admin', 'admin@drivego.com', '$2a$10$.7/UOAsGFc4Z9yiQOaH/aOwC4cgWt.Vs5bI.We8I9IWrQ.KgcLrYe', 'ADMIN', TRUE),
('user', 'user@drivego.com', '$2a$10$MVlbFSGswNa7IvRIjSmLlOXqYXPSI6UPb6w82dqtSk7Gcr5UBnCAW', 'USER', TRUE);


INSERT IGNORE INTO maintenance_records (vehicle_id, maintenance_type, service_date, service_center, cost, notes, under_maintenance, technician, estimated_readiness) VALUES 
('CAR-1001', 'Oil Change', '2024-09-20', 'QuickLube Center', 79.99, 'Used synthetic oil', FALSE, 'John Smith', '2024-09-25'),
('CAR-1002', 'Tire Rotation', '2024-08-30', 'TirePro Shop', 49.50, 'Rotated all four tires', FALSE, 'Mike Johnson', '2024-09-05'),
('CAR-1003', 'Brake Inspection', '2024-09-25', 'AutoCare Plus', 120.00, 'Front brake pads replaced', TRUE, 'Sarah Wilson', '2024-10-02'),
('CAR-1004', 'Engine Tune-up', '2024-09-15', 'ProMechanic', 250.00, 'Spark plugs and filters replaced', FALSE, 'David Brown', '2024-09-20');