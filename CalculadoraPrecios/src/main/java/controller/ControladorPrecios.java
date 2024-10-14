package main.java.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import main.java.model.GestorPrecios;
import main.java.view.PanelPrecios;

public class ControladorPrecios {

    private GestorPrecios gestorPrecios;
    private PanelPrecios panelPrecios;

    public ControladorPrecios(GestorPrecios gestorPrecios, PanelPrecios panelPrecios) {
        this.gestorPrecios = gestorPrecios;
        this.panelPrecios = panelPrecios;

        // Añadir comportamiento a los desplegables y botón guardar
        configurarEventos();
    }

    // Configura los eventos de los desplegables y botones
    private void configurarEventos() {
        // Cargar precios según selección de tipo de habitación y rango de fechas
        panelPrecios.getComboTiposHabitacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPrecioPorNoche();
            }
        });

        panelPrecios.getComboRangosFechas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPrecioPorNoche();
            }
        });

        // Guardar los cambios
        panelPrecios.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });
       
    }

    // Actualizar el precio por noche según el tipo de habitación y el rango de fechas seleccionado    
    private void actualizarPrecioPorNoche() {
        String tipoHabitacion = panelPrecios.getComboTiposHabitacion().getSelectedItem().toString();
        String rangoFecha = panelPrecios.getComboRangosFechas().getSelectedItem().toString();
        
        // Convertimos el rango a la fecha de inicio
        LocalDate fechaSeleccionada = gestorPrecios.convertirRangoAFechaInicio(rangoFecha);

        // Obtenemos el precio de la habitación para la fecha seleccionada
        double precioPorNoche = gestorPrecios.obtenerPrecio(tipoHabitacion, fechaSeleccionada);
        
        // Actualizamos el campo de texto
        panelPrecios.getTxtPrecioPorNoche().setText(String.valueOf(precioPorNoche));
    }

    // Guardar los cambios desde el PanelPrecios al GestorPrecios
    private void guardarCambios() {
        try {
            double nuevoPrecioMP = Double.parseDouble(panelPrecios.getTxtMediaPension().getText());
            double nuevoDescuento = Double.parseDouble(panelPrecios.getTxtDescuento().getText());
            double nuevoDUI = Double.parseDouble(panelPrecios.getTxtDUI().getText());
            //double nuevoPrecioPorNoche = Double.parseDouble(panelPrecios.getTxtPrecioPorNoche().getText());

            gestorPrecios.setPrecioMediaPension(nuevoPrecioMP);
            gestorPrecios.setFactorDescuento(nuevoDescuento);
            gestorPrecios.setFactorDobleUsoIndividual(nuevoDUI);

            // Mostrar mensaje de éxito
            System.out.println("Precios actualizados correctamente.");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Formato de número incorrecto.");
        }
    }
}
