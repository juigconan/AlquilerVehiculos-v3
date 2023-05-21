package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
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

public class BorrarVehiculo extends Controlador {

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private Label lbTitulo;

	@FXML
	private TextField tfMatricula;

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfMatricula);
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.validarConEnter(this::confirmar, tfMatricula);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			borrar(new ActionEvent());
		}
	}

	@FXML
	void borrar(ActionEvent event) {
		Vehiculo vehiculo = null;
		try {
			vehiculo = Vehiculo.getVehiculoConMatricula(tfMatricula.getText());
			if (Dialogos.mostrarDialogoConfirmacion("Borrar",
					String.format("¿Seguro que desea borrar el vehículo modelo %s con matricula %s?",
							vista.getControlador().getVehiculos()
									.get(vista.getControlador().getVehiculos().indexOf(vehiculo)).getModelo(),
							vehiculo.getMatricula()),
					getEscenario())) {
				vista.getControlador().borrarVehiculo(vehiculo);
			}
			Dialogos.mostrarDialogoInformacion("EXITO", "Vehículo insertado correctamente", getEscenario());

			cancelar(event);
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), this.getEscenario());
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		Controles.limpiarCamposTexto(tfMatricula);
		getEscenario().close();
	}

}
