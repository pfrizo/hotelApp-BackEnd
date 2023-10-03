CREATE TABLE users(
    id LONG PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL
);

CREATE TABLE rooms(
    id LONG PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    desc VARCHAR(150) NOT NULL,
    size VARCHAR(5) NOT NULL,
    guests INT NOT NULL,
    daily_value FLOAT NOT NULL
);

CREATE TABLE reservations(
    id LONG PRIMARY KEY AUTO_INCREMENT,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    adult_num INT NOT NULL,
    child_num INT,
    user_id LONG NOT NULL,
    room LONG NOT NULL,
    price FLOAT NOT NULL,

    CONSTRAINT reservations_users_FK FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT reservations_rooms_FK FOREIGN KEY (room) REFERENCES rooms(id)
);

INSERT INTO rooms (name, desc, size, guests, daily_value)
    VALUES ('Single', 'Single bed, bathroom, phone and wi-fi', '15m²', 1, 150.00);

INSERT INTO rooms (name, desc, size, guests, daily_value)
    VALUES ('Couple', 'Couple bed (Queen-size), bathroom, phone, wi-fi and tv', '25m²', 2, 200.00);

INSERT INTO rooms (name, desc, size, guests, daily_value)
    VALUES ('Luxury', '2 King-size beds, bathrooom, phone, wi-fi, breakfast, lunch, dinner', '40m²', 4, 300.00);