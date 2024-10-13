package main.java.view;

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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import main.java.model.GestorPrecios;

public class PanelPrecios extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Componentes del panel
    private JComboBox<String> comboTiposHabitacion;
    private JComboBox<String> comboRangosFechas;
    private JTextField txtMediaPension;
    private JTextField txtDescuento;
    private JTextField txtDUI;
    private JTextField txtPrecioPorNoche;
    private JButton btnGuardar;
    private JToggleButton toggleModoOscuroClaro;
    private JButton btnVolver;

    private GestorPrecios gestorPrecios;

    // Constructor del PanelPrecios
    public PanelPrecios(GestorPrecios gestorPrecios, VentanaPrincipal ventanaPrincipal) {
        this.gestorPrecios = gestorPrecios;

        // Configuración del layout con GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre componentes

        // Inicializar los componentes
        comboTiposHabitacion = new JComboBox<>(gestorPrecios.getTiposHabitacion());
        comboRangosFechas = new JComboBox<>(gestorPrecios.obtenerRangosDeFechas()); // ya no se cargará dinámicamente
        txtMediaPension = new JTextField(String.valueOf(gestorPrecios.getPrecioMediaPension()), 10);
        txtDescuento = new JTextField(String.valueOf(gestorPrecios.getFactorDescuento()), 10);
        txtDUI = new JTextField(String.valueOf(gestorPrecios.getFactorDobleUsoIndividual()), 10);
        txtPrecioPorNoche = new JTextField("0.0", 10); // Se actualizará dinámicamente

        btnGuardar = new JButton("Guardar");

        // Crear JToggleButton para cambiar entre modo oscuro y claro
        //ImageIcon iconMoon = new ImageIcon("icons/icono_luna.png"); // Ruta del ícono de luna
        //ImageIcon iconSun = new ImageIcon("icons/icono_sol.png");   // Ruta del ícono de sol
        toggleModoOscuroClaro = new JToggleButton(); // Comienza en modo oscuro
        toggleModoOscuroClaro.setSelected(false); // Inicia en modo oscuro

        btnVolver = new JButton("Volver");

        // Añadir componentes al panel con posiciones específicas
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Abarca dos columnas
        add(toggleModoOscuroClaro, gbc);

        gbc.gridwidth = 1; // Restablecer gridwidth

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tipo de Habitación:"), gbc);

        gbc.gridx = 1;
        add(comboTiposHabitacion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Rango de Fechas:"), gbc);

        gbc.gridx = 1;
        add(comboRangosFechas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Precio por Noche:"), gbc);

        gbc.gridx = 1;
        add(txtPrecioPorNoche, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Media Pensión:"), gbc);

        gbc.gridx = 1;
        add(txtMediaPension, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Descuento Early Booking:"), gbc);

        gbc.gridx = 1;
        add(txtDescuento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Doble Uso Individual:"), gbc);

        gbc.gridx = 1;
        add(txtDUI, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(btnGuardar, gbc);

        gbc.gridx = 1;
        add(btnVolver, gbc);
        
     // Test
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

//        // Acción del botón para cambiar entre modo oscuro y claro
//        toggleModoOscuroClaro.addItemListener(e -> {
//            if (toggleModoOscuroClaro.isSelected()) {
//                FlatDarkLaf.setup(); // Cambiar a modo oscuro
//                toggleModoOscuroClaro.setIcon(iconMoon); // Cambiar al ícono de luna
//            } else {
//                FlatLightLaf.setup(); // Cambiar a modo claro
//                toggleModoOscuroClaro.setIcon(iconSun); // Cambiar al ícono de sol
//            }
//            SwingUtilities.invokeLater(() -> {
//                SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(PanelPrecios.this));
//            });
//        });
        
        
        
	}
    //Getters
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

	public JToggleButton getToggleModoOscuroClaro() {
		return toggleModoOscuroClaro;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public GestorPrecios getGestorPrecios() {
		return gestorPrecios;
	}
}
