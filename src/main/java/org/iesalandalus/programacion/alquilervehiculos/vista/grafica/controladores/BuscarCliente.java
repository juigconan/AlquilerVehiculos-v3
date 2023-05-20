package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class BuscarCliente extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	private Button btAceptar;

	@FXML
	private Label lbTitulo;

	@FXML
	private MenuItem miBorrar;

	@FXML
	private MenuItem miModificar;

	@FXML
	private TableColumn<Cliente, String> tcDni;

	@FXML
	private TableColumn<Cliente, String> tcNombre;

	@FXML
	private TableColumn<Cliente, String> tcTelefono;

	@FXML
	private TextField tfDni;

	@FXML
	private TableView<Cliente> tvCliente;

	@FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfDni);
		tfDni.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
		Controles.validarConEnter(this::confirmar, tfDni);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			buscar(new ActionEvent());
		}
	}

	private void buscar(ActionEvent actionEvent) {
		tvCliente.setVisible(true);
		tvCliente.getSelectionModel().clearSelection();
		tvCliente.getItems().clear();
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		try {
			tvCliente.getItems().add(vista.getControlador().buscar(Cliente.getClienteConDni(tfDni.getText())));
		} catch (IllegalArgumentException e) {
			tvCliente.setVisible(false);
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), this.getEscenario());
		}
	}

	@FXML
	void aceptar(ActionEvent event) {
		tvCliente.setVisible(false);
		tvCliente.getItems().clear();
		tfDni.clear();
		getEscenario().close();
	}

	@FXML
	void borrar(ActionEvent event) {
		Cliente cliente = tvCliente.getSelectionModel().getSelectedItem();
		try {
			if (Dialogos.mostrarDialogoConfirmacion("Borrar",
					String.format("¿Seguro que desea borrar a %s - %s?", cliente.getNombre(), cliente.getDni()),
					getEscenario())) {
				vista.getControlador().borrarCliente(cliente);
				aceptar(event);
			}
		} catch (OperationNotSupportedException e) {
			e.getStackTrace();
		}
	}

	@FXML
	void modificar(ActionEvent event) {
		// Creo la escena manualmente para poder coger el controlador
		FXMLLoader loaderModificarCliente = new FXMLLoader(
				LocalizadorRecursos.class.getResource("vistas/ModificarCliente.fxml"));
		Cliente cliente = tvCliente.getSelectionModel().getSelectedItem();
		try {
			Parent raiz = loaderModificarCliente.load();
			ModificarCliente modificarCliente = loaderModificarCliente.getController();
			modificarCliente.cargarDatos(cliente.getNombre(), cliente.getDni(), cliente.getTelefono());
			Stage escena = new Stage();
			escena.setTitle("Modificar cliente");
			escena.setScene(new Scene(raiz));
			modificarCliente.setEscenario(escena);
			escena.setResizable(false);
			escena.showAndWait();
			buscar(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}