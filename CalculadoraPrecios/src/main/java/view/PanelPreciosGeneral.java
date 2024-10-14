package main.java.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.model.GestorPreciosGeneral;

public class PanelPreciosGeneral extends PanelBase {

	private static final long serialVersionUID = 1L;

	private Map<String, JTextField> mapFechaInicio;
	private Map<String, JTextField> mapPrecio;
	private JComboBox<String> comboTiposHabitacion;
	private JButton btnGuardar;
	private JButton btnVolver;

	// Constructor
	public PanelPreciosGeneral(GestorPreciosGeneral gestorPreciosGeneral, VentanaPrincipal ventanaPrincipal) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 5, 10, 5);

		mapFechaInicio = new HashMap<>();
		mapPrecio = new HashMap<>();
		comboTiposHabitacion = new JComboBox<>(gestorPreciosGeneral.getTiposHabitacion());

		int row = 0;
		for (String fechaInicio : gestorPreciosGeneral.obtenerFechasInicio()) {
			gbc.gridx = 0;
			gbc.gridy = row;

			JComboBox<String> comboTiposHabitacionFila = new JComboBox<>(gestorPreciosGeneral.getTiposHabitacion());
			add(comboTiposHabitacionFila, gbc);

			gbc.gridx = 1;
			JTextField txtFecha = new JTextField(fechaInicio, 10);
			add(txtFecha, gbc);
			mapFechaInicio.put(fechaInicio, txtFecha); // Usamos la fechaInicio como clave

			gbc.gridx = 2;
			JLabel labelPrecio = new JLabel("Precio:"); // Crear JLabel para Precio
			cambiarColoresEtiqueta(labelPrecio); // Cambiar color del JLabel
			add(labelPrecio, gbc);

			gbc.gridx = 3;
			JTextField txtPrecio = new JTextField(String.valueOf(
					gestorPreciosGeneral.getPrecio(comboTiposHabitacionFila.getSelectedItem().toString(), fechaInicio)),
					10);
			txtPrecio.setEditable(false); // Hacer que el campo de precio no sea editable por el usuario
			cambiarColoresTextField(txtPrecio); // Cambiar color del JTextField
			add(txtPrecio, gbc);
			mapPrecio.put(fechaInicio, txtPrecio); // Usar la fechaInicio como clave para el precio

			// Listener para actualizar el precio cuando cambia el tipo de habitación
			comboTiposHabitacionFila.addActionListener(e -> {
				String tipoHabitacion = (String) comboTiposHabitacionFila.getSelectedItem();
				JTextField txtPrecioActualizado = mapPrecio.get(fechaInicio);
				if (txtPrecioActualizado != null) {
					String nuevoPrecio = String.valueOf(gestorPreciosGeneral.getPrecio(tipoHabitacion, fechaInicio));
					txtPrecioActualizado.setText(nuevoPrecio);
					cambiarColoresTextField(txtPrecioActualizado); // Cambiar el color del precio al actualizar
				}
			});

			row++;
		}

		// Botón para guardar cambios
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 2;
		btnGuardar = new JButton("Guardar");
		add(btnGuardar, gbc);

		// Botón para volver
		gbc.gridx = 2;
		gbc.gridwidth = 2;
		btnVolver = new JButton("Volver");
		add(btnVolver, gbc);

		// Acciones de los botones
		btnGuardar.addActionListener(e -> {
			// Guardar los cambios
			ventanaPrincipal.getControladorPreciosGeneral().guardarCambios(mapFechaInicio, mapPrecio);
		});

		btnVolver.addActionListener(e -> {
			// Volver a PanelPrecios
			ventanaPrincipal.mostrarPanelPrecios();
		});
	}

	public JComboBox<String> getComboTiposHabitacion() {
		return comboTiposHabitacion;
	}
	
	
}
