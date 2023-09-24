CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL
);

CREATE TABLE reservations(
    id INT PRIMARY KEY AUTO_INCREMENT,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    adult_num INT NOT NULL,
    child_num INT,
    user_id INT NOT NULL,
    value FLOAT NOT NULL,

    CONSTRAINT RESERVATION_USER_FK FOREIGN KEY (user_id) REFERENCES users(id)
);