package dao;

import militaryUnit.MilitaryUnit;
import java.util.ArrayList;

// Interfaz DAO para la tabla attack_units_stats.
// Recibe el array army[] completo pero solo usa los slots de unidades de ataque (índices 0-3).
public interface AttackUnitDAO {

    // Reemplaza todas las unidades de ataque guardadas (borra las antiguas e inserta las actuales)
    void insertUnits(int civilizationId, ArrayList<MilitaryUnit>[] army);

    // Carga las unidades de ataque de una civilización (Swordsman, Spearman, Crossbow, Cannon)
    ArrayList<MilitaryUnit> loadUnits(int civilizationId);

    // Elimina todas las unidades de ataque de una civilización
    void deleteUnits(int civilizationId);
}
