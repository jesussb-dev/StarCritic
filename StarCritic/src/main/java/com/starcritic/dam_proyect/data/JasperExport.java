/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.data;

import com.starcritic.dam_proyect.data.database.ContenidoDB;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Exporta una lista de contenido a PDF/CSV con JasperReports. A diferencia de la
 * versión original (que llenaba el informe con una conexión JDBC directa), aquí
 * los datos se obtienen de la API REST y se inyectan como datasource en memoria,
 * de modo que el cliente no necesita acceso a la base de datos.
 *
 * @author Jesús Santos Baquero
 */
public class JasperExport {

    private static final String currentDirectory = System.getProperty("user.dir");

    private final String nombreLista;
    private final int id_Usuario;
    private final Map<Integer, String> nombreContenido;
    private final String type;

    public JasperExport(String nombreLista, int id_Usuario, Map<Integer, String> nombreContenido, String type) {
        this.nombreLista = nombreLista;
        this.id_Usuario = id_Usuario;
        this.nombreContenido = nombreContenido;
        this.type = type;
    }

    /**
     * Exportar la lista de contenido a PDF o CSV. El fichero se guarda en
     * {@code ./Listas/} con un nombre que incluye el nombre de la lista y el id
     * del usuario. Si el tipo es "pdf", se abre el visor de Jasper.
     */
    public void exportList() {
        try {
            // La plantilla usa "Times New Roman"; en SOs sin esa fuente (Linux)
            // JasperReports lanzaría JRFontNotFoundException. Sustituye por la
            // fuente por defecto en su lugar.
            JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance())
                    .setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
            Files.createDirectories(Paths.get(currentDirectory + "/Listas"));
            JasperReport report = JasperCompileManager.compileReport(
                    JasperExport.class.getClassLoader().getResourceAsStream("Example.jrxml")
            );
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nombreLista", nombreLista);
            parameters.put("id_Usuario", id_Usuario);
            parameters.put("nombreContenido", nombreContenido);

            JasperPrint print = JasperFillManager.fillReport(report, parameters, construirDatasource());
            // Comparación tolerante (mayúsculas/espacios/null): cualquier selección
            // de PDF debe guardar siempre un PDF, no caer silenciosamente a CSV.
            if (type != null && type.trim().equalsIgnoreCase("pdf")) {
                JasperExportManager.exportReportToPdfFile(
                        print,
                        currentDirectory + "/Listas/Lista_" + nombreLista + "_" + id_Usuario + ".pdf"
                );
                // El visor es Swing: abrirlo en el EDT (este método corre en hilo de fondo).
                java.awt.EventQueue.invokeLater(() -> JasperViewer.viewReport(print, false));
            } else {
                exportToCsv(print, currentDirectory + "/Listas/Lista_" + nombreLista + "_" + id_Usuario + ".csv");
            }
        } catch (IOException | JRException ex) {
            Logger.getLogger(JasperExport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Construye el datasource del informe a partir de los contenidos de la lista
     * (id + título ya conocidos) completando tipo e ID de API desde el backend.
     */
    private JRMapCollectionDataSource construirDatasource() {
        List<Map<String, ?>> filas = new ArrayList<>();
        if (nombreContenido != null) {
            for (Map.Entry<Integer, String> entrada : nombreContenido.entrySet()) {
                int idContenido = entrada.getKey();
                TipoContenido tipo = ContenidoDB.obtenerTipoContenido(idContenido);
                String apiId = ContenidoDB.obtenerApiId(idContenido, tipo);

                Map<String, Object> fila = new HashMap<>();
                fila.put("ID_contenido", idContenido);
                fila.put("titulo", entrada.getValue());
                fila.put("ID_Api", apiId);
                fila.put("tipo", tipo != null ? tipo.name() : null);
                filas.add(fila);
            }
        }
        return new JRMapCollectionDataSource(filas);
    }

    private void exportToCsv(JasperPrint print, String outputPath) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(outputPath));

        SimpleCsvExporterConfiguration config = new SimpleCsvExporterConfiguration();
        config.setFieldDelimiter(";");
        config.setRecordDelimiter("\r\n");
        exporter.setConfiguration(config);

        exporter.exportReport();
    }
}
