package battle;
import java.util.ArrayList;

import militaryUnit.MilitaryUnit;

public class Battle {
	private ArrayList<MilitaryUnit> civilizationArmy;
	private ArrayList<MilitaryUnit> enemyArmy;
	private ArrayList armies;
	private String battleDevelopment;
	private int [][] initalCostFleet; // = new int [2][3];
	private int initalNumberUnitsCivilization, initialNumberUnitsEnemy;
	private int wasteWoodIron;
	private int enemyDrops, civilizationDrops;
	private int [][] resourcesLooses; // = new int [2][4];
	private int [][] initialArmies; // = new int [2][9];
	private int actualNumberUnitsCivilization, actualNumberUnitsEnemy;
	
	public Battle(ArrayList<MilitaryUnit> civilizationArmy, ArrayList<MilitaryUnit> enemyArmy) {
		super();
		this.civilizationArmy = civilizationArmy;
		this.enemyArmy = enemyArmy;
	}
	//Setters y getters
	public ArrayList<MilitaryUnit> getCivilizationArmy() {
		return civilizationArmy;
	}
	public ArrayList<MilitaryUnit> getEnemyArmy() {
		return enemyArmy;
	}

	// Metodos de la clase Battle
	public String getBattleReport(int battles) {
		return "Has librado un total de " + battles;
	}
	
	public String getBattleDevelopment() {
		return ""; // paso a paso?? (eso pone en el documento)
	}
	
	public void initInitialArmies() {
		initialArmies = new int [2][9]; // Falta darle los valores de los ejercitos

	}
	
	public void updateResourcesLooses() {
		resourcesLooses = new int [2][4];
	}
	
	public int fleetResourceCost(ArrayList<MilitaryUnit> army) {
		
		return 0;
	}
	
	public int initialFleetNumber(ArrayList<MilitaryUnit> army) {
		
		return 0;
	}
	
	public int remainderPercentageFleet(ArrayList<MilitaryUnit> army) {
		
		return 0;
	}
	
	public int getGroupDefender(ArrayList<MilitaryUnit> army) {
		
		return 0;
	}
	
	public int getCivilizationGroupAttacker() {
		
		return 0;
	}
	
	public int getEnemyGroupAttacker() {
		
		return 0;
	}
	
	public void resetArmyArmor() {
		
	}
	
	
	
	
	
}





















