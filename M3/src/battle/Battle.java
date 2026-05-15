
package battle;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.catalog.Catalog;

import Attack.*;
import defenseUnit.*;
import variables.*;
import militaryUnit.MilitaryUnit;
import specialUnit.Magician;
import specialUnit.Priest;




public class Battle implements Variables {
	
	private ArrayList<MilitaryUnit> civilizationArmy;
	private ArrayList<MilitaryUnit> enemyArmy;
	private ArrayList<MilitaryUnit> [][] armies;
	private String battleDevelopment;
	private int [][] initialCostFleet;
	private int initialNumberUnitsCivilization, initialNumberUnitsEnemy;
	private int [] wasteWoodIron;
	private int enemyDrops, civilizationDrops;
	private int [][] resourcesLooses;
	private int [][] initialArmies;
	private int [] actualNumberUnitsCivilization, actualNumberUnitsEnemy;
	
	public Battle(ArrayList<MilitaryUnit> civilizationArmy, ArrayList<MilitaryUnit> enemyArmy) {
		super();
		this.civilizationArmy = civilizationArmy;
		this.enemyArmy = enemyArmy;
		
	    // INICIALIZAR ARMIES
		this.armies = new ArrayList[2][9];
		for (int i = 0; i < 2; i++) {
		    for (int j = 0; j < 9; j++) {
		        this.armies[i][j] = new ArrayList<MilitaryUnit>();
		    }
		}
		
		for (MilitaryUnit unit : civilizationArmy) { // Este for es para aÃ±adir las unidades de nuestra civilizacion en el arrayList de armies
			if (unit instanceof Swordsman) { 
				armies[0][0].add(unit); // armies de 0 es nuestro ejercito y armies de 1 es el ejercito enemigo
			}
			else if (unit instanceof Spearman) {
				armies[0][1].add(unit);
			}
			else if (unit instanceof Crosswob) {
				armies[0][2].add(unit);
			}
			else if (unit instanceof Cannon) {
				armies[0][3].add(unit);
			}
			else if (unit instanceof ArrowTower) {
				armies[0][4].add(unit);
			}
			else if (unit instanceof Catapult) {
				armies[0][5].add(unit);
			}
			else if (unit instanceof RocketLauncherTower) {
				armies[0][6].add(unit);
			}
			else if (unit instanceof Magician) {
				armies[0][7].add(unit);
			}
			else if (unit instanceof Priest) {
				armies[0][8].add(unit);
			}
		}
		
		for (MilitaryUnit unit : enemyArmy) { // En este for no comprobamos el resto de clases ya que el ejercito enemigo solamente puede tener estas tropas
			if (unit instanceof Swordsman) { 
				armies[1][0].add(unit); 
			}
			else if (unit instanceof Spearman) {
				armies[1][1].add(unit);
			}
			else if (unit instanceof Crosswob) {
				armies[1][2].add(unit);
			}
			else if (unit instanceof Cannon) {
				armies[1][3].add(unit);
			}
		}
			
		this.battleDevelopment = "";
		
		this.initialCostFleet = new int [2][3];
		for (MilitaryUnit unit : civilizationArmy) {
			initialCostFleet[0][0] += unit.getFoodCost(); //Comida
			initialCostFleet[0][1] += unit.getWoodCost(); //Madera
			initialCostFleet[0][2] += unit.getIronCost(); //Hierro
		}
		
		for (MilitaryUnit unit : enemyArmy) {
			initialCostFleet[1][0] += unit.getFoodCost(); //Comida
			initialCostFleet[1][1] += unit.getWoodCost(); //Madera
			initialCostFleet[1][2] += unit.getIronCost(); //Hierro
		}
		
		this.initialNumberUnitsCivilization = getCivilizationArmy().size();
		this.initialNumberUnitsEnemy = getEnemyArmy().size();
		
		this.wasteWoodIron = new int [2];
		
		this.enemyDrops = 0;
		this.civilizationDrops = 0;
		
		this.resourcesLooses = new int [2][4];

		
		
		this.actualNumberUnitsCivilization = new int[9];
		this.actualNumberUnitsEnemy = new int[4];
	}
	
