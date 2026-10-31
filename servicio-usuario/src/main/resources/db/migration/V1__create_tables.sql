CREATE TABLE usuarios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(40) UNIQUE NOT NULL,
    nombre VARCHAR(60) NOT NULL,
    apellidos VARCHAR(60) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(60) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(40) NOT NULL,
    estado VARCHAR(40) NOT NULL
);

CREATE TABLE horario_nutricionista(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    dia_semana VARCHAR(20) NOT NULL,
    hora_entrada TIME NOT NULL,
    hora_salida TIME NOT NULL,

    CONSTRAINT fk_horario_nutricionista
            FOREIGN KEY (id_usuario)
            REFERENCES usuarios(id)
            ON DELETE CASCADE
);