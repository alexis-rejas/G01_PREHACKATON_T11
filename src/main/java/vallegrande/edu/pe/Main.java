package vallegrande.edu.pe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vallegrande.edu.pe.util.JasperCompiler;
import java.io.IOException;

/**
 * Main - Punto de entrada de la aplicación
 * Sistema de Gestión de Eventos - Pre-Hackatón T11
 * Vallegrande - Grupo G01
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Compilar reportes JasperReports al iniciar
            JasperCompiler.compileReports();
            
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/EventoView.fxml")
            );
            
            Scene scene = new Scene(loader.load(), 1400, 800);
            primaryStage.setTitle("G01 - Sistema de Gestión de Eventos y Conferencias");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(1000);
            primaryStage.setMinHeight(600);
            primaryStage.show();
            
            System.out.println("═══════════════════════════════════════════");
            System.out.println("✓ Aplicación iniciada");
            System.out.println("  Vallegrande - G01");
            System.out.println("  Sistema de Gestión de Eventos");
            System.out.println("═══════════════════════════════════════════");
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
