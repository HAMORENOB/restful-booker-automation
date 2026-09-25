# RESTful Booker API Automation

Framework de automatización de pruebas para la API pública RESTful Booker, desarrollado con Java 21, Maven, REST Assured, JUnit 5, Jackson Databind, Lombok y Allure.

## Objetivo

Automatizar escenarios de autenticación, creación, consulta, filtrado, actualización, validación contractual y manejo de casos negativos sobre la API RESTful Booker.

## Tecnologías

- Java 21
- Maven
- REST Assured 6
- JUnit 5
- Jackson Databind
- Lombok
- JSON Schema Validator
- Allure Report

## Arquitectura

El proyecto utiliza una arquitectura organizada por responsabilidades:

- 'config': configuración reutilizable y selección del ambiente.
- models': contratos Java para solicitudes y respuestas.
- 'services': consumo de endpoints mediante REST Assured.
- 'filters': autenticación y captura de evidencias para Allure.
- 'utils': generación de datos de prueba.
- 'assertions': validaciones reutilizables.
- 'tests': escenarios de prueba automatizados.
- 'schemas': contratos de validación JSON Schema.

## Requisitos previos

- Java JDK 21
- Apache Maven
- Git
- IntelliJ IDEA o un IDE compatible

## Verificar las instalaciones

Ejecuta los siguientes comandos en PowerShell:

java -version
javac -version
mvn -version
git --version

## Autor

Harold Arley Moreno Bermudez