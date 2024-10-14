package main.java.model;

import java.time.LocalDate;

public class Reserva {
	private Habitacion habitacion;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private boolean mediaPension;
	private boolean descuento;
	
	//Getters and Setters
	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	public boolean isMediaPension() {
		return mediaPension;
	}
	public void setMediaPension(boolean mediaPension) {
		this.mediaPension = mediaPension;
	}
	public boolean isDescuento() {
		return descuento;
	}
	public void setDescuento(boolean descuento) {
		this.descuento = descuento;
	}
	
	
}
