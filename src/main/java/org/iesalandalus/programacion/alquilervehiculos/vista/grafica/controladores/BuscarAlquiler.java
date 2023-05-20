package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BuscarAlquiler extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();
	
	@FXML
	private Button btAceptar;

	@FXML
	private DatePicker dpFechaAlquiler;

	@FXML
	private Label lbTitulo;

	@FXML
	private MenuItem miBorrar;

	@FXML
	private MenuItem miDevolver;

	@FXML
	private TableColumn<Alquiler, String> tcCliente;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaAlquiler;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaDevolucion;

	@FXML
	private TableColumn<Alquiler, String> tcVehiculo;

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TableView<Alquiler> tvAlquiler;

	@FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfMatricula);
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		tfDni.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
		Controles.validarConEnter(this::confirmar, tfMatricula, tfDni);
		dpFechaAlquiler.setOnKeyReleased(this::confirmar);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			buscar(new ActionEvent());
		}
	}

	void buscar(ActionEvent event) {
		tvAlquiler.setVisible(true);
		tvAlquiler.getSelectionModel().clearSelection();
		tvAlquiler.getItems().clear();
		tcCliente.setCellValueFactory(fila -> new SimpleObjectProperty<String>(fila.getValue().getCliente().getDni()));
		tcVehiculo.setCellValueFactory(
				fila -> new SimpleObjectProperty<String>(fila.getValue().getVehiculo().getMatricula()));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
		tcFechaAlquiler.setCellFactory(celda -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellFactory(celda -> new FormateadorCeldaFecha());
		try {
			Cliente cliente = vista.getControlador().buscar(Cliente.getClienteConDni(tfDni.getText()));
			Vehiculo vehiculo = vista.getControlador().buscar(Vehiculo.getVehiculoConMatricula(tfMatricula.getText()));
			tvAlquiler.getItems().add(vista.getControlador().buscar(
					vista.getControlador().buscar(new Alquiler(cliente, vehiculo, dpFechaAlquiler.getValue()))));
		} catch (IllegalArgumentException e) {
			tvAlquiler.setVisible(false);
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void aceptar(ActionEvent event) {
		limpiarVista();
		getEscenario().close();
	}

	private void limpiarVista() {
		tvAlquiler.setVisible(false);
		tvAlquiler.getItems().clear();
		tfMatricula.clear();
		tfDni.clear();
		dpFechaAlquiler.getEditor().clear();
		dpFechaAlquiler.setValue(null);
	}

	@FXML
	void borrar(ActionEvent event) {
		Alquiler alquiler = tvAlquiler.getSelectionModel().getSelectedItem();
		try {
			if (Dialogos
					.mostrarDialogoConfirmacion("Borrar",
							String.format("¿Seguro que desea borrar el alquiler con cliente %s y vehiculo %s?",
									alquiler.getCliente().getDni(), alquiler.getVehiculo().getMatricula()),
							getEscenario())) {
				vista.getControlador().borrarAlquiler(alquiler);
				limpiarVista();
			}
		} catch (OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void devolver(ActionEvent event) {
		DevolverAlquiler devolverAlquiler = (DevolverAlquiler) Controladores.get("vistas/DevolverAlquiler.fxml", "Devolver alquiler", getEscenario());
		devolverAlquiler.getEscenario().setResizable(false);
		devolverAlquiler.cargarDatos(tvAlquiler.getSelectionModel().getSelectedItem());
		devolverAlquiler.getEscenario().showAndWait();
		buscar(event);
	}

}
