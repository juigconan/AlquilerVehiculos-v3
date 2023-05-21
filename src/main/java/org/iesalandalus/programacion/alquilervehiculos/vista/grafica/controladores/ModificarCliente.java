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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ModificarCliente extends Controlador {

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
		tfNombre.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
		tfTelefono.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));
		Controles.validarConEnter(this::confirmar, tfNombre, tfTelefono);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			modificar(new ActionEvent());
		}
	}

	public void cargarDatos(String nombre, String dni, String telefono) {
		tfNombre.setText(nombre);
		tfDni.setText(dni);
		tfTelefono.setText(telefono);
	}

	@FXML
	void cancelar(ActionEvent event) {
		getEscenario().close();
	}

	@FXML
	void modificar(ActionEvent event) {
		Cliente cliente = null;
		try {
			cliente = Cliente.getClienteConDni(tfDni.getText());
			if (Dialogos.mostrarDialogoConfirmacion("Modificar", String.format("¿Seguro que desea modificar a %s - %s?",
					vista.getControlador().buscar(cliente).getNombre(), cliente.getDni()), getEscenario())) {
				vista.getControlador().modificarCliente(Cliente.getClienteConDni(tfDni.getText()), tfNombre.getText(),
						tfTelefono.getText());
			}
			Dialogos.mostrarDialogoInformacion("EXITO", "Cliente modificado correctamente", getEscenario());
			cancelar(event);
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), this.getEscenario());
		}
	}
}
