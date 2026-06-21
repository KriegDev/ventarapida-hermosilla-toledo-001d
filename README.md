# VentaRápida - Sistema de Punto de Venta con Microservicios

VentaRápida es un sistema backend desarrollado con arquitectura de microservicios para gestionar un punto de venta. El proyecto permite administrar productos, categorías, clientes, stock, movimientos de inventario, órdenes de venta, pagos, facturación, proveedores, órdenes de compra, reportes, notificaciones y autenticación mediante token JWT.

El sistema se encuentra organizado en servicios independientes, cada uno con su propia responsabilidad, base de datos y puerto de ejecución. La comunicación externa se realiza principalmente mediante un **API Gateway**, el cual centraliza las peticiones y aplica filtros de seguridad mediante token.

---

## Microservicios del sistema

| N° | Microservicio          | Responsabilidad principal                                         | Puerto |
| -: | ---------------------- | ----------------------------------------------------------------- | ------ |
|  0 | `api-gateway`          | Entrada principal del sistema, enrutamiento y validación de token | `4425` |
|  1 | `service-auth`         | Registro, login y generación de token JWT                         | `4428` |
|  2 | `service-catalogo`     | Gestión de productos y categorías                                 | `4419` |
|  3 | `service-cliente`      | Registro y administración de clientes                             | `4420` |
|  4 | `service-inventario`   | Control de stock y movimientos de inventario                      | `4421` |
|  5 | `service-ventas`       | Gestión de órdenes de venta                                       | `4422` |
|  6 | `service-pagos`        | Métodos de pago, procesamiento y anulación de pagos               | `4423` |
|  7 | `service-facturacion`  | Generación y consulta de facturas                                 | `4426` |
|  8 | `service_proveedores`  | Proveedores, órdenes de compra e ítems de compra                  | `4427` |
|  9 | `service-reportes`     | Generación de reportes consolidados                               | `4429` |
| 10 | `service-notificacion` | Gestión de notificaciones                                         | `4430` |

---

## Bibliotecas utilizadas

### Backend y estructura base

* **Spring Boot**: framework principal para construir cada microservicio.
* **Spring Web / Spring Web MVC**: exposición de endpoints REST.
* **Spring WebFlux**: uso de `WebClient` para comunicación entre microservicios.
* **Spring Cloud Gateway**: implementación del API Gateway.
* **Spring Cloud Netflix Eureka Client**: dependencia incluida para descubrimiento de servicios.

### Persistencia y base de datos

* **Spring Data JPA**: manejo de repositorios y operaciones CRUD.
* **Hibernate**: ORM utilizado para mapear entidades Java a tablas.
* **MySQL Connector/J**: driver de conexión hacia bases de datos MySQL.
* **H2 Database**: dependencia de apoyo en algunos servicios para pruebas o ejecución local.

### Seguridad

* **Spring Security**: protección de rutas y configuración de autenticación.
* **JJWT**: generación, firma y validación de tokens JWT.
* **CORS Global**: configuración en el API Gateway para permitir métodos HTTP como `GET`, `POST`, `PUT`, `PATCH`, `DELETE` y `OPTIONS`.

### Validaciones y utilidades

* **Spring Boot Starter Validation**: validaciones con anotaciones como `@NotBlank`, `@NotNull`, `@Size`, `@Min` y `@DecimalMin`.
* **Lombok**: reducción de código repetitivo.
* **Jackson**: serialización y deserialización JSON, incluyendo uso de `@JsonProperty`.

### Documentación

* **SpringDoc OpenAPI WebMVC UI**: documentación Swagger para microservicios tradicionales.
* **SpringDoc OpenAPI WebFlux UI**: documentación Swagger para el API Gateway.

### Testing

* **JUnit 5**: framework de pruebas unitarias.
* **Mockito**: creación de objetos simulados o mocks.
* **Spring Boot Starter Test**: conjunto de herramientas para testing.
* **Reactor Test**: apoyo para pruebas de flujos reactivos.

