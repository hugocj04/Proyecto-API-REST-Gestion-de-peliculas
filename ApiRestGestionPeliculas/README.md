🎬 API de Gestión de Películas
Una API REST completa desarrollada con Spring Boot para gestionar un catálogo de películas, directores y actores. Permite crear, listar, actualizar y eliminar películas, así como gestionar las relaciones entre directores y su reparto de actores.

🚀 Inicio Rápido
Prerrequisitos
Java 17 o superior instalado

Maven 3.6+

Postman (recomendado para pruebas)

Ejecución
bash
# Clonar el proyecto (si está en repositorio)
git clone url-del-repositorio

# Compilar y ejecutar
mvn spring-boot:run
Una vez ejecutada, la aplicación estará disponible en el puerto 9000.

📊 URLs de Acceso
🔗 API Principal: http://localhost:9000/api/v1

📚 Documentación Interactiva: http://localhost:9000/swagger-ui.html

🗄️ Consola de Base de Datos: http://localhost:9000/h2-console

📖 Especificación OpenAPI: http://localhost:9000/api-docs

🎯 Características Principales
Gestión Completa de Entidades
CRUD completo para Directores y Películas

CRUD básico para Actores (crear y listar)

Relaciones Many-to-Many entre Películas y Actores

Validaciones de negocio integradas

Validaciones Implementadas
Títulos de película únicos

Edad mínima del director (18 años al estrenar película)

Prevención de asignaciones duplicadas de actores

Manejo de entidades no encontradas

Calidad del Código
Arquitectura en capas (Controller-Service-Repository)

Patrón DTO para transferencia de datos

Manejo centralizado de excepciones

Documentación completa con OpenAPI 3.0

📋 Endpoints Disponibles
🎬 Gestión de Directores
Método	Endpoint	Descripción	Códigos de Respuesta
GET	/directores	Obtener todos los directores	200
GET	/directores/{id}	Obtener director específico	200, 404
POST	/directores	Crear nuevo director	201, 400
PUT	/directores/{id}	Actualizar director existente	200, 404, 400
DELETE	/directores/{id}	Eliminar director	204, 404
🎭 Gestión de Actores
Método	Endpoint	Descripción	Códigos de Respuesta
GET	/actores	Listar todos los actores	200
POST	/actores	Registrar nuevo actor	201, 400
📽️ Gestión de Películas
Método	Endpoint	Descripción	Códigos de Respuesta
GET	/peliculas	Listar todas las películas	200
GET	/peliculas/{id}	Obtener película con reparto completo	200, 404
POST	/peliculas	Crear nueva película	201, 400, 404, 409
PUT	/peliculas/{id}	Actualizar película existente	200, 400, 404, 409
DELETE	/peliculas/{id}	Eliminar película	204, 404
🔗 Asignaciones
Método	Endpoint	Descripción	Códigos de Respuesta
POST	/peliculas/{id}/actores/{actorId}	Asignar actor al reparto	200, 404, 409
💾 Configuración de Base de Datos
La aplicación utiliza H2 Database en modo memoria, lo que significa:

Características
✅ No requiere instalación adicional

✅ Inicio rápido sin configuración

✅ Ideal para desarrollo y pruebas

✅ Se reinicia limpia en cada ejecución

Acceso a la Consola H2
URL: http://localhost:9000/h2-console

JDBC URL: jdbc:h2:mem:testdb

Usuario: sa

Contraseña: (dejar vacío)

⚠️ Importante: Los datos se pierden al detener la aplicación, ya que la base de datos reside completamente en memoria.

🛠 Stack Tecnológico
Backend
Java 17 - Lenguaje de programación

Spring Boot 3.x - Framework principal

Spring Data JPA - Acceso a datos

H2 Database - Base de datos en memoria

Herramientas de Desarrollo
Maven - Gestión de dependencias

Lombok - Reducción de código boilerplate

OpenAPI 3.0 + Swagger UI - Documentación automática

Calidad de Código
DTO Pattern - Separación de concerns

Global Exception Handling - Manejo centralizado de errores

RESTful Principles - Diseño de APIs conforme a estándares

📁 Estructura del Proyecto
text
gestion-peliculas/
├── src/main/java/com/gestionpeliculas/
│   ├── controller/      # Controladores REST
│   ├── service/         # Lógica de negocio
│   ├── repository/      # Acceso a datos
│   ├── model/          # Entidades JPA
│   ├── dto/            # Objetos de transferencia
│   └── exceptions/     # Excepciones personalizadas
├── postman/            # Colección de pruebas
│   └── Gestion de peliculas API.postman_collection.json
├── src/main/resources/
│   └── application.properties
└── README.md
🧪 Guía de Pruebas
Flujo Recomendado de Pruebas
Configuración Inicial

bash
mvn spring-boot:run
Crear Datos Base

Crear al menos 2 directores

Crear 3-4 actores

Crear 1-2 películas

Probar Funcionalidades Principales

Asignar actores a películas

Consultar películas con reparto completo

Probar actualizaciones

Validar Casos de Error

Títulos duplicados

Asignaciones repetidas

Directores menores de edad

Ejemplos de Uso
Crear Director:

bash
POST http://localhost:9000/api/v1/directores
Content-Type: application/json

{
  "nombre": "Christopher Nolan",
  "anioNacimiento": 1970
}
Crear Película:

bash
POST http://localhost:9000/api/v1/peliculas
Content-Type: application/json

{
  "titulo": "Inception",
  "genero": "Ciencia Ficción",
  "fechaEstreno": "2010-07-16",
  "directorId": 1
}
🚫 Gestión de Errores
La API utiliza el estándar ProblemDetail para respuestas de error consistentes:

Códigos de Error Comunes
400 Bad Request - Datos de entrada inválidos

404 Not Found - Recurso no encontrado

409 Conflict - Violación de reglas de negocio

Ejemplos de Respuestas de Error
Película No Encontrada:

json
{
  "type": "about:blank",
  "title": "Entidad No Encontrada",
  "status": 404,
  "detail": "Película no encontrada",
  "instance": "/api/v1/peliculas/999"
}
Título Duplicado:

json
{
  "type": "about:blank",
  "title": "Película Ya Existe",
  "status": 409,
  "detail": "Ya existe una película con ese título",
  "instance": "/api/v1/peliculas"
}

🔧 Configuración Avanzada
application.properties
properties
spring.application.name=ApiRestGestionPeliculas
server.port=9000

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true