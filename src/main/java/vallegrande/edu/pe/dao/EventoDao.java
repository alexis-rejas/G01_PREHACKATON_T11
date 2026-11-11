package vallegrande.edu.pe.dao;

import vallegrande.edu.pe.model.Evento;
import vallegrande.edu.pe.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para Evento - Operaciones CRUD
 */
public class EventoDao implements IDao<Evento> {
    private static final String TABLE = "eventos";
    private final DatabaseConnection dbConnection;

    public EventoDao() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public boolean create(Evento evento) {
        String sql = "INSERT INTO " + TABLE + 
                    " (nombre, descripcion, fecha, lugar, modalidad, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, evento.getNombre());
            pstmt.setString(2, evento.getDescripcion());
            pstmt.setDate(3, Date.valueOf(evento.getFecha()));
            pstmt.setString(4, evento.getLugar());
            pstmt.setString(5, evento.getModalidad());
            pstmt.setString(6, evento.getEstado());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error crear evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Evento read(int id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_evento = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapToEvento(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error leer evento: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Evento> readAll() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY fecha DESC";
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                eventos.add(mapToEvento(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error leer eventos: " + e.getMessage());
        }
        return eventos;
    }

    @Override
    public boolean update(Evento evento) {
        String sql = "UPDATE " + TABLE + 
                    " SET nombre = ?, descripcion = ?, fecha = ?, lugar = ?, " +
                    "modalidad = ?, estado = ? WHERE id_evento = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, evento.getNombre());
            pstmt.setString(2, evento.getDescripcion());
            pstmt.setDate(3, Date.valueOf(evento.getFecha()));
            pstmt.setString(4, evento.getLugar());
            pstmt.setString(5, evento.getModalidad());
            pstmt.setString(6, evento.getEstado());
            pstmt.setInt(7, evento.getIdEvento());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id_evento = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Evento> search(String criteria, String value) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " WHERE " + criteria + " LIKE ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + value + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                eventos.add(mapToEvento(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error buscar evento: " + e.getMessage());
        }
        return eventos;
    }

    private Evento mapToEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setIdEvento(rs.getInt("id_evento"));
        evento.setNombre(rs.getString("nombre"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setFecha(rs.getDate("fecha").toLocalDate());
        evento.setLugar(rs.getString("lugar"));
        evento.setModalidad(rs.getString("modalidad"));
        evento.setEstado(rs.getString("estado"));
        evento.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        evento.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime());
        return evento;
    }
}
