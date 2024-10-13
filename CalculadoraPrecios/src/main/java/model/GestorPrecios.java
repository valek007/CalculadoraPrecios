package main.java.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class GestorPrecios {

	private Map<String, Map<LocalDate, Double>> preciosPorHabitacion; // Mapa de precios por tipo de habitación y fecha
	private Properties propiedades;

	// Variables para propiedades específicas
	private String[] tiposHabitacion = { "HM", "HD", "HB", "HS", "PR", "SU" };
	private double precioMediaPension;
	private double factorDescuento;
	private double factorDobleUsoIndividual;

	// Constructor
	public GestorPrecios(Properties propiedades) {
		this.propiedades = propiedades;
		this.preciosPorHabitacion = new HashMap<>();
		cargarPreciosDesdeProperties();
		cargarValoresConfigurables(); // Cargar media pensión, descuento, DUI
	}

	// Método para cargar los precios del archivo .properties
	private void cargarPreciosDesdeProperties() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (String key : propiedades.stringPropertyNames()) {
			if (key.matches("(HM|HD|HB|HS|PR|SU)\\.\\d{4}-\\d{2}-\\d{2}")) { // Filtra solo las líneas de precios
				String[] partes = key.split("\\.");
				String tipoHabitacion = partes[0];
				LocalDate fecha = LocalDate.parse(partes[1], formatter);
				double precio = Double.parseDouble(propiedades.getProperty(key));

				preciosPorHabitacion.putIfAbsent(tipoHabitacion, new HashMap<>());
				preciosPorHabitacion.get(tipoHabitacion).put(fecha, precio);
			}
		}
	}

	// Método para cargar valores de media pensión, descuento y doble uso individual
	// desde el archivo .properties
	private void cargarValoresConfigurables() {
		this.precioMediaPension = Double.parseDouble(propiedades.getProperty("precio.mediaPension", "0.0"));
		this.factorDescuento = Double.parseDouble(propiedades.getProperty("descuento.factor", "0.0"));
		this.factorDobleUsoIndividual = Double.parseDouble(propiedades.getProperty("dobleUsoIndividual.factor", "0.0"));
	}

	// Método para obtener el precio dado el tipo de habitación y la fecha
	public double obtenerPrecio(String tipoHabitacion, LocalDate fecha) {
		Map<LocalDate, Double> preciosHabitacion = preciosPorHabitacion.get(tipoHabitacion);
		if (preciosHabitacion != null) {
			return preciosHabitacion.getOrDefault(fecha, 0.0);
		}
		return 0.0;
	}

	// Método para establecer el precio de una habitación en una fecha específica
	public void setPrecio(String tipoHabitacion, String fechaInicio, double nuevoPrecio) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse(fechaInicio, formatter);

		// Si la habitación ya tiene precios para alguna fecha
		preciosPorHabitacion.putIfAbsent(tipoHabitacion, new HashMap<>());
		preciosPorHabitacion.get(tipoHabitacion).put(fecha, nuevoPrecio); // Actualiza el precio en la fecha
	}

	// Guardar los cambios en el archivo properties
	public void guardarCambios() throws IOException {
		// Guardar los cambios de los precios en el archivo properties
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (Map.Entry<String, Map<LocalDate, Double>> entradaHabitacion : preciosPorHabitacion.entrySet()) {
			String tipoHabitacion = entradaHabitacion.getKey();
			for (Map.Entry<LocalDate, Double> entradaFechaPrecio : entradaHabitacion.getValue().entrySet()) {
				String fecha = entradaFechaPrecio.getKey().format(formatter);
				String key = tipoHabitacion + "." + fecha;
				propiedades.setProperty(key, String.valueOf(entradaFechaPrecio.getValue()));
			}
		}

		// Guardar las propiedades en el archivo
		File archivo = new File("src/main/resources/precios.properties");
		try (FileOutputStream out = new FileOutputStream(archivo.getAbsolutePath())) {
			propiedades.store(out, null);
		}
	}

	// Método para obtener los rangos de fechas
	public String[] obtenerRangosDeFechas() {
		// Seleccionamos una habitación cualquiera para obtener los rangos de fechas
		String tipoHabitacion = preciosPorHabitacion.keySet().iterator().next();

		// Obtenemos y ordenamos las fechas disponibles para esa habitación
		List<LocalDate> fechas = new ArrayList<>(preciosPorHabitacion.get(tipoHabitacion).keySet());
		Collections.sort(fechas); // Ordenar las fechas cronológicamente

		// Lista para guardar los rangos de fechas como Strings
		List<String> rangosDeFechas = new ArrayList<>();

		// Generar los rangos basados en las fechas disponibles
		for (int i = 0; i < fechas.size() - 1; i++) {
			LocalDate fechaInicio = fechas.get(i);
			LocalDate fechaFin = fechas.get(i + 1).minusDays(1); // Un día antes de la siguiente fecha
			String rango = fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM")) + "-"
					+ fechaFin.format(DateTimeFormatter.ofPattern("dd/MM"));
			rangosDeFechas.add(rango); // Añadir el rango generado a la lista
		}

		// Convertir la lista a un array y devolverlo
		return rangosDeFechas.toArray(new String[0]);
	}

	public String[] obtenerFechasInicio() {
		// Seleccionamos una habitación cualquiera para obtener las fechas de inicio
		String tipoHabitacion = preciosPorHabitacion.keySet().iterator().next();

		// Obtenemos y ordenamos las fechas disponibles para esa habitación
		List<LocalDate> fechas = new ArrayList<>(preciosPorHabitacion.get(tipoHabitacion).keySet());
		Collections.sort(fechas); // Ordenar las fechas cronológicamente

		// Lista para guardar las fechas de inicio como Strings
		List<String> fechasDeInicio = new ArrayList<>();

		// Generar la lista de fechas de inicio basadas en las fechas disponibles
		for (LocalDate fecha : fechas) {
			String fechaInicio = fecha.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			fechasDeInicio.add(fechaInicio); // Añadir la fecha de inicio generada a la lista
		}

		// Convertir la lista a un array y devolverla
		return fechasDeInicio.toArray(new String[0]);
	}

	// Método para convertir un rango a la fecha de inicio
	public LocalDate convertirRangoAFechaInicio(String rangoFecha) {
		String[] partes = rangoFecha.split("-");
		return LocalDate.parse("2024-" + partes[0].substring(3, 5) + "-" + partes[0].substring(0, 2));
	}

	// Getters
	public Map<String, Map<LocalDate, Double>> getPreciosPorHabitacion() {
		return preciosPorHabitacion;
	}

	public Properties getPropiedades() {
		return propiedades;
	}

	public String[] getTiposHabitacion() {
		return tiposHabitacion;
	}

	public double getPrecioMediaPension() {
		return precioMediaPension;
	}

	public double getFactorDescuento() {
		return factorDescuento;
	}

	public double getFactorDobleUsoIndividual() {
		return factorDobleUsoIndividual;
	}

	// Setters

	public void setPrecioMediaPension(double precioMediaPension) {
		this.precioMediaPension = precioMediaPension;
	}

	public void setFactorDescuento(double factorDescuento) {
		this.factorDescuento = factorDescuento;
	}

	public void setFactorDobleUsoIndividual(double factorDobleUsoIndividual) {
		this.factorDobleUsoIndividual = factorDobleUsoIndividual;
	}

}
