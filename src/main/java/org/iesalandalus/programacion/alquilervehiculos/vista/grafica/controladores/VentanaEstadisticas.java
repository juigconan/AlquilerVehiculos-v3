package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;


import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class VentanaEstadisticas extends Controlador {
	
	private VistaGrafica vista = VistaGrafica.getInstancia();

    @FXML
    private Button btAceptar;

    @FXML
    private DatePicker dpEstadistica;

    @FXML
    private ListView<String> lvEstadisticas;
    
    @FXML
	void initialize() {
		dpEstadistica.setOnKeyReleased(this::confirmar);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			mostrarEstadisticas(new ActionEvent());
		}
	}

    private void mostrarEstadisticas(ActionEvent actionEvent) {
    	lvEstadisticas.getItems().clear();
    	Map<TipoVehiculo, Integer> mapaEstadisticas = inicializaEstadisticas();
		try {
			LocalDate fecha = dpEstadistica.getValue();
			for (Alquiler alquiler : vista.getControlador().getAlquileres()) {
				if (alquiler.getFechaAlquiler().getMonth().equals(fecha.getMonth())
						&& alquiler.getFechaAlquiler().getYear() == fecha.getYear()) {
					mapaEstadisticas.put(TipoVehiculo.get(alquiler.getVehiculo()), mapaEstadisticas.get(TipoVehiculo.get(alquiler.getVehiculo())) + 1);
				}
			}
		} catch (IllegalArgumentException e) {
			mapaEstadisticas = null;
		}

		if (mapaEstadisticas == null) {
			System.out.println("ERROR: No es posible mostrar las estadisticas, la fecha no es válida.");
		} else {
			for (Map.Entry<TipoVehiculo, Integer> entrada : mapaEstadisticas.entrySet()) {
				TipoVehiculo tipo = entrada.getKey();
				Integer numero = entrada.getValue();
				lvEstadisticas.getItems().add(String.format("%s --> %d%n", tipo, numero));
			}
		}
		
	}
    
    private Map<TipoVehiculo, Integer> inicializaEstadisticas() {
		Map<TipoVehiculo,Integer> mapaEstadisticas = new EnumMap<>(TipoVehiculo.class);
		for (int i = 0; i < TipoVehiculo.values().length; i++) {
			mapaEstadisticas.put(TipoVehiculo.get(i), 0);
		}
		return mapaEstadisticas;
	}


	@FXML
    void aceptar(ActionEvent event) {

    }

}


