package dao.impl;

import dao.CivilizationDAO;
import dao.AttackUnitDAO;
import dao.DefenseUnitDAO;
import dao.SpecialUnitDAO;
import civilizations.Civilization;
import database.DBConnection;
import militaryUnit.MilitaryUnit;
import Attack.Swordsman;
import Attack.Spearman;
import Attack.Crossbow;
import Attack.Cannon;
import defenseUnit.ArrowTower;
import defenseUnit.Catapult;
import defenseUnit.RocketLauncherTower;
import specialUnit.Magician;
import specialUnit.Priest;
import variables.Variables;

import java.sql.*;
import java.util.ArrayList;

public class CivilizationDAOImpl implements CivilizationDAO, Variables {

    private Connection conexion;

    // Delegamos la gestión de unidades a sus propios DAOs
    private AttackUnitDAO attackUnitDAO;
    private DefenseUnitDAO defenseUnitDAO;
    private SpecialUnitDAO specialUnitDAO;

    public CivilizationDAOImpl() {
        this.conexion = DBConnection.getInstance();
        this.attackUnitDAO = new AttackUnitDAOImpl();
        this.defenseUnitDAO = new DefenseUnitDAOImpl();
        this.specialUnitDAO = new SpecialUnitDAOImpl();
    }

    @Override
    public int insertCivilization(Civilization civ) {
    	System.out.println("entro a la función");
        String sql = "INSERT INTO civilization_stats " +
                     "(name, wood_amount, iron_amount, food_amount, mana_amount, " +
                     "magicTower_counter, church_counter, farm_counter, smithy_counter, " +
                     "carpentry_counter, technology_defense_level, technology_attack_level, battles_counter) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("entro al string la función");
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            System.out.println("antes de setear");
            setearParametros(ps, civ);

            System.out.println("dopo setear");
            ps.executeUpdate();

            ResultSet llaves = ps.getGeneratedKeys();
            if (llaves.next()) {
                int id = llaves.getInt(1);
                civ.setCivilization_id(id); // actualizamos el objeto con el ID real de la BD

                // Guardamos el ejército completo usando los DAOs específicos
                attackUnitDAO.insertUnits(id, civ.getArmy());
                defenseUnitDAO.insertUnits(id, civ.getArmy());
                specialUnitDAO.insertUnits(id, civ.getArmy());

                return id;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar civilización: " + e.getMessage());
        }
        return -1;
    }

