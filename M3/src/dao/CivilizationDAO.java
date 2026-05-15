package dao;

import civilizations.Civilization;
import java.util.ArrayList;

// Interfaz DAO para la tabla civilization_stats.
// Trabaja directamente con la clase Civilization del juego.
public interface CivilizationDAO {

    // Guarda una civilización nueva (stats + ejército) y devuelve el ID generado (-1 si falla)
    int insertCivilization(Civilization civilizacion);

    // Actualiza los datos de una civilización existente (stats + ejército)
    void updateCivilization(Civilization civilizacion);

    // Carga una civilización completa (stats + ejército) desde la base de datos. Devuelve null si no existe
    Civilization loadCivilization(int civilizationId);

    // Lista todas las civilizaciones con sus stats básicos (sin cargar el ejército)
    ArrayList<Civilization> getAllCivilizations();

    // Elimina una civilización y todo lo asociado (el CASCADE de la BD borra unidades y batallas)
    void deleteCivilization(int civilizationId);
}
