VentaRápida - Sistema de Punto de Ventas Microservicios

Sistema de gestión de ventas, control y auditoría de inventario, presentación de catálogo con integración a pasarela de pagos flow.

Los microservicios se distribuyen de manera integrada por el API-Gateway, que funcionará como puerta de entrada a los 5 microservicios.

Lista de Microservicios:
  -0 API-Gateway : puerto 4425.
  -1 Catálogo: Gestión de productos y categorías: puerto 4419.
  -2 Clientes: Registro de compradores: puerto 4420.
  -3 Inventario: Movimientos de stock: puerto 4421.
  -4 Ventas: Procesamiento de órdenes: puerto 4422.
  -5 Pagos: Integración con Flow: puerto 4423.

Tecnologías Utilizadas:
  -1 Backend: Java con Spring Boot.
  -2 Comunicación: Spring Cloud Gateway y WebClient para llamadas asíncronas/síncronas.
  -3 Persistencia: Spring Data JPA / Hibernate.
  -4 Herramientas: Lombok, Jackson (Mapeos de DTOs con @JsonPropierty).
  -5 Pasarela de Pago: API de Flow (Sandbox).

Configuración y Ejecución
  1. Requisitos: Java 21, Spring 3.5.14, base de datos (MySQL).
  2. Variables de entorno: Se requieren credenciales de Flow para el archivo propierties.yml del servicio de pagos.
  3. Orden de Encendido: Junto a los 5 microservicios, debe estar activo (arriba) la API-Gateway para el funcionamiento del servicio de ventas.

Flujo Principal
  1. Validación: Ventas consulta precios al Catálogo.
  2. Stock: Se registra una salida en el Inventario.
  3. Pago: Se genera el cobro en el servicio de Pagos, se obtiene el link de Flow.
  4. Confirmación: Uso de Webhooks para actualizar el status a "PAGADO".

Documentación de la API
  Endpoints Claves:
    -/api/v1/categorias
      -Crea y lista categorías.
      /{id}
      -Modifica, elimina y muestra una categoría específica.
    -/api/v1/productos
      -Crea y lista los productos existente.
      /{id}
        -Modifica, elimina y muestra un producto específico.
      /sku/{sku}
        -Muestra un producto a partir de su código SKU.
      /existe/{id}
        -Muestra específicamente si el producto existe (Pensado para control de flujo de Inventario).
    -/api/v1/clientes
      -Crea y lista clientes
      /{id}
        -Busca un cliente específico por id
      /run/{run}
        -Busca un cliente específico por run.
      /actualizar-datos-contacto/{id}
        -Permite actualizar los datos de contacto del cliente.
      /actualizar-status/{id}
        -Permite actualizar el estatus de activo/inactivo del cliente (Pensado para futuras integraciones de fidelización).
    -/api/v1/stock
      -lista los productos en stock.
      /{id}
        -Muestra un producto específico en stock.
      /{/bajo-stock}
        -Muestra todos los productos cuyo stock existente es menor al criterio de stock mínimo (por defecto 5 unidades).
      /stock-minimo/{id}
        -Permite modificar el stock mínimo de un producto.
    -/api/v1/movimiento-inventario
      -muestra una lista con todos los movimientos de entrada y salida de inventario.
      -/nuevo/{id}
        -Crea un nuevo movimiento de inventario que modifica el stock.
      -/{id}
        -Busca un movimiento de inventario por su ID.
      -/buscar/{dia}
        -Obtiene todos los movimientos de inventario del día en que se genera la consulta.
      -/buscar/{fecha}
        -Obtiene todos los movimientos de inventario ocurridos en una fecha específica.
      -/entre/{ini}/y/{fin}
        -Obtiene todos los movimientos de inventario ocurridos dentro de un rango de fechas.
    -/api/v1/metodos-pago
      -Lista y crea métodos de pago.
      -/{id}
        -Modifica datos del método de pago.
      -/actualizar-status/{id}
        -Permite activar y desactivar métodos de pago disponibles.
    -/api/v1/pago
      -Lista todos los pagos realizados.
      -/procesar
        -Genera una solicitud de pago dependiendo del tipo de pago:
          1. CRÉDITO
          2. DÉBITO
          3. EFECTIVO
          4. FLOW
          -En caso de ser Flow se genera un ticket de flow con una solicitud a la plataforma de pagos online.
      -/confirmar-flow
        -Confirma el pago con Flow.
      -/{id}
        -Permite anular pagos que no se hayan concretados.
    -/api/v1/ordenes
      -Crea una orden de venta y muestra el total de ventas realizadas.
      -/{id}
        -Busca una venta por su id.
      -/numero/{nro}
        -Busca una venta por su número de venta.
      -/status/{status}
        -Lista todas las ventas según su status.
        
