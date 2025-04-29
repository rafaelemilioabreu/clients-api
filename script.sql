CREATE TABLE clients (
    client_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20)
);

CREATE TABLE addresses (
    address_id SERIAL PRIMARY KEY,
    client_id INTEGER NOT NULL,
    address TEXT NOT NULL,
    CONSTRAINT fk_client
        FOREIGN KEY (client_id)
        REFERENCES clients (client_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_addresses_client_id ON addresses (client_id);

INSERT INTO clients (first_name, last_name, email, phone) VALUES
('Juan', 'Pérez', 'juan.perez@ejemplo.com', '809-123-4567'),
('María', 'González', 'maria.gonzalez@ejemplo.com', '809-765-4321');

INSERT INTO addresses (client_id, address) VALUES
(1, 'Calle Principal #123, Los Jardines, Santiago'),
(1, 'Avenida Comercial #456, Zona Colonial, Santo Domingo'),
(2, 'Calle Las Flores #789, Bella Vista, Santo Domingo');