package main.java.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

public class GestorPreciosGeneral extends GestorPrecios {

	private Properties propiedades;

	public GestorPreciosGeneral(Properties propiedades) {
		super(propiedades);
		this.propiedades = propiedades;
	}

	@Override
	public String[] obtenerFechasInicio() {
		// Agregar lógica específica para GestorPreciosGeneral si es necesario
		return super.obtenerFechasInicio(); // Llama al método de la clase padre
	}

	// Método para establecer una nueva fecha de inicio
	public void setFechaInicio(String fechaOriginal, String nuevaFechaInicio) {
		// Actualizar la propiedad en el archivo con la nueva fecha y el mismo precio
		for (String tipoHabitacion : getTiposHabitacion()) {
			String claveOriginal = tipoHabitacion + "." + fechaOriginal;
			String claveNueva = tipoHabitacion + "." + nuevaFechaInicio;

			if (propiedades.containsKey(claveOriginal)) {
				// Obtener el precio original para la fecha anterior
				String precio = propiedades.getProperty(claveOriginal);

				// Borrar la clave original y crear la nueva clave con la nueva fecha
				propiedades.remove(claveOriginal);
				propiedades.setProperty(claveNueva, precio);
			}
		}
	}

	// Método para establecer el precio de una habitación y fecha de inicio
	// específicas
	public void setPrecio(String fechaOriginal, String nuevaFechaInicio, double nuevoPrecio) {
		for (String tipoHabitacion : getTiposHabitacion()) {
			String claveNueva = tipoHabitacion + "." + nuevaFechaInicio;
			propiedades.setProperty(claveNueva, String.valueOf(nuevoPrecio)); // Guardar el nuevo precio
		}
	}

	public double getPrecio(String tipoHabitacion, String fechaInicio) {
		// Convertir la fecha de inicio (String) a LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate fecha = LocalDate.parse(fechaInicio, formatter);

		// Obtener el mapa de precios para el tipo de habitación dado
		Map<LocalDate, Double> preciosHabitacion = super.getPreciosPorHabitacion().get(tipoHabitacion);

		if (preciosHabitacion != null && !preciosHabitacion.isEmpty()) {
			// Buscar el precio para la fecha exacta proporcionada
			Double precio = preciosHabitacion.get(fecha);

			// Si se encuentra el precio, devolverlo
			if (precio != null) {
				return precio;
			}
		}

		return 0.0; // Devolver un precio por defecto si no se encuentra
	}

	// Getters

	public Properties getPropiedades() {
		return propiedades;
	}

}