	//Setters y getters
	public ArrayList<MilitaryUnit> getCivilizationArmy() {
		return civilizationArmy;
	}
	public ArrayList<MilitaryUnit> getEnemyArmy() {
		return enemyArmy;
	}
	
	public int getInitialNumberUnitsCivilization() {
		return initialNumberUnitsCivilization;
	}

	public int getInitialNumberUnitsEnemy() {
		return initialNumberUnitsEnemy;
	}
	
	public ArrayList<MilitaryUnit>[][] getArmies() {
		return armies;
	}
	
	public int[][] getInitialArmies() {
		return initialArmies;
	}

	// Metodos de la clase Battle
	public String getBattleReport(int battles) {
		return "You have waged a total of " + battles;
	}
	
	public String getBattleDevelopment() {
		return battleDevelopment;
	}
	
	public void initInitialArmies() { //Es para inicializar el Array de initialArmies 
		initialArmies = new int [2][9];
		/*for (int i = 0; i < 9; i++) {
			initialArmies[0][i] = armies[0][i].size();
			initialArmies[1][i] = armies[1][i].size();
		}*/
 	}
	
	public void updateResourcesLooses() {
		resourcesLooses = new int [2][4];
	}
	
	public int[] fleetResourceCost(ArrayList<MilitaryUnit> army) { // Devolvemos un array con los costes del ejercito
		int[] costes = new int[3];
		for (int i = 0; i < army.size();i++) {
			costes[0] += army.get(i).getFoodCost(); // Comida, Madera, Hierro
			costes[1] += army.get(i).getWoodCost();
			costes[2] += army.get(i).getIronCost();
		}
		return costes;
	}
	
	public void initialFleetNumber(ArrayList<MilitaryUnit> army) { // Pone la cantidad que hay de cada tipo de tropa de cada ejercito
		
		for (int i = 0; i < army.size(); i++) {
			
			if (army.size() == 9) {
				initialArmies[0][i] = armies[0][i].size();
			}else {
				initialArmies[1][i] = armies[1][i].size();
			}
		}
	}
	
	public float remainderPercentageFleet(ArrayList<MilitaryUnit> army) { // Este metodo nos devuelve el porcentaje restante de nuestro ejercito respecto al inicial
		float porcentaje;
		int army_total = 0;
		
		for (int i = 0; i < army.size(); i++) {
			
			if (army.size() == 9) {
				army_total += armies[0][i].size();
			}else {
				army_total += armies[1][i].size();
			}
		}
		
		if (army == civilizationArmy) { 
			porcentaje = army_total * 100 / initialNumberUnitsCivilization;
		} else {
			porcentaje = army_total * 100 / initialNumberUnitsEnemy;
		}
		return porcentaje;
	}
	
	public int getGroupDefender(ArrayList<MilitaryUnit> army) {
		int num_unit;
		int sel_unit;
		
		if(army.size() == 9) {
			sel_unit = (int) Math.random()*8;
		}else {
			sel_unit = (int) Math.random()*3;
		}
		
		num_unit = sel_unit;
		
		return num_unit;
	}
	
	public int getCivilizationGroupAttacker() {
		int sel_unit = (int)Math.random()*8;
		return sel_unit;
	}
	
	public int getEnemyGroupAttacker() {
		int sel_unit = (int)Math.random()*4;
		return sel_unit;
	}
	
	public void resetArmyArmor() {
		for (int i = 0; i < civilizationArmy.size();i++) {
			civilizationArmy.get(i).resetArmor();
		}
	}
	
	
	public int startArmy() { // Para saber quien empieza 
		if (Math.random() < 0.5) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public void startBattle() {
		initInitialArmies();
//	    while (remainderPercentageFleet(civilizationArmy) > 20 && remainderPercentageFleet(enemyArmy) > 20) {
//	    	
//	    }
	}
	
	public int [] actualNumberUnitsCivilization() {
		for (int i = 0; i < actualNumberUnitsCivilization.length;i++) {
			actualNumberUnitsCivilization[i] = armies[0][i].size();
		}
		return actualNumberUnitsCivilization;
	}
	
	public int [] actualNumberUnitsEnemy() {
		for (int i = 0; i < actualNumberUnitsEnemy.length;i++) {
			actualNumberUnitsEnemy[i] = armies[1][i].size();
		}
		return actualNumberUnitsEnemy;
	}
	
}