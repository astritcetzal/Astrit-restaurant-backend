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

1. **Persistencia Maestro-Detalle (Cascade):** Se configuro la relacion entre `Pedido` y `DetallePedido`. Se utilizo una llave primaria compuesta (`@EmbeddedId` y `@MapsId`) para que, al enviar un JSON con el pedido y sus platillos, JPA guardara el encabezado y todos sus detalles automaticamente en la base de datos mediante una sola transaccion (CascadeType.ALL).
2. **Seguridad Stateless:** Se configuro el filtro de seguridad de Spring Security (`JwtFilter`) para interceptar peticiones. Esto permite rechazar el acceso (HTTP 403 Forbidden) a usuarios no autenticados y validar el token JWT en la cabecera de las peticiones autorizadas.
3. **Documentacion Precisa:** Se refinaron los esquemas de Swagger (OpenAPI) para asegurar que los objetos JSON de ejemplo validaran correctamente los tipos de datos y los codigos semanticos HTTP (200, 201, 403).

## Script de Base de Datos (DDL)
A continuacion, se presenta el script SQL utilizado para la creacion de la estructura relacional en PostgreSQL:

```sql
CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL,
    estado BOOLEAN NOT NULL
);

CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    id_categoria INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE mesa (
    id_mesa SERIAL PRIMARY KEY,
    numero_mesa INT NOT NULL,
    ubicacion VARCHAR(50) NOT NULL,
    estado BOOLEAN NOT NULL,
    asientos INT NOT NULL
);

CREATE TABLE pedido (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_mesa INT NOT NULL,
    metodo_pago CHAR(1) NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT fk_mesa FOREIGN KEY (id_mesa) REFERENCES mesa(id_mesa)
);

CREATE TABLE detalle_pedido (
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_pedido, id_producto),
    CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    CONSTRAINT fk_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);


````
-- ============================================================================
-- 1. LIMPIEZA DE TABLAS (Orden inverso a las dependencias FK)
-- ============================================================================
DROP TABLE IF EXISTS detalle_pedido CASCADE;
DROP TABLE IF EXISTS pedido CASCADE;
DROP TABLE IF EXISTS producto CASCADE;
DROP TABLE IF EXISTS categoria CASCADE;
DROP TABLE IF EXISTS mesa CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;

-- ============================================================================
-- 2. CREACIÓN DE ESTRUCTURA
-- ============================================================================
CREATE TABLE categoria (
id_categoria SERIAL PRIMARY KEY,
descripcion VARCHAR(50) NOT NULL,
estado BOOLEAN NOT NULL
);

CREATE TABLE producto (
id_producto SERIAL PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
id_categoria INT NOT NULL,
precio DECIMAL(10,2) NOT NULL,
CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE cliente (
id_cliente SERIAL PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE mesa (
id_mesa SERIAL PRIMARY KEY,
numero_mesa INT NOT NULL,
ubicacion VARCHAR(50) NOT NULL,
estado BOOLEAN NOT NULL,
asientos INT NOT NULL
);

CREATE TABLE pedido (
id_pedido SERIAL PRIMARY KEY,
id_cliente INT NOT NULL,
id_mesa INT NOT NULL,
metodo_pago CHAR(1) NOT NULL,
CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
CONSTRAINT fk_mesa FOREIGN KEY (id_mesa) REFERENCES mesa(id_mesa)
);

CREATE TABLE detalle_pedido (
id_pedido INT NOT NULL,
id_producto INT NOT NULL,
cantidad INT NOT NULL,
total DECIMAL(10,2) NOT NULL,
PRIMARY KEY (id_pedido, id_producto),
CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
CONSTRAINT fk_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- ============================================================================
-- 3. POBLADO DE CATEGORÍAS (IDs 1 al 7 autogenerados en orden)
-- ============================================================================
INSERT INTO categoria (descripcion, estado) VALUES
('Entradas y Botanas', TRUE),              -- id_categoria = 1
('Sopas y Caldos', TRUE),                  -- id_categoria = 2
('Tacos y Antojitos', TRUE),               -- id_categoria = 3
('Platos Fuertes', TRUE),                  -- id_categoria = 4
('Aguas Frescas', TRUE),                   -- id_categoria = 5
('Bebidas Calientes', TRUE),               -- id_categoria = 6
('Refrescos y Embotelladas', TRUE);        -- id_categoria = 7

-- ============================================================================
-- 4. POBLADO DE PRODUCTOS (21 platillos y bebidas, todos <= 50 caracteres)
-- ============================================================================
INSERT INTO producto (nombre, id_categoria, precio) VALUES
-- 1. Entradas y Botanas
('Guacamole tradicional', 1, 95.00),
('Queso fundido con chorizo', 1, 115.00),
('Sopesitos surtidos (3 pzas)', 1, 85.00),

-- 2. Sopas y Caldos
('Sopa de tortilla (Azteca)', 2, 90.00),
('Pozole rojo tradicional', 2, 130.00),
('Caldo tlalpeño', 2, 110.00),

-- 3. Tacos y Antojitos
('Orden de tacos al pastor', 3, 105.00),
('Enchiladas suizas', 3, 125.00),
('Chilaquiles tradicionales', 3, 110.00),

-- 4. Platos Fuertes
('Mole poblano con pollo', 4, 155.00),
('Cochinita pibil', 4, 145.00),
('Arrachera a la parrilla', 4, 230.00),

-- 5. Aguas Frescas
('Agua de horchata (500 ml)', 5, 40.00),
('Agua de jamaica (500 ml)', 5, 40.00),
('Agua de tamarindo (500 ml)', 5, 40.00),

-- 6. Bebidas Calientes
('Café de olla', 6, 45.00),
('Chocolate caliente mexicano', 6, 55.00),
('Champurrado tradicional', 6, 50.00),

-- 7. Refrescos y Embotelladas
('Refresco tradicional en vidrio', 7, 38.00),
('Refresco comercial', 7, 35.00),
('Agua mineral preparada', 7, 45.00);

-- ============================================================================
-- 5. DATOS BASE PARA MESAS (Útil si conectas TableController)
-- ============================================================================
INSERT INTO mesa (numero_mesa, ubicacion, estado, asientos) VALUES
(1, 'Terraza Principal', TRUE, 4),
(2, 'Salón Interior', TRUE, 2),
(3, 'Área Familiar', TRUE, 6),
(4, 'Barra Central', TRUE, 2);
````