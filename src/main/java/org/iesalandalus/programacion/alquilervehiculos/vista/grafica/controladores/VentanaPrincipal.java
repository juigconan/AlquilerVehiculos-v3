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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
	private Menu mBorrar;

	@FXML
	private Menu mBuscar;

    @FXML
    private Menu mOpciones;
    
    @FXML
    private MenuItem miCerrarAplicacion;

	@FXML
	private Menu mInsertar;

	@FXML
	private MenuBar mbMenu;

	@FXML
	private MenuItem miBorrarAlquiler;

	@FXML
	private MenuItem miBorrarAlquilerMenu;

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
	private MenuItem miMostrarEstadisticas;

	@FXML
	void initialize() {
		refrescarTablas();
		actualizarMenus();
	}

	private void actualizarMenus() {
		tvClientes.getSelectionModel().selectedItemProperty()
				.addListener((ob, ov, nv) -> miBorrarCliente.setDisable(nv == null));
		tvClientes.getSelectionModel().selectedItemProperty()
				.addListener((ob, ov, nv) -> miModificarCliente.setDisable(nv == null));
		tvAlquileres.getSelectionModel().selectedItemProperty()
				.addListener((ob, ov, nv) -> miBorrarAlquiler.setDisable(nv == null));
		tvAlquileres.getSelectionModel().selectedItemProperty()
				.addListener((ob, ov, nv) -> miDevolverAlquiler.setDisable(nv == null));
		tvVehiculos.getSelectionModel().selectedItemProperty()
				.addListener((ob, ov, nv) -> miBorrarVehiculo.setDisable(nv == null));
	}

	private void refrescarTablas() {
		// Tabla de clientes
		tvClientes.getSelectionModel().clearSelection();
		tvClientes.getItems().clear();
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		tvClientes.setItems(FXCollections.observableList(vista.getControlador().getClientes()));

		// Tabla de vehiculos
		tvVehiculos.getSelectionModel().clearSelection();
		tvVehiculos.getItems().clear();
		tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		tcPlazas.setCellValueFactory(fila -> new SimpleObjectProperty<String>(calcularPlazas(fila.getValue())));
		tcPma.setCellValueFactory(fila -> new SimpleObjectProperty<String>(calcularPma(fila.getValue())));
		tcCilindrada.setCellValueFactory(fila -> new SimpleObjectProperty<String>(calcularCilindrada(fila.getValue())));
		tvVehiculos.setItems(FXCollections.observableList(vista.getControlador().getVehiculos()));

		// Tabla de alquileres
		tvAlquileres.getSelectionModel().clearSelection();
		tvAlquileres.getItems().clear();
		tcCliente.setCellValueFactory(fila -> new SimpleObjectProperty<String>(fila.getValue().getCliente().getDni()));
		tcVehiculo.setCellValueFactory(
				fila -> new SimpleObjectProperty<String>(fila.getValue().getVehiculo().getMatricula()));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
		tcFechaAlquiler.setCellFactory(celda -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellFactory(celda -> new FormateadorCeldaFecha());
		tvAlquileres.setItems(FXCollections.observableList(vista.getControlador().getAlquileres()));
	}

	protected static String calcularPma(Vehiculo vehiculo) {
		String pma = "--";
		if (vehiculo instanceof Furgoneta furgoneta) {
			pma = Integer.toString(furgoneta.getPma());
		}
		return pma;
	}

	protected static String calcularPlazas(Vehiculo vehiculo) {
		String plazas = "--";
		if (vehiculo instanceof Furgoneta furgoneta) {
			plazas = Integer.toString(furgoneta.getPlazas());
		}
		if (vehiculo instanceof Autobus autobus) {
			plazas = Integer.toString(autobus.getPlazas());
		}
		return plazas;
	}

	protected static String calcularCilindrada(Vehiculo vehiculo) {
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
		refrescarTablas();

	}

	@FXML
	void buscarCliente(ActionEvent event) {
		BuscarCliente buscarCliente = (BuscarCliente) Controladores.get("vistas/BuscarCliente.fxml", "Buscar cliente",
				getEscenario());
		buscarCliente.getEscenario().setResizable(false);
		buscarCliente.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void borrarCliente(ActionEvent event) {
		Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
		try {
			if (Dialogos.mostrarDialogoConfirmacion("Borrar",
					String.format("¿Seguro que desea borrar a %s - %s?", cliente.getNombre(), cliente.getDni()),
					getEscenario())) {
				vista.getControlador().borrarCliente(cliente);
				refrescarTablas();
			}
		} catch (OperationNotSupportedException e) {
			e.getStackTrace();
		}
	}

	@FXML
	void borrarClienteMenu(ActionEvent event) {
		BorrarCliente borrarCliente = (BorrarCliente) Controladores.get("vistas/BorrarCliente.fxml", "Borrar cliente",
				getEscenario());
		borrarCliente.getEscenario().setResizable(false);
		borrarCliente.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void modificarCliente(ActionEvent event) {
		Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
		ModificarCliente modificarCliente = (ModificarCliente) Controladores.get("vistas/ModificarCliente.fxml",
				"Modificar cliente", getEscenario());
		modificarCliente.getEscenario().setResizable(false);
		modificarCliente.cargarDatos(cliente.getNombre(), cliente.getDni(), cliente.getTelefono());
		modificarCliente.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void insertarVehiculo(ActionEvent event) {

		InsertarVehiculo insertarVehiculo = (InsertarVehiculo) Controladores.get("vistas/InsertarVehiculo.fxml",
				"Insertar vehiculo", getEscenario());
		insertarVehiculo.getEscenario().setResizable(false);
		insertarVehiculo.getEscenario().showAndWait();
		refrescarTablas();

	}

	@FXML
	void borrarVehiculo(ActionEvent event) {
		Vehiculo vehiculo = tvVehiculos.getSelectionModel().getSelectedItem();
		try {
			if (Dialogos.mostrarDialogoConfirmacion("Borrar",
					String.format("¿Seguro que desea borrar el vehiculo con modelo %s, matricula %s?",
							vehiculo.getModelo(), vehiculo.getMatricula()),
					getEscenario())) {
				vista.getControlador().borrarVehiculo(vehiculo);
				refrescarTablas();
			}
		} catch (OperationNotSupportedException e) {
			e.getStackTrace();
		}
	}

	@FXML
	void borrarVehiculoMenu(ActionEvent event) {
		BorrarVehiculo borrarVehiculo = (BorrarVehiculo) Controladores.get("vistas/BorrarVehiculo.fxml",
				"Borrar vehiculo", getEscenario());
		borrarVehiculo.getEscenario().setResizable(false);
		borrarVehiculo.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void buscarVehiculo(ActionEvent event) {
		BuscarVehiculo buscarVehiculo = (BuscarVehiculo) Controladores.get("vistas/BuscarVehiculo.fxml",
				"Buscar vehiculo", getEscenario());
		buscarVehiculo.getEscenario().setResizable(false);
		buscarVehiculo.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void insertarAlquiler(ActionEvent event) {
		InsertarAlquiler insertarAlquiler = (InsertarAlquiler) Controladores.get("vistas/InsertarAlquiler.fxml",
				"Insertar alquiler", getEscenario());
		insertarAlquiler.getEscenario().setResizable(false);
		insertarAlquiler.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void devolverAlquiler(ActionEvent event) {
		DevolverAlquiler devolverAlquiler = (DevolverAlquiler) Controladores.get("vistas/DevolverAlquiler.fxml",
				"Devolver alquiler", getEscenario());
		devolverAlquiler.getEscenario().setResizable(false);
		devolverAlquiler.cargarDatos(tvAlquileres.getSelectionModel().getSelectedItem());
		devolverAlquiler.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void buscarAlquiler(ActionEvent event) {
		BuscarAlquiler buscarAlquiler = (BuscarAlquiler) Controladores.get("vistas/BuscarAlquiler.fxml",
				"Buscar alquiler", getEscenario());
		buscarAlquiler.getEscenario().setResizable(false);
		buscarAlquiler.getEscenario().showAndWait();
		refrescarTablas();
	}

	@FXML
	void borrarAlquiler(ActionEvent event) {
		Alquiler alquiler = tvAlquileres.getSelectionModel().getSelectedItem();
		try {
			if (Dialogos
					.mostrarDialogoConfirmacion("Borrar",
							String.format("¿Seguro que desea borrar el alquiler con cliente %s y vehiculo %s?",
									alquiler.getCliente().getDni(), alquiler.getVehiculo().getMatricula()),
							getEscenario())) {
				vista.getControlador().borrarAlquiler(alquiler);
				refrescarTablas();
			}
		} catch (OperationNotSupportedException e) {
			e.getStackTrace();
		}
	}

	@FXML
	void borrarAlquilerMenu(ActionEvent event) {

	}

	@FXML
	void abrirAcercaDe(ActionEvent event) {

	}
	

    @FXML
    void cerrar(ActionEvent event) {
    		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estas seguro de que quieres salir de la aplicación?",
    				getEscenario())) {
    			getEscenario().close();
    		} else {
    			event.consume();
    		}
    	

    }

	@FXML
	void mostrarEstadisticas(ActionEvent event) {
		VentanaEstadisticas ventanaEstadisticas = (VentanaEstadisticas) Controladores
				.get("vistas/VentanaEstadisticas.fxml", "Estadisticas", getEscenario());
		ventanaEstadisticas.getEscenario().setResizable(false);
		ventanaEstadisticas.getEscenario().showAndWait();
	}

}
