package simulacro;

import javax.persistence.*;
import java.util.List;

/**
 * EJERCICIO 1 - APARTADOS B, C y D
 * Operaciones con ObjectDB usando JPA
 * <p>
 * Según Tema 4 (pág. 21-25):
 * - EntityManagerFactory representa la base de datos
 * - EntityManager representa una conexión a la base de datos
 * - Las operaciones de modificación requieren una transacción activa
 */
public class Main {
    public static void main(String[] args) {

        // =====================================================================
        // APARTADO B) Conexión e inserción de cursos
        // =====================================================================
        // 1. Conectarse a una base de datos ObjectDB llamada academia.odb
        // Según Tema 4 (pág. 22): "Podemos proporcionar la ruta del archivo de la base de datos"
        // ObjectDB creará el archivo automáticamente si no existe
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("academia.odb");

        // 2. Obtener EntityManager - representa la conexión a la BD (Tema 4, pág. 24)
        EntityManager em = emf.createEntityManager();

        try {
            // =====================================================================
            // INSERTAR DOS CURSOS (Apartado B)
            // =====================================================================
            // Según Tema 4 (pág. 25): "Las operaciones que modifican el contenido de la base
            // de datos solo deben realizarse dentro de una transacción activa"

            // 2. Iniciar una transacción
            em.getTransaction().begin();

            // 3. Insertar dos cursos distintos
            // Según Tema 4 (pág. 29): "Para insertar un objeto nuevo, basta con crearlo y persistirlo"
            Curso c1 = new Curso("C001", "Java Avanzado", 120, 150.0, "avanzado");
            Curso c2 = new Curso("C002", "Python Básico", 40, 50.0, "básico");

            // persist() convierte los objetos en objetos gestionados (persistentes)
            em.persist(c1);
            em.persist(c2);

            // 4. Confirmar la transacción (Tema 4, pág. 25)
            // "Las actualizaciones se aplican a la base de datos cuando se confirma la transacción"
            em.getTransaction().commit();

            System.out.println("--- Cursos insertados correctamente ---");

            // =====================================================================
            // APARTADO C) Consulta orientada a objetos (JPQL/OQL)
            // =====================================================================
            // Según Tema 4 (pág. 26-27): Se puede usar JPQL para consultas
            // "La sintaxis es similar a SQL, pero se usan nombres de CLASES y ATRIBUTOS"
            //
            // Según Tema 4 (pág. 35-36 sobre OQL):
            // "SELECT p.nombre, p.email FROM p IN Profesor WHERE p.ingreso <= 1990"
            // En JPQL/ObjectDB la sintaxis es similar pero con FROM Clase alias

            // Aquí comenzaría otro try/catch (no obligatorio, solo indicarlo en un comentario)

            // Consulta: Cursos con precio > 100 y nivel "avanzado"
            String jpql = "SELECT c FROM Curso c WHERE c.precio > 100 AND c.nivel = 'avanzado'";

            // Según Tema 4 (pág. 26): "TypedQuery es la forma preferida de trabajar con consultas"
            // También acepta Query
            TypedQuery<Curso> query = em.createQuery(jpql, Curso.class);
            List<Curso> resultados = query.getResultList();

            // Mostrar nombre y número de horas de cada curso obtenido
            System.out.println("\n--- Cursos con precio > 100€ y nivel avanzado ---");
            for (Curso c : resultados) {
                System.out.println("Nombre: " + c.getNombre() + " | Horas: " + c.getNumeroHoras());
            }

            // =====================================================================
            // APARTADO D) Localizar y modificar un curso
            // =====================================================================
            // Según Tema 4 (pág. 30): "Para poder ser actualizados los objetos, éstos deben
            // de haber sido recuperados en la misma sesión"

            // Aquí comenzaría otro try/catch (no obligatorio, solo indicarlo en un comentario)

            // Iniciar transacción para la modificación
            em.getTransaction().begin();

            // Localizar el curso por su código usando find() (Tema 4, pág. 26)
            // "Para recuperar un objeto a partir de la clave identificativa utilizaremos find()"
            Curso cursoAModificar = em.find(Curso.class, "C001");

            if (cursoAModificar != null) {
                System.out.println("\n--- Modificando curso C001 ---");
                System.out.println("Precio anterior: " + cursoAModificar.getPrecio());

                // Reducir precio en 15% (quedarse con el 85%)
                // Según Tema 4 (pág. 30): "El EntityManager detecta automáticamente
                // los cambios y los aplica cuando se confirma la transacción"
                double nuevoPrecio = cursoAModificar.getPrecio() * 0.85;
                cursoAModificar.setPrecio(nuevoPrecio);

                System.out.println("Nuevo precio: " + cursoAModificar.getPrecio());
            }

            // Confirmar la transacción (los cambios se guardan)
            em.getTransaction().commit();


            // Cerrar recursos (Tema 4, pág. 23-24)
            // "Cuando la conexión a la base de datos ya no es necesaria, se puede cerrar"
            em.close();
            // "EntityManagerFactory también se usa para cerrar la base de datos"
            emf.close();

        } catch (Exception e) {
            // Aquí sería el rollback si la transacción no se produce
            System.out.println(e.getMessage());

        }
    }
}