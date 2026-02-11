package simulacro;

import javax.persistence.*;
import java.io.Serializable;

/**
 * EJERCICIO 1 - APARTADO A)
 * Clase Curso para almacenarse de forma persistente en una base de datos
 * orientada a objetos (ObjectDB) usando JPA.
 *
 * Según la teoría del Tema 4 (pág. 18-19):
 * - ObjectDB usa JPA (Java Persistence API) para la persistencia de objetos.
 * - El modelo de clases es el propio esquema de la base de datos.
 * - Una clase persistente debe estar anotada con @Entity y tener @Id.
 *
 * Según la teoría del Tema 3 (pág. 60):
 * Las clases persistentes deben:
 * - Tener un constructor público sin argumentos.
 * - Tener métodos get/set para las propiedades a persistir.
 * - Implementar Serializable.
 */

// @Entity indica que esta clase es una entidad persistente (Tema 4, pág. 21)
@Entity
public class Curso implements Serializable {

    // Atributo identificador (OID - Object Identifier)
    // Según Tema 4 (pág. 6): "Cada objeto tiene un identificador único"
    // @Id marca este atributo como la clave primaria del objeto
    @Id
    private String codigo;

    // Atributos que modelan los datos del curso (Tema 4, pág. 48-50)
    private String nombre;
    private int numeroHoras;
    private double precio;
    private String nivel;  // "básico", "intermedio" o "avanzado"

    /**
     * Constructor vacío - OBLIGATORIO para JPA/ObjectDB
     * Según Tema 3 (pág. 60): "Deben tener un constructor público sin ningún tipo de argumentos"
     */
    public Curso() {
    }

    /**
     * Constructor con parámetros para facilitar la creación de objetos
     * Similar al constructor por defecto de los tipos objeto en Oracle (Tema 4, pág. 55)
     */
    public Curso(String codigo, String nombre, int numeroHoras, double precio, String nivel) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.numeroHoras = numeroHoras;
        this.precio = precio;
        this.nivel = nivel;
    }

    // Nota del enunciado: "No es necesario escribir getters y setters completos"
    // Según Tema 3 (pág. 60): "Para cada propiedad que queramos persistir debe haber un método get/set"
    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getNumeroHoras() { return numeroHoras; }
    public double getPrecio() { return precio; }
    public String getNivel() { return nivel; }

    // Setters (al menos para los atributos que se modificarán)
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNumeroHoras(int numeroHoras) { this.numeroHoras = numeroHoras; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setNivel(String nivel) { this.nivel = nivel; }
}