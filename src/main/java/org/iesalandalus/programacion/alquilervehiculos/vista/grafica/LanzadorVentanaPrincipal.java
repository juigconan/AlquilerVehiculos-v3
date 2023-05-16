package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores.VentanaPrincipal;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LanzadorVentanaPrincipal extends Application {

	public LanzadorVentanaPrincipal() {
	}

	@Override
	public void start(Stage escenarioPrincipal) {
		try {
			
			VentanaPrincipal ventanaPrincipal = (VentanaPrincipal) Controladores.get("vistas/VentanaPrincipal.fxml", "Ventana Principal", null);
			ventanaPrincipal.getEscenario().setOnCloseRequest(e -> confirmarSalida(ventanaPrincipal.getEscenario(), e));
			ventanaPrincipal.getEscenario().setResizable(false);
			ventanaPrincipal.getEscenario().getScene().getStylesheets().add(LocalizadorRecursos.class.getResource("estilos/estiloPrincipal.css").toExternalForm());
			ventanaPrincipal.getEscenario().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void comenzar() {
		launch(LanzadorVentanaPrincipal.class);
	}
	
	private void confirmarSalida(Stage escenario, WindowEvent e) {
		if(Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estas seguro de que quieres salir de la aplicación?", escenario)) {
			escenario.close();
		}
		else {
			e.consume();
		}
	}
}