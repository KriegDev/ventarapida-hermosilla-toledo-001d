-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 06:09:53
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
-- Base de datos: `bd_pago`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodo_pago`
--

CREATE TABLE `metodo_pago` (
  `id` bigint(20) NOT NULL,
  `activo` bit(1) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

CREATE TABLE `pagos` (
  `id` bigint(20) NOT NULL,
  `estado` varchar(255) NOT NULL,
  `estado_flow` varchar(255) NOT NULL,
  `fecha_creacion` datetime(6) NOT NULL,
  `fecha_pago` datetime(6) DEFAULT NULL,
  `flow_order` varchar(255) DEFAULT NULL,
  `flow_token` varchar(255) DEFAULT NULL,
  `id_orden` bigint(20) NOT NULL,
  `monto` bigint(20) NOT NULL,
  `id_metodo_pago` bigint(20) NOT NULL
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
(9, 'PENDIENTE', 'PENDIENTE_FLOW', '2026-05-13 00:00:04.000000', NULL, NULL, '7B006ED609C211924B5F0EDB119B908AE353BBFS', 2025, 5000, 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKnrrsdg1qhbpakbl54c769qs64` (`nombre`);

--
-- Indices de la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKc13iuitpc4omxhb4l0wxvk6g9` (`id_metodo_pago`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `pagos`
--
ALTER TABLE `pagos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD CONSTRAINT `FKc13iuitpc4omxhb4l0wxvk6g9` FOREIGN KEY (`id_metodo_pago`) REFERENCES `metodo_pago` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