    public void updateCivilization(Civilization civ) {
        String sql = "UPDATE civilization_stats SET " +
                     "name=?, wood_amount=?, iron_amount=?, food_amount=?, mana_amount=?, " +
                     "magicTower_counter=?, church_counter=?, farm_counter=?, smithy_counter=?, " +
                     "carpentry_counter=?, technology_defense_level=?, technology_attack_level=?, battles_counter=? " +
                     "WHERE civilization_id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            setearParametros(ps, civ);
            ps.setInt(14, civ.getCivilization_id());
            ps.executeUpdate();

            // Reemplazamos el ejército completo (borra e inserta de nuevo)
            attackUnitDAO.insertUnits(civ.getCivilization_id(), civ.getArmy());
            defenseUnitDAO.insertUnits(civ.getCivilization_id(), civ.getArmy());
            specialUnitDAO.insertUnits(civ.getCivilization_id(), civ.getArmy());
        } catch (SQLException e) {
            System.err.println("Error al actualizar civilización: " + e.getMessage());
        }
    }


    public Civilization loadCivilization(int civilizationId) {
        String sql = "SELECT * FROM civilization_stats WHERE civilization_id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Civilization civ = mapearResultSet(rs);
                ArrayList<MilitaryUnit>[] army = civ.getArmy();

                // Cargamos unidades de ataque y las colocamos en su slot correcto dentro de army[]
                for (MilitaryUnit u : attackUnitDAO.loadUnits(civilizationId)) {
                    if      (u instanceof Swordsman) army[IDX_UNIT_SWORDSMAN].add(u);
                    else if (u instanceof Spearman)  army[IDX_UNIT_SPEARMAN].add(u);
                    else if (u instanceof Crossbow)  army[IDX_UNIT_CROSSBOW].add(u);
                    else if (u instanceof Cannon)    army[IDX_UNIT_CANNON].add(u);
                }

                // Unidades de defensa
                for (MilitaryUnit u : defenseUnitDAO.loadUnits(civilizationId)) {
                    if      (u instanceof ArrowTower)          army[IDX_UNIT_ARROWTOWER].add(u);
                    else if (u instanceof Catapult)            army[IDX_UNIT_CATAPULT].add(u);
                    else if (u instanceof RocketLauncherTower) army[IDX_UNIT_ROCKETLAUNCHER].add(u);
                }

                // Unidades especiales
                for (MilitaryUnit u : specialUnitDAO.loadUnits(civilizationId)) {
                    if      (u instanceof Magician) army[IDX_UNIT_MAGICIAN].add(u);
                    else if (u instanceof Priest)   army[IDX_UNIT_PRIEST].add(u);
                }

                return civ;
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar civilización: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Civilization> getAllCivilizations() {
        ArrayList<Civilization> lista = new ArrayList<>();
        String sql = "SELECT * FROM civilization_stats";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs)); // Sin ejército: solo stats básicos
            }
        } catch (SQLException e) {
            System.err.println("Error al listar civilizaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void deleteCivilization(int civilizationId) {
        // Las tablas de unidades y batallas tienen ON DELETE CASCADE en la FK,
        // así que basta con eliminar de civilization_stats
        String sql = "DELETE FROM civilization_stats WHERE civilization_id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, civilizationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar civilización: " + e.getMessage());
        }
    }

    // Asigna los 13 campos de Civilization al PreparedStatement en el orden del SQL
    private void setearParametros(PreparedStatement ps, Civilization civ) throws SQLException {
        ps.setString(1, civ.getName());
        ps.setInt(2, civ.getWood());
        ps.setInt(3, civ.getIron());
        ps.setInt(4, civ.getFood());
        ps.setInt(5, civ.getMana());
        ps.setInt(6, civ.getMagicTower());
        ps.setInt(7, civ.getChurch());
        ps.setInt(8, civ.getFarm());
        ps.setInt(9, civ.getSmithy());
        ps.setInt(10, civ.getCarpentry());
        ps.setInt(11, civ.getTechnologyDefense());
        ps.setInt(12, civ.getTechnologyAttack());
        ps.setInt(13, civ.getBattles());
    }

    // Convierte una fila del ResultSet en un objeto Civilization (sin ejército)
    private Civilization mapearResultSet(ResultSet rs) throws SQLException {
        // Usamos el constructor con recursos para que el objeto quede correctamente inicializado
        Civilization civ = new Civilization(
            rs.getInt("wood_amount"),
            rs.getInt("iron_amount"),
            rs.getInt("food_amount"),
            rs.getInt("mana_amount")
        );
        civ.setCivilization_id(rs.getInt("civilization_id"));
        civ.setName(rs.getString("name"));
        civ.setMagicTower(rs.getInt("magicTower_counter"));
        civ.setChurch(rs.getInt("church_counter"));
        civ.setFarm(rs.getInt("farm_counter"));
        civ.setSmithy(rs.getInt("smithy_counter"));
        civ.setCarpentry(rs.getInt("carpentry_counter"));
        // setTechnologyDefense y setTechnologyAttack llaman a recalcularTechnologyCosts() internamente
        civ.setTechnologyDefense(rs.getInt("technology_defense_level"));
        civ.setTechnologyAttack(rs.getInt("technology_attack_level"));
        civ.setBattles(rs.getInt("battles_counter"));
        return civ;
    }
}
