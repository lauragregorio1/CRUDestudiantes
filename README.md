# DOCUMENTACIÓN REGISTRO DE ESTUDIANTES


## Descripción General:

La aplicación de ‘Registro de Estudiantes’ es una herramienta desarrollada en JavaFX para gestionar y mantener un registro completo de estudiantes. Proporciona funcionalidades de Crear, Leer, Actualizar y Eliminar (CRUD) datos de estudiantes, permitiendo a los usuarios administrar la información de manera eficiente.

**Funcionalidades Principales:**

-Creación de Estudiantes: Permite agregar nuevos estudiantes con información detallada, incluyendo nombre, apellidos, edad y curso. Cada estudiante tiene un id único en la base de datos autogenerado.

-Visualización de Estudiantes: Muestra una lista completa de todos los estudiantes registrados en una tabla, facilitando la consulta y revisión de la información.

-Actualización de Datos: Ofrece la capacidad de modificar los datos de un estudiante existente, permitiendo cambios en nombre, apellidos, edad o curso.

-Eliminación de Estudiantes: Permite eliminar registros de estudiantes según los parámetros proporcionados.













## Diagrama de clases:

![](Aspose.Words.f103e425-52d9-4abd-84fc-1d583e87b679.001.png)

**Configuración de la Conexión JDBC:**

La aplicación utiliza JDBC (Java Database Connectivity) para interactuar con una base de datos MySQL local. La conexión JDBC se establece con la base de datos "estudiantes" en el servidor local. Se utiliza una URL JDBC para conectarse, y se emplea la librería ‘mysql-connector-java’ para comunicarse con la base de datos.

URL de Conexión: La URL de conexión está construida utilizando la dirección local del servidor MySQL y el nombre de la base de datos a la que se accederá. La URL se crea concatenando la dirección jdbc:mysql://localhost/ con el nombre de la base de datos (estudiantes). Además, incluye parámetros como useUnicode, useJDBCCompliantTimezoneShift, useLegacyDatetimeCode, y serverTimezone que ajustan la configuración del controlador JDBC.

Credenciales de Usuario: Se utilizan las credenciales predeterminadas (root como nombre de usuario y una contraseña vacía "") para la autenticación y acceso a la base de datos.

Driver JDBC: La carga del controlador o driver JDBC (com.mysql.cj.jdbc.Driver) se realiza mediante Class.forName() para permitir la comunicación entre la aplicación Java y el servidor MySQL.

Método conectar(): El método conectar() se encarga de establecer la conexión con la base de datos. Algunos aspectos a destacar:

Manejo de Excepciones: El código está envuelto en un bloque try-catch para manejar posibles excepciones. Tres excepciones específicas son capturadas y manejadas:

ClassNotFoundException: Se maneja si no se puede encontrar el controlador JDBC. Imprime un mensaje y los detalles del error.

SQLException: Captura errores relacionados con la conexión, como problemas con la URL, credenciales incorrectas, entre otros. También imprime un mensaje y los detalles del error.

Exception: Captura errores generales de conexión y también imprime un mensaje y los detalles del error.

Establecimiento de la Conexión: Utiliza DriverManager.getConnection() para conectarse a la base de datos. Se pasa la URL de conexión, el nombre de usuario y la contraseña como argumentos para establecer la conexión.

Mensajes de Éxito o Error: Imprime mensajes indicando si la conexión se estableció correctamente o si se produjo algún error en el proceso.

## Implementación de Métodos CRUD:

Los métodos CRUD se han implementado en la clase Modelo, encargada de interactuar con la base de datos mediante consultas SQL.

- Método crearEstudiante:

Este método se encarga de insertar un nuevo registro de estudiante en la base de datos. Algunos puntos clave a tener en cuenta:

Preparación de la Consulta: Se prepara una consulta SQL de tipo INSERT INTO que inserta un nuevo estudiante en la tabla correspondiente con los datos proporcionados (nombre, apellidos, edad y curso).

Uso de PreparedStatement: Se utiliza un PreparedStatement para ejecutar la consulta SQL preparada. Los datos del estudiante se establecen en el PreparedStatement utilizando métodos como setString() y setLong().

Ejecución de la Consulta: Se ejecuta la consulta mediante executeUpdate() que realiza la inserción en la base de datos.

Actualización de la Interfaz: Luego de la inserción, se actualiza la interfaz a través del controlador (miControlador.initialize()) para reflejar los cambios en la vista.

- Método devolverEstudiante:

Este método busca un estudiante específico en la base de datos según los parámetros proporcionados. Puntos destacados:

