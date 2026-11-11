-- ============================================================
-- SCRIPT DE BASE DE DATOS - SISTEMA DE GESTIÓN DE EVENTOS
-- Pre-Hackatón T11 - G01
-- ============================================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS db_eventos;
USE db_eventos;

-- ============================================================
-- TABLA: EVENTOS
-- ============================================================
CREATE TABLE IF NOT EXISTS eventos (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL UNIQUE,
    descripcion TEXT,
    fecha DATE NOT NULL,
    lugar VARCHAR(150) NOT NULL,
    modalidad ENUM('Presencial', 'Virtual', 'Hibrida') DEFAULT 'Presencial',
    estado ENUM('Programado', 'En_Curso', 'Finalizado', 'Cancelado') DEFAULT 'Programado',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_estado (estado),
    INDEX idx_fecha (fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: PARTICIPANTES
-- ============================================================
CREATE TABLE IF NOT EXISTS participantes (
    id_participante INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    institucion VARCHAR(150),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dni (dni),
    INDEX idx_correo (correo),
    INDEX idx_institucion (institucion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: INSCRIPCIONES
-- ============================================================
CREATE TABLE IF NOT EXISTS inscripciones (
    id_inscripcion INT AUTO_INCREMENT PRIMARY KEY,
    id_evento INT NOT NULL,
    id_participante INT NOT NULL,
    fecha_inscripcion DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Confirmado', 'Pendiente', 'Cancelado', 'Completado') DEFAULT 'Confirmado',
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_evento) REFERENCES eventos(id_evento) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_participante) REFERENCES participantes(id_participante) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY uk_evento_participante (id_evento, id_participante),
    INDEX idx_estado (estado),
    INDEX idx_fecha_inscripcion (fecha_inscripcion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- DATOS DE PRUEBA - EVENTOS
-- ============================================================
INSERT INTO eventos (nombre, descripcion, fecha, lugar, modalidad, estado) VALUES
('Conferencia de Inteligencia Artificial', 'Conferencia magistral sobre IA y machine learning', '2026-12-15', 'Lima', 'Presencial', 'Programado'),
('Workshop JavaFX Avanzado', 'Taller práctico de desarrollo con JavaFX', '2026-12-20', 'Virtual', 'Virtual', 'Programado'),
('Seminario de Arquitectura de Software', 'Seminario sobre patrones y mejores prácticas', '2026-12-22', 'Híbrido', 'Hibrida', 'Programado'),
('Expo Tecnológica 2026', 'Exposición de tecnologías emergentes', '2026-12-25', 'Centro de Convenciones', 'Presencial', 'Programado'),
('Curso MySQL Avanzado', 'Curso intensivo de bases de datos', '2026-12-18', 'Virtual', 'Virtual', 'Programado');

-- ============================================================
-- DATOS DE PRUEBA - PARTICIPANTES
-- ============================================================
INSERT INTO participantes (nombres, apellidos, dni, correo, telefono, institucion) VALUES
('Juan', 'Pérez García', '12345678', 'juan.perez@example.com', '999888777', 'Universidad del Perú'),
('María', 'López Rodríguez', '12345679', 'maria.lopez@example.com', '999888778', 'Instituto Tecnológico'),
('Carlos', 'Martínez Santos', '12345680', 'carlos.martinez@example.com', '999888779', 'Universidad del Perú'),
('Ana', 'García López', '12345681', 'ana.garcia@example.com', '999888780', 'Empresa Tech Solutions'),
('Roberto', 'Fernández Díaz', '12345682', 'roberto.fernandez@example.com', '999888781', 'Universidad del Perú'),
('Patricia', 'Ruiz Hernández', '12345683', 'patricia.ruiz@example.com', '999888782', 'Centro de Capacitación'),
('Miguel', 'Sánchez Torres', '12345684', 'miguel.sanchez@example.com', '999888783', 'Empresa Dev Corp'),
('Isabel', 'Morales Flores', '12345685', 'isabel.morales@example.com', '999888784', 'Universidad del Perú');

-- ============================================================
-- DATOS DE PRUEBA - INSCRIPCIONES
-- ============================================================
INSERT INTO inscripciones (id_evento, id_participante, estado) VALUES
(1, 1, 'Confirmado'),
(1, 2, 'Confirmado'),
(1, 3, 'Confirmado'),
(2, 4, 'Confirmado'),
(2, 5, 'Confirmado'),
(3, 1, 'Confirmado'),
(3, 6, 'Confirmado'),
(4, 7, 'Confirmado'),
(4, 8, 'Confirmado'),
(5, 1, 'Pendiente'),
(5, 2, 'Confirmado');

-- ============================================================
-- VERIFICACIÓN FINAL
-- ============================================================
SELECT COUNT(*) AS total_eventos FROM eventos;
SELECT COUNT(*) AS total_participantes FROM participantes;
SELECT COUNT(*) AS total_inscripciones FROM inscripciones;
