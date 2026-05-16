-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 06:09:58
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
-- Base de datos: `bd_venta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `id` bigint(20) NOT NULL,
  `cantidad` decimal(38,2) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `precio_unitario` bigint(20) NOT NULL,
  `id_orden` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`id`, `cantidad`, `id_producto`, `precio_unitario`, `id_orden`) VALUES
(1, 6.00, 1, 750, 1),
(2, 2.00, 1, 1900, 2),
(3, 4.00, 1, 1900, 3),
(4, 4.00, 1, 1900, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE `orden` (
  `id` bigint(20) NOT NULL,
  `fecha_venta` date DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `id_metodo_pago` bigint(20) DEFAULT NULL,
  `monto_total` bigint(20) DEFAULT NULL,
  `numero_orden` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL
) ;

--
-- Volcado de datos para la tabla `orden`
--

INSERT INTO `orden` (`id`, `fecha_venta`, `id_cliente`, `id_metodo_pago`, `monto_total`, `numero_orden`, `status`) VALUES
(1, '2026-01-24', 1, 1, 25900, 1989, 'FINALIZADO'),
(2, '2026-05-12', 1, 4, 3800, 2024, 'PAGADO'),
(3, '2026-05-12', 1, 4, 7600, 2024, 'PAGADO'),
(4, '2026-05-12', 1, 4, 7600, 2025, 'PENDIENTE_PAGO');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK97yqfpqe3gw24a8ldigbfcae3` (`id_orden`);

--
-- Indices de la tabla `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detalle`
--
ALTER TABLE `detalle`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `orden`
--
ALTER TABLE `orden`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

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
