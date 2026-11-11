-- =====================================================
-- SCRIPT DE CONFIGURACIÓN - MySQL Workbench
-- Proyecto: G01_PREHACKATON_T11
-- Base de Datos: db_eventos
-- =====================================================
-- Ejecuta este script completo en MySQL Workbench
-- =====================================================

-- 1. CREAR BASE DE DATOS
DROP DATABASE IF EXISTS db_eventos;
CREATE DATABASE db_eventos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_eventos;

-- 2. CREAR TABLA: EVENTOS
CREATE TABLE eventos (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    lugar VARCHAR(150),
    modalidad ENUM('Presencial', 'Virtual', 'Hibrida') DEFAULT 'Presencial',
    estado ENUM('Programado', 'En_Curso', 'Finalizado', 'Cancelado') DEFAULT 'Programado',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_fecha (fecha),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. CREAR TABLA: PARTICIPANTES
CREATE TABLE participantes (
    id_participante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(100),
    telefono VARCHAR(15),
    dni VARCHAR(12) UNIQUE,
    institucion VARCHAR(200),
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nombre (nombre),
    INDEX idx_estado (estado),
    UNIQUE KEY unique_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. CREAR TABLA: INSCRIPCIONES
CREATE TABLE inscripciones (
    id_inscripcion INT AUTO_INCREMENT PRIMARY KEY,
    id_evento INT NOT NULL,
    id_participante INT NOT NULL,
    fecha_inscripcion DATE DEFAULT CURDATE(),
    estado ENUM('Confirmado', 'Cancelado', 'En_Espera') DEFAULT 'Confirmado',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_evento) REFERENCES eventos(id_evento) ON DELETE CASCADE,
    FOREIGN KEY (id_participante) REFERENCES participantes(id_participante) ON DELETE CASCADE,
    INDEX idx_evento (id_evento),
    INDEX idx_participante (id_participante),
    INDEX idx_estado (estado),
    UNIQUE KEY unique_inscripcion (id_evento, id_participante)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERTAR DATOS DE PRUEBA
-- =====================================================

-- 5. INSERTAR EVENTOS DE PRUEBA
INSERT INTO eventos (nombre, descripcion, fecha, lugar, modalidad, estado) VALUES
('Hackatón 2026', 'Competencia de programación de 48 horas con premios', '2026-12-15', 'Auditorio Principal Vallegrande', 'Presencial', 'Programado'),
('Webinar Python Avanzado', 'Introducción a frameworks modernos en Python', '2026-11-20', 'Plataforma Zoom', 'Virtual', 'En_Curso'),
('Workshop JavaFX y JavaSwing', 'Desarrollo de interfaces gráficas modernas', '2026-11-25', 'Aula 101 - Vallegrande', 'Hibrida', 'Programado'),
('Charla: Clean Code', 'Buenas prácticas en desarrollo de software', '2026-11-18', 'Aula 202', 'Presencial', 'Programado'),
('Webinar Bases de Datos', 'SQL avanzado y modelado de datos', '2026-12-05', 'Teams Online', 'Virtual', 'Programado');

-- 6. INSERTAR PARTICIPANTES DE PRUEBA
INSERT INTO participantes (nombre, email, telefono, dni, institucion, estado) VALUES
('Juan Pérez Gómez', 'juan.perez@gmail.com', '987654321', '12345678', 'Vallegrande', 'Activo'),
('María García López', 'maria.garcia@gmail.com', '987654322', '12345679', 'Universidad Técnica del Norte', 'Activo'),
('Carlos López Martínez', 'carlos.lopez@gmail.com', '987654323', '12345680', 'Instituto Tecnológico', 'Activo'),
('Ana Rodríguez Silva', 'ana.rodriguez@gmail.com', '987654324', '12345681', 'Vallegrande', 'Activo'),
('Roberto Fernández Ruiz', 'roberto.fernandez@gmail.com', '987654325', '12345682', 'Academia XYZ', 'Activo'),
('Sofía Hernández Díaz', 'sofia.hernandez@gmail.com', '987654326', '12345683', 'Instituto ABC', 'Activo'),
('Miguel Ángel Torres', 'miguel.torres@gmail.com', '987654327', '12345684', 'Universidad Estatal', 'Inactivo'),
('Patricia Sánchez Ramírez', 'patricia.sanchez@gmail.com', '987654328', '12345685', 'Vallegrande', 'Activo');

-- 7. INSERTAR INSCRIPCIONES DE PRUEBA
INSERT INTO inscripciones (id_evento, id_participante, fecha_inscripcion, estado) VALUES
(1, 1, '2026-11-01', 'Confirmado'),
(1, 2, '2026-11-02', 'Confirmado'),
(1, 3, '2026-11-03', 'En_Espera'),
(2, 4, '2026-11-15', 'Confirmado'),
(2, 5, '2026-11-16', 'Confirmado'),
(3, 1, '2026-11-10', 'Confirmado'),
(3, 6, '2026-11-11', 'Confirmado'),
(4, 2, '2026-11-08', 'Confirmado'),
(4, 3, '2026-11-09', 'Cancelado'),
(5, 1, '2026-11-12', 'Confirmado'),
(5, 4, '2026-11-13', 'En_Espera');

-- =====================================================
-- VERIFICAR DATOS
-- =====================================================

SELECT '=== EVENTOS ===' AS status;
SELECT * FROM eventos;

SELECT '=== PARTICIPANTES ===' AS status;
SELECT * FROM participantes;

SELECT '=== INSCRIPCIONES ===' AS status;
SELECT * FROM inscripciones;

-- =====================================================
-- CONTAR REGISTROS
-- =====================================================

SELECT 
    (SELECT COUNT(*) FROM eventos) AS total_eventos,
    (SELECT COUNT(*) FROM participantes) AS total_participantes,
    (SELECT COUNT(*) FROM inscripciones) AS total_inscripciones;

-- =====================================================
-- FIN DEL SCRIPT
-- Creado: Noviembre 10, 2026
-- Status: ✓ LISTO PARA USAR
-- =====================================================
