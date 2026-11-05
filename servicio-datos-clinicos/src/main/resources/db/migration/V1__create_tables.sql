CREATE TABLE fichas_clinicas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL UNIQUE,
    plan_alimentacion VARCHAR(4000)
);

CREATE TABLE informes_clinicos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ficha_clinica INT NOT NULL,
    id_nutricionista INT NOT NULL,
    fecha DATE NOT NULL,
    peso DECIMAL(6, 2) NOT NULL CHECK (peso > 0),
    talla DECIMAL(6, 2) NOT NULL CHECK (talla > 0),
    circunferencia_cintura DECIMAL(6, 2) NOT NULL CHECK (circunferencia_cintura > 0),
    descripcion VARCHAR(4000) NOT NULL,

    CONSTRAINT fk_informe_ficha_clinica
        FOREIGN KEY (id_ficha_clinica) REFERENCES fichas_clinicas(id)
);