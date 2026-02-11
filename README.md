# Simulacro de Examen - 2ª Evaluación
## Acceso a Datos - DAM2

Este proyecto contiene la solución completa del simulacro de examen de la segunda evaluación, incluyendo ejercicios sobre **Bases de Datos Orientadas a Objetos (ObjectDB/JPA)** y **Bases de Datos Objeto-Relacionales (Oracle)**.

---

## 📁 Estructura del Proyecto

```
examen/
├── src/main/java/simulacro/
│   ├── Curso.java              # Ejercicio 1 - Clase persistente
│   ├── Main.java               # Ejercicio 1 - Operaciones CRUD con ObjectDB
│   ├── ejercicio2.sql          # Ejercicio 2 - SQL Objeto-Relacional (Oracle)
│   └── teoria/                 # Documentación de referencia
│       ├── SimulacroExamen2aEvaluación.md
│       ├── Tema 3. Herramientas de mapeo objeto-relacional.md
│       └── Tema 4. Bases de datos objeto-relacionales y orientadas a objetos.md
├── pom.xml                     # Configuración Maven con dependencias ObjectDB
└── academia.odb                # Base de datos ObjectDB (se crea automáticamente)
```

---

## 🎯 Ejercicio 1: Base de Datos Orientada a Objetos (ObjectDB)

### Descripción
Una academia de formación online gestiona sus cursos usando una base de datos orientada a objetos con **ObjectDB** y **JPA**.

### Archivos
- **`Curso.java`**: Clase persistente con anotaciones JPA
- **`Main.java`**: Operaciones de conexión, inserción, consulta y modificación

### Características Implementadas

#### A) Clase Curso (Curso.java)
```java
@Entity
public class Curso implements Serializable {
    @Id
    private String codigo;          // Identificador único
    private String nombre;
    private int numeroHoras;
    private double precio;
    private String nivel;           // básico, intermedio, avanzado
    
    // Constructor vacío + Constructor con parámetros
    // Getters y Setters completos
}
```

#### B) Conexión e Inserción
- Conexión a `academia.odb` usando `EntityManagerFactory`
- Transacción con `begin()` y `commit()`
- Inserción de 2 cursos con `persist()`

#### C) Consulta JPQL
```java
String jpql = "SELECT c FROM Curso c WHERE c.precio > 100 AND c.nivel = 'avanzado'";
TypedQuery<Curso> query = em.createQuery(jpql, Curso.class);
List<Curso> resultados = query.getResultList();
```

#### D) Modificación
- Localización de curso por código con `find()`
- Reducción del precio en 15%
- Actualización automática al hacer `commit()`

### Tecnologías
- **ObjectDB 2.8.1** (Base de datos orientada a objetos)
- **JPA 2.1** (Java Persistence API)
- **Maven** para gestión de dependencias

---

## 🎯 Ejercicio 2: Base de Datos Objeto-Relacional (Oracle)

### Descripción
Sistema de gestión de ciudadanos y trámites para un ayuntamiento usando **Oracle** con tipos objeto, referencias y colecciones.

### Archivo
- **`ejercicio2.sql`**: Script SQL completo con todas las definiciones e instrucciones

### Características Implementadas

#### A) Tipos Objeto
```sql
-- Tipo ciudadano
CREATE OR REPLACE TYPE Ciudadano_t AS OBJECT (
    dni         VARCHAR2(9),
    nombre      VARCHAR2(100),
    telefono    VARCHAR2(15)
);

-- Tipo colección VARRAY para documentos
CREATE OR REPLACE TYPE Documentos_va AS VARRAY(10) OF VARCHAR2(200);

-- Tipo trámite con referencia y colección
CREATE OR REPLACE TYPE Tramite_t AS OBJECT (
    id_tramite      NUMBER,
    fecha           DATE,
    estado          VARCHAR2(50),
    ciudadano_ref   REF Ciudadano_t,    -- Referencia
    documentos      Documentos_va        -- Colección
);
```

#### B) Tablas de Objetos
```sql
CREATE TABLE Ciudadanos OF Ciudadano_t (dni PRIMARY KEY);
CREATE TABLE Tramites OF Tramite_t (id_tramite PRIMARY KEY);
```

#### C) Inserción de Datos
- Inserción de ciudadano con constructor del tipo
- Inserción de trámite con referencia usando `SELECT REF(c)`
- Colección VARRAY con 2 documentos

