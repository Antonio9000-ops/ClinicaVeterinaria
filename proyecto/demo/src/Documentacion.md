# Documentación del Proyecto: Clínica Veterinaria

## 1. Resumen del Proyecto
Este proyecto es una aplicación web para la gestión de una clínica veterinaria, desarrollada con Java y el framework Spring Boot. La aplicación permite a los propietarios de mascotas registrarse, iniciar sesión y, en futuras iteraciones, gestionar a sus mascotas y citas.

La arquitectura sigue un patrón de N-Capas para separar responsabilidades, haciendo el código más mantenible, escalable y fácil de probar.

## 2. Tecnologías y Dependencias
- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3
- **Seguridad:** Spring Security
- **Acceso a Datos:** Spring Data JPA (Hibernate)
- **Motor de Plantillas (Frontend):** Thymeleaf
- **Base de Datos:** MySQL
- **Gestión de Dependencias:** Maven

**Librerías Adicionales:**
- Lombok
- mysql-connector-j

## 3. Estructura del Proyecto
```plaintext
.
└── src
    └── main
        ├── java
        │   └── com
        │       └── veterinaria
        │           ├── PetcareApplication.java
        │           ├── domain
        │           │   ├── dto
        │           │   ├── repository
        │           │   └── service
        │           │       └── UserDetailsServiceImp.java
        │           ├── persistence
        │           │   ├── crud
        │           │   │   ├── MascotaCrudRepository.java
        │           │   │   └── PropietarioCrudRepository.java
        │           │   └── entity
        │           │       ├── Mascota.java
        │           │       └── Propietario.java
        │           └── web
        │               ├── config
        │               │   └── SecurityConfig.java
        │               └── controller
        │                   ├── AuthController.java
        │                   ├── MascotaController.java
        │                   └── ViewController.java
        └── resources
            ├── static/
            ├── templates/
            │   ├── home.html
            │   ├── login.html
            │   └── register.html
            └── application.properties
```

## 4. Configuración del Entorno
### 4.1. Base de Datos
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

### 4.2. application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/market?useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## 5. Clases y Archivos Destacados
### Propietario.java
Entidad que representa la tabla propietarios.
```java
@Entity
@Table(name = "propietarios")
@Getter
@Setter
public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietario")
    private Integer idPropietario;

    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String email;
    private String password;

    @OneToMany(mappedBy = "propietario")
    private List<Mascota> mascotas;
}
```

### UserDetailsServiceImp.java
Servicio de autenticación para Spring Security.
```java
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private PropietarioCrudRepository propietarioCrudRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Propietario propietario = propietarioCrudRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + username));

        return User.builder()
                .username(propietario.getEmail())
                .password(propietario.getPassword())
                .roles("USER")
                .build();
    }
}
```

## 6. Flujos Principales
### Flujo de Registro Web
1. El usuario accede a `/register`.
2. Completa el formulario.
3. Los datos se envían a `/auth/register-web`.
4. Se cifra la contraseña y se guarda en la base de datos.
5. Se redirige a `/login?success`.

### Flujo de Login Web
1. El usuario accede a `/login`.
2. Envía sus credenciales.
3. Spring Security valida al usuario.
4. Si es correcto, redirige a `/home`; si no, a `/login?error`.
