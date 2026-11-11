package vallegrande.edu.pe.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model - Entidad Evento
 * Representa un evento o conferencia en el sistema
 */
public class Evento {
    private int idEvento;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private String lugar;
    private String modalidad;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Evento() {}

    public Evento(String nombre, String descripcion, LocalDate fecha, String lugar, 
                  String modalidad, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.lugar = lugar;
        this.modalidad = modalidad;
        this.estado = estado;
    }

    public Evento(int idEvento, String nombre, String descripcion, LocalDate fecha, 
                  String lugar, String modalidad, String estado, 
                  LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.lugar = lugar;
        this.modalidad = modalidad;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    // GETTERS
    public int getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFecha() { return fecha; }
    public String getLugar() { return lugar; }
    public String getModalidad() { return modalidad; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    // SETTERS
    public void setIdEvento(int idEvento) { this.idEvento = idEvento; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "Evento{" + "id=" + idEvento + ", nombre='" + nombre + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return idEvento == evento.idEvento && Objects.equals(nombre, evento.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento, nombre);
    }
}
