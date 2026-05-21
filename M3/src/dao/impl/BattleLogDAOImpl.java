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
        //delete(civilizationId, numBattle);

        String sql = "INSERT INTO battle_log (civilization_id, num_battle, num_line, log_entry) " +
                     "VALUES (?, ?, ?, ?)";
        // Dividimos el texto del log por saltos de línea para insertar una fila por línea

    	int numLineas = logCompleto.split("\r?\n").length;
        //String[] lineas = logCompleto.split("\n", -1);
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.setInt(2, numBattle);
            ps.setInt(3, numLineas);              // num_line = índice de la línea
            ps.setString(4, logCompleto);
            //ps.addBatch();  

            ps.executeUpdate();
//            
//            for (int i = 0; i < lineas.length; i++) {
//                ps.setInt(1, civilizationId);
//                ps.setInt(2, numBattle);
//                ps.setInt(3, i);              // num_line = índice de la línea
//                ps.setString(4, lineas[i]);
//                ps.addBatch();
//            }
            //ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al guardar log de batalla: " + e.getMessage());
        }
    }
}
