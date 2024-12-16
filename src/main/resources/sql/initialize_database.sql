-- Eliminar tablas existentes
DROP TABLE IF EXISTS user_trainer;
DROP TABLE IF EXISTS pokemon;
DROP TABLE IF EXISTS trainer;
DROP TABLE IF EXISTS users;

-- Crear tabla 'users'
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       phone VARCHAR(100),
                       password VARCHAR(100) NOT NULL
);

-- Crear tabla 'trainer'
CREATE TABLE trainer (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         birth_date DATE NOT NULL,
                         nationality VARCHAR(100),
                         user_id INT,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Crear tabla 'pokemon'
CREATE TABLE pokemon (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         type VARCHAR(50),
                         energy FLOAT DEFAULT 100,
                         power INT,
                         specie VARCHAR(100),
                         trainer_id INT,
                         FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE SET NULL
);

-- Crear tabla 'user_trainer'
CREATE TABLE user_trainer (
                              user_id INT,
                              trainer_id INT,
                              PRIMARY KEY (user_id, trainer_id),
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                              FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE CASCADE
);

-- Insertar datos en 'users'
INSERT INTO users (name, phone, password) VALUES ('Casia', '1111111111', 'password123');
INSERT INTO users (name, phone, password) VALUES ('Lumen', '2222222222', 'password456');
INSERT INTO users (name, phone, password) VALUES ('Luis', '3333333333', 'password789');

-- Insertar datos en 'trainer'
INSERT INTO trainer (name, birth_date, nationality, user_id) VALUES ('Haruto Takahashi', '1988-05-12', 'Japón', 1);
INSERT INTO trainer (name, birth_date, nationality, user_id) VALUES ('Aiko Nakamura', '1992-07-24', 'Japón', 2);
INSERT INTO trainer (name, birth_date, nationality, user_id) VALUES ('Ren Zhang', '1990-11-10', 'China', 3);

-- Insertar datos en 'pokemon'
INSERT INTO pokemon (type, power, specie, trainer_id) VALUES ('Fuego', 50, 'Charmander', 1);
INSERT INTO pokemon (type, power, specie, trainer_id) VALUES ('Agua', 60, 'Squirtle', 2);
INSERT INTO pokemon (type, power, specie, trainer_id) VALUES ('Eléctrico', 55, 'Pikachu', 3);
INSERT INTO pokemon (type, power, specie, trainer_id) VALUES ('Planta', 45, 'Bulbasaur', NULL);

-- Relacionar usuarios y entrenadores en 'user_trainer'
INSERT INTO user_trainer (user_id, trainer_id) VALUES (1, 1);
INSERT INTO user_trainer (user_id, trainer_id) VALUES (2, 2);
INSERT INTO user_trainer (user_id, trainer_id) VALUES (3, 3);