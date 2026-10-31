CREATE TABLE servicios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(250) NOT NULL,
    precio INT NOT NULL,
    duracion_minutos INT NOT NULL,
    modalidad VARCHAR(60) NOT NULL
);

CREATE TABLE reservas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_nutricionista INT NOT NULL,
    id_cliente INT NOT NULL,
    id_servicio INT NOT NULL,
    estado VARCHAR(40) NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME  NOT NULL,

    CONSTRAINT fk_reserva_servicio
            FOREIGN KEY (id_servicio)
            REFERENCES servicios(id)
            ON DELETE CASCADE
);

