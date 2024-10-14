package main.java.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private Properties propiedades;

    // Constructor que recibe el nombre del archivo de propiedades
    public PropertiesLoader(String archivo) {
        propiedades = new Properties();
        cargarPropiedades(archivo);
    }

    // Método para cargar el archivo .properties desde el classpath
    private void cargarPropiedades(String archivo) {
        // Intentar cargar el archivo desde el classpath
    	//File file = new File("src/main/resources/precios.properties");
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("main/resources/precios.properties")) {
            if (input == null) {
                throw new IOException("No se pudo encontrar el archivo: " + archivo);
            }
            // Cargar las propiedades desde el InputStream
            propiedades.load(input);
        } catch (IOException e) {
            e.printStackTrace(); // Mostrar el error en consola
        }
    }

    // Método para obtener las propiedades cargadas
    public Properties getPropiedades() {
        return propiedades;
    }
    
    // Método para guardar los cambios en el archivo properties
    public void guardarCambios() throws IOException {
    	// Guardar las propiedades en el archivo
    			File archivo = new File("src/main/resources/precios.properties");
    			try (FileOutputStream out = new FileOutputStream(archivo.getAbsolutePath())) {
    				propiedades.store(out, null);
    			}
    }
}