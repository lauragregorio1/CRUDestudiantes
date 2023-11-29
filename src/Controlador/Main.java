package Controlador;

import Modelo.Modelo;
import Vista.Vista;

public class Main {

	public static void main(String[] args) {
		// Utilizo setters para que mis clases se conozcan
		Modelo miModelo = new Modelo();
		Vista miVista = new Vista();
		Controller miController = new Controller();
		PantallaActualizarController miContoller2 = new PantallaActualizarController();
		miModelo.setControlador(miController);
		miModelo.setVista(miVista);
		miController.setVista(miVista);
		miController.setModelo(miModelo);
		Vista.launch(Vista.class, args); // Lanzo la pantalla principal
	}

}
