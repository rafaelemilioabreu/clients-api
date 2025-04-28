# API REST de Clientes y Direcciones - OrionTek

Este proyecto es una API REST desarrollada con Java, Jersey y PostgreSQL que permite gestionar clientes y sus direcciones. La API ofrece endpoints para realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) tanto para clientes como para direcciones.

## Requisitos previos

- Java JDK 8 o superior
- PostgreSQL 10 o superior
- Eclipse IDE para Java EE Developers
- Apache Tomcat 9.x
- Maven (integrado en Eclipse o instalado por separado)

## Instalación

### 1. Configurar la base de datos

1. Abre pgAdmin o cualquier herramienta de administración de PostgreSQL
2. Crea una nueva base de datos llamada `orion_client`
3. Ejecuta el siguiente script SQL para crear las tablas necesarias.


### 2. Importar el proyecto en Eclipse

1. Abre Eclipse IDE
2. Selecciona File → Import → Maven → Existing Maven Projects
3. Navega hasta la carpeta del proyecto y selecciónala
4. Haz clic en "Finish"

### 3. Configurar la conexión a la base de datos

1. Abre el archivo `src/main/java/com/oriontek/config/DatabaseConnection.java`
2. Modifica las siguientes líneas con tus credenciales de PostgreSQL:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/orion_client";
private static final String USER = "tu_usuario"; // Reemplaza con tu usuario de PostgreSQL
private static final String PASSWORD = "tu_contraseña"; // Reemplaza con tu contraseña
```

### 4. Configurar Tomcat en Eclipse

1. Abre la vista "Servers" en Eclipse (Window → Show View → Servers)
2. Haz clic en "No servers are available..." o haz clic derecho → New → Server
3. Selecciona "Apache" → "Tomcat v9.0 Server"
4. Haz clic en "Next"
5. Haz clic en "Browse" y selecciona la carpeta donde instalaste Tomcat
6. Haz clic en "Finish"

### 5. Desplegar la aplicación

1. Haz clic derecho en el servidor Tomcat → "Add and Remove..."
2. Selecciona el proyecto "clients-api" y añádelo al servidor
3. Haz clic en "Finish"
4. Haz clic derecho en el servidor Tomcat → "Start"

## Uso de la API

La API estará disponible en la URL base: `http://localhost:8080/clients-api/api/`

### Endpoints de Clientes

| Método | URL | Descripción |
|--------|-----|-------------|
| GET    | `/customers` | Obtener todos los clientes |
| GET    | `/customers/{id}` | Obtener un cliente por ID |
| POST   | `/customers` | Crear un nuevo cliente |
| PUT    | `/customers/{id}` | Actualizar un cliente existente |
| DELETE | `/customers/{id}` | Eliminar un cliente |

### Endpoints de Direcciones

| Método | URL | Descripción |
|--------|-----|-------------|
| GET    | `/addresses` | Obtener todas las direcciones |
| GET    | `/addresses/{id}` | Obtener una dirección por ID |
| GET    | `/addresses/customer/{customerId}` | Obtener direcciones por ID de cliente |
| POST   | `/addresses` | Crear una nueva dirección |
| PUT    | `/addresses/{id}` | Actualizar una dirección existente |
| DELETE | `/addresses/{id}` | Eliminar una dirección |

## Ejemplos de uso

### Crear un cliente nuevo (con dirección)

**Método**: POST  
**URL**: `http://localhost:8080/clients-api/api/customers`  
**Cuerpo** (JSON):
```json
{
  "firstName": "Pedro",
  "lastName": "Martínez",
  "email": "pedro.martinez@ejemplo.com",
  "phone": "809-555-1234",
  "addresses": [
    {
      "address": "Calle Duarte #45, La Vega"
    }
  ]
}
```

### Agregar una dirección a un cliente existente

**Método**: POST  
**URL**: `http://localhost:8080/clients-api/api/addresses`  
**Cuerpo** (JSON):
```json
{
  "customerId": 1,
  "address": "Avenida Independencia #789, Santo Domingo"
}
```

### Obtener todos los clientes con sus direcciones

**Método**: GET  
**URL**: `http://localhost:8080/clients-api/api/customers`

### Obtener todas las direcciones de un cliente específico

**Método**: GET  
**URL**: `http://localhost:8080/clients-api/api/addresses/customer/1`