---

## Herramientas de instalación y ejecución

| Herramienta                                            | Uso                                                 |
| ------------------------------------------------------ | --------------------------------------------------- |
| Java JDK                                               | Ejecutar los microservicios Spring Boot             |
| Maven                                                  | Descargar dependencias, compilar y ejecutar pruebas |
| MySQL Server                                           | Crear y administrar las bases de datos              |
| Postman                                                | Probar endpoints REST                               |
| Git                                                    | Clonar y versionar el proyecto                      |
| Visual Studio Code / IntelliJ IDEA / Spring Tool Suite | Editar y ejecutar el código                         |
| Navegador web                                          | Acceder a Swagger UI                                |

---

## Requisitos previos

Antes de ejecutar el sistema, se debe verificar lo siguiente:

1. Tener instalado **Java JDK 21 o superior**.
2. Tener instalado **Maven**.
3. Tener instalado y ejecutándose **MySQL**.
4. Crear las bases de datos requeridas por cada microservicio.
5. Configurar usuario y contraseña de MySQL en cada archivo `application.properties`.
6. Ejecutar primero los microservicios y luego el API Gateway.
7. Para probar rutas protegidas, iniciar sesión y copiar el token JWT.

---

## Orden recomendado de ejecución

Se recomienda ejecutar los servicios en el siguiente orden:

1. `service-auth`
2. `service-catalogo`
3. `service-cliente`
4. `service-inventario`
5. `service-pagos`
6. `service-ventas`
7. `service-facturacion`
8. `service_proveedores`
9. `service-reportes`
10. `service-notificacion`
11. `api-gateway`

---

## Autenticación con token

El sistema utiliza autenticación basada en **JWT**. Para acceder a la mayoría de endpoints mediante el API Gateway, primero se debe iniciar sesión.

### Registrar usuario

```http
POST http://localhost:4425/api/v1/auth/registrar
```

Body:

```json
{
  "nombreUsuario": "servicios",
  "contrasena": "Servicios123"
}
```

### Login

```http
POST http://localhost:4425/api/v1/auth/login
```

Body:

```json
{
  "nombreUsuario": "servicios",
  "contrasena": "Servicios123"
}
```

La respuesta entrega un token JWT. Este token debe enviarse en las siguientes peticiones protegidas.

Header requerido:

```http
Authorization: Bearer TU_TOKEN_AQUI
```

---

## Ejemplos de rutas para API REST

La URL base recomendada es la del API Gateway:

```text
http://localhost:4425
```

Las rutas protegidas requieren:

```http
Authorization: Bearer TU_TOKEN_AQUI
```

### Servicio de catálogo - Categorías

| Método   | Ruta                      | Descripción                |
| -------- | ------------------------- | -------------------------- |
| `POST`   | `/api/v1/categorias`      | Crea una categoría         |
| `GET`    | `/api/v1/categorias`      | Lista todas las categorías |
| `GET`    | `/api/v1/categorias/{id}` | Busca una categoría por ID |
| `PUT`    | `/api/v1/categorias/{id}` | Actualiza una categoría    |
| `DELETE` | `/api/v1/categorias/{id}` | Elimina una categoría      |

Body para crear categoría:

```json
{
  "nombre": "Bebestibles"
}
```

### Servicio de catálogo - Productos

| Método   | Ruta                            | Descripción                    |
| -------- | ------------------------------- | ------------------------------ |
| `POST`   | `/api/v1/productos`             | Crea un producto               |
| `GET`    | `/api/v1/productos`             | Lista todos los productos      |
| `GET`    | `/api/v1/productos/{id}`        | Busca un producto por ID       |
| `GET`    | `/api/v1/productos/sku/{sku}`   | Busca un producto por SKU      |
| `GET`    | `/api/v1/productos/existe/{id}` | Verifica si existe un producto |
| `PUT`    | `/api/v1/productos/{id}`        | Actualiza un producto          |
| `DELETE` | `/api/v1/productos/{id}`        | Elimina un producto            |

