package main.java.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PanelReservas extends PanelBase {

	private static final long serialVersionUID = 1L;
	private JButton btnConfiguracion;
	private VentanaPrincipal ventanaPrincipal;

	public PanelReservas(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;

		// Configuración del layout y colores
		setLayout(new FlowLayout());

		// Botón Configuración
		btnConfiguracion = new JButton("Configuración");
		btnConfiguracion.setForeground(Color.BLACK);
		btnConfiguracion.setBackground(Color.WHITE);
		add(btnConfiguracion);

		// Acción del botón para cambiar al PanelPrecios
		btnConfiguracion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.mostrarPanelPrecios();
			}
		});
	}

	// Getters
	public VentanaPrincipal getVentanaPrincipal() {
		return ventanaPrincipal;
	}

}