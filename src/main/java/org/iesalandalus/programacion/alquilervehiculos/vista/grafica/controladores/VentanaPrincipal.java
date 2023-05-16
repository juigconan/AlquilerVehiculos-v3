package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VentanaPrincipal extends Controlador {

	private VistaGrafica vista = VistaGrafica.getInstancia();

	@FXML
	private ImageView ivAlquileres;

	@FXML
	private ImageView ivClientes;

	@FXML
	private ImageView ivVehiculos;

	@FXML
	private Menu mAcercaDe;

	@FXML
	private Menu mAyuda;

	@FXML
	private Menu mAyuda11;

	@FXML
	private Menu mAyuda111;

	@FXML
	private Menu mInsertar;

	@FXML
	private MenuBar mbMenu;

	@FXML
	private MenuItem miBorrarAlquiler;

	@FXML
	private MenuItem miBorrarAlquilerMune;

	@FXML
	private MenuItem miBorrarCliente;

	@FXML
	private MenuItem miBorrarClienteMenu;

	@FXML
	private MenuItem miBorrarVehiculo;

	@FXML
	private MenuItem miBorrarVehiculoMenu;

	@FXML
	private MenuItem miBuscarAlquiler;

	@FXML
	private MenuItem miBuscarAlquilerMenu;

	@FXML
	private MenuItem miBuscarClienteMenu;

	@FXML
	private MenuItem miBuscarVehiculo;

	@FXML
	private MenuItem miBuscarVehiculoMenu;

	@FXML
	private MenuItem miDevolverAlquiler;

	@FXML
	private MenuItem miInsertarAlquiler;

	@FXML
	private MenuItem miInsertarCliente;

	@FXML
	private MenuItem miInsertarClienteMenu;

	@FXML
	private MenuItem miInsertarVehiculo;

	@FXML
	private MenuItem miInsertarVehiculoMenu;

	@FXML
	private MenuItem miModificarCliente;

	@FXML
	private Tab tabAlquileres;

	@FXML
	private Tab tabClientes;

	@FXML
	private Tab tabVehiculos;

	@FXML
	private TableColumn<Vehiculo, String> tcCilindrada;

	@FXML
	private TableColumn<Alquiler, String> tcCliente;

	@FXML
	private TableColumn<Cliente, String> tcDni;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaAlquiler;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaDevolucion;

	@FXML
	private TableColumn<Vehiculo, String> tcMarca;

	@FXML
	private TableColumn<Vehiculo, String> tcMatricula;

	@FXML
	private TableColumn<Vehiculo, String> tcModelo;

	@FXML
	private TableColumn<Cliente, String> tcNombre;

	@FXML
	private TableColumn<Vehiculo, String> tcPlazas;

	@FXML
	private TableColumn<Vehiculo, String> tcPma;

	@FXML
	private TableColumn<Cliente, String> tcTelefono;

	@FXML
	private TableColumn<Alquiler, String> tcVehiculo;

	@FXML
	private TableView<Alquiler> tvAlquileres;

	@FXML
	private TableView<Cliente> tvClientes;

	@FXML
	private TableView<Vehiculo> tvVehiculos;

	@FXML
	void initialize() {
		iniciarTablas();
		iniciarImagenes();
	}

	private void iniciarImagenes() {
		ivAlquileres
				.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/imagenAlquileres.png")));
		ivClientes.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/imagenClientes.png")));
		ivVehiculos.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/imagenVehiculos.png")));
	}

	private void iniciarTablas() {

		// Tabla de clientes tcNombre, tcDni, tcTelefono
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		tvClientes.setItems(FXCollections.observableList(vista.getControlador().getClientes()));

		// Tabla de vehiculos tcMarca, tcModelo, tcMatricula, tcPlazas, tcPma,
		// tcCilindrada
		tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		tcPlazas.setCellValueFactory(fila -> new SimpleObjectProperty<String>(calcularPlazas(fila.getValue())));
		tcPma.setCellValueFactory(fila -> new SimpleObjectProperty<String>(calcularPma(fila.getValue())));
		tcCilindrada.setCellValueFactory(fila -> new SimpleObjectProperty<String>(calcularCilindrada(fila.getValue())));
		tvVehiculos.setItems(FXCollections.observableList(vista.getControlador().getVehiculos()));

		// Tabla de alquileres tcCliente, tcVehiculo, tcFechaAlquiler, tcFechaDevolucion
		tcCliente.setCellValueFactory(fila -> new SimpleObjectProperty<String>(fila.getValue().getCliente().getDni()));
		tcVehiculo.setCellValueFactory(
				fila -> new SimpleObjectProperty<String>(fila.getValue().getVehiculo().getMatricula()));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
		tcFechaAlquiler.setCellFactory(celda -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellFactory(celda -> new FormateadorCeldaFecha());
		tvAlquileres.setItems(FXCollections.observableList(vista.getControlador().getAlquileres()));
	}

	private String calcularPma(Vehiculo vehiculo) {
		String pma = "--";
		if (vehiculo instanceof Furgoneta furgoneta) {
			pma = Integer.toString(furgoneta.getPma());
		}
		return pma;
	}

	private String calcularPlazas(Vehiculo vehiculo) {
		String plazas = "--";
		if (vehiculo instanceof Furgoneta furgoneta) {
			plazas = Integer.toString(furgoneta.getPlazas());
		}
		if (vehiculo instanceof Autobus autobus) {
			plazas = Integer.toString(autobus.getPlazas());
		}
		return plazas;
	}

	private String calcularCilindrada(Vehiculo vehiculo) {
		String cilindrada = "--";
		if (vehiculo instanceof Turismo turismo) {
			cilindrada = Integer.toString(turismo.getCilindrada());
		}
		return cilindrada;
	}

	@FXML
	void insertarCliente(ActionEvent event) {

		InsertarCliente insertarCliente = (InsertarCliente) Controladores.get("vistas/InsertarCliente.fxml",
				"Insertar cliente", getEscenario());
		insertarCliente.getEscenario().setResizable(false);
		insertarCliente.getEscenario().showAndWait();
		iniciarTablas();

	}

	@FXML
	void buscarCliente(ActionEvent event) {

	}

	@FXML
	void borrarCliente(ActionEvent event) {
		Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
		try {
			if (cliente == null) {
				Dialogos.mostrarDialogoAdvertencia("Cuidado", "No ha seleccionado ningún cliente.", getEscenario());
			} else if (Dialogos.mostrarDialogoConfirmacion("Borrar",
					String.format("¿Seguro que desea borrar a %s - %s?", cliente.getNombre(), cliente.getDni()),
					getEscenario())) {
				vista.getControlador().borrarCliente(cliente);
				iniciarTablas();
			}
		} catch (OperationNotSupportedException e) {
		}
	}

	@FXML
	void abrirAcercaDe(ActionEvent event) {

	}

	@FXML
	void abrirAyuda(ActionEvent event) {

	}

	@FXML
	void borrarAlquiler(ActionEvent event) {

	}

	@FXML
	void borrarVehiculo(ActionEvent event) {

	}

	@FXML
	void buscarAlquiler(ActionEvent event) {

	}

	@FXML
	void buscarVehiculo(ActionEvent event) {

	}

	@FXML
	void devolverAlquiler(ActionEvent event) {

	}

	@FXML
	void insertarAlquiler(ActionEvent event) {

	}

	@FXML
	void insertarVehiculo(ActionEvent event) {

	}

	@FXML
	void modificarCliente(ActionEvent event) {

	}
}
