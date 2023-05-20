package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InsertarAlquiler extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private DatePicker dpAlquiler;

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfMatricula;

	@FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfDni, tfMatricula);
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		tfDni.textProperty()
		.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
		Controles.validarConEnter(this::confirmar, tfDni, tfMatricula);
		dpAlquiler.setOnKeyReleased(this::confirmar);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			insertar(new ActionEvent());
		}
	}

	@FXML
	void insertar(ActionEvent event) {
		Cliente cliente = vista.getControlador().buscar(Cliente.getClienteConDni(tfDni.getText()));
		Vehiculo vehiculo = vista.getControlador().buscar(Vehiculo.getVehiculoConMatricula(tfMatricula.getText()));
		LocalDate fechaAlquiler = dpAlquiler.getValue();
		try {
			vista.getControlador().insertarAlquiler(new Alquiler(cliente, vehiculo, fechaAlquiler));
			cancelar(event);
		} catch (OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		Controles.limpiarCamposTexto(tfDni, tfMatricula);
		dpAlquiler.getEditor().clear();
		dpAlquiler.setValue(null);
		getEscenario().close();
	}

}
