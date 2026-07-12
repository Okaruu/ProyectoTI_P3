
CREATE TABLE sucursal (
    id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(100) NOT NULL,
    numero_calle VARCHAR(10) NOT NULL,
    nombre_comuna VARCHAR(255) NOT NULL,
    nombre_region VARCHAR(255) NOT NULL
);

CREATE TABLE empleado (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    idSucursal INT,
    nombre_empleado VARCHAR(255) NOT NULL,
    puesto_empleado VARCHAR(255) NOT NULL,
    rut_empleado VARCHAR(13) NOT NULL UNIQUE,
    fecha_ingreso DATE NOT NULL,
    tipo_empleado VARCHAR(100) NOT NULL,
    salario DOUBLE NOT NULL,
    CONSTRAINT fk_empleado_sucursal FOREIGN KEY (idSucursal) REFERENCES sucursal(id_sucursal) ON DELETE CASCADE
);