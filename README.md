# Astrit Restaurant Backend API

## Descripcion del Proyecto
Este proyecto consiste en una API RESTful desarrollada para la gestion integral del backend de un restaurante. El sistema permite administrar el catalogo de productos, el estado y ubicacion de las mesas, la informacion de los clientes y el registro transaccional de los pedidos.

La aplicacion garantiza la integridad de los datos mediante persistencia relacional y expone endpoints seguros protegidos mediante autenticacion basada en tokens, ademas de contar con una documentacion interactiva para facilitar el consumo de los servicios.

## Herramientas y Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-000000?style=for-the-badge&logo=java&logoColor=white)

## Arquitectura
El proyecto esta estructurado bajo una Arquitectura en Capas Orientada al Dominio (Domain-Driven Design). Esta division garantiza la escalabilidad, el mantenimiento y la separacion de responsabilidades:

* **Capa Web (Controllers):** Expone los endpoints REST, maneja las peticiones HTTP y documenta los esquemas de entrada y salida con Swagger.
* **Capa de Dominio (Domain):** Contiene las reglas de negocio, los modelos limpios y las interfaces de los repositorios. Es el nucleo de la aplicacion y es completamente agnostica a la base de datos o frameworks de persistencia.
* **Capa de Persistencia (Persistence):** Se encarga de la comunicacion con PostgreSQL utilizando Spring Data JPA (Entidades, CrudRepositories) y utiliza MapStruct para traducir entre Entidades y modelos de Dominio.

## Generacion de Hash para Contrasenas
Para inicializar la base de datos con registros de clientes que cumplieran con los estandares de seguridad, las contrasenas no se guardaron en texto plano.

Se implemento una tecnica de inicializacion directamente en la clase principal `AstritRestaurantBackendApplication`. Se inyecto un `BCryptPasswordEncoder` y se utilizo `System.out.println()` al arrancar la aplicacion para generar el hash de una contrasena de prueba. El hash resultante (ej. `$2a$10$...`) se copio desde la consola y se inserto manualmente en el script SQL de la base de datos. Esto permitio probar la validacion de Spring Security en el endpoint de Login de manera segura.

```java
@SpringBootApplication
public class AstritRestaurantBackendApplication implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(AstritRestaurantBackendApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        // Generacion de hash para prueba de seguridad
        String contrasenaPlana = "1234";
        String contrasenaEncriptada = passwordEncoder.encode(contrasenaPlana);
        System.out.println("Hash generado para insertar en SQL: " + contrasenaEncriptada);
    }
}
````
