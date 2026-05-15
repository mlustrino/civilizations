package dao.impl;

import dao.SpecialUnitDAO;
import database.DBConnection;
import civilizations.Civilization;
import militaryUnit.MilitaryUnit;
import specialUnit.SpecialUnit;
import variables.Variables;
import specialUnit.Magician;
import specialUnit.Priest;

import java.sql.*;
import java.util.ArrayList;

public class SpecialUnitDAOImpl implements SpecialUnitDAO, Variables {

    private Connection conexion;

    private static final int[] INDICES = {
    		IDX_UNIT_MAGICIAN,
    		IDX_UNIT_PRIEST
    };
    private static final String[] TIPOS = { "Magician", "Priest" };

    public SpecialUnitDAOImpl() {
        this.conexion = DBConnection.getInstance();
    }


    public void insertUnits(int civilizationId, ArrayList<MilitaryUnit>[] army) {
        deleteUnits(civilizationId);

        // Las unidades especiales no tienen campo sanctified en la BD
        String sql = "INSERT INTO special_units_stats " +
                     "(civilization_id, unit_id, type, armor, base_damage, experience) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            int unitId = 0;
            for (int k = 0; k < INDICES.length; k++) {
                for (MilitaryUnit m : army[INDICES[k]]) {
                    SpecialUnit u = (SpecialUnit) m;
                    ps.setInt(1, civilizationId);
                    ps.setInt(2, unitId++);
                    ps.setString(3, TIPOS[k]);
                    ps.setInt(4, u.getActualArmor());
                    ps.setInt(5, u.getBaseDamage());
                    ps.setInt(6, u.getExperience());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al guardar unidades especiales: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<MilitaryUnit> loadUnits(int civilizationId) {
        ArrayList<MilitaryUnit> lista = new ArrayList<>();
        String sql = "SELECT * FROM special_units_stats WHERE civilization_id=? ORDER BY unit_id";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SpecialUnit u = createUnit(
                    rs.getString("type"),
                    rs.getInt("armor"),
                    rs.getInt("base_damage")
                );
                if (u != null) {
                    u.setExperience(rs.getInt("experience"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar unidades especiales: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void deleteUnits(int civilizationId) {
        String sql = "DELETE FROM special_units_stats WHERE civilization_id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar unidades especiales: " + e.getMessage());
        }
    }

    private SpecialUnit createUnit(String tipo, int armor, int baseDamage) {
        switch (tipo) {
            case "Magician": return new Magician(armor, baseDamage);
            case "Priest":   return new Priest(armor, baseDamage);
            default:
                System.err.println("Tipo de unidad especial desconocido en BD: " + tipo);
                return null;
        }
    }
}
