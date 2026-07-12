
CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    rut_cliente VARCHAR(13) NOT NULL,
    nombre_cliente VARCHAR(35) NOT NULL,
    telefono_cliente VARCHAR(12),
    email_cliente VARCHAR(255),
    rut_empleado VARCHAR(13) NOT NULL,
    nombre_empleado VARCHAR(255) NOT NULL,
    fecha_venta DATE NOT NULL,
    precio_final DOUBLE NOT NULL
);

CREATE TABLE detalle_venta (
    id_detalle_venta INT AUTO_INCREMENT PRIMARY KEY,
    idVenta INT NOT NULL,
    nombre_producto VARCHAR(255) NOT NULL,
    cantidad_producto INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    CONSTRAINT fk_detalle_venta_venta FOREIGN KEY (idVenta) REFERENCES venta(id_venta) ON DELETE CASCADE
);