-- =============================================================================
-- EJERCICIO 2: Base de datos objeto-relacional (Oracle)
-- Gestión de ciudadanos y trámites
-- =============================================================================

-- =============================================================================
-- APARTADO A) Definición de tipos objeto
-- =============================================================================

-- 1. Tipo objeto Ciudadano_t
CREATE OR REPLACE TYPE Ciudadano_t AS OBJECT (
    dni         VARCHAR2(9),    -- DNI del ciudadano (VARCHAR2 por la letra)
    nombre      VARCHAR2(100),  -- Nombre completo
    telefono    VARCHAR2(15)    -- Teléfono (VARCHAR2 para prefijos +34, etc.)
);
/ -- Obligatorio ponerlo para que ejecute el código

-- 2. Tipo colección para documentos (VARRAY)
CREATE OR REPLACE TYPE Documentos_va AS VARRAY(10) OF VARCHAR2(200);
/ -- Obligatorio ponerlo para que ejecute el código

-- 3. Tipo objeto Tramite_t con referencia y colección
CREATE OR REPLACE TYPE Tramite_t AS OBJECT (
    id_tramite      NUMBER,
    fecha           DATE,
    estado          VARCHAR2(50),
    ciudadano_ref   REF Ciudadano_t,    -- Referencia al ciudadano (Tema 4, pág. 72-73)
    documentos      Documentos_va       -- Colección VARRAY de documentos
);
/ -- Obligatorio ponerlo para que ejecute el código

-- =============================================================================
-- APARTADO B) Creación de tablas de objetos
-- =============================================================================

-- Tabla de objetos Ciudadanos
CREATE TABLE Ciudadanos OF Ciudadano_t (
    dni PRIMARY KEY
);

-- Tabla de objetos Tramites
CREATE TABLE Tramites OF Tramite_t (
    id_tramite PRIMARY KEY
);

-- =============================================================================
-- APARTADO C) Inserción de datos
-- =============================================================================

-- Insertar un ciudadano
INSERT INTO Ciudadanos VALUES (
    Ciudadano_t('12345678A', 'Juan García López', '612345678')
);

-- Insertar un trámite con referencia al ciudadano y colección de documentos
INSERT INTO Tramites VALUES (
    Tramite_t(
        1,                                                              -- id_tramite
        TO_DATE('2026-02-10', 'YYYY-MM-DD'),                           -- fecha
        'pendiente',                                                    -- estado
        (SELECT REF(c) FROM Ciudadanos c WHERE c.dni = '12345678A'),   -- referencia
        Documentos_va('solicitud.pdf', 'dni_escaneado.jpg')            -- 2 documentos (VARRAY)
    )
);

-- =============================================================================
-- APARTADO D) Consulta con desreferenciación
-- =============================================================================
SELECT
    DEREF(t.ciudadano_ref).nombre AS nombre_ciudadano,
    t.id_tramite,
    t.estado,
    t.documentos
FROM
    Tramites t;

-- =============================================================================
-- APARTADO E) Transacción para cambiar estado de un trámite
-- =============================================================================
-- Bloque PL/SQL para realizar la transacción
BEGIN
    -- Cambiar el estado del trámite de "pendiente" a "resuelto"
    UPDATE Tramites t
    SET t.estado = 'resuelto'
    WHERE t.id_tramite = 1;

    -- COMMIT: Confirma y hace permanentes los cambios
    COMMIT;

    -- Indicar con un comentario como se deshacen los cambios
    -- ROLLBACK: Deshace todos los cambios en caso de error
END; -- Cierra el BEGIN
/ -- Obligatorio ponerlo para que ejecute el código
