package simulacro;

import javax.persistence.*;
import java.util.List;

/**
 * EJERCICIO 1 - APARTADOS B, C y D
 */
public class Main {
    public static void main(String[] args) {

        // El EntityManagerFactory es la FÁBRICA que crea los gestores de entidades
        // Se crea UNA VEZ al inicio y se comparte para todas las operaciones
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("academia.odb");
        EntityManager em = emf.createEntityManager();

        /* =====================================================================
        // APARTADO B) Conexión e inserción de cursos
        // =====================================================================
        */
        try {
            // Iniciar una transacción (le pedimos al EntityManager las herramientas de transacción)
            em.getTransaction().begin();

            // Insertar dos cursos distintos
            Curso c1 = new Curso("1", "Java Avanzado", 120, 150.0, "avanzado");
            Curso c2 = new Curso("2", "Python Básico", 40, 50.0, "básico");

            // persist() convierte los objetos en objetos gestionados (persistentes)
            em.persist(c1);
            em.persist(c2);

            // Confirmar la transacción
            em.getTransaction().commit();
            System.out.println("✓ Apartado B completado: Cursos insertados correctamente");

        } catch (Exception e) {
            // Si algo falla, hacemos rollback para deshacer los cambios
            em.getTransaction().rollback();
            System.out.println("✗ Error en apartado B: " + e.getMessage());
        }

        /* =====================================================================
        // APARTADO C) Consulta orientada a objetos (JPQL/OQL)
        // =====================================================================
        */
        try {
            // Consulta: Cursos con precio > 100 y nivel "avanzado"
            String jpql = "SELECT c FROM Curso c WHERE c.precio > 100 AND c.nivel = 'avanzado'";
            // También acepta Query
            TypedQuery<Curso> query = em.createQuery(jpql, Curso.class);
            List<Curso> resultados = query.getResultList();

            // Mostrar nombre y número de horas de cada curso obtenido
            System.out.println("\n✓ Apartado C - Resultados de la consulta:");
            for (Curso c : resultados) {
                System.out.println("  Nombre: " + c.getNombre() + " | Horas: " + c.getNumeroHoras());
            }

        } catch (Exception e) {
            // Las consultas normalmente no necesitan rollback, pero controlamos el error
            System.out.println("✗ Error en apartado C: " + e.getMessage());
        }

        /* =====================================================================
        // APARTADO D) Localizar y modificar un curso
        // =====================================================================
        */
        try {
            // Iniciar transacción para la modificación
            em.getTransaction().begin();

            // Localizar el curso por su código usando find()
            Curso cursoAModificar = em.find(Curso.class, "C001");
            // No es necesario controlar con un if que no sea null, lo acepta así
            cursoAModificar.setPrecio(cursoAModificar.getPrecio() * 0.85);

            // Confirmar la transacción (los cambios se guardan)
            em.getTransaction().commit();
            System.out.println("\n✓ Apartado D completado: Precio modificado correctamente");

        } catch (Exception e) {
            // Si algo falla, hacemos rollback para deshacer los cambios
            em.getTransaction().rollback();
            System.out.println("✗ Error en apartado D: " + e.getMessage());
        }

        // Cerrar recursos al final de todo
        em.close();
        emf.close();
    }
}