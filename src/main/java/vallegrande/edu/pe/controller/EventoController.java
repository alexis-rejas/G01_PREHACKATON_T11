package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.dao.EventoDao;
import vallegrande.edu.pe.model.Evento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador - Lógica de negocio para Eventos
 * Usa EventoDao para conectar a BD MySQL
 */
public class EventoController {
    private final EventoDao eventoDao;
    private ObservableList<Evento> eventosObservable;

    public EventoController() {
        this.eventoDao = new EventoDao();
        this.eventosObservable = FXCollections.observableArrayList();
        System.out.println("✓ EventoController conectado a BD");
    }

    public String crearEvento(String nombre, String descripcion, LocalDate fecha, 
                              String lugar, String modalidad, String estado) {
        if (nombre.isEmpty() || descripcion.isEmpty() || fecha == null || lugar.isEmpty()) {
            return "Error: Campos obligatorios vacíos";
        }

        if (eventoDao.search("nombre", nombre).size() > 0) {
            return "Error: Nombre de evento duplicado";
        }

        Evento evento = new Evento(nombre, descripcion, fecha, lugar, modalidad, estado);
        if (eventoDao.create(evento)) {
            cargarEventos();
            return "✓ Evento creado exitosamente";
        }
        return "Error: No se pudo crear evento";
    }

    public String actualizarEvento(int id, String nombre, String descripcion, 
                                   LocalDate fecha, String lugar, String modalidad, String estado) {
        Evento evento = eventoDao.read(id);
        if (evento == null) {
            return "Error: Evento no encontrado";
        }

        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);
        evento.setFecha(fecha);
        evento.setLugar(lugar);
        evento.setModalidad(modalidad);
        evento.setEstado(estado);

        if (eventoDao.update(evento)) {
            cargarEventos();
            return "✓ Evento actualizado";
        }
        return "Error: No se pudo actualizar";
    }

    public String eliminarEvento(int id) {
        if (eventoDao.delete(id)) {
            cargarEventos();
            return "✓ Evento eliminado";
        }
        return "Error: No se pudo eliminar";
    }

    public void cargarEventos() {
        List<Evento> eventos = eventoDao.readAll();
        eventosObservable = FXCollections.observableArrayList(eventos);
    }

    public ObservableList<Evento> getEventosObservable() {
        if (eventosObservable.isEmpty()) {
            cargarEventos();
        }
        return eventosObservable;
    }

    public Evento obtenerEvento(int id) {
        return eventoDao.read(id);
    }

    public ObservableList<Evento> buscarEventos(String criteria, String value) {
        List<Evento> resultados = eventoDao.search(criteria, value);
        return FXCollections.observableArrayList(resultados);
    }

    public int getTotalEventos() {
        return eventoDao.readAll().size();
    }
}
