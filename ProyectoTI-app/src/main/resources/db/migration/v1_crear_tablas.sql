--las cree de menor a mayor dependenmcia asi no tira error al ejecutar
CREATE TABLE region (
    idRegion      INT            NOT NULL AUTO_INCREMENT,
    nombreRegion  VARCHAR(255)   NOT NULL,
    PRIMARY KEY (idRegion)
);
CREATE TABLE comuna (
    idComuna      INT            NOT NULL AUTO_INCREMENT,
    nombreComuna  VARCHAR(255)   NOT NULL,
    PRIMARY KEY (idComuna)
);
CREATE TABLE categoria (
    idCategoria      INT           NOT NULL AUTO_INCREMENT,
    nombreCategoria  VARCHAR(255)  NOT NULL,
    detalleCategoria VARCHAR(500)  NOT NULL,
    PRIMARY KEY (idCategoria)
);
CREATE TABLE marca (
    idMarca      INT           NOT NULL AUTO_INCREMENT,
    nombreMarca  VARCHAR(255)  NOT NULL,
    PRIMARY KEY (idMarca)
);
CREATE TABLE cliente (
    idCliente       INT           NOT NULL AUTO_INCREMENT,
    rutCliente      VARCHAR(13)   NOT NULL UNIQUE,
    nombreCliente   VARCHAR(35)   NOT NULL,
    telefonoCliente VARCHAR(12)   NOT NULL,
    emailCliente    VARCHAR(255)  NOT NULL,
    PRIMARY KEY (idCliente)
);
CREATE TABLE tipoEmpleado (
    idTipoEmpleado  INT            NOT NULL AUTO_INCREMENT,
    puesto          VARCHAR(100)   NOT NULL,
    tipoEmpleado    VARCHAR(100)   NOT NULL,
    salario         DOUBLE         NOT NULL,
    PRIMARY KEY (idTipoEmpleado)
);
CREATE TABLE sucursal (
    idSucursal   INT           NOT NULL AUTO_INCREMENT,
    calle        VARCHAR(100)  NOT NULL,
    numeroCalle  VARCHAR(10)   NOT NULL,
    idComuna     INT,
    idRegion     INT,
    PRIMARY KEY (idSucursal),
    CONSTRAINT fk_sucursal_comuna  FOREIGN KEY (idComuna)  REFERENCES comuna  (idComuna),
    CONSTRAINT fk_sucursal_region  FOREIGN KEY (idRegion)  REFERENCES region  (idRegion)
);
CREATE TABLE empleado (
    idEmpleado      INT           NOT NULL AUTO_INCREMENT,
    idTipoEmpleado  INT,
    idSucursal      INT,
    nombreEmpleado  VARCHAR(255)  NOT NULL,
    puestoEmpleado  VARCHAR(255)  NOT NULL,
    rutEmpleado     VARCHAR(13)   NOT NULL UNIQUE,
    fechaIngreso    DATE          NOT NULL,
    PRIMARY KEY (idEmpleado),
    CONSTRAINT fk_empleado_tipo_empleado  FOREIGN KEY (idTipoEmpleado)  REFERENCES tipoEmpleado (idTipoEmpleado),
    CONSTRAINT fk_empleado_sucursal       FOREIGN KEY (idSucursal)       REFERENCES sucursal     (idSucursal)
);
CREATE TABLE producto (
    idProducto          INT            NOT NULL AUTO_INCREMENT,
    nombreProducto      VARCHAR(255)   NOT NULL,
    descripcionProducto VARCHAR(255)   NOT NULL,
    stockProducto       INT            NOT NULL,
    precioProducto      DOUBLE         NOT NULL,
    idCategoria         INT,
    idMarca             INT,
    PRIMARY KEY (idProducto),
    CONSTRAINT fk_producto_categoria  FOREIGN KEY (idCategoria)  REFERENCES categoria (idCategoria),
    CONSTRAINT fk_producto_marca      FOREIGN KEY (idMarca)      REFERENCES marca     (idMarca)
);
CREATE TABLE venta (
    idVenta     INT     NOT NULL AUTO_INCREMENT,
    idCliente   INT,
    idEmpleado  INT,
    fechaVenta  DATE    NOT NULL,
    precioFinal DOUBLE  NOT NULL,
    PRIMARY KEY (idVenta),
    CONSTRAINT fk_venta_cliente   FOREIGN KEY (idCliente)   REFERENCES cliente   (idCliente),
    CONSTRAINT fk_venta_empleado  FOREIGN KEY (idEmpleado)  REFERENCES empleado  (idEmpleado)
);
CREATE TABLE detalle_venta (
    idDetalleVenta    INT     NOT NULL AUTO_INCREMENT,
    idVenta           INT     NOT NULL,
    idProducto        INT     NOT NULL,
    cantidadProducto  INT     NOT NULL,
    precioUnitario    DOUBLE  NOT NULL,
    PRIMARY KEY (idDetalleVenta),
    CONSTRAINT fk_detalle_venta_venta     FOREIGN KEY (idVenta)     REFERENCES venta    (idVenta),
    CONSTRAINT fk_detalle_venta_producto  FOREIGN KEY (idProducto)  REFERENCES producto (idProducto)
);