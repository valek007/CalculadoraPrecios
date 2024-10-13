package main.java.view;

import java.awt.CardLayout;
import java.util.Properties;

import javax.swing.JFrame;

import main.java.config.PropertiesLoader;
import main.java.controller.ControladorPrecios;
import main.java.controller.ControladorPreciosGeneral;
import main.java.model.GestorPrecios;
import main.java.model.GestorPreciosGeneral;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private PanelReservas panelReservas;
    private PanelPrecios panelPrecios;
    private GestorPrecios gestorPrecios;
    private ControladorPrecios controladorPrecios;
    private ControladorPreciosGeneral controladorPreciosGeneral;
    private PanelPreciosGeneral panelPreciosGeneral;
    private GestorPreciosGeneral gestorPreciosGeneral;

    public VentanaPrincipal() {
        // Cargar el archivo precios.properties
        Properties propiedades = new PropertiesLoader("precios.properties").getPropiedades();
        gestorPrecios = new GestorPrecios(propiedades);
        gestorPreciosGeneral = new GestorPreciosGeneral(propiedades);

        // Configuración de la ventana
        setTitle("Calculadora de Precios");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Crear paneles
        panelReservas = new PanelReservas(this);
        panelPrecios = new PanelPrecios(gestorPrecios, this);
        panelPreciosGeneral = new PanelPreciosGeneral(gestorPreciosGeneral, this);

        // Añadir los paneles
        add(panelReservas, "reservas");
        add(panelPrecios, "precios");
        add(panelPreciosGeneral, "preciosGeneral");

        // Inicializar el controlador de precios
        controladorPrecios = new ControladorPrecios(gestorPrecios, panelPrecios);

        // CORRECCIÓN: Inicializar el controlador de precios general con las instancias correctas
        controladorPreciosGeneral = new ControladorPreciosGeneral(gestorPreciosGeneral, panelPreciosGeneral);
        
        // Mostrar el primer panel
        mostrarPanelReservas();
    }

    // Método para mostrar el PanelReservas
    public void mostrarPanelReservas() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "reservas");
    }

    // Método para mostrar el PanelPrecios
    public void mostrarPanelPrecios() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "precios");
    }
    
    public void mostrarPanelPreciosGeneral() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "preciosGeneral");
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//Getters

	public ControladorPrecios getControladorPrecios() {
		return controladorPrecios;
	}

	public ControladorPreciosGeneral getControladorPreciosGeneral() {
		return controladorPreciosGeneral;
	}
}