-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 01:53:11
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_catalogo`
--
DROP DATABASE IF EXISTS `bd_catalogo`;
CREATE DATABASE IF NOT EXISTS `bd_catalogo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_catalogo`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`) VALUES
(1, 'Abarrotes'),
(2, 'Limpieza'),
(3, 'Bebidas'),
(4, 'Lacteos'),
(5, 'Frutas y Verduras');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  `granel` bit(1) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `precio_base` bigint(20) NOT NULL,
  `sku` varchar(255) NOT NULL,
  `id_categoria` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKclpng6f2m2r9i1y5g2yxajyuq` (`sku`),
  KEY `FK9nyueixdsgbycfhf7allg8su` (`id_categoria`)
) ;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `descripcion`, `granel`, `nombre`, `precio_base`, `sku`, `id_categoria`) VALUES
(1, 'Arroz Tucapel 1 kg, grado 1', b'0', 'Arroz Tucapel', 1900, 'AT102G1', 1),
(2, 'Coca-Cola envase desechable 2 litros', b'0', 'Coca-Cola 2lt', 1570, 'CC1L02G1', 3),
(3, 'Tallarines n°77 Luchetti', b'0', 'Tallarines', 1490, 'TL77789', 1),
(4, 'Cloro Clorinda 250 ml', b'0', 'Cloro', 1250, 'CC2507789', 2),
(5, 'Manzana Fuji Categoría 1', b'1', 'Manzana', 1490, 'MGC177789', 5);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `FK9nyueixdsgbycfhf7allg8su` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`);
--
-- Base de datos: `bd_cliente`
--
DROP DATABASE IF EXISTS `bd_cliente`;
CREATE DATABASE IF NOT EXISTS `bd_cliente` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_cliente`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `apmaterno` varchar(255) NOT NULL,
  `appaterno` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `dvrun` varchar(1) NOT NULL,
  `pnombre` varchar(255) NOT NULL,
  `run` bigint(20) NOT NULL,
  `snombre` varchar(255) NOT NULL,
  `telefono` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbu04udb314vu70jk4vahrhlek` (`run`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `activo`, `apmaterno`, `appaterno`, `correo`, `dvrun`, `pnombre`, `run`, `snombre`, `telefono`) VALUES
(1, b'1', 'Baeza', 'Hermosilla', 'Carolinafk4@gmail.com', '1', 'Carolina', 21605460, 'Nataly', 984258099),
(2, b'1', 'Jaramillo', 'Toledo', 'soykriegcl@gmail.com', '9', 'Pedro', 19291897, 'Felipe', 947875729),
(3, b'1', 'Gallardo', 'Soto', 'VSoto@correo.com', 'K', 'Valeria', 20569878, 'Antonia', 958569889),
(4, b'0', 'Polhammer', 'Livingstone', 'slivingstone@cruzados.cl', '3', 'Serjio', 5998532, 'Roberto', 958488448),
(5, b'1', 'Rende', 'Buonanotte', 'EleNano@correo.com', '6', 'Diego', 15655645, 'Mario', 966556655);
--
-- Base de datos: `bd_factura`
--
DROP DATABASE IF EXISTS `bd_factura`;
CREATE DATABASE IF NOT EXISTS `bd_factura` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_factura`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

DROP TABLE IF EXISTS `factura`;
CREATE TABLE IF NOT EXISTS `factura` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_emision` datetime(6) DEFAULT NULL,
  `id_orden` bigint(20) NOT NULL,
  `impuesto` decimal(38,2) NOT NULL,
  `monto_subtotal` bigint(20) NOT NULL,
  `monto_total` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`id`, `fecha_emision`, `id_orden`, `impuesto`, `monto_subtotal`, `monto_total`) VALUES
(1, '2026-06-15 23:14:42.000000', 20267, 476.00, 2504, 2980),
(2, '2026-06-15 23:16:27.000000', 20267, 476.00, 2504, 2980),
(3, '2026-06-15 23:20:29.000000', 20268, 714.00, 3756, 4470),
(4, '2026-06-15 23:22:29.000000', 20269, 714.00, 3756, 4470),
(5, '2026-06-15 23:22:47.000000', 20269, 476.00, 2504, 2980),
(6, '2026-06-15 23:36:29.000000', 20270, 714.00, 3756, 4470);
--
-- Base de datos: `bd_inventario`
--
DROP DATABASE IF EXISTS `bd_inventario`;
CREATE DATABASE IF NOT EXISTS `bd_inventario` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_inventario`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento_inventario`
--

