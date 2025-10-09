# Documentación del Proyecto: Clínica Veterinaria (Petcare)

## Prompt para IA: Análisis y Comprensión del Proyecto "Petcare"

**Asunto:** Documentación exhaustiva para la comprensión de la arquitectura y funcionalidad del proyecto "Petcare", una aplicación de gestión para clínicas veterinarias.

**Rol de la IA:** Actúa como un experto en arquitectura de software, desarrollo Java con Spring Boot, bases de datos relacionales (MySQL) y seguridad web. Tu objetivo es comprender a fondo este proyecto para poder asistir en su desarrollo, mantenimiento o extensión.

**Contexto General del Proyecto:**
"Petcare" es una aplicación web diseñada para la gestión integral de una clínica veterinaria. Su propósito principal es facilitar el registro y autenticación de propietarios de mascotas, y en futuras fases, la gestión de sus mascotas y la programación de citas. El proyecto sigue principios de diseño modular y una arquitectura en capas para asegurar escalabilidad, mantenibilidad y facilidad de prueba.

**Tecnologías Clave Utilizadas:**
*   **Backend Framework:** Spring Boot 3.3.4 (Java 21)
*   **Base de Datos:** MySQL
*   **ORM (Object-Relational Mapping):** Spring Data JPA (con Hibernate)
*   **Seguridad:** Spring Security (para autenticación y autorización)
*   **Mapeo de Objetos:** MapStruct (para conversión entre Entidades y DTOs)
*   **Motor de Plantillas (Frontend):** Thymeleaf (para renderizado de vistas del lado del servidor)
*   **Gestor de Dependencias:** Apache Maven
*   **Estilos Frontend:** Bootstrap 5 (implícito a través de las plantillas HTML)

**Arquitectura del Proyecto (N-Capas):**
El proyecto está estructurado en tres capas principales, cada una con responsabilidades bien definidas:

1.  **Capa de Dominio (`com.veterinaria.demo.domain`):**
    *   **Propósito:** Contiene la lógica de negocio central y las reglas del dominio. Es independiente de la capa de persistencia y presentación.
    *   **Sub-paquetes:**
        *   `dto` (Data Transfer Objects): Clases como `MascotaDTO` y `PropietarioDTO` que se utilizan para transferir datos de forma segura y eficiente entre las capas, evitando exponer las entidades directamente.
        *   `mapper`: Interfaces (ej. `MascotaMapper`, `PropietarioMapper`) que definen cómo se deben convertir las entidades a DTOs y viceversa. MapStruct genera automáticamente las implementaciones en tiempo de compilación.
        *   `repository`: Interfaces que extienden `JpaRepository` (o `CrudRepository`) de Spring Data JPA. Definen los contratos para las operaciones de acceso a datos, permitiendo la abstracción de la base de datos.
        *   `service`: Clases que implementan la lógica de negocio específica. Ejemplos incluyen `MascotaService`, `PropietarioService` (para operaciones CRUD y reglas de negocio sobre mascotas/propietarios) y `UserDetailsServiceImp` (esencial para la autenticación de usuarios con Spring Security).

2.  **Capa de Persistencia (`com.veterinaria.demo.persistence`):**
    *   **Propósito:** Gestiona la interacción directa con la base de datos.
    *   **Sub-paquetes:**
        *   `entity`: Clases POJO (Plain Old Java Object) que representan las tablas de la base de datos. Están anotadas con `@Entity` y `@Table` de JPA. Ejemplos: `Mascota.java`, `Propietario.java`.
        *   `crud`: Interfaces que extienden `JpaRepository` (o `CrudRepository`) de Spring Data JPA. Estas interfaces proporcionan métodos predefinidos para operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y permiten definir consultas personalizadas. Ejemplos: `MascotaCrudRepository`, `PropietarioCrudRepository`.

3.  **Capa de Presentación (`com.veterinaria.demo.web`):**
    *   **Propósito:** Expone la funcionalidad de la aplicación a los usuarios a través de una interfaz web y, potencialmente, una API REST.
    *   **Sub-paquetes:**
        *   `config`: Contiene clases de configuración relacionadas con la capa web, siendo `SecurityConfig.java` la más importante, donde se definen las reglas de seguridad, la configuración de autenticación y autorización, y el manejo de contraseñas.
        *   `controller`: Clases anotadas con `@Controller` o `@RestController` que manejan las solicitudes HTTP entrantes.
            *   `AuthController`: Gestiona las operaciones de registro e inicio de sesión de usuarios.
            *   `MascotaController`: Maneja las solicitudes relacionadas con la gestión de mascotas (crear, ver, editar, eliminar).
            *   `PropietarioController`: Maneja las solicitudes relacionadas con la gestión de propietarios.
            *   `ViewController`: Sirve las plantillas HTML (ej. `login.html`, `register.html`, `home.html`, `mis-mascotas.html`, `mascota-form.html`, `cuenta.html`).

