package main.java.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.java.model.GestorPreciosGeneral;
import main.java.view.PanelPreciosGeneral;

public class ControladorPreciosGeneral {

    private GestorPreciosGeneral gestorPreciosGeneral;
    private PanelPreciosGeneral panelPreciosGeneral;

    // Constructor
    public ControladorPreciosGeneral(GestorPreciosGeneral gestorPreciosGeneral, PanelPreciosGeneral panelPreciosGeneral) {
        this.gestorPreciosGeneral = gestorPreciosGeneral;
        this.panelPreciosGeneral = panelPreciosGeneral;
    }

    // Método para guardar los cambios
    public void guardarCambios(Map<String, JTextField> mapFechaInicio, Map<String, JTextField> mapPrecio) {
        try {
            DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (String fechaOriginal : mapFechaInicio.keySet()) {
                // Validar que los JTextField no sean nulos
                JTextField txtFechaInicio = mapFechaInicio.get(fechaOriginal);
                JTextField txtPrecio = mapPrecio.get(fechaOriginal);

                if (txtFechaInicio != null && txtPrecio != null) {
                    // Obtener los valores de los JTextField
                    String nuevaFechaInicioTexto = txtFechaInicio.getText().trim();
                    String nuevoPrecioTexto = txtPrecio.getText().trim();

                    // Convertir la nueva fecha al formato esperado por LocalDate
                    LocalDate nuevaFechaInicio = LocalDate.parse(nuevaFechaInicioTexto, formatterInput);

                    // Manejar la conversión del precio y validar si es un número
                    double nuevoPrecio;
                    try {
                        nuevoPrecio = Double.parseDouble(nuevoPrecioTexto);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelPreciosGeneral, "El valor del precio no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        continue;  // Saltar al siguiente registro si el precio es inválido
                    }

                    // Convertir la fecha de nuevo al formato correcto para guardar en el archivo .properties
                    String nuevaFechaFormatoArchivo = nuevaFechaInicio.format(formatterOutput);

                    // Actualizar en el GestorPreciosGeneral con la nueva fecha y el nuevo precio
                    gestorPreciosGeneral.setFechaInicio(fechaOriginal, nuevaFechaFormatoArchivo);
                    gestorPreciosGeneral.setPrecio(fechaOriginal, nuevaFechaFormatoArchivo, nuevoPrecio);
                }
            }

            // Guardar los cambios en el archivo properties
            gestorPreciosGeneral.guardarCambios();
            JOptionPane.showMessageDialog(panelPreciosGeneral, "Cambios guardados exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelPreciosGeneral, "Error al guardar los cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}