package dao;

import militaryUnit.MilitaryUnit;
import java.util.ArrayList;

// Interfaz DAO para la tabla special_units_stats.
// Recibe el array army[] completo pero solo usa los slots de unidades especiales (índices 7-8).
public interface SpecialUnitDAO {

    // Reemplaza todas las unidades especiales guardadas
    void insertUnits(int civilizationId, ArrayList<MilitaryUnit>[] army);

    // Carga las unidades especiales de una civilización (Magician, Priest)
    ArrayList<MilitaryUnit> loadUnits(int civilizationId);

    // Elimina todas las unidades especiales de una civilización
    void deleteUnits(int civilizationId);
}
