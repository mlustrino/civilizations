package dao;

import java.util.ArrayList;

// Interfaz DAO para las 5 tablas de estadísticas de batalla:
//   battle_stats, civilization_attack_stats, civilization_defense_stats,
//   civilization_special_stats, enemy_attack_stats.
//
// Nota: diseñada para encajar con la clase Battle cuando esté completa.
// Los métodos de estadísticas se llaman una vez por tipo de unidad presente en la batalla.
public interface BattleStatsDAO {

    // Guarda el resumen de la batalla en battle_stats. Devuelve el battle_id generado (-1 si falla)
    int insertBattleStats(int civilizationId, int numBattle, int woodAcquired, int ironAcquired);

    // Guarda estadísticas de unidades de ataque de la civilización (civilization_attack_stats)
    // unitType: "Swordsman", "Spearman", "Crossbow" o "Cannon"
    // initialArmy: cuántas había al inicio  |  drops: cuántas se perdieron
    void insertStatsAttakCivilization(int civId, int numBattle, String unitType, int initialArmy, int drops);

    // Guarda estadísticas de unidades de defensa de la civilización (civilization_defense_stats)
    // unitType: "ArrowTower", "Catapult" o "RocketLauncherTower"
    void insertStatsDefenseCivilization(int civId, int numBattle, String unitType, int initialArmy, int drops);

    // Guarda estadísticas de unidades especiales de la civilización (civilization_special_stats)
    // unitType: "Magician" o "Priest"
    void insertStatsSpecialCivilization(int civId, int numBattle, String unitType, int initialArmy, int drops);

    // Guarda estadísticas del ejército enemigo (enemy_attack_stats)
    // unitType: "Swordsman", "Spearman", "Crossbow" o "Cannon"
    void insertStatsEnemiAttak(int civId, int numBattle, String unitType, int initialArmy, int drops);

    // Actualiza los recursos adquiridos al terminar la batalla
    void updateBattleStats(int civilizationId, int numBattle, int woodAcquired, int ironAcquired);

    // Actualiza las bajas de cada tipo de unidad al terminar la batalla (drops = inicial - sobrevivientes)
    void updateDropsAttakCivilization(int civId, int numBattle, String unitType, int drops);
    void updateDropsDefenseCivilization(int civId, int numBattle, String unitType, int drops);
    void updateDropsSpecialCivilization(int civId, int numBattle, String unitType, int drops);
    void updateDropsEnemyAttak(int civId, int numBattle, String unitType, int drops);
    // Lista todos los resúmenes de batalla de una civilización, ordenados por numBattle
    ArrayList<BattleResumen> battleListByCivilization(int civilizationId);

    // Devuelve el siguiente num_battle disponible para una civilización (MAX actual + 1)
    int getNextBattleNum(int civilizationId);

    // Elimina en cascada. todos los registros de batalla de una civilización
    void deleteBattleByCivilization(int civilizationId);
}
