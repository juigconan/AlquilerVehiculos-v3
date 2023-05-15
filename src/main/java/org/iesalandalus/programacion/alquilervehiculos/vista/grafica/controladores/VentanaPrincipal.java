package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
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
	private MenuBar mbMenu;

	@FXML
	private MenuItem miBorrarAlquiler;

	@FXML
	private MenuItem miBorrarCliente;

	@FXML
	private MenuItem miBorrarVehiculo;

	@FXML
	private MenuItem miBuscarAlquiler;

	@FXML
	private MenuItem miBuscarVehiculo;

	@FXML
	private MenuItem miDevolverAlquiler;

	@FXML
	private MenuItem miInsertarAlquiler;

	@FXML
	private MenuItem miInsertarCliente;

	@FXML
	private MenuItem miInsertarVehiculo;

	@FXML
	private MenuItem miModificarCliente;

	@FXML
	private Tab tabAlquileres;

	@FXML
	private Tab tabClientes;

	@FXML
	private Tab tabVehiculos;

	@FXML
	private TableView<Alquiler> tvAlquileres;

	@FXML
	private TableView<Cliente> tvClientes;

	@FXML
	private TableView<Vehiculo> tvVehiculos;

	@FXML
	void initialize() {
//		tvClientes.getItems().addAll(vista.getControlador().getClientes());
//		tvVehiculos.getItems().addAll(vista.getControlador().getVehiculos());
//		tvAlquileres.getItems().addAll(vista.getControlador().getAlquileres());
		ivAlquileres.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/imagenAlquileres.png")));
		ivClientes.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/imagenClientes.png")));
		ivVehiculos.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/imagenVehiculos.png")));
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
	void borrarCliente(ActionEvent event) {

	}

	@FXML
	void borrarVehiculo(ActionEvent event) {

	}

	@FXML
	void buscarAlquiler(ActionEvent event) {

	}

	@FXML
	void buscarCliente(ActionEvent event) {

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
	void insertarCliente(ActionEvent event) {

	}

	@FXML
	void insertarVehiculo(ActionEvent event) {

	}

	@FXML
	void modificarCliente(ActionEvent event) {

	}
}
