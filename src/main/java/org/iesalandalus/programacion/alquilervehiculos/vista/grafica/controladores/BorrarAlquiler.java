package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

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

public class BorrarAlquiler extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private DatePicker dpFechaAlquiler;

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfMatricula;

	@FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfMatricula, tfDni);
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		tfDni.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
		Controles.validarConEnter(this::confirmar, tfMatricula);
		dpFechaAlquiler.setOnKeyReleased(this::confirmar);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			borrar(new ActionEvent());
		}
	}

	@FXML
	void borrar(ActionEvent event) {
		try {
			Cliente cliente = vista.getControlador().buscar(Cliente.getClienteConDni(tfDni.getText()));
			Vehiculo vehiculo = vista.getControlador().buscar(Vehiculo.getVehiculoConMatricula(tfMatricula.getText()));
			Alquiler alquiler = new Alquiler(cliente, vehiculo, dpFechaAlquiler.getValue());
			if (Dialogos
					.mostrarDialogoConfirmacion("Borrar",
							String.format("¿Seguro que desea borrar el alquiler con cliente %s y vehiculo %s?",
									alquiler.getCliente().getDni(), alquiler.getVehiculo().getMatricula()),
							getEscenario())) {
				vista.getControlador().borrarAlquiler(vista.getControlador().buscar(alquiler));
			}
			cancelar(event);
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), this.getEscenario());
		}

	}

	@FXML
	void cancelar(ActionEvent event) {
		tfMatricula.clear();
		tfDni.clear();
		dpFechaAlquiler.getEditor().clear();
		dpFechaAlquiler.setValue(null);
		getEscenario().close();
	}

}
