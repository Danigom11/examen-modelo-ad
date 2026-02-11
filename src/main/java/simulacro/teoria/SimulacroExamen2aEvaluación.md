
--- Página 1 ---
DAM2  Acceso a Datos 
SIMULACRO DE EXAMEN – 2ª EVALUACIÓN 
 
1. Una academia de formación online quiere comenzar a almacenar la información 
de sus cursos utilizando una base de datos orientada a objetos, de manera que los datos 
se gestionen directamente como objetos persistentes desde una aplicación Java.  
De cada curso se desea almacenar la siguiente información: 
 Código del curso. 
 Nombre del curso. 
 Número de horas. 
 Precio. 
 Nivel (básico, intermedio o avanzado). 
 
Utilizando Java y ObjectDB, se pide realizar las siguientes tareas: 
 
a) Escribe la definición de la clase Curso para que pueda ser almacenada de 
forma persistente en una base de datos orientada a objetos.  
La clase deberá: 
 Estar correctamente anotada para su persistencia. 
 Tener un atributo identificador. 
 Incluir los atributos necesarios. 
 Disponer de un constructor vacío y otro con parámetros. 
 
Nota: No es necesario escribir getters y setters completos.  
 
b) Escribe el código Java (de forma esquemática) necesario para: 
 Conectarse a una base de datos ObjectDB llamada academia.odb. 
 Iniciar una transacción. 
 Insertar dos cursos distintos  en la base de datos. 
 Confirmar la transacción. 
 
Nota: Indica claramente los pasos que se siguen en el proceso. 
 
c) Escribe una consulta orientada a objetos que permita obtener todos los cursos 
que: 
 Tengan un precio superior a 100 euros y 
 Sean de nivel "avanzado". 
 
Nota: Indica cómo se mostraría por pantalla el nombre y el número de horas de 
cada curso obtenido.  
 
--- Página 2 ---
d) Escribe el código necesario para localizar un curso a partir de su código y 
reducir su precio en 15 %. 
Nota: La modificación deberá realizarse correctamente dentro de una 
transacción. 
 
 
 
 
2. Un ayuntamiento está desarrollando una aplicación para gestionar información 
sobre ciudadanos y los trámites que realizan. Para ello ha decidido utilizar una base de 
datos objeto-relacional, aprovechando las ventajas de los tipos objeto, las referencias y 
las colecciones.  
Cada ciudadano puede realizar varios trámites, y cada trámite puede incluir una 
serie de documentos asociados. 
a) Escribe las sentencias SQL necesarias para definir: 
1. Un tipo objeto Ciudadano_t, que almacene: 
o DNI. 
o Nombre. 
o Teléfono. 
 
2. Un tipo objeto Tramite_t, que almacene: 
o Identificador del trámite. 
o Fecha. 
o Estado del trámite. 
o Una referencia al ciudadano que lo realiza. 
o Una colección de documentos (nombre de archivo). 
 
b) A partir de los tipos definidos anteriormente, escribe las sentencias SQL 
necesarias para crear las tablas de objetos correspondientes. 
Nota: Indica claramente cómo se almacenará la colección de 
documentos asociada a cada trámite.  
 
c) Escribe el código SQL necesario para: 
 Insertar un ciudadano. 
 Insertar un trámite asociado a ese ciudadano. 
 Incluir al menos dos documentos en la colección del trámite. 
 
Nota: No es necesario que los datos sean completamente realistas, pero 
sí que se vea claramente el uso de referencias y colecciones.  
 
 
--- Página 3 ---
d) Escribe una consulta que muestre: 
 El nombre del ciudadano. 
 El identificador del trámite. 
 El estado del trámite. 
 Los documentos asociados. 
 
Nota: Para obtener los datos del ciudadano deberá utilizarse 
correctamente la desreferenciación de objetos.  
 
e) Escribe las sentencias necesarias para: 
 Cambiar el estado de un trámite (por ejemplo, de "pendiente" a 
"resuelto"). 
 Realizar la operación dentro de una transacción. 
 Indicar qué sentencia permitiría deshacer los cambios en caso de error. 