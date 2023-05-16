package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InsertarCliente extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfTelefono;

	@FXML
	void initialize() {
		Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfDni);
	}

	@FXML
	void insertar(ActionEvent event) {
		try {
			vista.getControlador()
					.insertarCliente(new Cliente(tfNombre.getText(), tfDni.getText(), tfTelefono.getText()));
			Dialogos.mostrarDialogoInformacion("EXITO", "Cliente insertado correctamente", getEscenario());
			cancelar(event);
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), this.getEscenario());
		}

	}

	@FXML
	void cancelar(ActionEvent event) {
		getEscenario().close();
	}

}
