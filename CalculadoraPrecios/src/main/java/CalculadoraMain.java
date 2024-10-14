package main.java;

import javax.swing.SwingUtilities;

import main.java.view.VentanaPrincipal;

public class CalculadoraMain {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
	}
}
