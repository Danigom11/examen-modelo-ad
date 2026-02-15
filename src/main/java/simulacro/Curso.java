package simulacro;

import javax.persistence.*;
import java.io.Serializable;

/**
 * EJERCICIO 1 - APARTADO A)
 */

// @Entity indica que esta clase es una entidad persistente
@Entity
public class Curso implements Serializable {

    // @Id marca este atributo como la clave primaria del objeto
    @Id
    private String codigo;

    private String nombre;
    private int numeroHoras;
    private double precio;
    private String nivel;  // "básico", "intermedio" o "avanzado"

    /**
     * Constructor vacío - OBLIGATORIO para JPA/ObjectDB
     */
    public Curso() {
    }

    /**
     * Constructor con parámetros para facilitar la creación de objetos
     */
    public Curso(String codigo, String nombre, int numeroHoras, double precio, String nivel) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.numeroHoras = numeroHoras;
        this.precio = precio;
        this.nivel = nivel;
    }

    // No hay que poner los getters ni setters, ni siquiera en un comentario
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getNumeroHoras() { return numeroHoras; }
    public double getPrecio() { return precio; }
    public String getNivel() { return nivel; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNumeroHoras(int numeroHoras) { this.numeroHoras = numeroHoras; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setNivel(String nivel) { this.nivel = nivel; }
}