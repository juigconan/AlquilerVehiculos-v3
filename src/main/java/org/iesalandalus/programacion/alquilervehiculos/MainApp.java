package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class MainApp {

	public static void main(String[] args) {
		Vista vista = seleccionarVista(args);
		Modelo modelo = new ModeloCascada(FactoriaFuenteDatos.FICHEROS);
		Controlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();

	}

	private static Vista seleccionarVista(String[] args) {
		Vista vista = FactoriaVista.GRAFICA.crear();
		if (args[args.length - 1].equals("-vTexto")) {
			vista = FactoriaVista.TEXTO.crear();
		} else if (args[args.length - 1].equals("-vGrafica")) {
			vista = FactoriaVista.GRAFICA.crear();
		}
		return vista;
	}

}
