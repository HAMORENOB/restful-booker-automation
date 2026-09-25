# RESTful Booker API Automatio*

Framework de automatización de p*uebas para la API pública
RESTful-*ooker, desarrollado con Java 21, M*ven, REST Assured,
JUnit 5, Jackso*, Lombok y Allure.

## Objetivo

A*tomatizar escenarios de autenticac*ón, creación, consulta,
filtrado, *ctualización, validación contractu*l y manejo
negativo sobre la API R*STful-Booker.

## Tecnologías

- J*va 21
- Maven
- REST Assured 6
- J*nit 5
- Jackson Databind
- Lombok
* JSON Schema Validator
- Allure Re*ort

## Arquitectura

El proyecto *tiliza separación por responsabili*ades:

- `config`: configuración r*utilizable y selección de ambiente*
- `models`: contratos Java para s*licitudes y respuestas.
- `service*`: consumo de endpoints mediante R*ST Assured.
- `filters`: autentica*ión y captura de evidencias para A*lure.
- `utils`: generación de dat*s de prueba.
- `assertions`: valid*ciones reutilizables.
- `tests`: e*cenarios automatizados.
- `schemas*: contratos JSON Schema.

## Requi*itos previos

- Java JDK 21
- Apac*e Maven
- Git
- IntelliJ IDEA o un*IDE compatible

## Verificar insta*aciones

```powershell
java -versi*n
javac -version
mvn -version
git *-version