Body para crear producto:

```json
{
  "sku": "BEB-001",
  "nombre": "Coca Cola 1.5L",
  "descripcion": "Bebida gaseosa retornable",
  "precioBase": 1800,
  "granel": false,
  "categoria": {
    "id": 1
  }
}
```

### Servicio de clientes

| Método  | Ruta                                              | Descripción                      |
| ------- | ------------------------------------------------- | -------------------------------- |
| `POST`  | `/api/v1/clientes`                                | Crea un cliente                  |
| `GET`   | `/api/v1/clientes`                                | Lista todos los clientes         |
| `GET`   | `/api/v1/clientes/{id}`                           | Busca un cliente por ID          |
| `GET`   | `/api/v1/clientes/run/{run}`                      | Busca un cliente por RUN         |
| `PATCH` | `/api/v1/clientes/actualizar-datos-contacto/{id}` | Actualiza datos de contacto      |
| `PATCH` | `/api/v1/clientes/actualizar-status/{id}`         | Cambia el estado activo/inactivo |

### Servicio de inventario - Stock

| Método  | Ruta                              | Descripción                       |
| ------- | --------------------------------- | --------------------------------- |
| `GET`   | `/api/v1/stock`                   | Lista el stock                    |
| `GET`   | `/api/v1/stock/{id}`              | Busca stock por ID                |
| `GET`   | `/api/v1/stock/bajo-stock`        | Lista productos bajo stock mínimo |
| `GET`   | `/api/v1/stock/alertas-count`     | Cuenta alertas de bajo stock      |
| `PATCH` | `/api/v1/stock/stock-minimo/{id}` | Actualiza stock mínimo            |

### Servicio de inventario - Movimientos

| Método | Ruta                                                 | Descripción                         |
| ------ | ---------------------------------------------------- | ----------------------------------- |
| `POST` | `/api/v1/movimiento-inventario/nuevo/{id}`           | Crea un movimiento para un producto |
| `GET`  | `/api/v1/movimiento-inventario`                      | Lista movimientos                   |
| `GET`  | `/api/v1/movimiento-inventario/{id}`                 | Busca un movimiento por ID          |
| `GET`  | `/api/v1/movimiento-inventario/buscar/{dia}`         | Busca movimientos por día           |
| `GET`  | `/api/v1/movimiento-inventario/buscar-desde/{fecha}` | Busca movimientos desde una fecha   |
| `GET`  | `/api/v1/movimiento-inventario/entre/{ini}/y/{fin}`  | Busca movimientos entre fechas      |

### Servicio de pagos

| Método   | Ruta                                          | Descripción                       |
| -------- | --------------------------------------------- | --------------------------------- |
| `POST`   | `/api/v1/metodos-pago`                        | Crea un método de pago            |
| `GET`    | `/api/v1/metodos-pago`                        | Lista métodos de pago             |
| `PUT`    | `/api/v1/metodos-pago/{id}`                   | Actualiza método de pago          |
| `PATCH`  | `/api/v1/metodos-pago/actualizar-status/{id}` | Activa o desactiva método de pago |
| `POST`   | `/api/v1/pago/procesar`                       | Procesa un pago                   |
| `GET`    | `/api/v1/pago`                                | Lista pagos                       |
| `GET`    | `/api/v1/pago/confirmar-flow`                 | Confirma pago Flow                |
| `DELETE` | `/api/v1/pago/{id}`                           | Anula un pago                     |

### Servicio de ventas

| Método | Ruta                              | Descripción              |
| ------ | --------------------------------- | ------------------------ |
| `POST` | `/api/v1/ordenes`                 | Crea una orden de venta  |
| `GET`  | `/api/v1/ordenes`                 | Lista órdenes            |
| `GET`  | `/api/v1/ordenes/{id}`            | Busca orden por ID       |
| `GET`  | `/api/v1/ordenes/numero/{nro}`    | Busca orden por número   |
| `GET`  | `/api/v1/ordenes/status/{status}` | Lista órdenes por estado |
| `GET`  | `/api/v1/ordenes/total-recaudado` | Obtiene total recaudado  |

