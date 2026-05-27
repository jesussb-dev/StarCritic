package com.starcritic.dam_proyect.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author jsanbaq
 */
public class CsvImportTest {
    @TempDir
    Path tempDir;

    /** Escribe el contenido en un CSV temporal (UTF-8) y devuelve su ruta. */
    private String escribirCsv(String contenido) throws IOException {
        Path archivo = tempDir.resolve("contenido.csv");
        Files.writeString(archivo, contenido, StandardCharsets.UTF_8);
        return archivo.toString();
    }

    @Test
    public void importaFilasValidas() throws IOException {
        String csv = """
                ID_contenido;Titulo;Anio
                1;Inception;2010
                2;Matrix;1999
                """;

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));
        assertEquals(2, filas.size());
        assertArrayEquals(new String[]{"1", "Inception", "2010"}, filas.get(0));
        assertArrayEquals(new String[]{"2", "Matrix", "1999"}, filas.get(1));
    }

    @Test
    public void ignoraLineasAntesDeCabecera() throws IOException {
        String csv = """
                texto de relleno
                otra linea cualquiera
                ID_contenido;Titulo
                5;Zelda
                """;

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(1, filas.size());
        assertArrayEquals(new String[]{"5", "Zelda"}, filas.get(0));
    }

    @Test
    public void ignoraLineasEnBlanco() throws IOException {
        String csv = """
                ID_contenido;Titulo

                1;A

                2;B
                """;

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(2, filas.size());
    }

    @Test
    public void descartaPrimeraColumnaNoNumerica() throws IOException {
        String csv = """
                ID_contenido;Titulo
                abc;NoNumero
                3;Valido
                """;

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(1, filas.size());
        assertArrayEquals(new String[]{"3", "Valido"}, filas.get(0));
    }

    @Test
    public void descartaFilasDePaginacion() throws IOException {
        String csv = """
                ID_contenido;Titulo
                page 1;ignorar
                7;Valido
                """;

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(1, filas.size());
        assertArrayEquals(new String[]{"7", "Valido"}, filas.get(0));
    }

    @Test
    public void eliminaColumnasVacias() throws IOException {
        String csv = """
                ID_contenido;Titulo;Genero
                8;;Accion
                """;

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(1, filas.size());
        // La columna vacía intermedia se descarta: quedan 2 columnas, no 3.
        assertArrayEquals(new String[]{"8", "Accion"}, filas.get(0));
    }

    @Test
    public void recortaEspacios() throws IOException {
        // String explícito (no text block) para garantizar los espacios finales.
        String csv = "ID_contenido;Titulo\n"
                + "  9  ;  Con espacios  \n";

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(1, filas.size());
        assertArrayEquals(new String[]{"9", "Con espacios"}, filas.get(0));
    }

    @Test
    public void sinCabeceraImportaFilasNumericas() throws IOException {
        String csv = """
                no hay cabecera valida
                1;dato suelto
                """;

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(1, filas.size());
        assertArrayEquals(new String[]{"1", "dato suelto"}, filas.get(0));
    }

    @Test
    public void importaCsvExportadoPorJasper() throws IOException {
        // Reproduce el formato real que produce JasperExport: sin cabecera de
        // texto, con filas de título/fecha/paginación intercaladas que deben
        // descartarse y filas de datos con celdas vacías intermedias.
        String csv = ";;;;;;;;;\r\n"
                + ";;;;;;;;;\r\n"
                + ";;;;;;martes 26 mayo 2026;;;\r\n"
                + ";;;;;;;;;\r\n"
                + "16;;;Grand Theft Auto V;;3498;;VIDEOJUEGO;;\r\n"
                + "1;;;The Shawshank Redemption;;tt0111161;;PELICULA;;\r\n"
                + "12;;;Game of Thrones;;tt0944947;;SERIE;;\r\n"
                + ";;;;;;;;Page 1 of; 1\r\n"
                + ";martes 26 mayo 2026;;;;;;;;\r\n";

        List<String[]> filas = CsvImport.importarCsv(escribirCsv(csv));

        assertEquals(3, filas.size());
        assertArrayEquals(new String[]{"16", "Grand Theft Auto V", "3498", "VIDEOJUEGO"}, filas.get(0));
        assertArrayEquals(new String[]{"1", "The Shawshank Redemption", "tt0111161", "PELICULA"}, filas.get(1));
        assertArrayEquals(new String[]{"12", "Game of Thrones", "tt0944947", "SERIE"}, filas.get(2));
    }

    @Test
    public void archivoInexistenteLanzaIOException() {
        String rutaInexistente = tempDir.resolve("no_existe.csv").toString();

        assertThrows(IOException.class, () -> CsvImport.importarCsv(rutaInexistente));
    }
}
