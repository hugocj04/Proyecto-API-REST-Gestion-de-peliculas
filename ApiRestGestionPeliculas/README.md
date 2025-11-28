# API de Gestión de Películas

Una API REST completa desarrollada con Spring Boot para gestionar un catálogo de películas, directores y actores.

## Inicio Rápido

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- Postman (recomendado)

### Ejecución
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:9000

URLs de Acceso
API Principal: http://localhost:9000/api/v1

Documentación Swagger: http://localhost:9000/swagger-ui.html

Consola H2: http://localhost:9000/h2-console

Endpoints Disponibles
Directores
GET /api/v1/directores - Listar todos los directores

GET /api/v1/directores/{id} - Obtener director por ID

POST /api/v1/directores - Crear nuevo director

PUT /api/v1/directores/{id} - Actualizar director

DELETE /api/v1/directores/{id} - Eliminar director

Actores
GET /api/v1/actores - Listar todos los actores

POST /api/v1/actores - Crear nuevo actor

Películas
GET /api/v1/peliculas - Listar todas las películas

GET /api/v1/peliculas/{id} - Obtener película con reparto

POST /api/v1/peliculas - Crear nueva película

PUT /api/v1/peliculas/{id} - Actualizar película

DELETE /api/v1/peliculas/{id} - Eliminar película

POST /api/v1/peliculas/{id}/actores/{actorId} - Asignar actor a película

Base de Datos
H2 Database en memoria

JDBC URL: jdbc:h2:mem:testdb

Usuario: sa

Contraseña: (vacía)

Nota: Los datos se pierden al reiniciar la aplicación.

Tecnologías
Java 17

Spring Boot 3.x

Spring Data JPA

H2 Database

OpenAPI 3.0 + Swagger

Maven

Lombok

Estructura del Proyecto
text
gestion-peliculas/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── gestionpeliculas/
│       │           ├── controller/
│       │           ├── service/
│       │           ├── repository/
│       │           ├── model/
│       │           ├── dto/
│       │           └── exceptions/
│       └── resources/
│           └── application.properties
├── postman/
│   └── GestionPeliculas.postman_collection.json
└── README.md
Ejemplos de Uso
Crear Director
bash
POST http://localhost:9000/api/v1/directores
Content-Type: application/json

{
  "nombre": "Christopher Nolan",
  "anioNacimiento": 1970
}
Crear Película
bash
POST http://localhost:9000/api/v1/peliculas
Content-Type: application/json

{
  "titulo": "Inception",
  "genero": "Ciencia Ficción",
  "fechaEstreno": "2010-07-16",
  "directorId": 1
}
Gestión de Errores
La API utiliza ProblemDetail para respuestas de error estandarizadas:

400 Bad Request - Datos inválidos

404 Not Found - Recurso no encontrado

409 Conflict - Violación de reglas de negocio

Ejemplo de Error
json
{
  "type": "about:blank",
  "title": "Entidad No Encontrada",
  "status": 404,
  "detail": "Película no encontrada",
  "instance": "/api/v1/peliculas/999"
}
Colección Postman
Incluida en postman/GestionPeliculas.postman_collection.json

Orden de Pruebas Recomendado
Crear directores

Crear actores

Crear películas

Asignar actores a películas

Probar casos de error