DROP TABLE IF EXISTS `movimiento_inventario`;
CREATE TABLE IF NOT EXISTS `movimiento_inventario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_movimiento` datetime(6) NOT NULL,
  `monto` decimal(38,2) NOT NULL,
  `tipo_movimiento` varchar(255) NOT NULL,
  `stock_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8hm5ngvrmp7rkwlswisosrwvc` (`stock_id`)
) ;

--
-- Volcado de datos para la tabla `movimiento_inventario`
--

INSERT INTO `movimiento_inventario` (`id`, `fecha_movimiento`, `monto`, `tipo_movimiento`, `stock_id`) VALUES
(1, '2026-04-30 04:40:00.000000', 10.00, 'ENTRADA', 1),
(2, '2026-05-13 00:18:42.000000', 40.00, 'ENTRADA', 2),
(3, '2026-05-12 22:24:52.000000', 1.00, 'Salida', 1),
(4, '2026-05-12 22:40:00.000000', 1.00, 'Salida', 1),
(5, '2026-05-12 22:46:23.000000', 1.00, 'Salida', 1),
(6, '2026-05-12 22:51:08.000000', 1.00, 'Salida', 1),
(7, '2026-05-12 22:51:16.000000', 1.00, 'Salida', 1),
(8, '2026-05-12 23:04:00.000000', 10.00, 'Salida', 1),
(9, '2026-05-12 23:54:02.000000', 2.00, 'Salida', 1),
(10, '2026-05-12 23:55:13.000000', 2.00, 'Salida', 1),
(11, '2026-05-12 23:58:09.000000', 4.00, 'Salida', 1),
(12, '2026-05-12 23:58:54.000000', 4.00, 'Salida', 1),
(13, '2026-06-15 23:14:40.000000', 2.00, 'Salida', 3),
(14, '2026-06-15 23:20:29.000000', 3.00, 'Salida', 3),
(15, '2026-06-15 23:22:29.000000', 3.00, 'Salida', 3),
(16, '2026-06-15 23:36:27.000000', 3.00, 'Salida', 3),
(17, '2026-06-21 17:23:07.000000', 20.00, 'ENTRADA', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(38,2) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `stock_minimo` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- Volcado de datos para la tabla `stock`
--

INSERT INTO `stock` (`id`, `cantidad`, `id_producto`, `stock_minimo`) VALUES
(1, 398.00, 1, 5.00),
(2, 40.00, 2, 5.00),
(3, 39.00, 3, 10.00),
(4, 60.00, 4, 10.00),
(5, 50000.00, 5, 500.00);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `movimiento_inventario`
--
ALTER TABLE `movimiento_inventario`
  ADD CONSTRAINT `FK8hm5ngvrmp7rkwlswisosrwvc` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`);
--
-- Base de datos: `bd_notificaciones`
--
DROP DATABASE IF EXISTS `bd_notificaciones`;
CREATE DATABASE IF NOT EXISTS `bd_notificaciones` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_notificaciones`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
CREATE TABLE IF NOT EXISTS `notificaciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `fecha_envio` date NOT NULL,
  `status` varchar(255) NOT NULL,
  `tipo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
-- Base de datos: `bd_pago`
--
DROP DATABASE IF EXISTS `bd_pago`;
CREATE DATABASE IF NOT EXISTS `bd_pago` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_pago`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodo_pago`
--

DROP TABLE IF EXISTS `metodo_pago`;
CREATE TABLE IF NOT EXISTS `metodo_pago` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnrrsdg1qhbpakbl54c769qs64` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `metodo_pago`
--

INSERT INTO `metodo_pago` (`id`, `activo`, `nombre`) VALUES
(1, b'1', 'CREDITO'),
(2, b'1', 'DEBITO'),
(3, b'1', 'EFECTIVO'),
(4, b'1', 'FLOW');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos`
--

DROP TABLE IF EXISTS `pagos`;
CREATE TABLE IF NOT EXISTS `pagos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) NOT NULL,
  `estado_flow` varchar(255) NOT NULL,
  `fecha_creacion` datetime(6) NOT NULL,
  `fecha_pago` datetime(6) DEFAULT NULL,
  `flow_order` varchar(255) DEFAULT NULL,
  `flow_token` varchar(255) DEFAULT NULL,
  `id_orden` bigint(20) NOT NULL,
  `monto` bigint(20) NOT NULL,
  `id_metodo_pago` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc13iuitpc4omxhb4l0wxvk6g9` (`id_metodo_pago`)
) ;

