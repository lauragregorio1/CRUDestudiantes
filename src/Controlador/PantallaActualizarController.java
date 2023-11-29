package Controlador;

import Modelo.Modelo;
import Vista.Vista;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PantallaActualizarController {
	// Declaración de instancias y variables para datos antiguos
    private Modelo miModelo = new Modelo(); // Instancia del modelo
    private Controller miController = new Controller(); // Instancia del controlador principal
    private String nombreAntiguo; // Nombre antiguo del estudiante
    private String apellidosAntiguo; // Apellidos antiguos del estudiante
    private int edadAntigua; // Edad antigua del estudiante
    private int cursoAntiguo; // Curso antiguo del estudiante

    // Elementos de la interfaz de usuario usando @FXML
	@FXML
	private TableView<String[]> tablaEstudiantes;
	@FXML
	private Button btnActualizar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TextField nombreNuevo;
	@FXML
	private TextField apellidoNuevo;
	@FXML
	private TextField edadNueva;
	@FXML
	private TextField cursoNuevo;
	@FXML
	private Node raiz;
	@FXML
	private Label lblNoSeEncuentra;
	@FXML
	private Label lblNoSeEncuentraPantAnterior;
	@FXML
	private TableColumn<String[], String> colNombre;

	@FXML
	private TableColumn<String[], String> colApellidos;

	@FXML
	private TableColumn<String[], String> colEdad;

	@FXML
	private TableColumn<String[], String> colCurso;

    // Métodos para inicializar datos y elementos de la interfaz (recibe datos de la clase Controller)
	public void initData(String nombre, String apellidos, int edad, int curso, TableView<String[]> tablaEstudiantes, TableColumn<String[], String> colNombre, TableColumn<String[], String> colApellidos, TableColumn<String[], String> colEdad, TableColumn<String[], String> colCurso, Label lblNotFound) {
        // Inicialización de datos antiguos y elementos de la interfaz
		this.nombreAntiguo = nombre;
		this.apellidosAntiguo = apellidos;
		this.edadAntigua = edad;
		this.cursoAntiguo = curso;
		this.tablaEstudiantes = tablaEstudiantes;
		this.colNombre = colNombre;
		this.colApellidos = colApellidos;
		this.colEdad = colEdad;
		this.colCurso = colCurso;
		this.lblNoSeEncuentraPantAnterior =lblNotFound;
		initialize(nombre, apellidos, edad, curso);

	}

    // Método de inicialización de la pantalla de actualización
	@FXML
	public void initialize(String nombre, String apellidos, int edad, int curso) {
        // Establece los datos antiguos en los campos de texto
		nombreNuevo.setText(nombre);
		apellidoNuevo.setText(apellidos);
		edadNueva.setText(Integer.toString(edad));
		cursoNuevo.setText(Integer.toString(curso));
	}

    // Método para realizar la actualización de datos del estudiante
	@FXML
	public void actualizar() {
        // Obtención de texto de los campos
		lblNoSeEncuentraPantAnterior.setVisible(false);
		String nombreTexto = nombreNuevo.getText();
		String apellidosTexto = apellidoNuevo.getText();
		String edadTexto = edadNueva.getText();
		String cursoTexto = cursoNuevo.getText();
        // Validación de los campos
		boolean camposCorrectos = (nombreTexto instanceof String) && (apellidosTexto instanceof String)
				&& esNumero(edadTexto) && esNumero(cursoTexto);

		if (camposCorrectos) {

			miModelo.conectar();
	        // Actualización del estudiante en el modelo y manipulación de la interfaz
			miModelo.actualizarEstudiante(nombreNuevo.getText(), apellidoNuevo.getText(),
					Integer.parseInt(edadNueva.getText()), Integer.parseInt(cursoNuevo.getText()), this.nombreAntiguo,
					this.apellidosAntiguo, this.edadAntigua, this.cursoAntiguo, this.lblNoSeEncuentra);
			Stage escenario = (Stage) raiz.getScene().getWindow(); //Referencia al panel de esta pantalla
			escenario.close(); // Lo cierro
			
			tablaEstudiantes.getItems().clear(); // Se limpia la tabla estudiantes
			// Se vuleven a rellenar las celdas de la tabla para tener la nueva información del estudiante actualizada
			colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
			colApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
			colEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
			colCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
			miModelo.conectar();
			miModelo.mostrarTodos(tablaEstudiantes);

		} else {
			// Si los campos son incorrectos se avisará con esta etiqueta

			lblNoSeEncuentra.setVisible(true);
		}

	}

    // Método para cancelar la operación y cerrar la ventana
	@FXML
	public void cancelar() {
		Stage escenario = (Stage) raiz.getScene().getWindow();
        // Cierre de la ventana de actualización
		escenario.close();
	}

    // Método para verificar si un texto es numérico
	private boolean esNumero(String texto) {
        // Intento de convertir el texto a un número entero y manejo de excepciones
		try {
			Integer.parseInt(texto);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
