package vallegrande.edu.pe.util;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.nio.file.Paths;

/**
 * Utilidad para compilar archivos JRXML a JASPER
 * Se ejecuta durante la inicialización de la aplicación
 */
public class JasperCompiler {

    public static void compileReports() {
        try {
            // Ruta del archivo JRXML
            String jrxmlPath = "src/main/resources/reports/EventosReport.jrxml";
            String jasperPath = "src/main/resources/reports/EventosReport.jasper";

            File jrxmlFile = new File(jrxmlPath);
            
            if (jrxmlFile.exists()) {
                System.out.println("Compilando reporte: " + jrxmlPath);
                JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
                System.out.println("✓ Reporte compilado exitosamente: " + jasperPath);
            } else {
                System.err.println("⚠ Archivo JRXML no encontrado: " + jrxmlPath);
            }
        } catch (JRException e) {
            System.err.println("✗ Error compilando reportes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        compileReports();
    }
}
