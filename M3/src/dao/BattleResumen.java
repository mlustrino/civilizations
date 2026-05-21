package dao;

// Clase de datos simple que representa una fila de la tabla battle_stats.
// Se usa porque la clase Battle del juego todavía está incompleta.
public class BattleResumen {

    private int battleId;
    private int numBattle;
    private int woodAcquired;
    private int ironAcquired;

    public BattleResumen(int battleId, int numBattle, int woodAcquired, int ironAcquired) {
        this.battleId = battleId;
        this.numBattle = numBattle;
        this.woodAcquired = woodAcquired;
        this.ironAcquired = ironAcquired;
    }

    public int getBattleId()      { return battleId; }
    public int getNumBattle()     { return numBattle; }
    public int getWoodAcquired()  { return woodAcquired; }
    public int getIronAcquired()  { return ironAcquired; }

    @Override
    public String toString() {
        return "Batalla #" + numBattle + " [id=" + battleId + "] → Madera=" + woodAcquired + ", Hierro=" + ironAcquired;
    }
}