--
-- Volcado de datos para la tabla `pagos`
--

INSERT INTO `pagos` (`id`, `estado`, `estado_flow`, `fecha_creacion`, `fecha_pago`, `flow_order`, `flow_token`, `id_orden`, `monto`, `id_metodo_pago`) VALUES
(1, 'PAGADO', 'PAID', '2026-05-12 22:10:08.000000', '2026-05-12 22:10:08.000000', NULL, NULL, 1, 20000, 3),
(2, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-05-12 23:37:56.000000', NULL, NULL, '3B2BD6652C541D52FCF0D1E8D2ADB6FFFD1E1F1P', 6008, 5000, 4),
(3, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-05-12 23:38:11.000000', NULL, NULL, '5C55764C88D986C18B4A917C9FC8E3A537D52C7Y', 6008, 5000, 4),
(4, 'PAGADO', 'N/A', '2026-05-12 23:44:27.000000', '2026-05-12 23:44:27.000000', NULL, NULL, 6009, 5000, 1),
(5, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-05-12 23:55:13.000000', NULL, NULL, 'E39B6D2215F49FFC01F2E25649EB32527DBBDB8N', 2024, 3800, 4),
(6, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-05-12 23:58:09.000000', NULL, NULL, '2519350BEDBD8177D0A795D2F5CBB48486743D6X', 2024, 7600, 4),
(7, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-05-12 23:58:55.000000', NULL, NULL, '2017507AB79DF1F235945F841DDA737D37587D1U', 2025, 7600, 4),
(8, 'PAGADO', 'N/A', '2026-05-12 23:59:54.000000', '2026-05-12 23:59:54.000000', NULL, NULL, 2025, 5000, 1),
(9, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-05-13 00:00:04.000000', NULL, NULL, '7B006ED609C211924B5F0EDB119B908AE353BBFS', 2025, 5000, 4),
(10, 'PAGADO', 'N/A', '2026-06-15 23:14:41.000000', '2026-06-15 23:14:41.000000', NULL, NULL, 20267, 2980, 1),
(11, 'PAGADO', 'N/A', '2026-06-15 23:16:27.000000', '2026-06-15 23:16:27.000000', NULL, NULL, 20267, 2980, 1),
(12, 'PAGADO', 'N/A', '2026-06-15 23:20:29.000000', '2026-06-15 23:20:29.000000', NULL, NULL, 20268, 4470, 1),
(13, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-06-15 23:21:14.000000', NULL, NULL, 'FC2A0318653D9D422000F61E89950D2116C623DT', 20268, 2980, 4),
(14, 'PAGADO', 'N/A', '2026-06-15 23:22:29.000000', '2026-06-15 23:22:29.000000', NULL, NULL, 20269, 4470, 1),
(15, 'PAGADO', 'N/A', '2026-06-15 23:22:47.000000', '2026-06-15 23:22:47.000000', NULL, NULL, 20269, 2980, 1),
(16, 'PAGADO', 'N/A', '2026-06-15 23:36:28.000000', '2026-06-15 23:36:28.000000', NULL, NULL, 20270, 4470, 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD CONSTRAINT `FKc13iuitpc4omxhb4l0wxvk6g9` FOREIGN KEY (`id_metodo_pago`) REFERENCES `metodo_pago` (`id`);
--
-- Base de datos: `bd_proveedores`
--
DROP DATABASE IF EXISTS `bd_proveedores`;
CREATE DATABASE IF NOT EXISTS `bd_proveedores` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_proveedores`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `item_compra`
--

DROP TABLE IF EXISTS `item_compra`;
CREATE TABLE IF NOT EXISTS `item_compra` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(38,2) NOT NULL,
  `costo_unitario` bigint(20) NOT NULL,
  `granel` bit(1) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `id_orden_compra` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKprr0sgp3bkq9ypxhsdm3td2qx` (`id_orden_compra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_compra`
--

DROP TABLE IF EXISTS `orden_compra`;
CREATE TABLE IF NOT EXISTS `orden_compra` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `costo_total` bigint(20) NOT NULL,
  `fecha_compra` date NOT NULL,
  `status` varchar(255) NOT NULL,
  `id_proveedor` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpgp1ooe3wmycq29kdj6qydybp` (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
CREATE TABLE IF NOT EXISTS `proveedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `compañia` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre_contacto` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `item_compra`
--
ALTER TABLE `item_compra`
  ADD CONSTRAINT `FKprr0sgp3bkq9ypxhsdm3td2qx` FOREIGN KEY (`id_orden_compra`) REFERENCES `orden_compra` (`id`);

--
-- Filtros para la tabla `orden_compra`
--
ALTER TABLE `orden_compra`
  ADD CONSTRAINT `FKpgp1ooe3wmycq29kdj6qydybp` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id`);
--
-- Base de datos: `bd_reportes`
--
DROP DATABASE IF EXISTS `bd_reportes`;
CREATE DATABASE IF NOT EXISTS `bd_reportes` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_reportes`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_reportes`
--

DROP TABLE IF EXISTS `historial_reportes`;
CREATE TABLE IF NOT EXISTS `historial_reportes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alertas_stock_detectadas` bigint(20) NOT NULL,
  `fecha_generacion` datetime(6) NOT NULL,
  `generado_por` varchar(255) DEFAULT NULL,
  `tipo_reporte` varchar(255) NOT NULL,
  `total_ventas_calculadas` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historial_reportes`
--

INSERT INTO `historial_reportes` (`id`, `alertas_stock_detectadas`, `fecha_generacion`, `generado_por`, `tipo_reporte`, `total_ventas_calculadas`) VALUES
(1, 0, '2026-06-17 00:39:26.000000', 'SYSTEM', 'GENERAL', 11400);
--
-- Base de datos: `bd_usuario`
--
DROP DATABASE IF EXISTS `bd_usuario`;
CREATE DATABASE IF NOT EXISTS `bd_usuario` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_usuario`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) NOT NULL,
  `nombre_usuario` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpuhr3k3l7bj71hb7hk7ktpxn0` (`nombre_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `contrasena`, `nombre_usuario`) VALUES
(1, '$2a$10$ZppJDlvlWubbkHTFeKSJ/OZ0TyU6BgFU2sTlrKXp62R4L48EFiFoa', 'servicios'),
(3, '$2a$10$aNGNY3JlQ6WCZPYvBum5ou2Ngj1QpIyGzR.4W0Be5MDaBEhxhnlGS', 'evidencia');
--
-- Base de datos: `bd_venta`
--
DROP DATABASE IF EXISTS `bd_venta`;
CREATE DATABASE IF NOT EXISTS `bd_venta` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bd_venta`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

DROP TABLE IF EXISTS `detalle`;
CREATE TABLE IF NOT EXISTS `detalle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(38,2) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `precio_unitario` bigint(20) NOT NULL,
  `id_orden` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK97yqfpqe3gw24a8ldigbfcae3` (`id_orden`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`id`, `cantidad`, `id_producto`, `precio_unitario`, `id_orden`) VALUES
(1, 6.00, 1, 750, 1),
(2, 2.00, 1, 1900, 2),
(3, 4.00, 1, 1900, 3),
(4, 4.00, 1, 1900, 4),
(5, 2.00, 3, 1490, 5),
(6, 3.00, 3, 1490, 6),
(7, 3.00, 3, 1490, 7),
(8, 3.00, 3, 1490, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

DROP TABLE IF EXISTS `orden`;
CREATE TABLE IF NOT EXISTS `orden` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_venta` date DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `id_metodo_pago` bigint(20) DEFAULT NULL,
  `monto_total` bigint(20) DEFAULT NULL,
  `numero_orden` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- Volcado de datos para la tabla `orden`
--

INSERT INTO `orden` (`id`, `fecha_venta`, `id_cliente`, `id_metodo_pago`, `monto_total`, `numero_orden`, `status`) VALUES
(1, '2026-01-24', 1, 1, 25900, 1989, 'FINALIZADO'),
(2, '2026-05-12', 1, 4, 3800, 2024, 'PAGADO'),
(3, '2026-05-12', 1, 4, 7600, 2024, 'PAGADO'),
(4, '2026-05-12', 1, 4, 7600, 2025, 'PENDIENTE_PAGO'),
(5, '2026-06-15', 3, 1, 2980, 20267, 'PENDIENTE_PAGO'),
(6, '2026-06-15', 2, 1, 4470, 20268, 'PENDIENTE_PAGO'),
(7, '2026-06-15', 2, 1, 4470, 20269, 'PENDIENTE_PAGO'),
(8, '2026-06-15', 2, 1, 4470, 20270, 'PENDIENTE_PAGO');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD CONSTRAINT `FK97yqfpqe3gw24a8ldigbfcae3` FOREIGN KEY (`id_orden`) REFERENCES `orden` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
