package com.starcritic.dam_proyect.data.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utilidades de configuración heredadas de la antigua capa de acceso a datos.
 * En la versión cliente ya no hay conexión JDBC: los datos se obtienen vía API
 * REST (ver {@link com.starcritic.dam_proyect.data.api.rest.ApiClient}).
 *
 * @author Jesús Santos Baquero
 */
public class OperationsDB {

    private static final String CONFIG_PATH = "config.properties";
    private static Properties cachedProps;

    protected static String getProperty(String key) {
        if (cachedProps == null) {
            cachedProps = new Properties();
            try (InputStream input = OperationsDB.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
                if (input == null) {
                    System.err.println("No se encontró config.properties en el classpath");
                    return "";
                }
                cachedProps.load(input);
            } catch (IOException ex) {
                System.err.println("Error al leer config.properties");
                ex.printStackTrace();
                return "";
            }
        }
        String value = cachedProps.getProperty(key, "").trim();
        if (value.isEmpty()) {
            System.err.println("Propiedad no configurada: " + key);
        }
        return value;
    }

    protected static String valueOrEmpty(String value) {
        return (value != null) ? value : "";
    }
}
