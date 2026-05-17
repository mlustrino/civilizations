package dao;

// Interfaz DAO para la tabla battle_log.
// El log se guarda línea a línea en la BD y se reconstruye al cargar.
public interface BattleLogDAO {

    // Guarda el texto completo de battleDevelopment (lo divide por saltos de línea)
    void insert(int civilizationId, int numBattle, String logCompleto);

    // Carga todas las líneas del log de una batalla y las une en un único String
    String load(int civilizationId, int numBattle);

    // Elimina el log completo de una batalla
    void delete(int civilizationId, int numBattle);
}