Preparación de la Consulta: Se prepara una consulta SQL de tipo SELECT con condiciones para buscar un estudiante específico según nombre, apellidos, edad y curso.

Uso de PreparedStatement: Se utiliza un PreparedStatement para ejecutar la consulta SQL preparada. Los datos del estudiante se establecen en el PreparedStatement utilizando métodos como setString() y setLong().

Manejo de Resultados: Se ejecuta la consulta y se obtiene un ResultSet con los resultados. Se verifica si el estudiante existe y se agregan los resultados a una lista (estudiantes).

Actualización de la Interfaz: Si se encuentra el estudiante, se actualiza la tabla en la interfaz con los datos del estudiante encontrado y, si no se encontró el estudiante, se muestra un mensaje en la interfaz.

- Método eliminarEstudiante:

Este método elimina un registro de estudiante de la base de datos según los parámetros proporcionados. Puntos clave:

Preparación de la Consulta: Se prepara una consulta SQL de tipo DELETE FROM con condiciones para eliminar un estudiante específico.

Uso de PreparedStatement: Se utiliza un PreparedStatement para ejecutar la consulta SQL preparada. Los datos del estudiante se establecen en el PreparedStatement utilizando métodos como setString() y setLong().

Verificación de Eliminación: Se verifica si la eliminación fue exitosa y, si no se encontró el estudiante, se muestra un mensaje en la interfaz. Si se encontró, se actualiza la interfaz a través del controlador (miControlador.initialize()) para reflejar los cambios en la vista.

- Método actualizarEstudiante:

Este método actualiza los datos de un estudiante existente en la base de datos. Puntos clave:

Preparación de la Consulta: Se prepara una consulta SQL de tipo UPDATE con condiciones para actualizar los datos de un estudiante.

Uso de PreparedStatement: Se utiliza un PreparedStatement para ejecutar la consulta SQL preparada. Los datos del estudiante (antiguos y nuevos) se establecen en el PreparedStatement utilizando métodos como setString() y setLong().

Verificación de la Actualización: Se verifica si la actualización fue exitosa y se muestra un mensaje en la interfaz si no se encuentra el estudiante. Si es exitosase actualiza la interfaz para reflejar los cambios en la vista.


**Otros métodos:**

- Método comprobarEstudiante:

Este método se encarga de verificar la existencia de un estudiante en la base de datos según los parámetros proporcionados para abrir o no la ventana de actualizar. Puntos clave a considerar:

Preparación de la Consulta: Se prepara una consulta SQL de tipo SELECT con condiciones para buscar un estudiante específico según nombre, apellidos, edad y curso.

Uso de PreparedStatement: Se utiliza un PreparedStatement para ejecutar la consulta SQL preparada. Los parámetros del estudiante se establecen en el PreparedStatement.

Manejo de Resultados: Se ejecuta la consulta y se obtiene un ResultSet con los resultados. Se verifica si el estudiante existe y se actualiza el valor de existe a true o false en función de si se encontró o no el estudiante.

- Método mostrarTodos:

Este método recupera todos los registros de estudiantes almacenados en la base de datos y los muestra en la tabla de la interfaz. Aspectos destacados:

Preparación de la Consulta: Se prepara una consulta SQL de tipo SELECT sin condiciones, lo que devuelve todos los registros de la tabla estudiante.

Uso de PreparedStatement: Se utiliza un PreparedStatement para ejecutar la consulta SQL preparada.

Recuperación de Resultados: Se ejecuta la consulta y se obtiene un ResultSet con todos los registros de estudiantes.

Actualización de la Interfaz: Los datos recuperados se agregan a una lista (datosEstudiantes) y luego se añaden a la tabla de la interfaz, mostrando así todos los registros de estudiantes. https://www.youtube.com/watch?v=rs0ALuSzcgA

**Conclusión:**

La aplicación de Registro de Estudiantes ofrece una interfaz fácil de usar para la gestión de información estudiantil. La combinación de la conectividad JDBC con la base de datos y la implementación de métodos CRUD proporciona una herramienta versátil y robusta para mantener un registro de estudiantes actualizado y preciso.

## Referencias externas:

JavaFX:
<https://code.makery.ch/es/library/javafx-tutorial/part1>

<https://www.youtube.com/watch?v=rs0ALuSzcgA>


JDBC:

<https://www.mysqltutorial.org/mysql-jdbc-tutorial/>

<https://programandoointentandolo.com/2013/09/tutorial-jdbc-con-aplicaciones-de-ejemplo.html>
