package dao.impl;

import dao.BattleStatsDAO;
import dao.BattleResumen;
import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;

// Nota: cuando la clase Battle esté completa, se podrá añadir un método
// guardarBatalla(Battle batalla, Civilization civ) que llame a todos estos métodos de golpe.
public class BattleStatsDAOImpl implements BattleStatsDAO {

    private Connection conexion;

    public BattleStatsDAOImpl() {
        this.conexion = DBConnection.getInstance();
    }


    public int insertBattleStats(int civilizationId, int numBattle, int woodAcquired, int ironAcquired) {
        String sql = "INSERT INTO battle_stats (civilization_id, num_battle, wood_acquired, iron_acquired) " +
                     "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, civilizationId);
            ps.setInt(2, numBattle);
            ps.setInt(3, woodAcquired);
            ps.setInt(4, ironAcquired);
            ps.executeUpdate();

            ResultSet llaves = ps.getGeneratedKeys();
            if (llaves.next()) {
                return llaves.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar resumen de batalla: " + e.getMessage());
        }
        return -1;
    }

    public void insertStatsAttakCivilization(int civId, int numBattle,
                                                       String unitType, int initialArmy, int drops) {
        String sql = "INSERT INTO civilization_attack_stats " +
                     "(civilization_id, num_battle, unit_type, initial_army, drops) VALUES (?, ?, ?, ?, ?)";
        insertStats(sql, civId, numBattle, unitType, initialArmy, drops);
    }

    public void insertStatsDefenseCivilization(int civId, int numBattle,
                                                        String unitType, int initialArmy, int drops) {
        String sql = "INSERT INTO civilization_defense_stats " +
                     "(civilization_id, num_battle, unit_type, initial_army, drops) VALUES (?, ?, ?, ?, ?)";
        insertStats(sql, civId, numBattle, unitType, initialArmy, drops);
    }

    public void insertStatsSpecialCivilization(int civId, int numBattle,
                                                           String unitType, int initialArmy, int drops) {
        String sql = "INSERT INTO civilization_special_stats " +
                     "(civilization_id, num_battle, unit_type, initial_army, drops) VALUES (?, ?, ?, ?, ?)";
        insertStats(sql, civId, numBattle, unitType, initialArmy, drops);
    }

    public void insertStatsEnemiAttak(int civId, int numBattle,
                                                  String unitType, int initialArmy, int drops) {
        String sql = "INSERT INTO enemy_attack_stats " +
                     "(civilization_id, num_battle, unit_type, initial_army, drops) VALUES (?, ?, ?, ?, ?)";
        insertStats(sql, civId, numBattle, unitType, initialArmy, drops);
    }

    public ArrayList<BattleResumen> battleListByCivilization(int civilizationId) {
        ArrayList<BattleResumen> lista = new ArrayList<>();
        String sql = "SELECT * FROM battle_stats WHERE civilization_id=? ORDER BY num_battle";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new BattleResumen(
                    rs.getInt("battle_id"),
                    rs.getInt("num_battle"),
                    rs.getInt("wood_acquired"),
                    rs.getInt("iron_acquired")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar batallas: " + e.getMessage());
        }
        return lista;
    }


    public void deleteBattleByCivilization(int civilizationId) {
        // Borramos solo battle_stats; el CASCADE elimina las demás tablas de stats automáticamente
        String sql = "DELETE FROM battle_stats WHERE civilization_id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar batallas: " + e.getMessage());
        }
    }

    // Las 4 tablas de estadísticas tienen exactamente la misma estructura de INSERT,
    // así que usamos este método privado para evitar repetir código.
    private void insertStats(String sql, int civId, int numBattle,
                                             String unitType, int initialArmy, int drops) {
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civId);
            ps.setInt(2, numBattle);
            ps.setString(3, unitType);
            ps.setInt(4, initialArmy);
            ps.setInt(5, drops);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar estadísticas (" + unitType + "): " + e.getMessage());
        }
    }
}
