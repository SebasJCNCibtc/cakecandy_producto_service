CREATE TABLE categoria (
                           id_categoria INT PRIMARY KEY AUTO_INCREMENT,
                           nombre_categoria VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE marca (
                       id_marca INT PRIMARY KEY AUTO_INCREMENT,
                       nombre_marca VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE proveedor (
                           id_proveedor INT PRIMARY KEY AUTO_INCREMENT,
                           nombre_proveedor VARCHAR(100) NOT NULL,
                           contacto VARCHAR(100),
                           telefono VARCHAR(20),
                           email VARCHAR(100) UNIQUE
);

CREATE TABLE producto (
                          id_producto INT PRIMARY KEY AUTO_INCREMENT,
                          nombre_producto VARCHAR(200) NOT NULL,
                          precio_venta DECIMAL(10, 2) NOT NULL,
                          precio_costo DECIMAL(10, 2) NOT NULL,
                          stock INT NOT NULL,
                          id_categoria INT,
                          id_marca INT,
                          id_proveedor INT,
                          FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
                          FOREIGN KEY (id_marca) REFERENCES marca(id_marca),
                          FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

INSERT INTO categoria (nombre_categoria) VALUES ('Tortas');
INSERT INTO categoria (nombre_categoria) VALUES ('Galletas');

INSERT INTO marca (nombre_marca) VALUES ('Marca Propia');
INSERT INTO marca (nombre_marca) VALUES ('Donofrio');

INSERT INTO proveedor (nombre_proveedor, email) VALUES ('Proveedor de Harinas SA', 'contacto@harinassa.com');
INSERT INTO proveedor (nombre_proveedor, email) VALUES ('Distribuidora de Azucar S.R.L.', 'ventas@azucarsrl.com');