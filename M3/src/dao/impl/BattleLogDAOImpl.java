package dao.impl;

import dao.BattleLogDAO;
import database.DBConnection;
import java.sql.*;

public class BattleLogDAOImpl implements BattleLogDAO {

    private Connection conexion;

    public BattleLogDAOImpl() {
        this.conexion = DBConnection.getInstance();
    }

    public void insert(int civilizationId, int numBattle, String logCompleto) {
        delete(civilizationId, numBattle);

        String sql = "INSERT INTO battle_log (civilization_id, num_battle, num_line, log_entry) " +
                     "VALUES (?, ?, ?, ?)";
        // Dividimos el texto del log por saltos de línea para insertar una fila por línea
        String[] lineas = logCompleto.split("\n", -1);
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (int i = 0; i < lineas.length; i++) {
                ps.setInt(1, civilizationId);
                ps.setInt(2, numBattle);
                ps.setInt(3, i);              // num_line = índice de la línea
                ps.setString(4, lineas[i]);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al guardar log de batalla: " + e.getMessage());
        }
    }

    public String load(int civilizationId, int numBattle) {
        String sql = "SELECT log_entry FROM battle_log " +
                     "WHERE civilization_id=? AND num_battle=? ORDER BY num_line";
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.setInt(2, numBattle);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (sb.length() > 0) sb.append("\n");
                sb.append(rs.getString("log_entry"));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar log de batalla: " + e.getMessage());
        }
        return sb.toString();
    }
    
    public void delete(int civilizationId, int numBattle) {
        String sql = "DELETE FROM battle_log WHERE civilization_id=? AND num_battle=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.setInt(2, numBattle);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar log de batalla: " + e.getMessage());
        }
    }
}
