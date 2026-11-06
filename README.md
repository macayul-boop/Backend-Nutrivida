# Nutrivida - Backend con Docker

Este repositorio contiene el backend de **Nutrivida**, desarrollado con Spring Boot y una arquitectura de microservicios.

El proyecto utiliza Docker Compose para construir y ejecutar automáticamente todos los servicios necesarios, incluyendo MySQL, Eureka y API Gateway.

## Requisitos previos

Antes de comenzar, debes tener instalado:

- [Git](https://git-scm.com/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows o Mac).
- Docker Compose, incluido en las versiones actuales de Docker Desktop.

## 1. Clonar el repositorio

Abre una terminal y ejecuta:

```bash
git clone URL_DEL_REPOSITORIO
```

Ingresa a la carpeta del proyecto:

```bash
cd backend-nutrivida
```

## 2. Configurar las variables de entorno

En la raíz del proyecto, crea un archivo llamado `.env`.

Este archivo debe contener las siguientes variables:

```properties
DB_USERNAME=nutrivida_user
DB_PASSWORD=tu_contrasena_local
MYSQL_ROOT_PASSWORD=tu_contrasena_root
JWT_SECRET=TU_SECRETO_JWT
```

**Descripción de las variables:**

| Variable | Descripción |
|---|---|
| `DB_USERNAME` | Usuario que utilizarán los microservicios para conectarse a MySQL. |
| `DB_PASSWORD` | Contraseña del usuario de la base de datos. Debe coincidir con la contraseña definida en `database/init.sql`. |
| `MYSQL_ROOT_PASSWORD` | Contraseña del usuario administrador de MySQL. |
| `JWT_SECRET` | Clave secreta utilizada para firmar y validar los tokens JWT. |

**Importante:** No compartas ni subas el archivo `.env` al repositorio. Este archivo está excluido mediante `.gitignore`.

## 3. Levantar los servicios con Docker Compose

Asegúrate de que Docker Desktop esté iniciado.

Desde la raíz del proyecto, ejecuta:

```bash
docker compose up --build -d
```

Este comando se encarga de:

- Construir las imágenes Docker utilizando los Dockerfiles de cada microservicio.
- Crear los contenedores correspondientes.
- Crear la red interna para permitir la comunicación entre servicios.
- Crear el volumen persistente de MySQL.
- Ejecutar los servicios en segundo plano.

No es necesario construir las imágenes manualmente ni iniciar cada contenedor por separado.

**La primera ejecución puede tardar algunos minutos**, ya que Docker debe descargar las imágenes base y compilar los microservicios.

## 4. Comprobar que los servicios estén funcionando

Para revisar el estado de los contenedores, ejecuta:

```bash
docker compose ps
```

Los seis contenedores deberían aparecer en estado `Up`.

También puedes consultar los registros de ejecución con:

```bash
docker compose logs --tail=50
```

Para visualizar los registros de un servicio específico:

```bash
docker compose logs servicio-usuario
```

## 5. Acceder a los servicios

Una vez iniciados los contenedores, puedes acceder a los siguientes servicios desde tu navegador:

| Servicio | Dirección |
|---|---|
| API Gateway | http://localhost:8080 |
| Eureka Dashboard | http://localhost:8761 |

Los microservicios y MySQL se comunican internamente mediante la red de Docker. Por lo tanto, sus puertos no están publicados hacia el equipo anfitrión.

## 6. Detener los servicios

Para detener y eliminar los contenedores, ejecuta:

```bash
docker compose down
```

Este comando no elimina las imágenes ni el volumen de MySQL, por lo que los datos de las bases de datos se conservan.

Para iniciar nuevamente los servicios:

```bash
docker compose up -d
```

Si modificaste el código fuente y necesitas reconstruir las imágenes:

```bash
docker compose up --build -d
```

## Consideraciones importantes

- Las bases de datos se inicializan automáticamente mediante el archivo `database/init.sql` durante la primera inicialización del volumen de MySQL.
- Las migraciones y creación de tablas son administradas por Flyway en cada microservicio.
- Si modificas `init.sql` después de haber inicializado MySQL, el script no se ejecutará nuevamente automáticamente.
- El volumen `mysql_data` permite conservar la información de las bases de datos al detener o eliminar los contenedores.
- **No utilices `docker compose down -v`** si deseas conservar los datos almacenados en MySQL.
- Para ejecutar el proyecto en otro equipo, es necesario crear su propio archivo `.env` y configurar correctamente las credenciales.

---

**Nutrivida Backend**  
Desarrollado con Spring Boot, Spring Cloud, MySQL, Eureka y Docker.