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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BorrarCliente extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private Label lbTitulo;

	@FXML
	private TextField tfDni;

	@FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfDni);
		tfDni.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
		Controles.validarConEnter(this::confirmar, tfDni);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			borrar(new ActionEvent());
		}
	}

	@FXML
	void borrar(ActionEvent event) {
		Cliente cliente = null;
		try {
			cliente = Cliente.getClienteConDni(tfDni.getText());
			if (Dialogos.mostrarDialogoConfirmacion("Borrar",
					String.format("¿Seguro que desea borrar a %s - %s?",
							vista.getControlador().getClientes()
									.get(vista.getControlador().getClientes().indexOf(cliente)).getNombre(),
							cliente.getDni()),
					getEscenario())) {
				vista.getControlador().borrarCliente(cliente);
			}
			cancelar(event);
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), this.getEscenario());
		}

	}

	@FXML
	void cancelar(ActionEvent event) {
		Controles.limpiarCamposTexto(tfDni);
		getEscenario().close();
	}

}
