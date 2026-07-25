CREATE TABLE customer_management.customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMP NOT NULL
);

INSERT INTO customer_management.customers (first_name, last_name, date_of_birth, created_at) VALUES
('John', 'Doe', '1990-01-01', CURRENT_TIMESTAMP),
('Jane', 'Smith', '1985-05-15', CURRENT_TIMESTAMP),
('Alice', 'Johnson', '1992-09-30', CURRENT_TIMESTAMP),
('Bob', 'Williams', '1988-12-20', CURRENT_TIMESTAMP),
('Charlie', 'Brown', '1995-07-10', CURRENT_TIMESTAMP),
('David', 'Jones', '1983-03-25', CURRENT_TIMESTAMP),
('Eve', 'Davis', '1991-11-05', CURRENT_TIMESTAMP),
('Frank', 'Miller', '1987-08-18', CURRENT_TIMESTAMP),
('Grace', 'Wilson', '1993-04-12', CURRENT_TIMESTAMP),
('Hannah', 'Moore', '1989-06-22', CURRENT_TIMESTAMP),
('Ian', 'Taylor', '1994-02-14', CURRENT_TIMESTAMP),
('Jack', 'Anderson', '1986-10-08', CURRENT_TIMESTAMP),
('Kathy', 'Thomas', '1990-03-03', CURRENT_TIMESTAMP),
('Leo', 'Jackson', '1984-09-17', CURRENT_TIMESTAMP),
('Mia', 'White', '1992-12-01', CURRENT_TIMESTAMP),
('Nina', 'Harris', '1988-05-27', CURRENT_TIMESTAMP),
('Oscar', 'Martin', '1991-07-19', CURRENT_TIMESTAMP),
('Paula', 'Thompson', '1985-11-11', CURRENT_TIMESTAMP),
('Quinn', 'Garcia', '1993-01-29', CURRENT_TIMESTAMP),
('Ryan', 'Martinez', '1987-04-04', CURRENT_TIMESTAMP);