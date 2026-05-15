package dao.impl;

import dao.DefenseUnitDAO;
import database.DBConnection;
import civilizations.Civilization;
import militaryUnit.MilitaryUnit;
import defenseUnit.DefenseUnit;
import defenseUnit.ArrowTower;
import defenseUnit.Catapult;
import defenseUnit.RocketLauncherTower;

import java.sql.*;
import java.util.ArrayList;

public class DefenseUnitDAOImpl implements DefenseUnitDAO {

    private Connection conexion;

    private static final int[] INDICES = {
        Civilization.IDX_ARMY_ARROWTOWER,
        Civilization.IDX_ARMY_CATAPULT,
        Civilization.IDX_ARMY_ROCKETLAUNCHER
    };
    private static final String[] TIPOS = { "ArrowTower", "Catapult", "RocketLauncherTower" };

    public DefenseUnitDAOImpl() {
        this.conexion = DBConnection.getInstance();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void insertUnits(int civilizationId, ArrayList<MilitaryUnit>[] army) {
        deleteUnits(civilizationId);

        String sql = "INSERT INTO defense_units_stats " +
                     "(civilization_id, unit_id, type, armor, base_damage, experience, sanctified) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            int unitId = 0;
            for (int k = 0; k < INDICES.length; k++) {
                for (MilitaryUnit m : army[INDICES[k]]) {
                    DefenseUnit u = (DefenseUnit) m;
                    ps.setInt(1, civilizationId);
                    ps.setInt(2, unitId++);
                    ps.setString(3, TIPOS[k]);
                    ps.setInt(4, u.getActualArmor());
                    ps.setInt(5, u.getBaseDamage());
                    ps.setInt(6, u.getExperience());
                    ps.setBoolean(7, u.isSanctified());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al guardar unidades de defensa: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<MilitaryUnit> loadUnits(int civilizationId) {
        ArrayList<MilitaryUnit> lista = new ArrayList<>();
        String sql = "SELECT * FROM defense_units_stats WHERE civilization_id=? ORDER BY unit_id";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DefenseUnit u = crearUnidad(
                    rs.getString("type"),
                    rs.getInt("armor"),
                    rs.getInt("base_damage")
                );
                if (u != null) {
                    u.setExperience(rs.getInt("experience"));
                    u.setSanctified(rs.getBoolean("sanctified"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar unidades de defensa: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void deleteUnits(int civilizationId) {
        String sql = "DELETE FROM defense_units_stats WHERE civilization_id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar unidades de defensa: " + e.getMessage());
        }
    }

    private DefenseUnit crearUnidad(String tipo, int armor, int baseDamage) {
        switch (tipo) {
            case "ArrowTower":          return new ArrowTower(armor, baseDamage);
            case "Catapult":            return new Catapult(armor, baseDamage);
            case "RocketLauncherTower": return new RocketLauncherTower(armor, baseDamage);
            default:
                System.err.println("Tipo de unidad de defensa desconocido en BD: " + tipo);
                return null;
        }
    }
}