**Entidades Clave y sus Relaciones:**
*   **`Propietario`:**
    *   **Descripción:** Representa a un dueño de mascota. Es una entidad JPA que mapea la tabla `propietarios`.
    *   **Atributos:** `idPropietario` (PK), `nombre`, `apellido`, `telefono`, `direccion`, `email` (UNIQUE), `password`.
    *   **Relaciones:** `@OneToMany` con `Mascota` (un propietario puede tener muchas mascotas).
*   **`Mascota`:**
    *   **Descripción:** Representa una mascota. Es una entidad JPA que mapea la tabla `mascotas`.
    *   **Atributos:** `idMascota` (PK), `nombre`, `especie`, `raza`, `fechaNacimiento`, `idPropietario` (FK).
    *   **Relaciones:** `@ManyToOne` con `Propietario` (muchas mascotas pertenecen a un propietario).

**Requerimientos Funcionales Principales (Implementados y Futuros):**
*   **Autenticación y Autorización:**
    *   Registro de nuevos propietarios (usuarios).
    *   Inicio de sesión seguro para usuarios registrados.
    *   Protección de rutas y recursos mediante Spring Security.
*   **Gestión de Propietarios:**
    *   Creación y actualización de perfiles de propietario.
*   **Gestión de Mascotas:**
    *   Registro de nuevas mascotas asociadas a un propietario.
    *   Visualización, edición y eliminación de mascotas.
*   **Gestión de Citas/Reservas:** (Funcionalidad planificada para futuras iteraciones)
    *   Programación y gestión de citas veterinarias.

**Configuración de la Base de Datos (`application.properties`):**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/market?useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update # O 'create', 'create-drop', 'none'
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

**Esquema de Base de Datos (SQL):**
```sql
CREATE DATABASE IF NOT EXISTS market;
USE market;

CREATE TABLE IF NOT EXISTS Propietarios (
    id_propietario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(100),
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Mascotas (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    especie VARCHAR(50),
    raza VARCHAR(50),
    fecha_nacimiento DATE,
    id_propietario INT,
    FOREIGN KEY (id_propietario) REFERENCES Propietarios(id_propietario)
);
```

---

## Estructura de Directorios y Componentes (Diagrama Simplificado)

```
ClinicaVeterinaria/
└── proyecto/
    └── demo/
        └── src/
            └── main/
                └── java/
                    └── com/
                        └── veterinaria/
                            └── demo/
                                ├── PetcareApplication.java (Entry Point)
                                ├── domain/
                                │   ├── dto/
                                │   │   ├── MascotaDTO.java
                                │   │   └── PropietarioDTO.java
                                │   ├── mapper/
                                │   │   ├── MascotaMapper.java
                                │   │   └── PropietarioMapper.java
                                │   ├── repository/
                                │   │   └── (Interfaces de repositorio)
                                │   └── service/
                                │       ├── MascotaService.java
                                │       ├── MascotaServiceImpl.java
                                │       ├── PropietarioService.java
                                │       ├── PropietarioServiceImpl.java
                                │       └── UserDetailsServiceImp.java
                                ├── persistence/
                                │   ├── crud/
                                │   │   ├── MascotaCrudRepository.java
                                │   │   └── PropietarioCrudRepository.java
                                │   └── entity/
                                │       ├── Mascota.java
                                │       └── Propietario.java
                                └── web/
                                    ├── config/
                                    │   └── SecurityConfig.java
                                    └── controller/
                                        ├── AuthController.java
                                        ├── MascotaController.java
                                        ├── PropietarioController.java
                                        └── ViewController.java
                └── resources/
                    ├── application.properties (Configuración)
                    └── templates/
                        ├── cuenta.html
                        ├── home.html
                        ├── login.html
                        ├── mascota-form.html
                        ├── mis-mascotas.html
                        └── register.html
```
