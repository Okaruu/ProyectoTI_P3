CREATE DATABASE IF NOT EXISTS instrumento_db;
USE instrumento_db;

CREATE TABLE instrumento (
    id_instrumento INT AUTO_INCREMENT PRIMARY KEY,
    nombre_instrumento VARCHAR(255) NOT NULL,
    descripcion_instrumento VARCHAR(255) NOT NULL,
    stock_instrumento INT NOT NULL,
    precio_instrumento DOUBLE NOT NULL,
    nombre_categoria VARCHAR(255) NOT NULL,
    detalle_categoria VARCHAR(500),
    nombre_marca VARCHAR(255) NOT NULL
);