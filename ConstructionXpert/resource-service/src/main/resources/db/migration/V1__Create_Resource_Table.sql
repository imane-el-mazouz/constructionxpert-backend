CREATE TABLE resource (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          quantity INTEGER,
                          type VARCHAR(255) NOT NULL,
                          provider VARCHAR(255) NOT NULL,
                          task_id BIGINT NOT NULL
);
