package dao.impl;

import dao.AttackUnitDAO;
import database.DBConnection;
import civilizations.Civilization;
import militaryUnit.MilitaryUnit;
import Attack.attackUnit;
import Attack.Swordsman;
import Attack.Spearman;
import Attack.Crosswob;
import Attack.Cannon;

import java.sql.*;
import java.util.ArrayList;

public class AttackUnitDAOImpl implements AttackUnitDAO {

    private Connection conexion;

    // Posiciones en army[] que corresponden a unidades de ataque y sus nombres en la BD
    private static final int[] INDICES = {
        Civilization.IDX_ARMY_SWORDSMAN,
        Civilization.IDX_ARMY_SPEARMAN,
        Civilization.IDX_ARMY_CROSSBOW,
        Civilization.IDX_ARMY_CANNON
    };
    private static final String[] TIPOS = { "Swordsman", "Spearman", "Crossbow", "Cannon" };

    public AttackUnitDAOImpl() {
        this.conexion = DBConnection.getInstance();
    }

    @Override
    @SuppressWarnings("unchecked") // Necesario por el array genérico ArrayList<MilitaryUnit>[]
    public void insertUnits(int civilizationId, ArrayList<MilitaryUnit>[] army) {
    	deleteUnits(civilizationId);

        String sql = "INSERT INTO attack_units_stats " +
                     "(civilization_id, unit_id, type, armor, base_damage, experience, sanctified) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            int unitId = 0;
            // Iteramos por cada tipo de unidad de ataque según su posición en army[]
            for (int k = 0; k < INDICES.length; k++) {
                for (MilitaryUnit m : army[INDICES[k]]) {
                    attackUnit u = (attackUnit) m; // El cast es seguro: army[0-3] solo tiene attackUnits
                    ps.setInt(1, civilizationId);
                    ps.setInt(2, unitId++);
                    ps.setString(3, TIPOS[k]);
                    ps.setInt(4, u.getActualArmor());
                    ps.setInt(5, u.getBaseDamage());
                    ps.setInt(6, u.getExperience());
                    ps.setBoolean(7, u.isSanctified());
                    ps.addBatch(); // addBatch acumula las inserciones para ejecutarlas todas de golpe
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al guardar unidades de ataque: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<MilitaryUnit> loadUnits(int civilizationId) {
        ArrayList<MilitaryUnit> lista = new ArrayList<>();
        String sql = "SELECT * FROM attack_units_stats WHERE civilization_id=? ORDER BY unit_id";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                attackUnit u = createUnit(
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
            System.err.println("Error al cargar unidades de ataque: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void deleteUnits(int civilizationId) {
        String sql = "DELETE FROM attack_units_stats WHERE civilization_id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar unidades de ataque: " + e.getMessage());
        }
    }

    // Crea la subclase concreta correcta según el valor del campo type en la BD
    private attackUnit createUnit(String tipo, int armor, int baseDamage) {
        switch (tipo) {
            case "Swordsman": return new Swordsman(armor, baseDamage);
            case "Spearman":  return new Spearman(armor, baseDamage);
            case "Crossbow":  return new Crosswob(armor, baseDamage); // El nombre de la clase tiene un typo: Crosswob
            case "Cannon":    return new Cannon(armor, baseDamage);
            default:
                System.err.println("Tipo de unidad de ataque desconocido en BD: " + tipo);
                return null;
        }
    }
}
