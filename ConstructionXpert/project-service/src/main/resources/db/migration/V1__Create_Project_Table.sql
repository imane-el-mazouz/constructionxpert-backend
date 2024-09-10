CREATE TABLE project (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         start_date DATE,
                         end_date DATE,
                         budget DECIMAL(19,2)
);
