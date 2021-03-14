DROP TABLE IF EXISTS vehicle;

CREATE TABLE vehicle (
    id SERIAL  PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    color VARCHAR(50) NOT NULL,
    power INT NOT NULL

);
