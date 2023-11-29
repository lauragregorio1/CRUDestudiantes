package Controlador;

import java.beans.EventHandler;
import java.io.IOException;

import Modelo.Modelo;
import Vista.Vista;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
    // Declaración de instancias para el modelo y la vista
	private Modelo miModelo = new Modelo(); // Instancia del modelo
	private Vista miVista; // Instancia de la vista
	
    // Métodos para establecer la vista y el modelo
	public void setVista(Vista miVista) {
		this.miVista = miVista;
	}

	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

    // Declaración de elementos de la interfaz gráfica usando @FXML
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnBuscar;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnEliminar;
	@FXML
	private TextField nombre;
	@FXML
	private TextField apellidos;
	@FXML
	private TextField edad;
	@FXML
	private TextField curso;
	@FXML
	private TableView<String[]> tablaEstudiantes;
	@FXML
	private AnchorPane paneTitulo;

	@FXML
	private TableColumn<String[], String> colNombre;

	@FXML
	private TableColumn<String[], String> colApellidos;

	@FXML
	private TableColumn<String[], String> colEdad;

	@FXML
	private TableColumn<String[], String> colCurso;

	@FXML
	private Label lblNotFound;

    // Método de inicialización de la pantalla
	@FXML
	public void initialize() {
        // Establece el controlador para el modelo
		miModelo.setControlador(this);
        // Limpia la tabla de estudiantes
		tablaEstudiantes.getItems().clear();
        // Configuración de las celdas de la tabla
		colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
		colApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
		colEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
		colCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        // Conexión al modelo y muestra todos los estudiantes en la tabla
		miModelo.conectar();
		miModelo.mostrarTodos(tablaEstudiantes);
	}

	// Métodos para realizar acciones específicas

    // Método para limpiar la tabla
	@FXML
	public void limpiarTabla() {
		tablaEstudiantes.getItems().clear();
	}
    // Método para buscar estudiantes
	@FXML
	public void buscar() {
        // Obtención de texto de los campos de búsqueda
		String nombreTexto = nombre.getText();
		String apellidosTexto = apellidos.getText();
		String edadTexto = edad.getText();
		String cursoTexto = curso.getText();
        // Validación de los campos de búsqueda
		boolean camposCorrectos = (nombreTexto instanceof String) && (apellidosTexto instanceof String)
				&& esNumero(edadTexto) && esNumero(cursoTexto);
		if (camposCorrectos) {

			miModelo.conectar();
	        // Realización de la búsqueda en el modelo
			miModelo.devolverEstudiante(nombre.getText(), apellidos.getText(), Integer.parseInt(edad.getText()),
					Integer.parseInt(curso.getText()), tablaEstudiantes, lblNotFound);
	        // Configuración de celdas, limpieza de campos si la búsqueda fue exitosa
			colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
			colApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
			colEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
			colCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
			nombre.setText("");
			apellidos.setText("");
			edad.setText("");
			curso.setText("");
		} else {
			// Si los campos son incorrectos se avisará con esta etiqueta
			lblNotFound.setVisible(true);
			initialize();
		}

	}

    // Método para actualizar información de un estudiante
	@FXML
	public void actualizar() {
        // Obtención de texto de los campos
		String nombreTexto = nombre.getText();
		String apellidosTexto = apellidos.getText();
		String edadTexto = edad.getText();
		String cursoTexto = curso.getText();
        // Validación de los campos de búsqueda
		boolean camposCorrectos = (nombreTexto instanceof String) && (apellidosTexto instanceof String)
				&& esNumero(edadTexto) && esNumero(cursoTexto);

		if (camposCorrectos) {
			miModelo.comprobarEstudiante(nombre.getText(), apellidos.getText(), Integer.parseInt(edad.getText()),
					Integer.parseInt(curso.getText()));
	        // Comprobación de existencia del estudiante y carga de una nueva ventana para actualizar
			boolean estudianteExiste = miModelo.isExiste();
			if (estudianteExiste) {
				try {
			        // Manipulación de la nueva ventana y limpieza de campos
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/update.fxml")); // Archivo fxml (interfaz)
					Parent root = loader.load();
					root.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm()); //Le asigno su archivo CSS

					// Accede al controlador de la nueva ventana si es necesario
					PantallaActualizarController pantallaActualizarController = loader.getController();
					// Le pasa datos necesarios a la nueva ventana
					pantallaActualizarController.initData(nombre.getText(), apellidos.getText(),
							Integer.parseInt(edad.getText()), Integer.parseInt(curso.getText()), this.tablaEstudiantes,  colNombre, colApellidos, colEdad, colCurso, lblNotFound);

					// Crea una nueva ventana (Stage)
					Stage stage = new Stage();
					stage.setTitle("Ventana actualizar");
					stage.setScene(new Scene(root));

					// Muestra la nueva ventana
					stage.show();
					nombre.setText("");
					apellidos.setText("");
					edad.setText("");
					curso.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else {
			lblNotFound.setVisible(true);		

		}
		} else {
			// Si los campos son incorrectos se avisará con esta etiqueta
			lblNotFound.setVisible(true);		
		}
	}
	
    // Método para agregar un nuevo estudiante
	@FXML
	public void anadir() {
        // Obtención de texto de los campos
		String nombreTexto = nombre.getText();
		String apellidosTexto = apellidos.getText();
		String edadTexto = edad.getText();
		String cursoTexto = curso.getText();
        // Validación de los campos
		boolean camposCorrectos = (nombreTexto instanceof String) && (apellidosTexto instanceof String)
				&& esNumero(edadTexto) && esNumero(cursoTexto);

		if (camposCorrectos) {
			miModelo.conectar();
	        // Creación de un nuevo estudiante en el modelo y limpieza de campos
			miModelo.crearEstudiante(nombre.getText(), apellidos.getText(), Integer.parseInt(edad.getText()),
					Integer.parseInt(curso.getText()), lblNotFound);
			nombre.setText("");
			apellidos.setText("");
			edad.setText("");
			curso.setText("");
		} else {
			// Si los campos son incorrectos se avisará con esta etiqueta
			lblNotFound.setVisible(true);
		}
	}

    // Método para eliminar un estudiante existente
	@FXML
	public void eliminar() {
        // Obtención de texto de los campos
		String nombreTexto = nombre.getText();
		String apellidosTexto = apellidos.getText();
		String edadTexto = edad.getText();
		String cursoTexto = curso.getText();
        // Validación de los campos
		boolean camposCorrectos = (nombreTexto instanceof String) && (apellidosTexto instanceof String)
				&& esNumero(edadTexto) && esNumero(cursoTexto);

		if (camposCorrectos) {

			miModelo.conectar();
	        // Eliminación del estudiante del modelo y limpieza de campos
			miModelo.eliminarEstudiante(nombre.getText(), apellidos.getText(), Integer.parseInt(edad.getText()),
					Integer.parseInt(curso.getText()), lblNotFound);
			nombre.setText("");
			apellidos.setText("");
			edad.setText("");
			curso.setText("");
		} else {
			// Si los campos son incorrectos se avisará con esta etiqueta
			lblNotFound.setVisible(true);

		}

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
