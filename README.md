# API de Gestión de Películas

Una API REST completa desarrollada con Spring Boot para gestionar un catálogo de películas, directores y actores.

## Estructura del Proyecto

```
ApiRestGestionPeliculas/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── gestionpeliculas/
│       │           ├── controller/
│       │           │   ├── ActorController.java
│       │           │   ├── DirectorController.java
│       │           │   └── PeliculaController.java
│       │           ├── service/
│       │           │   ├── ActorService.java
│       │           │   ├── DirectorService.java
│       │           │   └── PeliculaService.java
│       │           ├── repository/
│       │           │   ├── ActorRepository.java
│       │           │   ├── DirectorRepository.java
│       │           │   └── PeliculaRepository.java
│       │           ├── model/
│       │           │   ├── Actor.java
│       │           │   ├── Director.java
│       │           │   └── Pelicula.java
│       │           ├── dto/
│       │           │   ├── ActorRequestDTO.java
│       │           │   ├── ActorResponseDTO.java
│       │           │   ├── ActorSimpleDTO.java
│       │           │   ├── DirectorRequestDTO.java
│       │           │   ├── DirectorResponseDTO.java
│       │           │   ├── DirectorSimpleDTO.java
│       │           │   ├── PeliculaRequestDTO.java
│       │           │   └── PeliculaResponseDTO.java
│       │           ├── exception/
│       │           │   ├── ActorYaEnRepartoException.java
│       │           │   ├── DirectorMenorEdadException.java
│       │           │   ├── EntidadNoEncontradaException.java
│       │           │   ├── GlobalExceptionHandler.java
│       │           │   └── PeliculaYaExisteException.java
│       │           └── ApiRestGestionPeliculasApplication.java
│       └── resources/
│           └── application.properties
├── Gestion de Peliculas API.postman_collection.json
├── pom.xml
└── README.md
```

## Inicio Rápido

### Ejecución
```bash
mvn spring-boot:run
```

## URLs de la Aplicación

- La aplicación estará disponible en: http://localhost:8080

### Rutas de Acceso

- API Principal: http://localhost:8080/api/v1
- Documentación Swagger: http://localhost:8080/swagger-ui.html
- Consola H2: http://localhost:8080/h2-console

---

## Endpoints Disponibles

### Directores
- GET /api/v1/directores - Listar todos los directores
- GET /api/v1/directores/{id} - Obtener director por ID
- POST /api/v1/directores/create - Crear nuevo director
- PUT /api/v1/directores/update/{id} - Actualizar director
- DELETE /api/v1/directores/delete/{id} - Eliminar director

### Actores
- GET /api/v1/actores - Listar todos los actores
- GET /api/v1/actores/{id} - Obtener actor por ID
- POST /api/v1/actores/create - Crear nuevo actor

### Películas
- GET /api/v1/peliculas - Listar todas las películas
- GET /api/v1/peliculas/{id} - Obtener película con reparto
- POST /api/v1/peliculas/create - Crear nueva película
- PUT /api/v1/peliculas/update/{id} - Actualizar película
- DELETE /api/v1/peliculas/delete/{id} - Eliminar película
- POST /api/v1/peliculas/assign/{peliculaId}/actores/{actorId} - Asignar actor a película

---

## Base de Datos

### H2 Database

- JDBC URL: jdbc:h2:mem:testdb
- Usuario: sa
- Contraseña: (vacía)
- Nota: La base de datos se reinicia vacía en cada ejecución.

---

## Ejemplos de Uso

### Crear Director

```bash
POST http://localhost:8080/api/v1/directores/create
Content-Type: application/json

{
  "nombre": "Christopher Nolan",
  "anioNacimiento": 1970
}
```

### Crear Película

```bash
POST http://localhost:8080/api/v1/peliculas/create
Content-Type: application/json

{
  "titulo": "Inception",
  "genero": "Ciencia Ficción",
  "fechaEstreno": "2010-07-16",
  "directorId": 1
}
```

---

## Gestión de Errores

- 400 Bad Request - Datos inválidos
- 404 Not Found - Recurso no encontrado
- 409 Conflict - Violación de reglas de negocio

### Ejemplo

```json
{
  "type": "about:blank",
  "title": "Entidad No Encontrada",
  "status": 404,
  "detail": "Película no encontrada",
  "instance": "/api/v1/peliculas/999"
}
```

---

## Colección Postman

- Incluida en: Gestion de Peliculas API.postman_collection.json

---

## Orden de Pruebas Recomendado

1. Crear directores
2. Crear actores
3. Crear películas
4. Asignar actores a películas
5. Probar casos de error