DROP TABLE IF EXISTS users;

CREATE TABLE users (id INT AUTO_INCREMENT  PRIMARY KEY,
                    title VARCHAR(50) NOT NULL,
                    first_name VARCHAR(50) NOT NULL,
                    last_name VARCHAR(50) NOT NULL,
                    gender VARCHAR(50) NOT NULL,
                    street VARCHAR(50) NOT NULL,
                    city VARCHAR(50) NOT NULL,
                    state VARCHAR(50) NOT NULL,
                    postcode VARCHAR(50) NOT NULL
);

INSERT INTO users (title, first_name, last_name, gender, street, city, state, postcode) VALUES
('Mr','Aliko', 'Dangote', 'Male','10 Lillian','Sydney','NSW','2145'),
('Mrs','Bill', 'Gates', 'Male','11 Lillian','Sydney','NSW','2762'),
('Mrs','Suji', 'Alakija', 'Female','12 Lillian','Sydney','NSW','2762'),
('Mr','John', 'Ali', 'Male','13 Lillian','Sydney','NSW','2145'),
('Mr','Fernandes', 'Geo', 'Male','14 Lillian','Sydney','NSW','2762');
