package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.EventoController;
import vallegrande.edu.pe.model.Evento;
import vallegrande.edu.pe.util.ReportGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * View - Controlador de la interfaz FXML
 */
public class EventoView implements Initializable {
    
    @FXML private TextField txtIdEvento;
    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private DatePicker dtpFecha;
    @FXML private TextField txtLugar;
    @FXML private ComboBox<String> cmbModalidad;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private Button btnGuardar, btnActualizar, btnLimpiar, btnReporte;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Evento> tableEventos;
    @FXML private Label lblEstado;

    private EventoController controller;
    private Evento eventoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new EventoController();
        
        // Llenar ComboBox programáticamente
        cmbModalidad.getItems().addAll("Presencial", "Virtual", "Hibrida");
        cmbEstado.getItems().addAll("Programado", "En_Curso", "Finalizado", "Cancelado");
        
        configurarTabla();
        cargarEventos();
        tableEventos.setOnMouseClicked(event -> onSeleccionarEvento());
        System.out.println("✓ Vista inicializada");
    }

    private void configurarTabla() {
        // Crear columnas programáticamente
        TableColumn<Evento, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        colId.setPrefWidth(50);

        TableColumn<Evento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(150);

        TableColumn<Evento, Object> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setPrefWidth(100);

        TableColumn<Evento, String> colLugar = new TableColumn<>("Lugar");
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        colLugar.setPrefWidth(120);

        TableColumn<Evento, String> colModalidad = new TableColumn<>("Modalidad");
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colModalidad.setPrefWidth(100);

        TableColumn<Evento, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(100);

        tableEventos.getColumns().addAll(colId, colNombre, colFecha, colLugar, colModalidad, colEstado);
        tableEventos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void cargarEventos() {
        controller.cargarEventos();
        tableEventos.setItems(controller.getEventosObservable());
        actualizarEtiqueta();
    }

    private void actualizarEtiqueta() {
        lblEstado.setText("Total: " + controller.getTotalEventos());
    }

    @FXML
    private void onGuardar() {
        if (validar()) {
            String resultado = controller.crearEvento(
                txtNombre.getText(),
                txtDescripcion.getText(),
                dtpFecha.getValue(),
                txtLugar.getText(),
                cmbModalidad.getValue(),
                cmbEstado.getValue()
            );
            alerta("Resultado", resultado);
            if (resultado.contains("✓")) {
                limpiar();
                cargarEventos();
            }
        }
    }

    @FXML
    private void onActualizar() {
        if (eventoSeleccionado == null) {
            alerta("Error", "Selecciona un evento");
            return;
        }

        if (validar()) {
            String resultado = controller.actualizarEvento(
                eventoSeleccionado.getIdEvento(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                dtpFecha.getValue(),
                txtLugar.getText(),
                cmbModalidad.getValue(),
                cmbEstado.getValue()
            );
            alerta("Resultado", resultado);
            if (resultado.contains("✓")) {
                limpiar();
                cargarEventos();
            }
        }
    }

    @FXML
    private void onLimpiar() {
        limpiar();
    }

    @FXML
    private void onBuscar() {
        String termino = txtBuscar.getText().trim();
        if (termino.isEmpty()) {
            cargarEventos();
        } else {
            var resultados = controller.buscarEventos("nombre", termino);
            tableEventos.setItems(resultados);
            lblEstado.setText("Resultados: " + resultados.size());
        }
    }

    @FXML
    private void onReporte() {
        try {
            ReportGenerator.generateEventosReport();
        } catch (Exception e) {
            alerta("Error", "No se pudo generar el reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void onSeleccionarEvento() {
        Evento evento = tableEventos.getSelectionModel().getSelectedItem();
        if (evento != null) {
            eventoSeleccionado = evento;
            txtIdEvento.setText(String.valueOf(evento.getIdEvento()));
            txtNombre.setText(evento.getNombre());
            txtDescripcion.setText(evento.getDescripcion());
            dtpFecha.setValue(evento.getFecha());
            txtLugar.setText(evento.getLugar());
            cmbModalidad.setValue(evento.getModalidad());
            cmbEstado.setValue(evento.getEstado());
        }
    }

    private boolean validar() {
        if (txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() ||
            dtpFecha.getValue() == null || txtLugar.getText().isEmpty() ||
            cmbModalidad.getValue() == null || cmbEstado.getValue() == null) {
            alerta("Validación", "Llena todos los campos");
            return false;
        }
        return true;
    }

    private void limpiar() {
        txtIdEvento.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        dtpFecha.setValue(null);
        txtLugar.clear();
        cmbModalidad.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();
        eventoSeleccionado = null;
    }

    private void alerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
