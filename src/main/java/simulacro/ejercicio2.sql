-- =============================================================================
-- EJERCICIO 2: Base de datos objeto-relacional (Oracle)
-- Gestión de ciudadanos y trámites
-- =============================================================================
-- Basado en la teoría del Tema 4: Bases de datos objeto-relacionales y
-- orientadas a objetos
-- =============================================================================

-- =============================================================================
-- APARTADO A) Definición de tipos objeto
-- =============================================================================
-- Según Tema 4 (pág. 47-48):
-- "Un tipo de objeto representa una entidad del mundo real. Encapsula datos y operaciones.
--  Se compone de: nombre, atributos y métodos."
--
-- Según Tema 4 (pág. 50):
-- "Un atributo se declara mediante un nombre y un tipo. El nombre debe ser único
--  dentro del tipo de objeto."
-- =============================================================================

-- 1. Tipo objeto Ciudadano_t
-- Según Tema 4 (pág. 48-49), la sintaxis para definir un tipo objeto es:
-- CREATE OR REPLACE TYPE nombre_tipo AS OBJECT ( atributos );
CREATE OR REPLACE TYPE Ciudadano_t AS OBJECT (
    dni         VARCHAR2(9),    -- DNI del ciudadano (VARCHAR2 por la letra)
    nombre      VARCHAR2(100),  -- Nombre completo
    telefono    VARCHAR2(15)    -- Teléfono (VARCHAR2 para prefijos +34, etc.)
);
/ -- Obligatorio ponerlo para que ejecute el código

-- 2. Tipo colección para documentos (VARRAY)
-- Según Tema 4 (pág. 77):
-- "Un array es un conjunto ordenado de elementos del mismo tipo. Cada elemento tiene
--  asociado un índice que indica su posición dentro del array."
-- "Oracle permite que los VARRAY sean de longitud variable, aunque es necesario
--  especificar un tamaño máximo cuando se declara el tipo VARRAY."
CREATE OR REPLACE TYPE Documentos_va AS VARRAY(10) OF VARCHAR2(200);
/ -- Obligatorio ponerlo para que ejecute el código

-- 3. Tipo objeto Tramite_t con referencia y colección
-- Según Tema 4 (pág. 72):
-- "El tipo REF almacena una referencia a un objeto del tipo definido, e implementa
--  una relación de asociación entre los dos tipos de objetos"
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
-- Según Tema 4 (pág. 69):
-- "Una tabla de objetos es una clase especial de tabla que almacena un objeto en
--  cada fila y que facilita el acceso a los atributos de esos objetos como si
--  fueran columnas de la tabla."
--
-- Según Tema 4 (pág. 78):
-- "Cuando se declara un tipo VARRAY no se produce ninguna reserva de espacio.
--  Si el espacio que requiere lo permite, se almacena junto con el resto de
--  columnas de su tabla"
-- (No requiere NESTED TABLE...STORE AS como las tablas anidadas)
-- =============================================================================

-- Tabla de objetos Ciudadanos
CREATE TABLE Ciudadanos OF Ciudadano_t (
    dni PRIMARY KEY
);

-- Tabla de objetos Tramites
-- Con VARRAY no se necesita la cláusula NESTED TABLE...STORE AS
CREATE TABLE Tramites OF Tramite_t (
    id_tramite PRIMARY KEY
);

-- =============================================================================
-- APARTADO C) Inserción de datos
-- =============================================================================
-- Según Tema 4 (pág. 71):
-- "Se puede ejecutar INSERT... donde la tabla se considera como una tabla de objetos
--  que en cada fila almacena un objeto"
--
-- Según Tema 4 (pág. 85):
-- "La inserción de objetos con referencias implica la utilización del operador REF"
-- =============================================================================

-- Insertar un ciudadano usando el constructor del tipo (Tema 4, pág. 55)
INSERT INTO Ciudadanos VALUES (
    Ciudadano_t('12345678A', 'Juan García López', '612345678')
);

-- Insertar un trámite con referencia al ciudadano y colección de documentos
-- Según Tema 4 (pág. 85): Para obtener la referencia usamos SELECT REF(alias) FROM tabla
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
-- Según Tema 4 (pág. 74):
-- "Para obtener el valor de un objeto desde una referencia REF es necesario usar DEREF"
-- "DEREF toma como argumento una referencia a un objeto y devuelve el valor de dicho objeto"
-- =============================================================================

-- Consulta usando DEREF para desreferenciación
SELECT
    DEREF(t.ciudadano_ref).nombre AS nombre_ciudadano,
    t.id_tramite,
    t.estado,
    t.documentos AS documentos
FROM
    Tramites t;

-- =============================================================================
-- APARTADO E) Transacción para cambiar estado de un trámite
-- =============================================================================
-- Según Tema 4 (pág. 91):
-- "Una transacción es un conjunto de sentencias que se ejecutan formando una unidad
--  de trabajo, en forma indivisible o atómica: o se ejecutan todas o no se ejecuta ninguna"
--
-- Según Tema 4 (pág. 92):
-- Las transacciones deben cumplir el criterio ACID:
-- - Atomicidad: todas las operaciones o ninguna
-- - Consistencia: la BD queda en estado consistente
-- - Isolation: transacciones independientes
-- - Durabilidad: los cambios perduran
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
