package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

public class InsertarVehiculo extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private ChoiceBox<String> cbTipo;

	@FXML
	private TextField tfCilindrada;

	@FXML
	private TextField tfMarca;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TextField tfModelo;

	@FXML
	private TextField tfPlazas;

	@FXML
	private TextField tfPma;

	@FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfMarca, tfMatricula);
		limpiarCampos(tfModelo, tfPlazas, tfPma, tfCilindrada);
		tfMarca.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MARCA, tfMarca));
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.validarConEnter(this::confirmar, tfMarca, tfModelo, tfMatricula, tfPlazas, tfPma, tfCilindrada);
		cbTipo.getItems().addAll("Furgoneta", "Autobus", "Turismo");
		cbTipo.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> seleccionarTipo(nv));
		cbTipo.setValue("Furgoneta");
	}

	// Este metodo es para no asignar las clases valido e invalido
	void limpiarCampos(TextField... camposTexto) {
		for (TextField campo : camposTexto) {
			campo.textProperty().setValue("");
		}
	}

	private void seleccionarTipo(String tipo) {
		if (tipo == "Autobus") {
			limpiarCampos(tfPma, tfCilindrada);
			tfPlazas.setDisable(false);
			tfPma.setDisable(true);
			tfCilindrada.setDisable(true);
		} else if (tipo == "Furgoneta") {
			limpiarCampos(tfCilindrada);
			tfPlazas.setDisable(false);
			tfPma.setDisable(false);
			tfCilindrada.setDisable(true);
		} else if (tipo == "Turismo") {
			limpiarCampos(tfPma, tfPlazas);
			tfPlazas.setDisable(true);
			tfPma.setDisable(true);
			tfCilindrada.setDisable(false);
		}
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			insertar(new ActionEvent());
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		Controles.limpiarCamposTexto(tfMarca, tfMatricula);
		limpiarCampos(tfModelo, tfPlazas, tfPma, tfCilindrada);
		getEscenario().close();
	}

	@FXML
	void insertar(ActionEvent event) {
		String tipo = cbTipo.getSelectionModel().getSelectedItem();
		try {
			if (tipo == "Furgoneta") {
				vista.getControlador().insertarVehiculo(
						new Furgoneta(tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfPma.getText()),
								Integer.parseInt(tfPlazas.getText()), tfMatricula.getText()));
			} else if (tipo == "Autobus") {
				vista.getControlador().insertarVehiculo(new Autobus(tfMarca.getText(), tfModelo.getText(),
						Integer.parseInt(tfPlazas.getText()), tfMatricula.getText()));
			} else if (tipo == "Turismo") {
				vista.getControlador().insertarVehiculo(new Turismo(tfMarca.getText(), tfModelo.getText(),
						Integer.parseInt(tfCilindrada.getText()), tfMatricula.getText()));
			}
			Dialogos.mostrarDialogoInformacion("EXITO",
					String.format("%s %s correctamente", tipo, tipo == "Furgoneta" ? "insertada" : "insertado"),
					getEscenario());
			cancelar(event);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("ERROR", "El número introducido no tiene un formato válido", getEscenario());
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}

	}

}