### Servicio de proveedores

| Método  | Ruta                                      | Descripción                   |
| ------- | ----------------------------------------- | ----------------------------- |
| `POST`  | `/api/v1/proveedores`                     | Crea un proveedor             |
| `GET`   | `/api/v1/proveedores`                     | Lista proveedores             |
| `GET`   | `/api/v1/proveedores/{id}`                | Busca proveedor por ID        |
| `PATCH` | `/api/v1/proveedores/{id}`                | Actualiza datos del proveedor |
| `PATCH` | `/api/v1/proveedores/cambiar-status/{id}` | Activa o desactiva proveedor  |

### Servicio de órdenes de compra

| Método  | Ruta                                          | Descripción                  |
| ------- | --------------------------------------------- | ---------------------------- |
| `POST`  | `/api/v1/ordenes-compras`                     | Crea una orden de compra     |
| `GET`   | `/api/v1/ordenes-compras`                     | Lista órdenes de compra      |
| `GET`   | `/api/v1/ordenes-compras/{id}`                | Busca orden de compra por ID |
| `PATCH` | `/api/v1/ordenes-compras/cambiar-status/{id}` | Cambia el estado de la orden |

### Servicio de ítems de compra

| Método | Ruta                         | Descripción            |
| ------ | ---------------------------- | ---------------------- |
| `POST` | `/api/v1/items-compras`      | Crea un ítem de compra |
| `GET`  | `/api/v1/items-compras`      | Lista ítems de compra  |
| `GET`  | `/api/v1/items-compras/{id}` | Busca ítem por ID      |

### Servicio de facturación

| Método | Ruta                           | Descripción                               |
| ------ | ------------------------------ | ----------------------------------------- |
| `POST` | `/api/v1/facturas/generar`     | Genera una factura                        |
| `GET`  | `/api/v1/facturas`             | Lista facturas                            |
| `GET`  | `/api/v1/facturas/cronologico` | Lista facturas ordenadas cronológicamente |
| `GET`  | `/api/v1/facturas/buscar/{id}` | Busca una factura por ID                  |

### Servicio de reportes

| Método | Ruta                         | Descripción                 |
| ------ | ---------------------------- | --------------------------- |
| `POST` | `/api/v1/reportes/generar`   | Genera reporte consolidado  |
| `GET`  | `/api/v1/reportes/historial` | Lista historial de reportes |

### Servicio de notificaciones

| Método   | Ruta                          | Descripción               |
| -------- | ----------------------------- | ------------------------- |
| `POST`   | `/api/v1/notificaciones`      | Crea una notificación     |
| `GET`    | `/api/v1/notificaciones`      | Lista notificaciones      |
| `GET`    | `/api/v1/notificaciones/{id}` | Busca notificación por ID |
| `PUT`    | `/api/v1/notificaciones/{id}` | Actualiza notificación    |
| `DELETE` | `/api/v1/notificaciones/{id}` | Elimina notificación      |

---

## Ejemplos de rutas Swagger

### Swagger desde API Gateway

```text
http://localhost:4425/swagger-ui.html
```

Rutas OpenAPI disponibles desde el gateway:

| Servicio       | Ruta OpenAPI                                              |
| -------------- | --------------------------------------------------------- |
| Catálogo       | `http://localhost:4425/api/v1/catalogo/v3/api-docs`       |
| Inventario     | `http://localhost:4425/api/v1/inventario/v3/api-docs`     |
| Ventas         | `http://localhost:4425/api/v1/ventas/v3/api-docs`         |
| Pagos          | `http://localhost:4425/api/v1/pagos/v3/api-docs`          |
| Clientes       | `http://localhost:4425/api/v1/clientes/v3/api-docs`       |
| Proveedores    | `http://localhost:4425/api/v1/proveedores/v3/api-docs`    |
| Facturación    | `http://localhost:4425/api/v1/facturas/v3/api-docs`       |
| Reportes       | `http://localhost:4425/api/v1/reportes/v3/api-docs`       |
| Autenticación  | `http://localhost:4425/api/v1/auth/v3/api-docs`           |
| Notificaciones | `http://localhost:4425/api/v1/notificaciones/v3/api-docs` |

