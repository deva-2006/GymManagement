-- =======================================================
--   Gym Management System - Database Schema (Version 1)
--   Author: Deva
--   Database: gymDB
-- =======================================================

-- Create Database
CREATE DATABASE IF NOT EXISTS gymDB;
USE gymDB;

-- =======================================================
--   TABLE: plans
-- =======================================================
CREATE TABLE IF NOT EXISTS plans (
    plan_name VARCHAR(50) PRIMARY KEY,
    price INT,
    duration_days INT
);

-- Sample Plans
INSERT INTO plans (plan_name, price, duration_days) VALUES
('Basic', 1000, 30),
('Silver', 2500, 60),
('Gold', 5000, 90);

-- =======================================================
--   TABLE: members
-- =======================================================
CREATE TABLE IF NOT EXISTS members (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    age INT,
    phone VARCHAR(15),
    plan_name VARCHAR(50),
    join_date DATE,
    FOREIGN KEY (plan_name) REFERENCES plans(plan_name)
);

-- Sample Member (Optional)
-- INSERT INTO members (name, age, phone, plan_name, join_date)
-- VALUES ('Demo User', 25, '9876543210', 'Basic', CURDATE());

-- =======================================================
--   TABLE: attendance
-- =======================================================
CREATE TABLE IF NOT EXISTS attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT,
    date DATE,
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- =======================================================
--   TABLE: users (Admin login)
-- =======================================================
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    role ENUM('admin','member')
);

-- Sample Admin
INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'admin');
