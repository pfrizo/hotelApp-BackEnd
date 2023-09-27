CREATE TABLE users(
    id LONG PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL
);

CREATE TABLE rooms(
    id LONG PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    desc VARCHAR(100) NOT NULL,
    size INT NOT NULL,
    guests INT NOT NULL,
    dailyValue FLOAT NOT NULL
);

CREATE TABLE reservations(
    id LONG PRIMARY KEY AUTO_INCREMENT,
    checkIn DATE NOT NULL,
    checkOut DATE NOT NULL,
    adultNum INT NOT NULL,
    childNum INT,
    user LONG NOT NULL,
    room LONG NOT NULL,
    value FLOAT NOT NULL,

    CONSTRAINT reservations_users_FK FOREIGN KEY (user) REFERENCES users(id),
    CONSTRAINT reservations_rooms_FK FOREIGN KEY (room) REFERENCES rooms(id)
);

