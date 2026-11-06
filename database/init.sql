CREATE DATABASE IF NOT EXISTS nutrivida_usuario;

CREATE DATABASE IF NOT EXISTS nutrivida_reserva;

CREATE DATABASE IF NOT EXISTS nutrivida_datos_clinicos;

CREATE USER IF NOT EXISTS 'nutrivida_user'@'%'
IDENTIFIED BY 'tu_contrasena_local';

GRANT ALL PRIVILEGES ON nutrivida_usuario.*
TO 'nutrivida_user'@'%';

GRANT ALL PRIVILEGES ON nutrivida_reserva.*
TO 'nutrivida_user'@'%';

GRANT ALL PRIVILEGES ON nutrivida_datos_clinicos.*
TO 'nutrivida_user'@'%';

FLUSH PRIVILEGES;