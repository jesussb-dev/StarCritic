/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para importar ficheros CSV separados por punto y coma. Descarta
 * filas vacias, cabeceras y filas cuya primera columna no sea numérica.
 * @author Jesús Santos Baquero
 */
public class CsvImport {

    private static final String SEPARATOR = ";";

    /**
     * Importar un fichero CSV y devolver sus filas validas.
     * @param rutaArchivo la ruta absoluta del fichero CSV.
     * @return las filas validas en formato lista de arrays de columnas.
     * @throws IOException si ocurre un error al leer el fichero.
     */
    public static List<String[]> importarCsv(String rutaArchivo) throws IOException {
        List<String[]> filas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8))) {

            String linea;
            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] columnasCrudas = linea.split(SEPARATOR, -1);
                List<String> columnas = new ArrayList<>();
                for (String c : columnasCrudas) {
                    String valor = c.trim();
                    if (!valor.isEmpty()) {
                        columnas.add(valor);
                    }
                }

                if (columnas.isEmpty()) {
                    continue;
                }

                if (columnas.get(0).toLowerCase().startsWith("page ")
                        || !esNumero(columnas.get(0))) {
                    continue;
                }

                filas.add(columnas.toArray(new String[0]));
            }
        }

        return filas;
    }

    private static boolean esNumero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
