package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BuscarVehiculo extends Controlador{
	
	private VistaGrafica vista = VistaGrafica.getInstancia();

    @FXML
    private Button btAceptar;

    @FXML
    private Label lbTitulo;

    @FXML
    private MenuItem miBorrar;

    @FXML
    private TableColumn<Vehiculo, String> tcCilindrada;

    @FXML
    private TableColumn<Vehiculo, String> tcMarca;

    @FXML
    private TableColumn<Vehiculo, String> tcMatricula;

    @FXML
    private TableColumn<Vehiculo, String> tcModelo;

    @FXML
    private TableColumn<Vehiculo, String> tcPlazas;

    @FXML
    private TableColumn<Vehiculo, String> tcPma;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TableView<Vehiculo> tvVehiculo;
    
    @FXML
	void initialize() {
		// Este limpiar esta aqui para que direcatamente aparezca el campo en rojo
		Controles.limpiarCamposTexto(tfMatricula);
		tfMatricula.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		Controles.validarConEnter(this::confirmar, tfMatricula);
	}

	private void confirmar(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			buscar(new ActionEvent());
		}
	}
	
	private void buscar(ActionEvent actionEvent) {
		tvVehiculo.setVisible(true);
		tvVehiculo.getSelectionModel().clearSelection();
		tvVehiculo.getItems().clear();
		tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		tcPlazas.setCellValueFactory(fila -> new SimpleObjectProperty<String>(VentanaPrincipal.calcularPlazas(fila.getValue())));
		tcPma.setCellValueFactory(fila -> new SimpleObjectProperty<String>(VentanaPrincipal.calcularPma(fila.getValue())));
		tcCilindrada.setCellValueFactory(fila -> new SimpleObjectProperty<String>(VentanaPrincipal.calcularCilindrada(fila.getValue())));
		try {
			tvVehiculo.getItems().add(vista.getControlador().getVehiculos()
					.get(vista.getControlador().getVehiculos().indexOf(Vehiculo.getVehiculoConMatricula(tfMatricula.getText()))));
		} catch (IllegalArgumentException e) {
			tvVehiculo.setVisible(false);
			Dialogos.mostrarDialogoError("ERROR", e.getMessage(), getEscenario());
		}
	}


    @FXML
    void aceptar(ActionEvent event) {
    	tvVehiculo.setVisible(false);
    	tvVehiculo.getItems().clear();
    	getEscenario().close();
    }

    @FXML
    void borrar(ActionEvent event) {
    	Vehiculo vehiculo = tvVehiculo.getSelectionModel().getSelectedItem();
		try {
			if (Dialogos.mostrarDialogoConfirmacion("Borrar",
					String.format("¿Seguro que desea borrar el vehiculo con modelo %s, matricula %s?",
							vehiculo.getModelo(), vehiculo.getMatricula()),
					getEscenario())) {
				vista.getControlador().borrarVehiculo(vehiculo);
				aceptar(event);
			}
		} catch (OperationNotSupportedException e) {
			e.getStackTrace();
		}

    }

}
