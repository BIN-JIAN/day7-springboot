use bootcamp;
CREATE TABLE employee (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          age INT NOT NULL,
                          salary DOUBLE NOT NULL,
                          gender VARCHAR(10) NOT NULL,
                          status BOOLEAN NOT NULL,
                          company_id BIGINT NOT NULL
);
