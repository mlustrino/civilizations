package dao;

import militaryUnit.MilitaryUnit;
import java.util.ArrayList;

// Interfaz DAO para la tabla defense_units_stats.
// Recibe el array army[] completo pero solo usa los slots de defensa (índices 4-6).
public interface DefenseUnitDAO {

    // Reemplaza todas las unidades de defensa guardadas
    void insertUnits(int civilizationId, ArrayList<MilitaryUnit>[] army);

    // Carga las unidades de defensa de una civilización (ArrowTower, Catapult, RocketLauncherTower)
    ArrayList<MilitaryUnit> loadUnits(int civilizationId);

    // Elimina todas las unidades de defensa de una civilización
    void deleteUnits(int civilizationId);
}
