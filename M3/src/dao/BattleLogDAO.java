package dao;

// Interfaz DAO para la tabla battle_log.
// El log se guarda línea a línea en la BD y se reconstruye al cargar.
public interface BattleLogDAO {

    // Guarda el texto completo de battleDevelopment (lo divide por saltos de línea)
    void insert(int civilizationId, int numBattle, String logCompleto);

}
