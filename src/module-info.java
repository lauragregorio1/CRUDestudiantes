module EstudiantesJDBC {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;
	
	opens Vista to javafx.graphics, javafx.fxml;

	exports Controlador to javafx.fxml;
	exports Modelo;
	exports Vista;
	opens Controlador to javafx.fxml;
}
