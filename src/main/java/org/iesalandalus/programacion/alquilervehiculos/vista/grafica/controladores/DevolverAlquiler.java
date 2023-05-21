package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DevolverAlquiler extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	private Cliente cliente;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private DatePicker dpFechaDevolucion;

	@FXML
	void initialize() {
		dpFechaDevolucion.setOnKeyReleased(this::confirmar);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			devolver(new ActionEvent());
		}
	}

	public void cargarDatos(Alquiler alquiler) {
		cliente = alquiler.getCliente();
	}

	@FXML
	void cancelar(ActionEvent event) {
		dpFechaDevolucion.getEditor().clear();
		dpFechaDevolucion.setValue(null);
		getEscenario().close();
	}

	@FXML
	void devolver(ActionEvent event) {
		try {
			vista.getControlador().devolverAlquiler(cliente, dpFechaDevolucion.getValue());
			Dialogos.mostrarDialogoInformacion("EXITO", "Alquiler devuelto correctamente", getEscenario());
			cancelar(event);
		} catch (OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}
	}

}
