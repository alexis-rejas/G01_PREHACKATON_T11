package vallegrande.edu.pe.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import vallegrande.edu.pe.dao.EventoDao;
import vallegrande.edu.pe.model.Evento;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilidad para generar reportes con JasperReports
 */
public class ReportGenerator {
    private static final String REPORTS_PATH = "/reports/";

    /**
     * Genera reporte de eventos
     */
    public static void generateEventosReport() {
        try {
            // Cargar el archivo JRXML compilado
            InputStream reportStream = ReportGenerator.class.getResourceAsStream(REPORTS_PATH + "EventosReport.jasper");
            
            if (reportStream == null) {
                System.err.println("Error: Archivo de reporte no encontrado");
                return;
            }

            // Obtener conexión a BD
            Connection conn = null;
            try {
                conn = DatabaseConnection.getInstance().getConnection();
            } catch (Exception e) {
                System.err.println("Advertencia: Sin conexión a BD. Usando datos vacíos.");
                conn = null;
            }
            
            if (conn == null) {
                System.err.println("No hay datos disponibles para el reporte");
                return;
            }

            // Parámetros del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("titulo", "Reporte General de Eventos");

            // Generar reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                reportStream,
                parameters,
                conn
            );

            // Mostrar reporte
            JasperViewer.viewReport(jasperPrint, false);

            System.out.println("✓ Reporte de Eventos generado");

        } catch (JRException e) {
            System.err.println("Error generando reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exporta reporte a PDF
     */
    public static void exportEventosToPDF(String outputPath) {
        try {
            InputStream reportStream = ReportGenerator.class.getResourceAsStream(REPORTS_PATH + "EventosReport.jasper");
            
            if (reportStream == null) {
                System.err.println("Error: Archivo de reporte no encontrado");
                return;
            }

            Connection conn = null;
            try {
                conn = DatabaseConnection.getInstance().getConnection();
            } catch (Exception e) {
                System.err.println("Advertencia: Sin conexión a BD.");
                conn = null;
            }
            
            if (conn == null) {
                System.err.println("No hay datos disponibles para exportar");
                return;
            }
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("titulo", "Reporte General de Eventos");

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                reportStream,
                parameters,
                conn
            );

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
            System.out.println("✓ Reporte exportado a: " + outputPath);

        } catch (JRException e) {
            System.err.println("Error exportando reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
