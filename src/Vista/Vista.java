package Vista;
	
import java.io.IOException;

import Controlador.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;


public class Vista extends Application {
	
	  private static Scene scene;

	  // Método start que inicia la interfaz gráfica
	    @Override
	    public void start(Stage primaryStage) throws IOException {

	        // Crea una nueva escena con la raíz cargada desde el archivo FXML "PruebaSB.fxml"
	        Scene scene = new Scene(loadFXML("/PruebaSB"), 900, 600);
	        // Agrega un archivo CSS para estilos a la escena
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setTitle("Registro de estudiantes"); // Establece el título de la ventana

	        primaryStage.setScene(scene); // Establece la escena en el escenario principal
	        primaryStage.show(); // Muestra la ventana principal
	    }

	    static void setRoot(String fxml) throws IOException {
	        scene.setRoot(loadFXML(fxml)); // Establece la raíz de la escena con el archivo FXML dado
	    }

	    // Método para cargar archivos FXML y devolver la raíz del mismo
	    private static Parent loadFXML(String fxml) throws IOException {
	        FXMLLoader fxmlLoader = new FXMLLoader(Vista.class.getResource(fxml + ".fxml"));
	        return fxmlLoader.load(); // Carga el archivo FXML y devuelve su raíz
	    }

	    public static void main(String[] args) {
	        launch(args); // Método principal para iniciar la aplicación
	    }
}
