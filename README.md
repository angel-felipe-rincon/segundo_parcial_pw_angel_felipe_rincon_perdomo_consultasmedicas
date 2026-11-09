# Consultas Médicas

Aplicación web para la gestión de consultas médicas, con los roles
ADMINISTRADOR, MÉDICO y PACIENTE.

Autor: Angel Felipe Rincón Perdomo
Curso: Programación Web, Universidad Surcolombiana

## Tecnologías

- Java 21
- Spring Boot 4.1.2 (Maven)
- Spring Security (autenticación y roles)
- Spring Data JPA con PostgreSQL
- JSP (vistas)
- Swagger / OpenAPI (documentación de los servicios web)

## Requisitos previos

- JDK 21
- PostgreSQL en el puerto 5432
- Eclipse (o cualquier IDE) con Maven

## Base de datos

1. Crear una base de datos vacía llamada consultasmedicas.
2. Ajustar spring.datasource.username y spring.datasource.password
   en src/main/resources/application.properties.
3. Las tablas se crean al arrancar la aplicación. Los scripts de creación
   y carga de datos están en database/script.sql.

## Cómo ejecutar

1. Importar el proyecto como proyecto Maven existente.
2. Ejecutar ConsultasMedicasApplication como Spring Boot App.
3. Abrir http://localhost:8080/login.

## Usuarios de prueba

| Rol | Usuario | Clave |
|---|---|---|
| ADMINISTRADOR | admin | admin123 |
| MÉDICO | medico1 | medico123 |
| MÉDICO | medico2 | medico123 |
| PACIENTE | paciente1 | paciente123 |
| PACIENTE | paciente2 | paciente123 |

## Permisos por rol

- ADMINISTRADOR:crea, edita y elimina médicos y consultas.
- MÉDICO: solo ve las consultas asignadas a él.
- PACIENTE: ve sus consultas y actualiza el horario de las suyas.

## Documentación de los servicios (Swagger)

http://localhost:8080/swagger-ui.html