### Swagger directo por microservicio

| Servicio       | Swagger UI                              |
| -------------- | --------------------------------------- |
| Auth           | `http://localhost:4428/swagger-ui.html` |
| Catálogo       | `http://localhost:4419/swagger-ui.html` |
| Clientes       | `http://localhost:4420/swagger-ui.html` |
| Inventario     | `http://localhost:4421/swagger-ui.html` |
| Ventas         | `http://localhost:4422/swagger-ui.html` |
| Pagos          | `http://localhost:4423/swagger-ui.html` |
| Facturación    | `http://localhost:4426/swagger-ui.html` |
| Proveedores    | `http://localhost:4427/swagger-ui.html` |
| Reportes       | `http://localhost:4429/swagger-ui.html` |
| Notificaciones | `http://localhost:4430/swagger-ui.html` |

---

## Comunicación entre microservicios

La comunicación entre microservicios se realiza principalmente mediante `WebClient`, permitiendo que un servicio consulte información externa a través de HTTP.

Ejemplos dentro del sistema:

* `service-ventas` consulta productos en `service-catalogo`.
* `service-ventas` registra salidas o movimientos en `service-inventario`.
* `service-ventas` se comunica con `service-pagos` para procesar pagos.
* `service-inventario` valida productos consultando `service-catalogo`.
* `service-reportes` consulta información de ventas e inventario para generar reportes consolidados.
* `service_proveedores` puede registrar movimientos de entrada en inventario cuando una orden de compra cambia a recibida.

---

## Testing

El proyecto considera pruebas unitarias para validar la lógica de negocio sin depender directamente de la base de datos o de otros microservicios reales.

Las pruebas se desarrollan principalmente con:

* `JUnit 5`
* `Mockito`
* `Spring Boot Starter Test`
* `Reactor Test`

La estructura recomendada para las pruebas unitarias es el patrón **AAA**:

1. **Arrange**: preparar datos, objetos y mocks.
2. **Act**: ejecutar el método que se quiere probar.
3. **Assert**: validar el resultado obtenido.

Ejemplo de ejecución de pruebas con Maven:

```bash
mvn test
```

---

## Troubleshooting

### Error 401 Unauthorized

Si una ruta protegida devuelve `401`, verificar que se esté enviando correctamente el token en el header:

```http
Authorization: Bearer TU_TOKEN_AQUI
```

Primero se debe iniciar sesión en:

```http
POST http://localhost:4425/api/v1/auth/login
```

### Error al conectar con la base de datos

Verificar:

* Que MySQL esté encendido.
* Que la base de datos exista.
* Que el usuario y contraseña estén correctos en `application.properties`.
* Que el puerto de MySQL sea el correcto.

### Error al consumir otro microservicio

Verificar:

* Que el microservicio destino esté ejecutándose.
* Que el puerto configurado sea correcto.
* Que la ruta usada por `WebClient` exista.
* Que los DTOs coincidan con el JSON recibido.

### Problemas con DTOs

Cuando un atributo llega como `null`, revisar que los nombres del JSON coincidan con los atributos del DTO. En caso necesario, utilizar:

```java
@JsonProperty("nombreCampoJson")
```

---

## Estado del proyecto

El sistema se encuentra implementado como un ecosistema de microservicios Spring Boot con API Gateway, autenticación JWT, documentación Swagger, persistencia MySQL, comunicación entre servicios y pruebas unitarias.
::: 
