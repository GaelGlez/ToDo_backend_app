# ToDo Backend

Backend REST para una aplicacion ToDo desarrollado con Java 21 y Quarkus. Expone endpoints para administrar usuarios, listas y tareas, valida autenticacion con Firebase Admin SDK y persiste la informacion en MySQL mediante Hibernate ORM Panache.

La base de datos del proyecto esta desplegada en Railway y el backend esta preparado para despliegue en Render mediante Docker.

## Descripcion del backend

Este servicio centraliza la logica de negocio de la aplicacion ToDo. Recibe peticiones HTTP, valida datos de entrada con DTOs, ejecuta casos de uso, accede a repositorios de persistencia y devuelve respuestas JSON para el cliente.

La autenticacion se valida con Firebase. Los endpoints protegidos esperan un token de Firebase en el encabezado `Authorization` con el formato `Bearer <token>`.

## Funcionalidades principales

- API REST para usuarios, listas y tareas.
- Autenticacion mediante Firebase Admin SDK.
- Registro, consulta, actualizacion y eliminacion de usuarios.
- CRUD de listas de tareas.
- CRUD de tareas.
- Cambio de estado de tareas completadas.
- Busqueda de contenido.
- Catalogos de colores y categorias.
- Manejo centralizado de errores y validaciones.
- Persistencia en MySQL.
- Configuracion de CORS mediante variable de entorno.

## Arquitectura del proyecto

El codigo esta organizado por responsabilidades para separar dominio, casos de uso, infraestructura y entrada HTTP:

```text
src/main/java/com/itesm
|-- domain
|-- application
|-- infrastructure
`-- interfaces/rest

src/main/resources
`-- application.properties
```

- `domain`: contiene modelos de dominio y contratos de repositorios.
- `application`: contiene DTOs, servicios de aplicacion, contexto de usuario autenticado y casos de uso.
- `infrastructure`: contiene implementaciones tecnicas como Firebase, mappers, entidades JPA y repositorios con Panache.
- `interfaces/rest`: contiene recursos REST y mappers de excepciones HTTP.
- `src/main/resources/application.properties`: define configuracion de Quarkus, base de datos, CORS, Firebase y perfil de pruebas.

## Tecnologias utilizadas

- Java 21.
- Quarkus 3.31.3.
- Maven Wrapper.
- Quarkus ARC.
- RESTEasy JSON-B.
- Hibernate ORM Panache.
- JDBC MySQL.
- Hibernate Validator.
- Firebase Admin SDK 9.2.0.
- MySQL como base de datos principal.
- H2 para pruebas.
- JUnit y Rest Assured para pruebas.
- Docker para despliegue.
- Railway para base de datos.
- Render para despliegue del backend.

## Variables de entorno necesarias

El backend toma su configuracion desde variables de entorno. Puedes usar como referencia el archivo [.env_example](.env_example).

```env
DB_USERNAME=root
DB_PASSWORD=your_database_password
DB_URL=jdbc:mysql://HOST:PORT/railway

CORS_ORIGINS=http://localhost:8081,http://localhost:19006,http://10.0.2.2:8080

FIREBASE_SERVICE_ACCOUNT=/path/to/firebase-service-account.json
```

Descripcion de variables:

- `DB_USERNAME`: usuario de la base de datos MySQL.
- `DB_PASSWORD`: contrasena de la base de datos MySQL.
- `DB_URL`: URL JDBC de conexion a MySQL.
- `CORS_ORIGINS`: origenes permitidos para consumir la API desde frontend.
- `FIREBASE_SERVICE_ACCOUNT`: ruta al archivo JSON de cuenta de servicio de Firebase Admin SDK.

## Configuracion

La configuracion principal se encuentra en:

```text
src/main/resources/application.properties
```

Este archivo configura:

- Conexion a MySQL usando `DB_USERNAME`, `DB_PASSWORD` y `DB_URL`.
- CORS usando `CORS_ORIGINS`.
- Estrategia de schema de Hibernate ORM.
- Ruta de credenciales de Firebase con `FIREBASE_SERVICE_ACCOUNT`.
- Perfil `%test` con H2 en memoria para pruebas.

## Endpoints principales

### Estado

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `GET` | `/status` | Verifica el estado del servicio. |

### Usuarios

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `POST` | `/user` | Registra un usuario. |
| `GET` | `/user/profile` | Obtiene el perfil del usuario autenticado. |
| `GET` | `/user` | Lista usuarios. |
| `GET` | `/user/{id}` | Obtiene un usuario por ID. |
| `PUT` | `/user/{id}` | Actualiza un usuario. |
| `DELETE` | `/user/{id}` | Elimina un usuario. |

### Listas

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `POST` | `/lists` | Crea una lista. |
| `GET` | `/lists/graph` | Obtiene listas para vista tipo grafica. |
| `GET` | `/lists/{id}` | Obtiene una lista por ID. |
| `GET` | `/lists/{id}/todos` | Obtiene las tareas de una lista. |
| `PUT` | `/lists/{id}` | Actualiza una lista. |
| `DELETE` | `/lists/{id}` | Elimina una lista. |

### Tareas

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `POST` | `/todo` | Crea una tarea. |
| `GET` | `/todo/graph` | Obtiene tareas para vista tipo grafica. |
| `GET` | `/todo/{id}` | Obtiene una tarea por ID. |
| `PUT` | `/todo/{id}` | Actualiza una tarea. |
| `DELETE` | `/todo/{id}` | Elimina una tarea. |
| `PATCH` | `/todo/{id}/completed` | Cambia el estado de completado de una tarea. |

### Busqueda y catalogos

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `GET` | `/search` | Ejecuta busqueda de contenido. |
| `GET` | `/colors` | Lista colores disponibles. |
| `GET` | `/categories` | Lista categorias disponibles. |

## Ejecucion local

Requisitos:

- Java 21.
- Acceso a una base de datos MySQL.
- Variables de entorno configuradas.
- Archivo de cuenta de servicio de Firebase disponible localmente.

Ejecutar en modo desarrollo:

```shell
./mvnw quarkus:dev
```

En Windows tambien puedes usar:

```shell
mvnw.cmd quarkus:dev
```

La API queda disponible por defecto en:

```text
http://localhost:8080
```

## Pruebas

Ejecutar pruebas con Maven Wrapper:

```shell
./mvnw test
```

El perfil de pruebas usa H2 en memoria y no requiere la conexion MySQL de produccion.

## Empaquetado

Generar el paquete de la aplicacion:

```shell
./mvnw package
```

El artefacto ejecutable de Quarkus queda en:

```text
target/quarkus-app/quarkus-run.jar
```

Ejecutar el paquete:

```shell
java -jar target/quarkus-app/quarkus-run.jar
```

## Docker

El proyecto incluye un `Dockerfile` en la raiz para construir una imagen con Java 21:

```shell
docker build -t todo-backend .
```

Ejecutar el contenedor:

```shell
docker run --env-file .env -p 8080:8080 todo-backend
```

## Despliegue

- Base de datos: MySQL desplegado en Railway.
- Backend: Quarkus desplegado en Render.
- Configuracion: las variables de entorno deben configurarse en el panel del proveedor de despliegue.
- Firebase: la cuenta de servicio debe montarse o estar disponible en la ruta indicada por `FIREBASE_SERVICE_ACCOUNT`.

## Notas de seguridad

- No subir archivos `.env` con credenciales reales.
- No exponer el JSON de cuenta de servicio de Firebase en repositorios publicos.
- Mantener `CORS_ORIGINS` limitado a los dominios reales del frontend.
