package vallegrande.edu.pe.dao;

import vallegrande.edu.pe.model.Evento;
import java.time.LocalDate;
import java.util.*;

/**
 * EventoDAO alternativo - Datos en MEMORIA
 * Usa HashMap en lugar de base de datos SQL
 * Perfecto para desarrollo y testing SIN MySQL
 */
public class EventoDaoMemory implements IDao<Evento> {
    
    private static Map<Integer, Evento> eventos = new HashMap<>();
    private static int nextId = 1;

    // Datos precargados de prueba
    static {
        // Crear eventos de prueba (sin ID, se asignan después)
        Evento e1 = new Evento("Hackatón 2026", 
            "Competencia de programación de 48 horas", 
            LocalDate.of(2026, 12, 15), "Auditorio Principal", "Presencial", "Programado");
        e1.setIdEvento(1);
        eventos.put(1, e1);
        
        Evento e2 = new Evento("Webinar Python", 
            "Introducción a frameworks modernos", 
            LocalDate.of(2026, 11, 20), "Online", "Virtual", "En_Curso");
        e2.setIdEvento(2);
        eventos.put(2, e2);
        
        Evento e3 = new Evento("Workshop JavaFX", 
            "Desarrollo de interfaces gráficas", 
            LocalDate.of(2026, 11, 25), "Aula 101", "Hibrida", "Programado");
        e3.setIdEvento(3);
        eventos.put(3, e3);
        
        Evento e4 = new Evento("Charla Clean Code", 
            "Buenas prácticas en desarrollo", 
            LocalDate.of(2026, 11, 18), "Aula 202", "Presencial", "Programado");
        e4.setIdEvento(4);
        eventos.put(4, e4);
        
        Evento e5 = new Evento("Webinar Bases de Datos", 
            "SQL avanzado y modelado", 
            LocalDate.of(2026, 12, 5), "Teams", "Virtual", "Programado");
        e5.setIdEvento(5);
        eventos.put(5, e5);
        
        nextId = 6;
    }

    @Override
    public boolean create(Evento evento) {
        try {
            evento.setIdEvento(nextId++);
            eventos.put(evento.getIdEvento(), evento);
            System.out.println("✓ Evento guardado en memoria (ID: " + evento.getIdEvento() + ")");
            return true;
        } catch (Exception e) {
            System.err.println("Error guardando evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Evento read(int id) {
        return eventos.get(id);
    }

    @Override
    public List<Evento> readAll() {
        return new ArrayList<>(eventos.values());
    }

    @Override
    public boolean update(Evento evento) {
        try {
            if (eventos.containsKey(evento.getIdEvento())) {
                eventos.put(evento.getIdEvento(), evento);
                System.out.println("✓ Evento actualizado en memoria");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error actualizando evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (eventos.containsKey(id)) {
                eventos.remove(id);
                System.out.println("✓ Evento eliminado de memoria");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error eliminando evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Evento> search(String criteria, String value) {
        List<Evento> resultados = new ArrayList<>();
        
        for (Evento evento : eventos.values()) {
            switch (criteria.toLowerCase()) {
                case "nombre":
                    if (evento.getNombre().toLowerCase().contains(value.toLowerCase())) {
                        resultados.add(evento);
                    }
                    break;
                case "modalidad":
                    if (evento.getModalidad().equalsIgnoreCase(value)) {
                        resultados.add(evento);
                    }
                    break;
                case "estado":
                    if (evento.getEstado().equalsIgnoreCase(value)) {
                        resultados.add(evento);
                    }
                    break;
                case "lugar":
                    if (evento.getLugar().toLowerCase().contains(value.toLowerCase())) {
                        resultados.add(evento);
                    }
                    break;
            }
        }
        
        return resultados;
    }

    /**
     * Obtiene todos los eventos con información de memoria
     */
    public static String getStats() {
        return "Eventos en memoria: " + eventos.size();
    }
}