#### D) Consulta con Desreferenciación
```sql
SELECT
    DEREF(t.ciudadano_ref).nombre AS nombre_ciudadano,
    t.id_tramite,
    t.estado,
    t.documentos AS documentos
FROM Tramites t;
```

#### E) Transacción
```sql
BEGIN
    UPDATE Tramites t
    SET t.estado = 'resuelto'
    WHERE t.id_tramite = 1;
    
    COMMIT;
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
END;
/
```

### Conceptos Clave
- **Tipos Objeto**: `CREATE TYPE ... AS OBJECT`
- **Referencias**: `REF tipo_t` y `SELECT REF(alias)`
- **Colecciones**: `VARRAY` (array de tamaño fijo)
- **Desreferenciación**: `DEREF(referencia).atributo`
- **Transacciones**: `BEGIN...COMMIT...ROLLBACK`
- **Criterio ACID**: Atomicidad, Consistencia, Aislamiento, Durabilidad

---

## 🚀 Ejecución

### Ejercicio 1 (Java/ObjectDB)

#### Requisitos
- Java 21 o superior
- Maven

#### Pasos
```bash
# 1. Compilar el proyecto
mvn clean compile

# 2. Ejecutar Main.java
mvn exec:java -Dexec.mainClass="simulacro.Main"
```

#### Salida Esperada
```
--- Cursos insertados correctamente ---

--- Cursos con precio > 100€ y nivel avanzado ---
Nombre: Java Avanzado | Horas: 120

--- Modificando curso C001 ---
Precio anterior: 150.0
Nuevo precio: 127.5
```

### Ejercicio 2 (Oracle SQL)

#### Requisitos
- Oracle Database 11g o superior
- SQL*Plus o herramienta similar

#### Pasos
```bash
# 1. Conectar a Oracle
sqlplus usuario/password@database

# 2. Ejecutar el script completo
@ejercicio2.sql
```

---

## 📚 Documentación de Referencia

Todos los ejercicios están basados en la teoría oficial:

- **Tema 3**: Herramientas de mapeo objeto-relacional (ORM/Hibernate)
  - Clases persistentes (pág. 60)
  - Sesiones y estados de objetos (pág. 64-65)
  - Consultas HQL (pág. 72-88)

- **Tema 4**: Bases de datos objeto-relacionales y orientadas a objetos
  - ObjectDB con JPA (pág. 18-30)
  - Tipos objeto en Oracle (pág. 47-60)
  - Referencias REF y DEREF (pág. 72-74, 85-86)
  - Colecciones VARRAY (pág. 77-78)
  - Transacciones y ACID (pág. 91-93)

Cada sección del código incluye comentarios con referencias específicas a las páginas de la teoría.

---

## 📝 Notas Importantes

### Ejercicio 1
- ✅ La clase `Curso` debe implementar `Serializable`
- ✅ Obligatorio tener constructor vacío para JPA
- ✅ `@Entity` y `@Id` son las anotaciones mínimas requeridas
- ✅ Las transacciones se gestionan con `getTransaction().begin()` y `commit()`

### Ejercicio 2
- ✅ Usamos **VARRAY** en lugar de NESTED TABLE (según indicación de la profesora)
- ✅ VARRAY no requiere `NESTED TABLE...STORE AS`
- ✅ La desreferenciación se hace con **DEREF** (no notación de punto)
- ✅ Las transacciones PL/SQL usan `BEGIN...COMMIT...EXCEPTION...ROLLBACK...END;/`

---

## ✅ Checklist de Evaluación

### Ejercicio 1
- [x] Clase `Curso` correctamente anotada (@Entity, @Id)
- [x] Implements Serializable
- [x] Constructor vacío + constructor con parámetros
- [x] Conexión a `academia.odb`
- [x] Inserción de 2 cursos dentro de transacción
- [x] Consulta JPQL con filtros (precio > 100 y nivel avanzado)
- [x] Modificación de precio en 15% dentro de transacción

### Ejercicio 2
- [x] Tipo objeto `Ciudadano_t` definido
- [x] Tipo colección `VARRAY` definido
- [x] Tipo objeto `Tramite_t` con REF y colección
- [x] Tablas de objetos creadas
- [x] Inserción de ciudadano
- [x] Inserción de trámite con referencia y 2 documentos
- [x] Consulta con desreferenciación (DEREF)
- [x] Transacción con COMMIT y ROLLBACK

---

## 👨‍💻 Autor
**Dani G.**  
DAM2 - Acceso a Datos  
Fecha: Febrero 2026

---

## 📄 Licencia
Este proyecto es material educativo para uso académico.

