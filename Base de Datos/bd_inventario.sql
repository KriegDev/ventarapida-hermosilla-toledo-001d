-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 06:09:48
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
-- Base de datos: `bd_inventario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento_inventario`
--

CREATE TABLE `movimiento_inventario` (
  `id` bigint(20) NOT NULL,
  `fecha_movimiento` datetime(6) NOT NULL,
  `monto` decimal(38,2) NOT NULL,
  `tipo_movimiento` varchar(255) NOT NULL,
  `stock_id` bigint(20) NOT NULL
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
(12, '2026-05-12 23:58:54.000000', 4.00, 'Salida', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock`
--

CREATE TABLE `stock` (
  `id` bigint(20) NOT NULL,
  `cantidad` decimal(38,2) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `stock_minimo` decimal(38,2) NOT NULL
) ;

--
-- Volcado de datos para la tabla `stock`
--

INSERT INTO `stock` (`id`, `cantidad`, `id_producto`, `stock_minimo`) VALUES
(1, 378.00, 1, 5.00),
(2, 40.00, 2, 5.00),
(3, 50.00, 3, 10.00),
(4, 60.00, 4, 10.00),
(5, 50000.00, 5, 500.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `movimiento_inventario`
--
ALTER TABLE `movimiento_inventario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8hm5ngvrmp7rkwlswisosrwvc` (`stock_id`);

--
-- Indices de la tabla `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `movimiento_inventario`
--
ALTER TABLE `movimiento_inventario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `stock`
--
ALTER TABLE `stock`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `movimiento_inventario`
--
ALTER TABLE `movimiento_inventario`
  ADD CONSTRAINT `FK8hm5ngvrmp7rkwlswisosrwvc` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
