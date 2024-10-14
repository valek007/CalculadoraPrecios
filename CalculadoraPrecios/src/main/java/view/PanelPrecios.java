package main.java.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.model.GestorPrecios;
import main.java.view.component.EventSwitchSelected;
import main.java.view.component.SwitchButton;

public class PanelPrecios extends PanelBase {

	private static final long serialVersionUID = 1L;

	// Componentes del panel
	private JComboBox<String> comboTiposHabitacion;
	private JComboBox<String> comboRangosFechas;
	private JTextField txtMediaPension;
	private JTextField txtDescuento;
	private JTextField txtDUI;
	private JTextField txtPrecioPorNoche;
	private JButton btnGuardar;
	private JButton btnVolver;
	private SwitchButton switchButton;

	private JLabel labelTipoHabitacion;
	private JLabel labelRangoFechas;
	private JLabel labelPrecioPorNoche;
	private JLabel labelMediaPension;
	private JLabel labelDescuento;
	private JLabel labelDUI;

	private GestorPrecios gestorPrecios;
	private VentanaPrincipal ventanaPrincipal;

	// Constructor del PanelPrecios
	public PanelPrecios(GestorPrecios gestorPrecios, VentanaPrincipal ventanaPrincipal) {
		this.gestorPrecios = gestorPrecios;
		this.ventanaPrincipal = ventanaPrincipal;

		// Configuración del layout con GridBagLayout
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre componentes

		// Inicializar los componentes
		comboTiposHabitacion = new JComboBox<>(gestorPrecios.getTiposHabitacion());
		comboRangosFechas = new JComboBox<>(gestorPrecios.obtenerRangosDeFechas());
		txtMediaPension = new JTextField(String.valueOf(gestorPrecios.getPrecioMediaPension()), 10);
		txtDescuento = new JTextField(String.valueOf(gestorPrecios.getFactorDescuento()), 10);
		txtDUI = new JTextField(String.valueOf(gestorPrecios.getFactorDobleUsoIndividual()), 10);
		txtPrecioPorNoche = new JTextField("0.0", 10);

		btnGuardar = new JButton("Guardar");
		btnVolver = new JButton("Volver");
		switchButton = new SwitchButton();

		// Inicializar etiquetas (JLabel)
		labelTipoHabitacion = new JLabel("Tipo de Habitación:");
		labelRangoFechas = new JLabel("Rango de Fechas:");
		labelPrecioPorNoche = new JLabel("Precio por Noche:");
		labelMediaPension = new JLabel("Media Pensión:");
		labelDescuento = new JLabel("Descuento Early Booking:");
		labelDUI = new JLabel("Doble Uso Individual:");

		// Añadir componentes al panel con posiciones específicas
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1; // Mantener el SwitchButton en una sola columna
		gbc.fill = GridBagConstraints.NONE; // Evitar que se expanda horizontalmente
		gbc.anchor = GridBagConstraints.WEST; // Alinear el switchButton a la izquierda
		gbc.weightx = 0; // Evitar que tome más espacio horizontal
		gbc.weighty = 0; // Evitar que tome más espacio vertical
		switchButton.setPreferredSize(new Dimension(40, 20)); // Tamaño preferido más pequeño
		add(switchButton, gbc);

		// Ajustar tamaño de los JTextField
		txtMediaPension.setPreferredSize(new Dimension(50, 25)); // Reducir tamaño de los campos de texto
		txtDescuento.setPreferredSize(new Dimension(50, 25));
		txtDUI.setPreferredSize(new Dimension(50, 25));
		txtPrecioPorNoche.setPreferredSize(new Dimension(50, 25));

		gbc.gridwidth = 1;

		// Añadir etiquetas y campos de texto al panel
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(labelTipoHabitacion, gbc);

		gbc.gridx = 1;
		add(comboTiposHabitacion, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(labelRangoFechas, gbc);

		gbc.gridx = 1;
		add(comboRangosFechas, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(labelPrecioPorNoche, gbc);

		gbc.gridx = 1;
		add(txtPrecioPorNoche, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add(labelMediaPension, gbc);

		gbc.gridx = 1;
		add(txtMediaPension, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		add(labelDescuento, gbc);

		gbc.gridx = 1;
		add(txtDescuento, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		add(labelDUI, gbc);

		gbc.gridx = 1;
		add(txtDUI, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		add(btnGuardar, gbc);

		gbc.gridx = 1;
		add(btnVolver, gbc);

		// Configuración del evento para cambiar entre modo claro/oscuro
		switchButton.addEventSelected(new EventSwitchSelected() {
			@Override
			public void onSelected(boolean selected) {
				if (selected) {
					setModoOscuro();
					// Llamar al método para actualizar otros paneles aquí
					updateAllPanelsToDarkMode();
				} else {
					setModoClaro();
					// Llamar al método para actualizar otros paneles aquí
					updateAllPanelsToLightMode();
				}
			}
		});
		// Acción doble click sobre el desplegable rangosFechas
		comboRangosFechas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // Verificar si es un doble click
					ventanaPrincipal.mostrarPanelPreciosGeneral();
				}
			}
		});

		// Acción del botón Volver para cambiar al PanelReservas
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.mostrarPanelReservas(); // Volver a PanelReservas
			}
		});

		// Inicializar modo claro por defecto
		setModoClaro();
	}

	private void updateAllPanelsToDarkMode() {
		// Aquí se debe implementar la lógica para actualizar todos los paneles a modo
		// oscuro
		ventanaPrincipal.getPanelReservas().setModoOscuro();
		ventanaPrincipal.getPanelPreciosGeneral().setModoOscuro();
		super.setModoOscuro();

	}

	private void updateAllPanelsToLightMode() {
		// Aquí se debe implementar la lógica para actualizar todos los paneles a modo
		// claro
		ventanaPrincipal.getPanelReservas().setModoClaro();
		ventanaPrincipal.getPanelPreciosGeneral().setModoClaro();
		super.setModoClaro();
	}

	// Getters
	public JComboBox<String> getComboTiposHabitacion() {
		return comboTiposHabitacion;
	}

	public JComboBox<String> getComboRangosFechas() {
		return comboRangosFechas;
	}

	public JTextField getTxtMediaPension() {
		return txtMediaPension;
	}

	public JTextField getTxtDescuento() {
		return txtDescuento;
	}

	public JTextField getTxtDUI() {
		return txtDUI;
	}

	public JTextField getTxtPrecioPorNoche() {
		return txtPrecioPorNoche;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public SwitchButton getSwitchButton() {
		return switchButton;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public GestorPrecios getGestorPrecios() {
		return gestorPrecios;
	}

}
