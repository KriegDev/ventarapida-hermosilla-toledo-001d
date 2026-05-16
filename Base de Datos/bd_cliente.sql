-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 06:09:41
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
-- Base de datos: `bd_cliente`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL,
  `activo` bit(1) NOT NULL,
  `apmaterno` varchar(255) NOT NULL,
  `appaterno` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `dvrun` varchar(1) NOT NULL,
  `pnombre` varchar(255) NOT NULL,
  `run` bigint(20) NOT NULL,
  `snombre` varchar(255) NOT NULL,
  `telefono` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbu04udb314vu70jk4vahrhlek` (`run`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
