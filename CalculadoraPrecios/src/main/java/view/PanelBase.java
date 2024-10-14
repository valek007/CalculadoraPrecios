package main.java.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelBase extends JPanel {

    private static final long serialVersionUID = 1L;

    private boolean modoOscuro; // Campo para almacenar el estado del modo oscuro

    // Constructor
    public PanelBase() {
        // Configuración inicial si es necesario
    }

    // Método para cambiar al modo claro
    public void setModoClaro() {
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        modoOscuro = false; // Actualiza el estado
        cambiarColoresComponentes(); // Actualizar todos los componentes
    }

    // Método para cambiar al modo oscuro
    public void setModoOscuro() {
        this.setBackground(Color.DARK_GRAY);
        this.setForeground(Color.WHITE);
        modoOscuro = true; // Actualiza el estado
        cambiarColoresComponentes(); // Actualizar todos los componentes
    }

    // Método para comprobar si está en modo oscuro
    protected boolean isModoOscuro() {
        return modoOscuro; // Devuelve el estado actual
    }

    // Método para recorrer y cambiar los colores de todos los JLabel, JTextField y JComboBox
    private void cambiarColoresComponentes() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof JLabel) {
                cambiarColoresEtiqueta((JLabel) comp);
            } else if (comp instanceof JTextField) {
                cambiarColoresTextField((JTextField) comp);
            } else if (comp instanceof JComboBox) {
                cambiarColoresComboBox((JComboBox<?>) comp);
            }
        }
    }

    // Método para cambiar el color de un JLabel
    protected void cambiarColoresEtiqueta(JLabel label) {
        Color texto = isModoOscuro() ? Color.WHITE : Color.BLACK; // Color del texto
        label.setForeground(texto); // Cambiar solo el color del texto
    }

    // Método para cambiar colores de JTextField
    protected void cambiarColoresTextField(JTextField textField) {
        Color texto = isModoOscuro() ? Color.WHITE : Color.BLACK; // Color del texto
        Color fondo = isModoOscuro() ? Color.DARK_GRAY : Color.WHITE; // Color del fondo
        textField.setForeground(texto);
        textField.setBackground(fondo);
    }

    // Método para cambiar colores de JComboBox
    protected void cambiarColoresComboBox(JComboBox<?> comboBox) {
        Color texto = isModoOscuro() ? Color.WHITE : Color.BLACK; // Color del texto
        Color fondo = isModoOscuro() ? Color.DARK_GRAY : Color.WHITE; // Color del fondo
        comboBox.setForeground(texto);
        comboBox.setBackground(fondo);
    }
}




