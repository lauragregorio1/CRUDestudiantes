package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Controlador.Controller;
import Vista.Vista;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class Modelo {
    private Controller miControlador; // Instancia del controlador
    private Vista miVista; // Instancia de la vista
    private boolean existe; // Variable para verificar existencia

    // Métodos para establecer el controlador y la vista
    public void setControlador(Controller miControlador) {
        this.miControlador = miControlador;
    }

    public void setVista(Vista miVista) {
        this.miVista = miVista;
    }

    public boolean isExiste() {
        return existe;
    }

    // Configuración de datos de la base de datos
    private String db = "estudiantes"; // Nombre de la base de datos
    private String login = "root"; // Nombre de usuario para la conexión
    private String pwd = ""; // Contraseña para la conexión
    private String url = "jdbc:mysql://localhost/" + db
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // URL de conexión
    private Connection conexion; // Objeto de conexión

    // Método para establecer conexión con la base de datos
    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carga del driver JDBC
            conexion = DriverManager.getConnection(url, login, pwd); // Establecimiento de la conexión
            System.out.println("-> Conexion con MySQL establecida"); // Mensaje de éxito en la conexión
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC No encontrado"); // Mensaje en caso de no encontrar el driver
            e.printStackTrace(); // Imprimir detalles del error
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la BD"); // Mensaje en caso de error de conexión
            e.printStackTrace(); // Imprimir detalles del error
        } catch (Exception e) {
            System.out.println("Error general de Conexion"); // Mensaje en caso de error general de conexión
            e.printStackTrace(); // Imprimir detalles del error
        }
    }
    
    // Método para crear un estudiante en la base de datos
    public void crearEstudiante(String nombre, String apellidos, int edad, int curso, Label lblNotFound) {
        lblNotFound.setVisible(false); // Oculta el mensaje de "No encontrado"

        try {
            String queryEstudiante = "INSERT INTO estudiante (nombre, apellidos, edad, curso) VALUES (?, ?, ?, ?)";
            // Query para insertar un nuevo estudiante en la base de datos

            PreparedStatement pstmtEstudiante = conexion.prepareStatement(queryEstudiante); // Preparación de la consulta SQL
            pstmtEstudiante.setString(1, nombre); // Establece el nombre del estudiante
            pstmtEstudiante.setString(2, apellidos); // Establece los apellidos del estudiante
            pstmtEstudiante.setLong(3, edad); // Establece la edad del estudiante
            pstmtEstudiante.setLong(4, curso); // Establece el curso del estudiante
            pstmtEstudiante.executeUpdate(); // Ejecución de la consulta para insertar el estudiante

            pstmtEstudiante.close(); // Cierre del PreparedStatement

            // Llama al método initialize del controlador para actualizar la interfaz
            miControlador.initialize();

        } catch (SQLException e) {
            System.err.println(e.getMessage()); // Imprime el mensaje de error en caso de excepción SQL
        }
    }

    // Método para devolver la información de un estudiante en la base de datos
    public void devolverEstudiante(String nombre, String apellidos, int edad, int curso, TableView tablaEstudiantes, Label lblNotFound) {
        lblNotFound.setVisible(false); // Oculta el mensaje de "No encontrado"

        ArrayList<String[]> estudiantes = new ArrayList<>(); // Lista para almacenar datos de estudiantes

        try {
            String query = "SELECT nombre, apellidos, edad, curso FROM estudiante WHERE nombre = ? AND apellidos = ? AND edad = ? AND curso = ?";
            // Query para buscar un estudiante en la base de datos según los parámetros dados

            PreparedStatement pstmt = conexion.prepareStatement(query); // Preparación de la consulta SQL
            pstmt.setString(1, nombre); // Establece el nombre del estudiante a buscar
            pstmt.setString(2, apellidos); // Establece los apellidos del estudiante a buscar
            pstmt.setLong(3, edad); // Establece la edad del estudiante a buscar
            pstmt.setLong(4, curso); // Establece el curso del estudiante a buscar

            ResultSet rset = pstmt.executeQuery(); // Ejecuta la consulta y obtiene resultados

            if (!rset.next()) { // Verifica si no hay resultados
                lblNotFound.setVisible(true); // Muestra el mensaje de "No encontrado"
            } else {
                // Hubo resultados, procesar y mostrar en la tabla
                do {
                    // Obtiene los datos del estudiante
                    String nombreEstudiante = rset.getString("nombre");
                    String apellidosEstudiante = rset.getString("apellidos");
                    String edadEstudiante = rset.getString("edad");
                    String cursoEstudiante = rset.getString("curso");

                    // Agrega los datos a la lista de estudiantes
                    estudiantes.add(new String[]{nombreEstudiante, apellidosEstudiante, edadEstudiante, cursoEstudiante});
                } while (rset.next());

                miControlador.limpiarTabla(); // Limpia la tabla a través del controlador
                tablaEstudiantes.getItems().addAll(estudiantes); // Agrega los datos a la tabla
            }

            rset.close(); 
            pstmt.close(); 

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime detalles del error en caso de excepción SQL
        }
    }

    // Método para eliminar un estudiante en la base de datos
    public void eliminarEstudiante(String nombre, String apellidos, int edad, int curso, Label lblNotFound) {
        lblNotFound.setVisible(false); // Oculta el mensaje de "No encontrado"

        try {
            String queryEstudiante = "DELETE FROM estudiante WHERE nombre = ? and apellidos = ? and edad = ? and curso = ?";
            // Query para eliminar un estudiante de la base de datos

            PreparedStatement pstmtEstudiante = conexion.prepareStatement(queryEstudiante); // Preparación de la consulta SQL
            pstmtEstudiante.setString(1, nombre); // Establece el nombre del estudiante a eliminar
            pstmtEstudiante.setString(2, apellidos); // Establece los apellidos del estudiante a eliminar
            pstmtEstudiante.setLong(3, edad); // Establece la edad del estudiante a eliminar
            pstmtEstudiante.setLong(4, curso); // Establece el curso del estudiante a eliminar

            if (pstmtEstudiante.executeUpdate() == 0) { // Verifica si no se eliminó ningún estudiante
                lblNotFound.setVisible(true); // Muestra el mensaje de "No encontrado"
            }

            pstmtEstudiante.close(); // Cierra el PreparedStatement

            miControlador.initialize(); // Llama al método initialize del controlador para actualizar la interfaz

        } catch (SQLException e) {
            System.err.println(e.getMessage()); // Imprime el mensaje de error en caso de excepción SQL
        }
    }

    // Método para actualizar un estudiante en la base de datos
    public void actualizarEstudiante(String nombre, String apellidos, int edad, int curso, String nombreAntiguo, String apellidosAntiguo, int edadAntigua, int cursoAntiguo, Label lblNoSeEncuentra) {
        lblNoSeEncuentra.setVisible(false); // Oculta el mensaje de "No encontrado"

        try {
            String queryEstudiante = "UPDATE estudiante SET nombre = ?, apellidos = ?, edad = ?, curso = ? WHERE nombre = ? AND apellidos = ? AND edad = ? AND curso = ?";
            // Query para actualizar los datos de un estudiante en la base de datos

            PreparedStatement pstmtEstudiante = conexion.prepareStatement(queryEstudiante); // Preparación de la consulta SQL
            pstmtEstudiante.setString(1, nombre); // Establece el nuevo nombre del estudiante
            pstmtEstudiante.setString(2, apellidos); // Establece los nuevos apellidos del estudiante
            pstmtEstudiante.setLong(3, edad); // Establece la nueva edad del estudiante
            pstmtEstudiante.setLong(4, curso); // Establece el nuevo curso del estudiante
            pstmtEstudiante.setString(5, nombreAntiguo); // Establece el nombre antiguo del estudiante
            pstmtEstudiante.setString(6, apellidosAntiguo); // Establece los apellidos antiguos del estudiante
            pstmtEstudiante.setLong(7, edadAntigua); // Establece la edad antigua del estudiante
            pstmtEstudiante.setLong(8, cursoAntiguo); // Establece el curso antiguo del estudiante

            if (pstmtEstudiante.executeUpdate() == 0) { // Verifica si no se actualizó ningún estudiante
                lblNoSeEncuentra.setVisible(true); // Muestra el mensaje de "No encontrado"
            }

            pstmtEstudiante.close(); // Cierra el PreparedStatement


        } catch (SQLException e) {
            System.err.println(e.getMessage()); // Imprime el mensaje de error en caso de excepción SQL
        }
    }

    // Método para mostrar todos los estudiantes en la tabla de la interfaz
    public void mostrarTodos(TableView tablaEstudiantes) {

        try {
            String query = "SELECT nombre, apellidos, edad, curso FROM estudiante";
            // Consulta para seleccionar todos los estudiantes en la base de datos

            PreparedStatement pstmt = conexion.prepareStatement(query); // Preparación de la consulta SQL
            ResultSet rset = pstmt.executeQuery(); // Ejecución de la consulta y obtención de resultados

            ArrayList<String[]> datosEstudiantes = new ArrayList<>(); // Lista para almacenar los datos de los estudiantes

            while (rset.next()) {
                // Obtiene los datos de cada estudiante
                String nombre = rset.getString("nombre");
                String apellidos = rset.getString("apellidos");
                String edad = rset.getString("edad");
                String curso = rset.getString("curso");

                // Agrega los datos a la lista de estudiantes
                datosEstudiantes.add(new String[] { nombre, apellidos, edad, curso });
            }

            tablaEstudiantes.getItems().addAll(datosEstudiantes); // Agrega los datos a la tabla

            rset.close(); // Cierra el ResultSet
            pstmt.close(); // Cierra el PreparedStatement

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime detalles del error en caso de excepción SQL
        }
    }

    // Método para comprobar la existencia de un estudiante en la base de datos
    public void comprobarEstudiante(String nombre, String apellidos, int edad, int curso) {

        try {
            String query = "SELECT nombre, apellidos, edad, curso FROM estudiante WHERE nombre = ? AND apellidos = ? AND edad = ? AND curso = ?";
            // Consulta para buscar un estudiante en la base de datos según los parámetros dados

            PreparedStatement pstmt = conexion.prepareStatement(query); // Preparación de la consulta SQL
            pstmt.setString(1, nombre); // Establece el nombre del estudiante a buscar
            pstmt.setString(2, apellidos); // Establece los apellidos del estudiante a buscar
            pstmt.setLong(3, edad); // Establece la edad del estudiante a buscar
            pstmt.setLong(4, curso); // Establece el curso del estudiante a buscar

            ResultSet rset = pstmt.executeQuery(); // Ejecución de la consulta y obtención de resultados

            if (!rset.next()) { // Verifica si no hay resultados
                System.out.println("no existe"); // Mensaje si no se encuentra el estudiante
                existe = false; // Establece la variable existe como falso
            } else {
                existe = true; // Establece la variable existe como verdadero si se encuentra el estudiante
            }

            rset.close(); // Cierra el ResultSet
            pstmt.close(); // Cierra el PreparedStatement

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime detalles del error en caso de excepción SQL
        }
    }


}
